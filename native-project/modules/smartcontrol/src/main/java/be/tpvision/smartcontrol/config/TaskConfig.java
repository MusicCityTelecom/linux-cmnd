package be.tpvision.smartcontrol.config;

import be.tpvision.smartcontrol.io.FetchInfoDataTaskAlreadyRunningException;
import be.tpvision.smartcontrol.io.FetchOverviewDataTaskAlreadyRunningException;
import be.tpvision.smartcontrol.messages.config.task_config.ExecuteTaskMessages;
import be.tpvision.smartcontrol.service.ContentManagementService;
import be.tpvision.smartcontrol.service.DeviceDataManagementService;
import be.tpvision.smartcontrol.service.DeviceService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.util.Assert;

@Configuration
@EnableScheduling
public class TaskConfig implements SchedulingConfigurer {
   private static final Logger logger = LoggerFactory.getLogger(TaskConfig.class);
   private static final ExecutorService threadPool = Executors.newCachedThreadPool();
   @Value("${smartcms.content.temp.clean-up.enabled}")
   private boolean tempContentCleanUpEnabled;
   @Value("${smartcms.content.temp.clean-up.interval.minutes}")
   private int tempContentCleanUpIntervalMinutes;
   @Value("${smartcontrol.synchronization.enabled}")
   private boolean dataSynchronisationEnabled;
   @Value("${smartcontrol.synchronization.interval.minutes}")
   private int dataSynchronisationIntervalMinutes;
   @Value("${smartcontrol.content.update.enabled}")
   private boolean contentUpdateEnabled;
   @Value("${smartcontrol.content.update.interval.minutes}")
   private int contentUpdateIntervalMinutes;
   @Autowired
   private ContentManagementService contentManagementService;
   @Autowired
   private DeviceDataManagementService deviceDataManagementService;
   @Autowired
   private DeviceService deviceService;

   private static <T> T executeTask(final FutureTask<T> futureTask, long timeout, final TimeUnit timeUnit) {
      Assert.notNull(futureTask, ExecuteTaskMessages.FUTURE_TASK_CAN_NOT_BE_NULL);
      Assert.isTrue(timeout >= 0L, ExecuteTaskMessages.TIMEOUT_HAS_TO_BE_A_POSITIVE_NUMBER);
      Assert.notNull(timeUnit, ExecuteTaskMessages.TIME_UNIT_CAN_NOT_BE_NULL);
      threadPool.execute(futureTask);

      try {
         return futureTask.get(timeout, timeUnit);
      } catch (InterruptedException | ExecutionException | TimeoutException e) {
         String message = e.getMessage();
         logger.error(message, e);
         futureTask.cancel(true);
         return null;
      }
   }

   @Override
   public void configureTasks(final ScheduledTaskRegistrar taskRegistrar) {
      if (this.tempContentCleanUpEnabled) {
         long intervalMilliseconds = TimeUnit.MINUTES.toMillis(this.tempContentCleanUpIntervalMinutes);
         taskRegistrar.addFixedDelayTask(this.contentManagementService::deleteTempContent, intervalMilliseconds);
      }

      if (this.dataSynchronisationEnabled) {
         long intervalMilliseconds = TimeUnit.MINUTES.toMillis(this.dataSynchronisationIntervalMinutes);
         long timeout = intervalMilliseconds - TimeUnit.SECONDS.toMillis(10L);
         Runnable overviewRunnable = () -> {
            FutureTask<Void> futureTask = new FutureTask<>(() -> {
               try {
                  this.deviceDataManagementService.fetchOverviewData();
               } catch (FetchOverviewDataTaskAlreadyRunningException e) {
                  String message = e.getMessage();
                  logger.info(message);
               }

               return null;
            });
            executeTask(futureTask, timeout, TimeUnit.MILLISECONDS);
         };
         taskRegistrar.addFixedDelayTask(overviewRunnable, intervalMilliseconds);
         Runnable infoRunnable = () -> {
            FutureTask<Void> futureTask = new FutureTask<>(() -> {
               try {
                  this.deviceDataManagementService.fetchInfoData();
               } catch (FetchInfoDataTaskAlreadyRunningException e) {
                  String message = e.getMessage();
                  logger.info(message);
               }

               return null;
            });
            executeTask(futureTask, timeout, TimeUnit.MILLISECONDS);
         };
         taskRegistrar.addFixedDelayTask(infoRunnable, intervalMilliseconds);
      }

      if (this.contentUpdateEnabled) {
         long intervalMilliseconds = TimeUnit.MINUTES.toMillis(this.contentUpdateIntervalMinutes);
         taskRegistrar.addFixedDelayTask(() -> this.contentManagementService.updateContent(true, true), intervalMilliseconds);
      }

      new Thread(() -> this.deviceService.logAllDevices()).start();
   }

   @Bean
   public TaskExecutor taskExecutor() {
      ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
      threadPoolTaskExecutor.setCorePoolSize(2);
      threadPoolTaskExecutor.setMaxPoolSize(10);
      threadPoolTaskExecutor.setQueueCapacity(10);
      return threadPoolTaskExecutor;
   }

   @Bean
   public TaskScheduler taskScheduler() {
      ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
      threadPoolTaskScheduler.setPoolSize(10);
      return threadPoolTaskScheduler;
   }
}
