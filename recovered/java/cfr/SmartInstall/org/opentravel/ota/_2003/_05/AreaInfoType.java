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
import javax.xml.datatype.XMLGregorianCalendar;
import org.opentravel.ota._2003._05.ContactInfoType;
import org.opentravel.ota._2003._05.ContactInfosType;
import org.opentravel.ota._2003._05.MultimediaDescriptionsType;
import org.opentravel.ota._2003._05.OperationSchedulesPlusChargeType;
import org.opentravel.ota._2003._05.ParagraphType;
import org.opentravel.ota._2003._05.RefPointsType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="AreaInfoType", propOrder={"refPoints", "attractions", "recreations", "otherHotels"})
public class AreaInfoType {
    @XmlElement(name="RefPoints")
    protected RefPoints refPoints;
    @XmlElement(name="Attractions")
    protected Attractions attractions;
    @XmlElement(name="Recreations")
    protected Recreations recreations;
    @XmlElement(name="OtherHotels")
    protected OtherHotels otherHotels;

    public RefPoints getRefPoints() {
        return this.refPoints;
    }

    public void setRefPoints(RefPoints value) {
        this.refPoints = value;
    }

    public Attractions getAttractions() {
        return this.attractions;
    }

    public void setAttractions(Attractions value) {
        this.attractions = value;
    }

    public Recreations getRecreations() {
        return this.recreations;
    }

    public void setRecreations(Recreations value) {
        this.recreations = value;
    }

    public OtherHotels getOtherHotels() {
        return this.otherHotels;
    }

