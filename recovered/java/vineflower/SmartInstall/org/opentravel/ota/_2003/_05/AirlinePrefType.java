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
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "AirlinePrefType",
   propOrder = {
         "loyaltyPref",
         "vendorPref",
         "paymentFormPref",
         "airportOriginPref",
         "airportDestinationPref",
         "airportRoutePref",
         "fareRestrictPref",
         "farePref",
         "tourCodePref",
         "flightTypePref",
         "equipPref",
         "cabinPref",
         "seatPref",
         "ticketDistribPref",
         "mealPref",
         "specRequestPref",
         "ssrPref",
         "tpaExtensions",
         "mediaEntertainPref",
         "petInfoPref",
         "accountInformation",
         "osiPref",
         "keywordPref"
   }
)
public class AirlinePrefType {
   @XmlElement(name = "LoyaltyPref")
   protected List<LoyaltyPrefType> loyaltyPref;
   @XmlElement(name = "VendorPref")
   protected List<AirlinePrefType.VendorPref> vendorPref;
   @XmlElement(name = "PaymentFormPref")
   protected List<PaymentFormPrefType> paymentFormPref;
   @XmlElement(name = "AirportOriginPref")
   protected List<AirportPrefType> airportOriginPref;
   @XmlElement(name = "AirportDestinationPref")
   protected AirportPrefType airportDestinationPref;
   @XmlElement(name = "AirportRoutePref")
   protected List<AirportPrefType> airportRoutePref;
   @XmlElement(name = "FareRestrictPref")
   protected List<AirlinePrefType.FareRestrictPref> fareRestrictPref;
   @XmlElement(name = "FarePref")
   protected List<AirlinePrefType.FarePref> farePref;
   @XmlElement(name = "TourCodePref")
   protected List<AirlinePrefType.TourCodePref> tourCodePref;
   @XmlElement(name = "FlightTypePref")
   protected List<AirlinePrefType.FlightTypePref> flightTypePref;
   @XmlElement(name = "EquipPref")
   protected List<EquipmentTypePref> equipPref;
   @XmlElement(name = "CabinPref")
   protected List<AirlinePrefType.CabinPref> cabinPref;
   @XmlElement(name = "SeatPref")
   protected List<AirlinePrefType.SeatPref> seatPref;
   @XmlElement(name = "TicketDistribPref")
   protected List<TicketDistribPrefType> ticketDistribPref;
   @XmlElement(name = "MealPref")
   protected List<MealPrefType> mealPref;
   @XmlElement(name = "SpecRequestPref")
   protected List<SpecRequestPrefType> specRequestPref;
   @XmlElement(name = "SSR_Pref")
   protected List<AirlinePrefType.SSRPref> ssrPref;
   @XmlElement(name = "TPA_Extensions")
   protected TPAExtensionsType tpaExtensions;
   @XmlElement(name = "MediaEntertainPref")
   protected List<MediaEntertainPrefType> mediaEntertainPref;
   @XmlElement(name = "PetInfoPref")
   protected List<PetInfoPrefType> petInfoPref;
   @XmlElement(name = "AccountInformation")
   protected AirlinePrefType.AccountInformation accountInformation;
   @XmlElement(name = "OSI_Pref")
   protected List<OtherServiceInfoType> osiPref;
   @XmlElement(name = "KeywordPref")
   protected List<AirlinePrefType.KeywordPref> keywordPref;
   @XmlAttribute(name = "PassengerTypeCode")
   protected String passengerTypeCode;
   @XmlAttribute(name = "AirTicketType")
   protected TicketType airTicketType;
   @XmlAttribute(name = "SmokingAllowed")
   protected Boolean smokingAllowed;
   @XmlAttribute(name = "PreferLevel")
   protected PreferLevelType preferLevel;
   @XmlAttribute(name = "ShareSynchInd")
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   protected String shareSynchInd;
   @XmlAttribute(name = "ShareMarketInd")
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   protected String shareMarketInd;

   public List<LoyaltyPrefType> getLoyaltyPref() {
      if (this.loyaltyPref == null) {
         this.loyaltyPref = new ArrayList<>();
      }

      return this.loyaltyPref;
   }

