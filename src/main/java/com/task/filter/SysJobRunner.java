package com.task.filter;


import com.task.entity.SysJobEntity;
import com.task.enums.SysJobStatusEnum;
import com.task.mapper.SysJobRepository;
import com.task.schedule.CronTaskRegistrar;
import com.task.schedule.SchedulingRunnable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

/**
 * 类功能说明
 *
 * @author qianjinlong
 * @email 1277977370@qq.com
 * @date 2021/10/18
 */
@Component
public class SysJobRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(SysJobRunner.class);

    private SysJobRepository sysJobRepository;

    private CronTaskRegistrar cronTaskRegistrar;

    @Autowired
    public SysJobRunner(SysJobRepository sysJobRepository, CronTaskRegistrar cronTaskRegistrar) {
        this.sysJobRepository = sysJobRepository;
        this.cronTaskRegistrar = cronTaskRegistrar;
    }

    @Override
    public void run(String... args) {
        // 初始加载数据库里状态为正常的定时任务
        Stream<SysJobEntity> stream = sysJobRepository.getSysJobListByStatus(SysJobStatusEnum.NORMAL.ordinal());
        stream.forEach(o -> {
            SchedulingRunnable task = new SchedulingRunnable(o.getBeanName(), o.getMethodName(), o.getMethodParams());
            cronTaskRegistrar.addCronTask(task, o.getCronExpression());
        });
        logger.info("定时任务已加载完毕...");
    }
}