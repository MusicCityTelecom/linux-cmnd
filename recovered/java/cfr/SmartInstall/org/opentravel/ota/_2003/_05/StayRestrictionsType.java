/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.StayUnitType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="StayRestrictionsType", propOrder={"minimumStay", "maximumStay"})
public class StayRestrictionsType {
    @XmlElement(name="MinimumStay")
    protected MinimumStay minimumStay;
    @XmlElement(name="MaximumStay")
    protected MaximumStay maximumStay;
    @XmlAttribute(name="StayRestrictionsInd")
    protected Boolean stayRestrictionsInd;

    public MinimumStay getMinimumStay() {
        return this.minimumStay;
    }

    public void setMinimumStay(MinimumStay value) {
        this.minimumStay = value;
    }

    public MaximumStay getMaximumStay() {
        return this.maximumStay;
    }

    public void setMaximumStay(MaximumStay value) {
        this.maximumStay = value;
    }

    public Boolean isStayRestrictionsInd() {
        return this.stayRestrictionsInd;
    }

    public void setStayRestrictionsInd(Boolean value) {
        this.stayRestrictionsInd = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class MinimumStay {
        @XmlAttribute(name="ReturnTimeOfDay")
        protected String returnTimeOfDay;
        @XmlAttribute(name="MinStay")
        protected Integer minStay;
        @XmlAttribute(name="StayUnit")
        protected StayUnitType stayUnit;
        @XmlAttribute(name="MinStayDate")
        protected String minStayDate;
        @XmlAttribute(name="ComplicatedRulesInd")
        protected Boolean complicatedRulesInd;

        public String getReturnTimeOfDay() {
            return this.returnTimeOfDay;
        }

        public void setReturnTimeOfDay(String value) {
            this.returnTimeOfDay = value;
        }

        public Integer getMinStay() {
            return this.minStay;
        }

        public void setMinStay(Integer value) {
            this.minStay = value;
        }

        public StayUnitType getStayUnit() {
            return this.stayUnit;
        }

        public void setStayUnit(StayUnitType value) {
            this.stayUnit = value;
        }

        public String getMinStayDate() {
            return this.minStayDate;
        }

        public void setMinStayDate(String value) {
            this.minStayDate = value;
        }

        public Boolean isComplicatedRulesInd() {
            return this.complicatedRulesInd;
        }

        public void setComplicatedRulesInd(Boolean value) {
            this.complicatedRulesInd = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class MaximumStay {
        @XmlAttribute(name="ReturnType")
        protected String returnType;
        @XmlAttribute(name="ReturnTimeOfDay")
        protected String returnTimeOfDay;
        @XmlAttribute(name="MaxStay")
        protected Integer maxStay;
        @XmlAttribute(name="StayUnit")
        protected StayUnitType stayUnit;
        @XmlAttribute(name="MaxStayDate")
        protected String maxStayDate;
        @XmlAttribute(name="ComplicatedRulesInd")
        protected Boolean complicatedRulesInd;

        public String getReturnType() {
            return this.returnType;
        }

        public void setReturnType(String value) {
            this.returnType = value;
        }

        public String getReturnTimeOfDay() {
            return this.returnTimeOfDay;
        }

        public void setReturnTimeOfDay(String value) {
            this.returnTimeOfDay = value;
        }

        public Integer getMaxStay() {
            return this.maxStay;
        }

        public void setMaxStay(Integer value) {
            this.maxStay = value;
        }

        public StayUnitType getStayUnit() {
            return this.stayUnit;
        }

        public void setStayUnit(StayUnitType value) {
            this.stayUnit = value;
        }

        public String getMaxStayDate() {
            return this.maxStayDate;
        }

        public void setMaxStayDate(String value) {
            this.maxStayDate = value;
        }

        public Boolean isComplicatedRulesInd() {
            return this.complicatedRulesInd;
        }

        public void setComplicatedRulesInd(Boolean value) {
            this.complicatedRulesInd = value;
        }
    }
}

