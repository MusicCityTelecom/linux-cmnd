/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.DateTimeSpanType;
import org.opentravel.ota._2003._05.DestinationLevelType;
import org.opentravel.ota._2003._05.MealPlanType;
import org.opentravel.ota._2003._05.PkgFlightSegmentType;
import org.opentravel.ota._2003._05.PricingType;
import org.opentravel.ota._2003._05.PropertyIdentityType;
import org.opentravel.ota._2003._05.RoomProfileType;
import org.opentravel.ota._2003._05.VehicleRentalCoreType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ItineraryItemResponseType", propOrder={"accommodation", "flight", "rentalCar"})
public class ItineraryItemResponseType {
    @XmlElement(name="Accommodation")
    protected Accommodation accommodation;
    @XmlElement(name="Flight")
    protected PkgFlightSegmentType flight;
    @XmlElement(name="RentalCar")
    protected RentalCar rentalCar;
    @XmlAttribute(name="RPH")
    protected String rph;
    @XmlAttribute(name="ItinerarySequence")
    @XmlSchemaType(name="positiveInteger")
    protected BigInteger itinerarySequence;
    @XmlAttribute(name="ChronologicalSequence")
    @XmlSchemaType(name="positiveInteger")
    protected BigInteger chronologicalSequence;

    public Accommodation getAccommodation() {
        return this.accommodation;
    }

    public void setAccommodation(Accommodation value) {
        this.accommodation = value;
    }

    public PkgFlightSegmentType getFlight() {
        return this.flight;
    }

    public void setFlight(PkgFlightSegmentType value) {
        this.flight = value;
    }

    public RentalCar getRentalCar() {
        return this.rentalCar;
    }

    public void setRentalCar(RentalCar value) {
        this.rentalCar = value;
    }

    public String getRPH() {
        return this.rph;
    }

    public void setRPH(String value) {
        this.rph = value;
    }

    public BigInteger getItinerarySequence() {
        return this.itinerarySequence;
    }

    public void setItinerarySequence(BigInteger value) {
        this.itinerarySequence = value;
    }

    public BigInteger getChronologicalSequence() {
        return this.chronologicalSequence;
    }

