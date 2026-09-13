package org.opentravel.ota._2003._05;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "",
   propOrder = {
         "pos",
         "success",
         "warnings",
         "profiles",
         "hotelStays",
         "roomStays",
         "services",
         "areas",
         "criteria",
         "currencyConversions",
         "rebatePrograms",
         "tpaExtensions",
         "errors"
   }
)
@XmlRootElement(name = "OTA_HotelAvailRS")
public class OTAHotelAvailRS {
   @XmlElement(name = "POS")
   protected POSType pos;
   @XmlElement(name = "Success")
   protected SuccessType success;
   @XmlElement(name = "Warnings")
   protected WarningsType warnings;
   @XmlElement(name = "Profiles")
   protected ProfilesType profiles;
   @XmlElement(name = "HotelStays")
   protected OTAHotelAvailRS.HotelStays hotelStays;
   @XmlElement(name = "RoomStays")
   protected OTAHotelAvailRS.RoomStays roomStays;
   @XmlElement(name = "Services")
   protected ServicesType services;
   @XmlElement(name = "Areas")
   protected AreasType areas;
   @XmlElement(name = "Criteria")
   protected OTAHotelAvailRS.Criteria criteria;
   @XmlElement(name = "CurrencyConversions")
   protected OTAHotelAvailRS.CurrencyConversions currencyConversions;
   @XmlElement(name = "RebatePrograms")
   protected OTAHotelAvailRS.RebatePrograms rebatePrograms;
   @XmlElement(name = "TPA_Extensions")
   protected TPAExtensionsType tpaExtensions;
   @XmlElement(name = "Errors")
   protected ErrorsType errors;
   @XmlAttribute(name = "SearchCacheLevel")
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   protected String searchCacheLevel;
   @XmlAttribute(name = "EchoToken")
   protected String echoToken;
   @XmlAttribute(name = "TimeStamp")
   @XmlSchemaType(name = "dateTime")
   protected XMLGregorianCalendar timeStamp;
   @XmlAttribute(name = "Target")
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   protected String target;
   @XmlAttribute(name = "TargetName")
   protected String targetName;
   @XmlAttribute(name = "Version", required = true)
   protected BigDecimal version;
   @XmlAttribute(name = "TransactionIdentifier")
   protected String transactionIdentifier;
   @XmlAttribute(name = "SequenceNmbr")
   @XmlSchemaType(name = "nonNegativeInteger")
   protected BigInteger sequenceNmbr;
   @XmlAttribute(name = "TransactionStatusCode")
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   protected String transactionStatusCode;
   @XmlAttribute(name = "RetransmissionIndicator")
   protected Boolean retransmissionIndicator;
   @XmlAttribute(name = "CorrelationID")
   protected String correlationID;
   @XmlAttribute(name = "AltLangID")
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   @XmlSchemaType(name = "language")
   protected String altLangID;
   @XmlAttribute(name = "PrimaryLangID")
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   @XmlSchemaType(name = "language")
   protected String primaryLangID;

   public POSType getPOS() {
      return this.pos;
   }

   public void setPOS(POSType value) {
      this.pos = value;
   }

   public SuccessType getSuccess() {
      return this.success;
   }

   public void setSuccess(SuccessType value) {
      this.success = value;
   }

   public WarningsType getWarnings() {
      return this.warnings;
   }

   public void setWarnings(WarningsType value) {
      this.warnings = value;
   }

   public ProfilesType getProfiles() {
      return this.profiles;
   }

   public void setProfiles(ProfilesType value) {
      this.profiles = value;
   }

   public OTAHotelAvailRS.HotelStays getHotelStays() {
      return this.hotelStays;
   }

   public void setHotelStays(OTAHotelAvailRS.HotelStays value) {
      this.hotelStays = value;
   }

   public OTAHotelAvailRS.RoomStays getRoomStays() {
      return this.roomStays;
   }

   public void setRoomStays(OTAHotelAvailRS.RoomStays value) {
      this.roomStays = value;
   }

   public ServicesType getServices() {
      return this.services;
   }

   public void setServices(ServicesType value) {
      this.services = value;
   }

   public AreasType getAreas() {
      return this.areas;
   }

   public void setAreas(AreasType value) {
      this.areas = value;
   }

   public OTAHotelAvailRS.Criteria getCriteria() {
      return this.criteria;
   }

   public void setCriteria(OTAHotelAvailRS.Criteria value) {
      this.criteria = value;
   }

   public OTAHotelAvailRS.CurrencyConversions getCurrencyConversions() {
      return this.currencyConversions;
   }

   public void setCurrencyConversions(OTAHotelAvailRS.CurrencyConversions value) {
      this.currencyConversions = value;
   }

