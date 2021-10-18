package com.task.controller;

import com.task.entity.SysJobEntity;
import com.task.service.ScheduledTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 类功能说明
 *
 * @author qianjinlong
 * @email 1277977370@qq.com
 * @date 2021/10/18
 */
@RestController
@RequestMapping
public class TestController {

    @Autowired
    private ScheduledTaskService scheduledTaskService;

    @GetMapping("/task")
    public String task() throws InterruptedException {
        SysJobEntity sysJobEntity = new SysJobEntity();
        sysJobEntity.setJobId(0);
        sysJobEntity.setBeanName("demoTask");
        sysJobEntity.setMethodName("taskWithParams");
        sysJobEntity.setMethodParams("12346798");
        sysJobEntity.setCronExpression("0/3 * * * * ?");
        sysJobEntity.setJobStatus(1);
        sysJobEntity.setRemark("");
        sysJobEntity.setCreateTime(new Date());
        sysJobEntity.setUpdateTime(new Date());
        return scheduledTaskService.deleteSysJobById(sysJobEntity);
    }
}
