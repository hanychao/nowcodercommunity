package com.nowcoder.community;

import com.nowcoder.community.util.MailClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * @author hyc
 * @create 2022-05-24 15:20
 */
@SpringBootTest
@ContextConfiguration(classes=CommunityApplication.class)
public class MailTests {
    @Autowired
    private MailClient mailClient;
    @Autowired
    private TemplateEngine templateEngine;
    @Test
    public void testMail(){
        mailClient.sendMail("1404733494@qq.com","testEmail","welcome to springboot");
    }
    @Test
    public void testHtmlMail(){
        Context context = new Context();
        context.setVariable("username","ahu.lab.102.2F");
        String content = templateEngine.process("/mail/demo", context);
        System.out.println(content);

        mailClient.sendMail("1404733494@qq.com","testEmail",content);
    }

}
