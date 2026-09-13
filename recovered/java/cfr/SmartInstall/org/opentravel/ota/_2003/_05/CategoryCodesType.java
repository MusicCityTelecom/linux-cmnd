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
import org.opentravel.ota._2003._05.MultimediaDescriptionsType;
import org.opentravel.ota._2003._05.TimeUnitType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="CategoryCodesType", propOrder={"locationCategory", "segmentCategory", "hotelCategory", "architecturalStyle", "guestRoomInfo"})
public class CategoryCodesType {
    @XmlElement(name="LocationCategory")
    protected List<LocationCategory> locationCategory;
    @XmlElement(name="SegmentCategory")
    protected List<SegmentCategory> segmentCategory;
    @XmlElement(name="HotelCategory")
    protected List<HotelCategory> hotelCategory;
    @XmlElement(name="ArchitecturalStyle")
    protected List<ArchitecturalStyle> architecturalStyle;
    @XmlElement(name="GuestRoomInfo")
    protected List<GuestRoomInfo> guestRoomInfo;

    public List<LocationCategory> getLocationCategory() {
        if (this.locationCategory == null) {
            this.locationCategory = new ArrayList<LocationCategory>();
        }
        return this.locationCategory;
    }

    public List<SegmentCategory> getSegmentCategory() {
        if (this.segmentCategory == null) {
            this.segmentCategory = new ArrayList<SegmentCategory>();
        }
        return this.segmentCategory;
    }

    public List<HotelCategory> getHotelCategory() {
        if (this.hotelCategory == null) {
            this.hotelCategory = new ArrayList<HotelCategory>();
        }
        return this.hotelCategory;
    }

    public List<ArchitecturalStyle> getArchitecturalStyle() {
        if (this.architecturalStyle == null) {
            this.architecturalStyle = new ArrayList<ArchitecturalStyle>();
        }
        return this.architecturalStyle;
    }