    public void setChronologicalSequence(BigInteger value) {
        this.chronologicalSequence = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class RentalCar
    extends VehicleRentalCoreType {
        @XmlAttribute(name="RPH")
        protected String rph;
        @XmlAttribute(name="Name")
        protected String name;
        @XmlAttribute(name="Code")
        protected String code;

        public String getRPH() {
            return this.rph;
        }

        public void setRPH(String value) {
            this.rph = value;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String value) {
            this.name = value;
        }

        public String getCode() {
            return this.code;
        }

        public void setCode(String value) {
            this.code = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"identity", "accommodationClass", "dateRange", "roomProfiles", "mealPlans"})
    public static class Accommodation {
        @XmlElement(name="Identity")
        protected PropertyIdentityType identity;
        @XmlElement(name="AccommodationClass")
        protected AccommodationClass accommodationClass;
        @XmlElement(name="DateRange", required=true)
        protected DateTimeSpanType dateRange;
        @XmlElement(name="RoomProfiles")
        protected RoomProfiles roomProfiles;
        @XmlElement(name="MealPlans")
        protected MealPlans mealPlans;
        @XmlAttribute(name="RPH")
        protected String rph;
        @XmlAttribute(name="ResortName")
        protected String resortName;
        @XmlAttribute(name="ResortCode")
        protected String resortCode;
        @XmlAttribute(name="DestinationCode")
        protected String destinationCode;
        @XmlAttribute(name="DestinationLevel")
        protected DestinationLevelType destinationLevel;
        @XmlAttribute(name="DestinationName")
        protected String destinationName;

        public PropertyIdentityType getIdentity() {
            return this.identity;
        }

        public void setIdentity(PropertyIdentityType value) {
            this.identity = value;
        }

        public AccommodationClass getAccommodationClass() {
            return this.accommodationClass;
        }

        public void setAccommodationClass(AccommodationClass value) {
            this.accommodationClass = value;
        }

        public DateTimeSpanType getDateRange() {
            return this.dateRange;
        }

        public void setDateRange(DateTimeSpanType value) {
            this.dateRange = value;
        }

        public RoomProfiles getRoomProfiles() {
            return this.roomProfiles;
        }

        public void setRoomProfiles(RoomProfiles value) {
            this.roomProfiles = value;
        }

        public MealPlans getMealPlans() {
            return this.mealPlans;
        }

        public void setMealPlans(MealPlans value) {
            this.mealPlans = value;
        }

        public String getRPH() {
            return this.rph;
        }

        public void setRPH(String value) {
            this.rph = value;
        }

        public String getResortName() {
            return this.resortName;
        }

        public void setResortName(String value) {
            this.resortName = value;
        }

        public String getResortCode() {
            return this.resortCode;
        }

        public void setResortCode(String value) {
            this.resortCode = value;
        }

        public String getDestinationCode() {
            return this.destinationCode;
        }

        public void setDestinationCode(String value) {
            this.destinationCode = value;
        }

        public DestinationLevelType getDestinationLevel() {
            return this.destinationLevel;
        }

        public void setDestinationLevel(DestinationLevelType value) {
            this.destinationLevel = value;
        }

        public String getDestinationName() {
            return this.destinationName;
        }

        public void setDestinationName(String value) {
            this.destinationName = value;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="", propOrder={"roomProfile"})
        public static class RoomProfiles {
            @XmlElement(name="RoomProfile", required=true)
            protected List<RoomProfile> roomProfile;

            public List<RoomProfile> getRoomProfile() {
                if (this.roomProfile == null) {
                    this.roomProfile = new ArrayList<RoomProfile>();
                }
                return this.roomProfile;
            }

            @XmlAccessorType(value=XmlAccessType.FIELD)
            @XmlType(name="", propOrder={"prices"})
            public static class RoomProfile
            extends RoomProfileType {
                @XmlElement(name="Prices")
                protected List<Prices> prices;

                public List<Prices> getPrices() {
                    if (this.prices == null) {
                        this.prices = new ArrayList<Prices>();
                    }
                    return this.prices;
                }

                @XmlAccessorType(value=XmlAccessType.FIELD)
                @XmlType(name="", propOrder={"price"})
                public static class Prices {
                    @XmlElement(name="Price", required=true)
                    protected List<Price> price;
                    @XmlAttribute(name="SupplementIndicator")
                    protected Boolean supplementIndicator;
                    @XmlAttribute(name="MealPlanRPH")
                    protected String mealPlanRPH;

                    public List<Price> getPrice() {
                        if (this.price == null) {
                            this.price = new ArrayList<Price>();
                        }
                        return this.price;
                    }

                    public Boolean isSupplementIndicator() {
                        return this.supplementIndicator;
                    }

                    public void setSupplementIndicator(Boolean value) {
                        this.supplementIndicator = value;
                    }

                    public String getMealPlanRPH() {
                        return this.mealPlanRPH;
                    }

                    public void setMealPlanRPH(String value) {
                        this.mealPlanRPH = value;
                    }

                    @XmlAccessorType(value=XmlAccessType.FIELD)
                    @XmlType(name="")
                    public static class Price {
                        @XmlAttribute(name="PriceQualifier")
                        protected Integer priceQualifier;
                        @XmlAttribute(name="PriceBasis")
                        protected PricingType priceBasis;
                        @XmlAttribute(name="AgeQualifyingCode")
                        protected String ageQualifyingCode;
                        @XmlAttribute(name="Age")
                        protected Integer age;
                        @XmlAttribute(name="Count")
                        protected Integer count;
                        @XmlAttribute(name="AgeBucket")
                        protected String ageBucket;
                        @XmlAttribute(name="Amount")
                        protected BigDecimal amount;
                        @XmlAttribute(name="CurrencyCode")
                        protected String currencyCode;
                        @XmlAttribute(name="DecimalPlaces")
                        @XmlSchemaType(name="nonNegativeInteger")
                        protected BigInteger decimalPlaces;

                        public Integer getPriceQualifier() {
                            return this.priceQualifier;
                        }

                        public void setPriceQualifier(Integer value) {
                            this.priceQualifier = value;
                        }

                        public PricingType getPriceBasis() {
                            return this.priceBasis;
                        }

                        public void setPriceBasis(PricingType value) {
                            this.priceBasis = value;
                        }

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

                        public BigDecimal getAmount() {
                            return this.amount;
                        }

                        public void setAmount(BigDecimal value) {
                            this.amount = value;
                        }

                        public String getCurrencyCode() {
                            return this.currencyCode;
                        }

                        public void setCurrencyCode(String value) {
                            this.currencyCode = value;
                        }

                        public BigInteger getDecimalPlaces() {
                            return this.decimalPlaces;
                        }

                        public void setDecimalPlaces(BigInteger value) {
                            this.decimalPlaces = value;
                        }
                    }
                }
            }
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="", propOrder={"mealPlan"})
        public static class MealPlans {
            @XmlElement(name="MealPlan", required=true)
            protected List<MealPlanType> mealPlan;

            public List<MealPlanType> getMealPlan() {
                if (this.mealPlan == null) {
                    this.mealPlan = new ArrayList<MealPlanType>();
                }
                return this.mealPlan;
            }
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="")
        public static class AccommodationClass {
            @XmlAttribute(name="Code")
            protected String code;
            @XmlAttribute(name="Name")
            protected String name;
            @XmlAttribute(name="NationalCode")
            protected String nationalCode;
            @XmlAttribute(name="OfficialName")
            protected String officialName;

            public String getCode() {
                return this.code;
            }

            public void setCode(String value) {
                this.code = value;
            }

            public String getName() {
                return this.name;
            }

            public void setName(String value) {
                this.name = value;
            }

            public String getNationalCode() {
                return this.nationalCode;
            }

            public void setNationalCode(String value) {
                this.nationalCode = value;
            }

            public String getOfficialName() {
                return this.officialName;
            }

            public void setOfficialName(String value) {
                this.officialName = value;
            }
        }
    }
}

