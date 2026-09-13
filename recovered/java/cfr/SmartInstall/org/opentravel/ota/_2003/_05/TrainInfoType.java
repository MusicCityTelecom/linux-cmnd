/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.TrainIdentificationType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="TrainInfoType", propOrder={"train", "validDate"})
public class TrainInfoType {
    @XmlElement(name="Train", required=true)
    protected TrainIdentificationType train;
    @XmlElement(name="ValidDate")
    protected ValidDate validDate;
    @XmlAttribute(name="DelayTime")
    @XmlSchemaType(name="anySimpleType")
    protected String delayTime;
    @XmlAttribute(name="ScheduleCode")
    protected String scheduleCode;

    public TrainIdentificationType getTrain() {
        return this.train;
    }

    public void setTrain(TrainIdentificationType value) {
        this.train = value;
    }

    public ValidDate getValidDate() {
        return this.validDate;
    }

    public void setValidDate(ValidDate value) {
        this.validDate = value;
    }

    public String getDelayTime() {
        return this.delayTime;
    }

    public void setDelayTime(String value) {
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

