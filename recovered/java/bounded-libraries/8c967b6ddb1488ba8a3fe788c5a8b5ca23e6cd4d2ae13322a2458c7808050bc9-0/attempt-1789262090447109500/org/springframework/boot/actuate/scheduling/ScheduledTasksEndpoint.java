/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.scheduling.Trigger
 *  org.springframework.scheduling.config.CronTask
 *  org.springframework.scheduling.config.FixedDelayTask
 *  org.springframework.scheduling.config.FixedRateTask
 *  org.springframework.scheduling.config.IntervalTask
 *  org.springframework.scheduling.config.ScheduledTask
 *  org.springframework.scheduling.config.ScheduledTaskHolder
 *  org.springframework.scheduling.config.Task
 *  org.springframework.scheduling.config.TriggerTask
 *  org.springframework.scheduling.support.CronTrigger
 *  org.springframework.scheduling.support.PeriodicTrigger
 *  org.springframework.scheduling.support.ScheduledMethodRunnable
 */
package org.springframework.boot.actuate.scheduling;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.FixedDelayTask;
import org.springframework.scheduling.config.FixedRateTask;
import org.springframework.scheduling.config.IntervalTask;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.scheduling.config.ScheduledTaskHolder;
import org.springframework.scheduling.config.Task;
import org.springframework.scheduling.config.TriggerTask;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.scheduling.support.ScheduledMethodRunnable;

@Endpoint(id="scheduledtasks")
public class ScheduledTasksEndpoint {
    private final Collection<ScheduledTaskHolder> scheduledTaskHolders;

    public ScheduledTasksEndpoint(Collection<ScheduledTaskHolder> scheduledTaskHolders) {
        this.scheduledTaskHolders = scheduledTaskHolders;
    }

    @ReadOperation
    public ScheduledTasksReport scheduledTasks() {
        Map<TaskType, List<TaskDescription>> descriptionsByType = this.scheduledTaskHolders.stream().flatMap(holder -> holder.getScheduledTasks().stream()).map(ScheduledTask::getTask).map(x$0 -> TaskDescription.of(x$0)).filter(Objects::nonNull).collect(Collectors.groupingBy(rec$ -> ((TaskDescription)rec$).getType()));
        return new ScheduledTasksReport(descriptionsByType);
    }

    private static enum TaskType {
        CRON,
        CUSTOM_TRIGGER,
        FIXED_DELAY,
        FIXED_RATE;

    }

    public static final class RunnableDescription {
        private final String target;

        private RunnableDescription(Runnable runnable) {
            if (runnable instanceof ScheduledMethodRunnable) {
                Method method = ((ScheduledMethodRunnable)runnable).getMethod();
                this.target = method.getDeclaringClass().getName() + "." + method.getName();
            } else {
                this.target = runnable.getClass().getName();
            }
        }

        public String getTarget() {
            return this.target;
        }
    }

    public static final class CustomTriggerTaskDescription
    extends TaskDescription {
        private final String trigger;

        private CustomTriggerTaskDescription(TriggerTask task) {
            super(TaskType.CUSTOM_TRIGGER, task.getRunnable());
            this.trigger = task.getTrigger().toString();
        }

        public String getTrigger() {
            return this.trigger;
        }
    }

    public static final class CronTaskDescription
    extends TaskDescription {
        private final String expression;

        private CronTaskDescription(CronTask task) {
            super(TaskType.CRON, task.getRunnable());
            this.expression = task.getExpression();
        }

        private CronTaskDescription(TriggerTask task, CronTrigger trigger) {
            super(TaskType.CRON, task.getRunnable());
            this.expression = trigger.getExpression();
        }

        public String getExpression() {
            return this.expression;
        }
    }

    public static final class FixedRateTaskDescription
    extends IntervalTaskDescription {
        private FixedRateTaskDescription(FixedRateTask task) {
            super(TaskType.FIXED_RATE, (IntervalTask)task);
        }

        private FixedRateTaskDescription(TriggerTask task, PeriodicTrigger trigger) {
            super(TaskType.FIXED_RATE, task, trigger);
        }
    }

