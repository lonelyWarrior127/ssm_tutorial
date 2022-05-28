package com.yaop.controller;

import com.yaop.domain.Student;
import com.yaop.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {
    /*
     * 声明引用类型，调用业务方法
     * */
    @Resource
    private StudentService service;

    //添加学生
    @RequestMapping("/addStudent.do")
    public ModelAndView addStudent(Student student) {
        ModelAndView mv = new ModelAndView();
        //调用service，处理业务逻辑，把处理结果返回给用户
        int rows = service.addStudent(student);
        String msg = "注册失败！";
        System.out.println("注册用户");
        if (rows > 0) {
            //注册成功，给用户成功地数据和视图
            msg = "注册成功";
        }
        mv.addObject("msg", student.getName() + "," + msg);
        mv.setViewName("result");
        return mv;
    }

    /*
    * 浏览学生
    * @responseBody注解的作用是将controller的方法返回的对象通过适当的转换器转换为指定的格式之后，写入到response对象的body区，通常用来返回JSON数据或者是XML数据。
    * */
    @RequestMapping("/queryStudent.do")
    @ResponseBody
    public List<Student> queryStudents(){
        List<Student> students = service.queryStudents();
        return students;


    }
}
