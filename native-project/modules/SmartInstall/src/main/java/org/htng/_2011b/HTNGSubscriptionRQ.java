package org.htng._2011b;

import java.math.BigDecimal;
import java.math.BigInteger;
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
import org.opentravel.ota._2003._05.TPAExtensionsType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"eventType", "tpaExtensions"})
@XmlRootElement(name = "HTNG_SubscriptionRQ")
public class HTNGSubscriptionRQ {
   @XmlElement(name = "EventType", required = true)
   protected HTNGSubscriptionRQ.EventType eventType;
   @XmlElement(name = "TPA_Extensions", namespace = "http://www.opentravel.org/OTA/2003/05")
   protected TPAExtensionsType tpaExtensions;
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

   public HTNGSubscriptionRQ.EventType getEventType() {
      return this.eventType;
   }

   public void setEventType(HTNGSubscriptionRQ.EventType value) {
      this.eventType = value;
   }

   public TPAExtensionsType getTPAExtensions() {
      return this.tpaExtensions;
   }

   public void setTPAExtensions(TPAExtensionsType value) {
      this.tpaExtensions = value;
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
   @XmlType(name = "", propOrder = {"consumerEndpoint", "requestedFilters"})
   public static class EventType {
      @XmlElement(name = "ConsumerEndpoint", required = true)
      @XmlSchemaType(name = "anyURI")
      protected String consumerEndpoint;
      @XmlElement(name = "RequestedFilters", required = true)
      protected HTNGEventFiltersType requestedFilters;
      @XmlAttribute(name = "ConsumerSubscriptionID")
      protected String consumerSubscriptionID;
      @XmlAttribute(name = "ProducerEventID")
      protected String producerEventID;
      @XmlAttribute(name = "TerminationDateTime")
      @XmlSchemaType(name = "dateTime")
      protected XMLGregorianCalendar terminationDateTime;

      public String getConsumerEndpoint() {
         return this.consumerEndpoint;
      }

      public void setConsumerEndpoint(String value) {
         this.consumerEndpoint = value;
      }

      public HTNGEventFiltersType getRequestedFilters() {
         return this.requestedFilters;
      }

      public void setRequestedFilters(HTNGEventFiltersType value) {
         this.requestedFilters = value;
      }

      public String getConsumerSubscriptionID() {
         return this.consumerSubscriptionID;
      }

      public void setConsumerSubscriptionID(String value) {
         this.consumerSubscriptionID = value;
      }

      public String getProducerEventID() {
         return this.producerEventID;
      }

      public void setProducerEventID(String value) {
         this.producerEventID = value;
      }

      public XMLGregorianCalendar getTerminationDateTime() {
         return this.terminationDateTime;
      }

      public void setTerminationDateTime(XMLGregorianCalendar value) {
         this.terminationDateTime = value;
      }
   }
}
