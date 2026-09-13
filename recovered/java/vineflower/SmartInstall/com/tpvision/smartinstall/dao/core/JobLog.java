package com.tpvision.smartinstall.dao.core;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "job_log")
public class JobLog {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "log_id")
   private int logId;
   @Column(name = "job_name")
   private String jobName;
   @Column(name = "start_time")
   private Date startTime;
   @Column(name = "end_time")
   private Date endTime;
   @Column(name = "operator_name")
   private String operatorName;
   @Column(name = "result")
   private String result;

   public int getLogId() {
      return this.logId;
   }

   public void setLogId(int logId) {
      this.logId = logId;
   }

   public Date getStartTime() {
      return this.startTime;
   }

   public void setStartTime(Date startTime) {
      this.startTime = startTime;
   }

   public Date getEndTime() {
      return this.endTime;
   }

   public void setEndTime(Date endTime) {
      this.endTime = endTime;
   }

   public String getJobName() {
      return this.jobName;
   }

   public void setJobName(String jobName) {
      this.jobName = jobName;
   }

   public String getOperatorName() {
      return this.operatorName;
   }

   public void setOperatorName(String operatorName) {
      this.operatorName = operatorName;
   }

   public String getResult() {
      return this.result;
   }

   public void setResult(String result) {
      this.result = result;
   }
}
