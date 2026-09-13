/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.dao.mgr;

import com.tpvision.smartinstall.dao.JobLogRepository;
import com.tpvision.smartinstall.dao.core.JobLog;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobLogManager {
    @Autowired
    private JobLogRepository jobLogRepository;

    public void saveJobLog(JobLog jobLog) {
        this.jobLogRepository.save(jobLog);
    }

    public List<JobLog> findByJobName(String jobName) {
        return this.jobLogRepository.findByJobName(jobName);
    }
}

