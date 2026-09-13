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
import org.opentravel.ota._2003._05.DateTimeSpanType;
import org.opentravel.ota._2003._05.GuaranteeType;
import org.opentravel.ota._2003._05.GuestCountType;
import org.opentravel.ota._2003._05.RatePlanLiteType;
import org.opentravel.ota._2003._05.RequiredPaymentLiteType;
import org.opentravel.ota._2003._05.RoomRateLiteType;
import org.opentravel.ota._2003._05.RoomTypeLiteType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="RoomStayLiteType", propOrder={"roomTypes", "ratePlans", "roomRates", "guestCounts", "timeSpan", "guarantee", "depositPayment", "basicPropertyInfo"})
public class RoomStayLiteType {
    @XmlElement(name="RoomTypes")
    protected RoomTypes roomTypes;
    @XmlElement(name="RatePlans")
    protected RatePlans ratePlans;
    @XmlElement(name="RoomRates")
    protected RoomRates roomRates;
    @XmlElement(name="GuestCounts")
    protected GuestCountType guestCounts;
    @XmlElement(name="TimeSpan")
    protected DateTimeSpanType timeSpan;
    @XmlElement(name="Guarantee")
    protected List<GuaranteeType> guarantee;
    @XmlElement(name="DepositPayment")
    protected List<RequiredPaymentLiteType> depositPayment;
    @XmlElement(name="BasicPropertyInfo")
    protected BasicPropertyInfo basicPropertyInfo;

    public RoomTypes getRoomTypes() {
        return this.roomTypes;
    }

    public void setRoomTypes(RoomTypes value) {
        this.roomTypes = value;
    }

    public RatePlans getRatePlans() {
        return this.ratePlans;
    }

    public void setRatePlans(RatePlans value) {
        this.ratePlans = value;
    }

    public RoomRates getRoomRates() {
        return this.roomRates;
    }

    public void setRoomRates(RoomRates value) {
        this.roomRates = value;
    }

    public GuestCountType getGuestCounts() {
        return this.guestCounts;
    }

    public void setGuestCounts(GuestCountType value) {
        this.guestCounts = value;
    }

    public DateTimeSpanType getTimeSpan() {
        return this.timeSpan;
    }

    public void setTimeSpan(DateTimeSpanType value) {
        this.timeSpan = value;
    }

    public List<GuaranteeType> getGuarantee() {
        if (this.guarantee == null) {
            this.guarantee = new ArrayList<GuaranteeType>();
        }
        return this.guarantee;
    }

    public List<RequiredPaymentLiteType> getDepositPayment() {
        if (this.depositPayment == null) {
            this.depositPayment = new ArrayList<RequiredPaymentLiteType>();
        }
        return this.depositPayment;
    }

    public BasicPropertyInfo getBasicPropertyInfo() {
        return this.basicPropertyInfo;
    }

    public void setBasicPropertyInfo(BasicPropertyInfo value) {
        this.basicPropertyInfo = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"roomType"})
    public static class RoomTypes {
        @XmlElement(name="RoomType", required=true)
        protected List<RoomTypeLiteType> roomType;

        public List<RoomTypeLiteType> getRoomType() {
            if (this.roomType == null) {
                this.roomType = new ArrayList<RoomTypeLiteType>();
            }
            return this.roomType;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"roomRate"})
    public static class RoomRates {
        @XmlElement(name="RoomRate", required=true)
        protected List<RoomRateLiteType> roomRate;

        public List<RoomRateLiteType> getRoomRate() {
            if (this.roomRate == null) {
                this.roomRate = new ArrayList<RoomRateLiteType>();
            }
            return this.roomRate;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"ratePlan"})
    public static class RatePlans {
        @XmlElement(name="RatePlan", required=true)
        protected List<RatePlanLiteType> ratePlan;

        public List<RatePlanLiteType> getRatePlan() {
            if (this.ratePlan == null) {
                this.ratePlan = new ArrayList<RatePlanLiteType>();
            }
            return this.ratePlan;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class BasicPropertyInfo {
        @XmlAttribute(name="ChainCode")
        protected String chainCode;
        @XmlAttribute(name="BrandCode")
        protected String brandCode;
        @XmlAttribute(name="HotelCode")
        protected String hotelCode;
        @XmlAttribute(name="HotelCityCode")
        protected String hotelCityCode;
        @XmlAttribute(name="HotelName")
        protected String hotelName;
        @XmlAttribute(name="HotelCodeContext")
        protected String hotelCodeContext;
        @XmlAttribute(name="ChainName")
        protected String chainName;
        @XmlAttribute(name="BrandName")
        protected String brandName;
        @XmlAttribute(name="AreaID")
        protected String areaID;

        public String getChainCode() {
            return this.chainCode;
        }

        public void setChainCode(String value) {
            this.chainCode = value;
        }

        public String getBrandCode() {
            return this.brandCode;
        }

        public void setBrandCode(String value) {
            this.brandCode = value;
        }

        public String getHotelCode() {
            return this.hotelCode;
        }

        public void setHotelCode(String value) {
            this.hotelCode = value;
        }

        public String getHotelCityCode() {
            return this.hotelCityCode;
        }

        public void setHotelCityCode(String value) {
            this.hotelCityCode = value;
        }

        public String getHotelName() {
            return this.hotelName;
        }

        public void setHotelName(String value) {
            this.hotelName = value;
        }

        public String getHotelCodeContext() {
            return this.hotelCodeContext;
        }

        public void setHotelCodeContext(String value) {
            this.hotelCodeContext = value;
        }

        public String getChainName() {
            return this.chainName;
        }

        public void setChainName(String value) {
            this.chainName = value;
        }

        public String getBrandName() {
            return this.brandName;
        }

        public void setBrandName(String value) {
            this.brandName = value;
        }

        public String getAreaID() {
            return this.areaID;
        }

        public void setAreaID(String value) {
            this.areaID = value;
        }
    }
}

