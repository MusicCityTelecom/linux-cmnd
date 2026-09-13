package org.htng._2011b;

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
import org.opentravel.ota._2003._05.ErrorsType;
import org.opentravel.ota._2003._05.ParagraphType;
import org.opentravel.ota._2003._05.SuccessType;
import org.opentravel.ota._2003._05.UniqueIDType;
import org.opentravel.ota._2003._05.WarningsType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"success", "warnings", "uniqueID", "room", "profileMessageSummary", "profileMessages", "errors"})
@XmlRootElement(name = "HTNG_ProfileMessageRS")
public class HTNGProfileMessageRS {
   @XmlElement(name = "Success")
   protected SuccessType success;
   @XmlElement(name = "Warnings")
   protected WarningsType warnings;
   @XmlElement(name = "UniqueID")
   protected UniqueIDType uniqueID;
   @XmlElement(name = "Room")
   protected HTNGComponentRoomType room;
   @XmlElement(name = "ProfileMessageSummary")
   protected HTNGProfileMessageSummaryType profileMessageSummary;
   @XmlElement(name = "ProfileMessages")
   protected HTNGProfileMessageRS.ProfileMessages profileMessages;
   @XmlElement(name = "Errors")
   protected ErrorsType errors;
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

   public UniqueIDType getUniqueID() {
      return this.uniqueID;
   }

   public void setUniqueID(UniqueIDType value) {
      this.uniqueID = value;
   }

   public HTNGComponentRoomType getRoom() {
      return this.room;
   }

   public void setRoom(HTNGComponentRoomType value) {
      this.room = value;
   }

   public HTNGProfileMessageSummaryType getProfileMessageSummary() {
      return this.profileMessageSummary;
   }

   public void setProfileMessageSummary(HTNGProfileMessageSummaryType value) {
      this.profileMessageSummary = value;
   }

   public HTNGProfileMessageRS.ProfileMessages getProfileMessages() {
      return this.profileMessages;
   }

   public void setProfileMessages(HTNGProfileMessageRS.ProfileMessages value) {
      this.profileMessages = value;
   }

   public ErrorsType getErrors() {
      return this.errors;
   }

   public void setErrors(ErrorsType value) {
      this.errors = value;
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
   @XmlType(name = "", propOrder = "profileMessage")
   public static class ProfileMessages {
      @XmlElement(name = "ProfileMessage", required = true)
      protected List<HTNGProfileMessageRS.ProfileMessages.ProfileMessage> profileMessage;

      public List<HTNGProfileMessageRS.ProfileMessages.ProfileMessage> getProfileMessage() {
         if (this.profileMessage == null) {
            this.profileMessage = new ArrayList<>();
         }

         return this.profileMessage;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class ProfileMessage extends ParagraphType {
         @XmlAttribute(name = "CreatedBySystemID")
         protected String createdBySystemID;
         @XmlAttribute(name = "MessageID", required = true)
         protected String messageID;
         @XmlAttribute(name = "Status", required = true)
         protected HTNGProfileMessageStatusType status;

         public String getCreatedBySystemID() {
            return this.createdBySystemID;
         }

         public void setCreatedBySystemID(String value) {
            this.createdBySystemID = value;
         }

         public String getMessageID() {
            return this.messageID;
         }

         public void setMessageID(String value) {
            this.messageID = value;
         }

         public HTNGProfileMessageStatusType getStatus() {
            return this.status;
         }

         public void setStatus(HTNGProfileMessageStatusType value) {
            this.status = value;
         }
      }
   }
}
