package com.yaop;

import com.yaop.dao.StudentDao;
import com.yaop.domain.Student;
import com.yaop.service.StudentService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class MyTest {

    @Test
    public void test01(){
        String config = "applicationContext.xml";
        ApplicationContext ctx = new ClassPathXmlApplicationContext(config);
        String names[] = ctx.getBeanDefinitionNames();
        for(String name: names){
            System.out.println("容器中对象的名称="+name+"=====对象类型="+ctx.getBean(name));
        }
    }

    @Test
    public void test02(){
        String config = "applicationContext.xml";
        ApplicationContext ctx = new ClassPathXmlApplicationContext(config);
        StudentDao dao = (StudentDao) ctx.getBean("studentDao");
        Student stu = new Student();
        stu.setId(1);
        stu.setName("huihui");
        stu.setAge(10);
        dao.insertStudent(stu);
    }

    @Test
    public void test03(){
        String config = "applicationContext.xml";
        ApplicationContext ctx = new ClassPathXmlApplicationContext(config);
        StudentService service = (StudentService) ctx.getBean("studentService");
//        Student stu = new Student();
//        stu.setId(3);
//        stu.setName("yangyang");
//        stu.setAge(48);
//        service.addStudent(stu);
        List<Student> stus = service.queryStudent();
        for(Student s : stus){
            System.out.println(s);

        }
    }


}
