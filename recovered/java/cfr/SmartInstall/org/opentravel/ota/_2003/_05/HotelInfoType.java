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
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;
import org.opentravel.ota._2003._05.CategoryCodesType;
import org.opentravel.ota._2003._05.ContactInfoType;
import org.opentravel.ota._2003._05.DateTimeSpanType;
import org.opentravel.ota._2003._05.FeaturesType;
import org.opentravel.ota._2003._05.MultimediaDescriptionsType;
import org.opentravel.ota._2003._05.OperationSchedulesPlusChargeType;
import org.opentravel.ota._2003._05.RelativePositionType;
import org.opentravel.ota._2003._05.WeatherInfoType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="HotelInfoType", propOrder={"hotelName", "closedSeasons", "blackoutDates", "relativePositions", "categoryCodes", "descriptions", "hotelInfoCodes", "position", "services", "weatherInfos", "ownershipManagementInfos", "languages"})
public class HotelInfoType {
    @XmlElement(name="HotelName")
    protected HotelName hotelName;
    @XmlElement(name="ClosedSeasons")
    protected ClosedSeasons closedSeasons;
    @XmlElement(name="BlackoutDates")
    protected BlackoutDates blackoutDates;
    @XmlElement(name="RelativePositions")
    protected RelativePositions relativePositions;
    @XmlElement(name="CategoryCodes")
    protected CategoryCodesType categoryCodes;
    @XmlElement(name="Descriptions")
    protected Descriptions descriptions;
    @XmlElement(name="HotelInfoCodes")
    protected HotelInfoCodes hotelInfoCodes;
    @XmlElement(name="Position")
    protected Position position;
    @XmlElement(name="Services")
    protected Services services;
    @XmlElement(name="WeatherInfos")
    protected WeatherInfos weatherInfos;
    @XmlElement(name="OwnershipManagementInfos")
    protected OwnershipManagementInfos ownershipManagementInfos;
    @XmlElement(name="Languages")
    protected Languages languages;
    @XmlAttribute(name="WhenBuilt")
    protected String whenBuilt;
    @XmlAttribute(name="LastUpdated")
    @XmlSchemaType(name="dateTime")
    protected XMLGregorianCalendar lastUpdated;
    @XmlAttribute(name="AreaWeather")
    protected String areaWeather;
    @XmlAttribute(name="InterfaceCompliance")
    protected String interfaceCompliance;
    @XmlAttribute(name="PMSSystem")
    protected String pmsSystem;
    @XmlAttribute(name="HotelStatus")
    protected String hotelStatus;
    @XmlAttribute(name="HotelStatusCode")
    protected String hotelStatusCode;
    @XmlAttribute(name="TaxID")
    protected String taxID;
    @XmlAttribute(name="DaylightSavingIndicator")
    protected Boolean daylightSavingIndicator;
    @XmlAttribute(name="ISO9000CertifiedInd")
    protected Boolean iso9000CertifiedInd;
    @XmlAttribute(name="Start")
    protected String start;
    @XmlAttribute(name="Duration")
    protected String duration;
    @XmlAttribute(name="End")
    protected String end;

    public HotelName getHotelName() {
        return this.hotelName;
    }

    public void setHotelName(HotelName value) {
        this.hotelName = value;
    }

    public ClosedSeasons getClosedSeasons() {
        return this.closedSeasons;
    }

    public void setClosedSeasons(ClosedSeasons value) {
        this.closedSeasons = value;
    }

    public BlackoutDates getBlackoutDates() {
        return this.blackoutDates;
    }

    public void setBlackoutDates(BlackoutDates value) {
        this.blackoutDates = value;
    }

    public RelativePositions getRelativePositions() {
        return this.relativePositions;
    }

    public void setRelativePositions(RelativePositions value) {
        this.relativePositions = value;
    }

    public CategoryCodesType getCategoryCodes() {
        return this.categoryCodes;
    }

    public void setCategoryCodes(CategoryCodesType value) {
        this.categoryCodes = value;
    }

