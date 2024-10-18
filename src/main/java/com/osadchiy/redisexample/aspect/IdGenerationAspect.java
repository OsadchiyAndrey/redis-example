package com.osadchiy.redisexample.aspect;

import com.osadchiy.redisexample.entity.Student;
import lombok.AllArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Aspect
@Component
@AllArgsConstructor
public class IdGenerationAspect {

  @Around("execution(* com.osadchiy.redisexample.service.StudentService.save(..)) && args(student)")
  public Object generateId(ProceedingJoinPoint joinPoint, Student student) throws Throwable {
    student.setId(UUID.randomUUID().toString());

    return joinPoint.proceed(new Object[]{student});
  }

}
