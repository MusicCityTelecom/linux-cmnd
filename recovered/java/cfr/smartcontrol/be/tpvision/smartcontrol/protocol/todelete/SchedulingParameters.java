/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.protocol.todelete;

import be.tpvision.smartcontrol.protocol.todelete.InputSource;
import be.tpvision.smartcontrol.util.Convertible;
import be.tpvision.smartcontrol.util.Convertibles;
import be.tpvision.smartcontrol.util.ValueUtilities;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Objects;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class SchedulingParameters
implements Convertibles {
    private Page page;
    private LocalTime start;
    private LocalTime end;
    private InputSource.SourceType sourceType;
    private WorkingDays workingDays;

    public Page getPage() {
        return this.page;
    }

    public void setPage(Page page) {
        this.page = page;
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
        this.workingDays = workingDays;
    }

    @Override
    public byte[] convert() {
        byte pageByte = this.page.convert();
        int startHour = this.start.getHour();
        byte startHourByte = (byte)startHour;
        int startMinute = this.start.getMinute();
        byte startMinuteByte = (byte)startMinute;
        int endHour = this.end.getHour();
        byte endHourByte = (byte)endHour;
        int endMinute = this.end.getMinute();
        byte endMinuteByte = (byte)endMinute;
        byte sourceTypeByte = this.sourceType.convert();
        byte workingDaysByte = this.workingDays.convert();
        return new byte[]{pageByte, startHourByte, startMinuteByte, endHourByte, endMinuteByte, sourceTypeByte, workingDaysByte};
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof SchedulingParameters)) {
            return false;
        }
        SchedulingParameters that = (SchedulingParameters)object;
        return new EqualsBuilder().append(this.page, that.page).append(this.start, that.start).append(this.end, that.end).append(this.sourceType, that.sourceType).append(this.workingDays, that.workingDays).isEquals();
    }

    public int hashCode() {
        return Objects.hash(this.page, this.start, this.end, this.sourceType, this.workingDays);
    }

    public String toString() {
        return new ToStringBuilder(this).append("page", this.page).append("start", this.start).append("end", this.end).append("sourceType", this.sourceType).append("workingDays", this.workingDays).toString();
    }

    public static class WorkingDays
    implements Convertible {
        private LinkedHashMap<WorkingDay, Boolean> workingDays = new LinkedHashMap();

        public WorkingDays() {
            for (WorkingDay workingDay : WorkingDay.values()) {
                this.workingDays.put(workingDay, false);
            }
        }

        public boolean getValue(WorkingDay workingDay) {
            return this.workingDays.get((Object)workingDay);
        }

        public void setValue(WorkingDay workingDay, boolean value) {
            this.workingDays.put(workingDay, value);
        }

        @Override
        public byte convert() {
            Boolean[] workingDayValues = this.workingDays.values().toArray(new Boolean[0]);
            boolean[] workingDayBits = ArrayUtils.toPrimitive(workingDayValues);
            return ValueUtilities.toByte(workingDayBits);
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof WorkingDays)) {
                return false;
            }
            WorkingDays that = (WorkingDays)object;
            return new EqualsBuilder().append(this.workingDays, that.workingDays).isEquals();
        }

        public int hashCode() {
            return Objects.hash(this.workingDays);
        }

        public String toString() {
            return new ToStringBuilder(this).append("workingDays", this.workingDays).toString();
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

    public static class Page
    implements Convertible {
        private PageNumber pageNumber;
        private Status status;

        public Page(PageNumber pageNumber) {
            this(pageNumber, null);
        }

        public Page(PageNumber pageNumber, Status status) {
            this.pageNumber = pageNumber;
            this.status = status;
        }

        public PageNumber getPageNumber() {
            return this.pageNumber;
        }

        public void setPageNumber(PageNumber pageNumber) {
            this.pageNumber = pageNumber;
        }

        public Status getStatus() {
            return this.status;
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        @Override
        public byte convert() {
            byte statusByte = this.status != null ? this.status.convert() : (byte)0;
            boolean[] statusBooleanByte = ValueUtilities.toBooleanArray(statusByte);
            boolean[] statusBits = Arrays.copyOfRange(statusBooleanByte, 4, 8);
            byte pageNumberByte = this.pageNumber != null ? this.pageNumber.convert() : (byte)0;
            boolean[] pageNumberBooleanByte = ValueUtilities.toBooleanArray(pageNumberByte);
            boolean[] pageNumberBits = Arrays.copyOfRange(pageNumberBooleanByte, 4, 8);
            boolean[] pageBits = ArrayUtils.addAll(statusBits, pageNumberBits);
            return ValueUtilities.toByte(pageBits);
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof Page)) {
                return false;
            }
            Page page = (Page)object;
            return new EqualsBuilder().append(this.pageNumber, page.pageNumber).append(this.status, page.status).isEquals();
        }

        public int hashCode() {
            return Objects.hash(this.pageNumber, this.status);
        }

        public String toString() {
            return new ToStringBuilder(this).append("pageNumber", this.pageNumber).append("status", this.status).toString();
        }

        public static enum Status implements Convertible
        {
            DISABLED(0),
            ENABLED(1);

            private byte data;

            private Status(byte data) {
                this.data = data;
            }

            public byte getData() {
                return this.data;
            }

            @Override
            public byte convert() {
                return this.data;
            }
        }

        public static class PageNumber
        implements Convertible {
            public static final int MINIMUM = 1;
            public static final int MAXIMUM = 7;
            private int number;

            public PageNumber() {
                this(1);
            }

            public PageNumber(int number) {
                this.number = number;
            }

            public int getNumber() {
                return this.number;
            }

            public void setNumber(int number) {
                this.number = ValueUtilities.getValue(number, 1, 7);
            }

            @Override
            public byte convert() {
                return (byte)this.number;
            }

            public boolean equals(Object object) {
                if (this == object) {
                    return true;
                }
                if (!(object instanceof PageNumber)) {
                    return false;
                }
                PageNumber that = (PageNumber)object;
                return new EqualsBuilder().append(this.number, that.number).isEquals();
            }

            public int hashCode() {
                return Objects.hash(this.number);
            }

            public String toString() {
                return new ToStringBuilder(this).append("number", this.number).toString();
            }
        }
    }
}

