/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.opentravel.ota._2003._05.RailAvailQueryType;
import org.opentravel.ota._2003._05.RailPassengerCategoryDetailType;
import org.opentravel.ota._2003._05.RailPassengerOccupationType;
import org.opentravel.ota._2003._05.RailRateQualifyingType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="RailPassengerCategoryType", propOrder={"occupation", "passengerQualifyingInfo", "rateQualifier", "adaRequirement"})
@XmlSeeAlso(value={RailAvailQueryType.PassengerType.class, RailPassengerCategoryDetailType.class})
public class RailPassengerCategoryType {
    @XmlElement(name="Occupation")
    protected RailPassengerOccupationType occupation;
    @XmlElement(name="PassengerQualifyingInfo")
    protected List<PassengerQualifyingInfo> passengerQualifyingInfo;
    @XmlElement(name="RateQualifier")
    protected List<RailRateQualifyingType> rateQualifier;
    @XmlElement(name="ADA_Requirement")
    protected List<String> adaRequirement;
    @XmlAttribute(name="AccompaniedByInfantInd")
    protected Boolean accompaniedByInfantInd;
    @XmlAttribute(name="Gender")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    protected String gender;

    public RailPassengerOccupationType getOccupation() {
        return this.occupation;
    }

    public void setOccupation(RailPassengerOccupationType value) {
        this.occupation = value;
    }

    public List<PassengerQualifyingInfo> getPassengerQualifyingInfo() {
        if (this.passengerQualifyingInfo == null) {
            this.passengerQualifyingInfo = new ArrayList<PassengerQualifyingInfo>();
        }
        return this.passengerQualifyingInfo;
    }

    public List<RailRateQualifyingType> getRateQualifier() {
        if (this.rateQualifier == null) {
            this.rateQualifier = new ArrayList<RailRateQualifyingType>();
        }
        return this.rateQualifier;
    }

    public List<String> getADARequirement() {
        if (this.adaRequirement == null) {
            this.adaRequirement = new ArrayList<String>();
        }
        return this.adaRequirement;
    }

    public Boolean isAccompaniedByInfantInd() {
        return this.accompaniedByInfantInd;
    }

    public void setAccompaniedByInfantInd(Boolean value) {
        this.accompaniedByInfantInd = value;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String value) {
        this.gender = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class PassengerQualifyingInfo {
        @XmlAttribute(name="Code")
        protected String code;
        @XmlAttribute(name="CodeContext")
        protected String codeContext;

        public String getCode() {
            return this.code;
        }

        public void setCode(String value) {
            this.code = value;
        }

        public String getCodeContext() {
            return this.codeContext;
        }

        public void setCodeContext(String value) {
            this.codeContext = value;
        }
    }
}

