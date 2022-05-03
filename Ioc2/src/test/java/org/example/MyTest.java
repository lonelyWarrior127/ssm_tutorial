package org.example;

import org.example.domain.SysUser;
import org.example.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {

    @Test
    public void test01(){
        String config = "applicationContext.xml";
        ApplicationContext ctx = new ClassPathXmlApplicationContext(config);
        UserService userService =  (UserService) ctx.getBean("userService");
        SysUser user = new SysUser();
        user.setName("卓卓");
        user.setAge(18);
        userService.addUser(user);
    }
}
