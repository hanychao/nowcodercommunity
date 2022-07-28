package com.nowcoder.community;

import org.junit.jupiter.api.Test;
import org.quartz.Job;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * @author hyc
 * @create 2022-07-28 14:07
 */
@SpringBootTest
@ContextConfiguration(classes=CommunityApplication.class)
public class QuartzTests {
    @Autowired
    private Scheduler scheduler;

    @Test
    public void testDeleteJob(){
        boolean result = false;
        try {
            result = scheduler.deleteJob(new JobKey("alphaJob","alphaJobGroup"));
            System.out.println(result);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
