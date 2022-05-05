package proxy;

import com.yaop.service.SomeService;
import com.yaop.service.impl.SomeServiceImpl;
import handle.MyAspect;


//应用aspectJ框架实现AOP时，框架代生成如下代码
public class ServiceProxy implements SomeService {

    //真正的目标
    MyAspect aspect = new MyAspect();
    SomeService target = new SomeServiceImpl();
    @Override
    public void doSome(String name, Integer age) {
        aspect.myBefore();
        target.doSome("hui",10);

    }
}
