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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.Duration;
import org.opentravel.ota._2003._05.DOWRestrictionsType;
import org.opentravel.ota._2003._05.DayOfWeekType;
import org.opentravel.ota._2003._05.HotelRatePlanType;
import org.opentravel.ota._2003._05.LengthsOfStayType;
import org.opentravel.ota._2003._05.ParagraphType;
import org.opentravel.ota._2003._05.TimeUnitType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="OfferType", propOrder={"offerRules", "discount", "freeUpgrade", "offerDescription", "compatibleOffers", "inventories", "guests"})
@XmlSeeAlso(value={HotelRatePlanType.Offers.Offer.class})
public class OfferType {
    @XmlElement(name="OfferRules")
    protected OfferRules offerRules;
    @XmlElement(name="Discount")
    protected Discount discount;
    @XmlElement(name="FreeUpgrade")
    protected FreeUpgrade freeUpgrade;
    @XmlElement(name="OfferDescription")
    protected ParagraphType offerDescription;
    @XmlElement(name="CompatibleOffers")
    protected CompatibleOffers compatibleOffers;
    @XmlElement(name="Inventories")
    protected Inventories inventories;
    @XmlElement(name="Guests")
    protected Guests guests;
    @XmlAttribute(name="OfferCode")
    protected String offerCode;
    @XmlAttribute(name="RPH")
    protected String rph;
    @XmlAttribute(name="ApplicationOrder")
    protected BigInteger applicationOrder;

    public OfferRules getOfferRules() {
        return this.offerRules;
    }

    public void setOfferRules(OfferRules value) {
        this.offerRules = value;
    }

    public Discount getDiscount() {
        return this.discount;
    }

    public void setDiscount(Discount value) {
        this.discount = value;
    }

    public FreeUpgrade getFreeUpgrade() {
        return this.freeUpgrade;
    }

    public void setFreeUpgrade(FreeUpgrade value) {
        this.freeUpgrade = value;
    }

    public ParagraphType getOfferDescription() {
        return this.offerDescription;
    }

    public void setOfferDescription(ParagraphType value) {
        this.offerDescription = value;
    }

    public CompatibleOffers getCompatibleOffers() {
        return this.compatibleOffers;
    }

    public void setCompatibleOffers(CompatibleOffers value) {
        this.compatibleOffers = value;
    }

    public Inventories getInventories() {
        return this.inventories;
    }

    public void setInventories(Inventories value) {
        this.inventories = value;
    }

    public Guests getGuests() {
        return this.guests;
    }

    public void setGuests(Guests value) {
        this.guests = value;
    }

    public String getOfferCode() {
        return this.offerCode;
    }

    public void setOfferCode(String value) {
        this.offerCode = value;
    }

    public String getRPH() {
        return this.rph;
    }

    public void setRPH(String value) {
        this.rph = value;
    }

    public BigInteger getApplicationOrder() {
        return this.applicationOrder;
    }

