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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;
import org.opentravel.ota._2003._05.AddressInfoType;
import org.opentravel.ota._2003._05.PropertyValueMatchType;
import org.opentravel.ota._2003._05.RelativePositionType;
import org.opentravel.ota._2003._05.VendorMessagesType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="BasicPropertyInfoType", propOrder={"vendorMessages", "position", "address", "contactNumbers", "award", "relativePosition", "hotelAmenity", "recreation", "service", "policy"})
@XmlSeeAlso(value={PropertyValueMatchType.class})
public class BasicPropertyInfoType {
    @XmlElement(name="VendorMessages")
    protected VendorMessagesType vendorMessages;
    @XmlElement(name="Position")
    protected Position position;
    @XmlElement(name="Address")
    protected AddressInfoType address;
    @XmlElement(name="ContactNumbers")
    protected ContactNumbers contactNumbers;
    @XmlElement(name="Award")
    protected List<Award> award;
    @XmlElement(name="RelativePosition")
    protected RelativePositionType relativePosition;
    @XmlElement(name="HotelAmenity")
    protected List<HotelAmenity> hotelAmenity;
    @XmlElement(name="Recreation")
    protected List<Recreation> recreation;
    @XmlElement(name="Service")
    protected List<Service> service;
    @XmlElement(name="Policy")
    protected Policy policy;
    @XmlAttribute(name="HotelSegmentCategoryCode")
    protected String hotelSegmentCategoryCode;
    @XmlAttribute(name="SupplierIntegrationLevel")
    @XmlSchemaType(name="nonNegativeInteger")
    protected BigInteger supplierIntegrationLevel;
    @XmlAttribute(name="MaxGroupRoomQuantity")
    @XmlSchemaType(name="nonNegativeInteger")
    protected BigInteger maxGroupRoomQuantity;
    @XmlAttribute(name="CurrencyCode")
    protected String currencyCode;
    @XmlAttribute(name="MasterChainCode")
    protected String masterChainCode;
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

    public VendorMessagesType getVendorMessages() {
        return this.vendorMessages;
    }