    public Descriptions getDescriptions() {
        return this.descriptions;
    }

    public void setDescriptions(Descriptions value) {
        this.descriptions = value;
    }

    public HotelInfoCodes getHotelInfoCodes() {
        return this.hotelInfoCodes;
    }

    public void setHotelInfoCodes(HotelInfoCodes value) {
        this.hotelInfoCodes = value;
    }

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position value) {
        this.position = value;
    }

    public Services getServices() {
        return this.services;
    }

    public void setServices(Services value) {
        this.services = value;
    }

    public WeatherInfos getWeatherInfos() {
        return this.weatherInfos;
    }

    public void setWeatherInfos(WeatherInfos value) {
        this.weatherInfos = value;
    }

    public OwnershipManagementInfos getOwnershipManagementInfos() {
        return this.ownershipManagementInfos;
    }

    public void setOwnershipManagementInfos(OwnershipManagementInfos value) {
        this.ownershipManagementInfos = value;
    }

    public Languages getLanguages() {
        return this.languages;
    }

    public void setLanguages(Languages value) {
        this.languages = value;
    }

    public String getWhenBuilt() {
        return this.whenBuilt;
    }

    public void setWhenBuilt(String value) {
        this.whenBuilt = value;
    }

    public XMLGregorianCalendar getLastUpdated() {
        return this.lastUpdated;
    }

    public void setLastUpdated(XMLGregorianCalendar value) {
        this.lastUpdated = value;
    }

    public String getAreaWeather() {
        return this.areaWeather;
    }

    public void setAreaWeather(String value) {
        this.areaWeather = value;
    }

    public String getInterfaceCompliance() {
        return this.interfaceCompliance;
    }

    public void setInterfaceCompliance(String value) {
        this.interfaceCompliance = value;
    }

    public String getPMSSystem() {
        return this.pmsSystem;
    }

    public void setPMSSystem(String value) {
        this.pmsSystem = value;
    }

    public String getHotelStatus() {
        return this.hotelStatus;
    }

    public void setHotelStatus(String value) {
        this.hotelStatus = value;
    }

    public String getHotelStatusCode() {
        return this.hotelStatusCode;
    }

    public void setHotelStatusCode(String value) {
        this.hotelStatusCode = value;
    }

    public String getTaxID() {
        return this.taxID;
    }

    public void setTaxID(String value) {
        this.taxID = value;
    }

    public Boolean isDaylightSavingIndicator() {
        return this.daylightSavingIndicator;
    }

    public void setDaylightSavingIndicator(Boolean value) {
        this.daylightSavingIndicator = value;
    }

    public Boolean isISO9000CertifiedInd() {
        return this.iso9000CertifiedInd;
    }

    public void setISO9000CertifiedInd(Boolean value) {
        this.iso9000CertifiedInd = value;
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
    @XmlType(name="", propOrder={"weatherInfo"})
    public static class WeatherInfos {
        @XmlElement(name="WeatherInfo", required=true)
        protected List<WeatherInfoType> weatherInfo;

        public List<WeatherInfoType> getWeatherInfo() {
            if (this.weatherInfo == null) {
                this.weatherInfo = new ArrayList<WeatherInfoType>();
            }
            return this.weatherInfo;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"service"})
    public static class Services {
        @XmlElement(name="Service", required=true)
        protected List<Service> service;

        public List<Service> getService() {
            if (this.service == null) {
                this.service = new ArrayList<Service>();
            }
            return this.service;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="", propOrder={"contact", "relativePosition", "operationSchedules", "multimediaDescriptions", "features", "descriptiveText"})
        public static class Service {
            @XmlElement(name="Contact")
            protected ContactInfoType contact;
            @XmlElement(name="RelativePosition")
            protected RelativePositionType relativePosition;
            @XmlElement(name="OperationSchedules")
            protected OperationSchedulesPlusChargeType operationSchedules;
            @XmlElement(name="MultimediaDescriptions")
            protected MultimediaDescriptionsType multimediaDescriptions;
            @XmlElement(name="Features")
            protected FeaturesType features;
            @XmlElement(name="DescriptiveText")
            protected String descriptiveText;
            @XmlAttribute(name="Included")
            protected Boolean included;
            @XmlAttribute(name="Code")
            protected String code;
            @XmlAttribute(name="BusinessServiceCode")
            protected String businessServiceCode;
            @XmlAttribute(name="ExistsCode")
            protected String existsCode;
            @XmlAttribute(name="AvailableToAnyGuest")
            protected Boolean availableToAnyGuest;
            @XmlAttribute(name="InvCode")
            protected String invCode;
            @XmlAttribute(name="ProximityCode")
            protected String proximityCode;
            @XmlAttribute(name="MealPlanCode")
            protected String mealPlanCode;
            @XmlAttribute(name="Quantity")
            @XmlSchemaType(name="nonNegativeInteger")
            protected BigInteger quantity;
            @XmlAttribute(name="Sort")
            @XmlSchemaType(name="nonNegativeInteger")
            protected BigInteger sort;
            @XmlAttribute(name="MeetingRoomCode")
            protected String meetingRoomCode;
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

            public RelativePositionType getRelativePosition() {
                return this.relativePosition;
            }

            public void setRelativePosition(RelativePositionType value) {
                this.relativePosition = value;
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

            public FeaturesType getFeatures() {
                return this.features;
            }

            public void setFeatures(FeaturesType value) {
                this.features = value;
            }

            public String getDescriptiveText() {
                return this.descriptiveText;
            }

            public void setDescriptiveText(String value) {
                this.descriptiveText = value;
            }

            public Boolean isIncluded() {
                return this.included;
            }

            public void setIncluded(Boolean value) {
                this.included = value;
            }

            public String getCode() {
                return this.code;
            }

            public void setCode(String value) {
                this.code = value;
            }

            public String getBusinessServiceCode() {
                return this.businessServiceCode;
            }

            public void setBusinessServiceCode(String value) {
                this.businessServiceCode = value;
            }

            public String getExistsCode() {
                return this.existsCode;
            }

            public void setExistsCode(String value) {
                this.existsCode = value;
            }

            public Boolean isAvailableToAnyGuest() {
                return this.availableToAnyGuest;
            }

            public void setAvailableToAnyGuest(Boolean value) {
                this.availableToAnyGuest = value;
            }

            public String getInvCode() {
                return this.invCode;
            }

            public void setInvCode(String value) {
                this.invCode = value;
            }

            public String getProximityCode() {
                return this.proximityCode;
            }

            public void setProximityCode(String value) {
                this.proximityCode = value;
            }

            public String getMealPlanCode() {
                return this.mealPlanCode;
            }

            public void setMealPlanCode(String value) {
                this.mealPlanCode = value;
            }

            public BigInteger getQuantity() {
                return this.quantity;
            }

            public void setQuantity(BigInteger value) {
                this.quantity = value;
            }

            public BigInteger getSort() {
                return this.sort;
            }

            public void setSort(BigInteger value) {
                this.sort = value;
            }

            public String getMeetingRoomCode() {
                return this.meetingRoomCode;
            }

            public void setMeetingRoomCode(String value) {
                this.meetingRoomCode = value;
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
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"relativePosition"})
    public static class RelativePositions {
        @XmlElement(name="RelativePosition", required=true)
        protected List<RelativePositionType> relativePosition;

        public List<RelativePositionType> getRelativePosition() {
            if (this.relativePosition == null) {
                this.relativePosition = new ArrayList<RelativePositionType>();
            }
            return this.relativePosition;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class Position {
        @XmlAttribute(name="Latitude")
        protected String latitude;
        @XmlAttribute(name="Longitude")
        protected String longitude;
        @XmlAttribute(name="Altitude")
        protected String altitude;
        @XmlAttribute(name="AltitudeUnitOfMeasureCode")
        protected String altitudeUnitOfMeasureCode;
        @XmlAttribute(name="PositionAccuracy")
        protected String positionAccuracy;

        public String getLatitude() {
            return this.latitude;
        }

        public void setLatitude(String value) {
            this.latitude = value;
        }

        public String getLongitude() {
            return this.longitude;
        }

        public void setLongitude(String value) {
            this.longitude = value;
        }

        public String getAltitude() {
            return this.altitude;
        }

        public void setAltitude(String value) {
            this.altitude = value;
        }

        public String getAltitudeUnitOfMeasureCode() {
            return this.altitudeUnitOfMeasureCode;
        }

        public void setAltitudeUnitOfMeasureCode(String value) {
            this.altitudeUnitOfMeasureCode = value;
        }

        public String getPositionAccuracy() {
            return this.positionAccuracy;
        }

        public void setPositionAccuracy(String value) {
            this.positionAccuracy = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"ownershipManagementInfo"})
    public static class OwnershipManagementInfos {
        @XmlElement(name="OwnershipManagementInfo", required=true)
        protected List<OwnershipManagementInfo> ownershipManagementInfo;

        public List<OwnershipManagementInfo> getOwnershipManagementInfo() {
            if (this.ownershipManagementInfo == null) {
                this.ownershipManagementInfo = new ArrayList<OwnershipManagementInfo>();
            }
            return this.ownershipManagementInfo;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="")
        public static class OwnershipManagementInfo
        extends ContactInfoType {
            @XmlAttribute(name="RelationshipTypeCode")
            protected String relationshipTypeCode;

            public String getRelationshipTypeCode() {
                return this.relationshipTypeCode;
            }

            public void setRelationshipTypeCode(String value) {
                this.relationshipTypeCode = value;
            }
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"language"})
    public static class Languages {
        @XmlElement(name="Language", required=true)
        protected List<Language> language;

        public List<Language> getLanguage() {
            if (this.language == null) {
                this.language = new ArrayList<Language>();
            }
            return this.language;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="")
        public static class Language {
            @XmlAttribute(name="Language")
            @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
            @XmlSchemaType(name="language")
            protected String language;
            @XmlAttribute(name="PrimaryLangInd")
            protected Boolean primaryLangInd;

            public String getLanguage() {
                return this.language;
            }

            public void setLanguage(String value) {
                this.language = value;
            }

            public Boolean isPrimaryLangInd() {
                return this.primaryLangInd;
            }

            public void setPrimaryLangInd(Boolean value) {
                this.primaryLangInd = value;
            }
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"value"})
    public static class HotelName {
        @XmlValue
        protected String value;
        @XmlAttribute(name="HotelShortName")
        protected String hotelShortName;

        public String getValue() {
            return this.value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getHotelShortName() {
            return this.hotelShortName;
        }

        public void setHotelShortName(String value) {
            this.hotelShortName = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"hotelInfoCode"})
    public static class HotelInfoCodes {
        @XmlElement(name="HotelInfoCode", required=true)
        protected List<HotelInfoCode> hotelInfoCode;

        public List<HotelInfoCode> getHotelInfoCode() {
            if (this.hotelInfoCode == null) {
                this.hotelInfoCode = new ArrayList<HotelInfoCode>();
            }
            return this.hotelInfoCode;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="")
        public static class HotelInfoCode {
            @XmlAttribute(name="Code")
            protected String code;
            @XmlAttribute(name="OptionCode")
            protected String optionCode;
            @XmlAttribute(name="CodeDetail")
            protected String codeDetail;
            @XmlAttribute(name="Removal")
            protected Boolean removal;
            @XmlAttribute(name="Quantity")
            @XmlSchemaType(name="nonNegativeInteger")
            protected BigInteger quantity;

            public String getCode() {
                return this.code;
            }

            public void setCode(String value) {
                this.code = value;
            }

            public String getOptionCode() {
                return this.optionCode;
            }

            public void setOptionCode(String value) {
                this.optionCode = value;
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

            public BigInteger getQuantity() {
                return this.quantity;
            }

            public void setQuantity(BigInteger value) {
                this.quantity = value;
            }
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"renovation", "multimediaDescriptions", "descriptiveText"})
    public static class Descriptions {
        @XmlElement(name="Renovation")
        protected List<Renovation> renovation;
        @XmlElement(name="MultimediaDescriptions")
        protected MultimediaDescriptions multimediaDescriptions;
        @XmlElement(name="DescriptiveText")
        protected String descriptiveText;

        public List<Renovation> getRenovation() {
            if (this.renovation == null) {
                this.renovation = new ArrayList<Renovation>();
            }
            return this.renovation;
        }

        public MultimediaDescriptions getMultimediaDescriptions() {
            return this.multimediaDescriptions;
        }

        public void setMultimediaDescriptions(MultimediaDescriptions value) {
            this.multimediaDescriptions = value;
        }

        public String getDescriptiveText() {
            return this.descriptiveText;
        }

        public void setDescriptiveText(String value) {
            this.descriptiveText = value;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="", propOrder={"multimediaDescriptions", "descriptiveText"})
        public static class Renovation {
            @XmlElement(name="MultimediaDescriptions")
            protected MultimediaDescriptionsType multimediaDescriptions;
            @XmlElement(name="DescriptiveText")
            protected String descriptiveText;
            @XmlAttribute(name="ImmediatePlans")
            protected Boolean immediatePlans;
            @XmlAttribute(name="PercentOfRenovationCompleted")
            protected BigDecimal percentOfRenovationCompleted;
            @XmlAttribute(name="AreaText")
            protected String areaText;
            @XmlAttribute(name="RenovationCompletionDate")
            protected String renovationCompletionDate;
            @XmlAttribute(name="Start")
            protected String start;
            @XmlAttribute(name="Duration")
            protected String duration;
            @XmlAttribute(name="End")
            protected String end;

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

            public Boolean isImmediatePlans() {
                return this.immediatePlans;
            }

            public void setImmediatePlans(Boolean value) {
                this.immediatePlans = value;
            }

            public BigDecimal getPercentOfRenovationCompleted() {
                return this.percentOfRenovationCompleted;
            }

            public void setPercentOfRenovationCompleted(BigDecimal value) {
                this.percentOfRenovationCompleted = value;
            }

            public String getAreaText() {
                return this.areaText;
            }

            public void setAreaText(String value) {
                this.areaText = value;
            }

            public String getRenovationCompletionDate() {
                return this.renovationCompletionDate;
            }

            public void setRenovationCompletionDate(String value) {
                this.renovationCompletionDate = value;
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

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="")
        public static class MultimediaDescriptions
        extends MultimediaDescriptionsType {
            @XmlAttribute(name="InfoCode")
            protected String infoCode;
            @XmlAttribute(name="AdditionalDetailCode")
            protected String additionalDetailCode;

            public String getInfoCode() {
                return this.infoCode;
            }

            public void setInfoCode(String value) {
                this.infoCode = value;
            }

            public String getAdditionalDetailCode() {
                return this.additionalDetailCode;
            }

            public void setAdditionalDetailCode(String value) {
                this.additionalDetailCode = value;
            }
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"closedSeason"})
    public static class ClosedSeasons {
        @XmlElement(name="ClosedSeason", required=true)
        protected List<DateTimeSpanType> closedSeason;

        public List<DateTimeSpanType> getClosedSeason() {
            if (this.closedSeason == null) {
                this.closedSeason = new ArrayList<DateTimeSpanType>();
            }
            return this.closedSeason;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"blackoutDate"})
    public static class BlackoutDates {
        @XmlElement(name="BlackoutDate", required=true)
        protected List<BlackoutDate> blackoutDate;

        public List<BlackoutDate> getBlackoutDate() {
            if (this.blackoutDate == null) {
                this.blackoutDate = new ArrayList<BlackoutDate>();
            }
            return this.blackoutDate;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="")
        public static class BlackoutDate
        extends DateTimeSpanType {
            @XmlAttribute(name="Name")
            protected String name;

            public String getName() {
                return this.name;
            }

            public void setName(String value) {
                this.name = value;
            }
        }
    }
}

