/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.BusIdentificationType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="BusInfoType", propOrder={"bus", "validDate"})
public class BusInfoType {
    @XmlElement(name="Bus", required=true)
    protected BusIdentificationType bus;
    @XmlElement(name="ValidDate")
    protected ValidDate validDate;
    @XmlAttribute(name="DelayTime")
    @XmlSchemaType(name="positiveInteger")
    protected BigInteger delayTime;
    @XmlAttribute(name="ScheduleCode")
    protected String scheduleCode;

    public BusIdentificationType getBus() {
        return this.bus;
    }

    public void setBus(BusIdentificationType value) {
        this.bus = value;
    }

    public ValidDate getValidDate() {
        return this.validDate;
    }

    public void setValidDate(ValidDate value) {
        this.validDate = value;
    }

    public BigInteger getDelayTime() {
        return this.delayTime;
    }

    public void setDelayTime(BigInteger value) {
        this.delayTime = value;
    }

    public String getScheduleCode() {
        return this.scheduleCode;
    }

    public void setScheduleCode(String value) {
        this.scheduleCode = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class ValidDate {
        @XmlAttribute(name="StartPeriod")
        protected String startPeriod;
        @XmlAttribute(name="Duration")
        protected String duration;
        @XmlAttribute(name="EndPeriod")
        protected String endPeriod;

        public String getStartPeriod() {
            return this.startPeriod;
        }

        public void setStartPeriod(String value) {
            this.startPeriod = value;
        }

        public String getDuration() {
            return this.duration;
        }

        public void setDuration(String value) {
            this.duration = value;
        }

        public String getEndPeriod() {
            return this.endPeriod;
        }

        public void setEndPeriod(String value) {
            this.endPeriod = value;
        }
    }
}