    public void setVendorMessages(VendorMessagesType value) {
        this.vendorMessages = value;
    }

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position value) {
        this.position = value;
    }

    public AddressInfoType getAddress() {
        return this.address;
    }

    public void setAddress(AddressInfoType value) {
        this.address = value;
    }

    public ContactNumbers getContactNumbers() {
        return this.contactNumbers;
    }

    public void setContactNumbers(ContactNumbers value) {
        this.contactNumbers = value;
    }

    public List<Award> getAward() {
        if (this.award == null) {
            this.award = new ArrayList<Award>();
        }
        return this.award;
    }

    public RelativePositionType getRelativePosition() {
        return this.relativePosition;
    }

    public void setRelativePosition(RelativePositionType value) {
        this.relativePosition = value;
    }

    public List<HotelAmenity> getHotelAmenity() {
        if (this.hotelAmenity == null) {
            this.hotelAmenity = new ArrayList<HotelAmenity>();
        }
        return this.hotelAmenity;
    }

    public List<Recreation> getRecreation() {
        if (this.recreation == null) {
            this.recreation = new ArrayList<Recreation>();
        }
        return this.recreation;
    }

    public List<Service> getService() {
        if (this.service == null) {
            this.service = new ArrayList<Service>();
        }
        return this.service;
    }

    public Policy getPolicy() {
        return this.policy;
    }

    public void setPolicy(Policy value) {
        this.policy = value;
    }

    public String getHotelSegmentCategoryCode() {
        return this.hotelSegmentCategoryCode;
    }

    public void setHotelSegmentCategoryCode(String value) {
        this.hotelSegmentCategoryCode = value;
    }

    public BigInteger getSupplierIntegrationLevel() {
        return this.supplierIntegrationLevel;
    }

    public void setSupplierIntegrationLevel(BigInteger value) {
        this.supplierIntegrationLevel = value;
    }

    public BigInteger getMaxGroupRoomQuantity() {
        return this.maxGroupRoomQuantity;
    }

    public void setMaxGroupRoomQuantity(BigInteger value) {
        this.maxGroupRoomQuantity = value;
    }

    public String getCurrencyCode() {
        return this.currencyCode;
    }

    public void setCurrencyCode(String value) {
        this.currencyCode = value;
    }

    public String getMasterChainCode() {
        return this.masterChainCode;
    }

    public void setMasterChainCode(String value) {
        this.masterChainCode = value;
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

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class Service {
        @XmlAttribute(name="BusinessServiceCode")
        protected String businessServiceCode;

        public String getBusinessServiceCode() {
            return this.businessServiceCode;
        }

        public void setBusinessServiceCode(String value) {
            this.businessServiceCode = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class Recreation {
        @XmlAttribute(name="Code")
        protected String code;

        public String getCode() {
            return this.code;
        }

        public void setCode(String value) {
            this.code = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class Position {
        @XmlAttribute(name="MapURL")
        @XmlSchemaType(name="anyURI")
        protected String mapURL;
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

        public String getMapURL() {
            return this.mapURL;
        }

        public void setMapURL(String value) {
            this.mapURL = value;
        }

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
    @XmlType(name="")
    public static class Policy {
        @XmlAttribute(name="CheckInTime")
        @XmlSchemaType(name="time")
        protected XMLGregorianCalendar checkInTime;
        @XmlAttribute(name="CheckOutTime")
        @XmlSchemaType(name="time")
        protected XMLGregorianCalendar checkOutTime;

        public XMLGregorianCalendar getCheckInTime() {
            return this.checkInTime;
        }

        public void setCheckInTime(XMLGregorianCalendar value) {
            this.checkInTime = value;
        }

        public XMLGregorianCalendar getCheckOutTime() {
            return this.checkOutTime;
        }

        public void setCheckOutTime(XMLGregorianCalendar value) {
            this.checkOutTime = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class HotelAmenity {
        @XmlAttribute(name="Code")
        protected String code;

        public String getCode() {
            return this.code;
        }

        public void setCode(String value) {
            this.code = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"contactNumber"})
    public static class ContactNumbers {
        @XmlElement(name="ContactNumber")
        protected List<ContactNumber> contactNumber;

        public List<ContactNumber> getContactNumber() {
            if (this.contactNumber == null) {
                this.contactNumber = new ArrayList<ContactNumber>();
            }
            return this.contactNumber;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="")
        public static class ContactNumber {
            @XmlAttribute(name="RPH")
            protected String rph;
            @XmlAttribute(name="FormattedInd")
            protected Boolean formattedInd;
            @XmlAttribute(name="ShareSynchInd")
            @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
            protected String shareSynchInd;
            @XmlAttribute(name="ShareMarketInd")
            @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
            protected String shareMarketInd;
            @XmlAttribute(name="PhoneLocationType")
            protected String phoneLocationType;
            @XmlAttribute(name="PhoneTechType")
            protected String phoneTechType;
            @XmlAttribute(name="PhoneUseType")
            protected String phoneUseType;
            @XmlAttribute(name="CountryAccessCode")
            protected String countryAccessCode;
            @XmlAttribute(name="AreaCityCode")
            protected String areaCityCode;
            @XmlAttribute(name="PhoneNumber", required=true)
            protected String phoneNumber;
            @XmlAttribute(name="Extension")
            protected String extension;
            @XmlAttribute(name="PIN")
            protected String pin;
            @XmlAttribute(name="Remark")
            protected String remark;
            @XmlAttribute(name="DefaultInd")
            protected Boolean defaultInd;

            public String getRPH() {
                return this.rph;
            }

            public void setRPH(String value) {
                this.rph = value;
            }

            public Boolean isFormattedInd() {
                return this.formattedInd;
            }

            public void setFormattedInd(Boolean value) {
                this.formattedInd = value;
            }

            public String getShareSynchInd() {
                return this.shareSynchInd;
            }

            public void setShareSynchInd(String value) {
                this.shareSynchInd = value;
            }

            public String getShareMarketInd() {
                return this.shareMarketInd;
            }

            public void setShareMarketInd(String value) {
                this.shareMarketInd = value;
            }

            public String getPhoneLocationType() {
                return this.phoneLocationType;
            }

            public void setPhoneLocationType(String value) {
                this.phoneLocationType = value;
            }

            public String getPhoneTechType() {
                return this.phoneTechType;
            }

            public void setPhoneTechType(String value) {
                this.phoneTechType = value;
            }

            public String getPhoneUseType() {
                return this.phoneUseType;
            }

            public void setPhoneUseType(String value) {
                this.phoneUseType = value;
            }

            public String getCountryAccessCode() {
                return this.countryAccessCode;
            }

            public void setCountryAccessCode(String value) {
                this.countryAccessCode = value;
            }

            public String getAreaCityCode() {
                return this.areaCityCode;
            }

            public void setAreaCityCode(String value) {
                this.areaCityCode = value;
            }

            public String getPhoneNumber() {
                return this.phoneNumber;
            }

            public void setPhoneNumber(String value) {
                this.phoneNumber = value;
            }

            public String getExtension() {
                return this.extension;
            }

            public void setExtension(String value) {
                this.extension = value;
            }

            public String getPIN() {
                return this.pin;
            }

            public void setPIN(String value) {
                this.pin = value;
            }

            public String getRemark() {
                return this.remark;
            }

            public void setRemark(String value) {
                this.remark = value;
            }

            public Boolean isDefaultInd() {
                return this.defaultInd;
            }

            public void setDefaultInd(Boolean value) {
                this.defaultInd = value;
            }
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class Award {
        @XmlAttribute(name="Provider")
        protected String provider;
        @XmlAttribute(name="Rating")
        protected String rating;

        public String getProvider() {
            return this.provider;
        }

        public void setProvider(String value) {
            this.provider = value;
        }

        public String getRating() {
            return this.rating;
        }

        public void setRating(String value) {
            this.rating = value;
        }
    }
}

