package com.task.schedule;

import com.task.schedule.util.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 添加 Runnable 接口实现类，被定时任务线程池调用，用来执行指定 bean 里面的方法。
 *
 * @author qianjinlong
 * @email 1277977370@qq.com
 * @date 2021/10/18
 */
public class SchedulingRunnable implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(SchedulingRunnable.class);

    /** 类名 */
    private String beanName;

    /** 方法名 */
    private String methodName;

    /** 参数 */
    private String params;

    public SchedulingRunnable(String beanName, String methodName) {
        this(beanName, methodName, null);
    }

    public SchedulingRunnable(String beanName, String methodName, String params) {
        this.beanName = beanName;
        this.methodName = methodName;
        this.params = params;
    }

    @Override
    public void run() {
        logger.info("定时任务开始执行 - bean：{}，方法：{}，参数：{}", beanName, methodName, params);
        long startTime = System.currentTimeMillis();

        try {
            Object target = SpringContextUtil.getBean(beanName);

            Method method = null;
            if (StringUtils.isEmpty(params)) {
                method = target.getClass().getDeclaredMethod(methodName);
            } else {
                method = target.getClass().getDeclaredMethod(methodName, String.class);
            }
            ReflectionUtils.makeAccessible(method);
            if (StringUtils.isEmpty(params)) {
                method.invoke(target);
            } else {
                method.invoke(target, params);
            }
        } catch (Exception e) {
            logger.error(String.format("定时任务执行异常 - bean：%s，方法：%s，参数：%s ", beanName, methodName, params), e);
        }

        long times = System.currentTimeMillis() - startTime;
        logger.info("定时任务执行结束 - bean：{}，方法：{}，参数：{}，耗时：{} 毫秒", beanName, methodName, params, times);
    }

    @Override
    public int hashCode() {
        if (params == null) {
            return Objects.hash(beanName, methodName);
        }

        return Objects.hash(beanName, methodName, params);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SchedulingRunnable that = (SchedulingRunnable) o;
        if (params == null) {
            return beanName.equals(that.beanName) &&
                    methodName.equals(that.methodName) &&
                    that.params == null;
        }

        return beanName.equals(that.beanName) &&
                methodName.equals(that.methodName) &&
                params.equals(that.params);
    }

}
