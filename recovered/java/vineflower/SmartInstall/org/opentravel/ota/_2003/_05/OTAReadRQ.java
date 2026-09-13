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
@XmlType(name = "", propOrder = {"pos", "uniqueID", "readRequests"})
@XmlRootElement(name = "OTA_ReadRQ")
public class OTAReadRQ {
   @XmlElement(name = "POS")
   protected POSType pos;
   @XmlElement(name = "UniqueID")
   protected UniqueIDType uniqueID;
   @XmlElement(name = "ReadRequests")
   protected OTAReadRQ.ReadRequests readRequests;
   @XmlAttribute(name = "ReservationType")
   protected String reservationType;
   @XmlAttribute(name = "ReturnListIndicator")
   protected Boolean returnListIndicator;
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
   @XmlAttribute(name = "ReqRespVersion")
   protected String reqRespVersion;
   @XmlAttribute(name = "MoreIndicator")
   protected Boolean moreIndicator;
   @XmlAttribute(name = "MoreDataEchoToken")
   protected String moreDataEchoToken;
   @XmlAttribute(name = "MaxResponses")
   @XmlSchemaType(name = "positiveInteger")
   protected BigInteger maxResponses;

   public POSType getPOS() {
      return this.pos;
   }

   public void setPOS(POSType value) {
      this.pos = value;
   }

   public UniqueIDType getUniqueID() {
      return this.uniqueID;
   }

   public void setUniqueID(UniqueIDType value) {
      this.uniqueID = value;
   }

   public OTAReadRQ.ReadRequests getReadRequests() {
      return this.readRequests;
   }

   public void setReadRequests(OTAReadRQ.ReadRequests value) {
      this.readRequests = value;
   }

   public String getReservationType() {
      return this.reservationType;
   }

   public void setReservationType(String value) {
      this.reservationType = value;
   }

   public Boolean isReturnListIndicator() {
      return this.returnListIndicator;
   }

