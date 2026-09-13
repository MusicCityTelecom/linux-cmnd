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
import org.opentravel.ota._2003._05.CompanyNameType;
import org.opentravel.ota._2003._05.SpecialReqDetailsType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="SpecialServiceRequestType", propOrder={"airline", "text"})
@XmlSeeAlso(value={SpecialReqDetailsType.SpecialServiceRequests.SpecialServiceRequest.class})
public class SpecialServiceRequestType {
    @XmlElement(name="Airline")
    protected CompanyNameType airline;
    @XmlElement(name="Text")
    protected String text;
    @XmlAttribute(name="SSRCode", required=true)
    protected String ssrCode;
    @XmlAttribute(name="ServiceQuantity")
    protected Integer serviceQuantity;
    @XmlAttribute(name="Status")
    protected String status;
    @XmlAttribute(name="Number")
    protected Integer number;

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

    public String getSSRCode() {
        return this.ssrCode;
    }

    public void setSSRCode(String value) {
        this.ssrCode = value;
    }

    public Integer getServiceQuantity() {
        return this.serviceQuantity;
    }

    public void setServiceQuantity(Integer value) {
        this.serviceQuantity = value;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String value) {
        this.status = value;
    }

    public Integer getNumber() {
        return this.number;
    }

    public void setNumber(Integer value) {
        this.number = value;
    }
}

