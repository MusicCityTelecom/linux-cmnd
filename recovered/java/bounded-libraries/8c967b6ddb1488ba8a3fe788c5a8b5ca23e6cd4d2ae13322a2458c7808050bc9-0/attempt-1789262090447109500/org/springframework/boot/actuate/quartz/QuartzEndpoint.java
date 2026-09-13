/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.quartz.CalendarIntervalTrigger
 *  org.quartz.CronTrigger
 *  org.quartz.DailyTimeIntervalTrigger
 *  org.quartz.DateBuilder$IntervalUnit
 *  org.quartz.JobDataMap
 *  org.quartz.JobDetail
 *  org.quartz.JobKey
 *  org.quartz.Scheduler
 *  org.quartz.SchedulerException
 *  org.quartz.SimpleTrigger
 *  org.quartz.TimeOfDay
 *  org.quartz.Trigger
 *  org.quartz.Trigger$TriggerState
 *  org.quartz.TriggerKey
 *  org.quartz.impl.matchers.GroupMatcher
 *  org.springframework.util.Assert
 */
package org.springframework.boot.actuate.quartz;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.quartz.CalendarIntervalTrigger;
import org.quartz.CronTrigger;
import org.quartz.DailyTimeIntervalTrigger;
import org.quartz.DateBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.TimeOfDay;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.boot.actuate.endpoint.Sanitizer;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.util.Assert;

@Endpoint(id="quartz")
public class QuartzEndpoint {
    private static final Comparator<Trigger> TRIGGER_COMPARATOR = Comparator.comparing(Trigger::getNextFireTime, Comparator.nullsLast(Comparator.naturalOrder())).thenComparing(Comparator.comparingInt(Trigger::getPriority).reversed());
    private final Scheduler scheduler;
    private final Sanitizer sanitizer;

    public QuartzEndpoint(Scheduler scheduler) {
        this(scheduler, new Sanitizer());
    }

    public QuartzEndpoint(Scheduler scheduler, Sanitizer sanitizer) {
        Assert.notNull((Object)scheduler, (String)"Scheduler must not be null");
        Assert.notNull((Object)sanitizer, (String)"Sanitizer must not be null");
        this.scheduler = scheduler;
        this.sanitizer = sanitizer;
    }

    @ReadOperation
    public QuartzReport quartzReport() throws SchedulerException {
        return new QuartzReport(new GroupNames(this.scheduler.getJobGroupNames()), new GroupNames(this.scheduler.getTriggerGroupNames()));
    }

    public QuartzGroups quartzJobGroups() throws SchedulerException {
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();
        for (String groupName : this.scheduler.getJobGroupNames()) {
            List jobs = this.scheduler.getJobKeys(GroupMatcher.jobGroupEquals((String)groupName)).stream().map(key -> key.getName()).collect(Collectors.toList());
            result.put(groupName, Collections.singletonMap("jobs", jobs));
        }
        return new QuartzGroups(result);
    }

    public QuartzGroups quartzTriggerGroups() throws SchedulerException {
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();
        Set pausedTriggerGroups = this.scheduler.getPausedTriggerGroups();
        for (String groupName : this.scheduler.getTriggerGroupNames()) {
            LinkedHashMap<String, Object> groupDetails = new LinkedHashMap<String, Object>();
            groupDetails.put("paused", pausedTriggerGroups.contains(groupName));
            groupDetails.put("triggers", this.scheduler.getTriggerKeys(GroupMatcher.triggerGroupEquals((String)groupName)).stream().map(key -> key.getName()).collect(Collectors.toList()));
            result.put(groupName, groupDetails);
        }
        return new QuartzGroups(result);
    }

    public QuartzJobGroupSummary quartzJobGroupSummary(String group) throws SchedulerException {
        List<JobDetail> jobs = this.findJobsByGroup(group);
        if (jobs.isEmpty() && !this.scheduler.getJobGroupNames().contains(group)) {
            return null;
        }
        LinkedHashMap<String, QuartzJobSummary> result = new LinkedHashMap<String, QuartzJobSummary>();
        for (JobDetail job : jobs) {
            result.put(job.getKey().getName(), QuartzJobSummary.of(job));
        }
        return new QuartzJobGroupSummary(group, result);
    }

