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
@XmlType(name = "", propOrder = {"pos", "ratePlans"})
@XmlRootElement(name = "OTA_HotelRatePlanRQ")
public class OTAHotelRatePlanRQ {
   @XmlElement(name = "POS")
   protected POSType pos;
   @XmlElement(name = "RatePlans", required = true)
   protected OTAHotelRatePlanRQ.RatePlans ratePlans;
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
   @XmlAttribute(name = "MaxResponses")
   @XmlSchemaType(name = "positiveInteger")
   protected BigInteger maxResponses;

   public POSType getPOS() {
      return this.pos;
   }

   public void setPOS(POSType value) {
      this.pos = value;
   }

   public OTAHotelRatePlanRQ.RatePlans getRatePlans() {
      return this.ratePlans;
   }

   public void setRatePlans(OTAHotelRatePlanRQ.RatePlans value) {
      this.ratePlans = value;
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

   public BigInteger getMaxResponses() {
      return this.maxResponses;
   }

   public void setMaxResponses(BigInteger value) {
      this.maxResponses = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "ratePlan")
   public static class RatePlans {
      @XmlElement(name = "RatePlan", required = true)
      protected List<OTAHotelRatePlanRQ.RatePlans.RatePlan> ratePlan;

      public List<OTAHotelRatePlanRQ.RatePlans.RatePlan> getRatePlan() {
         if (this.ratePlan == null) {
            this.ratePlan = new ArrayList<>();
         }

         return this.ratePlan;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = {"dateRange", "destinationSystemsCode", "ratePlanCandidates", "offers", "hotelRef", "tpaExtensions"})
      public static class RatePlan {
         @XmlElement(name = "DateRange")
         protected OTAHotelRatePlanRQ.RatePlans.RatePlan.DateRange dateRange;
         @XmlElement(name = "DestinationSystemsCode")
         protected DestinationSystemCodesType destinationSystemsCode;
         @XmlElement(name = "RatePlanCandidates")
         protected RatePlanCandidatesType ratePlanCandidates;
         @XmlElement(name = "Offers")
         protected OTAHotelRatePlanRQ.RatePlans.RatePlan.Offers offers;
         @XmlElement(name = "HotelRef")
         protected OTAHotelRatePlanRQ.RatePlans.RatePlan.HotelRef hotelRef;
         @XmlElement(name = "TPA_Extensions")
         protected TPAExtensionsType tpaExtensions;

         public OTAHotelRatePlanRQ.RatePlans.RatePlan.DateRange getDateRange() {
            return this.dateRange;
         }

         public void setDateRange(OTAHotelRatePlanRQ.RatePlans.RatePlan.DateRange value) {
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

         public OTAHotelRatePlanRQ.RatePlans.RatePlan.Offers getOffers() {
            return this.offers;
         }

         public void setOffers(OTAHotelRatePlanRQ.RatePlans.RatePlan.Offers value) {
            this.offers = value;
         }

         public OTAHotelRatePlanRQ.RatePlans.RatePlan.HotelRef getHotelRef() {
            return this.hotelRef;
         }

         public void setHotelRef(OTAHotelRatePlanRQ.RatePlans.RatePlan.HotelRef value) {
            this.hotelRef = value;
         }

         public TPAExtensionsType getTPAExtensions() {
            return this.tpaExtensions;
         }

         public void setTPAExtensions(TPAExtensionsType value) {
            this.tpaExtensions = value;
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
         @XmlType(name = "", propOrder = "offer")
         public static class Offers {
            @XmlElement(name = "Offer", required = true)
            protected List<OTAHotelRatePlanRQ.RatePlans.RatePlan.Offers.Offer> offer;
            @XmlAttribute(name = "SendData")
            protected Boolean sendData;

            public List<OTAHotelRatePlanRQ.RatePlans.RatePlan.Offers.Offer> getOffer() {
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
      }
   }
}
