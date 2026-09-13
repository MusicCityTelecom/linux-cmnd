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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import org.opentravel.ota._2003._05.RateLiteType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="RoomRateLiteType", propOrder={"rates"})
public class RoomRateLiteType {
    @XmlElement(name="Rates", required=true)
    protected RateLiteType rates;
    @XmlAttribute(name="RoomTypeCode")
    protected String roomTypeCode;
    @XmlAttribute(name="InvBlockCode")
    protected String invBlockCode;
    @XmlAttribute(name="NumberOfUnits")
    protected BigInteger numberOfUnits;
    @XmlAttribute(name="EffectiveDate")
    @XmlSchemaType(name="date")
    protected XMLGregorianCalendar effectiveDate;
    @XmlAttribute(name="ExpireDate")
    @XmlSchemaType(name="date")
    protected XMLGregorianCalendar expireDate;
    @XmlAttribute(name="ExpireDateExclusiveIndicator")
    protected Boolean expireDateExclusiveIndicator;
    @XmlAttribute(name="RatePlanType")
    protected String ratePlanType;
    @XmlAttribute(name="RatePlanCode")
    protected String ratePlanCode;
    @XmlAttribute(name="RatePlanID")
    protected String ratePlanID;
    @XmlAttribute(name="RatePlanQualifier")
    protected Boolean ratePlanQualifier;
    @XmlAttribute(name="RatePlanCategory")
    protected String ratePlanCategory;
    @XmlAttribute(name="PromotionCode")
    protected String promotionCode;
    @XmlAttribute(name="PromotionVendorCode")
    protected List<String> promotionVendorCode;

    public RateLiteType getRates() {
        return this.rates;
    }

    public void setRates(RateLiteType value) {
        this.rates = value;
    }

    public String getRoomTypeCode() {
        return this.roomTypeCode;
    }

    public void setRoomTypeCode(String value) {
        this.roomTypeCode = value;
    }

    public String getInvBlockCode() {
        return this.invBlockCode;
    }

    public void setInvBlockCode(String value) {
        this.invBlockCode = value;
    }

    public BigInteger getNumberOfUnits() {
        return this.numberOfUnits;
    }

    public void setNumberOfUnits(BigInteger value) {
        this.numberOfUnits = value;
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

    public String getRatePlanType() {
        return this.ratePlanType;
    }

    public void setRatePlanType(String value) {
        this.ratePlanType = value;
    }

    public String getRatePlanCode() {
        return this.ratePlanCode;
    }

    public void setRatePlanCode(String value) {
        this.ratePlanCode = value;
    }

    public String getRatePlanID() {
        return this.ratePlanID;
    }

    public void setRatePlanID(String value) {
        this.ratePlanID = value;
    }

    public Boolean isRatePlanQualifier() {
        return this.ratePlanQualifier;
    }

    public void setRatePlanQualifier(Boolean value) {
        this.ratePlanQualifier = value;
    }

    public String getRatePlanCategory() {
        return this.ratePlanCategory;
    }

    public void setRatePlanCategory(String value) {
        this.ratePlanCategory = value;
    }

    public String getPromotionCode() {
        return this.promotionCode;
    }

    public void setPromotionCode(String value) {
        this.promotionCode = value;
    }

    public List<String> getPromotionVendorCode() {
        if (this.promotionVendorCode == null) {
            this.promotionVendorCode = new ArrayList<String>();
        }
        return this.promotionVendorCode;
    }
}

