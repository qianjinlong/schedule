package com.task.schedule.task;

import java.util.concurrent.ScheduledFuture;

/**
 * ScheduledFuture 的包装类，
 * ScheduledFuture 是 ScheduledExecutorService 定时任务线程池的执行结果。
 *
 * @author qianjinlong
 * @email 1277977370@qq.com
 * @date 2021/10/18
 */
public final class ScheduledTask {

    /** 任务触发器 */
    public volatile ScheduledFuture<?> future;

    /** 取消定时任务 */
    public void cancel() {
        ScheduledFuture<?> future = this.future;
        if (future != null) {
            // 尝试取消此任务的执行
            future.cancel(true);
        }
    }
}
