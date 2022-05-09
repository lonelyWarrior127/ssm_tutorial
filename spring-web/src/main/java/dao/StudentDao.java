package dao;

import domain.Student;
import org.apache.ibatis.annotations.Param;

public interface StudentDao {
    int insertStudent(Student student);
    Student selectById(@Param("studentId") Integer id);
}
