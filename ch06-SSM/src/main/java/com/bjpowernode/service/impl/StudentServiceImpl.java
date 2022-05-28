package com.bjpowernode.service.impl;

import com.bjpowernode.dao.StudentDao;
import com.bjpowernode.domain.Student;
import com.bjpowernode.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  <bean>
 *  @Service
 *
 */
@Service
public class StudentServiceImpl implements StudentService {
    /**
     * dao 是引用类型。 StudentDao类型的对象是在spring的配置文件中创建的对象
     * 引用类型自动注入  @Autowired, @Resource
     */
    @Autowired
    private StudentDao dao;

    @Override
    public int addStudent(Student student) {
        int rows = dao.insertStudent(student);
        return rows;
    }

    @Override
    public List<Student> queryStudents() {
        List<Student> list = dao.selectStudents();

        return list;
    }
}
