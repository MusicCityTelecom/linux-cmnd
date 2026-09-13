/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.TravelPurposeType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="RailRateQualifyingType", propOrder={"travelPurpose", "discountType"})
public class RailRateQualifyingType {
    @XmlElement(name="TravelPurpose")
    protected TravelPurposeType travelPurpose;
    @XmlElement(name="DiscountType")
    protected DiscountType discountType;
    @XmlAttribute(name="PromotionCode")
    protected String promotionCode;

    public TravelPurposeType getTravelPurpose() {
        return this.travelPurpose;
    }

    public void setTravelPurpose(TravelPurposeType value) {
        this.travelPurpose = value;
    }

    public DiscountType getDiscountType() {
        return this.discountType;
    }

    public void setDiscountType(DiscountType value) {
        this.discountType = value;
    }

    public String getPromotionCode() {
        return this.promotionCode;
    }

    public void setPromotionCode(String value) {
        this.promotionCode = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class DiscountType {
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

