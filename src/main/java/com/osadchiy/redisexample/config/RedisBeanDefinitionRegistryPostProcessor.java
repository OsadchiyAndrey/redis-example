package com.osadchiy.redisexample.config;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.core.env.Environment;

@Log4j2
public class RedisBeanDefinitionRegistryPostProcessor  implements BeanDefinitionRegistryPostProcessor {

  private RedisProperties redisProperties;

  public RedisBeanDefinitionRegistryPostProcessor(Environment environment) {
    Binder binder = Binder.get(environment);
    redisProperties = binder.bind("redis", RedisProperties.class).get();
  }

  @Override
  public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) {
    AbstractBeanDefinition redisClientBeanDefinition = getRedisClientBeanDefinition();
    registry.registerBeanDefinition("redisClient", redisClientBeanDefinition);

    AbstractBeanDefinition redisConnectionDefinition = getRedisConnectionBeanDefinition();
    registry.registerBeanDefinition("redisConnection", redisConnectionDefinition);

    AbstractBeanDefinition redisCommandsDefinition = getRedisCommandsBeanDefinition();
    registry.registerBeanDefinition("redisCommands", redisCommandsDefinition);
  }

  @Override
  public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
  }

  private AbstractBeanDefinition getRedisClientBeanDefinition() {
    return BeanDefinitionBuilder
        .genericBeanDefinition(RedisClient.class)
        .setFactoryMethod("create")
        .addConstructorArgValue("redis://" + redisProperties.getHost() + ":" + redisProperties.getPort())
        .getBeanDefinition();
  }

  private AbstractBeanDefinition getRedisConnectionBeanDefinition() {
    return BeanDefinitionBuilder.genericBeanDefinition(StatefulRedisConnection.class)
        .setFactoryMethodOnBean("connect", "redisClient")
        .setPrimary(true)
        .getBeanDefinition();
  }

  private AbstractBeanDefinition getRedisCommandsBeanDefinition() {
    return BeanDefinitionBuilder
        .genericBeanDefinition(RedisCommands.class)
        .setFactoryMethodOnBean("sync", "redisConnection")
        .setPrimary(true)
        .getBeanDefinition();
  }

}
