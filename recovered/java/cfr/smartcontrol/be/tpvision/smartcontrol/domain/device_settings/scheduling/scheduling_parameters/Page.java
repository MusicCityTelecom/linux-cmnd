/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.domain.device_settings.scheduling.scheduling_parameters;

import be.tpvision.smartcontrol.domain.device_settings.DeviceSetting;
import be.tpvision.smartcontrol.domain.device_settings.input_sources.InputSource;
import be.tpvision.smartcontrol.messages.domain.device_settings.scheduling.scheduling_parameters.page.SetWorkingDaysMessages;
import be.tpvision.smartcontrol.messages.domain.device_settings.scheduling.scheduling_parameters.page.working_days.GetValueMessages;
import be.tpvision.smartcontrol.messages.domain.device_settings.scheduling.scheduling_parameters.page.working_days.SetValueMessages;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

public class Page
implements DeviceSetting {
    private Integer number;
    private Status status;
    private LocalTime start;
    private LocalTime end;
    private InputSource.SourceType sourceType;
    private WorkingDays workingDays;
    private InputSource.Tag tag;

    protected Page() {
    }

    public Page(Integer number, Status status, LocalTime start, LocalTime end, InputSource.SourceType sourceType, WorkingDays workingDays, InputSource.Tag tag) {
        this.number = number;
        this.status = status;
        this.start = start;
        this.end = end;
        this.sourceType = sourceType;
        this.workingDays = workingDays;
        this.tag = tag;
    }

    public Integer getNumber() {
        return this.number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalTime getStart() {
        return this.start;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public LocalTime getEnd() {
        return this.end;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }

    public InputSource.SourceType getSourceType() {
        return this.sourceType;
    }

    public void setSourceType(InputSource.SourceType sourceType) {
        this.sourceType = sourceType;
    }

    public WorkingDays getWorkingDays() {
        return this.workingDays;
    }

    public void setWorkingDays(WorkingDays workingDays) {
        Assert.notNull((Object)workingDays, SetWorkingDaysMessages.WORKING_DAYS_CAN_NOT_BE_NULL);
        this.workingDays = workingDays;
    }

    public InputSource.Tag getTag() {
        return this.tag;
    }

    public void setTag(InputSource.Tag tag) {
        this.tag = tag;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Page)) {
            return false;
        }
        Page that = (Page)object;
        return new EqualsBuilder().append(this.getNumber(), that.getNumber()).append((Object)this.getStatus(), (Object)that.getStatus()).append(this.getStart(), that.getStart()).append(this.getEnd(), that.getEnd()).append((Object)this.getSourceType(), (Object)that.getSourceType()).append(this.getWorkingDays(), that.getWorkingDays()).append((Object)this.getTag(), (Object)that.getTag()).isEquals();
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.getNumber(), this.getStatus(), this.getStart(), this.getEnd(), this.getSourceType(), this.getWorkingDays(), this.getTag()});
    }

    public String toString() {
        return new ToStringBuilder(this).append("number", this.getNumber()).append("status", (Object)this.getStatus()).append("start", this.getStart()).append("end", this.getEnd()).append("sourceType", (Object)this.getSourceType()).append("workingDays", this.getWorkingDays()).append("tag", (Object)this.getTag()).toString();
    }

    public static class WorkingDays {
        private LinkedHashMap<WorkingDay, Boolean> workingDays = new LinkedHashMap();

        public WorkingDays() {
            for (WorkingDay workingDay : WorkingDay.values()) {
                this.workingDays.put(workingDay, false);
            }
        }

        public LinkedHashMap<WorkingDay, Boolean> getWorkingDays() {
            return this.workingDays;
        }

        public boolean getValue(WorkingDay workingDay) {
            Assert.notNull((Object)workingDay, GetValueMessages.WORKING_DAY_CAN_NOT_BE_NULL);
            return this.workingDays.get((Object)workingDay);
        }

        public void setValue(WorkingDay workingDay, boolean value) {
            Assert.notNull((Object)workingDay, SetValueMessages.WORKING_DAY_CAN_NOT_BE_NULL);
            this.workingDays.put(workingDay, value);
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof WorkingDays)) {
                return false;
            }
            WorkingDays that = (WorkingDays)object;
            return new EqualsBuilder().append(this.getWorkingDays(), that.getWorkingDays()).isEquals();
        }

        public int hashCode() {
            return Objects.hash(this.getWorkingDays());
        }

        public String toString() {
            return new ToStringBuilder(this).append("workingDays", this.getWorkingDays()).toString();
        }
    }

    public static enum WorkingDay {
        EVERY_WEEK,
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY,
        SUNDAY;

    }

    public static enum Status {
        DISABLED,
        ENABLED;

    }
}

