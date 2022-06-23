package com.nowcoder.community.controller.advice;


import com.nowcoder.community.util.CommunityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
/**
 * 虽然在springboot中的templates下的error包中放404和500页面之后，当服务器出错就会自动连接到相应的404或者500页面，
 * 但是对于500的错误，我们还需要记录相应的日志，
 * 同时，对于异步请求出错时，我们不是返回页面，而是返回json,所以我们还需要通过对请求类型的判断来决定向浏览器返回什么东西
 */

/**
 *  加了注解@ControllerAdvice表示是Controller全局配置类，不用对任何Controller再做配置，可以统一做Controller的全局配置。@ControllerAdvice用来修饰类。
 *  异常处理方案@ExceptionHandler、绑定数据方案@ModelAttribute、绑定参数方案@DataBinder. 他们都用来修饰方法。
 *  这里只演示，统一处理异常（@ExceptionHandler）
 */
@ControllerAdvice(annotations = Controller.class)//// 限定注解@Controller,表示只扫描所有的Controller,否则组件扫描所有的bean
public class ExceptionAdvice {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);

    @ExceptionHandler({Exception.class})  // 处理哪些异常？Exception是所有异常的父类,所有异常都处理
    // 有异常controller会传过来Exception
    public void handleException(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //记录日志
        logger.error("服务器发生异常：" + e.getMessage());//异常的概括
        for (StackTraceElement element : e.getStackTrace()) {//把异常所有栈的信息都记录下来
            logger.error(element.toString());
        }

        //根据请求的类型来对浏览器做出不同相应
        String xRequestedWith = request.getHeader("x-requested-with");
        if("XMLHttpRequest".equals(xRequestedWith)){// 异步请求
            response.setContentType("application/plain;charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(CommunityUtil.getJSONString(1,"服务器异常!"));// 输出JSON字符串
        }else{// 请求html，重定向到错误页面
            response.sendRedirect(request.getContextPath() + "/error");//request.getContextPath()获取项目的访问路径 即 /community
        }
    }
}
