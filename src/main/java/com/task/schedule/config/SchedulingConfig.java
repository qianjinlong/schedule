package com.task.schedule.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * 执行定时任务的线程池配置类
 * <p>
 * 注：org.springframework.scheduling.ScheduledTaskRegistrar 这个类的源代码，
 * 发现可以通过改造这个类就能实现动态增删启停定时任务功能。
 * 参考：https://mp.weixin.qq.com/s/D2WDNCBuqSbTSuY8TkjPqA
 *
 * @author qianjinlong
 * @email 1277977370@qq.com
 * @date 2021/10/18
 */
@Configuration
public class SchedulingConfig {

    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        // 定时任务执行线程池核心线程数
        taskScheduler.setPoolSize(4);
        taskScheduler.setRemoveOnCancelPolicy(true);
        taskScheduler.setThreadNamePrefix("TaskSchedulerThreadPool-");
        return taskScheduler;
    }
}