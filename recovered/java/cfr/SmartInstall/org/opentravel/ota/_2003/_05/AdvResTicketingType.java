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
import org.opentravel.ota._2003._05.RuleInfoType;
import org.opentravel.ota._2003._05.StayUnitType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="AdvResTicketingType", propOrder={"advReservation", "advTicketing"})
@XmlSeeAlso(value={RuleInfoType.ResTicketingRules.AdvResTicketing.class})
public class AdvResTicketingType {
    @XmlElement(name="AdvReservation")
    protected AdvReservation advReservation;
    @XmlElement(name="AdvTicketing")
    protected AdvTicketing advTicketing;
    @XmlAttribute(name="AdvResInd")
    protected Boolean advResInd;
    @XmlAttribute(name="AdvTicketingInd")
    protected Boolean advTicketingInd;
    @XmlAttribute(name="RequestedTicketingDate")
    protected String requestedTicketingDate;

    public AdvReservation getAdvReservation() {
        return this.advReservation;
    }

    public void setAdvReservation(AdvReservation value) {
        this.advReservation = value;
    }

    public AdvTicketing getAdvTicketing() {
        return this.advTicketing;
    }

    public void setAdvTicketing(AdvTicketing value) {
        this.advTicketing = value;
    }

    public Boolean isAdvResInd() {
        return this.advResInd;
    }

    public void setAdvResInd(Boolean value) {
        this.advResInd = value;
    }

    public Boolean isAdvTicketingInd() {
        return this.advTicketingInd;
    }

    public void setAdvTicketingInd(Boolean value) {
        this.advTicketingInd = value;
    }

    public String getRequestedTicketingDate() {
        return this.requestedTicketingDate;
    }

    public void setRequestedTicketingDate(String value) {
        this.requestedTicketingDate = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class AdvTicketing {
        @XmlAttribute(name="FromResTimeOfDay")
        protected String fromResTimeOfDay;
        @XmlAttribute(name="FromResPeriod")
        protected String fromResPeriod;
        @XmlAttribute(name="FromResUnit")
        protected StayUnitType fromResUnit;
        @XmlAttribute(name="FromDepartTimeOfDay")
        protected String fromDepartTimeOfDay;
        @XmlAttribute(name="FromDepartPeriod")
        protected String fromDepartPeriod;
        @XmlAttribute(name="FromDepartUnit")
        protected StayUnitType fromDepartUnit;

        public String getFromResTimeOfDay() {
            return this.fromResTimeOfDay;
        }

        public void setFromResTimeOfDay(String value) {
            this.fromResTimeOfDay = value;
        }

        public String getFromResPeriod() {
            return this.fromResPeriod;
        }

        public void setFromResPeriod(String value) {
            this.fromResPeriod = value;
        }

        public StayUnitType getFromResUnit() {
            return this.fromResUnit;
        }

        public void setFromResUnit(StayUnitType value) {
            this.fromResUnit = value;
        }

        public String getFromDepartTimeOfDay() {
            return this.fromDepartTimeOfDay;
        }

        public void setFromDepartTimeOfDay(String value) {
            this.fromDepartTimeOfDay = value;
        }

        public String getFromDepartPeriod() {
            return this.fromDepartPeriod;
        }

        public void setFromDepartPeriod(String value) {
            this.fromDepartPeriod = value;
        }

        public StayUnitType getFromDepartUnit() {
            return this.fromDepartUnit;
        }

        public void setFromDepartUnit(StayUnitType value) {
            this.fromDepartUnit = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class AdvReservation {
        @XmlAttribute(name="LatestTimeOfDay")
        protected String latestTimeOfDay;
        @XmlAttribute(name="LatestPeriod")
        protected String latestPeriod;
        @XmlAttribute(name="LatestUnit")
        protected StayUnitType latestUnit;

        public String getLatestTimeOfDay() {
            return this.latestTimeOfDay;
        }

        public void setLatestTimeOfDay(String value) {
            this.latestTimeOfDay = value;
        }

        public String getLatestPeriod() {
            return this.latestPeriod;
        }

        public void setLatestPeriod(String value) {
            this.latestPeriod = value;
        }

        public StayUnitType getLatestUnit() {
            return this.latestUnit;
        }

        public void setLatestUnit(StayUnitType value) {
            this.latestUnit = value;
        }
    }
}

