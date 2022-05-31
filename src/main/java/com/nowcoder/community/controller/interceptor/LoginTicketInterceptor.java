package com.nowcoder.community.controller.interceptor;

import com.nowcoder.community.entity.LoginTicket;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.UserService;
import com.nowcoder.community.util.CookieUtil;
import com.nowcoder.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author hyc
 * @create 2022-05-31 20:35
 */
@Component
public class LoginTicketInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;
    @Autowired
    private HostHolder hostHolder;

    //在Controller之前执行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /**
         * 从浏览器请求中获取到发给用户的登录凭证
         * （由于在给用户发送登录凭证之后，紧接着就会有一个重定向到首页的操作
         * ，这个重定向就是让浏览器发出一个请求，所以用户收到登录凭证后，这里立马就会收到一个请求）
         */
        String ticket = CookieUtil.getValue(request, "ticket");//在随着请求一起发过来的cookie中拿到该用户的登录凭证
        if (ticket != null) {
            LoginTicket loginTicket = userService.findLoginTicket(ticket);
            if (loginTicket != null && loginTicket.getStatus() == 0 && loginTicket.getExpired().after(new Date())) {
                User user = userService.findUserById(loginTicket.getUserId());
                hostHolder.setUsers(user);
            }
        }
        return true;
    }

    //在Controller之后、模板引擎之前执行
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        User user = hostHolder.getUser();
        if (user != null && modelAndView != null) {
            /**
             * 往模板引擎中添加当前登录用户
             * index的显示就是根据modelAndView里面是否有当前登录用户对象来决定怎么显示首页
             */
            modelAndView.addObject("loginUser",user);
        }
    }

    //在模板引擎之后执行
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        hostHolder.clear();
    }
}
