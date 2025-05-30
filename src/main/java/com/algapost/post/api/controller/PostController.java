package com.algapost.post.api.controller;

import com.algapost.post.api.model.PostInput;
import com.algapost.post.api.model.PostOutput;
import com.algapost.post.api.model.PostSummaryOutput;
import com.algapost.post.domain.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

  private final PostService service;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public PostSummaryOutput create(@RequestBody PostInput input) {
    return service.save(input);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public Page<PostOutput> search(Pageable pageable) {
    return service.search(pageable);
  }

  @GetMapping("/{postId}")
  @ResponseStatus(HttpStatus.OK)
  public PostOutput findByPostId(@PathVariable String postId) {
    return service.findById(postId);
  }

}
