package service.Impl;

import dao.StudentDao;
import domain.Student;
import service.StudentService;

public class StudentServiceImpl implements StudentService {

    private StudentDao dao;



    @Override
    public int addStudent(Student student) {

        return dao.insertStudent(student);

    }

    @Override
    public Student findStudentById(Integer id) {
        return dao.selectById(id);

    }

    public void setDao(StudentDao dao) {
        this.dao = dao;
    }
}
