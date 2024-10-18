package com.osadchiy.redisexample.service;

import com.osadchiy.redisexample.entity.Student;
import java.util.List;

public interface StudentService {

  List<Student> findAll();

  Student findById(String id);

  Student save(Student student);

  void deleteById(String id);
}
