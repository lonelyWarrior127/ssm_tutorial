package org.example;

import org.example.handle.MyAspect;
import org.example.service.SomeService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {

    @Test
    public  void  test01(){
        String config = "applicationContext.xml";
        ApplicationContext ctx = new ClassPathXmlApplicationContext(config);
        SomeService someService = (SomeService) ctx.getBean("someService");
        String ret = someService.doFirst("hello");
        //String ret =myAspect.myAround();
        System.out.println("ret调用目标方法的结果=="+ret);

    }

    @Test
    public  void  test02(){
        MyHandler handler = new MyHandler();
        String ret = handler.doFirst();
        System.out.println("ret调用目标方法的结果=="+ret);

    }
}
