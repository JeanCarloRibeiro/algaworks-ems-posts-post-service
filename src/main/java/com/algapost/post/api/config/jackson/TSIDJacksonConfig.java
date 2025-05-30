package com.algapost.post.api.config.jackson;

import com.algapost.post.api.model.PostId;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TSIDJacksonConfig {
  @Bean
  public Module tsidModule() {
    SimpleModule module = new SimpleModule();
    module.addSerializer(PostId.class, new PostIdToStringSerializer());
    return module;
  }

}
