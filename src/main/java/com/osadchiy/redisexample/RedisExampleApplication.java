package com.osadchiy.redisexample;

import com.osadchiy.redisexample.config.RedisBeanDefinitionRegistryPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;

@SpringBootApplication
public class RedisExampleApplication {

  public static void main(String[] args) {
    SpringApplication.run(RedisExampleApplication.class, args);
  }

  @Bean
  public RedisBeanDefinitionRegistryPostProcessor redisBeanDefinitionRegistryPostProcessor(ConfigurableEnvironment environment) {
    return new RedisBeanDefinitionRegistryPostProcessor(environment);
  }

}