   public List<AirlinePrefType.VendorPref> getVendorPref() {
      if (this.vendorPref == null) {
         this.vendorPref = new ArrayList<>();
      }

      return this.vendorPref;
   }

   public List<PaymentFormPrefType> getPaymentFormPref() {
      if (this.paymentFormPref == null) {
         this.paymentFormPref = new ArrayList<>();
      }

      return this.paymentFormPref;
   }

   public List<AirportPrefType> getAirportOriginPref() {
      if (this.airportOriginPref == null) {
         this.airportOriginPref = new ArrayList<>();
      }

      return this.airportOriginPref;
   }

   public AirportPrefType getAirportDestinationPref() {
      return this.airportDestinationPref;
   }

   public void setAirportDestinationPref(AirportPrefType value) {
      this.airportDestinationPref = value;
   }

   public List<AirportPrefType> getAirportRoutePref() {
      if (this.airportRoutePref == null) {
         this.airportRoutePref = new ArrayList<>();
      }

      return this.airportRoutePref;
   }

   public List<AirlinePrefType.FareRestrictPref> getFareRestrictPref() {
      if (this.fareRestrictPref == null) {
         this.fareRestrictPref = new ArrayList<>();
      }

      return this.fareRestrictPref;
   }

   public List<AirlinePrefType.FarePref> getFarePref() {
      if (this.farePref == null) {
         this.farePref = new ArrayList<>();
      }

      return this.farePref;
   }

   public List<AirlinePrefType.TourCodePref> getTourCodePref() {
      if (this.tourCodePref == null) {
         this.tourCodePref = new ArrayList<>();
      }

      return this.tourCodePref;
   }

   public List<AirlinePrefType.FlightTypePref> getFlightTypePref() {
      if (this.flightTypePref == null) {
         this.flightTypePref = new ArrayList<>();
      }

      return this.flightTypePref;
   }

   public List<EquipmentTypePref> getEquipPref() {
      if (this.equipPref == null) {
         this.equipPref = new ArrayList<>();
      }

      return this.equipPref;
   }

   public List<AirlinePrefType.CabinPref> getCabinPref() {
      if (this.cabinPref == null) {
         this.cabinPref = new ArrayList<>();
      }

      return this.cabinPref;
   }

   public List<AirlinePrefType.SeatPref> getSeatPref() {
      if (this.seatPref == null) {
         this.seatPref = new ArrayList<>();
      }

      return this.seatPref;
   }

   public List<TicketDistribPrefType> getTicketDistribPref() {
      if (this.ticketDistribPref == null) {
         this.ticketDistribPref = new ArrayList<>();
      }

      return this.ticketDistribPref;
   }

   public List<MealPrefType> getMealPref() {
      if (this.mealPref == null) {
         this.mealPref = new ArrayList<>();
      }

      return this.mealPref;
   }

   public List<SpecRequestPrefType> getSpecRequestPref() {
      if (this.specRequestPref == null) {
         this.specRequestPref = new ArrayList<>();
      }

      return this.specRequestPref;
   }

   public List<AirlinePrefType.SSRPref> getSSRPref() {
      if (this.ssrPref == null) {
         this.ssrPref = new ArrayList<>();
      }

      return this.ssrPref;
   }

   public TPAExtensionsType getTPAExtensions() {
      return this.tpaExtensions;
   }

   public void setTPAExtensions(TPAExtensionsType value) {
      this.tpaExtensions = value;
   }

   public List<MediaEntertainPrefType> getMediaEntertainPref() {
      if (this.mediaEntertainPref == null) {
         this.mediaEntertainPref = new ArrayList<>();
      }

      return this.mediaEntertainPref;
   }

   public List<PetInfoPrefType> getPetInfoPref() {
      if (this.petInfoPref == null) {
         this.petInfoPref = new ArrayList<>();
      }

      return this.petInfoPref;
   }

   public AirlinePrefType.AccountInformation getAccountInformation() {
      return this.accountInformation;
   }

