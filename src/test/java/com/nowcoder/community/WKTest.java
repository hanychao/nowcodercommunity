package com.nowcoder.community;

import java.io.IOException;

/**
 * @author hyc
 * @create 2022-07-28 21:16
 */
public class WKTest {
    public static void main(String[] args) {
        String cmd = "E:\\wkhtmltopdf\\bin\\wkhtmltoimage --quality 75 https://www.nowcoder.com F:/FakeNowCoderCommunity/data/wk-images/2.png";
        try {
            //Runtime执行命令只是将命令提交给本地操作系统（与当前主程序是异步的），剩下的事由操作系统来执行，所以ok很快被输出，图片生成滞后一点
            Runtime.getRuntime().exec(cmd);
            System.out.println("ok");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
