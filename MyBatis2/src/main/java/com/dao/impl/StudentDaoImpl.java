package com.dao.impl;

import com.dao.StudentDao;
import com.domain.Student;
import com.utils.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class StudentDaoImpl implements StudentDao {
    public StudentDaoImpl() {
        super();
    }

    @Override
    public Student selectStudentById(Integer id) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        String sqlId = "com.dao.StudentDao.selectStudentById";
        Student student = sqlSession.selectOne(sqlId, id);
        sqlSession.close();
        return student;
    }

    @Override
    public List<Student> selectStudents() {
        SqlSession session = MyBatisUtil.getSqlSession();
        String sqlId = "com.dao.StudentDao" + "." + "selectStudents";
        List<Student> students = session.selectList(sqlId);
        session.close();
        return students;
    }

    @Override
    public int insertStudent(Student student) {
        SqlSession session = MyBatisUtil.getSqlSession();
        String sqlId = "com.dao.StudentDao" + "." + "insertStudent";
        int rows = session.insert(sqlId, student);
        session.commit();
        session.close();
        return rows;
    }
}
