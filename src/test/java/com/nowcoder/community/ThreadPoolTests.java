package com.nowcoder.community;

import com.nowcoder.community.service.AlphaService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.test.context.ContextConfiguration;

import java.util.Date;
import java.util.concurrent.*;

/**
 * @author hyc
 * @create 2022-07-28 9:29
 */
@SpringBootTest
@ContextConfiguration(classes=CommunityApplication.class)
public class ThreadPoolTests {
    private static final Logger logger = LoggerFactory.getLogger(ThreadPoolTests.class);


    //JDK普通线程池
    private ExecutorService executorService = Executors.newFixedThreadPool(5);
    //JDK可执行定时任务的线程池
    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);

    //Spring普通线程池
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    //Spring可执行定时任务的线程池
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    public void sleep(long m){
        try {
            Thread.sleep(m);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testExecutorService(){
        Runnable task = new Runnable() {
            @Override
            public void run() {
                logger.debug("JDK ExecutorService");
            }
        };
        for (int i = 0; i < 10; i++) {
            executorService.submit(task);
        }
        sleep(10000);
    }

    @Test
    public void testScheduledExecutorService(){
        Runnable task = new Runnable() {
            @Override
            public void run() {
                logger.debug("JDK ScheduledExecutorService");
            }
        };
        scheduledExecutorService.scheduleAtFixedRate(task,10000,1000, TimeUnit.MILLISECONDS);
        sleep(30000);
    }

    @Test
    public void testThreadPoolTaskExecutor(){
        Runnable task = new Runnable() {
            @Override
            public void run() {
                logger.debug("Spring ThreadPoolTaskExecutor");
            }
        };
        for (int i = 0; i < 10; i++) {
            threadPoolTaskExecutor.submit(task);
        }
        sleep(10000);
    }

    @Test
    public void testThreadPoolTaskScheduler(){
        Runnable task = new Runnable() {
            @Override
            public void run() {
                logger.debug("Spring ThreadPoolTaskScheduler");
            }
        };
        Date startTime = new Date(System.currentTimeMillis()+10000);
        threadPoolTaskScheduler.scheduleAtFixedRate(task,startTime,1000);
        sleep(30000);
    }

    @Autowired
    private AlphaService alphaService;

    @Test
    public void testThreadPoolTaskExecutorSimple(){
        for (int i = 0; i < 10; i++) {
            alphaService.execute1();
        }
        sleep(10000);
    }

    @Test
    public void testThreadPoolTaskSchedulerSimple(){
        sleep(30000);
    }
}
