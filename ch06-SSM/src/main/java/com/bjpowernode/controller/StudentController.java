package com.bjpowernode.controller;

import com.bjpowernode.domain.Student;
import com.bjpowernode.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 */
@Controller
@RequestMapping("/student")
public class StudentController {

    /**
       声明引用类型， 调用引用类型的业务方法
       引用类型自动注入  @Autowired, @Resource
     */
    @Resource
    private StudentService service;

    //添加学生
    @RequestMapping("/addStudent.do")
    public ModelAndView addStudent(Student student){
        ModelAndView mv  = new ModelAndView();

        //调用service，处理业务逻辑，把处理结果返回给用户
        int rows  = service.addStudent( student);

        String msg="注册失败！！！";
        if(rows > 0 ){
            //注册成功， 给用户成功的数据和视图
            msg = "注册成功的";
        }
        mv.addObject("msg", student.getName()+"," + msg);
        mv.setViewName("result");

        return mv;
    }


    //浏览学生
    @RequestMapping("/queryStudent.do")
    @ResponseBody
    public List<Student> queryStudents(){
        List<Student> students = service.queryStudents();
        return students;
    }
}
