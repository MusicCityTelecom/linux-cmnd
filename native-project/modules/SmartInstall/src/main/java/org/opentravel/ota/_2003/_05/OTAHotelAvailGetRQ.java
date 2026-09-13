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
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"pos", "hotelAvailRequests"})
@XmlRootElement(name = "OTA_HotelAvailGetRQ")
public class OTAHotelAvailGetRQ {
   @XmlElement(name = "POS")
   protected POSType pos;
   @XmlElement(name = "HotelAvailRequests", required = true)
   protected OTAHotelAvailGetRQ.HotelAvailRequests hotelAvailRequests;
   @XmlAttribute(name = "SummaryOnly")
   protected Boolean summaryOnly;
   @XmlAttribute(name = "SortOrder")
   protected String sortOrder;
   @XmlAttribute(name = "RequestedCurrency")
   protected String requestedCurrency;
   @XmlAttribute(name = "SearchCacheLevel")
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   protected String searchCacheLevel;
   @XmlAttribute(name = "MaximumWaitTime")
   protected BigDecimal maximumWaitTime;
   @XmlAttribute(name = "MoreDataEchoToken")
   protected String moreDataEchoToken;
   @XmlAttribute(name = "InfoSource")
   protected String infoSource;
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

   public POSType getPOS() {
      return this.pos;
   }

   public void setPOS(POSType value) {
      this.pos = value;
   }

   public OTAHotelAvailGetRQ.HotelAvailRequests getHotelAvailRequests() {
      return this.hotelAvailRequests;
   }

   public void setHotelAvailRequests(OTAHotelAvailGetRQ.HotelAvailRequests value) {
      this.hotelAvailRequests = value;
   }

   public Boolean isSummaryOnly() {
      return this.summaryOnly;
   }

   public void setSummaryOnly(Boolean value) {
      this.summaryOnly = value;
   }

   public String getSortOrder() {
      return this.sortOrder;
   }

   public void setSortOrder(String value) {
      this.sortOrder = value;
   }

   public String getRequestedCurrency() {
      return this.requestedCurrency;
   }

   public void setRequestedCurrency(String value) {
      this.requestedCurrency = value;
   }

   public String getSearchCacheLevel() {
      return this.searchCacheLevel;
   }

   public void setSearchCacheLevel(String value) {
      this.searchCacheLevel = value;
   }

   public BigDecimal getMaximumWaitTime() {
      return this.maximumWaitTime;
   }

