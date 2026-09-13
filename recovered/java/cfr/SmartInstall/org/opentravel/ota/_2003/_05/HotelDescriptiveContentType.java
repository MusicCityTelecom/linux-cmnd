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
import org.opentravel.ota._2003._05.AffiliationInfoType;
import org.opentravel.ota._2003._05.AreaInfoType;
import org.opentravel.ota._2003._05.ContactInfosType;
import org.opentravel.ota._2003._05.DescriptionType;
import org.opentravel.ota._2003._05.DestinationSystemCodesType;
import org.opentravel.ota._2003._05.FacilityInfoType;
import org.opentravel.ota._2003._05.GDSInfoType;
import org.opentravel.ota._2003._05.HotelInfoType;
import org.opentravel.ota._2003._05.MultimediaDescriptionsType;
import org.opentravel.ota._2003._05.OTANotifReportRQ;
import org.opentravel.ota._2003._05.PoliciesType;
import org.opentravel.ota._2003._05.TPAExtensionsType;
import org.opentravel.ota._2003._05.ViewershipsType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="HotelDescriptiveContentType", propOrder={"destinationSystemsCode", "hotelInfo", "facilityInfo", "policies", "areaInfo", "affiliationInfo", "multimediaDescriptions", "contactInfos", "gdsInfo", "viewerships", "effectivePeriods", "promotions", "roomBlocks", "tpaExtensions"})
@XmlSeeAlso(value={OTANotifReportRQ.NotifDetails.HotelNotifReport.HotelDescriptiveContents.HotelDescriptiveContent.class})
public class HotelDescriptiveContentType {
    @XmlElement(name="DestinationSystemsCode")
    protected DestinationSystemCodesType destinationSystemsCode;
    @XmlElement(name="HotelInfo")
    protected HotelInfoType hotelInfo;
    @XmlElement(name="FacilityInfo")
    protected FacilityInfoType facilityInfo;
    @XmlElement(name="Policies")
    protected Policies policies;
    @XmlElement(name="AreaInfo")
    protected AreaInfoType areaInfo;
    @XmlElement(name="AffiliationInfo")
    protected AffiliationInfoType affiliationInfo;
    @XmlElement(name="MultimediaDescriptions")
    protected MultimediaDescriptionsType multimediaDescriptions;
    @XmlElement(name="ContactInfos")
    protected ContactInfosType contactInfos;
    @XmlElement(name="GDS_Info")
    protected GDSInfoType gdsInfo;
    @XmlElement(name="Viewerships")
    protected ViewershipsType viewerships;
    @XmlElement(name="EffectivePeriods")
    protected EffectivePeriods effectivePeriods;
    @XmlElement(name="Promotions")
    protected Promotions promotions;
    @XmlElement(name="RoomBlocks")
    protected RoomBlocks roomBlocks;
    @XmlElement(name="TPA_Extensions")
    protected TPAExtensionsType tpaExtensions;
    @XmlAttribute(name="LanguageCode")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    @XmlSchemaType(name="language")
    protected String languageCode;
    @XmlAttribute(name="TimeZone")
    protected String timeZone;
    @XmlAttribute(name="DistanceUnitOfMeasureCode")
    protected String distanceUnitOfMeasureCode;
    @XmlAttribute(name="AreaUnitOfMeasureCode")
    protected String areaUnitOfMeasureCode;
    @XmlAttribute(name="WeightUnitOfMeasureCode")
    protected String weightUnitOfMeasureCode;
    @XmlAttribute(name="CurrencyCode")
    protected String currencyCode;
    @XmlAttribute(name="DecimalPlaces")
    @XmlSchemaType(name="nonNegativeInteger")
    protected BigInteger decimalPlaces;
    @XmlAttribute(name="UnitOfMeasureQuantity")
    protected BigDecimal unitOfMeasureQuantity;
    @XmlAttribute(name="UnitOfMeasure")
    protected String unitOfMeasure;
    @XmlAttribute(name="UnitOfMeasureCode")
    protected String unitOfMeasureCode;
    @XmlAttribute(name="Start")
    protected String start;
    @XmlAttribute(name="Duration")
    protected String duration;
    @XmlAttribute(name="End")
    protected String end;

    public DestinationSystemCodesType getDestinationSystemsCode() {
        return this.destinationSystemsCode;
    }

    public void setDestinationSystemsCode(DestinationSystemCodesType value) {
        this.destinationSystemsCode = value;
    }