   public void setAccountInformation(AirlinePrefType.AccountInformation value) {
      this.accountInformation = value;
   }

   public List<OtherServiceInfoType> getOSIPref() {
      if (this.osiPref == null) {
         this.osiPref = new ArrayList<>();
      }

      return this.osiPref;
   }

   public List<AirlinePrefType.KeywordPref> getKeywordPref() {
      if (this.keywordPref == null) {
         this.keywordPref = new ArrayList<>();
      }

      return this.keywordPref;
   }

   public String getPassengerTypeCode() {
      return this.passengerTypeCode;
   }

   public void setPassengerTypeCode(String value) {
      this.passengerTypeCode = value;
   }

   public TicketType getAirTicketType() {
      return this.airTicketType;
   }

   public void setAirTicketType(TicketType value) {
      this.airTicketType = value;
   }

   public Boolean isSmokingAllowed() {
      return this.smokingAllowed;
   }

   public void setSmokingAllowed(Boolean value) {
      this.smokingAllowed = value;
   }

   public PreferLevelType getPreferLevel() {
      return this.preferLevel;
   }

   public void setPreferLevel(PreferLevelType value) {
      this.preferLevel = value;
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

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "taxRegistrationDetails")
   public static class AccountInformation {
      @XmlElement(name = "TaxRegistrationDetails")
      protected AirlinePrefType.AccountInformation.TaxRegistrationDetails taxRegistrationDetails;
      @XmlAttribute(name = "Number")
      protected String number;
      @XmlAttribute(name = "CostCenter")
      protected String costCenter;
      @XmlAttribute(name = "CompanyNumber")
      protected String companyNumber;
      @XmlAttribute(name = "ClientReference")
      protected String clientReference;

      public AirlinePrefType.AccountInformation.TaxRegistrationDetails getTaxRegistrationDetails() {
         return this.taxRegistrationDetails;
      }

      public void setTaxRegistrationDetails(AirlinePrefType.AccountInformation.TaxRegistrationDetails value) {
         this.taxRegistrationDetails = value;
      }

      public String getNumber() {
         return this.number;
      }

      public void setNumber(String value) {
         this.number = value;
      }

      public String getCostCenter() {
         return this.costCenter;
      }

      public void setCostCenter(String value) {
         this.costCenter = value;
      }

      public String getCompanyNumber() {
         return this.companyNumber;
      }

      public void setCompanyNumber(String value) {
         this.companyNumber = value;
      }

      public String getClientReference() {
         return this.clientReference;
      }

