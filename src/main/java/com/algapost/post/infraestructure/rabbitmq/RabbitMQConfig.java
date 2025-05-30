package com.algapost.post.infraestructure.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

  public static final String FANOUT_EXCHANGE_TEXT_PROCESSOR = "text-processor-service.post-processing.v1.e";
  public static final String FANOUT_EXCHANGE_POST_RESULT = "post-service.post-processing-result.v1.e";
  public static final String QUEUE_POST_RESULT = "post-service.post-processing-result.v1.q";

  @Bean
  public Jackson2JsonMessageConverter jackson2JsonMessageConverter(ObjectMapper objectMapper) {
    return new Jackson2JsonMessageConverter(objectMapper);
  }
  @Bean
  public RabbitAdmin rabbitAdmin(ConnectionFactory factory) {
    return new RabbitAdmin(factory);
  }
  @Bean
  public FanoutExchange textProcessorExchange() {
    return ExchangeBuilder.fanoutExchange(FANOUT_EXCHANGE_TEXT_PROCESSOR).build();
  }

  @Bean
  public FanoutExchange postProcessingResultExchange() {
    return ExchangeBuilder.fanoutExchange(FANOUT_EXCHANGE_POST_RESULT).build();
  }

}
