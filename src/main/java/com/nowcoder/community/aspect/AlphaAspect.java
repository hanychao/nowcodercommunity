package com.nowcoder.community.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * 方面组件中需要声明的两个东西：
 * 1、切点---在哪些目标对象的哪些位置织入Aspect组件代码。
 * 2、通知---实现具体的系统服务逻辑，具体要做什么
 * ，以及要做的位置在哪里，比如现在知道要把代码织入到成员方法里
 * ，那么是要织入到方法的开头、还是结束位置、还是有返回值的地方、还是抛异常的地方、还是方法的前后。
 */
//@Component
//@Aspect
public class AlphaAspect {

    //声明切点
    //这里的切点匹配了所有业务组件的所有方法
    @Pointcut("execution(* com.nowcoder.community.service.*.*(..))") //固定关键字(方法返回值 包名.bean.方法(参数)) ， *代表所有  ..代表所有的参数
    public void pointcut() {

    }

    @Before("pointcut()") //表示以 pointcut() 为切点
    public void before() {
        System.out.println("before");
    }

    @After("pointcut()")
    public void after() {
        System.out.println("after");
    }

    @AfterReturning("pointcut()")
    public void afterReturning() {
        System.out.println("afterReturning");
    }

    @AfterThrowing("pointcut()")
    public void afterThrowing() {
        System.out.println("afterThrowing");
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {// 参数：连接点
        System.out.println("aroundBefore");
        Object obj = proceedingJoinPoint.proceed();// 连接点调用目标组件的方法，返回目标组件的返回值
        System.out.println("aroundAfter");
        return obj;
    }

}