      public void setClientReference(String value) {
         this.clientReference = value;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class TaxRegistrationDetails {
         @XmlAttribute(name = "TaxID")
         protected String taxID;
         @XmlAttribute(name = "RecipientName")
         protected String recipientName;
         @XmlAttribute(name = "RecipientAddress")
         protected String recipientAddress;

         public String getTaxID() {
            return this.taxID;
         }

         public void setTaxID(String value) {
            this.taxID = value;
         }

         public String getRecipientName() {
            return this.recipientName;
         }

         public void setRecipientName(String value) {
            this.recipientName = value;
         }

         public String getRecipientAddress() {
            return this.recipientAddress;
         }

         public void setRecipientAddress(String value) {
            this.recipientAddress = value;
         }
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class CabinPref {
      @XmlAttribute(name = "Cabin")
      protected CabinType cabin;
      @XmlAttribute(name = "PreferLevel")
      protected PreferLevelType preferLevel;

      public CabinType getCabin() {
         return this.cabin;
      }

      public void setCabin(CabinType value) {
         this.cabin = value;
      }

      public PreferLevelType getPreferLevel() {
         return this.preferLevel;
      }

      public void setPreferLevel(PreferLevelType value) {
         this.preferLevel = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class FarePref {
      @XmlAttribute(name = "Code")
      protected String code;
      @XmlAttribute(name = "Description")
      protected String description;
      @XmlAttribute(name = "AirlineVendorPrefRPH")
      protected List<String> airlineVendorPrefRPH;
      @XmlAttribute(name = "RateCategoryCode")
      protected String rateCategoryCode;
      @XmlAttribute(name = "TransferAction")
      protected TransferActionType transferAction;

      public String getCode() {
         return this.code;
      }

      public void setCode(String value) {
         this.code = value;
      }

      public String getDescription() {
         return this.description;
      }

      public void setDescription(String value) {
         this.description = value;
      }

      public List<String> getAirlineVendorPrefRPH() {
         if (this.airlineVendorPrefRPH == null) {
            this.airlineVendorPrefRPH = new ArrayList<>();
         }

         return this.airlineVendorPrefRPH;
      }

      public String getRateCategoryCode() {
         return this.rateCategoryCode;
      }

      public void setRateCategoryCode(String value) {
         this.rateCategoryCode = value;
      }

      public TransferActionType getTransferAction() {
         return this.transferAction;
      }

      public void setTransferAction(TransferActionType value) {
         this.transferAction = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class FareRestrictPref {
      @XmlAttribute(name = "FareRestriction")
      protected String fareRestriction;
      @XmlAttribute(name = "Date")
      protected String date;
      @XmlAttribute(name = "PreferLevel")
      protected PreferLevelType preferLevel;

      public String getFareRestriction() {
         return this.fareRestriction;
      }

      public void setFareRestriction(String value) {
         this.fareRestriction = value;
      }

      public String getDate() {
         return this.date;
      }

      public void setDate(String value) {
         this.date = value;
      }

      public PreferLevelType getPreferLevel() {
         return this.preferLevel;
      }

      public void setPreferLevel(PreferLevelType value) {
         this.preferLevel = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class FlightTypePref {
      @XmlAttribute(name = "FlightType")
      protected FlightTypeType flightType;
      @XmlAttribute(name = "MaxConnections")
      @XmlSchemaType(name = "nonNegativeInteger")
      protected BigInteger maxConnections;
      @XmlAttribute(name = "NonScheduledFltInfo")
      @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
      protected String nonScheduledFltInfo;
      @XmlAttribute(name = "BackhaulIndicator")
      protected Boolean backhaulIndicator;
      @XmlAttribute(name = "GroundTransportIndicator")
      protected Boolean groundTransportIndicator;
      @XmlAttribute(name = "DirectAndNonStopOnlyInd")
      protected Boolean directAndNonStopOnlyInd;
      @XmlAttribute(name = "NonStopsOnlyInd")
      protected Boolean nonStopsOnlyInd;
      @XmlAttribute(name = "OnlineConnectionsOnlyInd")
      protected Boolean onlineConnectionsOnlyInd;
      @XmlAttribute(name = "RoutingType")
      @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
      protected String routingType;
      @XmlAttribute(name = "ExcludeTrainInd")
      protected Boolean excludeTrainInd;
      @XmlAttribute(name = "PreferLevel")
      protected PreferLevelType preferLevel;

      public FlightTypeType getFlightType() {
         return this.flightType;
      }

      public void setFlightType(FlightTypeType value) {
         this.flightType = value;
      }

      public BigInteger getMaxConnections() {
         return this.maxConnections;
      }

      public void setMaxConnections(BigInteger value) {
         this.maxConnections = value;
      }

      public String getNonScheduledFltInfo() {
         return this.nonScheduledFltInfo;
      }

      public void setNonScheduledFltInfo(String value) {
         this.nonScheduledFltInfo = value;
      }

      public Boolean isBackhaulIndicator() {
         return this.backhaulIndicator;
      }

      public void setBackhaulIndicator(Boolean value) {
         this.backhaulIndicator = value;
      }

      public Boolean isGroundTransportIndicator() {
         return this.groundTransportIndicator;
      }

      public void setGroundTransportIndicator(Boolean value) {
         this.groundTransportIndicator = value;
      }

      public Boolean isDirectAndNonStopOnlyInd() {
         return this.directAndNonStopOnlyInd;
      }

      public void setDirectAndNonStopOnlyInd(Boolean value) {
         this.directAndNonStopOnlyInd = value;
      }

      public Boolean isNonStopsOnlyInd() {
         return this.nonStopsOnlyInd;
      }

      public void setNonStopsOnlyInd(Boolean value) {
         this.nonStopsOnlyInd = value;
      }

      public Boolean isOnlineConnectionsOnlyInd() {
         return this.onlineConnectionsOnlyInd;
      }

      public void setOnlineConnectionsOnlyInd(Boolean value) {
         this.onlineConnectionsOnlyInd = value;
      }

      public String getRoutingType() {
         return this.routingType;
      }

      public void setRoutingType(String value) {
         this.routingType = value;
      }

      public Boolean isExcludeTrainInd() {
         return this.excludeTrainInd;
      }

      public void setExcludeTrainInd(Boolean value) {
         this.excludeTrainInd = value;
      }

      public PreferLevelType getPreferLevel() {
         return this.preferLevel;
      }

      public void setPreferLevel(PreferLevelType value) {
         this.preferLevel = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class KeywordPref {
      @XmlAttribute(name = "VendorCode")
      protected String vendorCode;
      @XmlAttribute(name = "Description")
      protected String description;
      @XmlAttribute(name = "Keyword")
      protected String keyword;
      @XmlAttribute(name = "StatusCode")
      protected String statusCode;
      @XmlAttribute(name = "NumberInParty")
      @XmlSchemaType(name = "positiveInteger")
      protected BigInteger numberInParty;
      @XmlAttribute(name = "AirlineVendorRPH")
      protected List<String> airlineVendorRPH;
      @XmlAttribute(name = "TransferAction")
      protected TransferActionType transferAction;

      public String getVendorCode() {
         return this.vendorCode;
      }

      public void setVendorCode(String value) {
         this.vendorCode = value;
      }

      public String getDescription() {
         return this.description;
      }

      public void setDescription(String value) {
         this.description = value;
      }

      public String getKeyword() {
         return this.keyword;
      }

      public void setKeyword(String value) {
         this.keyword = value;
      }

      public String getStatusCode() {
         return this.statusCode;
      }

      public void setStatusCode(String value) {
         this.statusCode = value;
      }

      public BigInteger getNumberInParty() {
         return this.numberInParty;
      }

      public void setNumberInParty(BigInteger value) {
         this.numberInParty = value;
      }

      public List<String> getAirlineVendorRPH() {
         if (this.airlineVendorRPH == null) {
            this.airlineVendorRPH = new ArrayList<>();
         }

         return this.airlineVendorRPH;
      }

      public TransferActionType getTransferAction() {
         return this.transferAction;
      }

      public void setTransferAction(TransferActionType value) {
         this.transferAction = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class SSRPref {
      @XmlAttribute(name = "VendorCode")
      protected String vendorCode;
      @XmlAttribute(name = "NumberInParty")
      @XmlSchemaType(name = "positiveInteger")
      protected BigInteger numberInParty;
      @XmlAttribute(name = "DefaultStatusCode")
      protected String defaultStatusCode;
      @XmlAttribute(name = "Remark")
      protected String remark;
      @XmlAttribute(name = "LookupKey")
      protected String lookupKey;
      @XmlAttribute(name = "AirlineVendorPrefRPH")
      protected List<String> airlineVendorPrefRPH;
      @XmlAttribute(name = "TransferActionType")
      protected TransferActionType transferActionType;
      @XmlAttribute(name = "SSR_Code")
      protected String ssrCode;
      @XmlAttribute(name = "PreferLevel")
      protected PreferLevelType preferLevel;

      public String getVendorCode() {
         return this.vendorCode;
      }

      public void setVendorCode(String value) {
         this.vendorCode = value;
      }

      public BigInteger getNumberInParty() {
         return this.numberInParty;
      }

      public void setNumberInParty(BigInteger value) {
         this.numberInParty = value;
      }

      public String getDefaultStatusCode() {
         return this.defaultStatusCode;
      }

      public void setDefaultStatusCode(String value) {
         this.defaultStatusCode = value;
      }

      public String getRemark() {
         return this.remark;
      }

      public void setRemark(String value) {
         this.remark = value;
      }

      public String getLookupKey() {
         return this.lookupKey;
      }

      public void setLookupKey(String value) {
         this.lookupKey = value;
      }

      public List<String> getAirlineVendorPrefRPH() {
         if (this.airlineVendorPrefRPH == null) {
            this.airlineVendorPrefRPH = new ArrayList<>();
         }

         return this.airlineVendorPrefRPH;
      }

      public TransferActionType getTransferActionType() {
         return this.transferActionType;
      }

      public void setTransferActionType(TransferActionType value) {
         this.transferActionType = value;
      }

      public String getSSRCode() {
         return this.ssrCode;
      }

      public void setSSRCode(String value) {
         this.ssrCode = value;
      }

      public PreferLevelType getPreferLevel() {
         return this.preferLevel;
      }

      public void setPreferLevel(PreferLevelType value) {
         this.preferLevel = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class SeatPref {
      @XmlAttribute(name = "FlightDistanceQualifier")
      @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
      protected String flightDistanceQualifier;
      @XmlAttribute(name = "InternationalIndicator")
      protected Boolean internationalIndicator;
      @XmlAttribute(name = "AirlineVendorPrefRPH")
      protected List<String> airlineVendorPrefRPH;
      @XmlAttribute(name = "PassengerTypeCode")
      protected String passengerTypeCode;
      @XmlAttribute(name = "TransferAction")
      protected TransferActionType transferAction;
      @XmlAttribute(name = "SeatNumber")
      protected String seatNumber;
      @XmlAttribute(name = "SeatPreference")
      protected List<String> seatPreference;
      @XmlAttribute(name = "DeckLevel")
      protected String deckLevel;
      @XmlAttribute(name = "RowNumber")
      protected Integer rowNumber;
      @XmlAttribute(name = "SeatInRow")
      protected String seatInRow;
      @XmlAttribute(name = "SmokingAllowed")
      protected Boolean smokingAllowed;
      @XmlAttribute(name = "PreferLevel")
      protected PreferLevelType preferLevel;

      public String getFlightDistanceQualifier() {
         return this.flightDistanceQualifier;
      }

      public void setFlightDistanceQualifier(String value) {
         this.flightDistanceQualifier = value;
      }

      public Boolean isInternationalIndicator() {
         return this.internationalIndicator;
      }

      public void setInternationalIndicator(Boolean value) {
         this.internationalIndicator = value;
      }

      public List<String> getAirlineVendorPrefRPH() {
         if (this.airlineVendorPrefRPH == null) {
            this.airlineVendorPrefRPH = new ArrayList<>();
         }

         return this.airlineVendorPrefRPH;
      }

      public String getPassengerTypeCode() {
         return this.passengerTypeCode;
      }

      public void setPassengerTypeCode(String value) {
         this.passengerTypeCode = value;
      }

      public TransferActionType getTransferAction() {
         return this.transferAction;
      }

      public void setTransferAction(TransferActionType value) {
         this.transferAction = value;
      }

      public String getSeatNumber() {
         return this.seatNumber;
      }

      public void setSeatNumber(String value) {
         this.seatNumber = value;
      }

      public List<String> getSeatPreference() {
         if (this.seatPreference == null) {
            this.seatPreference = new ArrayList<>();
         }

         return this.seatPreference;
      }

      public String getDeckLevel() {
         return this.deckLevel;
      }

      public void setDeckLevel(String value) {
         this.deckLevel = value;
      }

      public Integer getRowNumber() {
         return this.rowNumber;
      }

      public void setRowNumber(Integer value) {
         this.rowNumber = value;
      }

      public String getSeatInRow() {
         return this.seatInRow;
      }

      public void setSeatInRow(String value) {
         this.seatInRow = value;
      }

      public Boolean isSmokingAllowed() {
         return this.smokingAllowed;
      }

      public void setSmokingAllowed(Boolean value) {
         this.smokingAllowed = value;
      }

      public PreferLevelType getPreferLevel() {
         return this.preferLevel;
      }

      public void setPreferLevel(PreferLevelType value) {
         this.preferLevel = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = {"tourCodeInfo", "staffTourCodeInfo"})
   public static class TourCodePref {
      @XmlElement(name = "TourCodeInfo")
      protected AirlinePrefType.TourCodePref.TourCodeInfo tourCodeInfo;
      @XmlElement(name = "StaffTourCodeInfo")
      protected AirlinePrefType.TourCodePref.StaffTourCodeInfo staffTourCodeInfo;
      @XmlAttribute(name = "PassengerTypeCode")
      protected String passengerTypeCode;
      @XmlAttribute(name = "AirlineVendorPrefRPH")
      protected List<String> airlineVendorPrefRPH;
      @XmlAttribute(name = "TransferAction")
      protected TransferActionType transferAction;

      public AirlinePrefType.TourCodePref.TourCodeInfo getTourCodeInfo() {
         return this.tourCodeInfo;
      }

      public void setTourCodeInfo(AirlinePrefType.TourCodePref.TourCodeInfo value) {
         this.tourCodeInfo = value;
      }

      public AirlinePrefType.TourCodePref.StaffTourCodeInfo getStaffTourCodeInfo() {
         return this.staffTourCodeInfo;
      }

      public void setStaffTourCodeInfo(AirlinePrefType.TourCodePref.StaffTourCodeInfo value) {
         this.staffTourCodeInfo = value;
      }

      public String getPassengerTypeCode() {
         return this.passengerTypeCode;
      }

      public void setPassengerTypeCode(String value) {
         this.passengerTypeCode = value;
      }

      public List<String> getAirlineVendorPrefRPH() {
         if (this.airlineVendorPrefRPH == null) {
            this.airlineVendorPrefRPH = new ArrayList<>();
         }

         return this.airlineVendorPrefRPH;
      }

      public TransferActionType getTransferAction() {
         return this.transferAction;
      }

      public void setTransferAction(TransferActionType value) {
         this.transferAction = value;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class StaffTourCodeInfo {
         @XmlAttribute(name = "StaffType")
         @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
         protected String staffType;
         @XmlAttribute(name = "EmployeeID")
         protected String employeeID;
         @XmlAttribute(name = "VendorCode")
         protected String vendorCode;
         @XmlAttribute(name = "Description")
         protected String description;

         public String getStaffType() {
            return this.staffType;
         }

         public void setStaffType(String value) {
            this.staffType = value;
         }

         public String getEmployeeID() {
            return this.employeeID;
         }

         public void setEmployeeID(String value) {
            this.employeeID = value;
         }

         public String getVendorCode() {
            return this.vendorCode;
         }

         public void setVendorCode(String value) {
            this.vendorCode = value;
         }

         public String getDescription() {
            return this.description;
         }

         public void setDescription(String value) {
            this.description = value;
         }
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class TourCodeInfo {
         @XmlAttribute(name = "TourTypeCode")
         protected String tourTypeCode;
         @XmlAttribute(name = "YearNum")
         protected Integer yearNum;
         @XmlAttribute(name = "PartyID")
         protected String partyID;
         @XmlAttribute(name = "PromotionCode")
         protected String promotionCode;
         @XmlAttribute(name = "PromotionVendorCode")
         protected List<String> promotionVendorCode;

         public String getTourTypeCode() {
            return this.tourTypeCode;
         }

         public void setTourTypeCode(String value) {
            this.tourTypeCode = value;
         }

         public Integer getYearNum() {
            return this.yearNum;
         }

         public void setYearNum(Integer value) {
            this.yearNum = value;
         }

         public String getPartyID() {
            return this.partyID;
         }

         public void setPartyID(String value) {
            this.partyID = value;
         }

         public String getPromotionCode() {
            return this.promotionCode;
         }

         public void setPromotionCode(String value) {
            this.promotionCode = value;
         }

         public List<String> getPromotionVendorCode() {
            if (this.promotionVendorCode == null) {
               this.promotionVendorCode = new ArrayList<>();
            }

            return this.promotionVendorCode;
         }
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class VendorPref extends CompanyNamePrefType {
      @XmlAttribute(name = "RPH")
      protected String rph;

      public String getRPH() {
         return this.rph;
      }

      public void setRPH(String value) {
         this.rph = value;
      }
   }
}
