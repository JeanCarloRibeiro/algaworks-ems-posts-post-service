package com.algapost.post.domain.service.exception;

public class FieldNotFoundException extends RuntimeException {
  public FieldNotFoundException(String message) {
    super(message);
  }
}
