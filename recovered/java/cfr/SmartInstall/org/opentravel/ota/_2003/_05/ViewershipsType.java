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
import javax.xml.bind.annotation.XmlValue;
import org.opentravel.ota._2003._05.ProfileType;
import org.opentravel.ota._2003._05.UniqueIDType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ViewershipsType", propOrder={"viewership"})
public class ViewershipsType {
    @XmlElement(name="Viewership", required=true)
    protected List<Viewership> viewership;

    public List<Viewership> getViewership() {
        if (this.viewership == null) {
            this.viewership = new ArrayList<Viewership>();
        }
        return this.viewership;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"viewershipCodes", "systemCodes", "profileTypes", "profileRefs", "profiles", "locationCodes", "bookingChannelCodes", "distributorTypes"})
    public static class Viewership {
        @XmlElement(name="ViewershipCodes")
        protected ViewershipCodes viewershipCodes;
        @XmlElement(name="SystemCodes")
        protected SystemCodes systemCodes;
        @XmlElement(name="ProfileTypes")
        protected ProfileTypes profileTypes;
        @XmlElement(name="ProfileRefs")
        protected ProfileRefs profileRefs;
        @XmlElement(name="Profiles")
        protected Profiles profiles;
        @XmlElement(name="LocationCodes")
        protected LocationCodes locationCodes;
        @XmlElement(name="BookingChannelCodes")
        protected BookingChannelCodes bookingChannelCodes;
        @XmlElement(name="DistributorTypes")
        protected DistributorTypes distributorTypes;
        @XmlAttribute(name="ViewershipRPH")
        protected String viewershipRPH;
        @XmlAttribute(name="ViewOnly")
        protected Boolean viewOnly;

        public ViewershipCodes getViewershipCodes() {
            return this.viewershipCodes;
        }

        public void setViewershipCodes(ViewershipCodes value) {
            this.viewershipCodes = value;
        }

        public SystemCodes getSystemCodes() {
            return this.systemCodes;
        }

        public void setSystemCodes(SystemCodes value) {
            this.systemCodes = value;
        }

        public ProfileTypes getProfileTypes() {
            return this.profileTypes;
        }

        public void setProfileTypes(ProfileTypes value) {
            this.profileTypes = value;
        }

        public ProfileRefs getProfileRefs() {
            return this.profileRefs;
        }

        public void setProfileRefs(ProfileRefs value) {
            this.profileRefs = value;
        }

        public Profiles getProfiles() {
            return this.profiles;
        }

        public void setProfiles(Profiles value) {
            this.profiles = value;
        }

        public LocationCodes getLocationCodes() {
            return this.locationCodes;
        }

        public void setLocationCodes(LocationCodes value) {
            this.locationCodes = value;
        }

        public BookingChannelCodes getBookingChannelCodes() {
            return this.bookingChannelCodes;
        }

        public void setBookingChannelCodes(BookingChannelCodes value) {
            this.bookingChannelCodes = value;
        }

        public DistributorTypes getDistributorTypes() {
            return this.distributorTypes;
        }

        public void setDistributorTypes(DistributorTypes value) {
            this.distributorTypes = value;
        }

        public String getViewershipRPH() {
            return this.viewershipRPH;
        }

        public void setViewershipRPH(String value) {
            this.viewershipRPH = value;
        }

        public Boolean isViewOnly() {
            return this.viewOnly;
        }

        public void setViewOnly(Boolean value) {
            this.viewOnly = value;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="", propOrder={"viewershipCode"})
        public static class ViewershipCodes {
            @XmlElement(name="ViewershipCode", required=true)
            protected String viewershipCode;

            public String getViewershipCode() {
                return this.viewershipCode;
            }

            public void setViewershipCode(String value) {
                this.viewershipCode = value;
            }
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="", propOrder={"systemCode"})
        public static class SystemCodes {
            @XmlElement(name="SystemCode", required=true)
            protected List<SystemCode> systemCode;
            @XmlAttribute(name="SystemCodesInclusive")
            protected Boolean systemCodesInclusive;

            public List<SystemCode> getSystemCode() {
                if (this.systemCode == null) {
                    this.systemCode = new ArrayList<SystemCode>();
                }
                return this.systemCode;
            }

            public Boolean isSystemCodesInclusive() {
                return this.systemCodesInclusive;
            }

            public void setSystemCodesInclusive(Boolean value) {
                this.systemCodesInclusive = value;
            }

            @XmlAccessorType(value=XmlAccessType.FIELD)
            @XmlType(name="", propOrder={"value"})
            public static class SystemCode {
                @XmlValue
                protected String value;
                @XmlAttribute(name="RestrictedDisplayIndicator")
                protected Boolean restrictedDisplayIndicator;
                @XmlAttribute(name="Sort")
                @XmlSchemaType(name="nonNegativeInteger")
                protected BigInteger sort;

                public String getValue() {
                    return this.value;
                }

                public void setValue(String value) {
                    this.value = value;
                }

                public Boolean isRestrictedDisplayIndicator() {
                    return this.restrictedDisplayIndicator;
                }

                public void setRestrictedDisplayIndicator(Boolean value) {
                    this.restrictedDisplayIndicator = value;
                }

                public BigInteger getSort() {
                    return this.sort;
                }

                public void setSort(BigInteger value) {
                    this.sort = value;
                }
            }
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="", propOrder={"profileType"})
        public static class ProfileTypes {
            @XmlElement(name="ProfileType", required=true)
            protected List<ProfileType> profileType;

            public List<ProfileType> getProfileType() {
                if (this.profileType == null) {
                    this.profileType = new ArrayList<ProfileType>();
                }
                return this.profileType;
            }

            @XmlAccessorType(value=XmlAccessType.FIELD)
            @XmlType(name="")
            public static class ProfileType {
                @XmlAttribute(name="ProfileType")
                protected String profileType;

                public String getProfileType() {
                    return this.profileType;
                }

                public void setProfileType(String value) {
                    this.profileType = value;
                }
            }
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="", propOrder={"profile"})
        public static class Profiles {
            @XmlElement(name="Profile", required=true)
            protected List<ProfileType> profile;

            public List<ProfileType> getProfile() {
                if (this.profile == null) {
                    this.profile = new ArrayList<ProfileType>();
                }
                return this.profile;
            }
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="", propOrder={"profileRef"})
        public static class ProfileRefs {
            @XmlElement(name="ProfileRef", required=true)
            protected List<UniqueIDType> profileRef;

            public List<UniqueIDType> getProfileRef() {
                if (this.profileRef == null) {
                    this.profileRef = new ArrayList<UniqueIDType>();
                }
                return this.profileRef;
            }
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="", propOrder={"locationCode"})
        public static class LocationCodes {
            @XmlElement(name="LocationCode", required=true)
            protected List<LocationCode> locationCode;
            @XmlAttribute(name="LocationCodesInclusive")
            protected Boolean locationCodesInclusive;

            public List<LocationCode> getLocationCode() {
                if (this.locationCode == null) {
                    this.locationCode = new ArrayList<LocationCode>();
                }
                return this.locationCode;
            }

            public Boolean isLocationCodesInclusive() {
                return this.locationCodesInclusive;
            }

            public void setLocationCodesInclusive(Boolean value) {
                this.locationCodesInclusive = value;
            }

            @XmlAccessorType(value=XmlAccessType.FIELD)
            @XmlType(name="")
            public static class LocationCode {
                @XmlAttribute(name="CityCode")
                protected String cityCode;
                @XmlAttribute(name="StateProvinceCode")
                protected String stateProvinceCode;
                @XmlAttribute(name="CountryCode")
                protected String countryCode;

                public String getCityCode() {
                    return this.cityCode;
                }

                public void setCityCode(String value) {
                    this.cityCode = value;
                }

                public String getStateProvinceCode() {
                    return this.stateProvinceCode;
                }

                public void setStateProvinceCode(String value) {
                    this.stateProvinceCode = value;
                }

                public String getCountryCode() {
                    return this.countryCode;
                }

                public void setCountryCode(String value) {
                    this.countryCode = value;
                }
            }
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="", propOrder={"distributorType"})
        public static class DistributorTypes {
            @XmlElement(name="DistributorType", required=true)
            protected List<DistributorType> distributorType;

            public List<DistributorType> getDistributorType() {
                if (this.distributorType == null) {
                    this.distributorType = new ArrayList<DistributorType>();
                }
                return this.distributorType;
            }

            @XmlAccessorType(value=XmlAccessType.FIELD)
            @XmlType(name="", propOrder={"value"})
            public static class DistributorType {
                @XmlValue
                protected String value;
                @XmlAttribute(name="DistributorCode")
                protected String distributorCode;
                @XmlAttribute(name="DistributorTypeCode")
                protected String distributorTypeCode;

                public String getValue() {
                    return this.value;
                }

                public void setValue(String value) {
                    this.value = value;
                }

                public String getDistributorCode() {
                    return this.distributorCode;
                }

                public void setDistributorCode(String value) {
                    this.distributorCode = value;
                }

                public String getDistributorTypeCode() {
                    return this.distributorTypeCode;
                }

                public void setDistributorTypeCode(String value) {
                    this.distributorTypeCode = value;
                }
            }
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="", propOrder={"bookingChannelCode"})
        public static class BookingChannelCodes {
            @XmlElement(name="BookingChannelCode", required=true)
            protected List<BookingChannelCode> bookingChannelCode;
            @XmlAttribute(name="ChannelCodesInclusive")
            protected Boolean channelCodesInclusive;

            public List<BookingChannelCode> getBookingChannelCode() {
                if (this.bookingChannelCode == null) {
                    this.bookingChannelCode = new ArrayList<BookingChannelCode>();
                }
                return this.bookingChannelCode;
            }

            public Boolean isChannelCodesInclusive() {
                return this.channelCodesInclusive;
            }

            public void setChannelCodesInclusive(Boolean value) {
                this.channelCodesInclusive = value;
            }

            @XmlAccessorType(value=XmlAccessType.FIELD)
            @XmlType(name="", propOrder={"value"})
            public static class BookingChannelCode {
                @XmlValue
                protected String value;
                @XmlAttribute(name="RestrictedDisplayIndicator")
                protected Boolean restrictedDisplayIndicator;
                @XmlAttribute(name="Sort")
                @XmlSchemaType(name="nonNegativeInteger")
                protected BigInteger sort;

                public String getValue() {
                    return this.value;
                }

                public void setValue(String value) {
                    this.value = value;
                }

                public Boolean isRestrictedDisplayIndicator() {
                    return this.restrictedDisplayIndicator;
                }

                public void setRestrictedDisplayIndicator(Boolean value) {
                    this.restrictedDisplayIndicator = value;
                }

                public BigInteger getSort() {
                    return this.sort;
                }

                public void setSort(BigInteger value) {
                    this.sort = value;
                }
            }
        }
    }
}

