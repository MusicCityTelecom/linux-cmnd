/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.TimeUnitType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="LengthsOfStayType", propOrder={"lengthOfStay"})
public class LengthsOfStayType {
    @XmlElement(name="LengthOfStay")
    protected List<LengthOfStay> lengthOfStay;
    @XmlAttribute(name="ArrivalDateBased")
    protected Boolean arrivalDateBased;
    @XmlAttribute(name="FixedPatternLength")
    protected Integer fixedPatternLength;

    public List<LengthOfStay> getLengthOfStay() {
        if (this.lengthOfStay == null) {
            this.lengthOfStay = new ArrayList<LengthOfStay>();
        }
        return this.lengthOfStay;
    }

    public Boolean isArrivalDateBased() {
        return this.arrivalDateBased;
    }

    public void setArrivalDateBased(Boolean value) {
        this.arrivalDateBased = value;
    }

    public Integer getFixedPatternLength() {
        return this.fixedPatternLength;
    }

    public void setFixedPatternLength(Integer value) {
        this.fixedPatternLength = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"losPattern"})
    public static class LengthOfStay {
        @XmlElement(name="LOS_Pattern")
        protected LOSPattern losPattern;
        @XmlAttribute(name="Time")
        protected BigInteger time;
        @XmlAttribute(name="TimeUnit")
        protected TimeUnitType timeUnit;
        @XmlAttribute(name="OpenStatusIndicator")
        protected Boolean openStatusIndicator;
        @XmlAttribute(name="MinMaxMessageType")
        protected String minMaxMessageType;

        public LOSPattern getLOSPattern() {
            return this.losPattern;
        }

        public void setLOSPattern(LOSPattern value) {
            this.losPattern = value;
        }

        public BigInteger getTime() {
            return this.time;
        }

        public void setTime(BigInteger value) {
            this.time = value;
        }

        public TimeUnitType getTimeUnit() {
            return this.timeUnit;
        }

        public void setTimeUnit(TimeUnitType value) {
            this.timeUnit = value;
        }

        public Boolean isOpenStatusIndicator() {
            return this.openStatusIndicator;
        }

        public void setOpenStatusIndicator(Boolean value) {
            this.openStatusIndicator = value;
        }

        public String getMinMaxMessageType() {
            return this.minMaxMessageType;
        }

        public void setMinMaxMessageType(String value) {
            this.minMaxMessageType = value;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="")
        public static class LOSPattern {
            @XmlAttribute(name="FullPatternLOS")
            protected String fullPatternLOS;

            public String getFullPatternLOS() {
                return this.fullPatternLOS;
            }

            public void setFullPatternLOS(String value) {
                this.fullPatternLOS = value;
            }
        }
    }
}

