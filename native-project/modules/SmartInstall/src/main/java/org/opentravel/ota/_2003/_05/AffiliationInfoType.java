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

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AffiliationInfoType", propOrder = {"distribSystems", "brands", "loyalPrograms", "awards", "partnerInfos", "descriptions"})
public class AffiliationInfoType {
   @XmlElement(name = "DistribSystems")
   protected AffiliationInfoType.DistribSystems distribSystems;
   @XmlElement(name = "Brands")
   protected AffiliationInfoType.Brands brands;
   @XmlElement(name = "LoyalPrograms")
   protected AffiliationInfoType.LoyalPrograms loyalPrograms;
   @XmlElement(name = "Awards")
   protected AffiliationInfoType.Awards awards;
   @XmlElement(name = "PartnerInfos")
   protected AffiliationInfoType.PartnerInfos partnerInfos;
   @XmlElement(name = "Descriptions")
   protected AffiliationInfoType.Descriptions descriptions;
   @XmlAttribute(name = "LastUpdated")
   @XmlSchemaType(name = "dateTime")
   protected XMLGregorianCalendar lastUpdated;

   public AffiliationInfoType.DistribSystems getDistribSystems() {
      return this.distribSystems;
   }

   public void setDistribSystems(AffiliationInfoType.DistribSystems value) {
      this.distribSystems = value;
   }

   public AffiliationInfoType.Brands getBrands() {
      return this.brands;
   }

   public void setBrands(AffiliationInfoType.Brands value) {
      this.brands = value;
   }

   public AffiliationInfoType.LoyalPrograms getLoyalPrograms() {
      return this.loyalPrograms;
   }

   public void setLoyalPrograms(AffiliationInfoType.LoyalPrograms value) {
      this.loyalPrograms = value;
   }

   public AffiliationInfoType.Awards getAwards() {
      return this.awards;
   }

   public void setAwards(AffiliationInfoType.Awards value) {
      this.awards = value;
   }

   public AffiliationInfoType.PartnerInfos getPartnerInfos() {
      return this.partnerInfos;
   }

   public void setPartnerInfos(AffiliationInfoType.PartnerInfos value) {
      this.partnerInfos = value;
   }

   public AffiliationInfoType.Descriptions getDescriptions() {
      return this.descriptions;
   }

   public void setDescriptions(AffiliationInfoType.Descriptions value) {
      this.descriptions = value;
   }

   public XMLGregorianCalendar getLastUpdated() {
      return this.lastUpdated;
   }

   public void setLastUpdated(XMLGregorianCalendar value) {
      this.lastUpdated = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "award")
   public static class Awards {
      @XmlElement(name = "Award", required = true)
      protected List<AffiliationInfoType.Awards.Award> award;

