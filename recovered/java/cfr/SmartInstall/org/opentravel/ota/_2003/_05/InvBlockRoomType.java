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
import org.opentravel.ota._2003._05.CommissionType;
import org.opentravel.ota._2003._05.DOWRulesType;
import org.opentravel.ota._2003._05.RateUploadType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="InvBlockRoomType", propOrder={"roomTypeAllocations", "ratePlans", "daysOfWeeks"})
public class InvBlockRoomType {
    @XmlElement(name="RoomTypeAllocations")
    protected List<RoomTypeAllocations> roomTypeAllocations;
    @XmlElement(name="RatePlans")
    protected RatePlans ratePlans;
    @XmlElement(name="DaysOfWeeks")
    protected DaysOfWeeks daysOfWeeks;
    @XmlAttribute(name="RoomTypeCode")
    protected String roomTypeCode;
    @XmlAttribute(name="Start")
    protected String start;
    @XmlAttribute(name="Duration")
    protected String duration;
    @XmlAttribute(name="End")
    protected String end;

    public List<RoomTypeAllocations> getRoomTypeAllocations() {
        if (this.roomTypeAllocations == null) {
            this.roomTypeAllocations = new ArrayList<RoomTypeAllocations>();
        }
        return this.roomTypeAllocations;
    }

    public RatePlans getRatePlans() {
        return this.ratePlans;
    }

    public void setRatePlans(RatePlans value) {
        this.ratePlans = value;
    }

    public DaysOfWeeks getDaysOfWeeks() {
        return this.daysOfWeeks;
    }

    public void setDaysOfWeeks(DaysOfWeeks value) {
        this.daysOfWeeks = value;
    }

    public String getRoomTypeCode() {
        return this.roomTypeCode;
    }

