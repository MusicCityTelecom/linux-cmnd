/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;
import org.opentravel.ota._2003._05.BlackoutDateType;
import org.opentravel.ota._2003._05.CompanyNameType;
import org.opentravel.ota._2003._05.ContactInfoType;
import org.opentravel.ota._2003._05.ParagraphType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="AffiliationInfoType", propOrder={"distribSystems", "brands", "loyalPrograms", "awards", "partnerInfos", "descriptions"})
public class AffiliationInfoType {
    @XmlElement(name="DistribSystems")
    protected DistribSystems distribSystems;
    @XmlElement(name="Brands")
    protected Brands brands;
    @XmlElement(name="LoyalPrograms")
    protected LoyalPrograms loyalPrograms;
    @XmlElement(name="Awards")
    protected Awards awards;
    @XmlElement(name="PartnerInfos")
    protected PartnerInfos partnerInfos;
    @XmlElement(name="Descriptions")
    protected Descriptions descriptions;
    @XmlAttribute(name="LastUpdated")
    @XmlSchemaType(name="dateTime")
    protected XMLGregorianCalendar lastUpdated;

    public DistribSystems getDistribSystems() {
        return this.distribSystems;
    }

    public void setDistribSystems(DistribSystems value) {
        this.distribSystems = value;
    }

    public Brands getBrands() {
        return this.brands;
    }

    public void setBrands(Brands value) {
        this.brands = value;
    }

    public LoyalPrograms getLoyalPrograms() {
        return this.loyalPrograms;
    }

    public void setLoyalPrograms(LoyalPrograms value) {
        this.loyalPrograms = value;
    }

    public Awards getAwards() {
        return this.awards;
    }

    public void setAwards(Awards value) {
        this.awards = value;
    }

    public PartnerInfos getPartnerInfos() {
        return this.partnerInfos;
    }

    public void setPartnerInfos(PartnerInfos value) {
        this.partnerInfos = value;
    }

    public Descriptions getDescriptions() {
        return this.descriptions;
    }

    public void setDescriptions(Descriptions value) {
        this.descriptions = value;
    }

    public XMLGregorianCalendar getLastUpdated() {
        return this.lastUpdated;
    }

