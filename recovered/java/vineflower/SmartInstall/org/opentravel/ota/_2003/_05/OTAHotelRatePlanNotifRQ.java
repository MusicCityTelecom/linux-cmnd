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
@XmlType(name = "", propOrder = {"pos", "uniqueID", "ratePlans", "offers", "tpaExtensions"})
@XmlRootElement(name = "OTA_HotelRatePlanNotifRQ")
public class OTAHotelRatePlanNotifRQ {
   @XmlElement(name = "POS")
   protected POSType pos;
   @XmlElement(name = "UniqueID")
   protected UniqueIDType uniqueID;
   @XmlElement(name = "RatePlans", required = true)
   protected OTAHotelRatePlanNotifRQ.RatePlans ratePlans;
   @XmlElement(name = "Offers")
   protected OTAHotelRatePlanNotifRQ.Offers offers;
   @XmlElement(name = "TPA_Extensions")
   protected TPAExtensionsType tpaExtensions;
   @XmlAttribute(name = "MessageContentCode")
   protected String messageContentCode;
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

   public UniqueIDType getUniqueID() {
      return this.uniqueID;
   }

   public void setUniqueID(UniqueIDType value) {
      this.uniqueID = value;
   }

   public OTAHotelRatePlanNotifRQ.RatePlans getRatePlans() {
      return this.ratePlans;
   }

   public void setRatePlans(OTAHotelRatePlanNotifRQ.RatePlans value) {
      this.ratePlans = value;
   }

   public OTAHotelRatePlanNotifRQ.Offers getOffers() {
      return this.offers;
   }

   public void setOffers(OTAHotelRatePlanNotifRQ.Offers value) {
      this.offers = value;
   }

   public TPAExtensionsType getTPAExtensions() {
      return this.tpaExtensions;
   }

   public void setTPAExtensions(TPAExtensionsType value) {
      this.tpaExtensions = value;
   }

   public String getMessageContentCode() {
      return this.messageContentCode;
   }

   public void setMessageContentCode(String value) {
      this.messageContentCode = value;
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
   @XmlType(name = "", propOrder = "offer")
   public static class Offers {
      @XmlElement(name = "Offer", required = true)
      protected List<OfferType> offer;

      public List<OfferType> getOffer() {
         if (this.offer == null) {
            this.offer = new ArrayList<>();
         }

         return this.offer;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "ratePlan")
   public static class RatePlans {
      @XmlElement(name = "RatePlan", required = true)
      protected List<HotelRatePlanType> ratePlan;
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

      public List<HotelRatePlanType> getRatePlan() {
         if (this.ratePlan == null) {
            this.ratePlan = new ArrayList<>();
         }

         return this.ratePlan;
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
}
