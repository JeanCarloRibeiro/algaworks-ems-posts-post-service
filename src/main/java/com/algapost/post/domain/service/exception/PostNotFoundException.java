package com.algapost.post.domain.service.exception;

public class PostNotFoundException extends RuntimeException {
  public PostNotFoundException(String message) {
    super(message);
  }
}
