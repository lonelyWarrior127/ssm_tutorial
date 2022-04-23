import com.domain.Student;
import com.utils.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class MyTest {
    @Test
    public void TestSelectById(){
        // 1. 获取SqlSession
        SqlSession session = MyBatisUtil.getSqlSession();
        // 2. 指定SqlId
        String sqlId = "com.dao.StudentDao"+"."+"selectStudentById";
        // 3. 执行SqlSession的方法，表示执行SQL语句
        Student student = session.selectOne(sqlId,1);
        System.out.println(student);
        // 4. 关闭SqlSession对象
        session.close();
    }

    @Test
    public void TestSelectStudents(){
        // 1. 获取SqlSession
        SqlSession session = MyBatisUtil.getSqlSession();
        // 2. 指定SqlId
        String sqlId = "com.dao.StudentDao"+"."+"selectStudents";
        // 3. 执行SqlSession的方法，表示执行SQL语句
        List<Student> students = session.selectList(sqlId);
        for(Student stu :students){
            System.out.println(stu);
        }

        // 4. 关闭SqlSession对象
        session.close();
    }

    @Test
    public void TestInsertStudent(){
        // 1. 获取SqlSession
        SqlSession session = MyBatisUtil.getSqlSession();
        // 2. 指定SqlId
        String sqlId = "com.dao.StudentDao"+"."+"insertStudent";
        // 3. 执行SqlSession的方法，表示执行SQL语句
        Student student = new Student();
        student.setId(6);
        student.setName("英英");
        student.setEmail("ying@qq.com");
        student.setAge(48);
        int rows = session.insert(sqlId,student);
        System.out.println(rows);
        session.commit();
        // 4. 关闭SqlSession对象
        session.close();
    }




}
