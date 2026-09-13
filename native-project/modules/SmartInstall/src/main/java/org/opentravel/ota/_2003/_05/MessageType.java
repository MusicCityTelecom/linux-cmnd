package org.opentravel.ota._2003._05;

import java.math.BigDecimal;
import java.math.BigInteger;
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
@XmlType(name = "MessageType", propOrder = {"originalPayloadStdAttributes", "messageContent"})
public class MessageType {
   @XmlElement(name = "OriginalPayloadStdAttributes")
   protected MessageType.OriginalPayloadStdAttributes originalPayloadStdAttributes;
   @XmlElement(name = "MessageContent")
   protected String messageContent;
   @XmlAttribute(name = "StartSeqNmbr")
   @XmlSchemaType(name = "nonNegativeInteger")
   protected BigInteger startSeqNmbr;
   @XmlAttribute(name = "EndSeqNmbr")
   @XmlSchemaType(name = "nonNegativeInteger")
   protected BigInteger endSeqNmbr;
   @XmlAttribute(name = "MessageType")
   protected String messageType;
   @XmlAttribute(name = "ResponseValue")
   protected String responseValue;
   @XmlAttribute(name = "RequestCode")
   protected String requestCode;
   @XmlAttribute(name = "ReasonForRequest")
   protected String reasonForRequest;
   @XmlAttribute(name = "UserName")
   protected String userName;
   @XmlAttribute(name = "RatePlanCode")
   protected String ratePlanCode;
   @XmlAttribute(name = "ConfirmationID")
   protected String confirmationID;
   @XmlAttribute(name = "ReservationID")
   protected String reservationID;
   @XmlAttribute(name = "Start")
   protected String start;
   @XmlAttribute(name = "Duration")
   protected String duration;
   @XmlAttribute(name = "End")
   protected String end;
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

   public MessageType.OriginalPayloadStdAttributes getOriginalPayloadStdAttributes() {
      return this.originalPayloadStdAttributes;
   }

   public void setOriginalPayloadStdAttributes(MessageType.OriginalPayloadStdAttributes value) {
      this.originalPayloadStdAttributes = value;
   }

   public String getMessageContent() {
      return this.messageContent;
   }

   public void setMessageContent(String value) {
      this.messageContent = value;
   }

   public BigInteger getStartSeqNmbr() {
      return this.startSeqNmbr;
   }

   public void setStartSeqNmbr(BigInteger value) {
      this.startSeqNmbr = value;
   }

   public BigInteger getEndSeqNmbr() {
      return this.endSeqNmbr;
   }

   public void setEndSeqNmbr(BigInteger value) {
      this.endSeqNmbr = value;
   }

   public String getMessageType() {
      return this.messageType;
   }

   public void setMessageType(String value) {
      this.messageType = value;
   }

   public String getResponseValue() {
      return this.responseValue;
   }

   public void setResponseValue(String value) {
      this.responseValue = value;
   }

   public String getRequestCode() {
      return this.requestCode;
   }

   public void setRequestCode(String value) {
      this.requestCode = value;
   }

   public String getReasonForRequest() {
      return this.reasonForRequest;
   }

   public void setReasonForRequest(String value) {
      this.reasonForRequest = value;
   }

   public String getUserName() {
      return this.userName;
   }

   public void setUserName(String value) {
      this.userName = value;
   }

   public String getRatePlanCode() {
      return this.ratePlanCode;
   }

   public void setRatePlanCode(String value) {
      this.ratePlanCode = value;
   }

   public String getConfirmationID() {
      return this.confirmationID;
   }

   public void setConfirmationID(String value) {
      this.confirmationID = value;
   }

   public String getReservationID() {
      return this.reservationID;
   }

   public void setReservationID(String value) {
      this.reservationID = value;
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
   public static class OriginalPayloadStdAttributes {
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
   }
}