    private List<JobDetail> findJobsByGroup(String group) throws SchedulerException {
        ArrayList<JobDetail> jobs = new ArrayList<JobDetail>();
        Set jobKeys = this.scheduler.getJobKeys(GroupMatcher.jobGroupEquals((String)group));
        for (JobKey jobKey : jobKeys) {
            jobs.add(this.scheduler.getJobDetail(jobKey));
        }
        return jobs;
    }

    public QuartzTriggerGroupSummary quartzTriggerGroupSummary(String group) throws SchedulerException {
        List<Trigger> triggers = this.findTriggersByGroup(group);
        if (triggers.isEmpty() && !this.scheduler.getTriggerGroupNames().contains(group)) {
            return null;
        }
        LinkedHashMap result = new LinkedHashMap();
        triggers.forEach(trigger -> {
            TriggerDescription triggerDescription = TriggerDescription.of(trigger);
            Map triggerTypes = result.computeIfAbsent(triggerDescription.getType(), key -> new LinkedHashMap());
            triggerTypes.put(trigger.getKey().getName(), triggerDescription.buildSummary(true));
        });
        boolean paused = this.scheduler.getPausedTriggerGroups().contains(group);
        return new QuartzTriggerGroupSummary(group, paused, result);
    }

    private List<Trigger> findTriggersByGroup(String group) throws SchedulerException {
        ArrayList<Trigger> triggers = new ArrayList<Trigger>();
        Set triggerKeys = this.scheduler.getTriggerKeys(GroupMatcher.triggerGroupEquals((String)group));
        for (TriggerKey triggerKey : triggerKeys) {
            triggers.add(this.scheduler.getTrigger(triggerKey));
        }
        return triggers;
    }

