/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import org.opentravel.ota._2003._05.BasicPropertyInfoType;
import org.opentravel.ota._2003._05.CancelPenaltiesType;
import org.opentravel.ota._2003._05.DOWRestrictionsType;
import org.opentravel.ota._2003._05.DateTimeSpanType;
import org.opentravel.ota._2003._05.DiscountType;
import org.opentravel.ota._2003._05.GuaranteeType;
import org.opentravel.ota._2003._05.GuestCountType;
import org.opentravel.ota._2003._05.HotelRoomListType;
import org.opentravel.ota._2003._05.OTAHotelAvailRS;
import org.opentravel.ota._2003._05.RatePlanType;
import org.opentravel.ota._2003._05.RequiredPaymentsType;
import org.opentravel.ota._2003._05.RoomRateType;
import org.opentravel.ota._2003._05.RoomStaysType;
import org.opentravel.ota._2003._05.RoomTypeType;
import org.opentravel.ota._2003._05.ServiceRPHsType;
import org.opentravel.ota._2003._05.TPAExtensionsType;
import org.opentravel.ota._2003._05.TotalType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="RoomStayType", propOrder={"roomTypes", "ratePlans", "roomRates", "guestCounts", "timeSpan", "guarantee", "depositPayments", "cancelPenalties", "discount", "total", "basicPropertyInfo", "mapURL", "tpaExtensions"})
@XmlSeeAlso(value={OTAHotelAvailRS.RoomStays.RoomStay.class, RoomStaysType.RoomStay.class, HotelRoomListType.RoomStays.RoomStay.class})
public class RoomStayType {
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
    @XmlElement(name="DepositPayments")
    protected RequiredPaymentsType depositPayments;
    @XmlElement(name="CancelPenalties")
    protected CancelPenaltiesType cancelPenalties;
    @XmlElement(name="Discount")
    protected DiscountType discount;
    @XmlElement(name="Total")
    protected TotalType total;
    @XmlElement(name="BasicPropertyInfo")
    protected BasicPropertyInfoType basicPropertyInfo;
    @XmlElement(name="MapURL")
    protected MapURL mapURL;
    @XmlElement(name="TPA_Extensions")
    protected TPAExtensionsType tpaExtensions;
    @XmlAttribute(name="MarketCode")
    protected String marketCode;
    @XmlAttribute(name="SourceOfBusiness")
    protected String sourceOfBusiness;
    @XmlAttribute(name="DiscountCode")
    protected String discountCode;
    @XmlAttribute(name="RoomStayStatus")
    protected String roomStayStatus;
    @XmlAttribute(name="WarningRPH")
    protected List<String> warningRPH;
    @XmlAttribute(name="RoomStayLanguage")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    @XmlSchemaType(name="language")
    protected String roomStayLanguage;
    @XmlAttribute(name="PromotionCode")
    protected String promotionCode;
    @XmlAttribute(name="PromotionVendorCode")
    protected List<String> promotionVendorCode;

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

    public RequiredPaymentsType getDepositPayments() {
        return this.depositPayments;
    }

    public void setDepositPayments(RequiredPaymentsType value) {
        this.depositPayments = value;
    }

    public CancelPenaltiesType getCancelPenalties() {
        return this.cancelPenalties;
    }

    public void setCancelPenalties(CancelPenaltiesType value) {
        this.cancelPenalties = value;
    }

    public DiscountType getDiscount() {
        return this.discount;
    }

    public void setDiscount(DiscountType value) {
        this.discount = value;
    }

    public TotalType getTotal() {
        return this.total;
    }

    public void setTotal(TotalType value) {
        this.total = value;
    }

    public BasicPropertyInfoType getBasicPropertyInfo() {
        return this.basicPropertyInfo;
    }

    public void setBasicPropertyInfo(BasicPropertyInfoType value) {
        this.basicPropertyInfo = value;
    }

    public MapURL getMapURL() {
        return this.mapURL;
    }

    public void setMapURL(MapURL value) {
        this.mapURL = value;
    }

    public TPAExtensionsType getTPAExtensions() {
        return this.tpaExtensions;
    }

    public void setTPAExtensions(TPAExtensionsType value) {
        this.tpaExtensions = value;
    }

    public String getMarketCode() {
        return this.marketCode;
    }

    public void setMarketCode(String value) {
        this.marketCode = value;
    }

    public String getSourceOfBusiness() {
        return this.sourceOfBusiness;
    }

    public void setSourceOfBusiness(String value) {
        this.sourceOfBusiness = value;
    }

    public String getDiscountCode() {
        return this.discountCode;
    }

    public void setDiscountCode(String value) {
        this.discountCode = value;
    }

