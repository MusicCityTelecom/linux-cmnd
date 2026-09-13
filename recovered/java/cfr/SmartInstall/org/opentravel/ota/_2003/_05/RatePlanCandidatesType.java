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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.opentravel.ota._2003._05.HotelSearchCriterionType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="RatePlanCandidatesType", propOrder={"ratePlanCandidate"})
@XmlSeeAlso(value={HotelSearchCriterionType.RatePlanCandidates.class})
public class RatePlanCandidatesType {
    @XmlElement(name="RatePlanCandidate", required=true)
    protected List<RatePlanCandidate> ratePlanCandidate;

    public List<RatePlanCandidate> getRatePlanCandidate() {
        if (this.ratePlanCandidate == null) {
            this.ratePlanCandidate = new ArrayList<RatePlanCandidate>();
        }
        return this.ratePlanCandidate;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"hotelRefs", "mealsIncluded", "arrivalPolicy", "ratePlanCommission"})
    public static class RatePlanCandidate {
        @XmlElement(name="HotelRefs")
        protected HotelRefs hotelRefs;
        @XmlElement(name="MealsIncluded")
        protected MealsIncluded mealsIncluded;
        @XmlElement(name="ArrivalPolicy")
        protected ArrivalPolicy arrivalPolicy;
        @XmlElement(name="RatePlanCommission")
        protected RatePlanCommission ratePlanCommission;
        @XmlAttribute(name="RPH")
        protected String rph;
        @XmlAttribute(name="PrepaidQualifier")
        @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
        protected String prepaidQualifier;
        @XmlAttribute(name="AvailRatesOnlyInd")
        protected Boolean availRatesOnlyInd;
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

        public HotelRefs getHotelRefs() {
            return this.hotelRefs;
        }

        public void setHotelRefs(HotelRefs value) {
            this.hotelRefs = value;
        }

        public MealsIncluded getMealsIncluded() {
            return this.mealsIncluded;
        }

        public void setMealsIncluded(MealsIncluded value) {
            this.mealsIncluded = value;
        }

        public ArrivalPolicy getArrivalPolicy() {
            return this.arrivalPolicy;
        }

        public void setArrivalPolicy(ArrivalPolicy value) {
            this.arrivalPolicy = value;
        }

        public RatePlanCommission getRatePlanCommission() {
            return this.ratePlanCommission;
        }

        public void setRatePlanCommission(RatePlanCommission value) {
            this.ratePlanCommission = value;
        }

        public String getRPH() {
            return this.rph;
        }

        public void setRPH(String value) {
            this.rph = value;
        }

        public String getPrepaidQualifier() {
            return this.prepaidQualifier;
        }

        public void setPrepaidQualifier(String value) {
            this.prepaidQualifier = value;
        }

        public Boolean isAvailRatesOnlyInd() {
            return this.availRatesOnlyInd;
        }

        public void setAvailRatesOnlyInd(Boolean value) {
            this.availRatesOnlyInd = value;
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

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="")
        public static class RatePlanCommission {
            @XmlAttribute(name="MaxCommissionPercentage")
            protected BigDecimal maxCommissionPercentage;
            @XmlAttribute(name="MinCommissionPercentage")
            protected BigDecimal minCommissionPercentage;
            @XmlAttribute(name="CommissionableIndicator")
            protected Boolean commissionableIndicator;

            public BigDecimal getMaxCommissionPercentage() {
                return this.maxCommissionPercentage;
            }

            public void setMaxCommissionPercentage(BigDecimal value) {
                this.maxCommissionPercentage = value;
            }

            public BigDecimal getMinCommissionPercentage() {
                return this.minCommissionPercentage;
            }

            public void setMinCommissionPercentage(BigDecimal value) {
                this.minCommissionPercentage = value;
            }

            public Boolean isCommissionableIndicator() {
                return this.commissionableIndicator;
            }

            public void setCommissionableIndicator(Boolean value) {
                this.commissionableIndicator = value;
            }
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="")
        public static class MealsIncluded {
            @XmlAttribute(name="Breakfast")
            protected Boolean breakfast;
            @XmlAttribute(name="Lunch")
            protected Boolean lunch;
            @XmlAttribute(name="Dinner")
            protected Boolean dinner;
            @XmlAttribute(name="MealPlanIndicator")
            protected Boolean mealPlanIndicator;
            @XmlAttribute(name="MealPlanCodes")
            protected List<String> mealPlanCodes;

            public Boolean isBreakfast() {
                return this.breakfast;
            }

            public void setBreakfast(Boolean value) {
                this.breakfast = value;
            }

            public Boolean isLunch() {
                return this.lunch;
            }

            public void setLunch(Boolean value) {
                this.lunch = value;
            }

            public Boolean isDinner() {
                return this.dinner;
            }

            public void setDinner(Boolean value) {
                this.dinner = value;
            }

            public Boolean isMealPlanIndicator() {
                return this.mealPlanIndicator;
            }

            public void setMealPlanIndicator(Boolean value) {
                this.mealPlanIndicator = value;
            }

            public List<String> getMealPlanCodes() {
                if (this.mealPlanCodes == null) {
                    this.mealPlanCodes = new ArrayList<String>();
                }
                return this.mealPlanCodes;
            }
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="", propOrder={"hotelRef"})
        public static class HotelRefs {
            @XmlElement(name="HotelRef", required=true)
            protected List<HotelRef> hotelRef;

            public List<HotelRef> getHotelRef() {
                if (this.hotelRef == null) {
                    this.hotelRef = new ArrayList<HotelRef>();
                }
                return this.hotelRef;
            }

            @XmlAccessorType(value=XmlAccessType.FIELD)
            @XmlType(name="")
            public static class HotelRef {
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

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="")
        public static class ArrivalPolicy {
            @XmlAttribute(name="GuaranteePolicyIndicator")
            protected Boolean guaranteePolicyIndicator;
            @XmlAttribute(name="DepositPolicyIndicator")
            protected Boolean depositPolicyIndicator;
            @XmlAttribute(name="HoldTimePolicyIndicator")
            protected Boolean holdTimePolicyIndicator;

            public Boolean isGuaranteePolicyIndicator() {
                return this.guaranteePolicyIndicator;
            }

            public void setGuaranteePolicyIndicator(Boolean value) {
                this.guaranteePolicyIndicator = value;
            }

            public Boolean isDepositPolicyIndicator() {
                return this.depositPolicyIndicator;
            }

            public void setDepositPolicyIndicator(Boolean value) {
                this.depositPolicyIndicator = value;
            }

            public Boolean isHoldTimePolicyIndicator() {
                return this.holdTimePolicyIndicator;
            }

            public void setHoldTimePolicyIndicator(Boolean value) {
                this.holdTimePolicyIndicator = value;
            }
        }
    }
}

