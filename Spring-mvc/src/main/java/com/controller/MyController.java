package com.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vo.Student;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @Controller：创建控制器（处理器）对象 控制器：叫做后端控制器，自定义的类处理请求的
 * 位置：在类的上面，表示此类的对象，对象放在springmvc的容器中
 * @RequestMapping
 *      属性： value:表示所有请求地址的公共前缀，相当于是模块的名称
 *      位置： 在类的上面
 *              @RequestMapping(value = "/test")
 */
@Controller
public class MyController {
    //定义方法，处理请求 public void doGet(){}

    /**
     * springmvc框架，使用控制器类中的方法，处理请求
     * 方法的特点：
     * 1. 方法的形参，表示请求中的参数
     * 2. 方法的返回值，表示本次请求的处理结果
     *
     * @RequestMapping 请求映射
     * 属性：value 请求中的url的地址唯一值，以/开头
     * 位置： 1. 在方法的上面 2.在类定义的上面（可选）
     * 作用：把指定的请求，交给指定的方法处理，等同于url-pattern
     * <p>
     * ModelAndView：表示本次请求的处理结果（数据或视图）
     * Model :表示数据
     * View：表示视图
     */

    @RequestMapping(value = "/some.do",method = RequestMethod.GET)
    public ModelAndView doSome() { //相当于doGet()
        ModelAndView mv = new ModelAndView();
        mv.addObject("msg", "处理了some.do请求");
        mv.addObject("fun", "执行了doSome方法");
        mv.setViewName("show");
        return mv;
    }

    @RequestMapping(value = "/other.do",method = RequestMethod.POST)
    public ModelAndView doOther() { //相当于doGet()

        ModelAndView mv = new ModelAndView();
        mv.addObject("msg", "处理了other.do请求");
        mv.addObject("fun", "执行了doOther方法");
        mv.setViewName("show");
        return mv;
    }

    /**
     * 逐个接收请求参数
     * 要求：请求中的参数名和控制器方法的形参名,按照名称对应接收请求参数
     * 参数接收：
     *  1.框架使用request对象，接收参数
     *    String name =request.getParameter("name");
     *    String     age =request.getParameter("age");
     *  2. 在中央调度器的内部调用doPropertyParam方法时，按名称对象传递参数
     *      doPropertyParam(name,Integer.valueof(age));
     *      框架可以实现请求参数String到int long float等类型的转换
     */
    @RequestMapping(value = "/received-property.do")
    public ModelAndView doPropertyParam(String name, Integer age){
        System.out.println("执行doPropertyParam");
        ModelAndView mv = new ModelAndView();
        mv.addObject("myname",name);
        mv.addObject("myage",age);
        mv.setViewName("property");
        return mv;
    }
    @RequestMapping(value = "/string-return.do")
    public String doStringView(HttpServletRequest request, String name, Integer age){
        //处理数据
        request.setAttribute("myname",name);
        request.setAttribute("myage",age);
        //返回结果，forward转发到show.jsp
        return "show";
    }

    /**
     * 控制器方法返回void，响应ajax请求，使用HttpServletResponse输出结果
     */
    @RequestMapping("/return-void-ajax.do")
    public void returnVoidAjax(HttpServletResponse response, String name, Integer age) throws IOException {
        System.out.println("处理void返回类型，name= "+name+ ",age="+age);
        //调用service得到对象
        Student student = new Student();
        student.setName(name);
        student.setAge(age);
        //把对象转为json
        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(student);
        System.out.println("服务器端对象转为json==="+json);
        //输出json，响应ajax
        response.setContentType("application/json;charset=utf-8");
        PrintWriter pw = response.getWriter();
        pw.println(json);
        pw.flush();
        pw.close();
    }

    @RequestMapping("/receive-object.do")
    public ModelAndView doReceiveObject(Student student){
        ModelAndView mv = new ModelAndView();
        mv.addObject("myname",student.getName());
        mv.addObject("myage",student.getAge());
        mv.setViewName("show");
        return mv;
    }


    /**
     * 控制器方法返回Student--json
     */

    @RequestMapping("/doStudentJson.do")
    @ResponseBody
    public Student doAjaxJson(String name,Integer age){
        Student student = new Student();
        student.setName(name);
        student.setAge(age);
        return student;
    }

    /**
     * 控制器方法返回List<Student> --json array
     */

    @RequestMapping("/doStudentJsonArray.do")
    @ResponseBody
    public List<Student> doAjaxJsonArray(String name, Integer age){

        Student student1 = new Student();
        student1.setName("张三");
        student1.setAge(18);

        Student student = new Student();
        student.setName("卓卓");
        student.setAge(20);
        List<Student> stus = new ArrayList<>();
        stus.add(student1);
        stus.add(student);
        return stus;
    }

    /**
     * 控制器方法返回String--数据
     * 区分返回值字符串String是数据还是视图
     *  1. 方法上面有@ResponseBody注解就是数据
     *  2. 方法上面有无@ResponseBody就是视图
     */
    @RequestMapping("/doStringData.do")
    @ResponseBody
    public String doStringData(String name,Integer age){
        return "hello SpringMVC注解式开发";
    }






}
