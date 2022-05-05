import com.yaop.service.SomeService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import proxy.ServiceProxy;

public class MyTest {

    @Test
    public void  test01(){
        String config = "applicationContext.xml";
        ApplicationContext ctx = new ClassPathXmlApplicationContext(config);
        SomeService someService = (SomeService) ctx.getBean("someService");
        //someService==com.sun.proxy.$Proxy7
        //加入代理的处理： 1）目标方法执行时，有切面功能 2）someService对象是改变后的代理对象com.sun.proxy.$Proxy7
        System.out.println("someService==" + someService.getClass().getName());
        //代理对象，调用方法，才有切面的功能增强
        someService.doSome("huihui",10);
    }

    @Test
    public void test02(){
        ServiceProxy service = new ServiceProxy();
        service.doSome("huizhuo",28);
    }

    @Test
    public void  test03(){
        String config = "applicationContext.xml";
        ApplicationContext ctx = new ClassPathXmlApplicationContext(config);
        SomeService someService = (SomeService) ctx.getBean("someService");
        someService.doSome("huihui",10);
    }
}
