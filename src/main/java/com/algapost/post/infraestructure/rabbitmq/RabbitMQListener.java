package com.algapost.post.infraestructure.rabbitmq;


import com.algapost.post.api.model.PostDataResult;
import com.algapost.post.domain.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;


@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitMQListener {

  private final PostService service;

  @SneakyThrows
  @RabbitListener(queues = RabbitMQConfig.QUEUE_POST_RESULT, concurrency = "2-3")
  public void handleTextProcessResult(@Payload PostDataResult postData,
                     @Headers Map<String, Object> headers) {

    log.info("Received msg {} from queue {}.", postData, RabbitMQConfig.QUEUE_POST_RESULT);
    service.processTextResult(postData);

  }

}
