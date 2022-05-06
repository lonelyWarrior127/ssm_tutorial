package org.example.handle;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

//@Aspect
//public class MyAspect {
//    /*
//    异常通知方法的定义
//      1. public 2. void 3. 方法名称自定义 4. 有参数是Execption
//     */
//
//    /**
//     * @AfterThrowing：异常通知 属性 value 切入点表达式
//     * throwing 自定义变量，表示目标方法抛出的异常
//     * 变量名必须和通知方法的形参名一样
//     * 位置：方法上面
//     * 特点：
//     * 1. 在目标方法抛出异常后执行的，没有异常不执行
//     * 2. 能够获取目标方案的异常信息
//     * 3. 不是异常处理程序，可以得到异常发生的通知，可以发送邮件、短信通知开发人员
//     * 看做目标方法的监控程序
//     * 异常通知的执行
//     * <p>
//     * try{
//     * SomeServiceImpl.doSecond(...)
//     * }catch(Exection e){
//     * myAfterThrowing(Exception ex)
//     * }
//     */
//    @AfterThrowing(value = "execution(* *..SomeServiceImpl.doFirst(..))", throwing = "ex")
//    public void myAfterThrowing(Exception ex) {
//        System.out.println("异常通知，在目标方法抛出异常时执行，异常原因是：" + ex.getMessage());
//        /*
//            异常发生可以做：
//                1.记录异常的时间，位置等信息
//                2.发送邮件、短信通知开发人员
//         */
//    }
//
//}

@Aspect
public class MyAspect {
    //异常通知方法的定义
    // 1. public 2. void 3. 方法名称自定义 4. 没有形参
    /*
    @After：最终通知
            属性：value 切入点表达式
            位置：在方法的上面
    特点：
        1. 在目标方法之后执行
        2. 总是会被执行,抛出异常后也会执行
        3. 可以用来做程序的收尾工作，例如清除临时数据、变量，清理内存
     try{
        SomeServiceImpl.doSecond(...)
     }catch(Exection e){
            myAfterThrowing(Exception ex)
     }finally{
        myAfter()
     }

     */
    @After(value = "execution(* *..SomeServiceImpl.doThird(..))")
    public void myAfter(){
        System.out.println("最终通知，总是会被执行的");
    }


    /*
        @Pointcut：定义和管理切入点，不是通知注解
                属性：value 切入点表达式
                位置：在自定义方法的上面，这个方法看做是切入点表达式的别名
                     其他的通知注解中，可以使用方法名称，就表示使用这个切入点表达式了

     */
    @Pointcut(value = "execution(* *..SomeServiceImpl.doThird(..))")
    private void mypt(){
        //无需代码
    }

    @After(value = "mypt()")
    public void myAfter1(){
        System.out.println("最终通知，总是会被执行的");
    }


}
