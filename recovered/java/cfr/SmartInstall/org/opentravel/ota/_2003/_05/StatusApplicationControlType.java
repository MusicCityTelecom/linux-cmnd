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
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.opentravel.ota._2003._05.DestinationSystemCodesType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="StatusApplicationControlType", propOrder={"destinationSystemCodes"})
public class StatusApplicationControlType {
    @XmlElement(name="DestinationSystemCodes")
    protected DestinationSystemCodesType destinationSystemCodes;
    @XmlAttribute(name="RateTier")
    protected String rateTier;
    @XmlAttribute(name="AllRateCode")
    protected Boolean allRateCode;
    @XmlAttribute(name="AllInvCode")
    protected Boolean allInvCode;
    @XmlAttribute(name="InvBlockCode")
    protected String invBlockCode;
    @XmlAttribute(name="Override")
    protected Boolean override;
    @XmlAttribute(name="QuoteID")
    protected String quoteID;
    @XmlAttribute(name="SubBlockCode")
    protected String subBlockCode;
    @XmlAttribute(name="WingIdentifier")
    protected String wingIdentifier;
    @XmlAttribute(name="RatePlanCodeType")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    protected String ratePlanCodeType;
    @XmlAttribute(name="InvBlockCodeApply")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    protected String invBlockCodeApply;
    @XmlAttribute(name="Mon")
    protected Boolean mon;
    @XmlAttribute(name="Tue")
    protected Boolean tue;
    @XmlAttribute(name="Weds")
    protected Boolean weds;
    @XmlAttribute(name="Thur")
    protected Boolean thur;
    @XmlAttribute(name="Fri")
    protected Boolean fri;
    @XmlAttribute(name="Sat")
    protected Boolean sat;
    @XmlAttribute(name="Sun")
    protected Boolean sun;
    @XmlAttribute(name="InvCodeApplication")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    protected String invCodeApplication;
    @XmlAttribute(name="InvCode")
    protected String invCode;
    @XmlAttribute(name="InvType")
    protected String invType;
    @XmlAttribute(name="InvTypeCode")
    protected String invTypeCode;
    @XmlAttribute(name="IsRoom")
    protected Boolean isRoom;
    @XmlAttribute(name="Start")
    protected String start;
    @XmlAttribute(name="Duration")
    protected String duration;
    @XmlAttribute(name="End")
    protected String end;
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

    public DestinationSystemCodesType getDestinationSystemCodes() {
        return this.destinationSystemCodes;
    }

    public void setDestinationSystemCodes(DestinationSystemCodesType value) {
        this.destinationSystemCodes = value;
    }

    public String getRateTier() {
        return this.rateTier;
    }

    public void setRateTier(String value) {
        this.rateTier = value;
    }

    public Boolean isAllRateCode() {
        return this.allRateCode;
    }

    public void setAllRateCode(Boolean value) {
        this.allRateCode = value;
    }

    public Boolean isAllInvCode() {
        return this.allInvCode;
    }

    public void setAllInvCode(Boolean value) {
        this.allInvCode = value;
    }

    public String getInvBlockCode() {
        return this.invBlockCode;
    }

    public void setInvBlockCode(String value) {
        this.invBlockCode = value;
    }

    public Boolean isOverride() {
        return this.override;
    }

    public void setOverride(Boolean value) {
        this.override = value;
    }

    public String getQuoteID() {
        return this.quoteID;
    }

    public void setQuoteID(String value) {
        this.quoteID = value;
    }

    public String getSubBlockCode() {
        return this.subBlockCode;
    }

    public void setSubBlockCode(String value) {
        this.subBlockCode = value;
    }

    public String getWingIdentifier() {
        return this.wingIdentifier;
    }

    public void setWingIdentifier(String value) {
        this.wingIdentifier = value;
    }

    public String getRatePlanCodeType() {
        return this.ratePlanCodeType;
    }

    public void setRatePlanCodeType(String value) {
        this.ratePlanCodeType = value;
    }

    public String getInvBlockCodeApply() {
        return this.invBlockCodeApply;
    }

    public void setInvBlockCodeApply(String value) {
        this.invBlockCodeApply = value;
    }

    public Boolean isMon() {
        return this.mon;
    }

    public void setMon(Boolean value) {
        this.mon = value;
    }

    public Boolean isTue() {
        return this.tue;
    }

    public void setTue(Boolean value) {
        this.tue = value;
    }

    public Boolean isWeds() {
        return this.weds;
    }

    public void setWeds(Boolean value) {
        this.weds = value;
    }

    public Boolean isThur() {
        return this.thur;
    }

    public void setThur(Boolean value) {
        this.thur = value;
    }

    public Boolean isFri() {
        return this.fri;
    }

    public void setFri(Boolean value) {
        this.fri = value;
    }

    public Boolean isSat() {
        return this.sat;
    }

    public void setSat(Boolean value) {
        this.sat = value;
    }

    public Boolean isSun() {
        return this.sun;
    }

    public void setSun(Boolean value) {
        this.sun = value;
    }

    public String getInvCodeApplication() {
        return this.invCodeApplication;
    }

    public void setInvCodeApplication(String value) {
        this.invCodeApplication = value;
    }

    public String getInvCode() {
        return this.invCode;
    }

    public void setInvCode(String value) {
        this.invCode = value;
    }

    public String getInvType() {
        return this.invType;
    }

    public void setInvType(String value) {
        this.invType = value;
    }

    public String getInvTypeCode() {
        return this.invTypeCode;
    }

    public void setInvTypeCode(String value) {
        this.invTypeCode = value;
    }

    public Boolean isIsRoom() {
        return this.isRoom;
    }

    public void setIsRoom(Boolean value) {
        this.isRoom = value;
    }

    public String getStart() {
        return this.start;
    }

    public void setStart(String value) {
        this.start = value;
    }

    public String getDuration() {
        return this.duration;
    }

    public void setDuration(String value) {
        this.duration = value;
    }

    public String getEnd() {
        return this.end;
    }

    public void setEnd(String value) {
        this.end = value;
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

