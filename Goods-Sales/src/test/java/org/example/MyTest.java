package org.example;

import org.example.service.BuyGoodsService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {

    @Test
    public void  test01(){
        String config = "applicationContext.xml";
        ApplicationContext ctx = new ClassPathXmlApplicationContext(config);
        BuyGoodsService service=(BuyGoodsService) ctx.getBean("buyService");
        //System.out.println("service==" + service.getClass().getName());
        //service==com.sun.proxy.$Proxy17
        service.buy(1002,2);

    }
}
