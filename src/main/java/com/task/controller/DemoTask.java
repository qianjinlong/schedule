package com.task.controller;

import org.springframework.stereotype.Component;

/**
 * 任务测试
 *
 * @author qianjinlong
 * @email 1277977370@qq.com
 * @date 2021/10/18
 */
@Component("demoTask")
public class DemoTask {

    public void taskWithParams(String params) {
        System.out.println("执行有参示例任务：" + params);
    }

    public void taskNoParams() {
        System.out.println("执行无参示例任务");
        System.out.println("ds");
    }
}