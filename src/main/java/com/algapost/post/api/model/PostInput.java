package com.algapost.post.api.model;

import lombok.Data;

@Data
public class PostInput {
  private String title;
  private String body;
  private String author;
}
