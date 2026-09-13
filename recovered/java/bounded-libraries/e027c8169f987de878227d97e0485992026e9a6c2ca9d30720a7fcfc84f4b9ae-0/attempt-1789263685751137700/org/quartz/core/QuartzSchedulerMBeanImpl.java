/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.core;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import javax.management.ListenerNotFoundException;
import javax.management.MBeanNotificationInfo;
import javax.management.NotCompliantMBeanException;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import javax.management.NotificationEmitter;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.StandardMBean;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.TabularData;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.JobListener;
import org.quartz.SchedulerException;
import org.quartz.SchedulerListener;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.core.NullSampledStatisticsImpl;
import org.quartz.core.QuartzScheduler;
import org.quartz.core.SampledStatistics;
import org.quartz.core.SampledStatisticsImpl;
import org.quartz.core.jmx.JobDetailSupport;
import org.quartz.core.jmx.JobExecutionContextSupport;
import org.quartz.core.jmx.QuartzSchedulerMBean;
import org.quartz.core.jmx.TriggerSupport;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.triggers.AbstractTrigger;
import org.quartz.spi.OperableTrigger;

public class QuartzSchedulerMBeanImpl
extends StandardMBean
implements NotificationEmitter,
QuartzSchedulerMBean,
JobListener,
SchedulerListener {
    private static final MBeanNotificationInfo[] NOTIFICATION_INFO;
    private final QuartzScheduler scheduler;
    private boolean sampledStatisticsEnabled;
    private SampledStatistics sampledStatistics;
    private static final SampledStatistics NULL_SAMPLED_STATISTICS;
    protected final Emitter emitter = new Emitter();
    protected final AtomicLong sequenceNumber = new AtomicLong();

    protected QuartzSchedulerMBeanImpl(QuartzScheduler scheduler) throws NotCompliantMBeanException {
        super(QuartzSchedulerMBean.class);
        this.scheduler = scheduler;
        this.scheduler.addInternalJobListener(this);
        this.scheduler.addInternalSchedulerListener(this);
        this.sampledStatistics = NULL_SAMPLED_STATISTICS;
        this.sampledStatisticsEnabled = false;
    }

    @Override
    public TabularData getCurrentlyExecutingJobs() throws Exception {
        try {
            List<JobExecutionContext> currentlyExecutingJobs = this.scheduler.getCurrentlyExecutingJobs();
            return JobExecutionContextSupport.toTabularData(currentlyExecutingJobs);
        }
        catch (Exception e) {
            throw this.newPlainException(e);
        }
    }

    @Override
    public TabularData getAllJobDetails() throws Exception {
        try {
            ArrayList<JobDetail> detailList = new ArrayList<JobDetail>();
            for (String jobGroupName : this.scheduler.getJobGroupNames()) {
                for (JobKey jobKey : this.scheduler.getJobKeys(GroupMatcher.jobGroupEquals(jobGroupName))) {
                    detailList.add(this.scheduler.getJobDetail(jobKey));
                }
            }
            return JobDetailSupport.toTabularData(detailList.toArray(new JobDetail[detailList.size()]));
        }
        catch (Exception e) {
            throw this.newPlainException(e);
        }
    }

    @Override
    public List<CompositeData> getAllTriggers() throws Exception {
        try {
            ArrayList<Trigger> triggerList = new ArrayList<Trigger>();
            for (String triggerGroupName : this.scheduler.getTriggerGroupNames()) {
                for (TriggerKey triggerKey : this.scheduler.getTriggerKeys(GroupMatcher.triggerGroupEquals(triggerGroupName))) {
                    triggerList.add(this.scheduler.getTrigger(triggerKey));
                }
            }
            return TriggerSupport.toCompositeList(triggerList);
        }
        catch (Exception e) {
            throw this.newPlainException(e);
        }
    }

    @Override
    public void addJob(CompositeData jobDetail, boolean replace) throws Exception {
        try {
            this.scheduler.addJob(JobDetailSupport.newJobDetail(jobDetail), replace);
        }
        catch (Exception e) {
            throw this.newPlainException(e);
        }
    }

    private static void invokeSetter(Object target, String attribute, Object value) throws Exception {
        String setterName = "set" + Character.toUpperCase(attribute.charAt(0)) + attribute.substring(1);
        Class[] argTypes = new Class[]{value.getClass()};
        Method setter = QuartzSchedulerMBeanImpl.findMethod(target.getClass(), setterName, argTypes);
        if (setter == null) {
            throw new Exception("Unable to find setter for attribute '" + attribute + "' and value '" + value + "'");
        }
        setter.invoke(target, value);
    }

    private static Class<?> getWrapperIfPrimitive(Class<?> c) {
        Class result = c;
        try {
            Field f = c.getField("TYPE");
            f.setAccessible(true);
            result = (Class)f.get(null);
        }
        catch (Exception exception) {
            // empty catch block
        }
        return result;
    }

    private static Method findMethod(Class<?> targetType, String methodName, Class<?>[] argTypes) throws IntrospectionException {
        BeanInfo beanInfo = Introspector.getBeanInfo(targetType);
        if (beanInfo != null) {
            for (MethodDescriptor methodDesc : beanInfo.getMethodDescriptors()) {
                Method method = methodDesc.getMethod();
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (!methodName.equals(method.getName()) || argTypes.length != parameterTypes.length) continue;
                boolean matchedArgTypes = true;
                for (int i = 0; i < argTypes.length; ++i) {
                    if (QuartzSchedulerMBeanImpl.getWrapperIfPrimitive(argTypes[i]) == parameterTypes[i]) continue;
                    matchedArgTypes = false;
                    break;
                }
                if (!matchedArgTypes) continue;
                return method;
            }
        }
        return null;
    }

    @Override
    public void scheduleBasicJob(Map<String, Object> jobDetailInfo, Map<String, Object> triggerInfo) throws Exception {
        try {
            JobDetail jobDetail = JobDetailSupport.newJobDetail(jobDetailInfo);
            OperableTrigger trigger = TriggerSupport.newTrigger(triggerInfo);
            this.scheduler.deleteJob(jobDetail.getKey());
            this.scheduler.scheduleJob(jobDetail, trigger);
        }
        catch (ParseException pe) {
            throw pe;
        }
        catch (Exception e) {
            throw this.newPlainException(e);
        }
    }

    @Override
    public void scheduleJob(Map<String, Object> abstractJobInfo, Map<String, Object> abstractTriggerInfo) throws Exception {
        try {
            Object value;
            String key;
            String triggerClassName = (String)abstractTriggerInfo.remove("triggerClass");
            if (triggerClassName == null) {
                throw new IllegalArgumentException("No triggerClass specified");
            }
            Class<?> triggerClass = Class.forName(triggerClassName);
            Trigger trigger = (Trigger)triggerClass.newInstance();
            String jobDetailClassName = (String)abstractJobInfo.remove("jobDetailClass");
            if (jobDetailClassName == null) {
                throw new IllegalArgumentException("No jobDetailClass specified");
            }
            Class<?> jobDetailClass = Class.forName(jobDetailClassName);
            JobDetail jobDetail = (JobDetail)jobDetailClass.newInstance();
            String jobClassName = (String)abstractJobInfo.remove("jobClass");
            if (jobClassName == null) {
                throw new IllegalArgumentException("No jobClass specified");
            }
            Class<?> jobClass = Class.forName(jobClassName);
            abstractJobInfo.put("jobClass", jobClass);
            for (Map.Entry<String, Object> entry : abstractTriggerInfo.entrySet()) {
                key = entry.getKey();
                value = entry.getValue();
                if ("jobDataMap".equals(key)) {
                    value = new JobDataMap((Map)value);
                }
                QuartzSchedulerMBeanImpl.invokeSetter(trigger, key, value);
            }
            for (Map.Entry<String, Object> entry : abstractJobInfo.entrySet()) {
                key = entry.getKey();
                value = entry.getValue();
                if ("jobDataMap".equals(key)) {
                    value = new JobDataMap((Map)value);
                }
                QuartzSchedulerMBeanImpl.invokeSetter(jobDetail, key, value);
            }
            AbstractTrigger at = (AbstractTrigger)trigger;
            at.setKey(new TriggerKey(at.getName(), at.getGroup()));
            Date startDate = at.getStartTime();
            if (startDate == null || startDate.before(new Date())) {
                at.setStartTime(new Date());
            }
            this.scheduler.deleteJob(jobDetail.getKey());
            this.scheduler.scheduleJob(jobDetail, trigger);
        }
        catch (Exception e) {
            throw this.newPlainException(e);
        }
    }

    @Override
    public void scheduleJob(String jobName, String jobGroup, Map<String, Object> abstractTriggerInfo) throws Exception {
        try {
            JobKey jobKey = new JobKey(jobName, jobGroup);
            JobDetail jobDetail = this.scheduler.getJobDetail(jobKey);
            if (jobDetail == null) {
                throw new IllegalArgumentException("No such job '" + jobKey + "'");
            }
            String triggerClassName = (String)abstractTriggerInfo.remove("triggerClass");
            if (triggerClassName == null) {
                throw new IllegalArgumentException("No triggerClass specified");
            }
            Class<?> triggerClass = Class.forName(triggerClassName);
            Trigger trigger = (Trigger)triggerClass.newInstance();
            for (Map.Entry<String, Object> entry : abstractTriggerInfo.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if ("jobDataMap".equals(key)) {
                    value = new JobDataMap((Map)value);
                }
                QuartzSchedulerMBeanImpl.invokeSetter(trigger, key, value);
            }
            AbstractTrigger at = (AbstractTrigger)trigger;
            at.setKey(new TriggerKey(at.getName(), at.getGroup()));
            Date startDate = at.getStartTime();
            if (startDate == null || startDate.before(new Date())) {
                at.setStartTime(new Date());
            }
            this.scheduler.scheduleJob(trigger);
        }
        catch (Exception e) {
            throw this.newPlainException(e);
        }
    }

    @Override
    public void addJob(Map<String, Object> abstractJobInfo, boolean replace) throws Exception {
        try {
            String jobDetailClassName = (String)abstractJobInfo.remove("jobDetailClass");
            if (jobDetailClassName == null) {
                throw new IllegalArgumentException("No jobDetailClass specified");
            }
            Class<?> jobDetailClass = Class.forName(jobDetailClassName);
            JobDetail jobDetail = (JobDetail)jobDetailClass.newInstance();
            String jobClassName = (String)abstractJobInfo.remove("jobClass");
            if (jobClassName == null) {
                throw new IllegalArgumentException("No jobClass specified");
            }
            Class<?> jobClass = Class.forName(jobClassName);
            abstractJobInfo.put("jobClass", jobClass);
            for (Map.Entry<String, Object> entry : abstractJobInfo.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if ("jobDataMap".equals(key)) {
                    value = new JobDataMap((Map)value);
                }
                QuartzSchedulerMBeanImpl.invokeSetter(jobDetail, key, value);
            }
            this.scheduler.addJob(jobDetail, replace);
        }
        catch (Exception e) {
            throw this.newPlainException(e);
        }
    }

    private Exception newPlainException(Exception e) {
        String type = e.getClass().getName();
        if (type.startsWith("java.") || type.startsWith("javax.")) {
            return e;
        }
        Exception result = new Exception(e.getMessage());
        result.setStackTrace(e.getStackTrace());
        return result;
    }

    @Override
    public void deleteCalendar(String calendarName) throws Exception {
        try {
            this.scheduler.deleteCalendar(calendarName);
        }
        catch (Exception e) {
            throw this.newPlainException(e);
        }
    }

    @Override
    public boolean deleteJob(String jobName, String jobGroupName) throws Exception {
        try {
            return this.scheduler.deleteJob(JobKey.jobKey(jobName, jobGroupName));
        }
        catch (Exception e) {
            throw this.newPlainException(e);
        }
    }

    @Override
    public List<String> getCalendarNames() throws Exception {
        try {
            return this.scheduler.getCalendarNames();
        }
        catch (Exception e) {
            throw this.newPlainException(e);
        }
    }

    @Override
    public CompositeData getJobDetail(String jobName, String jobGroupName) throws Exception {
        try {
            JobDetail jobDetail = this.scheduler.getJobDetail(JobKey.jobKey(jobName, jobGroupName));
            return JobDetailSupport.toCompositeData(jobDetail);
        }
        catch (Exception e) {
            throw this.newPlainException(e);
        }
    }

    @Override
    public List<String> getJobGroupNames() throws Exception {
        try {
            return this.scheduler.getJobGroupNames();
        }
        catch (Exception e) {
            throw this.newPlainException(e);
        }
    }

    @Override
    public List<String> getJobNames(String groupName) throws Exception {
        try {
            ArrayList<String> jobNames = new ArrayList<String>();
            for (JobKey key : this.scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
                jobNames.add(key.getName());
            }
            return jobNames;
        }
        catch (Exception e) {
            throw this.newPlainException(e);
        }
    }

    @Override
    public String getJobStoreClassName() {
        return this.scheduler.getJobStoreClass().getName();
    }

    @Override
    public Set<String> getPausedTriggerGroups() throws Exception {
        try {
            return this.scheduler.getPausedTriggerGroups();
        }
        catch (Exception e) {
            throw this.newPlainException(e);
        }
    }

    @Override
    public CompositeData getTrigger(String name, String groupName) throws Exception {
        try {
            Trigger trigger = this.scheduler.getTrigger(TriggerKey.triggerKey(name, groupName));
            return TriggerSupport.toCompositeData(trigger);
        }
        catch (Exception e) {
            throw this.newPlainException(e);
        }
    }

    @Override
    public List<String> getTriggerGroupNames() throws Exception {
        try {
            return this.scheduler.getTriggerGroupNames();
        }
        catch (Exception e) {
            throw this.newPlainException(e);
        }
    }

    @Override
    public List<String> getTriggerNames(String groupName) throws Exception {
        try {
            ArrayList<String> triggerNames = new ArrayList<String>();
            for (TriggerKey key : this.scheduler.getTriggerKeys(GroupMatcher.triggerGroupEquals(groupName))) {
                triggerNames.add(key.getName());
            }
            return triggerNames;
        }
        catch (Exception e) {
            throw this.newPlainException(e);
        }
    }

    @Override
    public String getTriggerState(String triggerName, String triggerGroupName) throws Exception {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
            Trigger.TriggerState ts = this.scheduler.getTriggerState(triggerKey);
            return ts.name();
        }
        catch (Exception e) {
            throw this.newPlainException(e);
        }
    }

    @Override
    public List<CompositeData> getTriggersOfJob(String jobName, String jobGroupName) throws Exception {
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
            return TriggerSupport.toCompositeList(this.scheduler.getTriggersOfJob(jobKey));
        }
        catch (Exception e) {
            throw this.newPlainException(e);
        }
    }

    @Override
    public boolean interruptJob(String jobName, String jobGroupName) throws Exception {
        try {
            return this.scheduler.interrupt(JobKey.jobKey(jobName, jobGroupName));
        }
        catch (Exception e) {
            throw this.newPlainException(e);
        }
    }

    @Override
    public boolean interruptJob(String fireInstanceId) throws Exception {
        try {
            return this.scheduler.interrupt(fireInstanceId);
        }
        catch (Exception e) {
            throw this.newPlainException(e);
        }
    }

    @Override
    public Date scheduleJob(String jobName, String jobGroup, String triggerName, String triggerGroup) throws Exception {
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
            JobDetail jobDetail = this.scheduler.getJobDetail(jobKey);
            if (jobDetail == null) {
                throw new IllegalArgumentException("No such job: " + jobKey);
            }
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroup);
            Trigger trigger = this.scheduler.getTrigger(triggerKey);
            if (trigger == null) {
                throw new IllegalArgumentException("No such trigger: " + triggerKey);
            }
            return this.scheduler.scheduleJob(jobDetail, trigger);
        }
        catch (Exception e) {
            throw this.newPlainException(e);
        }
    }

    @Override
    public boolean unscheduleJob(String triggerName, String triggerGroup) throws Exception {
        try {
            return this.scheduler.unscheduleJob(TriggerKey.triggerKey(triggerName, triggerGroup));
        }
        catch (Exception e) {
            throw this.newPlainException(e);
        }
    }

    @Override
    public void clear() throws Exception {
        try {
            this.scheduler.clear();
        }
        catch (Exception e) {
            throw this.newPlainException(e);
        }
    }

    @Override
    public String getVersion() {
        return this.scheduler.getVersion();
    }

    @Override
    public boolean isShutdown() {
        return this.scheduler.isShutdown();
    }

    @Override
    public boolean isStarted() {
        return this.scheduler.isStarted();
    }

    @Override
    public void start() throws Exception {
        try {
            this.scheduler.start();
        }
        catch (Exception e) {
            throw this.newPlainException(e);
        }
    }

    @Override
    public void shutdown() {
        this.scheduler.shutdown();
    }

    @Override
    public void standby() {
        this.scheduler.standby();
    }

    @Override
    public boolean isStandbyMode() {
        return this.scheduler.isInStandbyMode();
    }

    @Override
    public String getSchedulerName() {
        return this.scheduler.getSchedulerName();
    }

    @Override
    public String getSchedulerInstanceId() {
        return this.scheduler.getSchedulerInstanceId();
    }

    @Override
    public String getThreadPoolClassName() {
        return this.scheduler.getThreadPoolClass().getName();
    }

    @Override
    public int getThreadPoolSize() {
        return this.scheduler.getThreadPoolSize();
    }

    @Override
    public void pauseJob(String jobName, String jobGroup) throws Exception {
        try {
            this.scheduler.pauseJob(JobKey.jobKey(jobName, jobGroup));
        }
        catch (Exception e) {
            throw this.newPlainException(e);
        }
    }

    public void pauseJobs(GroupMatcher<JobKey> matcher) throws Exception {
        try {
            this.scheduler.pauseJobs(matcher);
        }
        catch (Exception e) {
            throw this.newPlainException(e);
        }
    }

    @Override
    public void pauseJobGroup(String jobGroup) throws Exception {
        this.pauseJobs(GroupMatcher.groupEquals(jobGroup));
    }

    @Override
    public void pauseJobsStartingWith(String jobGroupPrefix) throws Exception {
        this.pauseJobs(GroupMatcher.groupStartsWith(jobGroupPrefix));
    }

    @Override
    public void pauseJobsEndingWith(String jobGroupSuffix) throws Exception {
        this.pauseJobs(GroupMatcher.groupEndsWith(jobGroupSuffix));
    }

    @Override
    public void pauseJobsContaining(String jobGroupToken) throws Exception {
        this.pauseJobs(GroupMatcher.groupContains(jobGroupToken));
    }

    @Override
    public void pauseJobsAll() throws Exception {
        this.pauseJobs(GroupMatcher.anyJobGroup());
    }

    @Override
    public void pauseAllTriggers() throws Exception {
        try {
            this.scheduler.pauseAll();
        }
        catch (Exception e) {
            throw this.newPlainException(e);
        }
    }

    private void pauseTriggers(GroupMatcher<TriggerKey> matcher) throws Exception {
        try {
            this.scheduler.pauseTriggers(matcher);
        }
        catch (Exception e) {
            throw this.newPlainException(e);
        }
    }

    @Override
    public void pauseTriggerGroup(String triggerGroup) throws Exception {
        this.pauseTriggers(GroupMatcher.groupEquals(triggerGroup));
    }

    @Override
    public void pauseTriggersStartingWith(String triggerGroupPrefix) throws Exception {
        this.pauseTriggers(GroupMatcher.groupStartsWith(triggerGroupPrefix));
    }

    @Override
    public void pauseTriggersEndingWith(String triggerGroupSuffix) throws Exception {
        this.pauseTriggers(GroupMatcher.groupEndsWith(triggerGroupSuffix));
    }

    @Override
    public void pauseTriggersContaining(String triggerGroupToken) throws Exception {
        this.pauseTriggers(GroupMatcher.groupContains(triggerGroupToken));
    }

    @Override
    public void pauseTriggersAll() throws Exception {
        this.pauseTriggers(GroupMatcher.anyTriggerGroup());
    }

    @Override
    public void pauseTrigger(String triggerName, String triggerGroup) throws Exception {
        try {
            this.scheduler.pauseTrigger(TriggerKey.triggerKey(triggerName, triggerGroup));
        }
        catch (Exception e) {
            throw this.newPlainException(e);
        }
    }

    @Override
    public void resumeAllTriggers() throws Exception {
        try {
            this.scheduler.resumeAll();
        }
        catch (Exception e) {
            throw this.newPlainException(e);
        }
    }

    @Override
    public void resumeJob(String jobName, String jobGroup) throws Exception {
        try {
            this.scheduler.resumeJob(JobKey.jobKey(jobName, jobGroup));
        }
        catch (Exception e) {
            throw this.newPlainException(e);
        }
    }

    public void resumeJobs(GroupMatcher<JobKey> matcher) throws Exception {
        try {
            this.scheduler.resumeJobs(matcher);
        }
        catch (Exception e) {
            throw this.newPlainException(e);
        }
    }

    @Override
    public void resumeJobGroup(String jobGroup) throws Exception {
        this.resumeJobs(GroupMatcher.groupEquals(jobGroup));
    }

    @Override
    public void resumeJobsStartingWith(String jobGroupPrefix) throws Exception {
        this.resumeJobs(GroupMatcher.groupStartsWith(jobGroupPrefix));
    }

    @Override
    public void resumeJobsEndingWith(String jobGroupSuffix) throws Exception {
        this.resumeJobs(GroupMatcher.groupEndsWith(jobGroupSuffix));
    }

    @Override
    public void resumeJobsContaining(String jobGroupToken) throws Exception {
        this.resumeJobs(GroupMatcher.groupContains(jobGroupToken));
    }

    @Override
    public void resumeJobsAll() throws Exception {
        this.resumeJobs(GroupMatcher.anyJobGroup());
    }

    @Override
    public void resumeTrigger(String triggerName, String triggerGroup) throws Exception {
        try {
            this.scheduler.resumeTrigger(TriggerKey.triggerKey(triggerName, triggerGroup));
        }
        catch (Exception e) {
            throw this.newPlainException(e);
        }
    }

    private void resumeTriggers(GroupMatcher<TriggerKey> matcher) throws Exception {
        try {
            this.scheduler.resumeTriggers(matcher);
        }
        catch (Exception e) {
            throw this.newPlainException(e);
        }
    }

    @Override
    public void resumeTriggerGroup(String triggerGroup) throws Exception {
        this.resumeTriggers(GroupMatcher.groupEquals(triggerGroup));
    }

    @Override
    public void resumeTriggersStartingWith(String triggerGroupPrefix) throws Exception {
        this.resumeTriggers(GroupMatcher.groupStartsWith(triggerGroupPrefix));
    }

    @Override
    public void resumeTriggersEndingWith(String triggerGroupSuffix) throws Exception {
        this.resumeTriggers(GroupMatcher.groupEndsWith(triggerGroupSuffix));
    }

    @Override
    public void resumeTriggersContaining(String triggerGroupToken) throws Exception {
        this.resumeTriggers(GroupMatcher.groupContains(triggerGroupToken));
    }

    @Override
    public void resumeTriggersAll() throws Exception {
        this.resumeTriggers(GroupMatcher.anyTriggerGroup());
    }

    @Override
    public void triggerJob(String jobName, String jobGroup, Map<String, String> jobDataMap) throws Exception {
        try {
            this.scheduler.triggerJob(JobKey.jobKey(jobName, jobGroup), new JobDataMap(jobDataMap));
        }
        catch (Exception e) {
            throw this.newPlainException(e);
        }
    }

    public void triggerJob(CompositeData trigger) throws Exception {
        try {
            this.scheduler.triggerJob(TriggerSupport.newTrigger(trigger));
        }
        catch (Exception e) {
            throw this.newPlainException(e);
        }
    }

    @Override
    public void jobAdded(JobDetail jobDetail) {
        this.sendNotification("jobAdded", JobDetailSupport.toCompositeData(jobDetail));
    }

    @Override
    public void jobDeleted(JobKey jobKey) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("jobName", jobKey.getName());
        map.put("jobGroup", jobKey.getGroup());
        this.sendNotification("jobDeleted", map);
    }

    @Override
    public void jobScheduled(Trigger trigger) {
        this.sendNotification("jobScheduled", TriggerSupport.toCompositeData(trigger));
    }

    @Override
    public void jobUnscheduled(TriggerKey triggerKey) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("triggerName", triggerKey.getName());
        map.put("triggerGroup", triggerKey.getGroup());
        this.sendNotification("jobUnscheduled", map);
    }

    @Override
    public void schedulingDataCleared() {
        this.sendNotification("schedulingDataCleared");
    }

    @Override
    public void jobPaused(JobKey jobKey) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("jobName", jobKey.getName());
        map.put("jobGroup", jobKey.getGroup());
        this.sendNotification("jobsPaused", map);
    }

    @Override
    public void jobsPaused(String jobGroup) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("jobName", null);
        map.put("jobGroup", jobGroup);
        this.sendNotification("jobsPaused", map);
    }

    @Override
    public void jobsResumed(String jobGroup) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("jobName", null);
        map.put("jobGroup", jobGroup);
        this.sendNotification("jobsResumed", map);
    }

    @Override
    public void jobResumed(JobKey jobKey) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("jobName", jobKey.getName());
        map.put("jobGroup", jobKey.getGroup());
        this.sendNotification("jobsResumed", map);
    }

    @Override
    public void schedulerError(String msg, SchedulerException cause) {
        this.sendNotification("schedulerError", cause.getMessage());
    }

    @Override
    public void schedulerStarted() {
        this.sendNotification("schedulerStarted");
    }

    @Override
    public void schedulerStarting() {
    }

    @Override
    public void schedulerInStandbyMode() {
        this.sendNotification("schedulerPaused");
    }

    @Override
    public void schedulerShutdown() {
        this.scheduler.removeInternalSchedulerListener(this);
        this.scheduler.removeInternalJobListener(this.getName());
        this.sendNotification("schedulerShutdown");
    }

    @Override
    public void schedulerShuttingdown() {
    }

    @Override
    public void triggerFinalized(Trigger trigger) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("triggerName", trigger.getKey().getName());
        map.put("triggerGroup", trigger.getKey().getGroup());
        this.sendNotification("triggerFinalized", map);
    }

    @Override
    public void triggersPaused(String triggerGroup) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("triggerName", null);
        map.put("triggerGroup", triggerGroup);
        this.sendNotification("triggersPaused", map);
    }

    @Override
    public void triggerPaused(TriggerKey triggerKey) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (triggerKey != null) {
            map.put("triggerName", triggerKey.getName());
            map.put("triggerGroup", triggerKey.getGroup());
        }
        this.sendNotification("triggersPaused", map);
    }

    @Override
    public void triggersResumed(String triggerGroup) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("triggerName", null);
        map.put("triggerGroup", triggerGroup);
        this.sendNotification("triggersResumed", map);
    }

    @Override
    public void triggerResumed(TriggerKey triggerKey) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (triggerKey != null) {
            map.put("triggerName", triggerKey.getName());
            map.put("triggerGroup", triggerKey.getGroup());
        }
        this.sendNotification("triggersResumed", map);
    }

    @Override
    public String getName() {
        return "QuartzSchedulerMBeanImpl.listener";
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        try {
            this.sendNotification("jobExecutionVetoed", JobExecutionContextSupport.toCompositeData(context));
        }
        catch (Exception e) {
            throw new RuntimeException(this.newPlainException(e));
        }
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        try {
            this.sendNotification("jobToBeExecuted", JobExecutionContextSupport.toCompositeData(context));
        }
        catch (Exception e) {
            throw new RuntimeException(this.newPlainException(e));
        }
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        try {
            this.sendNotification("jobWasExecuted", JobExecutionContextSupport.toCompositeData(context));
        }
        catch (Exception e) {
            throw new RuntimeException(this.newPlainException(e));
        }
    }

    public void sendNotification(String eventType) {
        this.sendNotification(eventType, null, null);
    }

    public void sendNotification(String eventType, Object data) {
        this.sendNotification(eventType, data, null);
    }

    public void sendNotification(String eventType, Object data, String msg) {
        Notification notif = new Notification(eventType, this, this.sequenceNumber.incrementAndGet(), System.currentTimeMillis(), msg);
        if (data != null) {
            notif.setUserData(data);
        }
        this.emitter.sendNotification(notif);
    }

    @Override
    public void addNotificationListener(NotificationListener notif, NotificationFilter filter, Object callBack) {
        this.emitter.addNotificationListener(notif, filter, callBack);
    }

    @Override
    public MBeanNotificationInfo[] getNotificationInfo() {
        return NOTIFICATION_INFO;
    }

    @Override
    public void removeNotificationListener(NotificationListener listener) throws ListenerNotFoundException {
        this.emitter.removeNotificationListener(listener);
    }

    @Override
    public void removeNotificationListener(NotificationListener notif, NotificationFilter filter, Object callBack) throws ListenerNotFoundException {
        this.emitter.removeNotificationListener(notif, filter, callBack);
    }

    @Override
    public synchronized boolean isSampledStatisticsEnabled() {
        return this.sampledStatisticsEnabled;
    }

    @Override
    public void setSampledStatisticsEnabled(boolean enabled) {
        if (enabled != this.sampledStatisticsEnabled) {
            this.sampledStatisticsEnabled = enabled;
            if (enabled) {
                this.sampledStatistics = new SampledStatisticsImpl(this.scheduler);
            } else {
                this.sampledStatistics.shutdown();
                this.sampledStatistics = NULL_SAMPLED_STATISTICS;
            }
            this.sendNotification("sampledStatisticsEnabled", enabled);
        }
    }

    @Override
    public long getJobsCompletedMostRecentSample() {
        return this.sampledStatistics.getJobsCompletedMostRecentSample();
    }

    @Override
    public long getJobsExecutedMostRecentSample() {
        return this.sampledStatistics.getJobsExecutingMostRecentSample();
    }

    @Override
    public long getJobsScheduledMostRecentSample() {
        return this.sampledStatistics.getJobsScheduledMostRecentSample();
    }

    @Override
    public Map<String, Long> getPerformanceMetrics() {
        HashMap<String, Long> result = new HashMap<String, Long>();
        result.put("JobsCompleted", this.getJobsCompletedMostRecentSample());
        result.put("JobsExecuted", this.getJobsExecutedMostRecentSample());
        result.put("JobsScheduled", this.getJobsScheduledMostRecentSample());
        return result;
    }

    static {
        NULL_SAMPLED_STATISTICS = new NullSampledStatisticsImpl();
        String[] notifTypes = new String[]{"schedulerStarted", "schedulerPaused", "schedulerShutdown"};
        String name = Notification.class.getName();
        String description = "QuartzScheduler JMX Event";
        NOTIFICATION_INFO = new MBeanNotificationInfo[]{new MBeanNotificationInfo(notifTypes, name, "QuartzScheduler JMX Event")};
    }

    private class Emitter
    extends NotificationBroadcasterSupport {
        private Emitter() {
        }

        @Override
        public MBeanNotificationInfo[] getNotificationInfo() {
            return QuartzSchedulerMBeanImpl.this.getNotificationInfo();
        }
    }
}