   public OTAHotelAvailRS.RebatePrograms getRebatePrograms() {
      return this.rebatePrograms;
   }

   public void setRebatePrograms(OTAHotelAvailRS.RebatePrograms value) {
      this.rebatePrograms = value;
   }

   public TPAExtensionsType getTPAExtensions() {
      return this.tpaExtensions;
   }

   public void setTPAExtensions(TPAExtensionsType value) {
      this.tpaExtensions = value;
   }

   public ErrorsType getErrors() {
      return this.errors;
   }

   public void setErrors(ErrorsType value) {
      this.errors = value;
   }

   public String getSearchCacheLevel() {
      return this.searchCacheLevel;
   }

   public void setSearchCacheLevel(String value) {
      this.searchCacheLevel = value;
   }

   public String getEchoToken() {
      return this.echoToken;
   }

   public void setEchoToken(String value) {
      this.echoToken = value;
   }

   public XMLGregorianCalendar getTimeStamp() {
      return this.timeStamp;
   }

   public void setTimeStamp(XMLGregorianCalendar value) {
      this.timeStamp = value;
   }

   public String getTarget() {
      return this.target;
   }

   public void setTarget(String value) {
      this.target = value;
   }

   public String getTargetName() {
      return this.targetName;
   }

   public void setTargetName(String value) {
      this.targetName = value;
   }

   public BigDecimal getVersion() {
      return this.version;
   }

   public void setVersion(BigDecimal value) {
      this.version = value;
   }

   public String getTransactionIdentifier() {
      return this.transactionIdentifier;
   }

   public void setTransactionIdentifier(String value) {
      this.transactionIdentifier = value;
   }

   public BigInteger getSequenceNmbr() {
      return this.sequenceNmbr;
   }

   public void setSequenceNmbr(BigInteger value) {
      this.sequenceNmbr = value;
   }

   public String getTransactionStatusCode() {
      return this.transactionStatusCode;
   }

   public void setTransactionStatusCode(String value) {
      this.transactionStatusCode = value;
   }

   public Boolean isRetransmissionIndicator() {
      return this.retransmissionIndicator;
   }

   public void setRetransmissionIndicator(Boolean value) {
      this.retransmissionIndicator = value;
   }

   public String getCorrelationID() {
      return this.correlationID;
   }

   public void setCorrelationID(String value) {
      this.correlationID = value;
   }

   public String getAltLangID() {
      return this.altLangID;
   }

   public void setAltLangID(String value) {
      this.altLangID = value;
   }

   public String getPrimaryLangID() {
      return this.primaryLangID;
   }

   public void setPrimaryLangID(String value) {
      this.primaryLangID = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "criterion")
   public static class Criteria {
      @XmlElement(name = "Criterion", required = true)
      protected List<HotelSearchCriterionType> criterion;

      public List<HotelSearchCriterionType> getCriterion() {
         if (this.criterion == null) {
            this.criterion = new ArrayList<>();
         }

         return this.criterion;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "currencyConversion")
   public static class CurrencyConversions {
      @XmlElement(name = "CurrencyConversion", required = true)
      protected List<OTAHotelAvailRS.CurrencyConversions.CurrencyConversion> currencyConversion;