    public List<GuestRoomInfo> getGuestRoomInfo() {
        if (this.guestRoomInfo == null) {
            this.guestRoomInfo = new ArrayList<GuestRoomInfo>();
        }
        return this.guestRoomInfo;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class SegmentCategory {
        @XmlAttribute(name="Code")
        protected String code;
        @XmlAttribute(name="ExistsCode")
        protected String existsCode;
        @XmlAttribute(name="CodeDetail")
        protected String codeDetail;
        @XmlAttribute(name="Removal")
        protected Boolean removal;

        public String getCode() {
            return this.code;
        }

        public void setCode(String value) {
            this.code = value;
        }

        public String getExistsCode() {
            return this.existsCode;
        }

        public void setExistsCode(String value) {
            this.existsCode = value;
        }

        public String getCodeDetail() {
            return this.codeDetail;
        }

        public void setCodeDetail(String value) {
            this.codeDetail = value;
        }

        public Boolean isRemoval() {
            return this.removal;
        }

        public void setRemoval(Boolean value) {
            this.removal = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class LocationCategory {
        @XmlAttribute(name="Code")
        protected String code;
        @XmlAttribute(name="ExistsCode")
        protected String existsCode;
        @XmlAttribute(name="CodeDetail")
        protected String codeDetail;
        @XmlAttribute(name="Removal")
        protected Boolean removal;

        public String getCode() {
            return this.code;
        }

        public void setCode(String value) {
            this.code = value;
        }

        public String getExistsCode() {
            return this.existsCode;
        }

        public void setExistsCode(String value) {
            this.existsCode = value;
        }

        public String getCodeDetail() {
            return this.codeDetail;
        }

        public void setCodeDetail(String value) {
            this.codeDetail = value;
        }

        public Boolean isRemoval() {
            return this.removal;
        }

        public void setRemoval(Boolean value) {
            this.removal = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class HotelCategory {
        @XmlAttribute(name="Code")
        protected String code;
        @XmlAttribute(name="ExistsCode")
        protected String existsCode;
        @XmlAttribute(name="CodeDetail")
        protected String codeDetail;
        @XmlAttribute(name="Removal")
        protected Boolean removal;

        public String getCode() {
            return this.code;
        }

        public void setCode(String value) {
            this.code = value;
        }

        public String getExistsCode() {
            return this.existsCode;
        }

        public void setExistsCode(String value) {
            this.existsCode = value;
        }

        public String getCodeDetail() {
            return this.codeDetail;
        }

        public void setCodeDetail(String value) {
            this.codeDetail = value;
        }

        public Boolean isRemoval() {
            return this.removal;
        }

        public void setRemoval(Boolean value) {
            this.removal = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"rateRanges", "multimediaDescriptions", "descriptiveText"})
    public static class GuestRoomInfo {
        @XmlElement(name="RateRanges")
        protected RateRanges rateRanges;
        @XmlElement(name="MultimediaDescriptions")
        protected MultimediaDescriptionsType multimediaDescriptions;
        @XmlElement(name="DescriptiveText")
        protected String descriptiveText;
        @XmlAttribute(name="Code")
        protected String code;
        @XmlAttribute(name="Quantity")
        @XmlSchemaType(name="nonNegativeInteger")
        protected BigInteger quantity;
        @XmlAttribute(name="ExistsCode")
        protected String existsCode;
        @XmlAttribute(name="CodeDetail")
        protected String codeDetail;
        @XmlAttribute(name="Removal")
        protected Boolean removal;

        public RateRanges getRateRanges() {
            return this.rateRanges;
        }

        public void setRateRanges(RateRanges value) {
            this.rateRanges = value;
        }

        public MultimediaDescriptionsType getMultimediaDescriptions() {
            return this.multimediaDescriptions;
        }

        public void setMultimediaDescriptions(MultimediaDescriptionsType value) {
            this.multimediaDescriptions = value;
        }

        public String getDescriptiveText() {
            return this.descriptiveText;
        }

        public void setDescriptiveText(String value) {
            this.descriptiveText = value;
        }

        public String getCode() {
            return this.code;
        }

        public void setCode(String value) {
            this.code = value;
        }

        public BigInteger getQuantity() {
            return this.quantity;
        }

        public void setQuantity(BigInteger value) {
            this.quantity = value;
        }

        public String getExistsCode() {
            return this.existsCode;
        }

        public void setExistsCode(String value) {
            this.existsCode = value;
        }

        public String getCodeDetail() {
            return this.codeDetail;
        }

        public void setCodeDetail(String value) {
            this.codeDetail = value;
        }

        public Boolean isRemoval() {
            return this.removal;
        }

        public void setRemoval(Boolean value) {
            this.removal = value;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="", propOrder={"rateRange"})
        public static class RateRanges {
            @XmlElement(name="RateRange")
            protected List<RateRange> rateRange;

            public List<RateRange> getRateRange() {
                if (this.rateRange == null) {
                    this.rateRange = new ArrayList<RateRange>();
                }
                return this.rateRange;
            }

            @XmlAccessorType(value=XmlAccessType.FIELD)
            @XmlType(name="")
            public static class RateRange {
                @XmlAttribute(name="Start")
                protected String start;
                @XmlAttribute(name="Duration")
                protected String duration;
                @XmlAttribute(name="End")
                protected String end;
                @XmlAttribute(name="MinRate")
                protected BigDecimal minRate;
                @XmlAttribute(name="MaxRate")
                protected BigDecimal maxRate;
                @XmlAttribute(name="FixedRate")
                protected BigDecimal fixedRate;
                @XmlAttribute(name="RateTimeUnit")
                protected TimeUnitType rateTimeUnit;
                @XmlAttribute(name="CurrencyCode")
                protected String currencyCode;
                @XmlAttribute(name="DecimalPlaces")
                @XmlSchemaType(name="nonNegativeInteger")
                protected BigInteger decimalPlaces;

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

                public BigDecimal getMinRate() {
                    return this.minRate;
                }

                public void setMinRate(BigDecimal value) {
                    this.minRate = value;
                }

                public BigDecimal getMaxRate() {
                    return this.maxRate;
                }

                public void setMaxRate(BigDecimal value) {
                    this.maxRate = value;
                }

                public BigDecimal getFixedRate() {
                    return this.fixedRate;
                }

                public void setFixedRate(BigDecimal value) {
                    this.fixedRate = value;
                }

                public TimeUnitType getRateTimeUnit() {
                    return this.rateTimeUnit;
                }

                public void setRateTimeUnit(TimeUnitType value) {
                    this.rateTimeUnit = value;
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

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class ArchitecturalStyle {
        @XmlAttribute(name="Code")
        protected String code;
        @XmlAttribute(name="ExistsCode")
        protected String existsCode;
        @XmlAttribute(name="CodeDetail")
        protected String codeDetail;
        @XmlAttribute(name="Removal")
        protected Boolean removal;

        public String getCode() {
            return this.code;
        }

        public void setCode(String value) {
            this.code = value;
        }

        public String getExistsCode() {
            return this.existsCode;
        }

        public void setExistsCode(String value) {
            this.existsCode = value;
        }

        public String getCodeDetail() {
            return this.codeDetail;
        }

        public void setCodeDetail(String value) {
            this.codeDetail = value;
        }

        public Boolean isRemoval() {
            return this.removal;
        }

        public void setRemoval(Boolean value) {
            this.removal = value;
        }
    }
}