    public HotelInfoType getHotelInfo() {
        return this.hotelInfo;
    }

    public void setHotelInfo(HotelInfoType value) {
        this.hotelInfo = value;
    }

    public FacilityInfoType getFacilityInfo() {
        return this.facilityInfo;
    }

    public void setFacilityInfo(FacilityInfoType value) {
        this.facilityInfo = value;
    }

    public Policies getPolicies() {
        return this.policies;
    }

    public void setPolicies(Policies value) {
        this.policies = value;
    }

    public AreaInfoType getAreaInfo() {
        return this.areaInfo;
    }

    public void setAreaInfo(AreaInfoType value) {
        this.areaInfo = value;
    }

    public AffiliationInfoType getAffiliationInfo() {
        return this.affiliationInfo;
    }

    public void setAffiliationInfo(AffiliationInfoType value) {
        this.affiliationInfo = value;
    }

    public MultimediaDescriptionsType getMultimediaDescriptions() {
        return this.multimediaDescriptions;
    }

    public void setMultimediaDescriptions(MultimediaDescriptionsType value) {
        this.multimediaDescriptions = value;
    }

    public ContactInfosType getContactInfos() {
        return this.contactInfos;
    }

    public void setContactInfos(ContactInfosType value) {
        this.contactInfos = value;
    }

    public GDSInfoType getGDSInfo() {
        return this.gdsInfo;
    }

    public void setGDSInfo(GDSInfoType value) {
        this.gdsInfo = value;
    }

    public ViewershipsType getViewerships() {
        return this.viewerships;
    }

    public void setViewerships(ViewershipsType value) {
        this.viewerships = value;
    }

    public EffectivePeriods getEffectivePeriods() {
        return this.effectivePeriods;
    }

    public void setEffectivePeriods(EffectivePeriods value) {
        this.effectivePeriods = value;
    }

    public Promotions getPromotions() {
        return this.promotions;
    }

    public void setPromotions(Promotions value) {
        this.promotions = value;
    }

    public RoomBlocks getRoomBlocks() {
        return this.roomBlocks;
    }

    public void setRoomBlocks(RoomBlocks value) {
        this.roomBlocks = value;
    }

    public TPAExtensionsType getTPAExtensions() {
        return this.tpaExtensions;
    }

    public void setTPAExtensions(TPAExtensionsType value) {
        this.tpaExtensions = value;
    }

    public String getLanguageCode() {
        return this.languageCode;
    }

    public void setLanguageCode(String value) {
        this.languageCode = value;
    }

    public String getTimeZone() {
        return this.timeZone;
    }

    public void setTimeZone(String value) {
        this.timeZone = value;
    }

    public String getDistanceUnitOfMeasureCode() {
        return this.distanceUnitOfMeasureCode;
    }

    public void setDistanceUnitOfMeasureCode(String value) {
        this.distanceUnitOfMeasureCode = value;
    }

    public String getAreaUnitOfMeasureCode() {
        return this.areaUnitOfMeasureCode;
    }

    public void setAreaUnitOfMeasureCode(String value) {
        this.areaUnitOfMeasureCode = value;
    }

    public String getWeightUnitOfMeasureCode() {
        return this.weightUnitOfMeasureCode;
    }

