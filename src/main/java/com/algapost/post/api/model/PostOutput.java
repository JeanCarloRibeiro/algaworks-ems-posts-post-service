package com.algapost.post.api.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data @Builder
public class PostOutput {
  private String id;
  private String title;
  private String body;
  private String author;
  private Integer wordCount;
  private BigDecimal calculatedValue;
}
