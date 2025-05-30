package com.algapost.post.api.config.web;

import com.algapost.post.domain.service.exception.FieldNotFoundException;
import com.algapost.post.domain.service.exception.PostNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler({FieldNotFoundException.class, PostNotFoundException.class})
  public ProblemDetail handleNotFoundException(RuntimeException e) {
    ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
    problemDetail.setTitle("Not found");
    problemDetail.setDetail(e.getMessage());
    problemDetail.setType(URI.create("/errors/not-found"));
    return problemDetail;
  }

}
