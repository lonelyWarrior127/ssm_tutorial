package dao;

import domain.Student;

public interface StudentDao {
    //查询一个学生
    Student selectStudentById(Integer id);
    //添加学生
    //返回值int:表示本次操作影响的数据库的行数
    int insertStudent(Student student);
}
