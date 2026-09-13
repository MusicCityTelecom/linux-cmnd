package com.tpvision.smartinstall.util;

import com.tpvision.smartinstall.dao.core.JobLog;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.schedule.Job;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TpvBatchJobUtils {
   private static final Logger LOG = LoggerFactory.getLogger(TpvBatchJobUtils.class);

   private TpvBatchJobUtils() {
   }

   public static Map<String, String> getExecuteAbleJobs(Job.ExecuteType... types) {
      Map<String, String> jobMap = new HashMap<>();

      try {
         for (Class<? extends Job> jobClz : TpvClassUtils.getAllAssignedClass(Job.class)) {
            Job job = jobClz.newInstance();
            String jobName = jobClz.getSimpleName();
            if (Arrays.asList(types).contains(job.getExecuteType()) && isAllowExecuteJob(job, jobName)) {
               jobMap.put(jobName, job.description());
            }
         }
      } catch (Exception ex) {
         LOG.error(ex.getMessage(), ex);
      }

      return jobMap;
   }

   private static boolean isAllowExecuteJob(Job job, String jobName) {
      return !job.isExecuteOnce() ? true : JpaManager.getJobLogManager().findByJobName(jobName).isEmpty();
   }

   public static String handleExecuteJobs(String jobName) {
      Job job = getExecuteJobInstance(jobName);
      if (job != null) {
         if (!isAllowExecuteJob(job, jobName)) {
            LOG.error("this job<{}> have already executed before.", jobName);
         }

         JobLog jobLog = saveExecuteJobLog(jobName);

         try {
            job.execute();
            saveJobResult("success", jobLog);
            return "{\"status\":\"success\"}";
         } catch (Exception ex) {
            saveJobResult("error:" + ex.getMessage(), jobLog);
            LOG.error(ex.getMessage(), ex);
         }
      }

      return "{\"status\":\"fail\"}";
   }

   private static Job getExecuteJobInstance(String jobName) {
      try {
         Class<? extends Job> clz = (Class<? extends Job>)Class.forName(Job.class.getPackage().getName() + "." + jobName);
         return clz.newInstance();
      } catch (Exception e) {
         LOG.error("cant create job object<{}>", jobName);
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