    public QuartzJobDetails quartzJob(String groupName, String jobName) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey((String)jobName, (String)groupName);
        JobDetail jobDetail = this.scheduler.getJobDetail(jobKey);
        if (jobDetail != null) {
            List triggers = this.scheduler.getTriggersOfJob(jobKey);
            return new QuartzJobDetails(jobDetail.getKey().getGroup(), jobDetail.getKey().getName(), jobDetail.getDescription(), jobDetail.getJobClass().getName(), jobDetail.isDurable(), jobDetail.requestsRecovery(), this.sanitizeJobDataMap(jobDetail.getJobDataMap()), QuartzEndpoint.extractTriggersSummary(triggers));
        }
        return null;
    }

    private static List<Map<String, Object>> extractTriggersSummary(List<? extends Trigger> triggers) {
        ArrayList<? extends Trigger> triggersToSort = new ArrayList<Trigger>(triggers);
        triggersToSort.sort(TRIGGER_COMPARATOR);
        ArrayList<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        triggersToSort.forEach(trigger -> {
            LinkedHashMap<String, Object> triggerSummary = new LinkedHashMap<String, Object>();
            triggerSummary.put("group", trigger.getKey().getGroup());
            triggerSummary.put("name", trigger.getKey().getName());
            triggerSummary.putAll(TriggerDescription.of(trigger).buildSummary(false));
            result.add(triggerSummary);
        });
        return result;
    }

    public Map<String, Object> quartzTrigger(String groupName, String triggerName) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey((String)triggerName, (String)groupName);
        Trigger trigger = this.scheduler.getTrigger(triggerKey);
        return trigger != null ? TriggerDescription.of(trigger).buildDetails(this.scheduler.getTriggerState(triggerKey), this.sanitizeJobDataMap(trigger.getJobDataMap())) : null;
    }

    private static Duration getIntervalDuration(long amount, DateBuilder.IntervalUnit unit) {
        return QuartzEndpoint.temporalUnit(unit).getDuration().multipliedBy(amount);
    }

    private static LocalTime getLocalTime(TimeOfDay timeOfDay) {
        return timeOfDay != null ? LocalTime.of(timeOfDay.getHour(), timeOfDay.getMinute(), timeOfDay.getSecond()) : null;
    }

    private Map<String, Object> sanitizeJobDataMap(JobDataMap dataMap) {
        if (dataMap != null) {
            LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>(dataMap.getWrappedMap());
            map.replaceAll(this.sanitizer::sanitize);
            return map;
        }
        return null;
    }

    private static TemporalUnit temporalUnit(DateBuilder.IntervalUnit unit) {
        switch (unit) {
            case DAY: {
                return ChronoUnit.DAYS;
            }
            case HOUR: {
                return ChronoUnit.HOURS;
            }
            case MINUTE: {
                return ChronoUnit.MINUTES;
            }
            case MONTH: {
                return ChronoUnit.MONTHS;
            }
            case SECOND: {
                return ChronoUnit.SECONDS;
            }
            case MILLISECOND: {
                return ChronoUnit.MILLIS;
            }
            case WEEK: {
                return ChronoUnit.WEEKS;
            }
            case YEAR: {
                return ChronoUnit.YEARS;
            }
        }
        throw new IllegalArgumentException("Unknown IntervalUnit");
    }

    public static final class CustomTriggerDescription
    extends TriggerDescription {
        public CustomTriggerDescription(Trigger trigger) {
            super(trigger, TriggerType.CUSTOM_TRIGGER);
        }

        @Override
        protected void appendSummary(Map<String, Object> content) {
            content.put("trigger", this.getTrigger().toString());
        }

        @Override
        protected void appendDetails(Map<String, Object> content) {
            this.appendSummary(content);
        }
    }

    public static final class CalendarIntervalTriggerDescription
    extends TriggerDescription {
        private final CalendarIntervalTrigger trigger;

        public CalendarIntervalTriggerDescription(CalendarIntervalTrigger trigger) {
            super((Trigger)trigger, TriggerType.CALENDAR_INTERVAL);
            this.trigger = trigger;
        }

        @Override
        protected void appendSummary(Map<String, Object> content) {
            content.put("interval", QuartzEndpoint.getIntervalDuration(this.trigger.getRepeatInterval(), this.trigger.getRepeatIntervalUnit()).toMillis());
            this.putIfNoNull(content, "timeZone", this.trigger.getTimeZone());
        }

        @Override
        protected void appendDetails(Map<String, Object> content) {
            this.appendSummary(content);
            content.put("timesTriggered", this.trigger.getTimesTriggered());
            content.put("preserveHourOfDayAcrossDaylightSavings", this.trigger.isPreserveHourOfDayAcrossDaylightSavings());
            content.put("skipDayIfHourDoesNotExist", this.trigger.isSkipDayIfHourDoesNotExist());
        }
    }

    public static final class DailyTimeIntervalTriggerDescription
    extends TriggerDescription {
        private final DailyTimeIntervalTrigger trigger;

        public DailyTimeIntervalTriggerDescription(DailyTimeIntervalTrigger trigger) {
            super((Trigger)trigger, TriggerType.DAILY_INTERVAL);
            this.trigger = trigger;
        }

        @Override
        protected void appendSummary(Map<String, Object> content) {
            content.put("interval", QuartzEndpoint.getIntervalDuration(this.trigger.getRepeatInterval(), this.trigger.getRepeatIntervalUnit()).toMillis());
            this.putIfNoNull(content, "daysOfWeek", this.trigger.getDaysOfWeek());
            this.putIfNoNull(content, "startTimeOfDay", QuartzEndpoint.getLocalTime(this.trigger.getStartTimeOfDay()));
            this.putIfNoNull(content, "endTimeOfDay", QuartzEndpoint.getLocalTime(this.trigger.getEndTimeOfDay()));
        }

        @Override
        protected void appendDetails(Map<String, Object> content) {
            this.appendSummary(content);
            content.put("repeatCount", this.trigger.getRepeatCount());
            content.put("timesTriggered", this.trigger.getTimesTriggered());
        }
    }

    public static final class SimpleTriggerDescription
    extends TriggerDescription {
        private final SimpleTrigger trigger;

        public SimpleTriggerDescription(SimpleTrigger trigger) {
            super((Trigger)trigger, TriggerType.SIMPLE);
            this.trigger = trigger;
        }

        @Override
        protected void appendSummary(Map<String, Object> content) {
            content.put("interval", this.trigger.getRepeatInterval());
        }

        @Override
        protected void appendDetails(Map<String, Object> content) {
            this.appendSummary(content);
            content.put("repeatCount", this.trigger.getRepeatCount());
            content.put("timesTriggered", this.trigger.getTimesTriggered());
        }
    }

    public static final class CronTriggerDescription
    extends TriggerDescription {
        private final CronTrigger trigger;

        public CronTriggerDescription(CronTrigger trigger) {
            super((Trigger)trigger, TriggerType.CRON);
            this.trigger = trigger;
        }

        @Override
        protected void appendSummary(Map<String, Object> content) {
            content.put("expression", this.trigger.getCronExpression());
            this.putIfNoNull(content, "timeZone", this.trigger.getTimeZone());
        }

        @Override
        protected void appendDetails(Map<String, Object> content) {
            this.appendSummary(content);
        }
    }

    public static abstract class TriggerDescription {
        private static final Map<Class<? extends Trigger>, Function<Trigger, TriggerDescription>> DESCRIBERS = new LinkedHashMap<Class<? extends Trigger>, Function<Trigger, TriggerDescription>>();
        private final Trigger trigger;
        private final TriggerType type;

        private static TriggerDescription of(Trigger trigger) {
            return DESCRIBERS.entrySet().stream().filter(entry -> ((Class)entry.getKey()).isInstance(trigger)).map(entry -> (TriggerDescription)((Function)entry.getValue()).apply(trigger)).findFirst().orElse(new CustomTriggerDescription(trigger));
        }

        protected TriggerDescription(Trigger trigger, TriggerType type) {
            this.trigger = trigger;
            this.type = type;
        }

        public Map<String, Object> buildSummary(boolean addTriggerSpecificSummary) {
            LinkedHashMap<String, Object> summary = new LinkedHashMap<String, Object>();
            this.putIfNoNull(summary, "previousFireTime", this.trigger.getPreviousFireTime());
            this.putIfNoNull(summary, "nextFireTime", this.trigger.getNextFireTime());
            summary.put("priority", this.trigger.getPriority());
            if (addTriggerSpecificSummary) {
                this.appendSummary(summary);
            }
            return summary;
        }

        protected abstract void appendSummary(Map<String, Object> var1);

        public Map<String, Object> buildDetails(Trigger.TriggerState triggerState, Map<String, Object> sanitizedDataMap) {
            LinkedHashMap<String, Object> details = new LinkedHashMap<String, Object>();
            details.put("group", this.trigger.getKey().getGroup());
            details.put("name", this.trigger.getKey().getName());
            this.putIfNoNull(details, "description", this.trigger.getDescription());
            details.put("state", triggerState);
            details.put("type", this.getType().getId());
            this.putIfNoNull(details, "calendarName", this.trigger.getCalendarName());
            this.putIfNoNull(details, "startTime", this.trigger.getStartTime());
            this.putIfNoNull(details, "endTime", this.trigger.getEndTime());
            this.putIfNoNull(details, "previousFireTime", this.trigger.getPreviousFireTime());
            this.putIfNoNull(details, "nextFireTime", this.trigger.getNextFireTime());
            this.putIfNoNull(details, "priority", this.trigger.getPriority());
            this.putIfNoNull(details, "finalFireTime", this.trigger.getFinalFireTime());
            this.putIfNoNull(details, "data", sanitizedDataMap);
            LinkedHashMap<String, Object> typeDetails = new LinkedHashMap<String, Object>();
            this.appendDetails(typeDetails);
            details.put(this.getType().getId(), typeDetails);
            return details;
        }

        protected abstract void appendDetails(Map<String, Object> var1);

        protected void putIfNoNull(Map<String, Object> content, String key, Object value) {
            if (value != null) {
                content.put(key, value);
            }
        }

        protected Trigger getTrigger() {
            return this.trigger;
        }

        protected TriggerType getType() {
            return this.type;
        }

        static {
            DESCRIBERS.put(CronTrigger.class, trigger -> new CronTriggerDescription((CronTrigger)trigger));
            DESCRIBERS.put(SimpleTrigger.class, trigger -> new SimpleTriggerDescription((SimpleTrigger)trigger));
            DESCRIBERS.put(DailyTimeIntervalTrigger.class, trigger -> new DailyTimeIntervalTriggerDescription((DailyTimeIntervalTrigger)trigger));
            DESCRIBERS.put(CalendarIntervalTrigger.class, trigger -> new CalendarIntervalTriggerDescription((CalendarIntervalTrigger)trigger));
        }
    }

    private static enum TriggerType {
        CRON("cron"),
        CUSTOM_TRIGGER("custom"),
        CALENDAR_INTERVAL("calendarInterval"),
        DAILY_INTERVAL("dailyTimeInterval"),
        SIMPLE("simple");

        private final String id;

        private TriggerType(String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }
    }

    public static final class QuartzTriggerGroupSummary {
        private final String group;
        private final boolean paused;
        private final Triggers triggers;

        private QuartzTriggerGroupSummary(String group, boolean paused, Map<TriggerType, Map<String, Object>> descriptionsByType) {
            this.group = group;
            this.paused = paused;
            this.triggers = new Triggers(descriptionsByType);
        }

        public String getGroup() {
            return this.group;
        }

        public boolean isPaused() {
            return this.paused;
        }

        public Triggers getTriggers() {
            return this.triggers;
        }

        public static final class Triggers {
            private final Map<String, Object> cron;
            private final Map<String, Object> simple;
            private final Map<String, Object> dailyTimeInterval;
            private final Map<String, Object> calendarInterval;
            private final Map<String, Object> custom;

            private Triggers(Map<TriggerType, Map<String, Object>> descriptionsByType) {
                this.cron = descriptionsByType.getOrDefault((Object)TriggerType.CRON, Collections.emptyMap());
                this.dailyTimeInterval = descriptionsByType.getOrDefault((Object)TriggerType.DAILY_INTERVAL, Collections.emptyMap());
                this.calendarInterval = descriptionsByType.getOrDefault((Object)TriggerType.CALENDAR_INTERVAL, Collections.emptyMap());
                this.simple = descriptionsByType.getOrDefault((Object)TriggerType.SIMPLE, Collections.emptyMap());
                this.custom = descriptionsByType.getOrDefault((Object)TriggerType.CUSTOM_TRIGGER, Collections.emptyMap());
            }

            public Map<String, Object> getCron() {
                return this.cron;
            }

            public Map<String, Object> getSimple() {
                return this.simple;
            }

            public Map<String, Object> getDailyTimeInterval() {
                return this.dailyTimeInterval;
            }

            public Map<String, Object> getCalendarInterval() {
                return this.calendarInterval;
            }

            public Map<String, Object> getCustom() {
                return this.custom;
            }
        }
    }

    public static final class QuartzJobDetails {
        private final String group;
        private final String name;
        private final String description;
        private final String className;
        private final boolean durable;
        private final boolean requestRecovery;
        private final Map<String, Object> data;
        private final List<Map<String, Object>> triggers;

        QuartzJobDetails(String group, String name, String description, String className, boolean durable, boolean requestRecovery, Map<String, Object> data, List<Map<String, Object>> triggers) {
            this.group = group;
            this.name = name;
            this.description = description;
            this.className = className;
            this.durable = durable;
            this.requestRecovery = requestRecovery;
            this.data = data;
            this.triggers = triggers;
        }

        public String getGroup() {
            return this.group;
        }

        public String getName() {
            return this.name;
        }

        public String getDescription() {
            return this.description;
        }

        public String getClassName() {
            return this.className;
        }

        public boolean isDurable() {
            return this.durable;
        }

        public boolean isRequestRecovery() {
            return this.requestRecovery;
        }

        public Map<String, Object> getData() {
            return this.data;
        }

        public List<Map<String, Object>> getTriggers() {
            return this.triggers;
        }
    }

    public static final class QuartzJobSummary {
        private final String className;

        private QuartzJobSummary(JobDetail job) {
            this.className = job.getJobClass().getName();
        }

        private static QuartzJobSummary of(JobDetail job) {
            return new QuartzJobSummary(job);
        }

        public String getClassName() {
            return this.className;
        }
    }

    public static final class QuartzJobGroupSummary {
        private final String group;
        private final Map<String, QuartzJobSummary> jobs;

        private QuartzJobGroupSummary(String group, Map<String, QuartzJobSummary> jobs) {
            this.group = group;
            this.jobs = jobs;
        }

        public String getGroup() {
            return this.group;
        }

        public Map<String, QuartzJobSummary> getJobs() {
            return this.jobs;
        }
    }

    public static class QuartzGroups {
        private final Map<String, Object> groups;

        public QuartzGroups(Map<String, Object> groups) {
            this.groups = groups;
        }

        public Map<String, Object> getGroups() {
            return this.groups;
        }
    }

    public static class GroupNames {
        private final Set<String> groups;

        public GroupNames(List<String> groups) {
            this.groups = new LinkedHashSet<String>(groups);
        }

        public Set<String> getGroups() {
            return this.groups;
        }
    }

    public static final class QuartzReport {
        private final GroupNames jobs;
        private final GroupNames triggers;

        QuartzReport(GroupNames jobs, GroupNames triggers) {
            this.jobs = jobs;
            this.triggers = triggers;
        }

        public GroupNames getJobs() {
            return this.jobs;
        }

        public GroupNames getTriggers() {
            return this.triggers;
        }
    }
}

