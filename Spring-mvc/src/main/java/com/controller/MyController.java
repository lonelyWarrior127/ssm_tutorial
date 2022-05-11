package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Controller：创建控制器（处理器）对象
 *          控制器：叫做后端控制器，自定义的类处理请求的
 *          位置：在类的上面，表示此类的对象，对象放在springmvc的容器中
 */
@Controller
public class MyController {
    //定义方法，处理请求 public void doGet(){}

    /**
     * springmvc框架，使用控制器类中的方法，处理请求
     * 方法的特点：
     *          1. 方法的形参，表示请求中的参数
     *          2. 方法的返回值，表示本次请求的处理结果
     * @RequestMapping 请求映射
     *          属性：value 请求中的url的地址唯一值，以/开头
     *          位置： 1. 在方法的上面 2.在类定义的上面（可选）
     *          作用：把指定的请求，交给指定的方法处理，等同于url-pattern
     *
     * ModelAndView：表示本次请求的处理结果（数据或视图）
     *          Model :表示数据
     *          View：表示视图
     */

    @RequestMapping(value = {"/some.do","/first.do"})
    public ModelAndView doSome(){ //相当于doGet()
        //使用这个方法处理请求。能处理请求的方法叫做控制器方法
        // 调用service对象，处理请求，返回数据
        ModelAndView mv = new ModelAndView();
        // 添加数据
        mv.addObject("msg","处理了some.do请求");
        mv.addObject("fun","执行了doSome方法");
        //指定视图   setViewName("视图的完整路径")
        //mv.setViewName("/WEB-INF/view/show.jsp");
        //当前配置了视图解析器，可使用文件名称作为视图名使用-视图逻辑名称
        mv.setViewName("show");
        //使用了逻辑名称，框架使用配置文件中视图解析器的前缀和后缀，拼接为完整的视图路径

        //返回结果
        return mv;
    }
    /**
     * 当框架调用完doSome方法后，得到返回的ModelAndView。
     * 框架会在后续的处理逻辑值，处理mv对象里面的数据和视图
     * 对数据执行request.setAttribute("msg","处理了some.do请求");把数据放入到request作用域
     * 对视图执行forward转发操作，等同于 request.getRequestDispather("/show.jsp").forward(..);
     */



    @RequestMapping(value = {"/other.do","/test/second.do"})
    public ModelAndView doOther(){ //相当于doGet()

        ModelAndView mv = new ModelAndView();
        mv.addObject("msg","处理了other.do请求");
        mv.addObject("fun","执行了doOther方法");
        mv.setViewName("show");
        return mv;
    }
}