      public List<AffiliationInfoType.Awards.Award> getAward() {
         if (this.award == null) {
            this.award = new ArrayList<>();
         }

         return this.award;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class Award {
         @XmlAttribute(name = "Date")
         @XmlSchemaType(name = "date")
         protected XMLGregorianCalendar date;
         @XmlAttribute(name = "Provider")
         protected String provider;
         @XmlAttribute(name = "Rating")
         protected String rating;
         @XmlAttribute(name = "OfficialAppointmentInd")
         protected Boolean officialAppointmentInd;
         @XmlAttribute(name = "RatingSymbol")
         @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
         protected String ratingSymbol;
         @XmlAttribute(name = "RPH")
         protected String rph;
         @XmlAttribute(name = "Removal")
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

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "brand")
   public static class Brands {
      @XmlElement(name = "Brand", required = true)
      protected List<AffiliationInfoType.Brands.Brand> brand;

      public List<AffiliationInfoType.Brands.Brand> getBrand() {
         if (this.brand == null) {
            this.brand = new ArrayList<>();
         }

         return this.brand;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class Brand {
         @XmlAttribute(name = "URL")
         protected String url;
         @XmlAttribute(name = "CategoryCode")
         protected String categoryCode;
         @XmlAttribute(name = "Marketing")
         protected String marketing;
         @XmlAttribute(name = "CodeDetail")
         protected String codeDetail;
         @XmlAttribute(name = "Removal")
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

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "description")
   public static class Descriptions {
      @XmlElement(name = "Description", required = true)
      protected List<ParagraphType> description;

      public List<ParagraphType> getDescription() {
         if (this.description == null) {
            this.description = new ArrayList<>();
         }

         return this.description;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "distribSystem")
   public static class DistribSystems {
      @XmlElement(name = "DistribSystem", required = true)
      protected AffiliationInfoType.DistribSystems.DistribSystem distribSystem;

      public AffiliationInfoType.DistribSystems.DistribSystem getDistribSystem() {
         return this.distribSystem;
      }

      public void setDistribSystem(AffiliationInfoType.DistribSystems.DistribSystem value) {
         this.distribSystem = value;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = "companyName")
      public static class DistribSystem {
         @XmlElement(name = "CompanyName")
         protected CompanyNameType companyName;
         @XmlAttribute(name = "ChainCode")
         protected String chainCode;
         @XmlAttribute(name = "BrandCode")
         protected String brandCode;
         @XmlAttribute(name = "HotelCode")
         protected String hotelCode;
         @XmlAttribute(name = "HotelCityCode")
         protected String hotelCityCode;
         @XmlAttribute(name = "HotelName")
         protected String hotelName;
         @XmlAttribute(name = "HotelCodeContext")
         protected String hotelCodeContext;
         @XmlAttribute(name = "ChainName")
         protected String chainName;
         @XmlAttribute(name = "BrandName")
         protected String brandName;
         @XmlAttribute(name = "AreaID")
         protected String areaID;
         @XmlAttribute(name = "Removal")
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

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "loyalProgram")
   public static class LoyalPrograms {
      @XmlElement(name = "LoyalProgram", required = true)
      protected List<AffiliationInfoType.LoyalPrograms.LoyalProgram> loyalProgram;

      public List<AffiliationInfoType.LoyalPrograms.LoyalProgram> getLoyalProgram() {
         if (this.loyalProgram == null) {
            this.loyalProgram = new ArrayList<>();
         }

         return this.loyalProgram;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = {"programDescription", "programRestriction", "blackoutDates"})
      public static class LoyalProgram {
         @XmlElement(name = "ProgramDescription")
         protected List<AffiliationInfoType.LoyalPrograms.LoyalProgram.ProgramDescription> programDescription;
         @XmlElement(name = "ProgramRestriction")
         protected AffiliationInfoType.LoyalPrograms.LoyalProgram.ProgramRestriction programRestriction;
         @XmlElement(name = "BlackoutDates")
         protected BlackoutDateType blackoutDates;
         @XmlAttribute(name = "ProgramName")
         protected String programName;
         @XmlAttribute(name = "SecondaryProgramName")
         protected String secondaryProgramName;
         @XmlAttribute(name = "AffiliateProgramName")
         protected String affiliateProgramName;
         @XmlAttribute(name = "HotelLevel")
         protected String hotelLevel;
         @XmlAttribute(name = "ProgramCode")
         protected String programCode;
         @XmlAttribute(name = "TravelSector")
         protected String travelSector;
         @XmlAttribute(name = "PrimaryProgramInd")
         protected Boolean primaryProgramInd;
         @XmlAttribute(name = "Removal")
         protected Boolean removal;

         public List<AffiliationInfoType.LoyalPrograms.LoyalProgram.ProgramDescription> getProgramDescription() {
            if (this.programDescription == null) {
               this.programDescription = new ArrayList<>();
            }

            return this.programDescription;
         }

         public AffiliationInfoType.LoyalPrograms.LoyalProgram.ProgramRestriction getProgramRestriction() {
            return this.programRestriction;
         }

         public void setProgramRestriction(AffiliationInfoType.LoyalPrograms.LoyalProgram.ProgramRestriction value) {
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

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "")
         public static class ProgramDescription extends ParagraphType {
            @XmlAttribute(name = "Removal")
            protected Boolean removal;

            public Boolean isRemoval() {
               return this.removal;
            }

            public void setRemoval(Boolean value) {
               this.removal = value;
            }
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "")
         public static class ProgramRestriction extends ParagraphType {
            @XmlAttribute(name = "Removal")
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

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "partnerInfo")
   public static class PartnerInfos {
      @XmlElement(name = "PartnerInfo", required = true)
      protected List<AffiliationInfoType.PartnerInfos.PartnerInfo> partnerInfo;

      public List<AffiliationInfoType.PartnerInfos.PartnerInfo> getPartnerInfo() {
         if (this.partnerInfo == null) {
            this.partnerInfo = new ArrayList<>();
         }

         return this.partnerInfo;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = {"contact", "description"})
      public static class PartnerInfo {
         @XmlElement(name = "Contact")
         protected ContactInfoType contact;
         @XmlElement(name = "Description")
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
}
