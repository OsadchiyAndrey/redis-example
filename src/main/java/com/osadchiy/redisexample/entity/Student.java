package com.osadchiy.redisexample.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Student {

  public enum Gender {
    MALE, FEMALE
  }

  private String id;
  private String name;
  private Gender gender;
  private Integer grade;
}
