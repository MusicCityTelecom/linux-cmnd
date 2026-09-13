/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.DayOfWeekType;
import org.opentravel.ota._2003._05.HotelInfoType;
import org.opentravel.ota._2003._05.ResCommonDetailType;
import org.opentravel.ota._2003._05.TimeInstantType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="DateTimeSpanType", propOrder={"dateWindowRange", "startDateWindow", "endDateWindow"})
@XmlSeeAlso(value={ResCommonDetailType.TimeSpan.class, HotelInfoType.BlackoutDates.BlackoutDate.class})
public class DateTimeSpanType {
    @XmlElement(name="DateWindowRange")
    protected TimeInstantType dateWindowRange;
    @XmlElement(name="StartDateWindow")
    protected StartDateWindow startDateWindow;
    @XmlElement(name="EndDateWindow")
    protected EndDateWindow endDateWindow;
    @XmlAttribute(name="Start")
    protected String start;
    @XmlAttribute(name="Duration")
    protected String duration;
    @XmlAttribute(name="End")
    protected String end;

    public TimeInstantType getDateWindowRange() {
        return this.dateWindowRange;
    }

    public void setDateWindowRange(TimeInstantType value) {
        this.dateWindowRange = value;
    }

    public StartDateWindow getStartDateWindow() {
        return this.startDateWindow;
    }

    public void setStartDateWindow(StartDateWindow value) {
        this.startDateWindow = value;
    }

    public EndDateWindow getEndDateWindow() {
        return this.endDateWindow;
    }

    public void setEndDateWindow(EndDateWindow value) {
        this.endDateWindow = value;
    }

    public String getStart() {
        return this.start;
    }

    public void setStart(String value) {
        this.start = value;
    }

    public String getDuration() {
        return this.duration;
    }

    public void setDuration(String value) {
        this.duration = value;
    }

    public String getEnd() {
        return this.end;
    }

    public void setEnd(String value) {
        this.end = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class StartDateWindow {
        @XmlAttribute(name="EarliestDate")
        protected String earliestDate;
        @XmlAttribute(name="LatestDate")
        protected String latestDate;
        @XmlAttribute(name="DOW")
        protected DayOfWeekType dow;

        public String getEarliestDate() {
            return this.earliestDate;
        }

        public void setEarliestDate(String value) {
            this.earliestDate = value;
        }

        public String getLatestDate() {
            return this.latestDate;
        }

        public void setLatestDate(String value) {
            this.latestDate = value;
        }

        public DayOfWeekType getDOW() {
            return this.dow;
        }

        public void setDOW(DayOfWeekType value) {
            this.dow = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class EndDateWindow {
        @XmlAttribute(name="EarliestDate")
        protected String earliestDate;
        @XmlAttribute(name="LatestDate")
        protected String latestDate;
        @XmlAttribute(name="DOW")
        protected DayOfWeekType dow;

        public String getEarliestDate() {
            return this.earliestDate;
        }

        public void setEarliestDate(String value) {
            this.earliestDate = value;
        }

        public String getLatestDate() {
            return this.latestDate;
        }

        public void setLatestDate(String value) {
            this.latestDate = value;
        }

        public DayOfWeekType getDOW() {
            return this.dow;
        }

        public void setDOW(DayOfWeekType value) {
            this.dow = value;
        }
    }
}

