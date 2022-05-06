package org.example.service.handle;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CheckArgumentAspect {

    @Around(value = "execution(* *..NumberServiceImpl.addNumber(..))")
    public Object myAround(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("===执行了环绕通知===");
        boolean isALL = true;
        //获取执行目标方法时的实参
        Object args[] = pjp.getArgs();
        for (Object arg : args) {
            if (arg == null) {
                //参数是空，不正确
                isALL = false;
                break;
            } else {
                Integer temp = (Integer) arg;
                if (temp.intValue() <= 0) {
                    isALL = false;
                    break;
                }
            }
        }
        //根据参数检查结果，决定是否执行目标方法
        Object res = null;
        if (isALL) {
            res = pjp.proceed();
        } else {
            res = -1;
        }
        return res;
    }


}