    public static final class FixedDelayTaskDescription
    extends IntervalTaskDescription {
        private FixedDelayTaskDescription(FixedDelayTask task) {
            super(TaskType.FIXED_DELAY, (IntervalTask)task);
        }

        private FixedDelayTaskDescription(TriggerTask task, PeriodicTrigger trigger) {
            super(TaskType.FIXED_DELAY, task, trigger);
        }
    }

    public static class IntervalTaskDescription
    extends TaskDescription {
        private final long initialDelay;
        private final long interval;

        protected IntervalTaskDescription(TaskType type, IntervalTask task) {
            super(type, task.getRunnable());
            this.initialDelay = task.getInitialDelay();
            this.interval = task.getInterval();
        }

        protected IntervalTaskDescription(TaskType type, TriggerTask task, PeriodicTrigger trigger) {
            super(type, task.getRunnable());
            this.initialDelay = trigger.getInitialDelay();
            this.interval = trigger.getPeriod();
        }

        public long getInitialDelay() {
            return this.initialDelay;
        }

        public long getInterval() {
            return this.interval;
        }
    }

    public static abstract class TaskDescription {
        private static final Map<Class<? extends Task>, Function<Task, TaskDescription>> DESCRIBERS = new LinkedHashMap<Class<? extends Task>, Function<Task, TaskDescription>>();
        private final TaskType type;
        private final RunnableDescription runnable;

        private static TaskDescription of(Task task) {
            return DESCRIBERS.entrySet().stream().filter(entry -> ((Class)entry.getKey()).isInstance(task)).map(entry -> (TaskDescription)((Function)entry.getValue()).apply(task)).findFirst().orElse(null);
        }

        private static TaskDescription describeTriggerTask(TriggerTask triggerTask) {
            Trigger trigger = triggerTask.getTrigger();
            if (trigger instanceof CronTrigger) {
                return new CronTaskDescription(triggerTask, (CronTrigger)trigger);
            }
            if (trigger instanceof PeriodicTrigger) {
                PeriodicTrigger periodicTrigger = (PeriodicTrigger)trigger;
                if (periodicTrigger.isFixedRate()) {
                    return new FixedRateTaskDescription(triggerTask, periodicTrigger);
                }
                return new FixedDelayTaskDescription(triggerTask, periodicTrigger);
            }
            return new CustomTriggerTaskDescription(triggerTask);
        }

        protected TaskDescription(TaskType type, Runnable runnable) {
            this.type = type;
            this.runnable = new RunnableDescription(runnable);
        }

        private TaskType getType() {
            return this.type;
        }

        public final RunnableDescription getRunnable() {
            return this.runnable;
        }

        static {
            DESCRIBERS.put(FixedRateTask.class, task -> new FixedRateTaskDescription((FixedRateTask)task));
            DESCRIBERS.put(FixedDelayTask.class, task -> new FixedDelayTaskDescription((FixedDelayTask)task));
            DESCRIBERS.put(CronTask.class, task -> new CronTaskDescription((CronTask)task));
            DESCRIBERS.put(TriggerTask.class, task -> TaskDescription.describeTriggerTask((TriggerTask)task));
        }
    }

    public static final class ScheduledTasksReport {
        private final List<TaskDescription> cron;
        private final List<TaskDescription> fixedDelay;
        private final List<TaskDescription> fixedRate;
        private final List<TaskDescription> custom;

        private ScheduledTasksReport(Map<TaskType, List<TaskDescription>> descriptionsByType) {
            this.cron = descriptionsByType.getOrDefault((Object)TaskType.CRON, Collections.emptyList());
            this.fixedDelay = descriptionsByType.getOrDefault((Object)TaskType.FIXED_DELAY, Collections.emptyList());
            this.fixedRate = descriptionsByType.getOrDefault((Object)TaskType.FIXED_RATE, Collections.emptyList());
            this.custom = descriptionsByType.getOrDefault((Object)TaskType.CUSTOM_TRIGGER, Collections.emptyList());
        }

        public List<TaskDescription> getCron() {
            return this.cron;
        }

        public List<TaskDescription> getFixedDelay() {
            return this.fixedDelay;
        }

        public List<TaskDescription> getFixedRate() {
            return this.fixedRate;
        }

        public List<TaskDescription> getCustom() {
            return this.custom;
        }
    }
}

