package com.nowcoder.community.confige;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author hyc
 * @create 2022-07-28 9:58
 */
@Configuration
@EnableScheduling
@EnableAsync
public class ThreadPoolConfig {
}