   public void setMaximumWaitTime(BigDecimal value) {
      this.maximumWaitTime = value;
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
   @XmlType(name = "", propOrder = "hotelAvailRequest")
   public static class HotelAvailRequests {
      @XmlElement(name = "HotelAvailRequest", required = true)
      protected List<OTAHotelAvailGetRQ.HotelAvailRequests.HotelAvailRequest> hotelAvailRequest;

      public List<OTAHotelAvailGetRQ.HotelAvailRequests.HotelAvailRequest> getHotelAvailRequest() {
         if (this.hotelAvailRequest == null) {
            this.hotelAvailRequest = new ArrayList<>();
         }

         return this.hotelAvailRequest;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(
         name = "",
         propOrder = {
               "dateRange",
               "destinationSystemsCode",
               "ratePlanCandidates",
               "roomTypeCandidates",
               "offers",
               "restrictionStatusCandidates",
               "lengthsOfStayCandidates",
               "bestAvailableRateCandidate",
               "hurdleRateCandidate",
               "deltaCandidate",
               "hotelRef",
               "rebatePrograms",
               "tpaExtensions"
         }
      )
      public static class HotelAvailRequest {
         @XmlElement(name = "DateRange")
         protected OTAHotelAvailGetRQ.HotelAvailRequests.HotelAvailRequest.DateRange dateRange;
         @XmlElement(name = "DestinationSystemsCode")
         protected DestinationSystemCodesType destinationSystemsCode;
         @XmlElement(name = "RatePlanCandidates")
         protected RatePlanCandidatesType ratePlanCandidates;
         @XmlElement(name = "RoomTypeCandidates")
         protected OTAHotelAvailGetRQ.HotelAvailRequests.HotelAvailRequest.RoomTypeCandidates roomTypeCandidates;
         @XmlElement(name = "Offers")
         protected OTAHotelAvailGetRQ.HotelAvailRequests.HotelAvailRequest.Offers offers;
         @XmlElement(name = "RestrictionStatusCandidates")
         protected OTAHotelAvailGetRQ.HotelAvailRequests.HotelAvailRequest.RestrictionStatusCandidates restrictionStatusCandidates;
         @XmlElement(name = "LengthsOfStayCandidates")
         protected OTAHotelAvailGetRQ.HotelAvailRequests.HotelAvailRequest.LengthsOfStayCandidates lengthsOfStayCandidates;
         @XmlElement(name = "BestAvailableRateCandidate")
         protected OTAHotelAvailGetRQ.HotelAvailRequests.HotelAvailRequest.BestAvailableRateCandidate bestAvailableRateCandidate;
         @XmlElement(name = "HurdleRateCandidate")
         protected OTAHotelAvailGetRQ.HotelAvailRequests.HotelAvailRequest.HurdleRateCandidate hurdleRateCandidate;
         @XmlElement(name = "DeltaCandidate")
         protected OTAHotelAvailGetRQ.HotelAvailRequests.HotelAvailRequest.DeltaCandidate deltaCandidate;
         @XmlElement(name = "HotelRef")
         protected OTAHotelAvailGetRQ.HotelAvailRequests.HotelAvailRequest.HotelRef hotelRef;
         @XmlElement(name = "RebatePrograms")
         protected OTAHotelAvailGetRQ.HotelAvailRequests.HotelAvailRequest.RebatePrograms rebatePrograms;
         @XmlElement(name = "TPA_Extensions")
         protected TPAExtensionsType tpaExtensions;
         @XmlAttribute(name = "SendBookingLimit")
         protected Boolean sendBookingLimit;
         @XmlAttribute(name = "BookingLimitMessageType")
         protected String bookingLimitMessageType;

         public OTAHotelAvailGetRQ.HotelAvailRequests.HotelAvailRequest.DateRange getDateRange() {
            return this.dateRange;
         }

         public void setDateRange(OTAHotelAvailGetRQ.HotelAvailRequests.HotelAvailRequest.DateRange value) {
            this.dateRange = value;
         }

         public DestinationSystemCodesType getDestinationSystemsCode() {
            return this.destinationSystemsCode;
         }

         public void setDestinationSystemsCode(DestinationSystemCodesType value) {
            this.destinationSystemsCode = value;
         }

         public RatePlanCandidatesType getRatePlanCandidates() {
            return this.ratePlanCandidates;
         }

         public void setRatePlanCandidates(RatePlanCandidatesType value) {
            this.ratePlanCandidates = value;
         }

         public OTAHotelAvailGetRQ.HotelAvailRequests.HotelAvailRequest.RoomTypeCandidates getRoomTypeCandidates() {
            return this.roomTypeCandidates;
         }

         public void setRoomTypeCandidates(OTAHotelAvailGetRQ.HotelAvailRequests.HotelAvailRequest.RoomTypeCandidates value) {
            this.roomTypeCandidates = value;
         }

         public OTAHotelAvailGetRQ.HotelAvailRequests.HotelAvailRequest.Offers getOffers() {
            return this.offers;
         }

         public void setOffers(OTAHotelAvailGetRQ.HotelAvailRequests.HotelAvailRequest.Offers value) {
            this.offers = value;
         }

         public OTAHotelAvailGetRQ.HotelAvailRequests.HotelAvailRequest.RestrictionStatusCandidates getRestrictionStatusCandidates() {
            return this.restrictionStatusCandidates;
         }

         public void setRestrictionStatusCandidates(OTAHotelAvailGetRQ.HotelAvailRequests.HotelAvailRequest.RestrictionStatusCandidates value) {
            this.restrictionStatusCandidates = value;
         }

         public OTAHotelAvailGetRQ.HotelAvailRequests.HotelAvailRequest.LengthsOfStayCandidates getLengthsOfStayCandidates() {
            return this.lengthsOfStayCandidates;
         }

         public void setLengthsOfStayCandidates(OTAHotelAvailGetRQ.HotelAvailRequests.HotelAvailRequest.LengthsOfStayCandidates value) {
            this.lengthsOfStayCandidates = value;
         }

         public OTAHotelAvailGetRQ.HotelAvailRequests.HotelAvailRequest.BestAvailableRateCandidate getBestAvailableRateCandidate() {
            return this.bestAvailableRateCandidate;
         }

         public void setBestAvailableRateCandidate(OTAHotelAvailGetRQ.HotelAvailRequests.HotelAvailRequest.BestAvailableRateCandidate value) {
            this.bestAvailableRateCandidate = value;
         }

         public OTAHotelAvailGetRQ.HotelAvailRequests.HotelAvailRequest.HurdleRateCandidate getHurdleRateCandidate() {
            return this.hurdleRateCandidate;
         }

         public void setHurdleRateCandidate(OTAHotelAvailGetRQ.HotelAvailRequests.HotelAvailRequest.HurdleRateCandidate value) {
            this.hurdleRateCandidate = value;
         }

         public OTAHotelAvailGetRQ.HotelAvailRequests.HotelAvailRequest.DeltaCandidate getDeltaCandidate() {
            return this.deltaCandidate;
         }

         public void setDeltaCandidate(OTAHotelAvailGetRQ.HotelAvailRequests.HotelAvailRequest.DeltaCandidate value) {
            this.deltaCandidate = value;
         }

         public OTAHotelAvailGetRQ.HotelAvailRequests.HotelAvailRequest.HotelRef getHotelRef() {
            return this.hotelRef;
         }

         public void setHotelRef(OTAHotelAvailGetRQ.HotelAvailRequests.HotelAvailRequest.HotelRef value) {
            this.hotelRef = value;
         }

         public OTAHotelAvailGetRQ.HotelAvailRequests.HotelAvailRequest.RebatePrograms getRebatePrograms() {
            return this.rebatePrograms;
         }

         public void setRebatePrograms(OTAHotelAvailGetRQ.HotelAvailRequests.HotelAvailRequest.RebatePrograms value) {
            this.rebatePrograms = value;
         }

         public TPAExtensionsType getTPAExtensions() {
            return this.tpaExtensions;
         }

         public void setTPAExtensions(TPAExtensionsType value) {
            this.tpaExtensions = value;
         }

         public Boolean isSendBookingLimit() {
            return this.sendBookingLimit;
         }

         public void setSendBookingLimit(Boolean value) {
            this.sendBookingLimit = value;
         }

         public String getBookingLimitMessageType() {
            return this.bookingLimitMessageType;
         }

         public void setBookingLimitMessageType(String value) {
            this.bookingLimitMessageType = value;
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "")
         public static class BestAvailableRateCandidate {
            @XmlAttribute(name = "SendLengthOfStayTime")
            protected Boolean sendLengthOfStayTime;
            @XmlAttribute(name = "SendRatePlanCode")
            protected Boolean sendRatePlanCode;
            @XmlAttribute(name = "SendAmount")
            protected Boolean sendAmount;
            @XmlAttribute(name = "CurrencyCode")
            protected String currencyCode;
            @XmlAttribute(name = "DecimalPlaces")
            @XmlSchemaType(name = "nonNegativeInteger")
            protected BigInteger decimalPlaces;

            public Boolean isSendLengthOfStayTime() {
               return this.sendLengthOfStayTime;
            }

            public void setSendLengthOfStayTime(Boolean value) {
               this.sendLengthOfStayTime = value;
            }

            public Boolean isSendRatePlanCode() {
               return this.sendRatePlanCode;
            }

            public void setSendRatePlanCode(Boolean value) {
               this.sendRatePlanCode = value;
            }

            public Boolean isSendAmount() {
               return this.sendAmount;
            }

            public void setSendAmount(Boolean value) {
               this.sendAmount = value;
            }

            public String getCurrencyCode() {
               return this.currencyCode;
            }

            public void setCurrencyCode(String value) {
               this.currencyCode = value;
            }

            public BigInteger getDecimalPlaces() {
               return this.decimalPlaces;
            }

            public void setDecimalPlaces(BigInteger value) {
               this.decimalPlaces = value;
            }
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "")
         public static class DateRange {
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
         public static class DeltaCandidate {
            @XmlAttribute(name = "SendAmount")
            protected Boolean sendAmount;
            @XmlAttribute(name = "CurrencyCode")
            protected String currencyCode;
            @XmlAttribute(name = "DecimalPlaces")
            @XmlSchemaType(name = "nonNegativeInteger")
            protected BigInteger decimalPlaces;

            public Boolean isSendAmount() {
               return this.sendAmount;
            }

            public void setSendAmount(Boolean value) {
               this.sendAmount = value;
            }

            public String getCurrencyCode() {
               return this.currencyCode;
            }

            public void setCurrencyCode(String value) {
               this.currencyCode = value;
            }

            public BigInteger getDecimalPlaces() {
               return this.decimalPlaces;
            }

            public void setDecimalPlaces(BigInteger value) {
               this.decimalPlaces = value;
            }
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "")
         public static class HotelRef {
            @XmlAttribute(name = "SegmentCategoryCode")
            protected String segmentCategoryCode;
            @XmlAttribute(name = "PropertyClassCode")
            protected String propertyClassCode;
            @XmlAttribute(name = "ArchitecturalStyleCode")
            protected String architecturalStyleCode;
            @XmlAttribute(name = "SupplierIntegrationLevel")
            @XmlSchemaType(name = "nonNegativeInteger")
            protected BigInteger supplierIntegrationLevel;
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

            public String getSegmentCategoryCode() {
               return this.segmentCategoryCode;
            }

            public void setSegmentCategoryCode(String value) {
               this.segmentCategoryCode = value;
            }

            public String getPropertyClassCode() {
               return this.propertyClassCode;
            }

            public void setPropertyClassCode(String value) {
               this.propertyClassCode = value;
            }

            public String getArchitecturalStyleCode() {
               return this.architecturalStyleCode;
            }

            public void setArchitecturalStyleCode(String value) {
               this.architecturalStyleCode = value;
            }

            public BigInteger getSupplierIntegrationLevel() {
               return this.supplierIntegrationLevel;
            }

            public void setSupplierIntegrationLevel(BigInteger value) {
               this.supplierIntegrationLevel = value;
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

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "")
         public static class HurdleRateCandidate {
            @XmlAttribute(name = "SendAmount")
            protected Boolean sendAmount;
            @XmlAttribute(name = "CurrencyCode")
            protected String currencyCode;
            @XmlAttribute(name = "DecimalPlaces")
            @XmlSchemaType(name = "nonNegativeInteger")
            protected BigInteger decimalPlaces;

            public Boolean isSendAmount() {
               return this.sendAmount;
            }

            public void setSendAmount(Boolean value) {
               this.sendAmount = value;
            }

            public String getCurrencyCode() {
               return this.currencyCode;
            }

            public void setCurrencyCode(String value) {
               this.currencyCode = value;
            }

            public BigInteger getDecimalPlaces() {
               return this.decimalPlaces;
            }

            public void setDecimalPlaces(BigInteger value) {
               this.decimalPlaces = value;
            }
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "", propOrder = "lengthOfStayCandidate")
         public static class LengthsOfStayCandidates {
            @XmlElement(name = "LengthOfStayCandidate")
            protected List<OTAHotelAvailGetRQ.HotelAvailRequests.HotelAvailRequest.LengthsOfStayCandidates.LengthOfStayCandidate> lengthOfStayCandidate;
            @XmlAttribute(name = "SendAllLengthsOfStay")
            protected Boolean sendAllLengthsOfStay;
            @XmlAttribute(name = "FixedPatternLength")
            protected Integer fixedPatternLength;

            public List<OTAHotelAvailGetRQ.HotelAvailRequests.HotelAvailRequest.LengthsOfStayCandidates.LengthOfStayCandidate> getLengthOfStayCandidate() {
               if (this.lengthOfStayCandidate == null) {
                  this.lengthOfStayCandidate = new ArrayList<>();
               }

               return this.lengthOfStayCandidate;
            }

            public Boolean isSendAllLengthsOfStay() {
               return this.sendAllLengthsOfStay;
            }

            public void setSendAllLengthsOfStay(Boolean value) {
               this.sendAllLengthsOfStay = value;
            }

            public Integer getFixedPatternLength() {
               return this.fixedPatternLength;
            }

            public void setFixedPatternLength(Integer value) {
               this.fixedPatternLength = value;
            }

            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "")
            public static class LengthOfStayCandidate {
               @XmlAttribute(name = "MinMaxMessageType")
               protected String minMaxMessageType;

               public String getMinMaxMessageType() {
                  return this.minMaxMessageType;
               }

               public void setMinMaxMessageType(String value) {
                  this.minMaxMessageType = value;
               }
            }
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "", propOrder = "offer")
         public static class Offers {
            @XmlElement(name = "Offer", required = true)
            protected List<OTAHotelAvailGetRQ.HotelAvailRequests.HotelAvailRequest.Offers.Offer> offer;
            @XmlAttribute(name = "SendData")
            protected Boolean sendData;

            public List<OTAHotelAvailGetRQ.HotelAvailRequests.HotelAvailRequest.Offers.Offer> getOffer() {
               if (this.offer == null) {
                  this.offer = new ArrayList<>();
               }

               return this.offer;
            }

            public Boolean isSendData() {
               return this.sendData;
            }

            public void setSendData(Boolean value) {
               this.sendData = value;
            }

            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "")
            public static class Offer {
               @XmlAttribute(name = "OfferCode")
               protected String offerCode;

               public String getOfferCode() {
                  return this.offerCode;
               }

               public void setOfferCode(String value) {
                  this.offerCode = value;
               }
            }
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "", propOrder = "rebateProgram")
         public static class RebatePrograms {
            @XmlElement(name = "RebateProgram", required = true)
            protected List<RebateType> rebateProgram;
            @XmlAttribute(name = "SendDataInd")
            protected Boolean sendDataInd;

            public List<RebateType> getRebateProgram() {
               if (this.rebateProgram == null) {
                  this.rebateProgram = new ArrayList<>();
               }

               return this.rebateProgram;
            }

            public Boolean isSendDataInd() {
               return this.sendDataInd;
            }

            public void setSendDataInd(Boolean value) {
               this.sendDataInd = value;
            }
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "", propOrder = "restrictionStatusCandidate")
         public static class RestrictionStatusCandidates {
            @XmlElement(name = "RestrictionStatusCandidate", required = true)
            protected List<OTAHotelAvailGetRQ.HotelAvailRequests.HotelAvailRequest.RestrictionStatusCandidates.RestrictionStatusCandidate> restrictionStatusCandidate;
            @XmlAttribute(name = "SendAllRestrictions")
            protected Boolean sendAllRestrictions;

            public List<OTAHotelAvailGetRQ.HotelAvailRequests.HotelAvailRequest.RestrictionStatusCandidates.RestrictionStatusCandidate> getRestrictionStatusCandidate() {
               if (this.restrictionStatusCandidate == null) {
                  this.restrictionStatusCandidate = new ArrayList<>();
               }

               return this.restrictionStatusCandidate;
            }

            public Boolean isSendAllRestrictions() {
               return this.sendAllRestrictions;
            }

            public void setSendAllRestrictions(Boolean value) {
               this.sendAllRestrictions = value;
            }

            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "")
            public static class RestrictionStatusCandidate {
               @XmlAttribute(name = "MaxAdvancedBookingOffset")
               protected Duration maxAdvancedBookingOffset;
               @XmlAttribute(name = "MinAdvancedBookingOffset")
               protected Duration minAdvancedBookingOffset;
               @XmlAttribute(name = "Restriction")
               protected List<String> restriction;
               @XmlAttribute(name = "Status")
               protected List<String> status;
               @XmlAttribute(name = "SellThroughOpenIndicator")
               protected Boolean sellThroughOpenIndicator;

               public Duration getMaxAdvancedBookingOffset() {
                  return this.maxAdvancedBookingOffset;
               }

               public void setMaxAdvancedBookingOffset(Duration value) {
                  this.maxAdvancedBookingOffset = value;
               }

               public Duration getMinAdvancedBookingOffset() {
                  return this.minAdvancedBookingOffset;
               }

               public void setMinAdvancedBookingOffset(Duration value) {
                  this.minAdvancedBookingOffset = value;
               }

               public List<String> getRestriction() {
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

               public Boolean isSellThroughOpenIndicator() {
                  return this.sellThroughOpenIndicator;
               }

               public void setSellThroughOpenIndicator(Boolean value) {
                  this.sellThroughOpenIndicator = value;
               }
            }
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "", propOrder = "roomTypeCandidate")
         public static class RoomTypeCandidates {
            @XmlElement(name = "RoomTypeCandidate", required = true)
            protected List<OTAHotelAvailGetRQ.HotelAvailRequests.HotelAvailRequest.RoomTypeCandidates.RoomTypeCandidate> roomTypeCandidate;

            public List<OTAHotelAvailGetRQ.HotelAvailRequests.HotelAvailRequest.RoomTypeCandidates.RoomTypeCandidate> getRoomTypeCandidate() {
               if (this.roomTypeCandidate == null) {
                  this.roomTypeCandidate = new ArrayList<>();
               }

               return this.roomTypeCandidate;
            }

            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "")
            public static class RoomTypeCandidate extends RoomStayCandidateType {
            }
         }
      }
   }
}
