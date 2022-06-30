package com.nowcoder.community.controller;

import com.nowcoder.community.annotation.LoginRequired;
import com.nowcoder.community.dao.UserMapper;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.FollowService;
import com.nowcoder.community.service.LikeService;
import com.nowcoder.community.service.UserService;
import com.nowcoder.community.util.CommunityConstant;
import com.nowcoder.community.util.CommunityUtil;
import com.nowcoder.community.util.HostHolder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

/**
 * @author hyc
 * @create 2022-05-31 22:08
 */
@Controller
@RequestMapping("/user")
public class UserController implements CommunityConstant {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Value("${community.path.domain}")
    private String domain; // http://localhost:8080

    @Value("${server.servlet.context-path}")
    private String contextPath; // /community

    @Value("${community.path.upload}") // community.path.upload = F:/FakeNowCoderCommunity/data/upload
    private String uploadPath;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private UserService userService;

    @LoginRequired
    @RequestMapping(path = "/setting", method = RequestMethod.GET)
    public String getSettingPage() {
        return "/site/setting";
    }

    @LoginRequired
    @RequestMapping(path = "/upload", method = RequestMethod.POST)
    public String uploadHeader(Model model, MultipartFile headerImg) throws Exception {
        if (headerImg == null) {
            model.addAttribute("error", "您还未选择图片！");
            return "/site/setting";
        }
        /**
         * 不能用上传文件的原始文件名，因为可能很多用户上传的文件名相同，所以我们需要对接收的文件重新统一命名，否则在硬盘上存放很可能会产生冲突
         *  可能很多人传过来的图片名字都叫1.png
         */
        String fileName = headerImg.getOriginalFilename();//得到上传文件的原始文件名
        String suffix = fileName.substring(fileName.lastIndexOf("."));//截取文件后缀名
        if (StringUtils.isBlank(suffix)) {
            model.addAttribute("error", "文件格式不正确！");
            return "/site/setting";
        }
        //给文件生成新名字
        fileName = CommunityUtil.generateUUID() + suffix;
        //确定文件的存放位置  将文件存放到uploadPath这个路径下，文件名字就叫fileName对应的名字
        File dest = new File(uploadPath + "/" + fileName);//还未写入内容，暂时为空
        try {
            //将当前文件headerImg写入到目标文件dest中
            headerImg.transferTo(dest);

        } catch (IOException e) {
            logger.error("上传文件失败：" + e.getMessage());
            throw new Exception("上传文件失败，服务器发生异常！" + e);
        }
        //更新当前用户头像的路径(web访问路径)
        // http://localhost:8080/community/user/header/xxx.png
        User user = hostHolder.getUser();
        /**
         * 通过这个headerUrl路径会自动访问下面的getHeader方法
         */
        String headerUrl = domain + contextPath + "/user/header/" + fileName;
        userService.updateHeader(user.getId(), headerUrl);

        return "redirect:/index";
    }

    @RequestMapping(value = "/header/{fileName}", method = RequestMethod.GET)
    public void getHeader(@PathVariable("fileName") String fileName, HttpServletResponse response) {
        //文件在服务器上的存放路径
        fileName = uploadPath + "/" + fileName;

        String suffix = fileName.substring(fileName.lastIndexOf("."));

        response.setContentType("image/" + suffix);//响应图片

        try (
                FileInputStream fis = new FileInputStream(fileName);//文件的输入流，读取文件
                OutputStream os = response.getOutputStream();//将文件输出到浏览器的输出流
        ) {

            //建立一个缓冲区，每次输出缓冲区大小的文件，这样就不用一个字节一个字节的输出，而是一批一批地输出
            byte[] buffer = new byte[1024];
            int b = 0;
            while ((b = fis.read(buffer)) != -1) {//b=fis.read(buffer)表示每次read到buffer这么多数据
                os.write(buffer, 0, b);//每次写buffer这么多数据，从0开始写，写到b
            }
        } catch (IOException e) {
            logger.error("读取头像失败：" + e.getMessage());
        }
    }

    @LoginRequired
    @RequestMapping(path = "/pswModify", method = RequestMethod.POST)
    public String changePsw(Model model, String oldPsw, String newPsw) {
        User user = hostHolder.getUser();
        //这里不需要对用户判空，因为加了LoginRequired注解，未登陆的用户无法访问当前方法
        Map<String, Object> map = userService.changePsw(user, oldPsw, newPsw);
        if (map.isEmpty()) {//修改密码成功
            return "redirect:/logout";
        } else {
            model.addAttribute("oldPswMsg", map.get("oldPswMsg"));
            model.addAttribute("newPswMsg", map.get("newPsw"));
            return "/site/setting";
        }
    }

    @Autowired
    private LikeService likeService;
    @Autowired
    private FollowService followService;

    @RequestMapping(path = "/profile/{userId}", method = RequestMethod.GET)
    public String getProfilePage(@PathVariable("userId") int userId, Model model) {
        User user = userService.findUserById(userId);
        if (user == null) {
            throw new RuntimeException("该用户不存在！");
        }
        model.addAttribute("user", user);

        int likeCount = likeService.findUserLikeCount(userId);
        model.addAttribute("likeCount", likeCount);

        long followeeCount = followService.findFolloweeCount(userId,ENTITY_TYPE_USER);
        model.addAttribute("followeeCount",followeeCount);

        long followerCount = followService.findFollowerCount(ENTITY_TYPE_USER,userId);
        model.addAttribute("followerCount",followerCount);

        boolean hasFollowed = false;
        if(hostHolder.getUser()!=null){
            hasFollowed = followService.hasFollowed(hostHolder.getUser().getId(),ENTITY_TYPE_USER,userId);
        }
        model.addAttribute("hasFollowed",hasFollowed);

        return "/site/profile";
    }
}
