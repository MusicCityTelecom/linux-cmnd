package com.tpvision.smartinstall.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RFTask {
   private static final Logger LOG = LoggerFactory.getLogger(RFTask.class);
   private final ScheduledExecutorService fScheduler;
   private final long fInitialDelay;
   private final long fDelayBetweenRuns;
   private final long fShutdownAfter;
   private static final int NUM_THREADS = 1;
   private static final boolean DONT_INTERRUPT_IF_RUNNING = false;

   public RFTask(long aInitialDelay, long aDelayBetweenBeeps, long aStopAfter) {
      this.fInitialDelay = aInitialDelay;
      this.fDelayBetweenRuns = aDelayBetweenBeeps;
      this.fShutdownAfter = aStopAfter;
      this.fScheduler = Executors.newScheduledThreadPool(1, new TpvNamedThreadFactory("rftask"));
   }

   public void activateRFThenStop() {
      Runnable stsrtRFTask = new RFTask.StartRFTask();
      ScheduledFuture<?> rfFuture = this.fScheduler.scheduleWithFixedDelay(stsrtRFTask, this.fInitialDelay, this.fDelayBetweenRuns, TimeUnit.SECONDS);
      Runnable stopRFTask = new RFTask.StopRFTask(rfFuture);
      this.fScheduler.schedule(stopRFTask, this.fShutdownAfter, TimeUnit.SECONDS);
   }

   private static final class StartRFTask implements Runnable {
      private StartRFTask() {
      }

      @Override
      public void run() {
         try {
            if (ProcessUtils.isProcessRunning("Gateway.exe")) {
               ProcessUtils.killProcess("Gateway.exe");
            }
         } catch (Exception e) {
            RFTask.LOG.error("" + e.getMessage(), e);
         }
      }
   }

   private final class StopRFTask implements Runnable {
      private ScheduledFuture<?> fSchedFuture;

      StopRFTask(ScheduledFuture<?> aSchedFuture) {
         this.fSchedFuture = aSchedFuture;
      }

      @Override
      public void run() {
         try {
            if (RFCommandState.instance().hasExecutionRunning()) {
               RFCommandState.instance().cleanup();
            }
         } catch (Exception e) {
            RFTask.LOG.error("" + e.getMessage(), e);
         }

         this.fSchedFuture.cancel(false);
         RFTask.this.fScheduler.shutdown();
      }
   }
}