    public void setRoomTypeCode(String value) {
        this.roomTypeCode = value;
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

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"roomTypeAllocation"})
    public static class RoomTypeAllocations {
        @XmlElement(name="RoomTypeAllocation", required=true)
        protected List<RoomTypeAllocation> roomTypeAllocation;
        @XmlAttribute(name="RoomTypePickUpStatus")
        protected String roomTypePickUpStatus;

        public List<RoomTypeAllocation> getRoomTypeAllocation() {
            if (this.roomTypeAllocation == null) {
                this.roomTypeAllocation = new ArrayList<RoomTypeAllocation>();
            }
            return this.roomTypeAllocation;
        }

        public String getRoomTypePickUpStatus() {
            return this.roomTypePickUpStatus;
        }

        public void setRoomTypePickUpStatus(String value) {
            this.roomTypePickUpStatus = value;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="")
        public static class RoomTypeAllocation {
            @XmlAttribute(name="NumberOfUnits")
            protected BigInteger numberOfUnits;
            @XmlAttribute(name="CompRoomQuantity")
            protected Integer compRoomQuantity;
            @XmlAttribute(name="CompRoomFactor")
            protected Integer compRoomFactor;
            @XmlAttribute(name="EndDateIndicator")
            protected Boolean endDateIndicator;
            @XmlAttribute(name="SellLimit")
            @XmlSchemaType(name="positiveInteger")
            protected BigInteger sellLimit;
            @XmlAttribute(name="ProcureBlockCode")
            protected String procureBlockCode;
            @XmlAttribute(name="AllocationID")
            protected String allocationID;
            @XmlAttribute(name="Start")
            protected String start;
            @XmlAttribute(name="Duration")
            protected String duration;
            @XmlAttribute(name="End")
            protected String end;

            public BigInteger getNumberOfUnits() {
                return this.numberOfUnits;
            }

            public void setNumberOfUnits(BigInteger value) {
                this.numberOfUnits = value;
            }

            public Integer getCompRoomQuantity() {
                return this.compRoomQuantity;
            }

            public void setCompRoomQuantity(Integer value) {
                this.compRoomQuantity = value;
            }

            public Integer getCompRoomFactor() {
                return this.compRoomFactor;
            }

            public void setCompRoomFactor(Integer value) {
                this.compRoomFactor = value;
            }

            public Boolean isEndDateIndicator() {
                return this.endDateIndicator;
            }

            public void setEndDateIndicator(Boolean value) {
                this.endDateIndicator = value;
            }

            public BigInteger getSellLimit() {
                return this.sellLimit;
            }

            public void setSellLimit(BigInteger value) {
                this.sellLimit = value;
            }

            public String getProcureBlockCode() {
                return this.procureBlockCode;
            }

            public void setProcureBlockCode(String value) {
                this.procureBlockCode = value;
            }

            public String getAllocationID() {
                return this.allocationID;
            }

            public void setAllocationID(String value) {
                this.allocationID = value;
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

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"ratePlan"})
    public static class RatePlans {
        @XmlElement(name="RatePlan", required=true)
        protected List<RatePlan> ratePlan;

        public List<RatePlan> getRatePlan() {
            if (this.ratePlan == null) {
                this.ratePlan = new ArrayList<RatePlan>();
            }
            return this.ratePlan;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="", propOrder={"marketCode", "commission", "methodInfo", "daysOfWeeks"})
        public static class RatePlan
        extends RateUploadType {
            @XmlElement(name="MarketCode")
            protected List<MarketCode> marketCode;
            @XmlElement(name="Commission")
            protected CommissionType commission;
            @XmlElement(name="MethodInfo")
            protected List<MethodInfo> methodInfo;
            @XmlElement(name="DaysOfWeeks")
            protected DaysOfWeeks daysOfWeeks;
            @XmlAttribute(name="RatePlanCode")
            protected String ratePlanCode;
            @XmlAttribute(name="BookingCode")
            protected String bookingCode;
            @XmlAttribute(name="UpgradeIndicator")
            protected Boolean upgradeIndicator;
            @XmlAttribute(name="PromotionCode")
            protected String promotionCode;
            @XmlAttribute(name="PromotionVendorCode")
            protected List<String> promotionVendorCode;

            public List<MarketCode> getMarketCode() {
                if (this.marketCode == null) {
                    this.marketCode = new ArrayList<MarketCode>();
                }
                return this.marketCode;
            }

            public CommissionType getCommission() {
                return this.commission;
            }

            public void setCommission(CommissionType value) {
                this.commission = value;
            }

            public List<MethodInfo> getMethodInfo() {
                if (this.methodInfo == null) {
                    this.methodInfo = new ArrayList<MethodInfo>();
                }
                return this.methodInfo;
            }

            public DaysOfWeeks getDaysOfWeeks() {
                return this.daysOfWeeks;
            }

            public void setDaysOfWeeks(DaysOfWeeks value) {
                this.daysOfWeeks = value;
            }

            public String getRatePlanCode() {
                return this.ratePlanCode;
            }

            public void setRatePlanCode(String value) {
                this.ratePlanCode = value;
            }

            public String getBookingCode() {
                return this.bookingCode;
            }

            public void setBookingCode(String value) {
                this.bookingCode = value;
            }

            public Boolean isUpgradeIndicator() {
                return this.upgradeIndicator;
            }

            public void setUpgradeIndicator(Boolean value) {
                this.upgradeIndicator = value;
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
            @XmlType(name="")
            public static class MethodInfo {
                @XmlAttribute(name="ReservationMethodCode")
                protected String reservationMethodCode;
                @XmlAttribute(name="BillingType")
                protected String billingType;
                @XmlAttribute(name="SignFoodAndBev")
                protected Boolean signFoodAndBev;

                public String getReservationMethodCode() {
                    return this.reservationMethodCode;
                }

                public void setReservationMethodCode(String value) {
                    this.reservationMethodCode = value;
                }

                public String getBillingType() {
                    return this.billingType;
                }

                public void setBillingType(String value) {
                    this.billingType = value;
                }

                public Boolean isSignFoodAndBev() {
                    return this.signFoodAndBev;
                }

                public void setSignFoodAndBev(Boolean value) {
                    this.signFoodAndBev = value;
                }
            }

            @XmlAccessorType(value=XmlAccessType.FIELD)
            @XmlType(name="")
            public static class MarketCode {
                @XmlAttribute(name="MarketCode")
                protected String marketCode;
                @XmlAttribute(name="MarketCodeName")
                protected String marketCodeName;
                @XmlAttribute(name="CommissionableIndicator")
                protected Boolean commissionableIndicator;

                public String getMarketCode() {
                    return this.marketCode;
                }

                public void setMarketCode(String value) {
                    this.marketCode = value;
                }

                public String getMarketCodeName() {
                    return this.marketCodeName;
                }

                public void setMarketCodeName(String value) {
                    this.marketCodeName = value;
                }

                public Boolean isCommissionableIndicator() {
                    return this.commissionableIndicator;
                }

                public void setCommissionableIndicator(Boolean value) {
                    this.commissionableIndicator = value;
                }
            }

            @XmlAccessorType(value=XmlAccessType.FIELD)
            @XmlType(name="", propOrder={"daysOfWeek"})
            public static class DaysOfWeeks {
                @XmlElement(name="DaysOfWeek", required=true)
                protected List<DOWRulesType> daysOfWeek;

                public List<DOWRulesType> getDaysOfWeek() {
                    if (this.daysOfWeek == null) {
                        this.daysOfWeek = new ArrayList<DOWRulesType>();
                    }
                    return this.daysOfWeek;
                }
            }
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"daysOfWeek"})
    public static class DaysOfWeeks {
        @XmlElement(name="DaysOfWeek", required=true)
        protected List<DOWRulesType> daysOfWeek;

        public List<DOWRulesType> getDaysOfWeek() {
            if (this.daysOfWeek == null) {
                this.daysOfWeek = new ArrayList<DOWRulesType>();
            }
            return this.daysOfWeek;
        }
    }
}

