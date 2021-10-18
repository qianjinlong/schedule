package com.task.schedule;


import com.task.schedule.task.ScheduledTask;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.config.CronTask;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 定时任务注册类，用来增加、删除定时任务。
 *
 * @author qianjinlong
 * @email 1277977370@qq.com
 * @date 2021/10/18
 */
@Component
public class CronTaskRegistrar implements DisposableBean {

    /** 定时任务存储 */
    private final Map<Runnable, ScheduledTask> scheduledTasks = new ConcurrentHashMap<>(16);

    /** 任务调度器接口 interface org.springframework.scheduling.TaskScheduler */
    private TaskScheduler taskScheduler;

    @Autowired
    public CronTaskRegistrar(TaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
    }

    /**
     * 添加定时任务
     *
     * @param task           任务
     * @param cronExpression cron 表达式
     */
    public void addCronTask(Runnable task, String cronExpression) {
        // 创建定时任务
        CronTask cronTask = new CronTask(task, cronExpression);
        // 添加定时任务
        this.addCronTask(cronTask);
    }

    /**
     * 添加定时任务
     *
     * @param cronTask 定时任务
     */
    public void addCronTask(CronTask cronTask) {
        if (cronTask != null) {
            // 获取 CronTask 当前任务
            Runnable runnable = cronTask.getRunnable();
            if (this.scheduledTasks.containsKey(runnable)) {
                // 若在 scheduledTasks 存在, 移除并取消此任务
                this.removeCronTask(runnable);
            }
            // 创建 ScheduledTask 任务对象
            ScheduledTask scheduledTask = this.buildScheduleCronTask(cronTask);
            // 向 scheduledTasks 添加任务
            this.scheduledTasks.put(runnable, scheduledTask);
        }
    }

    /**
     * 移除定时任务
     *
     * @param runnable 任务
     */
    public void removeCronTask(Runnable runnable) {
        // 从 scheduledTasks 移除指定任务, 并返回
        ScheduledTask scheduledTask = this.scheduledTasks.remove(runnable);
        if (scheduledTask != null) {
            // 取消定时任务
            scheduledTask.cancel();
        }
    }

    /**
     * 创建 ScheduledTask 任务对象
     *
     * @param cronTask 定时任务
     * @return ScheduledTask
     */
    public ScheduledTask buildScheduleCronTask(CronTask cronTask) {
        // 创建 ScheduledTask 任务对象
        ScheduledTask scheduledTask = new ScheduledTask();
        // 添加当前任务的任务触发器
        scheduledTask.future = this.taskScheduler.schedule(cronTask.getRunnable(), cronTask.getTrigger());
        return scheduledTask;
    }

    @Override
    public void destroy() {
        for (ScheduledTask task : this.scheduledTasks.values()) {
            // 取消任务
            task.cancel();
        }
        // 清空 scheduledTasks
        this.scheduledTasks.clear();
    }
}