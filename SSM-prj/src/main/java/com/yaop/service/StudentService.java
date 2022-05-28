package com.yaop.service;

import com.yaop.domain.Student;

import java.util.List;

public interface StudentService {
    int addStudent(Student student);
    List<Student> queryStudents();
}