    public void setLastUpdated(XMLGregorianCalendar value) {
        this.lastUpdated = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"partnerInfo"})
    public static class PartnerInfos {
        @XmlElement(name="PartnerInfo", required=true)
        protected List<PartnerInfo> partnerInfo;

        public List<PartnerInfo> getPartnerInfo() {
            if (this.partnerInfo == null) {
                this.partnerInfo = new ArrayList<PartnerInfo>();
            }
            return this.partnerInfo;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="", propOrder={"contact", "description"})
        public static class PartnerInfo {
            @XmlElement(name="Contact")
            protected ContactInfoType contact;
            @XmlElement(name="Description")
            protected ParagraphType description;

            public ContactInfoType getContact() {
                return this.contact;
            }

            public void setContact(ContactInfoType value) {
                this.contact = value;
            }

            public ParagraphType getDescription() {
                return this.description;
            }

            public void setDescription(ParagraphType value) {
                this.description = value;
            }
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"loyalProgram"})
    public static class LoyalPrograms {
        @XmlElement(name="LoyalProgram", required=true)
        protected List<LoyalProgram> loyalProgram;

        public List<LoyalProgram> getLoyalProgram() {
            if (this.loyalProgram == null) {
                this.loyalProgram = new ArrayList<LoyalProgram>();
            }
            return this.loyalProgram;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="", propOrder={"programDescription", "programRestriction", "blackoutDates"})
        public static class LoyalProgram {
            @XmlElement(name="ProgramDescription")
            protected List<ProgramDescription> programDescription;
            @XmlElement(name="ProgramRestriction")
            protected ProgramRestriction programRestriction;
            @XmlElement(name="BlackoutDates")
            protected BlackoutDateType blackoutDates;
            @XmlAttribute(name="ProgramName")
            protected String programName;
            @XmlAttribute(name="SecondaryProgramName")
            protected String secondaryProgramName;
            @XmlAttribute(name="AffiliateProgramName")
            protected String affiliateProgramName;
            @XmlAttribute(name="HotelLevel")
            protected String hotelLevel;
            @XmlAttribute(name="ProgramCode")
            protected String programCode;
            @XmlAttribute(name="TravelSector")
            protected String travelSector;
            @XmlAttribute(name="PrimaryProgramInd")
            protected Boolean primaryProgramInd;
            @XmlAttribute(name="Removal")
            protected Boolean removal;

            public List<ProgramDescription> getProgramDescription() {
                if (this.programDescription == null) {
                    this.programDescription = new ArrayList<ProgramDescription>();
                }
                return this.programDescription;
            }

            public ProgramRestriction getProgramRestriction() {
                return this.programRestriction;
            }

            public void setProgramRestriction(ProgramRestriction value) {
                this.programRestriction = value;
            }

            public BlackoutDateType getBlackoutDates() {
                return this.blackoutDates;
            }

            public void setBlackoutDates(BlackoutDateType value) {
                this.blackoutDates = value;
            }

            public String getProgramName() {
                return this.programName;
            }

            public void setProgramName(String value) {
                this.programName = value;
            }

            public String getSecondaryProgramName() {
                return this.secondaryProgramName;
            }

            public void setSecondaryProgramName(String value) {
                this.secondaryProgramName = value;
            }

            public String getAffiliateProgramName() {
                return this.affiliateProgramName;
            }

            public void setAffiliateProgramName(String value) {
                this.affiliateProgramName = value;
            }

            public String getHotelLevel() {
                return this.hotelLevel;
            }

            public void setHotelLevel(String value) {
                this.hotelLevel = value;
            }

            public String getProgramCode() {
                return this.programCode;
            }

            public void setProgramCode(String value) {
                this.programCode = value;
            }

            public String getTravelSector() {
                return this.travelSector;
            }

            public void setTravelSector(String value) {
                this.travelSector = value;
            }

            public Boolean isPrimaryProgramInd() {
                return this.primaryProgramInd;
            }

            public void setPrimaryProgramInd(Boolean value) {
                this.primaryProgramInd = value;
            }

            public Boolean isRemoval() {
                return this.removal;
            }

            public void setRemoval(Boolean value) {
                this.removal = value;
            }

            @XmlAccessorType(value=XmlAccessType.FIELD)
            @XmlType(name="")
            public static class ProgramRestriction
            extends ParagraphType {
                @XmlAttribute(name="Removal")
                protected Boolean removal;

                public Boolean isRemoval() {
                    return this.removal;
                }

                public void setRemoval(Boolean value) {
                    this.removal = value;
                }
            }

            @XmlAccessorType(value=XmlAccessType.FIELD)
            @XmlType(name="")
            public static class ProgramDescription
            extends ParagraphType {
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

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"distribSystem"})
    public static class DistribSystems {
        @XmlElement(name="DistribSystem", required=true)
        protected DistribSystem distribSystem;

        public DistribSystem getDistribSystem() {
            return this.distribSystem;
        }

        public void setDistribSystem(DistribSystem value) {
            this.distribSystem = value;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="", propOrder={"companyName"})
        public static class DistribSystem {
            @XmlElement(name="CompanyName")
            protected CompanyNameType companyName;
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
            @XmlAttribute(name="Removal")
            protected Boolean removal;

            public CompanyNameType getCompanyName() {
                return this.companyName;
            }

            public void setCompanyName(CompanyNameType value) {
                this.companyName = value;
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

            public Boolean isRemoval() {
                return this.removal;
            }

            public void setRemoval(Boolean value) {
                this.removal = value;
            }
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"description"})
    public static class Descriptions {
        @XmlElement(name="Description", required=true)
        protected List<ParagraphType> description;

        public List<ParagraphType> getDescription() {
            if (this.description == null) {
                this.description = new ArrayList<ParagraphType>();
            }
            return this.description;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"brand"})
    public static class Brands {
        @XmlElement(name="Brand", required=true)
        protected List<Brand> brand;

        public List<Brand> getBrand() {
            if (this.brand == null) {
                this.brand = new ArrayList<Brand>();
            }
            return this.brand;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="")
        public static class Brand {
            @XmlAttribute(name="URL")
            protected String url;
            @XmlAttribute(name="CategoryCode")
            protected String categoryCode;
            @XmlAttribute(name="Marketing")
            protected String marketing;
            @XmlAttribute(name="CodeDetail")
            protected String codeDetail;
            @XmlAttribute(name="Removal")
            protected Boolean removal;

            public String getURL() {
                return this.url;
            }

            public void setURL(String value) {
                this.url = value;
            }

            public String getCategoryCode() {
                return this.categoryCode;
            }

            public void setCategoryCode(String value) {
                this.categoryCode = value;
            }

            public String getMarketing() {
                return this.marketing;
            }

            public void setMarketing(String value) {
                this.marketing = value;
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
    @XmlType(name="", propOrder={"award"})
    public static class Awards {
        @XmlElement(name="Award", required=true)
        protected List<Award> award;

        public List<Award> getAward() {
            if (this.award == null) {
                this.award = new ArrayList<Award>();
            }
            return this.award;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="")
        public static class Award {
            @XmlAttribute(name="Date")
            @XmlSchemaType(name="date")
            protected XMLGregorianCalendar date;
            @XmlAttribute(name="Provider")
            protected String provider;
            @XmlAttribute(name="Rating")
            protected String rating;
            @XmlAttribute(name="OfficialAppointmentInd")
            protected Boolean officialAppointmentInd;
            @XmlAttribute(name="RatingSymbol")
            @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
            protected String ratingSymbol;
            @XmlAttribute(name="RPH")
            protected String rph;
            @XmlAttribute(name="Removal")
            protected Boolean removal;

            public XMLGregorianCalendar getDate() {
                return this.date;
            }

            public void setDate(XMLGregorianCalendar value) {
                this.date = value;
            }

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

            public Boolean isOfficialAppointmentInd() {
                return this.officialAppointmentInd;
            }

            public void setOfficialAppointmentInd(Boolean value) {
                this.officialAppointmentInd = value;
            }

            public String getRatingSymbol() {
                return this.ratingSymbol;
            }

            public void setRatingSymbol(String value) {
                this.ratingSymbol = value;
            }

            public String getRPH() {
                return this.rph;
            }

            public void setRPH(String value) {
                this.rph = value;
            }

            public Boolean isRemoval() {
                return this.removal;
            }

            public void setRemoval(Boolean value) {
                this.removal = value;
            }
        }
    }
}

