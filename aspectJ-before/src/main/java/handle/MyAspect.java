package handle;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import java.util.Date;

/*
* @Aspect：切面类的注解
*   位置：放在某个类的上面
*   作用：表示当前类时一个切面类。
*
*   切面类：表示切面功能的类
* */
@Aspect
public class MyAspect {
    //定义方法：表示切面的具体功能
    /*
    * 前置通知方法的定义
    *  1)方法是spring
    *  2)方法是void
    *  3）方法名称自定义
    *  4）方法可以有参数，如果有是JoinPoint,也可以没有
    * */

    /**
     * @Before ：前置通知
     *          属性：value 切入点表达式，表示切面的执行位置。在这个方法执行时会同时执行切面的功能
     *          位置：在方法的上面
     *          特点： 1）执行时间：在目标方法之前执行
     *                2）不会影响目标方法的执行
     *                3）不会修改目标方法的执行结果
     * **/
    @Before(value = "execution(public void com.yaop.service.impl.SomeServiceImpl.doSome(String,Integer))")
    public void myBefore(){
        //切面代码
        System.out.println("前置通知：切面的功能，在目标方法之前先执行: " + new Date());
    }

    /**
     * 切面类中的通知方法，可以有参数
     * JoinPoint必须是它
     * JoinPoint：表示正在执行的业务方法，想到雨反射中的Method
     *          使用要求：必须是参数列表的第一个
     *          作用：获取方法执行时的信息，例如方法的名称和参数集合
     * **/

    @Before(value = "execution(public void com.yaop.service.impl.SomeServiceImpl.doSome(String,Integer))")
    public void myBefore2(JoinPoint jp){
        // 获取方法的定义
        System.out.println("前置通知中，获取目标方法的定义："+ jp.getSignature());
        System.out.println("前置通知中，获取方法的名称：" + jp.getSignature().getName());

        //切面代码
        System.out.println("前置通知：切面的功能，在目标方法之前先执行: " + new Date());
    }
}
