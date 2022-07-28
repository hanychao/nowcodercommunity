package com.nowcoder.community.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author hyc
 * @create 2022-07-28 13:38
 */
public class AlphaJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println(Thread.currentThread().getName()+":execute a quartz job.");
    }
}