    public void setApplicationOrder(BigInteger value) {
        this.applicationOrder = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"offerRule"})
    public static class OfferRules {
        @XmlElement(name="OfferRule", required=true)
        protected List<OfferRule> offerRule;

        public List<OfferRule> getOfferRule() {
            if (this.offerRule == null) {
                this.offerRule = new ArrayList<OfferRule>();
            }
            return this.offerRule;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="", propOrder={"dateRestriction", "lengthsOfStay", "dowRestrictions", "occupancy", "inventories"})
        public static class OfferRule {
            @XmlElement(name="DateRestriction")
            protected List<DateRestriction> dateRestriction;
            @XmlElement(name="LengthsOfStay")
            protected LengthsOfStayType lengthsOfStay;
            @XmlElement(name="DOW_Restrictions")
            protected DOWRestrictionsType dowRestrictions;
            @XmlElement(name="Occupancy")
            protected List<Occupancy> occupancy;
            @XmlElement(name="Inventories")
            protected Inventories inventories;
            @XmlAttribute(name="StayOverDate")
            protected DayOfWeekType stayOverDate;
            @XmlAttribute(name="MinTotalOccupancy")
            @XmlSchemaType(name="nonNegativeInteger")
            protected BigInteger minTotalOccupancy;
            @XmlAttribute(name="MaxTotalOccupancy")
            @XmlSchemaType(name="nonNegativeInteger")
            protected BigInteger maxTotalOccupancy;
            @XmlAttribute(name="MaxContiguousBookings")
            @XmlSchemaType(name="nonNegativeInteger")
            protected BigInteger maxContiguousBookings;
            @XmlAttribute(name="MaxAdvancedBookingOffset")
            protected Duration maxAdvancedBookingOffset;
            @XmlAttribute(name="MinAdvancedBookingOffset")
            protected Duration minAdvancedBookingOffset;

            public List<DateRestriction> getDateRestriction() {
                if (this.dateRestriction == null) {
                    this.dateRestriction = new ArrayList<DateRestriction>();
                }
                return this.dateRestriction;
            }

            public LengthsOfStayType getLengthsOfStay() {
                return this.lengthsOfStay;
            }

            public void setLengthsOfStay(LengthsOfStayType value) {
                this.lengthsOfStay = value;
            }

            public DOWRestrictionsType getDOWRestrictions() {
                return this.dowRestrictions;
            }

            public void setDOWRestrictions(DOWRestrictionsType value) {
                this.dowRestrictions = value;
            }

            public List<Occupancy> getOccupancy() {
                if (this.occupancy == null) {
                    this.occupancy = new ArrayList<Occupancy>();
                }
                return this.occupancy;
            }

            public Inventories getInventories() {
                return this.inventories;
            }

            public void setInventories(Inventories value) {
                this.inventories = value;
            }

            public DayOfWeekType getStayOverDate() {
                return this.stayOverDate;
            }

            public void setStayOverDate(DayOfWeekType value) {
                this.stayOverDate = value;
            }

            public BigInteger getMinTotalOccupancy() {
                return this.minTotalOccupancy;
            }

            public void setMinTotalOccupancy(BigInteger value) {
                this.minTotalOccupancy = value;
            }

            public BigInteger getMaxTotalOccupancy() {
                return this.maxTotalOccupancy;
            }

            public void setMaxTotalOccupancy(BigInteger value) {
                this.maxTotalOccupancy = value;
            }

            public BigInteger getMaxContiguousBookings() {
                return this.maxContiguousBookings;
            }

            public void setMaxContiguousBookings(BigInteger value) {
                this.maxContiguousBookings = value;
            }

            public Duration getMaxAdvancedBookingOffset() {
                return this.maxAdvancedBookingOffset;
            }

            public void setMaxAdvancedBookingOffset(Duration value) {
                this.maxAdvancedBookingOffset = value;
            }

            public Duration getMinAdvancedBookingOffset() {
                return this.minAdvancedBookingOffset;
            }

            public void setMinAdvancedBookingOffset(Duration value) {
                this.minAdvancedBookingOffset = value;
            }

            @XmlAccessorType(value=XmlAccessType.FIELD)
            @XmlType(name="")
            public static class Occupancy {
                @XmlAttribute(name="MinOccupancy")
                protected Integer minOccupancy;
                @XmlAttribute(name="MaxOccupancy")
                protected Integer maxOccupancy;
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

                public Integer getMinOccupancy() {
                    return this.minOccupancy;
                }

                public void setMinOccupancy(Integer value) {
                    this.minOccupancy = value;
                }

                public Integer getMaxOccupancy() {
                    return this.maxOccupancy;
                }

                public void setMaxOccupancy(Integer value) {
                    this.maxOccupancy = value;
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

            @XmlAccessorType(value=XmlAccessType.FIELD)
            @XmlType(name="", propOrder={"inventory"})
            public static class Inventories {
                @XmlElement(name="Inventory", required=true)
                protected List<Inventory> inventory;

                public List<Inventory> getInventory() {
                    if (this.inventory == null) {
                        this.inventory = new ArrayList<Inventory>();
                    }
                    return this.inventory;
                }

                @XmlAccessorType(value=XmlAccessType.FIELD)
                @XmlType(name="")
                public static class Inventory {
                    @XmlAttribute(name="AppliesToIndicator", required=true)
                    protected boolean appliesToIndicator;
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

                    public boolean isAppliesToIndicator() {
                        return this.appliesToIndicator;
                    }

                    public void setAppliesToIndicator(boolean value) {
                        this.appliesToIndicator = value;
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
                }
            }

            @XmlAccessorType(value=XmlAccessType.FIELD)
            @XmlType(name="")
            public static class DateRestriction {
                @XmlAttribute(name="RestrictionType")
                @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
                protected String restrictionType;
                @XmlAttribute(name="Start")
                protected String start;
                @XmlAttribute(name="Duration")
                protected String duration;
                @XmlAttribute(name="End")
                protected String end;

                public String getRestrictionType() {
                    return this.restrictionType;
                }

                public void setRestrictionType(String value) {
                    this.restrictionType = value;
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
    @XmlType(name="", propOrder={"inventory"})
    public static class Inventories {
        @XmlElement(name="Inventory", required=true)
        protected List<Inventory> inventory;

        public List<Inventory> getInventory() {
            if (this.inventory == null) {
                this.inventory = new ArrayList<Inventory>();
            }
            return this.inventory;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="")
        public static class Inventory {
            @XmlAttribute(name="AppliesToIndicator", required=true)
            protected boolean appliesToIndicator;
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

            public boolean isAppliesToIndicator() {
                return this.appliesToIndicator;
            }

            public void setAppliesToIndicator(boolean value) {
                this.appliesToIndicator = value;
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
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"guest"})
    public static class Guests {
        @XmlElement(name="Guest", required=true)
        protected List<Guest> guest;

        public List<Guest> getGuest() {
            if (this.guest == null) {
                this.guest = new ArrayList<Guest>();
            }
            return this.guest;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="")
        public static class Guest {
            @XmlAttribute(name="MinCount")
            @XmlSchemaType(name="nonNegativeInteger")
            protected BigInteger minCount;
            @XmlAttribute(name="MaxCount")
            @XmlSchemaType(name="nonNegativeInteger")
            protected BigInteger maxCount;
            @XmlAttribute(name="FirstQualifyingPosition")
            @XmlSchemaType(name="nonNegativeInteger")
            protected BigInteger firstQualifyingPosition;
            @XmlAttribute(name="LastQualifyingPosition")
            @XmlSchemaType(name="nonNegativeInteger")
            protected BigInteger lastQualifyingPosition;
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

            public BigInteger getMinCount() {
                return this.minCount;
            }

            public void setMinCount(BigInteger value) {
                this.minCount = value;
            }

            public BigInteger getMaxCount() {
                return this.maxCount;
            }

            public void setMaxCount(BigInteger value) {
                this.maxCount = value;
            }

            public BigInteger getFirstQualifyingPosition() {
                return this.firstQualifyingPosition;
            }

            public void setFirstQualifyingPosition(BigInteger value) {
                this.firstQualifyingPosition = value;
            }

            public BigInteger getLastQualifyingPosition() {
                return this.lastQualifyingPosition;
            }

            public void setLastQualifyingPosition(BigInteger value) {
                this.lastQualifyingPosition = value;
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
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"upgradeFrom", "upgradeTo"})
    public static class FreeUpgrade {
        @XmlElement(name="UpgradeFrom")
        protected UpgradeFrom upgradeFrom;
        @XmlElement(name="UpgradeTo")
        protected UpgradeTo upgradeTo;
        @XmlAttribute(name="UpgradeBookingCode")
        protected String upgradeBookingCode;

        public UpgradeFrom getUpgradeFrom() {
            return this.upgradeFrom;
        }

        public void setUpgradeFrom(UpgradeFrom value) {
            this.upgradeFrom = value;
        }

        public UpgradeTo getUpgradeTo() {
            return this.upgradeTo;
        }

        public void setUpgradeTo(UpgradeTo value) {
            this.upgradeTo = value;
        }

        public String getUpgradeBookingCode() {
            return this.upgradeBookingCode;
        }

        public void setUpgradeBookingCode(String value) {
            this.upgradeBookingCode = value;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="")
        public static class UpgradeTo {
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
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="")
        public static class UpgradeFrom {
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
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class Discount {
        @XmlAttribute(name="NightsRequired")
        @XmlSchemaType(name="nonNegativeInteger")
        protected BigInteger nightsRequired;
        @XmlAttribute(name="NightsDiscounted")
        @XmlSchemaType(name="nonNegativeInteger")
        protected BigInteger nightsDiscounted;
        @XmlAttribute(name="DiscountPattern")
        protected String discountPattern;
        @XmlAttribute(name="Percent")
        protected BigDecimal percent;
        @XmlAttribute(name="ChargeUnitCode")
        protected String chargeUnitCode;
        @XmlAttribute(name="Amount")
        protected BigDecimal amount;
        @XmlAttribute(name="CurrencyCode")
        protected String currencyCode;
        @XmlAttribute(name="DecimalPlaces")
        @XmlSchemaType(name="nonNegativeInteger")
        protected BigInteger decimalPlaces;

        public BigInteger getNightsRequired() {
            return this.nightsRequired;
        }

        public void setNightsRequired(BigInteger value) {
            this.nightsRequired = value;
        }

        public BigInteger getNightsDiscounted() {
            return this.nightsDiscounted;
        }

        public void setNightsDiscounted(BigInteger value) {
            this.nightsDiscounted = value;
        }

        public String getDiscountPattern() {
            return this.discountPattern;
        }

        public void setDiscountPattern(String value) {
            this.discountPattern = value;
        }

        public BigDecimal getPercent() {
            return this.percent;
        }

        public void setPercent(BigDecimal value) {
            this.percent = value;
        }

        public String getChargeUnitCode() {
            return this.chargeUnitCode;
        }

        public void setChargeUnitCode(String value) {
            this.chargeUnitCode = value;
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

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"compatibleOffer"})
    public static class CompatibleOffers {
        @XmlElement(name="CompatibleOffer", required=true)
        protected List<CompatibleOffer> compatibleOffer;

        public List<CompatibleOffer> getCompatibleOffer() {
            if (this.compatibleOffer == null) {
                this.compatibleOffer = new ArrayList<CompatibleOffer>();
            }
            return this.compatibleOffer;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="")
        public static class CompatibleOffer {
            @XmlAttribute(name="OfferRPH")
            protected List<String> offerRPH;
            @XmlAttribute(name="IncompatibleOfferIndicator")
            protected Boolean incompatibleOfferIndicator;

            public List<String> getOfferRPH() {
                if (this.offerRPH == null) {
                    this.offerRPH = new ArrayList<String>();
                }
                return this.offerRPH;
            }

            public Boolean isIncompatibleOfferIndicator() {
                return this.incompatibleOfferIndicator;
            }

            public void setIncompatibleOfferIndicator(Boolean value) {
                this.incompatibleOfferIndicator = value;
            }
        }
    }
}

