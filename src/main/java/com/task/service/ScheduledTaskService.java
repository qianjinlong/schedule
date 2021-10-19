package com.task.service;

import com.task.entity.SysJobEntity;
import com.task.enums.SysJobStatusEnum;
import com.task.mapper.SysJobRepository;
import com.task.schedule.CronTaskRegistrar;
import com.task.schedule.SchedulingRunnable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 类功能说明
 *
 * @author qianjinlong
 * @email 1277977370@qq.com
 * @date 2021/10/18
 */
@Service
public class ScheduledTaskService {

    private final CronTaskRegistrar cronTaskRegistrar;

    private final SysJobRepository sysJobRepository;

    @Autowired
    public ScheduledTaskService(CronTaskRegistrar cronTaskRegistrar, SysJobRepository sysJobRepository) {
        this.cronTaskRegistrar = cronTaskRegistrar;
        this.sysJobRepository = sysJobRepository;
    }

    public String addSysJob(SysJobEntity sysJobEntity) {
        boolean success = sysJobRepository.addSysJob(sysJobEntity);
        if (! success) {
            return "error";
        } else {
            if (sysJobEntity.getJobStatus() == SysJobStatusEnum.NORMAL.ordinal()) {
                SchedulingRunnable task = new SchedulingRunnable(sysJobEntity.getBeanName(), sysJobEntity.getMethodName(), sysJobEntity.getMethodParams());
                cronTaskRegistrar.addCronTask(task, sysJobEntity.getCronExpression());
            }
        }
        return "success";
    }

    public String updateSysJob(SysJobEntity sysJobEntity) {
        boolean success = sysJobRepository.updateSysJob(sysJobEntity);
        if (! success) {
            return "error";
        } else {
            // 先移除再添加
            if (sysJobEntity.getJobStatus() == SysJobStatusEnum.NORMAL.ordinal()) {
                SchedulingRunnable task = new SchedulingRunnable(sysJobEntity.getBeanName(), sysJobEntity.getMethodName(), sysJobEntity.getMethodParams());
                cronTaskRegistrar.removeCronTask(task);
            }

            if (sysJobEntity.getJobStatus() == SysJobStatusEnum.NORMAL.ordinal()) {
                SchedulingRunnable task = new SchedulingRunnable(sysJobEntity.getBeanName(), sysJobEntity.getMethodName(), sysJobEntity.getMethodParams());
                cronTaskRegistrar.addCronTask(task, sysJobEntity.getCronExpression());
            }
        }
        return "success";
    }

    public String deleteSysJobById(SysJobEntity sysJobEntity) {
        boolean success = sysJobRepository.deleteSysJobById(sysJobEntity.getJobId());
        if (! success) {
            return "error";
        } else {
            if (sysJobEntity.getJobStatus().equals(SysJobStatusEnum.NORMAL.ordinal())) {
                SchedulingRunnable task = new SchedulingRunnable(sysJobEntity.getBeanName(), sysJobEntity.getMethodName(), sysJobEntity.getMethodParams());
                cronTaskRegistrar.removeCronTask(task);
            }
        }
        return "success";
    }


}
