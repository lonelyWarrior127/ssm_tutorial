import domain.Student;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class MyTest {

    //测试mybatis执行sql语句
//    @Test
//    public void testSelectStudentById() throws IOException {
//        //调用MyBatis某个对象的方法，执行mapper文件的SQL语句
//        //Mybatis核心类， SqlSessionFactory
//
//        // 1. 定义mybatis主配置文件的位置，从类路径开始的相对路径
//        String config = "mybatis.xml";
//        // 2. 读取主配置文件，使用mybatis框架中的Resource类
//        InputStream inputStream = Resources.getResourceAsStream(config);
//        // 3. 创建SqlSessionFactory对象，使用SqlSessionFactoryBuilder类的build方法
//        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
//        //4. 获取SqlSession对象
//        SqlSession session = sqlSessionFactory.openSession();
//        //5. 指定要执行SQL语句的id
//        // sql的id = namespace + "." + <select>|update|insert|delete标签的id属性值
//        String sqlId = "dao.StudentDao"+"."+"selectStudentById";
//        //6. 通过SqlSession的方法，执行SQL语句
//        Student  student = session.selectOne(sqlId,1);
//        System.out.println(student);
//        //7. 关闭SqlSession对象
//        session.close();
//    }

    @Test
    public void testinsertStudentById() throws IOException {

        String config = "mybatis.xml";
        InputStream inputStream = Resources.getResourceAsStream(config);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();

        String sqlId = "dao.StudentDao"+"."+"insertStudent";
        Student student = new Student();
        student.setId(4);
        student.setName("洋洋");
        student.setEmail("yang@qq.com");
        student.setAge(48);
        int rows = session.insert(sqlId,student);
        System.out.println("添加一个学生： "+rows);
        //mybatis默认执行sql语句是手工提交事务模式，在做inser,update,delete后需要提交事务。
        session.commit();

        session.close();
    }


}
