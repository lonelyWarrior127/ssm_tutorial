package org.example;

import org.example.service.NumberService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {

    @Test
    public void test01(){
        String config = "applicationContext.xml";
        ApplicationContext ctx = new ClassPathXmlApplicationContext(config);
        NumberService service = (NumberService) ctx.getBean("service");
        Integer ret = service.addNumber(0,2,3);
        System.out.println(ret);

    }
}
