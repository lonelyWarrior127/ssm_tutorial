package org.example.handle;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.util.Date;

@Aspect
public class MyAspect {


    //定义方法，表示切面的具体功能
    /*环绕通知方法的定义
        1)方法是public
        2)方法必须优返回值，推荐使用Object类型
        3)方法名称自定义
        4)方法必须有ProceedingJoinPoint参数
    * */

    /**
     * @Around：环绕通知 属性：value 切入点表达式
     * 位置：在方法定义的上面
     * <p>
     * 返回值： Object，表示调用目标方法希望得到的执行结果（不一定是目标方法自己的返回值）
     * 参数：ProceedingJoinPoint，相当于反射中的Method，作用：执行目标方法是等于Method.invoke()
     * 特点：
     * 1. 在目标方法的迁和后都能增强功能
     * 2. 控制目标是否执行
     * 3. 修改目标方法的执行结果。
     */
    @Around(value = "execution(* *..SomeServiceImpl.doFirst(..))")
    public Object myAround(ProceedingJoinPoint pjp) throws Throwable {
        //获取方法执行时的参数值
        String name = ""
        Object args[] = pjp.getArgs();
        if (args != null && args.length > 0) {
            Object arg = args[0];
            if (arg != null) {
                name = (String) arg;
            }

        }
        Object methodReturn = null;
        System.out.println("执行环绕通知，在目标方法之前输出日志事件==" + new Date());
        //执行目标方法，ProceedingJoinPoint,表示doFirst
        if ("huihui".equals(name)) {
            methodReturn = pjp.proceed();//method.invoke() 表示执行doFirst方法本身

        }
        if (methodReturn != null) {
            methodReturn = "环绕通知中，修改目标方法原来的执行结果";
        }
        System.out.println("环绕通知，在目标方法之后，增加了事务提交功能" + new Date());
        //return "Hello Around，不是目标方法的执行结果";

        return methodReturn;

    }


}
