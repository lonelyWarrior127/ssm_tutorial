package com.controller;

import com.exception.AgeException;
import com.exception.MyUserException;
import com.exception.NameException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Controller：创建控制器（处理器）对象 控制器：叫做后端控制器，自定义的类处理请求的
 * 位置：在类的上面，表示此类的对象，对象放在springmvc的容器中
 * @RequestMapping 属性： value:表示所有请求地址的公共前缀，相当于是模块的名称
 * 位置： 在类的上面
 * @RequestMapping(value = "/test")
 */
@Controller
public class MyController {
    //定义方法，处理请求 public void doGet(){}


    @RequestMapping(value = "/some.do", method = RequestMethod.POST)
    public ModelAndView doSome(String name, Integer age) throws MyUserException { //相当于doGet()
        ModelAndView mv = new ModelAndView();
        if(!"zs".equals(name)){
            throw new NameException("姓名不准确");
        }

        if(age == null || age.intValue()>80){
            throw  new AgeException("年龄超过80了");
        }
        mv.addObject("myname", name);
        mv.addObject("myage", age);
        mv.setViewName("show");
        return mv;
    }

}