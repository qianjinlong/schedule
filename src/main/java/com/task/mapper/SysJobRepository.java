package com.task.mapper;


import com.task.entity.SysJobEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

/**
 * 类功能说明
 *
 * @author qianjinlong
 * @email 1277977370@qq.com
 * @date 2021/10/18
 */
@Repository
public class SysJobRepository {

    public boolean addSysJob(SysJobEntity sysJobEntity) {
        return true;
    }

    public boolean updateSysJob(SysJobEntity sysJobEntity) {
        return true;
    }

    public boolean deleteSysJobById(Integer jobId) {
        return true;
    }

    public Stream<SysJobEntity> getSysJobListByStatus(Integer status) {
        List<SysJobEntity> list = new ArrayList<>();
        SysJobEntity sysJobEntity = new SysJobEntity();
        sysJobEntity.setJobId(0);
        sysJobEntity.setBeanName("demoTask");
        sysJobEntity.setMethodName("taskWithParams");
        sysJobEntity.setMethodParams("12346798");
        sysJobEntity.setCronExpression("0/3 * * * * ?");
        sysJobEntity.setJobStatus(0);
        sysJobEntity.setRemark("");
        sysJobEntity.setCreateTime(new Date());
        sysJobEntity.setUpdateTime(new Date());
        list.add(sysJobEntity);
        return list.stream();
    }
}
