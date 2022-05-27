package com.nowcoder.community.confige;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

/**
 * @author hyc
 * @create 2022-05-16 21:22
 */
@Configuration
public class AlphaConfig {
    @Bean
    public SimpleDateFormat getSimpleDateFormat(){
        return new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
    }
}