   public void setReturnListIndicator(Boolean value) {
      this.returnListIndicator = value;
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

   public String getReqRespVersion() {
      return this.reqRespVersion;
   }

   public void setReqRespVersion(String value) {
      this.reqRespVersion = value;
   }

   public Boolean isMoreIndicator() {
      return this.moreIndicator;
   }

   public void setMoreIndicator(Boolean value) {
      this.moreIndicator = value;
   }

   public String getMoreDataEchoToken() {
      return this.moreDataEchoToken;
   }

   public void setMoreDataEchoToken(String value) {
      this.moreDataEchoToken = value;
   }

   public BigInteger getMaxResponses() {
      return this.maxResponses;
   }

   public void setMaxResponses(BigInteger value) {
      this.maxResponses = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(
      name = "",
      propOrder = {
            "readRequest",
            "globalReservationReadRequest",
            "hotelReadRequest",
            "airReadRequest",
            "pkgReadRequest",
            "golfReadRequest",
            "vehicleReadRequest",
            "cruiseReadRequest",
            "profileReadRequest",
            "railReadRequest"
      }
   )
   public static class ReadRequests {
      @XmlElement(name = "ReadRequest")
      protected List<OTAReadRQ.ReadRequests.ReadRequest> readRequest;
      @XmlElement(name = "GlobalReservationReadRequest")
      protected List<OTAReadRQ.ReadRequests.GlobalReservationReadRequest> globalReservationReadRequest;
      @XmlElement(name = "HotelReadRequest")
      protected List<OTAReadRQ.ReadRequests.HotelReadRequest> hotelReadRequest;
      @XmlElement(name = "AirReadRequest")
      protected List<OTAReadRQ.ReadRequests.AirReadRequest> airReadRequest;
      @XmlElement(name = "PkgReadRequest")
      protected List<OTAReadRQ.ReadRequests.PkgReadRequest> pkgReadRequest;
      @XmlElement(name = "GolfReadRequest")
      protected List<OTAReadRQ.ReadRequests.GolfReadRequest> golfReadRequest;
      @XmlElement(name = "VehicleReadRequest")
      protected List<OTAReadRQ.ReadRequests.VehicleReadRequest> vehicleReadRequest;
      @XmlElement(name = "CruiseReadRequest")
      protected List<OTAReadRQ.ReadRequests.CruiseReadRequest> cruiseReadRequest;
      @XmlElement(name = "ProfileReadRequest")
      protected List<OTAReadRQ.ReadRequests.ProfileReadRequest> profileReadRequest;
      @XmlElement(name = "RailReadRequest")
      protected List<OTAReadRQ.ReadRequests.RailReadRequest> railReadRequest;

      public List<OTAReadRQ.ReadRequests.ReadRequest> getReadRequest() {
         if (this.readRequest == null) {
            this.readRequest = new ArrayList<>();
         }

         return this.readRequest;
      }

      public List<OTAReadRQ.ReadRequests.GlobalReservationReadRequest> getGlobalReservationReadRequest() {
         if (this.globalReservationReadRequest == null) {
            this.globalReservationReadRequest = new ArrayList<>();
         }

         return this.globalReservationReadRequest;
      }

      public List<OTAReadRQ.ReadRequests.HotelReadRequest> getHotelReadRequest() {
         if (this.hotelReadRequest == null) {
            this.hotelReadRequest = new ArrayList<>();
         }

         return this.hotelReadRequest;
      }

      public List<OTAReadRQ.ReadRequests.AirReadRequest> getAirReadRequest() {
         if (this.airReadRequest == null) {
            this.airReadRequest = new ArrayList<>();
         }

         return this.airReadRequest;
      }

      public List<OTAReadRQ.ReadRequests.PkgReadRequest> getPkgReadRequest() {
         if (this.pkgReadRequest == null) {
            this.pkgReadRequest = new ArrayList<>();
         }

         return this.pkgReadRequest;
      }

      public List<OTAReadRQ.ReadRequests.GolfReadRequest> getGolfReadRequest() {
         if (this.golfReadRequest == null) {
            this.golfReadRequest = new ArrayList<>();
         }

         return this.golfReadRequest;
      }

      public List<OTAReadRQ.ReadRequests.VehicleReadRequest> getVehicleReadRequest() {
         if (this.vehicleReadRequest == null) {
            this.vehicleReadRequest = new ArrayList<>();
         }

         return this.vehicleReadRequest;
      }

      public List<OTAReadRQ.ReadRequests.CruiseReadRequest> getCruiseReadRequest() {
         if (this.cruiseReadRequest == null) {
            this.cruiseReadRequest = new ArrayList<>();
         }

         return this.cruiseReadRequest;
      }

      public List<OTAReadRQ.ReadRequests.ProfileReadRequest> getProfileReadRequest() {
         if (this.profileReadRequest == null) {
            this.profileReadRequest = new ArrayList<>();
         }

         return this.profileReadRequest;
      }

      public List<OTAReadRQ.ReadRequests.RailReadRequest> getRailReadRequest() {
         if (this.railReadRequest == null) {
            this.railReadRequest = new ArrayList<>();
         }

         return this.railReadRequest;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(
         name = "",
         propOrder = {
               "pos",
               "airline",
               "flightNumber",
               "departureAirport",
               "departureDate",
               "name",
               "telephone",
               "custLoyalty",
               "creditCardInfo",
               "ticketNumber",
               "queueInfo",
               "date",
               "tpaExtensions"
         }
      )
      public static class AirReadRequest {
         @XmlElement(name = "POS")
         protected POSType pos;
         @XmlElement(name = "Airline")
         protected CompanyNameType airline;
         @XmlElement(name = "FlightNumber")
         protected String flightNumber;
         @XmlElement(name = "DepartureAirport")
         protected LocationType departureAirport;
         @XmlElement(name = "DepartureDate")
         @XmlSchemaType(name = "date")
         protected XMLGregorianCalendar departureDate;
         @XmlElement(name = "Name")
         protected PersonNameType name;
         @XmlElement(name = "Telephone")
         protected OTAReadRQ.ReadRequests.AirReadRequest.Telephone telephone;
         @XmlElement(name = "CustLoyalty")
         protected OTAReadRQ.ReadRequests.AirReadRequest.CustLoyalty custLoyalty;
         @XmlElement(name = "CreditCardInfo")
         protected PaymentCardType creditCardInfo;
         @XmlElement(name = "TicketNumber")
         protected TicketingInfoRSType ticketNumber;
         @XmlElement(name = "QueueInfo")
         protected OTAReadRQ.ReadRequests.AirReadRequest.QueueInfo queueInfo;
         @XmlElement(name = "Date")
         protected OTAReadRQ.ReadRequests.AirReadRequest.Date date;
         @XmlElement(name = "TPA_Extensions")
         protected TPAExtensionsType tpaExtensions;
         @XmlAttribute(name = "SeatNumber")
         protected String seatNumber;
         @XmlAttribute(name = "IncludeFF_EquivPartnerLev")
         protected Boolean includeFFEquivPartnerLev;
         @XmlAttribute(name = "ReturnFF_Number")
         protected Boolean returnFFNumber;
         @XmlAttribute(name = "ReturnDownlineSeg")
         protected Boolean returnDownlineSeg;
         @XmlAttribute(name = "InfoToReturn")
         protected String infoToReturn;
         @XmlAttribute(name = "FF_RequestCriteria")
         @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
         protected String ffRequestCriteria;
         @XmlAttribute(name = "No_SSR_Ind")
         protected Boolean noSSRInd;
         @XmlAttribute(name = "Start")
         protected String start;
         @XmlAttribute(name = "Duration")
         protected String duration;
         @XmlAttribute(name = "End")
         protected String end;

         public POSType getPOS() {
            return this.pos;
         }

         public void setPOS(POSType value) {
            this.pos = value;
         }

         public CompanyNameType getAirline() {
            return this.airline;
         }

         public void setAirline(CompanyNameType value) {
            this.airline = value;
         }

         public String getFlightNumber() {
            return this.flightNumber;
         }

         public void setFlightNumber(String value) {
            this.flightNumber = value;
         }

         public LocationType getDepartureAirport() {
            return this.departureAirport;
         }

         public void setDepartureAirport(LocationType value) {
            this.departureAirport = value;
         }

         public XMLGregorianCalendar getDepartureDate() {
            return this.departureDate;
         }

         public void setDepartureDate(XMLGregorianCalendar value) {
            this.departureDate = value;
         }

         public PersonNameType getName() {
            return this.name;
         }

         public void setName(PersonNameType value) {
            this.name = value;
         }

         public OTAReadRQ.ReadRequests.AirReadRequest.Telephone getTelephone() {
            return this.telephone;
         }

         public void setTelephone(OTAReadRQ.ReadRequests.AirReadRequest.Telephone value) {
            this.telephone = value;
         }

         public OTAReadRQ.ReadRequests.AirReadRequest.CustLoyalty getCustLoyalty() {
            return this.custLoyalty;
         }

         public void setCustLoyalty(OTAReadRQ.ReadRequests.AirReadRequest.CustLoyalty value) {
            this.custLoyalty = value;
         }

         public PaymentCardType getCreditCardInfo() {
            return this.creditCardInfo;
         }

         public void setCreditCardInfo(PaymentCardType value) {
            this.creditCardInfo = value;
         }

         public TicketingInfoRSType getTicketNumber() {
            return this.ticketNumber;
         }

         public void setTicketNumber(TicketingInfoRSType value) {
            this.ticketNumber = value;
         }

         public OTAReadRQ.ReadRequests.AirReadRequest.QueueInfo getQueueInfo() {
            return this.queueInfo;
         }

         public void setQueueInfo(OTAReadRQ.ReadRequests.AirReadRequest.QueueInfo value) {
            this.queueInfo = value;
         }

         public OTAReadRQ.ReadRequests.AirReadRequest.Date getDate() {
            return this.date;
         }

         public void setDate(OTAReadRQ.ReadRequests.AirReadRequest.Date value) {
            this.date = value;
         }

         public TPAExtensionsType getTPAExtensions() {
            return this.tpaExtensions;
         }

         public void setTPAExtensions(TPAExtensionsType value) {
            this.tpaExtensions = value;
         }

         public String getSeatNumber() {
            return this.seatNumber;
         }

         public void setSeatNumber(String value) {
            this.seatNumber = value;
         }

         public Boolean isIncludeFFEquivPartnerLev() {
            return this.includeFFEquivPartnerLev;
         }

         public void setIncludeFFEquivPartnerLev(Boolean value) {
            this.includeFFEquivPartnerLev = value;
         }

         public Boolean isReturnFFNumber() {
            return this.returnFFNumber;
         }

         public void setReturnFFNumber(Boolean value) {
            this.returnFFNumber = value;
         }

         public Boolean isReturnDownlineSeg() {
            return this.returnDownlineSeg;
         }

         public void setReturnDownlineSeg(Boolean value) {
            this.returnDownlineSeg = value;
         }

         public String getInfoToReturn() {
            return this.infoToReturn;
         }

         public void setInfoToReturn(String value) {
            this.infoToReturn = value;
         }

         public String getFFRequestCriteria() {
            return this.ffRequestCriteria;
         }

         public void setFFRequestCriteria(String value) {
            this.ffRequestCriteria = value;
         }

         public Boolean isNoSSRInd() {
            return this.noSSRInd;
         }

         public void setNoSSRInd(Boolean value) {
            this.noSSRInd = value;
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
         public static class CustLoyalty {
            @XmlAttribute(name = "ProgramID")
            protected String programID;
            @XmlAttribute(name = "MembershipID")
            protected String membershipID;
            @XmlAttribute(name = "TravelSector")
            protected String travelSector;
            @XmlAttribute(name = "RPH")
            protected String rph;
            @XmlAttribute(name = "VendorCode")
            protected List<String> vendorCode;
            @XmlAttribute(name = "PrimaryLoyaltyIndicator")
            protected Boolean primaryLoyaltyIndicator;
            @XmlAttribute(name = "AllianceLoyaltyLevelName")
            protected String allianceLoyaltyLevelName;
            @XmlAttribute(name = "CustomerType")
            protected String customerType;
            @XmlAttribute(name = "CustomerValue")
            protected String customerValue;
            @XmlAttribute(name = "Password")
            protected String password;
            @XmlAttribute(name = "ShareSynchInd")
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            protected String shareSynchInd;
            @XmlAttribute(name = "ShareMarketInd")
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            protected String shareMarketInd;
            @XmlAttribute(name = "EffectiveDate")
            @XmlSchemaType(name = "date")
            protected XMLGregorianCalendar effectiveDate;
            @XmlAttribute(name = "ExpireDate")
            @XmlSchemaType(name = "date")
            protected XMLGregorianCalendar expireDate;
            @XmlAttribute(name = "ExpireDateExclusiveIndicator")
            protected Boolean expireDateExclusiveIndicator;
            @XmlAttribute(name = "SingleVendorInd")
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            protected String singleVendorInd;
            @XmlAttribute(name = "LoyalLevel")
            protected String loyalLevel;
            @XmlAttribute(name = "LoyalLevelCode")
            protected Integer loyalLevelCode;
            @XmlAttribute(name = "SignupDate")
            @XmlSchemaType(name = "date")
            protected XMLGregorianCalendar signupDate;

            public String getProgramID() {
               return this.programID;
            }

            public void setProgramID(String value) {
               this.programID = value;
            }

            public String getMembershipID() {
               return this.membershipID;
            }

            public void setMembershipID(String value) {
               this.membershipID = value;
            }

            public String getTravelSector() {
               return this.travelSector;
            }

            public void setTravelSector(String value) {
               this.travelSector = value;
            }

            public String getRPH() {
               return this.rph;
            }

            public void setRPH(String value) {
               this.rph = value;
            }

            public List<String> getVendorCode() {
               if (this.vendorCode == null) {
                  this.vendorCode = new ArrayList<>();
               }

               return this.vendorCode;
            }

            public Boolean isPrimaryLoyaltyIndicator() {
               return this.primaryLoyaltyIndicator;
            }

            public void setPrimaryLoyaltyIndicator(Boolean value) {
               this.primaryLoyaltyIndicator = value;
            }

            public String getAllianceLoyaltyLevelName() {
               return this.allianceLoyaltyLevelName;
            }

            public void setAllianceLoyaltyLevelName(String value) {
               this.allianceLoyaltyLevelName = value;
            }

            public String getCustomerType() {
               return this.customerType;
            }

            public void setCustomerType(String value) {
               this.customerType = value;
            }

            public String getCustomerValue() {
               return this.customerValue;
            }

            public void setCustomerValue(String value) {
               this.customerValue = value;
            }

            public String getPassword() {
               return this.password;
            }

            public void setPassword(String value) {
               this.password = value;
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

            public XMLGregorianCalendar getEffectiveDate() {
               return this.effectiveDate;
            }

            public void setEffectiveDate(XMLGregorianCalendar value) {
               this.effectiveDate = value;
            }

            public XMLGregorianCalendar getExpireDate() {
               return this.expireDate;
            }

            public void setExpireDate(XMLGregorianCalendar value) {
               this.expireDate = value;
            }

            public Boolean isExpireDateExclusiveIndicator() {
               return this.expireDateExclusiveIndicator;
            }

            public void setExpireDateExclusiveIndicator(Boolean value) {
               this.expireDateExclusiveIndicator = value;
            }

            public String getSingleVendorInd() {
               return this.singleVendorInd;
            }

            public void setSingleVendorInd(String value) {
               this.singleVendorInd = value;
            }

            public String getLoyalLevel() {
               return this.loyalLevel;
            }

            public void setLoyalLevel(String value) {
               this.loyalLevel = value;
            }

            public Integer getLoyalLevelCode() {
               return this.loyalLevelCode;
            }

            public void setLoyalLevelCode(Integer value) {
               this.loyalLevelCode = value;
            }

            public XMLGregorianCalendar getSignupDate() {
               return this.signupDate;
            }

            public void setSignupDate(XMLGregorianCalendar value) {
               this.signupDate = value;
            }
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "")
         public static class Date {
            @XmlAttribute(name = "Start")
            protected String start;
            @XmlAttribute(name = "Duration")
            protected String duration;
            @XmlAttribute(name = "End")
            protected String end;

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

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "", propOrder = "queue")
         public static class QueueInfo {
            @XmlElement(name = "Queue", required = true)
            protected List<OTAReadRQ.ReadRequests.AirReadRequest.QueueInfo.Queue> queue;
            @XmlAttribute(name = "FirstItemOnlyInd")
            protected Boolean firstItemOnlyInd;
            @XmlAttribute(name = "RemoveFromQueueInd")
            protected Boolean removeFromQueueInd;
            @XmlAttribute(name = "FullDataInd")
            protected Boolean fullDataInd;
            @XmlAttribute(name = "StartDate")
            protected String startDate;
            @XmlAttribute(name = "EndDate")
            protected String endDate;

            public List<OTAReadRQ.ReadRequests.AirReadRequest.QueueInfo.Queue> getQueue() {
               if (this.queue == null) {
                  this.queue = new ArrayList<>();
               }

               return this.queue;
            }

            public Boolean isFirstItemOnlyInd() {
               return this.firstItemOnlyInd;
            }

            public void setFirstItemOnlyInd(Boolean value) {
               this.firstItemOnlyInd = value;
            }

            public Boolean isRemoveFromQueueInd() {
               return this.removeFromQueueInd;
            }

            public void setRemoveFromQueueInd(Boolean value) {
               this.removeFromQueueInd = value;
            }

            public Boolean isFullDataInd() {
               return this.fullDataInd;
            }

            public void setFullDataInd(Boolean value) {
               this.fullDataInd = value;
            }

            public String getStartDate() {
               return this.startDate;
            }

            public void setStartDate(String value) {
               this.startDate = value;
            }

            public String getEndDate() {
               return this.endDate;
            }

            public void setEndDate(String value) {
               this.endDate = value;
            }

            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "")
            public static class Queue {
               @XmlAttribute(name = "PseudoCityCode")
               protected String pseudoCityCode;
               @XmlAttribute(name = "QueueNumber")
               protected String queueNumber;
               @XmlAttribute(name = "QueueCategory")
               protected String queueCategory;
               @XmlAttribute(name = "SystemCode")
               protected String systemCode;
               @XmlAttribute(name = "QueueID")
               protected String queueID;

               public String getPseudoCityCode() {
                  return this.pseudoCityCode;
               }

               public void setPseudoCityCode(String value) {
                  this.pseudoCityCode = value;
               }

               public String getQueueNumber() {
                  return this.queueNumber;
               }

               public void setQueueNumber(String value) {
                  this.queueNumber = value;
               }

               public String getQueueCategory() {
                  return this.queueCategory;
               }

               public void setQueueCategory(String value) {
                  this.queueCategory = value;
               }

               public String getSystemCode() {
                  return this.systemCode;
               }

               public void setSystemCode(String value) {
                  this.systemCode = value;
               }

               public String getQueueID() {
                  return this.queueID;
               }

               public void setQueueID(String value) {
                  this.queueID = value;
               }
            }
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "")
         public static class Telephone {
            @XmlAttribute(name = "FormattedInd")
            protected Boolean formattedInd;
            @XmlAttribute(name = "ShareSynchInd")
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            protected String shareSynchInd;
            @XmlAttribute(name = "ShareMarketInd")
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            protected String shareMarketInd;
            @XmlAttribute(name = "PhoneLocationType")
            protected String phoneLocationType;
            @XmlAttribute(name = "PhoneTechType")
            protected String phoneTechType;
            @XmlAttribute(name = "PhoneUseType")
            protected String phoneUseType;
            @XmlAttribute(name = "CountryAccessCode")
            protected String countryAccessCode;
            @XmlAttribute(name = "AreaCityCode")
            protected String areaCityCode;
            @XmlAttribute(name = "PhoneNumber", required = true)
            protected String phoneNumber;
            @XmlAttribute(name = "Extension")
            protected String extension;
            @XmlAttribute(name = "PIN")
            protected String pin;
            @XmlAttribute(name = "Remark")
            protected String remark;

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
         }
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = {"selectedSailing", "guestInfo"})
      public static class CruiseReadRequest {
         @XmlElement(name = "SelectedSailing")
         protected OTAReadRQ.ReadRequests.CruiseReadRequest.SelectedSailing selectedSailing;
         @XmlElement(name = "GuestInfo")
         protected PersonNameType guestInfo;
         @XmlAttribute(name = "HistoryRequestedInd")
         protected Boolean historyRequestedInd;

         public OTAReadRQ.ReadRequests.CruiseReadRequest.SelectedSailing getSelectedSailing() {
            return this.selectedSailing;
         }

         public void setSelectedSailing(OTAReadRQ.ReadRequests.CruiseReadRequest.SelectedSailing value) {
            this.selectedSailing = value;
         }

         public PersonNameType getGuestInfo() {
            return this.guestInfo;
         }

         public void setGuestInfo(PersonNameType value) {
            this.guestInfo = value;
         }

         public Boolean isHistoryRequestedInd() {
            return this.historyRequestedInd;
         }

         public void setHistoryRequestedInd(Boolean value) {
            this.historyRequestedInd = value;
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "")
         public static class SelectedSailing {
            @XmlAttribute(name = "GroupCode")
            protected String groupCode;
            @XmlAttribute(name = "VoyageID")
            protected String voyageID;
            @XmlAttribute(name = "Status")
            protected String status;
            @XmlAttribute(name = "Start")
            protected String start;
            @XmlAttribute(name = "Duration")
            protected String duration;
            @XmlAttribute(name = "End")
            protected String end;
            @XmlAttribute(name = "VendorCode")
            protected String vendorCode;
            @XmlAttribute(name = "VendorName")
            protected String vendorName;
            @XmlAttribute(name = "ShipCode")
            protected String shipCode;
            @XmlAttribute(name = "ShipName")
            protected String shipName;
            @XmlAttribute(name = "VendorCodeContext")
            protected String vendorCodeContext;

            public String getGroupCode() {
               return this.groupCode;
            }

            public void setGroupCode(String value) {
               this.groupCode = value;
            }

            public String getVoyageID() {
               return this.voyageID;
            }

            public void setVoyageID(String value) {
               this.voyageID = value;
            }

            public String getStatus() {
               return this.status;
            }

            public void setStatus(String value) {
               this.status = value;
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

            public String getVendorCode() {
               return this.vendorCode;
            }

            public void setVendorCode(String value) {
               this.vendorCode = value;
            }

            public String getVendorName() {
               return this.vendorName;
            }

            public void setVendorName(String value) {
               this.vendorName = value;
            }

            public String getShipCode() {
               return this.shipCode;
            }

            public void setShipCode(String value) {
               this.shipCode = value;
            }

            public String getShipName() {
               return this.shipName;
            }

            public void setShipName(String value) {
               this.shipName = value;
            }

            public String getVendorCodeContext() {
               return this.vendorCodeContext;
            }

            public void setVendorCodeContext(String value) {
               this.vendorCodeContext = value;
            }
         }
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = "travelerName")
      public static class GlobalReservationReadRequest {
         @XmlElement(name = "TravelerName", required = true)
         protected PersonNameType travelerName;
         @XmlAttribute(name = "Start")
         protected String start;
         @XmlAttribute(name = "Duration")
         protected String duration;
         @XmlAttribute(name = "End")
         protected String end;

         public PersonNameType getTravelerName() {
            return this.travelerName;
         }

         public void setTravelerName(PersonNameType value) {
            this.travelerName = value;
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

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = {"membership", "name"})
      public static class GolfReadRequest {
         @XmlElement(name = "Membership")
         protected List<OTAReadRQ.ReadRequests.GolfReadRequest.Membership> membership;
         @XmlElement(name = "Name")
         protected PersonNameType name;
         @XmlAttribute(name = "RoundID")
         @XmlSchemaType(name = "positiveInteger")
         protected BigInteger roundID;
         @XmlAttribute(name = "PlayDateTime")
         protected String playDateTime;
         @XmlAttribute(name = "PackageID")
         protected String packageID;
         @XmlAttribute(name = "ID", required = true)
         protected String id;

         public List<OTAReadRQ.ReadRequests.GolfReadRequest.Membership> getMembership() {
            if (this.membership == null) {
               this.membership = new ArrayList<>();
            }

            return this.membership;
         }

         public PersonNameType getName() {
            return this.name;
         }

         public void setName(PersonNameType value) {
            this.name = value;
         }

         public BigInteger getRoundID() {
            return this.roundID;
         }

         public void setRoundID(BigInteger value) {
            this.roundID = value;
         }

         public String getPlayDateTime() {
            return this.playDateTime;
         }

         public void setPlayDateTime(String value) {
            this.playDateTime = value;
         }

         public String getPackageID() {
            return this.packageID;
         }

         public void setPackageID(String value) {
            this.packageID = value;
         }

         public String getID() {
            return this.id;
         }

         public void setID(String value) {
            this.id = value;
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "")
         public static class Membership {
            @XmlAttribute(name = "ProgramID")
            protected String programID;
            @XmlAttribute(name = "MembershipID")
            protected String membershipID;
            @XmlAttribute(name = "TravelSector")
            protected String travelSector;
            @XmlAttribute(name = "RPH")
            protected String rph;
            @XmlAttribute(name = "VendorCode")
            protected List<String> vendorCode;
            @XmlAttribute(name = "PrimaryLoyaltyIndicator")
            protected Boolean primaryLoyaltyIndicator;
            @XmlAttribute(name = "AllianceLoyaltyLevelName")
            protected String allianceLoyaltyLevelName;
            @XmlAttribute(name = "CustomerType")
            protected String customerType;
            @XmlAttribute(name = "CustomerValue")
            protected String customerValue;
            @XmlAttribute(name = "Password")
            protected String password;
            @XmlAttribute(name = "ShareSynchInd")
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            protected String shareSynchInd;
            @XmlAttribute(name = "ShareMarketInd")
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            protected String shareMarketInd;
            @XmlAttribute(name = "EffectiveDate")
            @XmlSchemaType(name = "date")
            protected XMLGregorianCalendar effectiveDate;
            @XmlAttribute(name = "ExpireDate")
            @XmlSchemaType(name = "date")
            protected XMLGregorianCalendar expireDate;
            @XmlAttribute(name = "ExpireDateExclusiveIndicator")
            protected Boolean expireDateExclusiveIndicator;
            @XmlAttribute(name = "SingleVendorInd")
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            protected String singleVendorInd;
            @XmlAttribute(name = "LoyalLevel")
            protected String loyalLevel;
            @XmlAttribute(name = "LoyalLevelCode")
            protected Integer loyalLevelCode;
            @XmlAttribute(name = "SignupDate")
            @XmlSchemaType(name = "date")
            protected XMLGregorianCalendar signupDate;

            public String getProgramID() {
               return this.programID;
            }

            public void setProgramID(String value) {
               this.programID = value;
            }

            public String getMembershipID() {
               return this.membershipID;
            }

            public void setMembershipID(String value) {
               this.membershipID = value;
            }

            public String getTravelSector() {
               return this.travelSector;
            }

            public void setTravelSector(String value) {
               this.travelSector = value;
            }

            public String getRPH() {
               return this.rph;
            }

            public void setRPH(String value) {
               this.rph = value;
            }

            public List<String> getVendorCode() {
               if (this.vendorCode == null) {
                  this.vendorCode = new ArrayList<>();
               }

               return this.vendorCode;
            }

            public Boolean isPrimaryLoyaltyIndicator() {
               return this.primaryLoyaltyIndicator;
            }

            public void setPrimaryLoyaltyIndicator(Boolean value) {
               this.primaryLoyaltyIndicator = value;
            }

            public String getAllianceLoyaltyLevelName() {
               return this.allianceLoyaltyLevelName;
            }

            public void setAllianceLoyaltyLevelName(String value) {
               this.allianceLoyaltyLevelName = value;
            }

            public String getCustomerType() {
               return this.customerType;
            }

            public void setCustomerType(String value) {
               this.customerType = value;
            }

            public String getCustomerValue() {
               return this.customerValue;
            }

            public void setCustomerValue(String value) {
               this.customerValue = value;
            }

            public String getPassword() {
               return this.password;
            }

            public void setPassword(String value) {
               this.password = value;
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

            public XMLGregorianCalendar getEffectiveDate() {
               return this.effectiveDate;
            }

            public void setEffectiveDate(XMLGregorianCalendar value) {
               this.effectiveDate = value;
            }

            public XMLGregorianCalendar getExpireDate() {
               return this.expireDate;
            }

            public void setExpireDate(XMLGregorianCalendar value) {
               this.expireDate = value;
            }

            public Boolean isExpireDateExclusiveIndicator() {
               return this.expireDateExclusiveIndicator;
            }

            public void setExpireDateExclusiveIndicator(Boolean value) {
               this.expireDateExclusiveIndicator = value;
            }

            public String getSingleVendorInd() {
               return this.singleVendorInd;
            }

            public void setSingleVendorInd(String value) {
               this.singleVendorInd = value;
            }

            public String getLoyalLevel() {
               return this.loyalLevel;
            }

            public void setLoyalLevel(String value) {
               this.loyalLevel = value;
            }

            public Integer getLoyalLevelCode() {
               return this.loyalLevelCode;
            }

            public void setLoyalLevelCode(Integer value) {
               this.loyalLevelCode = value;
            }

            public XMLGregorianCalendar getSignupDate() {
               return this.signupDate;
            }

            public void setSignupDate(XMLGregorianCalendar value) {
               this.signupDate = value;
            }
         }
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = {"cityName", "airport", "userID", "verification", "selectionCriteria", "tpaExtensions"})
      public static class HotelReadRequest {
         @XmlElement(name = "CityName")
         protected String cityName;
         @XmlElement(name = "Airport")
         protected OTAReadRQ.ReadRequests.HotelReadRequest.Airport airport;
         @XmlElement(name = "UserID")
         protected OTAReadRQ.ReadRequests.HotelReadRequest.UserID userID;
         @XmlElement(name = "Verification")
         protected VerificationType verification;
         @XmlElement(name = "SelectionCriteria")
         protected OTAReadRQ.ReadRequests.HotelReadRequest.SelectionCriteria selectionCriteria;
         @XmlElement(name = "TPA_Extensions")
         protected TPAExtensionsType tpaExtensions;
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

         public String getCityName() {
            return this.cityName;
         }

         public void setCityName(String value) {
            this.cityName = value;
         }

         public OTAReadRQ.ReadRequests.HotelReadRequest.Airport getAirport() {
            return this.airport;
         }

         public void setAirport(OTAReadRQ.ReadRequests.HotelReadRequest.Airport value) {
            this.airport = value;
         }

         public OTAReadRQ.ReadRequests.HotelReadRequest.UserID getUserID() {
            return this.userID;
         }

         public void setUserID(OTAReadRQ.ReadRequests.HotelReadRequest.UserID value) {
            this.userID = value;
         }

         public VerificationType getVerification() {
            return this.verification;
         }

         public void setVerification(VerificationType value) {
            this.verification = value;
         }

         public OTAReadRQ.ReadRequests.HotelReadRequest.SelectionCriteria getSelectionCriteria() {
            return this.selectionCriteria;
         }

         public void setSelectionCriteria(OTAReadRQ.ReadRequests.HotelReadRequest.SelectionCriteria value) {
            this.selectionCriteria = value;
         }

         public TPAExtensionsType getTPAExtensions() {
            return this.tpaExtensions;
         }

         public void setTPAExtensions(TPAExtensionsType value) {
            this.tpaExtensions = value;
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

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "")
         public static class Airport {
            @XmlAttribute(name = "LocationCode")
            protected String locationCode;
            @XmlAttribute(name = "CodeContext")
            protected String codeContext;
            @XmlAttribute(name = "AirportName")
            protected String airportName;

            public String getLocationCode() {
               return this.locationCode;
            }

            public void setLocationCode(String value) {
               this.locationCode = value;
            }

            public String getCodeContext() {
               return this.codeContext;
            }

            public void setCodeContext(String value) {
               this.codeContext = value;
            }

            public String getAirportName() {
               return this.airportName;
            }

            public void setAirportName(String value) {
               this.airportName = value;
            }
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "")
         public static class SelectionCriteria {
            @XmlAttribute(name = "DateType")
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            protected String dateType;
            @XmlAttribute(name = "SelectionType")
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            protected String selectionType;
            @XmlAttribute(name = "GroupCode")
            protected String groupCode;
            @XmlAttribute(name = "ResStatus")
            protected String resStatus;
            @XmlAttribute(name = "OriginalDeliveryMethodCode")
            protected String originalDeliveryMethodCode;
            @XmlAttribute(name = "Start")
            protected String start;
            @XmlAttribute(name = "Duration")
            protected String duration;
            @XmlAttribute(name = "End")
            protected String end;

            public String getDateType() {
               return this.dateType;
            }

            public void setDateType(String value) {
               this.dateType = value;
            }

            public String getSelectionType() {
               return this.selectionType;
            }

            public void setSelectionType(String value) {
               this.selectionType = value;
            }

            public String getGroupCode() {
               return this.groupCode;
            }

            public void setGroupCode(String value) {
               this.groupCode = value;
            }

            public String getResStatus() {
               return this.resStatus;
            }

            public void setResStatus(String value) {
               this.resStatus = value;
            }

            public String getOriginalDeliveryMethodCode() {
               return this.originalDeliveryMethodCode;
            }

            public void setOriginalDeliveryMethodCode(String value) {
               this.originalDeliveryMethodCode = value;
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

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "")
         public static class UserID extends UniqueIDType {
            @XmlAttribute(name = "PinNumber")
            protected String pinNumber;

            public String getPinNumber() {
               return this.pinNumber;
            }

            public void setPinNumber(String value) {
               this.pinNumber = value;
            }
         }
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = {"name", "arrivalLocation", "departureLocation"})
      public static class PkgReadRequest {
         @XmlElement(name = "Name")
         protected PersonNameType name;
         @XmlElement(name = "ArrivalLocation")
         protected LocationType arrivalLocation;
         @XmlElement(name = "DepartureLocation")
         protected LocationType departureLocation;
         @XmlAttribute(name = "TravelCode")
         protected String travelCode;
         @XmlAttribute(name = "TourCode")
         protected String tourCode;
         @XmlAttribute(name = "PackageID")
         protected String packageID;
         @XmlAttribute(name = "Start")
         protected String start;
         @XmlAttribute(name = "Duration")
         protected String duration;
         @XmlAttribute(name = "End")
         protected String end;

         public PersonNameType getName() {
            return this.name;
         }

         public void setName(PersonNameType value) {
            this.name = value;
         }

         public LocationType getArrivalLocation() {
            return this.arrivalLocation;
         }

         public void setArrivalLocation(LocationType value) {
            this.arrivalLocation = value;
         }

         public LocationType getDepartureLocation() {
            return this.departureLocation;
         }

         public void setDepartureLocation(LocationType value) {
            this.departureLocation = value;
         }

         public String getTravelCode() {
            return this.travelCode;
         }

         public void setTravelCode(String value) {
            this.travelCode = value;
         }

         public String getTourCode() {
            return this.tourCode;
         }

         public void setTourCode(String value) {
            this.tourCode = value;
         }

         public String getPackageID() {
            return this.packageID;
         }

         public void setPackageID(String value) {
            this.packageID = value;
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

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = {"uniqueID", "company", "customer"})
      public static class ProfileReadRequest {
         @XmlElement(name = "UniqueID")
         protected List<OTAReadRQ.ReadRequests.ProfileReadRequest.UniqueID> uniqueID;
         @XmlElement(name = "Company")
         protected ContactPersonType company;
         @XmlElement(name = "Customer")
         protected CustomerType customer;
         @XmlAttribute(name = "DateType")
         @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
         protected String dateType;
         @XmlAttribute(name = "StatusCode")
         protected String statusCode;
         @XmlAttribute(name = "ProfileTypeCode")
         protected String profileTypeCode;
         @XmlAttribute(name = "Start")
         protected String start;
         @XmlAttribute(name = "Duration")
         protected String duration;
         @XmlAttribute(name = "End")
         protected String end;

         public List<OTAReadRQ.ReadRequests.ProfileReadRequest.UniqueID> getUniqueID() {
            if (this.uniqueID == null) {
               this.uniqueID = new ArrayList<>();
            }

            return this.uniqueID;
         }

         public ContactPersonType getCompany() {
            return this.company;
         }

         public void setCompany(ContactPersonType value) {
            this.company = value;
         }

         public CustomerType getCustomer() {
            return this.customer;
         }

         public void setCustomer(CustomerType value) {
            this.customer = value;
         }

         public String getDateType() {
            return this.dateType;
         }

         public void setDateType(String value) {
            this.dateType = value;
         }

         public String getStatusCode() {
            return this.statusCode;
         }

         public void setStatusCode(String value) {
            this.statusCode = value;
         }

         public String getProfileTypeCode() {
            return this.profileTypeCode;
         }

         public void setProfileTypeCode(String value) {
            this.profileTypeCode = value;
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
         public static class UniqueID extends UniqueIDType {
            @XmlAttribute(name = "PinNumber")
            protected String pinNumber;

            public String getPinNumber() {
               return this.pinNumber;
            }

            public void setPinNumber(String value) {
               this.pinNumber = value;
            }
         }
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = {"pos", "train", "traveler", "bookingDateTime", "departureDateTime"})
      public static class RailReadRequest {
         @XmlElement(name = "POS")
         protected POSType pos;
         @XmlElement(name = "Train")
         protected TrainQueryType train;
         @XmlElement(name = "Traveler")
         protected PersonNameType traveler;
         @XmlElement(name = "BookingDateTime")
         protected OTAReadRQ.ReadRequests.RailReadRequest.BookingDateTime bookingDateTime;
         @XmlElement(name = "DepartureDateTime")
         protected OTAReadRQ.ReadRequests.RailReadRequest.DepartureDateTime departureDateTime;

         public POSType getPOS() {
            return this.pos;
         }

         public void setPOS(POSType value) {
            this.pos = value;
         }

         public TrainQueryType getTrain() {
            return this.train;
         }

         public void setTrain(TrainQueryType value) {
            this.train = value;
         }

         public PersonNameType getTraveler() {
            return this.traveler;
         }

         public void setTraveler(PersonNameType value) {
            this.traveler = value;
         }

         public OTAReadRQ.ReadRequests.RailReadRequest.BookingDateTime getBookingDateTime() {
            return this.bookingDateTime;
         }

         public void setBookingDateTime(OTAReadRQ.ReadRequests.RailReadRequest.BookingDateTime value) {
            this.bookingDateTime = value;
         }

         public OTAReadRQ.ReadRequests.RailReadRequest.DepartureDateTime getDepartureDateTime() {
            return this.departureDateTime;
         }

         public void setDepartureDateTime(OTAReadRQ.ReadRequests.RailReadRequest.DepartureDateTime value) {
            this.departureDateTime = value;
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "")
         public static class BookingDateTime {
            @XmlAttribute(name = "Start")
            protected String start;
            @XmlAttribute(name = "Duration")
            protected String duration;
            @XmlAttribute(name = "End")
            protected String end;

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

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "")
         public static class DepartureDateTime {
            @XmlAttribute(name = "Start")
            protected String start;
            @XmlAttribute(name = "Duration")
            protected String duration;
            @XmlAttribute(name = "End")
            protected String end;

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

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = {"uniqueID", "verification"})
      public static class ReadRequest {
         @XmlElement(name = "UniqueID", required = true)
         protected UniqueIDType uniqueID;
         @XmlElement(name = "Verification")
         protected VerificationType verification;
         @XmlAttribute(name = "HistoryRequestedInd")
         protected Boolean historyRequestedInd;

         public UniqueIDType getUniqueID() {
            return this.uniqueID;
         }

         public void setUniqueID(UniqueIDType value) {
            this.uniqueID = value;
         }

         public VerificationType getVerification() {
            return this.verification;
         }

         public void setVerification(VerificationType value) {
            this.verification = value;
         }

         public Boolean isHistoryRequestedInd() {
            return this.historyRequestedInd;
         }

         public void setHistoryRequestedInd(Boolean value) {
            this.historyRequestedInd = value;
         }
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = "vehRetResRQInfo")
      public static class VehicleReadRequest extends VehicleRetrieveResRQCoreType {
         @XmlElement(name = "VehRetResRQInfo", required = true)
         protected VehicleRetrieveResRQAdditionalInfoType vehRetResRQInfo;

         public VehicleRetrieveResRQAdditionalInfoType getVehRetResRQInfo() {
            return this.vehRetResRQInfo;
         }

         public void setVehRetResRQInfo(VehicleRetrieveResRQAdditionalInfoType value) {
            this.vehRetResRQInfo = value;
         }
      }
   }
}
