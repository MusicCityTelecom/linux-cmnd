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
import javax.xml.datatype.XMLGregorianCalendar;
import org.opentravel.ota._2003._05.TimeUnitType;
import org.opentravel.ota._2003._05.TotalType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="AmountLiteType", propOrder={"base"})
public class AmountLiteType {
    @XmlElement(name="Base", required=true)
    protected TotalType base;
    @XmlAttribute(name="GuaranteedInd")
    protected Boolean guaranteedInd;
    @XmlAttribute(name="EffectiveDate")
    @XmlSchemaType(name="date")
    protected XMLGregorianCalendar effectiveDate;
    @XmlAttribute(name="ExpireDate")
    @XmlSchemaType(name="date")
    protected XMLGregorianCalendar expireDate;
    @XmlAttribute(name="ExpireDateExclusiveIndicator")
    protected Boolean expireDateExclusiveIndicator;
    @XmlAttribute(name="AgeQualifyingCode")
    protected String ageQualifyingCode;
    @XmlAttribute(name="MinAge")
    protected Integer minAge;
    @XmlAttribute(name="MaxAge")
    protected Integer maxAge;
    @XmlAttribute(name="AgeTimeUnit")
    protected TimeUnitType ageTimeUnit;
    @XmlAttribute(name="AgeBucket")
    protected String ageBucket;

    public TotalType getBase() {
        return this.base;
    }

    public void setBase(TotalType value) {
        this.base = value;
    }

    public Boolean isGuaranteedInd() {
        return this.guaranteedInd;
    }

    public void setGuaranteedInd(Boolean value) {
        this.guaranteedInd = value;
    }

    public XMLGregorianCalendar getEffectiveDate() {
        return this.effectiveDate;
    }

    public void setEffectiveDate(XMLGregorianCalendar value) {
        this.effectiveDate = value;
    }

    public XMLGregorianCalendar getExpireDate() {
        return this.expireDate;
    }

    public void setExpireDate(XMLGregorianCalendar value) {
        this.expireDate = value;
    }

    public Boolean isExpireDateExclusiveIndicator() {
        return this.expireDateExclusiveIndicator;
    }

    public void setExpireDateExclusiveIndicator(Boolean value) {
        this.expireDateExclusiveIndicator = value;
    }

    public String getAgeQualifyingCode() {
        return this.ageQualifyingCode;
    }

    public void setAgeQualifyingCode(String value) {
        this.ageQualifyingCode = value;
    }

    public Integer getMinAge() {
        return this.minAge;
    }

    public void setMinAge(Integer value) {
        this.minAge = value;
    }

    public Integer getMaxAge() {
        return this.maxAge;
    }

    public void setMaxAge(Integer value) {
        this.maxAge = value;
    }

    public TimeUnitType getAgeTimeUnit() {
        return this.ageTimeUnit;
    }

    public void setAgeTimeUnit(TimeUnitType value) {
        this.ageTimeUnit = value;
    }

    public String getAgeBucket() {
        return this.ageBucket;
    }

    public void setAgeBucket(String value) {
        this.ageBucket = value;
    }
}

