package com.algapost.post.api.config.jackson;

import com.algapost.post.api.model.PostId;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class PostIdToStringSerializer extends JsonSerializer<PostId> {

  @Override
  public void serialize(PostId value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
    gen.writeString(value.toString());
  }
}
