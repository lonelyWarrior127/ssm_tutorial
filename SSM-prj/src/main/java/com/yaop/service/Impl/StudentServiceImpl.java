package com.yaop.service.Impl;

import com.yaop.dao.StudentDao;
import com.yaop.domain.Student;
import com.yaop.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentDao dao;
    /*
        dao是引用类型。StudentDao类型的对象时在spring的配置文件中创建的对象
        引用类型自动注入 @Autowired @Resource
    * */
    @Override
    public int addStudent(Student student) {
        int rows = dao.insertStudent(student);
        return rows;
    }

    @Override
    public List<Student> queryStudents() {
        List<Student> list = dao.selecctStudents();
        return list;
    }
}