    public String getRoomStayStatus() {
        return this.roomStayStatus;
    }

    public void setRoomStayStatus(String value) {
        this.roomStayStatus = value;
    }

    public List<String> getWarningRPH() {
        if (this.warningRPH == null) {
            this.warningRPH = new ArrayList<String>();
        }
        return this.warningRPH;
    }

    public String getRoomStayLanguage() {
        return this.roomStayLanguage;
    }

    public void setRoomStayLanguage(String value) {
        this.roomStayLanguage = value;
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

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"roomType"})
    public static class RoomTypes {
        @XmlElement(name="RoomType", required=true)
        protected List<RoomTypeType> roomType;

        public List<RoomTypeType> getRoomType() {
            if (this.roomType == null) {
                this.roomType = new ArrayList<RoomTypeType>();
            }
            return this.roomType;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"roomRate", "tpaExtensions"})
    public static class RoomRates {
        @XmlElement(name="RoomRate", required=true)
        protected List<RoomRate> roomRate;
        @XmlElement(name="TPA_Extensions")
        protected TPAExtensionsType tpaExtensions;
        @XmlAttribute(name="MoreRatesExistInd")
        protected Boolean moreRatesExistInd;

        public List<RoomRate> getRoomRate() {
            if (this.roomRate == null) {
                this.roomRate = new ArrayList<RoomRate>();
            }
            return this.roomRate;
        }

        public TPAExtensionsType getTPAExtensions() {
            return this.tpaExtensions;
        }

        public void setTPAExtensions(TPAExtensionsType value) {
            this.tpaExtensions = value;
        }

        public Boolean isMoreRatesExistInd() {
            return this.moreRatesExistInd;
        }

        public void setMoreRatesExistInd(Boolean value) {
            this.moreRatesExistInd = value;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="", propOrder={"advanceBookingRestriction", "restrictions", "serviceRPHs", "guestCounts"})
        public static class RoomRate
        extends RoomRateType {
            @XmlElement(name="AdvanceBookingRestriction")
            protected AdvanceBookingRestriction advanceBookingRestriction;
            @XmlElement(name="Restrictions")
            protected Restrictions restrictions;
            @XmlElement(name="ServiceRPHs")
            protected ServiceRPHsType serviceRPHs;
            @XmlElement(name="GuestCounts")
            protected GuestCounts guestCounts;

            public AdvanceBookingRestriction getAdvanceBookingRestriction() {
                return this.advanceBookingRestriction;
            }

            public void setAdvanceBookingRestriction(AdvanceBookingRestriction value) {
                this.advanceBookingRestriction = value;
            }

            public Restrictions getRestrictions() {
                return this.restrictions;
            }

            public void setRestrictions(Restrictions value) {
                this.restrictions = value;
            }

            public ServiceRPHsType getServiceRPHs() {
                return this.serviceRPHs;
            }

            public void setServiceRPHs(ServiceRPHsType value) {
                this.serviceRPHs = value;
            }

            public GuestCounts getGuestCounts() {
                return this.guestCounts;
            }

            public void setGuestCounts(GuestCounts value) {
                this.guestCounts = value;
            }

            @XmlAccessorType(value=XmlAccessType.FIELD)
            @XmlType(name="", propOrder={"restriction"})
            public static class Restrictions {
                @XmlElement(name="Restriction", required=true)
                protected List<Restriction> restriction;

                public List<Restriction> getRestriction() {
                    if (this.restriction == null) {
                        this.restriction = new ArrayList<Restriction>();
                    }
                    return this.restriction;
                }

                @XmlAccessorType(value=XmlAccessType.FIELD)
                @XmlType(name="", propOrder={"dowRestrictions"})
                public static class Restriction {
                    @XmlElement(name="DOW_Restrictions", required=true)
                    protected DOWRestrictionsType dowRestrictions;
                    @XmlAttribute(name="EffectiveDate")
                    @XmlSchemaType(name="date")
                    protected XMLGregorianCalendar effectiveDate;
                    @XmlAttribute(name="ExpireDate")
                    @XmlSchemaType(name="date")
                    protected XMLGregorianCalendar expireDate;
                    @XmlAttribute(name="ExpireDateExclusiveIndicator")
                    protected Boolean expireDateExclusiveIndicator;

                    public DOWRestrictionsType getDOWRestrictions() {
                        return this.dowRestrictions;
                    }

                    public void setDOWRestrictions(DOWRestrictionsType value) {
                        this.dowRestrictions = value;
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
                }
            }

            @XmlAccessorType(value=XmlAccessType.FIELD)
            @XmlType(name="", propOrder={"guestCount"})
            public static class GuestCounts {
                @XmlElement(name="GuestCount", required=true)
                protected List<GuestCount> guestCount;

                public List<GuestCount> getGuestCount() {
                    if (this.guestCount == null) {
                        this.guestCount = new ArrayList<GuestCount>();
                    }
                    return this.guestCount;
                }

                @XmlAccessorType(value=XmlAccessType.FIELD)
                @XmlType(name="")
                public static class GuestCount {
                    @XmlAttribute(name="AgeQualifyingCode")
                    protected String ageQualifyingCode;
                    @XmlAttribute(name="Age")
                    protected Integer age;
                    @XmlAttribute(name="Count")
                    protected Integer count;
                    @XmlAttribute(name="AgeBucket")
                    protected String ageBucket;

                    public String getAgeQualifyingCode() {
                        return this.ageQualifyingCode;
                    }

                    public void setAgeQualifyingCode(String value) {
                        this.ageQualifyingCode = value;
                    }

                    public Integer getAge() {
                        return this.age;
                    }

                    public void setAge(Integer value) {
                        this.age = value;
                    }

                    public Integer getCount() {
                        return this.count;
                    }

                    public void setCount(Integer value) {
                        this.count = value;
                    }

                    public String getAgeBucket() {
                        return this.ageBucket;
                    }

                    public void setAgeBucket(String value) {
                        this.ageBucket = value;
                    }
                }
            }

            @XmlAccessorType(value=XmlAccessType.FIELD)
            @XmlType(name="")
            public static class AdvanceBookingRestriction {
                @XmlAttribute(name="MinAdvanceBookingOffset")
                protected Duration minAdvanceBookingOffset;
                @XmlAttribute(name="MaxAdvanceBookingOffset")
                protected Duration maxAdvanceBookingOffset;
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
                @XmlAttribute(name="Start")
                protected String start;
                @XmlAttribute(name="Duration")
                protected String duration;
                @XmlAttribute(name="End")
                protected String end;

                public Duration getMinAdvanceBookingOffset() {
                    return this.minAdvanceBookingOffset;
                }

                public void setMinAdvanceBookingOffset(Duration value) {
                    this.minAdvanceBookingOffset = value;
                }

                public Duration getMaxAdvanceBookingOffset() {
                    return this.maxAdvanceBookingOffset;
                }

                public void setMaxAdvanceBookingOffset(Duration value) {
                    this.maxAdvanceBookingOffset = value;
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
            }
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"ratePlan"})
    public static class RatePlans {
        @XmlElement(name="RatePlan", required=true)
        protected List<RatePlanType> ratePlan;

        public List<RatePlanType> getRatePlan() {
            if (this.ratePlan == null) {
                this.ratePlan = new ArrayList<RatePlanType>();
            }
            return this.ratePlan;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"value"})
    public static class MapURL {
        @XmlValue
        @XmlSchemaType(name="anyURI")
        protected String value;
        @XmlAttribute(name="BottomRightLatitude")
        protected BigDecimal bottomRightLatitude;
        @XmlAttribute(name="BottomRightLongitude")
        protected BigDecimal bottomRightLongitude;
        @XmlAttribute(name="TopLeftLatitude")
        protected BigDecimal topLeftLatitude;
        @XmlAttribute(name="TopLeftLongitude")
        protected BigDecimal topLeftLongitude;
        @XmlAttribute(name="Height")
        protected Integer height;
        @XmlAttribute(name="Width")
        protected Integer width;
        @XmlAttribute(name="ZoomFactor")
        protected Integer zoomFactor;

        public String getValue() {
            return this.value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public BigDecimal getBottomRightLatitude() {
            return this.bottomRightLatitude;
        }

        public void setBottomRightLatitude(BigDecimal value) {
            this.bottomRightLatitude = value;
        }

        public BigDecimal getBottomRightLongitude() {
            return this.bottomRightLongitude;
        }

        public void setBottomRightLongitude(BigDecimal value) {
            this.bottomRightLongitude = value;
        }

        public BigDecimal getTopLeftLatitude() {
            return this.topLeftLatitude;
        }

        public void setTopLeftLatitude(BigDecimal value) {
            this.topLeftLatitude = value;
        }

        public BigDecimal getTopLeftLongitude() {
            return this.topLeftLongitude;
        }

        public void setTopLeftLongitude(BigDecimal value) {
            this.topLeftLongitude = value;
        }

        public Integer getHeight() {
            return this.height;
        }

        public void setHeight(Integer value) {
            this.height = value;
        }

        public Integer getWidth() {
            return this.width;
        }

        public void setWidth(Integer value) {
            this.width = value;
        }

        public Integer getZoomFactor() {
            return this.zoomFactor;
        }

        public void setZoomFactor(Integer value) {
            this.zoomFactor = value;
        }
    }
}

