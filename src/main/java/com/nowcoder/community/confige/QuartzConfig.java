package com.nowcoder.community.confige;

import com.nowcoder.community.quartz.AlphaJob;
import com.nowcoder.community.quartz.PostScoreRefreshJob;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

/**
 * @author hyc
 * @create 2022-07-28 13:41
 */
//本配置文件仅仅是第一次被读取到，将其封装的信息初始化到数据库里，以后quartz是访问数据库去调用这个任务，而不再访问这个配置文件
//配置->数据库->调用
@Configuration
public class QuartzConfig {
    //@Bean
    public JobDetailFactoryBean alphaJobDetail(){
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(AlphaJob.class);
        factoryBean.setName("alphaJob");
        factoryBean.setGroup("alphaJobGroup");
        factoryBean.setDurability(true);
        factoryBean.setRequestsRecovery(true);
        return factoryBean;
    }
    //@Bean
    public SimpleTriggerFactoryBean alphaTrigger(JobDetail alphaJobDetail){
        SimpleTriggerFactoryBean simpleTriggerFactoryBean = new SimpleTriggerFactoryBean();
        simpleTriggerFactoryBean.setJobDetail(alphaJobDetail);
        simpleTriggerFactoryBean.setName("alphaTrigger");
        simpleTriggerFactoryBean.setGroup("alphaTriggerGroup");
        simpleTriggerFactoryBean.setRepeatInterval(3000);
        simpleTriggerFactoryBean.setJobDataMap(new JobDataMap());
        return  simpleTriggerFactoryBean;
    }

    //刷新帖子分数任务
    @Bean
    public JobDetailFactoryBean postScoreRefreshJobDetail(){
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(PostScoreRefreshJob.class);
        factoryBean.setName("PostScoreRefreshJob");
        factoryBean.setGroup("communityJobGroup");
        factoryBean.setDurability(true);
        factoryBean.setRequestsRecovery(true);
        return factoryBean;
    }

    @Bean
    public SimpleTriggerFactoryBean postScoreRefreshTrigger(JobDetail postScoreRefreshJobDetail){
        SimpleTriggerFactoryBean simpleTriggerFactoryBean = new SimpleTriggerFactoryBean();
        simpleTriggerFactoryBean.setJobDetail(postScoreRefreshJobDetail);
        simpleTriggerFactoryBean.setName("postScoreRefreshTrigger");
        simpleTriggerFactoryBean.setGroup("communityTriggerGroup");
        simpleTriggerFactoryBean.setRepeatInterval(1000*60*5);
        simpleTriggerFactoryBean.setJobDataMap(new JobDataMap());
        return  simpleTriggerFactoryBean;
    }

}
