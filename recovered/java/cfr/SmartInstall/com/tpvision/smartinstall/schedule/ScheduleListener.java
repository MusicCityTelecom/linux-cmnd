/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.catalina.util.ServerInfo
 */
package com.tpvision.smartinstall.schedule;

import com.tpvision.smartinstall.api.ApiLicenseChecker;
import com.tpvision.smartinstall.dao.core.SIConfig;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.dao.mgr.SIConfigManager;
import com.tpvision.smartinstall.gateway.GatewayProcessTask;
import com.tpvision.smartinstall.pms.PmsUtils;
import com.tpvision.smartinstall.schedule.AutoCheckGuestTask;
import com.tpvision.smartinstall.schedule.AutoVersionChecktTask;
import com.tpvision.smartinstall.schedule.CmndMetricsLogCleanTask;
import com.tpvision.smartinstall.schedule.CmndMetricsTask;
import com.tpvision.smartinstall.schedule.DetectOfflineTask;
import com.tpvision.smartinstall.schedule.Job;
import com.tpvision.smartinstall.schedule.PlayoutWeatherForecastTask;
import com.tpvision.smartinstall.schedule.SynCastAnalyticalDataTask;
import com.tpvision.smartinstall.schedule.SyncLicenseManagerTask;
import com.tpvision.smartinstall.trigger.TriggerProcessTask;
import com.tpvision.smartinstall.util.CertUtils;
import com.tpvision.smartinstall.util.CommonConstants;
import com.tpvision.smartinstall.util.DatabaseUtils;
import com.tpvision.smartinstall.util.DownloadLimiter;
import com.tpvision.smartinstall.util.Log4j2Utils;
import com.tpvision.smartinstall.util.TpvBatchJobUtils;
import com.tpvision.smartinstall.util.TpvNamedThreadFactory;
import com.tpvision.smartinstall.util.TpvRunableTask;
import com.tpvision.smartinstall.util.Utils;
import com.tpvision.smartinstall.weather.WeatherTask;
import java.io.File;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.catalina.util.ServerInfo;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScheduleListener
implements ServletContextListener {
    private static final String PMS_HTNG_SHOW = "pms.htng.show";
    private static final String PMS_FIAS_SHOW = "pms.fias.show";
    private static final String PMS_ACCOR_SHOW = "pms.accor.show";
    private static final String PMS_IMPALA_SHOW = "pms.impala.show";
    private static final Logger LOG = LoggerFactory.getLogger(ScheduleListener.class);
    private static ScheduledExecutorService service4weather;
    private static ScheduledExecutorService service4weatherPlayout;
    private static List<ScheduledExecutorService> serviceList;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        this.injectCommonConstants(event);
        this.printCMNDServerInfo(event);
        Properties properties = Utils.getProductPropties();
        this.configAdminShow(event, properties);
        this.configFileBeatService(properties);
        LOG.info("check database migration");
        DatabaseUtils.getInstance().checkDatabaseInited();
        DatabaseUtils.getInstance().migrate();
        DownloadLimiter.init();
        ScheduleListener.createSchedule();
        this.startupBatchJobs();
    }

    private void printCMNDServerInfo(ServletContextEvent event) {
        List<String> inputArguments = ManagementFactory.getRuntimeMXBean().getInputArguments();
        LOG.info("Used java version:{}", (Object)System.getProperty("java.version"));
        LOG.info("java run options: {}", (Object)inputArguments);
        LOG.info("tomcat version:{}", (Object)ServerInfo.getServerInfo());
        LOG.info("CMND major version:{}", (Object)Utils.getCMNDMajorVersion());
        LOG.info("CMND build version:{}", (Object)Utils.getCMNDBuildVersion(event.getServletContext()));
    }

    private void startupBatchJobs() {
        LOG.info("start batch jobs.");
        Map<String, String> executeJobs = TpvBatchJobUtils.getExecuteAbleJobs(Job.ExecuteType.AUTOMATICALLY, Job.ExecuteType.ALL);
        LOG.info("get all support auto execute job map:{}", (Object)executeJobs);
        for (final String jobName : executeJobs.keySet()) {
            new Thread(new TpvRunableTask(){

                @Override
                public void execute() {
                    TpvBatchJobUtils.handleExecuteJobs(jobName);
                }
            }).start();
        }
    }

    private void configFileBeatService(Properties properties) {
        boolean isStartFileBeat = "yes".equalsIgnoreCase(properties.getProperty("send.log.to.esserver"));
        Log4j2Utils.initFileBeartService(isStartFileBeat);
    }

    private void configAdminShow(ServletContextEvent event, Properties properties) {
        String[] pmsNames;
        ServletContext servletContext = event.getServletContext();
        if ("yes".equalsIgnoreCase(properties.getProperty("pms.show"))) {
            servletContext.setAttribute("pmsSwitcher", "yes");
        } else {
            servletContext.setAttribute("pmsSwitcher", "no");
        }
        if ("yes".equalsIgnoreCase(properties.getProperty("pms.activate.license"))) {
            servletContext.setAttribute("pmsShowActivateLicense", true);
            servletContext.setAttribute("pmsShowActivateLicenseLink", properties.getProperty("pms.activate.license.url"));
        } else {
            servletContext.setAttribute("pmsShowActivateLicense", false);
        }
        for (String pmsName : pmsNames = new String[]{PMS_FIAS_SHOW, PMS_ACCOR_SHOW, PMS_HTNG_SHOW, "pms.hop.show", "pms.fiasserver.show", PMS_IMPALA_SHOW}) {
            servletContext.setAttribute(pmsName, "yes".equalsIgnoreCase(properties.getProperty(pmsName)));
        }
    }

    private static void createSchedule() {
        LOG.info("Initilize scheduled tasks.");
        ScheduleListener.createCertCheckSchedule();
        if (PmsUtils.isPmsEnabled()) {
            new Thread(() -> PmsUtils.setPMSEnabled(true)).start();
        }
        ScheduleListener.weatherSchedule();
        ScheduleListener.gatewayProcessSchedule();
        ScheduleListener.createTriggerSchedule();
        ScheduleListener.createDetectOfflineTaskSchedule();
        ScheduleListener.createLogMetricsTask();
        ScheduleListener.createAutoCheckOutGuestTaskSchedule();
        ScheduleListener.createLicenseCheckTask();
        ScheduleListener.createVersionCheckTask();
        ScheduleListener.createSynCastAnalyticalDataTask();
    }

    private static void createVersionCheckTask() {
        long period = 86400000L;
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1, new TpvNamedThreadFactory("autoVersionChecktTask"));
        service.scheduleWithFixedDelay(new AutoVersionChecktTask(), 0L, period, TimeUnit.MILLISECONDS);
        serviceList.add(service);
        LOG.info("Start CMND/Recetion/Firmware version check every day.");
    }

    private static void createLicenseCheckTask() {
        GregorianCalendar targetCalendar = new GregorianCalendar();
        targetCalendar.set(5, targetCalendar.get(5) + 1);
        targetCalendar.set(11, 0);
        targetCalendar.set(12, RandomUtils.nextInt(0, 30));
        targetCalendar.set(13, 0);
        long delay = targetCalendar.getTimeInMillis() - System.currentTimeMillis();
        long period = 86400000L;
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1, new TpvNamedThreadFactory("syncLicenseManagerTask"));
        service.scheduleWithFixedDelay(new SyncLicenseManagerTask(), delay, period, TimeUnit.MILLISECONDS);
        serviceList.add(service);
        new Thread(new TpvRunableTask(){

            @Override
            public void execute() {
                ApiLicenseChecker.getInstance().loadAll(false);
            }
        }).start();
        LOG.info("Start sync cloud license info every day.");
    }

    private static void createSynCastAnalyticalDataTask() {
        GregorianCalendar targetCalendar = new GregorianCalendar();
        targetCalendar.set(5, targetCalendar.get(5) + 1);
        targetCalendar.set(11, 0);
        targetCalendar.set(12, RandomUtils.nextInt(0, 120));
        targetCalendar.set(13, 0);
        long delay = targetCalendar.getTimeInMillis() - System.currentTimeMillis();
        long period = 86400000L;
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1, new TpvNamedThreadFactory("synCastAnalyticalDataTask"));
        service.scheduleWithFixedDelay(new SynCastAnalyticalDataTask(), delay, period, TimeUnit.MILLISECONDS);
        serviceList.add(service);
        new Thread(new SynCastAnalyticalDataTask()).start();
        LOG.info("Start syn CastAnalyticalData task every day.");
    }

    private static void createLogMetricsTask() {
        GregorianCalendar targetCalendar = new GregorianCalendar();
        targetCalendar.set(5, targetCalendar.get(5) + 1);
        targetCalendar.set(11, 0);
        targetCalendar.set(12, RandomUtils.nextInt(0, 60));
        targetCalendar.set(13, 0);
        long delay = targetCalendar.getTimeInMillis() - System.currentTimeMillis();
        long period = 86400000L;
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1, new TpvNamedThreadFactory("logMetricsTask"));
        service.scheduleWithFixedDelay(new CmndMetricsTask(), delay, period, TimeUnit.MILLISECONDS);
        serviceList.add(service);
        new Thread(new CmndMetricsTask()).start();
        LOG.info("Start logMetricsTask every 24 hours.");
        Calendar nextDayCalendar = Calendar.getInstance();
        nextDayCalendar.add(5, 1);
        nextDayCalendar.set(11, 0);
        nextDayCalendar.set(12, 0);
        nextDayCalendar.set(13, 0);
        long nextDayDelay = nextDayCalendar.getTimeInMillis() - System.currentTimeMillis();
        long dayPeriod = 86400000L;
        ScheduledExecutorService cleanService = Executors.newScheduledThreadPool(1, new TpvNamedThreadFactory("cmndMetricsLogCleanTask"));
        cleanService.scheduleWithFixedDelay(new CmndMetricsLogCleanTask(), nextDayDelay, dayPeriod, TimeUnit.MILLISECONDS);
        serviceList.add(service);
        new Thread(new CmndMetricsLogCleanTask()).start();
        LOG.info("Start cmndMetricsLogCleanTask every week.");
    }

    private static void createPlayoutWeatherForecastTask() {
        int currentHour = new GregorianCalendar().get(11);
        int[] executeHours = new int[]{6, 12, 18, 0};
        int earlyHour = executeHours[currentHour / 6];
        GregorianCalendar targetCalendar = new GregorianCalendar();
        if (earlyHour == 0) {
            targetCalendar.set(5, targetCalendar.get(5) + 1);
        }
        targetCalendar.set(11, earlyHour);
        targetCalendar.set(12, 0);
        targetCalendar.set(13, 0);
        long delay = targetCalendar.getTimeInMillis() - System.currentTimeMillis();
        long period = 21600000L;
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1, new TpvNamedThreadFactory("weatherplayout"));
        service.scheduleWithFixedDelay(new PlayoutWeatherForecastTask(), delay, period, TimeUnit.MILLISECONDS);
        serviceList.add(service);
        service4weatherPlayout = service;
        PlayoutWeatherForecastTask.startPlay();
        LOG.info("Start createPlayoutWeatherForecastTask every 6 hours.");
    }

    private static void createCertCheckSchedule() {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1, new TpvNamedThreadFactory("cert"));
        service.scheduleWithFixedDelay(CertUtils::scheduleCheckDomainCertFiles, 1L, 60000L, TimeUnit.MILLISECONDS);
        serviceList.add(service);
        LOG.info("Start createCertCheckSchedule every 15 min.");
    }

    private static void createDetectOfflineTaskSchedule() {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1, new TpvNamedThreadFactory("offline"));
        service.scheduleWithFixedDelay(new DetectOfflineTask(), 0L, 900000L, TimeUnit.MILLISECONDS);
        serviceList.add(service);
        LOG.info("Start DetectOfflineTask every 15 min.");
    }

    private static void createAutoCheckOutGuestTaskSchedule() {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1, new TpvNamedThreadFactory("checkout"));
        service.scheduleWithFixedDelay(new AutoCheckGuestTask(), 0L, 60000L, TimeUnit.MILLISECONDS);
        serviceList.add(service);
        LOG.info("Start AutoCheckOutGuestTask every min.");
    }

    private static void createTriggerSchedule() {
        LOG.info("Start Trigger Process every 1min.");
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1, new TpvNamedThreadFactory("triggle"));
        service.scheduleWithFixedDelay(new TriggerProcessTask(), 1000L, 60000L, TimeUnit.MILLISECONDS);
        serviceList.add(service);
    }

    private static void gatewayProcessSchedule() {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1, new TpvNamedThreadFactory("gateway"));
        service.scheduleWithFixedDelay(new GatewayProcessTask(), 1000L, 5000L, TimeUnit.MILLISECONDS);
        serviceList.add(service);
        LOG.info("start gateway checker task every 5s.");
    }

    private static void weatherSchedule() {
        SIConfigManager confMgr = JpaManager.getSIConfigManager();
        SIConfig config = confMgr.getSIConfig();
        if (null != config && config.isWeatherServiceEnabled()) {
            int refreshRate = config.getRealRefreshRate();
            service4weather = Executors.newScheduledThreadPool(1, new TpvNamedThreadFactory("weather"));
            service4weather.scheduleWithFixedDelay(new WeatherTask(), 1000L, (long)(refreshRate * 60) * 1000L, TimeUnit.MILLISECONDS);
            serviceList.add(service4weather);
            LOG.info("Created Tasks to run WeatherUpdate every {} minutes", (Object)refreshRate);
            if (service4weatherPlayout == null) {
                ScheduleListener.createPlayoutWeatherForecastTask();
            }
        } else {
            LOG.info("Weather service is disabled.");
            ScheduleListener.stopWeatherPlayout();
        }
    }

    private void injectCommonConstants(ServletContextEvent event) {
        Utils.setServletContext(event.getServletContext());
        CommonConstants.TOMCAT_WD = System.getProperty("catalina.home");
        CommonConstants.servletContextPath = event.getServletContext().getRealPath(File.separator);
        LOG.info("inject common constants.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        for (ScheduledExecutorService service : serviceList) {
            if (null == service) continue;
            service.shutdownNow();
        }
        LOG.info("Timer task destroyed.");
    }

    private static void stopWeatherPlayout() {
        if (null != service4weatherPlayout) {
            LOG.info("stop Weather Playout");
            service4weatherPlayout.shutdown();
        }
        service4weatherPlayout = null;
    }

    public static void resetWeatherService() {
        if (null != service4weather) {
            service4weather.shutdown();
        }
        service4weather = null;
        ScheduleListener.weatherSchedule();
    }

    static {
        serviceList = new ArrayList<ScheduledExecutorService>();
    }
}