      public List<OTAHotelAvailRS.CurrencyConversions.CurrencyConversion> getCurrencyConversion() {
         if (this.currencyConversion == null) {
            this.currencyConversion = new ArrayList<>();
         }

         return this.currencyConversion;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class CurrencyConversion {
         @XmlAttribute(name = "RateConversion")
         protected BigDecimal rateConversion;
         @XmlAttribute(name = "SourceCurrencyCode")
         protected String sourceCurrencyCode;
         @XmlAttribute(name = "RequestedCurrencyCode")
         protected String requestedCurrencyCode;
         @XmlAttribute(name = "DecimalPlaces")
         @XmlSchemaType(name = "nonNegativeInteger")
         protected BigInteger decimalPlaces;
         @XmlAttribute(name = "Source")
         protected String source;

         public BigDecimal getRateConversion() {
            return this.rateConversion;
         }

         public void setRateConversion(BigDecimal value) {
            this.rateConversion = value;
         }

         public String getSourceCurrencyCode() {
            return this.sourceCurrencyCode;
         }

         public void setSourceCurrencyCode(String value) {
            this.sourceCurrencyCode = value;
         }

         public String getRequestedCurrencyCode() {
            return this.requestedCurrencyCode;
         }

         public void setRequestedCurrencyCode(String value) {
            this.requestedCurrencyCode = value;
         }

         public BigInteger getDecimalPlaces() {
            return this.decimalPlaces;
         }

         public void setDecimalPlaces(BigInteger value) {
            this.decimalPlaces = value;
         }

         public String getSource() {
            return this.source;
         }

         public void setSource(String value) {
            this.source = value;
         }
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "hotelStay")
   public static class HotelStays {
      @XmlElement(name = "HotelStay", required = true)
      protected List<OTAHotelAvailRS.HotelStays.HotelStay> hotelStay;

      public List<OTAHotelAvailRS.HotelStays.HotelStay> getHotelStay() {
         if (this.hotelStay == null) {
            this.hotelStay = new ArrayList<>();
         }

         return this.hotelStay;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = {"availability", "basicPropertyInfo", "price"})
      public static class HotelStay {
         @XmlElement(name = "Availability")
         protected List<OTAHotelAvailRS.HotelStays.HotelStay.Availability> availability;
         @XmlElement(name = "BasicPropertyInfo")
         protected BasicPropertyInfoType basicPropertyInfo;
         @XmlElement(name = "Price")
         protected List<OTAHotelAvailRS.HotelStays.HotelStay.Price> price;
         @XmlAttribute(name = "RoomStayRPH")
         protected List<String> roomStayRPH;

         public List<OTAHotelAvailRS.HotelStays.HotelStay.Availability> getAvailability() {
            if (this.availability == null) {
               this.availability = new ArrayList<>();
            }

            return this.availability;
         }

         public BasicPropertyInfoType getBasicPropertyInfo() {
            return this.basicPropertyInfo;
         }

         public void setBasicPropertyInfo(BasicPropertyInfoType value) {
            this.basicPropertyInfo = value;
         }

         public List<OTAHotelAvailRS.HotelStays.HotelStay.Price> getPrice() {
            if (this.price == null) {
               this.price = new ArrayList<>();
            }

            return this.price;
         }

         public List<String> getRoomStayRPH() {
            if (this.roomStayRPH == null) {
               this.roomStayRPH = new ArrayList<>();
            }

            return this.roomStayRPH;
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "", propOrder = "restriction")
         public static class Availability {
            @XmlElement(name = "Restriction")
            protected List<OTAHotelAvailRS.HotelStays.HotelStay.Availability.Restriction> restriction;
            @XmlAttribute(name = "Status", required = true)
            protected List<String> status;
            @XmlAttribute(name = "Start")
            protected String start;
            @XmlAttribute(name = "Duration")
            protected String duration;
            @XmlAttribute(name = "End")
            protected String end;

            public List<OTAHotelAvailRS.HotelStays.HotelStay.Availability.Restriction> getRestriction() {
               if (this.restriction == null) {
                  this.restriction = new ArrayList<>();
               }

               return this.restriction;
            }

            public List<String> getStatus() {
               if (this.status == null) {
                  this.status = new ArrayList<>();
               }

               return this.status;
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

            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "")
            public static class Restriction {
               @XmlAttribute(name = "RestrictionType")
               @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
               protected String restrictionType;
               @XmlAttribute(name = "Time")
               protected BigInteger time;
               @XmlAttribute(name = "TimeUnit")
               protected TimeUnitType timeUnit;

               public String getRestrictionType() {
                  return this.restrictionType;
               }

               public void setRestrictionType(String value) {
                  this.restrictionType = value;
               }

               public BigInteger getTime() {
                  return this.time;
               }

               public void setTime(BigInteger value) {
                  this.time = value;
               }

               public TimeUnitType getTimeUnit() {
                  return this.timeUnit;
               }

               public void setTimeUnit(TimeUnitType value) {
                  this.timeUnit = value;
               }
            }
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "")
         public static class Price {
            @XmlAttribute(name = "AmountBeforeTax")
            protected BigDecimal amountBeforeTax;
            @XmlAttribute(name = "AmountAfterTax")
            protected BigDecimal amountAfterTax;
            @XmlAttribute(name = "CurrencyCode")
            protected String currencyCode;
            @XmlAttribute(name = "Decimal")
            @XmlSchemaType(name = "nonNegativeInteger")
            protected BigInteger decimal;
            @XmlAttribute(name = "Start")
            protected String start;
            @XmlAttribute(name = "Duration")
            protected String duration;
            @XmlAttribute(name = "End")
            protected String end;

            public BigDecimal getAmountBeforeTax() {
               return this.amountBeforeTax;
            }

            public void setAmountBeforeTax(BigDecimal value) {
               this.amountBeforeTax = value;
            }

            public BigDecimal getAmountAfterTax() {
               return this.amountAfterTax;
            }

            public void setAmountAfterTax(BigDecimal value) {
               this.amountAfterTax = value;
            }

            public String getCurrencyCode() {
               return this.currencyCode;
            }

            public void setCurrencyCode(String value) {
               this.currencyCode = value;
            }

            public BigInteger getDecimal() {
               return this.decimal;
            }

            public void setDecimal(BigInteger value) {
               this.decimal = value;
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

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "rebateProgram")
   public static class RebatePrograms {
      @XmlElement(name = "RebateProgram", required = true)
      protected List<RebateType> rebateProgram;

      public List<RebateType> getRebateProgram() {
         if (this.rebateProgram == null) {
            this.rebateProgram = new ArrayList<>();
         }

         return this.rebateProgram;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "roomStay")
   public static class RoomStays {
      @XmlElement(name = "RoomStay", required = true)
      protected List<OTAHotelAvailRS.RoomStays.RoomStay> roomStay;
      @XmlAttribute(name = "MoreIndicator")
      protected String moreIndicator;
      @XmlAttribute(name = "SortOrder")
      @XmlSchemaType(name = "positiveInteger")
      protected BigInteger sortOrder;

      public List<OTAHotelAvailRS.RoomStays.RoomStay> getRoomStay() {
         if (this.roomStay == null) {
            this.roomStay = new ArrayList<>();
         }

         return this.roomStay;
      }

      public String getMoreIndicator() {
         return this.moreIndicator;
      }

      public void setMoreIndicator(String value) {
         this.moreIndicator = value;
      }

      public BigInteger getSortOrder() {
         return this.sortOrder;
      }

      public void setSortOrder(BigInteger value) {
         this.sortOrder = value;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = {"reference", "serviceRPHs"})
      public static class RoomStay extends RoomStayType {
         @XmlElement(name = "Reference")
         protected OTAHotelAvailRS.RoomStays.RoomStay.Reference reference;
         @XmlElement(name = "ServiceRPHs")
         protected ServiceRPHsType serviceRPHs;
         @XmlAttribute(name = "IsAlternate")
         protected Boolean isAlternate;
         @XmlAttribute(name = "AvailabilityStatus")
         protected RateIndicatorType availabilityStatus;
         @XmlAttribute(name = "RoomStayCandidateRPH")
         protected String roomStayCandidateRPH;
         @XmlAttribute(name = "MoreDataEchoToken")
         protected String moreDataEchoToken;
         @XmlAttribute(name = "InfoSource")
         protected String infoSource;
         @XmlAttribute(name = "RPH")
         protected String rph;
         @XmlAttribute(name = "AvailableIndicator")
         protected Boolean availableIndicator;
         @XmlAttribute(name = "ResponseType")
         protected String responseType;

         public OTAHotelAvailRS.RoomStays.RoomStay.Reference getReference() {
            return this.reference;
         }

         public void setReference(OTAHotelAvailRS.RoomStays.RoomStay.Reference value) {
            this.reference = value;
         }

         public ServiceRPHsType getServiceRPHs() {
            return this.serviceRPHs;
         }

         public void setServiceRPHs(ServiceRPHsType value) {
            this.serviceRPHs = value;
         }

         public Boolean isIsAlternate() {
            return this.isAlternate;
         }

         public void setIsAlternate(Boolean value) {
            this.isAlternate = value;
         }

         public RateIndicatorType getAvailabilityStatus() {
            return this.availabilityStatus;
         }

         public void setAvailabilityStatus(RateIndicatorType value) {
            this.availabilityStatus = value;
         }

         public String getRoomStayCandidateRPH() {
            return this.roomStayCandidateRPH;
         }

         public void setRoomStayCandidateRPH(String value) {
            this.roomStayCandidateRPH = value;
         }

         public String getMoreDataEchoToken() {
            return this.moreDataEchoToken;
         }

         public void setMoreDataEchoToken(String value) {
            this.moreDataEchoToken = value;
         }

         public String getInfoSource() {
            return this.infoSource;
         }

         public void setInfoSource(String value) {
            this.infoSource = value;
         }

         public String getRPH() {
            return this.rph;
         }

         public void setRPH(String value) {
            this.rph = value;
         }

         public Boolean isAvailableIndicator() {
            return this.availableIndicator;
         }

         public void setAvailableIndicator(Boolean value) {
            this.availableIndicator = value;
         }

         public String getResponseType() {
            return this.responseType;
         }

         public void setResponseType(String value) {
            this.responseType = value;
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "")
         public static class Reference extends UniqueIDType {
            @XmlAttribute(name = "DateTime")
            @XmlSchemaType(name = "dateTime")
            protected XMLGregorianCalendar dateTime;

            public XMLGregorianCalendar getDateTime() {
               return this.dateTime;
            }

            public void setDateTime(XMLGregorianCalendar value) {
               this.dateTime = value;
            }
         }
      }
   }
}
