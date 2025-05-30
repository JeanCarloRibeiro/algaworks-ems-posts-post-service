package com.algapost.post.domain.model;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class PostData {
  private String postId;
  private String postBody;
}
