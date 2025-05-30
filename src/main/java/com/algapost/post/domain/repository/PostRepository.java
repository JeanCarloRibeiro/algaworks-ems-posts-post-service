package com.algapost.post.domain.repository;

import com.algapost.post.api.model.PostId;
import com.algapost.post.domain.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, PostId> {

}