    public void setWeightUnitOfMeasureCode(String value) {
        this.weightUnitOfMeasureCode = value;
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

    public BigDecimal getUnitOfMeasureQuantity() {
        return this.unitOfMeasureQuantity;
    }

    public void setUnitOfMeasureQuantity(BigDecimal value) {
        this.unitOfMeasureQuantity = value;
    }

    public String getUnitOfMeasure() {
        return this.unitOfMeasure;
    }

    public void setUnitOfMeasure(String value) {
        this.unitOfMeasure = value;
    }

    public String getUnitOfMeasureCode() {
        return this.unitOfMeasureCode;
    }

    public void setUnitOfMeasureCode(String value) {
        this.unitOfMeasureCode = value;
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
    @XmlType(name="")
    public static class RoomBlocks {
        @XmlAttribute(name="AvailableInd")
        protected Boolean availableInd;

        public Boolean isAvailableInd() {
            return this.availableInd;
        }

        public void setAvailableInd(Boolean value) {
            this.availableInd = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"promotion"})
    public static class Promotions {
        @XmlElement(name="Promotion", required=true)
        protected List<Promotion> promotion;

        public List<Promotion> getPromotion() {
            if (this.promotion == null) {
                this.promotion = new ArrayList<Promotion>();
            }
            return this.promotion;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="", propOrder={"description"})
        public static class Promotion {
            @XmlElement(name="Description")
            protected DescriptionType description;
            @XmlAttribute(name="PkgOrPromotion")
            @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
            protected String pkgOrPromotion;
            @XmlAttribute(name="PromotionCode")
            protected String promotionCode;
            @XmlAttribute(name="Type")
            protected String type;
            @XmlAttribute(name="Title")
            protected String title;
            @XmlAttribute(name="MinLOS")
            @XmlSchemaType(name="nonNegativeInteger")
            protected BigInteger minLOS;
            @XmlAttribute(name="Remarks")
            protected String remarks;
            @XmlAttribute(name="SortOrder")
            @XmlSchemaType(name="positiveInteger")
            protected BigInteger sortOrder;
            @XmlAttribute(name="Start")
            protected String start;
            @XmlAttribute(name="Duration")
            protected String duration;
            @XmlAttribute(name="End")
            protected String end;
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

            public DescriptionType getDescription() {
                return this.description;
            }

            public void setDescription(DescriptionType value) {
                this.description = value;
            }

            public String getPkgOrPromotion() {
                return this.pkgOrPromotion;
            }

            public void setPkgOrPromotion(String value) {
                this.pkgOrPromotion = value;
            }

            public String getPromotionCode() {
                return this.promotionCode;
            }

            public void setPromotionCode(String value) {
                this.promotionCode = value;
            }

            public String getType() {
                return this.type;
            }

            public void setType(String value) {
                this.type = value;
            }

            public String getTitle() {
                return this.title;
            }

            public void setTitle(String value) {
                this.title = value;
            }

            public BigInteger getMinLOS() {
                return this.minLOS;
            }

            public void setMinLOS(BigInteger value) {
                this.minLOS = value;
            }

            public String getRemarks() {
                return this.remarks;
            }

            public void setRemarks(String value) {
                this.remarks = value;
            }

            public BigInteger getSortOrder() {
                return this.sortOrder;
            }

            public void setSortOrder(BigInteger value) {
                this.sortOrder = value;
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
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class Policies
    extends PoliciesType {
        @XmlAttribute(name="GuaranteeRoomTypeViaGDS")
        protected Boolean guaranteeRoomTypeViaGDS;
        @XmlAttribute(name="GuaranteeRoomTypeViaCRC")
        protected Boolean guaranteeRoomTypeViaCRC;
        @XmlAttribute(name="GuaranteeRoomTypeViaProperty")
        protected Boolean guaranteeRoomTypeViaProperty;

        public Boolean isGuaranteeRoomTypeViaGDS() {
            return this.guaranteeRoomTypeViaGDS;
        }

        public void setGuaranteeRoomTypeViaGDS(Boolean value) {
            this.guaranteeRoomTypeViaGDS = value;
        }

        public Boolean isGuaranteeRoomTypeViaCRC() {
            return this.guaranteeRoomTypeViaCRC;
        }

        public void setGuaranteeRoomTypeViaCRC(Boolean value) {
            this.guaranteeRoomTypeViaCRC = value;
        }

        public Boolean isGuaranteeRoomTypeViaProperty() {
            return this.guaranteeRoomTypeViaProperty;
        }

        public void setGuaranteeRoomTypeViaProperty(Boolean value) {
            this.guaranteeRoomTypeViaProperty = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"effectivePeriod"})
    public static class EffectivePeriods {
        @XmlElement(name="EffectivePeriod", required=true)
        protected List<EffectivePeriod> effectivePeriod;

        public List<EffectivePeriod> getEffectivePeriod() {
            if (this.effectivePeriod == null) {
                this.effectivePeriod = new ArrayList<EffectivePeriod>();
            }
            return this.effectivePeriod;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="")
        public static class EffectivePeriod {
            @XmlAttribute(name="StartPeriod")
            protected String startPeriod;
            @XmlAttribute(name="Duration")
            protected String duration;
            @XmlAttribute(name="EndPeriod")
            protected String endPeriod;

            public String getStartPeriod() {
                return this.startPeriod;
            }

            public void setStartPeriod(String value) {
                this.startPeriod = value;
            }

            public String getDuration() {
                return this.duration;
            }

            public void setDuration(String value) {
                this.duration = value;
            }

            public String getEndPeriod() {
                return this.endPeriod;
            }

            public void setEndPeriod(String value) {
                this.endPeriod = value;
            }
        }
    }
}

