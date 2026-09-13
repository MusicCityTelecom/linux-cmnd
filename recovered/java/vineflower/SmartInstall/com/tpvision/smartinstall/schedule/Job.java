package com.tpvision.smartinstall.schedule;

public abstract class Job {
   public abstract void execute();

   public abstract String description();

   public abstract Job.ExecuteType getExecuteType();

   public abstract boolean isExecuteOnce();

   public enum ExecuteType {
      BYHAND,
      AUTOMATICALLY,
      ALL;
   }
}
