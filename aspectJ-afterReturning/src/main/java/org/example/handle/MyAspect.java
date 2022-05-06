package org.example.handle;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class MyAspect {
    // 定义方法，表示 切面的具体功能
    /**后置通知方法的定义
     * 1) public
     * 2) void
     * 3) 方法名称自定义
     * 4）方法可以有参数，推荐使用Object类型
     * **/

    /**
     * @AfterReturnning：后置通知 属性：value 切入点表达式
     * returning 自定义的变量，表示目标方法的返回值
     * 自定义变量名称必须和通知方法的形参名一样
     * 位置：在方法的上面
     * 特点：
     * 1. 在目标方法之后，执行的
     * 2. 能获取到目标方法的执行结果
     * 3. 慧影响目标方法的执行
     * 方法的参数：
     * Object res: 表示目标方法的返回值，使用res接收doOther的调用结果。
     * Object res = doOther();
     * 后置通知的执行顺序
     * Object res = SomeServiceImpl.doOther(..);
     * myAfterReturning(res);
     * JoinPoint必须是第一个参数
     * 思考：
     * 1. doOther方法返回是String,Integer,Long等基本类型，在后置通知中，修改返回值，是不会影响目标方法的最后调用结果的
     * 2. doOther返回的结果是对象类型，例如Student，在后台通知方法中，修改这个Student对象的属性值会不会影响最后调用结果？
     **/
    @AfterReturning(value = "execution(* *..SomeServiceImpl.do*(..))", returning = "res")
    public void myAfterReturnning(JoinPoint jp, Object res) {
        if (res != null) {
            res = "Hello AspectJ";
        }
        System.out.println("后置通知，在目标方法之后执行，能拿到执行结果：" + res);
        // Object res有什么用
        if ("abcd".equals(res)) {
            System.out.println("根据返回值的不同，做不同的增强功能");
        } else if ("add".equals(res)) {
            System.out.println("doOther做了添加数据库，我做了备份数据");

        }

    }


}
