package com.osadchiy.redisexample.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.osadchiy.redisexample.entity.Student;
import com.osadchiy.redisexample.service.StudentService;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

  private ApplicationContext context;

  @Override
  public List<Student> findAll() {
    RedisCommands<String, String> redisCommands = context.getBean(RedisCommands.class);
    List<String> keys = redisCommands.keys("student:*");
    return keys.stream()
        .map(redisCommands::get)
        .map(this::mapToStudent)
        .collect(Collectors.toList());
  }

  @Override
  public Student findById(String id) {
    RedisCommands<String, String> redisCommands = context.getBean(RedisCommands.class);
    String studentData = redisCommands.get("student:" + id);
    return mapToStudent(studentData);  }

  @Override
  public Student save(Student student) {
    RedisCommands<String, String> redisCommands = context.getBean(RedisCommands.class);
    String id = student.getId();
    redisCommands.set("student:" + id, mapToString(student));
    return student;
  }

  @Override
  public void deleteById(String id) {
    RedisCommands<String, String> redisCommands = context.getBean(RedisCommands.class);
    redisCommands.del("student:" + id);
  }

  private Student mapToStudent(String studentData) {
    try {
      return new ObjectMapper().readValue(studentData, Student.class);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  private String mapToString(Student student) {
    try {
      return new ObjectMapper().writeValueAsString(student);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
