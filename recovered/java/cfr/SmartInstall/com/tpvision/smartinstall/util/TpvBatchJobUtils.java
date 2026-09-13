/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.util;

import com.tpvision.smartinstall.dao.core.JobLog;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.schedule.Job;
import com.tpvision.smartinstall.util.TpvClassUtils;
import com.tpvision.smartinstall.util.Utils;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TpvBatchJobUtils {
    private static final Logger LOG = LoggerFactory.getLogger(TpvBatchJobUtils.class);

    private TpvBatchJobUtils() {
    }

    public static Map<String, String> getExecuteAbleJobs(Job.ExecuteType ... types) {
        HashMap<String, String> jobMap = new HashMap<String, String>();
        try {
            List<Class<Job>> list = TpvClassUtils.getAllAssignedClass(Job.class);
            for (Class<Job> jobClz : list) {
                Job job = jobClz.newInstance();
                String jobName = jobClz.getSimpleName();
                if (!Arrays.asList(types).contains((Object)job.getExecuteType()) || !TpvBatchJobUtils.isAllowExecuteJob(job, jobName)) continue;
                jobMap.put(jobName, job.description());
            }
        }
        catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        }
        return jobMap;
    }

    private static boolean isAllowExecuteJob(Job job, String jobName) {
        if (!job.isExecuteOnce()) {
            return true;
        }
        return JpaManager.getJobLogManager().findByJobName(jobName).isEmpty();
    }

    public static String handleExecuteJobs(String jobName) {
        Job job = TpvBatchJobUtils.getExecuteJobInstance(jobName);
        if (job != null) {
            if (!TpvBatchJobUtils.isAllowExecuteJob(job, jobName)) {
                LOG.error("this job<{}> have already executed before.", (Object)jobName);
            }
            JobLog jobLog = TpvBatchJobUtils.saveExecuteJobLog(jobName);
            try {
                job.execute();
                TpvBatchJobUtils.saveJobResult("success", jobLog);
                return "{\"status\":\"success\"}";
            }
            catch (Exception ex) {
                TpvBatchJobUtils.saveJobResult("error:" + ex.getMessage(), jobLog);
                LOG.error(ex.getMessage(), ex);
            }
        }
        return "{\"status\":\"fail\"}";
    }

    private static Job getExecuteJobInstance(String jobName) {
        try {
            Class<?> clz = Class.forName(Job.class.getPackage().getName() + "." + jobName);
            return (Job)clz.newInstance();
        }
        catch (Exception e) {
            LOG.error("cant create job object<{}>", (Object)jobName);
            return null;
        }
    }

    private static void saveJobResult(String result, JobLog jobLog) {
        jobLog.setEndTime(new Date());
        jobLog.setResult(result);
        JpaManager.getJobLogManager().saveJobLog(jobLog);
    }

    private static JobLog saveExecuteJobLog(String jobName) {
        JobLog jobLog = new JobLog();
        jobLog.setJobName(jobName);
        jobLog.setStartTime(new Date());
        jobLog.setOperatorName(Utils.getAuthenticationName());
        JpaManager.getJobLogManager().saveJobLog(jobLog);
        return jobLog;
    }
}

