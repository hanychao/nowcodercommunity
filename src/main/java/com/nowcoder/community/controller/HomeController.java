package com.nowcoder.community.controller;

import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.Page;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.DiscussPostService;
import com.nowcoder.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hyc
 * @create 2022-05-21 14:55
 */
@Controller
public class HomeController {
    @Autowired(required = false)
    private DiscussPostService discussPostService;
    @Autowired(required = false)
    private UserService userService;

    @RequestMapping(path = "/index", method = RequestMethod.GET)
    public String getIndexPage(Model model, Page page) {
        //方法调用前，Spring会自动实例化Model和Page，并且将Page注入到Model中；所以在thymeleaf助攻可以直接访问Page对象中的数据
        //DispatcherServlet会帮我们将请求中传进来的current limit等数据注入到page中
        page.setRows(discussPostService.findDiscussPostRows(0));
        page.setPath("/index");//这个路径时跟RequestMapping中的path一样，而并非模板引擎的那个index
        List<DiscussPost> list = discussPostService.findDiscussPosts(0, page.getOffset(), page.getLimit());
        List<Map<String,Object>> discussPosts = new ArrayList<>();
        if (list != null) {
            for (DiscussPost post : list) {
                Map<String,Object> map = new HashMap<>();
                User user = userService.findUserById(post.getUserId());
                map.put("user",user);
                map.put("post",post);
                discussPosts.add(map);
            }
        }
        model.addAttribute("discussPosts",discussPosts);
        return "/index";//模板引擎的文件位置
    }

}
