/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="DOW_RestrictionsType", propOrder={"availableDaysOfWeek", "arrivalDaysOfWeek", "departureDaysOfWeek", "requiredDaysOfWeek"})
public class DOWRestrictionsType {
    @XmlElement(name="AvailableDaysOfWeek")
    protected AvailableDaysOfWeek availableDaysOfWeek;
    @XmlElement(name="ArrivalDaysOfWeek")
    protected ArrivalDaysOfWeek arrivalDaysOfWeek;
    @XmlElement(name="DepartureDaysOfWeek")
    protected DepartureDaysOfWeek departureDaysOfWeek;
    @XmlElement(name="RequiredDaysOfWeek")
    protected RequiredDaysOfWeek requiredDaysOfWeek;

    public AvailableDaysOfWeek getAvailableDaysOfWeek() {
        return this.availableDaysOfWeek;
    }

    public void setAvailableDaysOfWeek(AvailableDaysOfWeek value) {
        this.availableDaysOfWeek = value;
    }

    public ArrivalDaysOfWeek getArrivalDaysOfWeek() {
        return this.arrivalDaysOfWeek;
    }

    public void setArrivalDaysOfWeek(ArrivalDaysOfWeek value) {
        this.arrivalDaysOfWeek = value;
    }

    public DepartureDaysOfWeek getDepartureDaysOfWeek() {
        return this.departureDaysOfWeek;
    }

    public void setDepartureDaysOfWeek(DepartureDaysOfWeek value) {
        this.departureDaysOfWeek = value;
    }

    public RequiredDaysOfWeek getRequiredDaysOfWeek() {
        return this.requiredDaysOfWeek;
    }

    public void setRequiredDaysOfWeek(RequiredDaysOfWeek value) {
        this.requiredDaysOfWeek = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class RequiredDaysOfWeek {
        @XmlAttribute(name="Mon")
        protected Boolean mon;
        @XmlAttribute(name="Tue")
        protected Boolean tue;
        @XmlAttribute(name="Weds")
        protected Boolean weds;
        @XmlAttribute(name="Thur")
        protected Boolean thur;
        @XmlAttribute(name="Fri")
        protected Boolean fri;
        @XmlAttribute(name="Sat")
        protected Boolean sat;
        @XmlAttribute(name="Sun")
        protected Boolean sun;

        public Boolean isMon() {
            return this.mon;
        }

        public void setMon(Boolean value) {
            this.mon = value;
        }

        public Boolean isTue() {
            return this.tue;
        }

        public void setTue(Boolean value) {
            this.tue = value;
        }

        public Boolean isWeds() {
            return this.weds;
        }

        public void setWeds(Boolean value) {
            this.weds = value;
        }

        public Boolean isThur() {
            return this.thur;
        }

        public void setThur(Boolean value) {
            this.thur = value;
        }

        public Boolean isFri() {
            return this.fri;
        }

        public void setFri(Boolean value) {
            this.fri = value;
        }

        public Boolean isSat() {
            return this.sat;
        }

        public void setSat(Boolean value) {
            this.sat = value;
        }

        public Boolean isSun() {
            return this.sun;
        }

        public void setSun(Boolean value) {
            this.sun = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class DepartureDaysOfWeek {
        @XmlAttribute(name="Mon")
        protected Boolean mon;
        @XmlAttribute(name="Tue")
        protected Boolean tue;
        @XmlAttribute(name="Weds")
        protected Boolean weds;
        @XmlAttribute(name="Thur")
        protected Boolean thur;
        @XmlAttribute(name="Fri")
        protected Boolean fri;
        @XmlAttribute(name="Sat")
        protected Boolean sat;
        @XmlAttribute(name="Sun")
        protected Boolean sun;

        public Boolean isMon() {
            return this.mon;
        }

        public void setMon(Boolean value) {
            this.mon = value;
        }

        public Boolean isTue() {
            return this.tue;
        }

        public void setTue(Boolean value) {
            this.tue = value;
        }

        public Boolean isWeds() {
            return this.weds;
        }

        public void setWeds(Boolean value) {
            this.weds = value;
        }

        public Boolean isThur() {
            return this.thur;
        }

        public void setThur(Boolean value) {
            this.thur = value;
        }

        public Boolean isFri() {
            return this.fri;
        }

        public void setFri(Boolean value) {
            this.fri = value;
        }

        public Boolean isSat() {
            return this.sat;
        }

        public void setSat(Boolean value) {
            this.sat = value;
        }

        public Boolean isSun() {
            return this.sun;
        }

        public void setSun(Boolean value) {
            this.sun = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class AvailableDaysOfWeek {
        @XmlAttribute(name="Mon")
        protected Boolean mon;
        @XmlAttribute(name="Tue")
        protected Boolean tue;
        @XmlAttribute(name="Weds")
        protected Boolean weds;
        @XmlAttribute(name="Thur")
        protected Boolean thur;
        @XmlAttribute(name="Fri")
        protected Boolean fri;
        @XmlAttribute(name="Sat")
        protected Boolean sat;
        @XmlAttribute(name="Sun")
        protected Boolean sun;

        public Boolean isMon() {
            return this.mon;
        }

        public void setMon(Boolean value) {
            this.mon = value;
        }

        public Boolean isTue() {
            return this.tue;
        }

        public void setTue(Boolean value) {
            this.tue = value;
        }

        public Boolean isWeds() {
            return this.weds;
        }

        public void setWeds(Boolean value) {
            this.weds = value;
        }

        public Boolean isThur() {
            return this.thur;
        }

        public void setThur(Boolean value) {
            this.thur = value;
        }

        public Boolean isFri() {
            return this.fri;
        }

        public void setFri(Boolean value) {
            this.fri = value;
        }

        public Boolean isSat() {
            return this.sat;
        }

        public void setSat(Boolean value) {
            this.sat = value;
        }

        public Boolean isSun() {
            return this.sun;
        }

        public void setSun(Boolean value) {
            this.sun = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class ArrivalDaysOfWeek {
        @XmlAttribute(name="Mon")
        protected Boolean mon;
        @XmlAttribute(name="Tue")
        protected Boolean tue;
        @XmlAttribute(name="Weds")
        protected Boolean weds;
        @XmlAttribute(name="Thur")
        protected Boolean thur;
        @XmlAttribute(name="Fri")
        protected Boolean fri;
        @XmlAttribute(name="Sat")
        protected Boolean sat;
        @XmlAttribute(name="Sun")
        protected Boolean sun;

        public Boolean isMon() {
            return this.mon;
        }

        public void setMon(Boolean value) {
            this.mon = value;
        }

        public Boolean isTue() {
            return this.tue;
        }

        public void setTue(Boolean value) {
            this.tue = value;
        }

        public Boolean isWeds() {
            return this.weds;
        }

        public void setWeds(Boolean value) {
            this.weds = value;
        }

        public Boolean isThur() {
            return this.thur;
        }

        public void setThur(Boolean value) {
            this.thur = value;
        }

        public Boolean isFri() {
            return this.fri;
        }

        public void setFri(Boolean value) {
            this.fri = value;
        }

        public Boolean isSat() {
            return this.sat;
        }

        public void setSat(Boolean value) {
            this.sat = value;
        }

        public Boolean isSun() {
            return this.sun;
        }

        public void setSun(Boolean value) {
            this.sun = value;
        }
    }
}

