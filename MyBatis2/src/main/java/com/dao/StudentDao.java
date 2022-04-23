package com.dao;

import com.domain.Student;

import java.util.List;

public interface StudentDao {
    Student selectStudentById(Integer id);
    List<Student> selectStudents();

    int  insertStudent(Student student);

}
