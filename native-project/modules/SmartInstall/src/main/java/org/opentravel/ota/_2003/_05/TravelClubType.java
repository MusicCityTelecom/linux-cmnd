package org.opentravel.ota._2003._05;

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
@XmlType(name = "TravelClubType", propOrder = {"travelClubName", "clubMemberName"})
public class TravelClubType {
   @XmlElement(name = "TravelClubName", required = true)
   protected CompanyNameType travelClubName;
   @XmlElement(name = "ClubMemberName")
   protected TravelClubType.ClubMemberName clubMemberName;
   @XmlAttribute(name = "EffectiveDate")
   @XmlSchemaType(name = "date")
   protected XMLGregorianCalendar effectiveDate;
   @XmlAttribute(name = "ExpireDate")
   @XmlSchemaType(name = "date")
   protected XMLGregorianCalendar expireDate;
   @XmlAttribute(name = "ExpireDateExclusiveIndicator")
   protected Boolean expireDateExclusiveIndicator;
   @XmlAttribute(name = "ShareSynchInd")
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   protected String shareSynchInd;
   @XmlAttribute(name = "ShareMarketInd")
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   protected String shareMarketInd;

   public CompanyNameType getTravelClubName() {
      return this.travelClubName;
   }

   public void setTravelClubName(CompanyNameType value) {
      this.travelClubName = value;
   }

   public TravelClubType.ClubMemberName getClubMemberName() {
      return this.clubMemberName;
   }

   public void setClubMemberName(TravelClubType.ClubMemberName value) {
      this.clubMemberName = value;
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
   @XmlType(name = "")
   public static class ClubMemberName extends PersonNameType {
      @XmlAttribute(name = "ID", required = true)
      protected String id;

      public String getID() {
         return this.id;
      }

      public void setID(String value) {
         this.id = value;
      }
   }
}
