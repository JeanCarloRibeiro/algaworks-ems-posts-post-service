package com.algapost.post.domain.service;

import com.algapost.post.api.model.PostDataResult;
import com.algapost.post.api.model.PostId;
import com.algapost.post.api.model.PostInput;
import com.algapost.post.api.model.PostOutput;
import com.algapost.post.api.model.PostSummaryOutput;
import com.algapost.post.common.IdGenerator;
import com.algapost.post.domain.model.Post;
import com.algapost.post.domain.model.PostData;
import com.algapost.post.domain.repository.PostRepository;
import com.algapost.post.domain.service.exception.FieldNotFoundException;
import com.algapost.post.domain.service.exception.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static com.algapost.post.infraestructure.rabbitmq.RabbitMQConfig.FANOUT_EXCHANGE_TEXT_PROCESSOR;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {

  private final PostRepository repository;
  private final RabbitTemplate rabbitTemplate;

  public PostSummaryOutput save(PostInput input) {

    validRequiredFields(input);

    Post post = Post.builder()
            .id(new PostId(IdGenerator.generateTimeBasedUUID()))
            .author(input.getAuthor())
            .title(input.getTitle())
            .body(input.getBody())
            .build();

    post = repository.saveAndFlush(post);
    log.info("Successfully created Post with id {}.", post.getId());

    PostData data = PostData.builder()
            .postId(post.getId().toString())
            .postBody(input.getBody())
            .build();

    rabbitTemplate.convertAndSend(FANOUT_EXCHANGE_TEXT_PROCESSOR, "", data);
    log.info("Sent Post object {} to process result to {}.", data, "TextProcessorService");
    return convertToModelSummary(post);

  }

  private void validRequiredFields(PostInput input) {
    if (input.getTitle().isEmpty()) {
      throw new FieldNotFoundException("Title is missing.");
    }
    if (input.getBody().isEmpty()) {
      throw new FieldNotFoundException("Body is missing.");
    }
    if (input.getAuthor().isEmpty()) {
      throw new FieldNotFoundException("Author is missing.");
    }

  }

  public Page<PostOutput> search(Pageable pageable) {
    Page<Post> posts = repository.findAll(pageable);
    if (posts.getTotalElements() == 0) {
      throw new PostNotFoundException("Posts not found.");
    }
    return posts.map(this::convertToModel);
  }

  @GetMapping("/{postId}")
  public PostOutput findById(@PathVariable String postId) {
    Post post = repository.findById(new PostId(postId))
            .orElseThrow(() -> new PostNotFoundException("Posts not found."));
    return convertToModel(post);
  }

  private PostOutput convertToModel(Post post) {
    return PostOutput.builder()
            .id(post.getId().toString())
            .title(post.getTitle())
            .author(post.getAuthor())
            .body(post.getBody())
            .calculatedValue(post.getCalculatedValue())
            .wordCount(post.getWordCount())
            .build();
  }

  private PostSummaryOutput convertToModelSummary(Post post) {
    return PostSummaryOutput.builder()
            .id(post.getId().toString())
            .title(post.getTitle())
            .author(post.getAuthor())
            .summary(getFirstRows(post.getBody()))
            .build();
  }

  private String getFirstRows(String input) {
    String[] rows = input.split("\n");
    StringBuilder result = new StringBuilder();
    int rowsToTake = Math.min(3, rows.length);

    for (int i = 0; i < rowsToTake; i++) {
      result.append(rows[i]);
      if (i < rowsToTake - 1) {
        result.append("\n");
      }
    }
    return result.toString();
  }

  public void processTextResult(PostDataResult postData) {

    Post post = repository.findById(new PostId(postData.getPostId()))
            .orElseThrow(() -> new PostNotFoundException("Post Notfound."));

    post.setWordCount(postData.getWordCount());
    post.setCalculatedValue(postData.getCalculatedValue());
    repository.saveAndFlush(post);

    log.info("Successfully updated Post {}.", postData);

  }

}
