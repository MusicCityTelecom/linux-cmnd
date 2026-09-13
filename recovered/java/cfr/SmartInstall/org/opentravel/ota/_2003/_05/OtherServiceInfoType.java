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
import org.opentravel.ota._2003._05.CompanyNameType;
import org.opentravel.ota._2003._05.SpecialReqDetailsType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="OtherServiceInfoType", propOrder={"travelerRefNumber", "airline", "text"})
@XmlSeeAlso(value={SpecialReqDetailsType.OtherServiceInformations.OtherServiceInformation.class})
public class OtherServiceInfoType {
    @XmlElement(name="TravelerRefNumber")
    protected List<TravelerRefNumber> travelerRefNumber;
    @XmlElement(name="Airline", required=true)
    protected CompanyNameType airline;
    @XmlElement(name="Text", required=true)
    protected String text;
    @XmlAttribute(name="Code")
    protected String code;

    public List<TravelerRefNumber> getTravelerRefNumber() {
        if (this.travelerRefNumber == null) {
            this.travelerRefNumber = new ArrayList<TravelerRefNumber>();
        }
        return this.travelerRefNumber;
    }

    public CompanyNameType getAirline() {
        return this.airline;
    }

    public void setAirline(CompanyNameType value) {
        this.airline = value;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String value) {
        this.text = value;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String value) {
        this.code = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class TravelerRefNumber {
        @XmlAttribute(name="RPH")
        protected String rph;
        @XmlAttribute(name="SurnameRefNumber")
        protected String surnameRefNumber;

        public String getRPH() {
            return this.rph;
        }

        public void setRPH(String value) {
            this.rph = value;
        }

        public String getSurnameRefNumber() {
            return this.surnameRefNumber;
        }

        public void setSurnameRefNumber(String value) {
            this.surnameRefNumber = value;
        }
    }
}

