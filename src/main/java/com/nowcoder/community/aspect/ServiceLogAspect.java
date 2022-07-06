package com.nowcoder.community.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 方面组件中需要声明的两个东西：
 * 1、切点---在哪些目标对象的哪些位置织入Aspect组件代码。
 * 2、通知---实现具体的系统服务逻辑，具体要做什么
 * ，以及要做的位置在哪里，比如现在知道要把代码织入到成员方法里
 * ，那么是要织入到方法的开头、还是结束位置、还是有返回值的地方、还是抛异常的地方、还是方法的前后。
 */
@Component
@Aspect
public class ServiceLogAspect {
    private static final Logger logger = LoggerFactory.getLogger(ServiceLogAspect.class);

    //声明切点
    //这里的切点匹配了所有业务组件的所有方法
    @Pointcut("execution(* com.nowcoder.community.service.*.*(..))") //固定关键字(方法返回值 包名.bean.方法(参数)) ， *代表所有  ..代表所有的参数
    public void pointcut() {

    }

    @Before("pointcut()")
    public void before(JoinPoint joinPoint) {// 参数：连接点,指代程序织入的目标
        // 用户[1.2.3.4],在[xxx],访问了[com.nowcoder.community.service.xxx()].
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return;
        }
        HttpServletRequest request = attributes.getRequest();
        String ip = request.getRemoteHost();
        String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        // 得到该连接点的 类名 和 方法名
        String target = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        logger.info(String.format("用户[%s],在[%s],访问了[%s].", ip, now, target));
    }
}
