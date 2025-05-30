package com.algapost.post.domain.model;

import com.algapost.post.api.model.PostId;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@AllArgsConstructor @NoArgsConstructor @Data @Builder
public class Post {
  @Id
  @AttributeOverride(name = "value", column = @Column(name = "id", columnDefinition = "UUID"))
  private PostId id;
  private String title;
  private String body;
  private String author;
  private Integer wordCount;
  private BigDecimal calculatedValue;
}
