package service;

import domain.Student;

public interface StudentService {
    int addStudent(Student student);
    Student findStudentById(Integer id);
}
