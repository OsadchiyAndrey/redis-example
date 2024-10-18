package com.osadchiy.redisexample.controller;

import com.osadchiy.redisexample.entity.Student;
import com.osadchiy.redisexample.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("student")
public class StudentController {

  StudentService studentService;

  @GetMapping
  @Cacheable("student-search")
  public ResponseEntity<List<Student>> findAll() {
      return ResponseEntity.ok(studentService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Student> findAll(@PathVariable String id) {
    return ResponseEntity.ok(studentService.findById(id));
  }

  @PostMapping
  public ResponseEntity<Student> save(@RequestBody Student student) {
    return ResponseEntity.ok(studentService.save(student));
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable String id) {
    studentService.deleteById(id);
  }
}
