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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import org.opentravel.ota._2003._05.ParagraphType;
import org.opentravel.ota._2003._05.RateIndicatorType;
import org.opentravel.ota._2003._05.RateType;
import org.opentravel.ota._2003._05.RoomStayType;
import org.opentravel.ota._2003._05.TotalType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="RoomRateType", propOrder={"rates", "roomRateDescription", "features", "total", "availability"})
@XmlSeeAlso(value={RoomStayType.RoomRates.RoomRate.class})
public class RoomRateType {
    @XmlElement(name="Rates")
    protected RateType rates;
    @XmlElement(name="RoomRateDescription")
    protected List<ParagraphType> roomRateDescription;
    @XmlElement(name="Features")
    protected Features features;
    @XmlElement(name="Total")
    protected TotalType total;
    @XmlElement(name="Availability")
    protected List<Availability> availability;
    @XmlAttribute(name="BookingCode")
    protected String bookingCode;
    @XmlAttribute(name="RoomTypeCode")
    protected String roomTypeCode;
    @XmlAttribute(name="InvBlockCode")
    protected String invBlockCode;
    @XmlAttribute(name="NumberOfUnits")
    protected BigInteger numberOfUnits;
    @XmlAttribute(name="AvailabilityStatus")
    protected RateIndicatorType availabilityStatus;
    @XmlAttribute(name="RoomID")
    protected String roomID;
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
    @XmlAttribute(name="EffectiveDate")
    @XmlSchemaType(name="date")
    protected XMLGregorianCalendar effectiveDate;
    @XmlAttribute(name="ExpireDate")
    @XmlSchemaType(name="date")
    protected XMLGregorianCalendar expireDate;
    @XmlAttribute(name="ExpireDateExclusiveIndicator")
    protected Boolean expireDateExclusiveIndicator;

    public RateType getRates() {
        return this.rates;
    }

    public void setRates(RateType value) {
        this.rates = value;
    }

    public List<ParagraphType> getRoomRateDescription() {
        if (this.roomRateDescription == null) {
            this.roomRateDescription = new ArrayList<ParagraphType>();
        }
        return this.roomRateDescription;
    }

    public Features getFeatures() {
        return this.features;
    }

    public void setFeatures(Features value) {
        this.features = value;
    }

    public TotalType getTotal() {
        return this.total;
    }

    public void setTotal(TotalType value) {
        this.total = value;
    }

    public List<Availability> getAvailability() {
        if (this.availability == null) {
            this.availability = new ArrayList<Availability>();
        }
        return this.availability;
    }

    public String getBookingCode() {
        return this.bookingCode;
    }

    public void setBookingCode(String value) {
        this.bookingCode = value;
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

    public RateIndicatorType getAvailabilityStatus() {
        return this.availabilityStatus;
    }

    public void setAvailabilityStatus(RateIndicatorType value) {
        this.availabilityStatus = value;
    }

    public String getRoomID() {
        return this.roomID;
    }

    public void setRoomID(String value) {
        this.roomID = value;
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

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"feature"})
    public static class Features {
        @XmlElement(name="Feature")
        protected List<Feature> feature;

        public List<Feature> getFeature() {
            if (this.feature == null) {
                this.feature = new ArrayList<Feature>();
            }
            return this.feature;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="", propOrder={"description"})
        public static class Feature {
            @XmlElement(name="Description")
            protected List<ParagraphType> description;
            @XmlAttribute(name="RoomAmenity")
            protected String roomAmenity;
            @XmlAttribute(name="Quantity")
            protected Integer quantity;
            @XmlAttribute(name="RoomViewCode")
            protected String roomViewCode;

            public List<ParagraphType> getDescription() {
                if (this.description == null) {
                    this.description = new ArrayList<ParagraphType>();
                }
                return this.description;
            }

            public String getRoomAmenity() {
                return this.roomAmenity;
            }

            public void setRoomAmenity(String value) {
                this.roomAmenity = value;
            }

            public Integer getQuantity() {
                return this.quantity;
            }

            public void setQuantity(Integer value) {
                this.quantity = value;
            }

            public String getRoomViewCode() {
                return this.roomViewCode;
            }

            public void setRoomViewCode(String value) {
                this.roomViewCode = value;
            }
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class Availability {
        @XmlAttribute(name="AvailabilityStatus")
        protected RateIndicatorType availabilityStatus;

        public RateIndicatorType getAvailabilityStatus() {
            return this.availabilityStatus;
        }

        public void setAvailabilityStatus(RateIndicatorType value) {
            this.availabilityStatus = value;
        }
    }
}

