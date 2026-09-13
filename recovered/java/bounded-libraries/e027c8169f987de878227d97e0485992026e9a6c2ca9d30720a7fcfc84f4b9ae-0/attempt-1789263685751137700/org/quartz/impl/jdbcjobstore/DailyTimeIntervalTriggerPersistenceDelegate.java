/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.impl.jdbcjobstore;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.quartz.DailyTimeIntervalScheduleBuilder;
import org.quartz.DailyTimeIntervalTrigger;
import org.quartz.DateBuilder;
import org.quartz.TimeOfDay;
import org.quartz.impl.jdbcjobstore.SimplePropertiesTriggerPersistenceDelegateSupport;
import org.quartz.impl.jdbcjobstore.SimplePropertiesTriggerProperties;
import org.quartz.impl.jdbcjobstore.TriggerPersistenceDelegate;
import org.quartz.impl.triggers.DailyTimeIntervalTriggerImpl;
import org.quartz.spi.OperableTrigger;

public class DailyTimeIntervalTriggerPersistenceDelegate
extends SimplePropertiesTriggerPersistenceDelegateSupport {
    @Override
    public boolean canHandleTriggerType(OperableTrigger trigger) {
        return trigger instanceof DailyTimeIntervalTrigger && !((DailyTimeIntervalTriggerImpl)trigger).hasAdditionalProperties();
    }

    @Override
    public String getHandledTriggerTypeDiscriminator() {
        return "DAILY_I";
    }

    @Override
    protected SimplePropertiesTriggerProperties getTriggerProperties(OperableTrigger trigger) {
        DailyTimeIntervalTriggerImpl dailyTrigger = (DailyTimeIntervalTriggerImpl)trigger;
        SimplePropertiesTriggerProperties props = new SimplePropertiesTriggerProperties();
        props.setInt1(dailyTrigger.getRepeatInterval());
        props.setString1(dailyTrigger.getRepeatIntervalUnit().name());
        props.setInt2(dailyTrigger.getTimesTriggered());
        Set<Integer> days = dailyTrigger.getDaysOfWeek();
        String daysStr = this.join(days, ",");
        props.setString2(daysStr);
        StringBuilder timeOfDayBuffer = new StringBuilder();
        TimeOfDay startTimeOfDay = dailyTrigger.getStartTimeOfDay();
        if (startTimeOfDay != null) {
            timeOfDayBuffer.append(startTimeOfDay.getHour()).append(",");
            timeOfDayBuffer.append(startTimeOfDay.getMinute()).append(",");
            timeOfDayBuffer.append(startTimeOfDay.getSecond()).append(",");
        } else {
            timeOfDayBuffer.append(",,,");
        }
        TimeOfDay endTimeOfDay = dailyTrigger.getEndTimeOfDay();
        if (endTimeOfDay != null) {
            timeOfDayBuffer.append(endTimeOfDay.getHour()).append(",");
            timeOfDayBuffer.append(endTimeOfDay.getMinute()).append(",");
            timeOfDayBuffer.append(endTimeOfDay.getSecond());
        } else {
            timeOfDayBuffer.append(",,,");
        }
        props.setString3(timeOfDayBuffer.toString());
        props.setLong1(dailyTrigger.getRepeatCount());
        return props;
    }

    private String join(Set<Integer> days, String sep) {
        StringBuilder sb = new StringBuilder();
        if (days == null || days.size() <= 0) {
            return "";
        }
        Iterator<Integer> itr = days.iterator();
        sb.append(itr.next());
        while (itr.hasNext()) {
            sb.append(sep).append(itr.next());
        }
        return sb.toString();
    }

    @Override
    protected TriggerPersistenceDelegate.TriggerPropertyBundle getTriggerPropertyBundle(SimplePropertiesTriggerProperties props) {
        int repeatCount = (int)props.getLong1();
        int interval = props.getInt1();
        String intervalUnitStr = props.getString1();
        String daysOfWeekStr = props.getString2();
        String timeOfDayStr = props.getString3();
        DateBuilder.IntervalUnit intervalUnit = DateBuilder.IntervalUnit.valueOf(intervalUnitStr);
        DailyTimeIntervalScheduleBuilder scheduleBuilder = DailyTimeIntervalScheduleBuilder.dailyTimeIntervalSchedule().withInterval(interval, intervalUnit).withRepeatCount(repeatCount);
        if (daysOfWeekStr != null) {
            HashSet<Integer> daysOfWeek = new HashSet<Integer>();
            String[] nums = daysOfWeekStr.split(",");
            if (nums.length > 0) {
                for (String num : nums) {
                    daysOfWeek.add(Integer.parseInt(num));
                }
                scheduleBuilder.onDaysOfTheWeek(daysOfWeek);
            }
        } else {
            scheduleBuilder.onDaysOfTheWeek(DailyTimeIntervalScheduleBuilder.ALL_DAYS_OF_THE_WEEK);
        }
        if (timeOfDayStr != null) {
            TimeOfDay endTimeOfDay;
            TimeOfDay startTimeOfDay;
            String[] nums = timeOfDayStr.split(",");
            if (nums.length >= 3) {
                int hour = Integer.parseInt(nums[0]);
                int min = Integer.parseInt(nums[1]);
                int sec = Integer.parseInt(nums[2]);
                startTimeOfDay = new TimeOfDay(hour, min, sec);
            } else {
                startTimeOfDay = TimeOfDay.hourMinuteAndSecondOfDay(0, 0, 0);
            }
            scheduleBuilder.startingDailyAt(startTimeOfDay);
            if (nums.length >= 6) {
                int hour = Integer.parseInt(nums[3]);
                int min = Integer.parseInt(nums[4]);
                int sec = Integer.parseInt(nums[5]);
                endTimeOfDay = new TimeOfDay(hour, min, sec);
            } else {
                endTimeOfDay = TimeOfDay.hourMinuteAndSecondOfDay(23, 59, 59);
            }
            scheduleBuilder.endingDailyAt(endTimeOfDay);
        } else {
            scheduleBuilder.startingDailyAt(TimeOfDay.hourMinuteAndSecondOfDay(0, 0, 0));
            scheduleBuilder.endingDailyAt(TimeOfDay.hourMinuteAndSecondOfDay(23, 59, 59));
        }
        int timesTriggered = props.getInt2();
        String[] statePropertyNames = new String[]{"timesTriggered"};
        Object[] statePropertyValues = new Object[]{timesTriggered};
        return new TriggerPersistenceDelegate.TriggerPropertyBundle(scheduleBuilder, statePropertyNames, statePropertyValues);
    }
}

