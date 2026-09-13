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
@XmlType(name = "", propOrder = {"success", "warnings", "reservationsList", "errors"})
@XmlRootElement(name = "OTA_ResRetrieveRS")
public class OTAResRetrieveRS {
   @XmlElement(name = "Success")
   protected SuccessType success;
   @XmlElement(name = "Warnings")
   protected WarningsType warnings;
   @XmlElement(name = "ReservationsList")
   protected OTAResRetrieveRS.ReservationsList reservationsList;
   @XmlElement(name = "Errors")
   protected ErrorsType errors;
   @XmlAttribute(name = "MoreIndicator")
   protected Boolean moreIndicator;
   @XmlAttribute(name = "MoreDataEchoToken")
   protected String moreDataEchoToken;
   @XmlAttribute(name = "MaxResponses")
   @XmlSchemaType(name = "positiveInteger")
   protected BigInteger maxResponses;
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

   public OTAResRetrieveRS.ReservationsList getReservationsList() {
      return this.reservationsList;
   }

   public void setReservationsList(OTAResRetrieveRS.ReservationsList value) {
      this.reservationsList = value;
   }

   public ErrorsType getErrors() {
      return this.errors;
   }

   public void setErrors(ErrorsType value) {
      this.errors = value;
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
   @XmlType(
      name = "",
      propOrder = {
            "airReservation",
            "hotelReservation",
            "packageReservation",
            "golfReservation",
            "vehicleReservation",
            "globalReservation",
            "cruiseReservation",
            "railReservation"
      }
   )
   public static class ReservationsList {
      @XmlElement(name = "AirReservation")
      protected List<OTAResRetrieveRS.ReservationsList.AirReservation> airReservation;
      @XmlElement(name = "HotelReservation")
      protected List<HotelReservationType> hotelReservation;
      @XmlElement(name = "PackageReservation")
      protected List<OTAResRetrieveRS.ReservationsList.PackageReservation> packageReservation;
      @XmlElement(name = "GolfReservation")
      protected List<OTAResRetrieveRS.ReservationsList.GolfReservation> golfReservation;
      @XmlElement(name = "VehicleReservation")
      protected List<OTAResRetrieveRS.ReservationsList.VehicleReservation> vehicleReservation;
      @XmlElement(name = "GlobalReservation")
      protected List<OTAResRetrieveRS.ReservationsList.GlobalReservation> globalReservation;
      @XmlElement(name = "CruiseReservation")
      protected List<CruiseReservationType> cruiseReservation;
      @XmlElement(name = "RailReservation")
      protected List<RailReservationSummaryType> railReservation;

      public List<OTAResRetrieveRS.ReservationsList.AirReservation> getAirReservation() {
         if (this.airReservation == null) {
            this.airReservation = new ArrayList<>();
         }

         return this.airReservation;
      }

      public List<HotelReservationType> getHotelReservation() {
         if (this.hotelReservation == null) {
            this.hotelReservation = new ArrayList<>();
         }

         return this.hotelReservation;
      }

      public List<OTAResRetrieveRS.ReservationsList.PackageReservation> getPackageReservation() {
         if (this.packageReservation == null) {
            this.packageReservation = new ArrayList<>();
         }

         return this.packageReservation;
      }

      public List<OTAResRetrieveRS.ReservationsList.GolfReservation> getGolfReservation() {
         if (this.golfReservation == null) {
            this.golfReservation = new ArrayList<>();
         }

         return this.golfReservation;
      }

      public List<OTAResRetrieveRS.ReservationsList.VehicleReservation> getVehicleReservation() {
         if (this.vehicleReservation == null) {
            this.vehicleReservation = new ArrayList<>();
         }

         return this.vehicleReservation;
      }

      public List<OTAResRetrieveRS.ReservationsList.GlobalReservation> getGlobalReservation() {
         if (this.globalReservation == null) {
            this.globalReservation = new ArrayList<>();
         }

         return this.globalReservation;
      }

      public List<CruiseReservationType> getCruiseReservation() {
         if (this.cruiseReservation == null) {
            this.cruiseReservation = new ArrayList<>();
         }

         return this.cruiseReservation;
      }

      public List<RailReservationSummaryType> getRailReservation() {
         if (this.railReservation == null) {
            this.railReservation = new ArrayList<>();
         }

         return this.railReservation;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = {"flightSegment", "travelerName", "tpaExtensions"})
      public static class AirReservation {
         @XmlElement(name = "FlightSegment")
         protected FlightSegmentType flightSegment;
         @XmlElement(name = "TravelerName")
         protected List<PersonNameType> travelerName;
         @XmlElement(name = "TPA_Extensions")
         protected TPAExtensionsType tpaExtensions;
         @XmlAttribute(name = "BookingReferenceID")
         protected String bookingReferenceID;
         @XmlAttribute(name = "DateBooked")
         @XmlSchemaType(name = "dateTime")
         protected XMLGregorianCalendar dateBooked;
         @XmlAttribute(name = "ItineraryName")
         protected String itineraryName;
         @XmlAttribute(name = "Status")
         protected TransactionStatusType status;
         @XmlAttribute(name = "SupplierBookingInfoList")
         protected List<String> supplierBookingInfoList;

         public FlightSegmentType getFlightSegment() {
            return this.flightSegment;
         }

         public void setFlightSegment(FlightSegmentType value) {
            this.flightSegment = value;
         }

         public List<PersonNameType> getTravelerName() {
            if (this.travelerName == null) {
               this.travelerName = new ArrayList<>();
            }

            return this.travelerName;
         }

         public TPAExtensionsType getTPAExtensions() {
            return this.tpaExtensions;
         }

         public void setTPAExtensions(TPAExtensionsType value) {
            this.tpaExtensions = value;
         }

         public String getBookingReferenceID() {
            return this.bookingReferenceID;
         }

         public void setBookingReferenceID(String value) {
            this.bookingReferenceID = value;
         }

         public XMLGregorianCalendar getDateBooked() {
            return this.dateBooked;
         }

         public void setDateBooked(XMLGregorianCalendar value) {
            this.dateBooked = value;
         }

         public String getItineraryName() {
            return this.itineraryName;
         }

         public void setItineraryName(String value) {
            this.itineraryName = value;
         }

         public TransactionStatusType getStatus() {
            return this.status;
         }

         public void setStatus(TransactionStatusType value) {
            this.status = value;
         }

         public List<String> getSupplierBookingInfoList() {
            if (this.supplierBookingInfoList == null) {
               this.supplierBookingInfoList = new ArrayList<>();
            }

            return this.supplierBookingInfoList;
         }
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = {"uniqueID", "travelerName"})
      public static class GlobalReservation {
         @XmlElement(name = "UniqueID", required = true)
         protected UniqueIDType uniqueID;
         @XmlElement(name = "TravelerName")
         protected PersonNameType travelerName;
         @XmlAttribute(name = "ItineraryName")
         protected String itineraryName;
         @XmlAttribute(name = "Start")
         protected String start;

         public UniqueIDType getUniqueID() {
            return this.uniqueID;
         }

         public void setUniqueID(UniqueIDType value) {
            this.uniqueID = value;
         }

         public PersonNameType getTravelerName() {
            return this.travelerName;
         }

         public void setTravelerName(PersonNameType value) {
            this.travelerName = value;
         }

         public String getItineraryName() {
            return this.itineraryName;
         }

         public void setItineraryName(String value) {
            this.itineraryName = value;
         }

         public String getStart() {
            return this.start;
         }

         public void setStart(String value) {
            this.start = value;
         }
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = {"membership", "name"})
      public static class GolfReservation {
         @XmlElement(name = "Membership")
         protected OTAResRetrieveRS.ReservationsList.GolfReservation.Membership membership;
         @XmlElement(name = "Name", required = true)
         protected PersonNameType name;
         @XmlAttribute(name = "RoundID", required = true)
         @XmlSchemaType(name = "positiveInteger")
         protected BigInteger roundID;
         @XmlAttribute(name = "PlayDateTime", required = true)
         protected String playDateTime;
         @XmlAttribute(name = "PackageID")
         protected String packageID;
         @XmlAttribute(name = "RequestorResID")
         protected String requestorResID;
         @XmlAttribute(name = "ResponderResConfID", required = true)
         protected String responderResConfID;
         @XmlAttribute(name = "ID", required = true)
         protected String id;

         public OTAResRetrieveRS.ReservationsList.GolfReservation.Membership getMembership() {
            return this.membership;
         }

         public void setMembership(OTAResRetrieveRS.ReservationsList.GolfReservation.Membership value) {
            this.membership = value;
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

         public String getRequestorResID() {
            return this.requestorResID;
         }

         public void setRequestorResID(String value) {
            this.requestorResID = value;
         }

         public String getResponderResConfID() {
            return this.responderResConfID;
         }

         public void setResponderResConfID(String value) {
            this.responderResConfID = value;
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
      @XmlType(name = "", propOrder = {"uniqueID", "name", "arrivalLocation", "departureLocation"})
      public static class PackageReservation {
         @XmlElement(name = "UniqueID")
         protected UniqueIDType uniqueID;
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
         @XmlAttribute(name = "Quantity")
         @XmlSchemaType(name = "nonNegativeInteger")
         protected BigInteger quantity;
         @XmlAttribute(name = "Start")
         protected String start;
         @XmlAttribute(name = "Duration")
         protected String duration;
         @XmlAttribute(name = "End")
         protected String end;
         @XmlAttribute(name = "ReservationStatusCode")
         protected String reservationStatusCode;
         @XmlAttribute(name = "ReservationStatus")
         protected InventoryStatusType reservationStatus;

         public UniqueIDType getUniqueID() {
            return this.uniqueID;
         }

         public void setUniqueID(UniqueIDType value) {
            this.uniqueID = value;
         }

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

         public BigInteger getQuantity() {
            return this.quantity;
         }

         public void setQuantity(BigInteger value) {
            this.quantity = value;
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

         public String getReservationStatusCode() {
            return this.reservationStatusCode;
         }

         public void setReservationStatusCode(String value) {
            this.reservationStatusCode = value;
         }

         public InventoryStatusType getReservationStatus() {
            return this.reservationStatus;
         }

         public void setReservationStatus(InventoryStatusType value) {
            this.reservationStatus = value;
         }
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = "vehResSummary")
      public static class VehicleReservation {
         @XmlElement(name = "VehResSummary", required = true)
         protected List<VehicleReservationSummaryType> vehResSummary;

         public List<VehicleReservationSummaryType> getVehResSummary() {
            if (this.vehResSummary == null) {
               this.vehResSummary = new ArrayList<>();
            }

            return this.vehResSummary;
         }
      }
   }
}
