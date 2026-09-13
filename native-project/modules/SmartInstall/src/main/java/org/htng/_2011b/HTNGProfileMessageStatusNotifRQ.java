package org.htng._2011b;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.ParagraphType;
import org.opentravel.ota._2003._05.TPAExtensionsType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"room", "profileMessageSummary", "profileMessages", "tpaExtensions"})
@XmlRootElement(name = "HTNG_ProfileMessageStatusNotifRQ")
public class HTNGProfileMessageStatusNotifRQ extends HTNGRequestBaseType {
   @XmlElement(name = "Room")
   protected HTNGComponentRoomType room;
   @XmlElement(name = "ProfileMessageSummary")
   protected HTNGProfileMessageSummaryType profileMessageSummary;
   @XmlElement(name = "ProfileMessages", required = true)
   protected HTNGProfileMessageStatusNotifRQ.ProfileMessages profileMessages;
   @XmlElement(name = "TPA_Extensions", namespace = "http://www.opentravel.org/OTA/2003/05")
   protected TPAExtensionsType tpaExtensions;

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

   public HTNGProfileMessageStatusNotifRQ.ProfileMessages getProfileMessages() {
      return this.profileMessages;
   }

   public void setProfileMessages(HTNGProfileMessageStatusNotifRQ.ProfileMessages value) {
      this.profileMessages = value;
   }

   public TPAExtensionsType getTPAExtensions() {
      return this.tpaExtensions;
   }

   public void setTPAExtensions(TPAExtensionsType value) {
      this.tpaExtensions = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "profileMessage")
   public static class ProfileMessages {
      @XmlElement(name = "ProfileMessage", required = true)
      protected List<HTNGProfileMessageStatusNotifRQ.ProfileMessages.ProfileMessage> profileMessage;

      public List<HTNGProfileMessageStatusNotifRQ.ProfileMessages.ProfileMessage> getProfileMessage() {
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