    public void setOtherHotels(OtherHotels value) {
        this.otherHotels = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class RefPoints
    extends RefPointsType {
        @XmlAttribute(name="LastUpdated")
        @XmlSchemaType(name="dateTime")
        protected XMLGregorianCalendar lastUpdated;

        public XMLGregorianCalendar getLastUpdated() {
            return this.lastUpdated;
        }

        public void setLastUpdated(XMLGregorianCalendar value) {
            this.lastUpdated = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"recreation"})
    public static class Recreations {
        @XmlElement(name="Recreation", required=true)
        protected List<Recreation> recreation;
        @XmlAttribute(name="LastUpdated")
        @XmlSchemaType(name="dateTime")
        protected XMLGregorianCalendar lastUpdated;

        public List<Recreation> getRecreation() {
            if (this.recreation == null) {
                this.recreation = new ArrayList<Recreation>();
            }
            return this.recreation;
        }

        public XMLGregorianCalendar getLastUpdated() {
            return this.lastUpdated;
        }

        public void setLastUpdated(XMLGregorianCalendar value) {
            this.lastUpdated = value;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="", propOrder={"contact", "operationSchedules", "refPoints", "multimediaDescriptions", "recreationDetails", "descriptiveText"})
        public static class Recreation {
            @XmlElement(name="Contact")
            protected ContactInfoType contact;
            @XmlElement(name="OperationSchedules")
            protected OperationSchedulesPlusChargeType operationSchedules;
            @XmlElement(name="RefPoints")
            protected RefPointsType refPoints;
            @XmlElement(name="MultimediaDescriptions")
            protected MultimediaDescriptions multimediaDescriptions;
            @XmlElement(name="RecreationDetails")
            protected RecreationDetails recreationDetails;
            @XmlElement(name="DescriptiveText")
            protected String descriptiveText;
            @XmlAttribute(name="Code")
            protected String code;
            @XmlAttribute(name="Name")
            protected String name;
            @XmlAttribute(name="ProximityCode")
            protected String proximityCode;
            @XmlAttribute(name="Included")
            protected Boolean included;
            @XmlAttribute(name="ExistsCode")
            protected String existsCode;
            @XmlAttribute(name="Sort")
            @XmlSchemaType(name="nonNegativeInteger")
            protected BigInteger sort;
            @XmlAttribute(name="YearReplaced")
            @XmlSchemaType(name="date")
            protected XMLGregorianCalendar yearReplaced;
            @XmlAttribute(name="CodeDetail")
            protected String codeDetail;
            @XmlAttribute(name="Removal")
            protected Boolean removal;
            @XmlAttribute(name="ID")
            protected String id;

            public ContactInfoType getContact() {
                return this.contact;
            }

            public void setContact(ContactInfoType value) {
                this.contact = value;
            }

            public OperationSchedulesPlusChargeType getOperationSchedules() {
                return this.operationSchedules;
            }

            public void setOperationSchedules(OperationSchedulesPlusChargeType value) {
                this.operationSchedules = value;
            }

            public RefPointsType getRefPoints() {
                return this.refPoints;
            }

            public void setRefPoints(RefPointsType value) {
                this.refPoints = value;
            }

            public MultimediaDescriptions getMultimediaDescriptions() {
                return this.multimediaDescriptions;
            }

            public void setMultimediaDescriptions(MultimediaDescriptions value) {
                this.multimediaDescriptions = value;
            }

            public RecreationDetails getRecreationDetails() {
                return this.recreationDetails;
            }

            public void setRecreationDetails(RecreationDetails value) {
                this.recreationDetails = value;
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

            public String getName() {
                return this.name;
            }

            public void setName(String value) {
                this.name = value;
            }

            public String getProximityCode() {
                return this.proximityCode;
            }

            public void setProximityCode(String value) {
                this.proximityCode = value;
            }

            public Boolean isIncluded() {
                return this.included;
            }

            public void setIncluded(Boolean value) {
                this.included = value;
            }

            public String getExistsCode() {
                return this.existsCode;
            }

            public void setExistsCode(String value) {
                this.existsCode = value;
            }

            public BigInteger getSort() {
                return this.sort;
            }

            public void setSort(BigInteger value) {
                this.sort = value;
            }

            public XMLGregorianCalendar getYearReplaced() {
                return this.yearReplaced;
            }

            public void setYearReplaced(XMLGregorianCalendar value) {
                this.yearReplaced = value;
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

            public String getID() {
                return this.id;
            }

            public void setID(String value) {
                this.id = value;
            }

            @XmlAccessorType(value=XmlAccessType.FIELD)
            @XmlType(name="", propOrder={"recreationDetail"})
            public static class RecreationDetails {
                @XmlElement(name="RecreationDetail", required=true)
                protected List<RecreationDetail> recreationDetail;

                public List<RecreationDetail> getRecreationDetail() {
                    if (this.recreationDetail == null) {
                        this.recreationDetail = new ArrayList<RecreationDetail>();
                    }
                    return this.recreationDetail;
                }

                @XmlAccessorType(value=XmlAccessType.FIELD)
                @XmlType(name="", propOrder={"description"})
                public static class RecreationDetail {
                    @XmlElement(name="Description")
                    protected List<ParagraphType> description;
                    @XmlAttribute(name="Code")
                    protected String code;
                    @XmlAttribute(name="ExistsCode")
                    protected String existsCode;
                    @XmlAttribute(name="CodeDetail")
                    protected String codeDetail;
                    @XmlAttribute(name="Removal")
                    protected Boolean removal;

                    public List<ParagraphType> getDescription() {
                        if (this.description == null) {
                            this.description = new ArrayList<ParagraphType>();
                        }
                        return this.description;
                    }

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

            @XmlAccessorType(value=XmlAccessType.FIELD)
            @XmlType(name="")
            public static class MultimediaDescriptions
            extends MultimediaDescriptionsType {
                @XmlAttribute(name="Location")
                protected Boolean location;
                @XmlAttribute(name="RefDirectionTo")
                protected Boolean refDirectionTo;

                public Boolean isLocation() {
                    return this.location;
                }

                public void setLocation(Boolean value) {
                    this.location = value;
                }

                public Boolean isRefDirectionTo() {
                    return this.refDirectionTo;
                }

                public void setRefDirectionTo(Boolean value) {
                    this.refDirectionTo = value;
                }
            }
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"otherHotel"})
    public static class OtherHotels {
        @XmlElement(name="OtherHotel", required=true)
        protected List<OtherHotel> otherHotel;
        @XmlAttribute(name="LastUpdated")
        @XmlSchemaType(name="dateTime")
        protected XMLGregorianCalendar lastUpdated;

        public List<OtherHotel> getOtherHotel() {
            if (this.otherHotel == null) {
                this.otherHotel = new ArrayList<OtherHotel>();
            }
            return this.otherHotel;
        }

        public XMLGregorianCalendar getLastUpdated() {
            return this.lastUpdated;
        }

        public void setLastUpdated(XMLGregorianCalendar value) {
            this.lastUpdated = value;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="", propOrder={"contactInfos", "refPoints"})
        public static class OtherHotel {
            @XmlElement(name="ContactInfos")
            protected ContactInfosType contactInfos;
            @XmlElement(name="RefPoints")
            protected RefPointsType refPoints;
            @XmlAttribute(name="CompetitorIndicator")
            protected Boolean competitorIndicator;
            @XmlAttribute(name="SegmentCategoryCode")
            protected String segmentCategoryCode;
            @XmlAttribute(name="ID")
            protected String id;
            @XmlAttribute(name="AlternateID")
            protected String alternateID;
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

            public ContactInfosType getContactInfos() {
                return this.contactInfos;
            }

            public void setContactInfos(ContactInfosType value) {
                this.contactInfos = value;
            }

            public RefPointsType getRefPoints() {
                return this.refPoints;
            }

            public void setRefPoints(RefPointsType value) {
                this.refPoints = value;
            }

            public Boolean isCompetitorIndicator() {
                return this.competitorIndicator;
            }

            public void setCompetitorIndicator(Boolean value) {
                this.competitorIndicator = value;
            }

            public String getSegmentCategoryCode() {
                return this.segmentCategoryCode;
            }

            public void setSegmentCategoryCode(String value) {
                this.segmentCategoryCode = value;
            }

            public String getID() {
                return this.id;
            }

            public void setID(String value) {
                this.id = value;
            }

            public String getAlternateID() {
                return this.alternateID;
            }

            public void setAlternateID(String value) {
                this.alternateID = value;
            }

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
    @XmlType(name="", propOrder={"attraction"})
    public static class Attractions {
        @XmlElement(name="Attraction", required=true)
        protected List<Attraction> attraction;
        @XmlAttribute(name="LastUpdated")
        @XmlSchemaType(name="dateTime")
        protected XMLGregorianCalendar lastUpdated;

        public List<Attraction> getAttraction() {
            if (this.attraction == null) {
                this.attraction = new ArrayList<Attraction>();
            }
            return this.attraction;
        }

        public XMLGregorianCalendar getLastUpdated() {
            return this.lastUpdated;
        }

        public void setLastUpdated(XMLGregorianCalendar value) {
            this.lastUpdated = value;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="", propOrder={"contact", "operationSchedules", "multimediaDescriptions", "refPoints", "descriptiveText"})
        public static class Attraction {
            @XmlElement(name="Contact")
            protected Contact contact;
            @XmlElement(name="OperationSchedules")
            protected OperationSchedulesPlusChargeType operationSchedules;
            @XmlElement(name="MultimediaDescriptions")
            protected MultimediaDescriptionsType multimediaDescriptions;
            @XmlElement(name="RefPoints")
            protected RefPointsType refPoints;
            @XmlElement(name="DescriptiveText")
            protected String descriptiveText;
            @XmlAttribute(name="AttractionCategoryCode")
            protected String attractionCategoryCode;
            @XmlAttribute(name="AttractionName")
            protected String attractionName;
            @XmlAttribute(name="AttractionFee")
            protected Float attractionFee;
            @XmlAttribute(name="CourtesyPhone")
            protected Boolean courtesyPhone;
            @XmlAttribute(name="ProximityCode")
            protected String proximityCode;
            @XmlAttribute(name="Sort")
            @XmlSchemaType(name="nonNegativeInteger")
            protected BigInteger sort;
            @XmlAttribute(name="MinAgeAppropriateCode")
            protected String minAgeAppropriateCode;
            @XmlAttribute(name="ApplicableStart")
            protected String applicableStart;
            @XmlAttribute(name="ApplicableEnd")
            protected String applicableEnd;
            @XmlAttribute(name="CodeDetail")
            protected String codeDetail;
            @XmlAttribute(name="Removal")
            protected Boolean removal;
            @XmlAttribute(name="ID")
            protected String id;
            @XmlAttribute(name="Start")
            protected String start;
            @XmlAttribute(name="Duration")
            protected String duration;
            @XmlAttribute(name="End")
            protected String end;
            @XmlAttribute(name="URI")
            @XmlSchemaType(name="anyURI")
            protected String uri;
            @XmlAttribute(name="Quantity")
            @XmlSchemaType(name="nonNegativeInteger")
            protected BigInteger quantity;
            @XmlAttribute(name="Code")
            protected String code;
            @XmlAttribute(name="CodeContext")
            protected String codeContext;

            public Contact getContact() {
                return this.contact;
            }

            public void setContact(Contact value) {
                this.contact = value;
            }

            public OperationSchedulesPlusChargeType getOperationSchedules() {
                return this.operationSchedules;
            }

            public void setOperationSchedules(OperationSchedulesPlusChargeType value) {
                this.operationSchedules = value;
            }

            public MultimediaDescriptionsType getMultimediaDescriptions() {
                return this.multimediaDescriptions;
            }

            public void setMultimediaDescriptions(MultimediaDescriptionsType value) {
                this.multimediaDescriptions = value;
            }

            public RefPointsType getRefPoints() {
                return this.refPoints;
            }

            public void setRefPoints(RefPointsType value) {
                this.refPoints = value;
            }

            public String getDescriptiveText() {
                return this.descriptiveText;
            }

            public void setDescriptiveText(String value) {
                this.descriptiveText = value;
            }

            public String getAttractionCategoryCode() {
                return this.attractionCategoryCode;
            }

            public void setAttractionCategoryCode(String value) {
                this.attractionCategoryCode = value;
            }

            public String getAttractionName() {
                return this.attractionName;
            }

            public void setAttractionName(String value) {
                this.attractionName = value;
            }

            public Float getAttractionFee() {
                return this.attractionFee;
            }

            public void setAttractionFee(Float value) {
                this.attractionFee = value;
            }

            public Boolean isCourtesyPhone() {
                return this.courtesyPhone;
            }

            public void setCourtesyPhone(Boolean value) {
                this.courtesyPhone = value;
            }

            public String getProximityCode() {
                return this.proximityCode;
            }

            public void setProximityCode(String value) {
                this.proximityCode = value;
            }

            public BigInteger getSort() {
                return this.sort;
            }

            public void setSort(BigInteger value) {
                this.sort = value;
            }

            public String getMinAgeAppropriateCode() {
                return this.minAgeAppropriateCode;
            }

            public void setMinAgeAppropriateCode(String value) {
                this.minAgeAppropriateCode = value;
            }

            public String getApplicableStart() {
                return this.applicableStart;
            }

            public void setApplicableStart(String value) {
                this.applicableStart = value;
            }

            public String getApplicableEnd() {
                return this.applicableEnd;
            }

            public void setApplicableEnd(String value) {
                this.applicableEnd = value;
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

            public String getID() {
                return this.id;
            }

            public void setID(String value) {
                this.id = value;
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

            public String getURI() {
                return this.uri;
            }

            public void setURI(String value) {
                this.uri = value;
            }

            public BigInteger getQuantity() {
                return this.quantity;
            }

            public void setQuantity(BigInteger value) {
                this.quantity = value;
            }

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

            @XmlAccessorType(value=XmlAccessType.FIELD)
            @XmlType(name="")
            public static class Contact
            extends ContactInfoType {
                @XmlAttribute(name="Removal")
                protected Boolean removal;

                public Boolean isRemoval() {
                    return this.removal;
                }

                public void setRemoval(Boolean value) {
                    this.removal = value;
                }
            }
        }
    }
}

