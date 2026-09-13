package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RatePlanLiteType", propOrder = {"guarantee", "ratePlanDescription"})
public class RatePlanLiteType {
   @XmlElement(name = "Guarantee")
   protected List<GuaranteeType> guarantee;
   @XmlElement(name = "RatePlanDescription")
   protected ParagraphType ratePlanDescription;
   @XmlAttribute(name = "RatePlanCode")
   protected String ratePlanCode;
   @XmlAttribute(name = "RateIndicator")
   protected RateIndicatorType rateIndicator;
   @XmlAttribute(name = "RatePlanType")
   protected String ratePlanType;
   @XmlAttribute(name = "RatePlanID")
   protected String ratePlanID;
   @XmlAttribute(name = "EffectiveDate")
   @XmlSchemaType(name = "date")
   protected XMLGregorianCalendar effectiveDate;
   @XmlAttribute(name = "ExpireDate")
   @XmlSchemaType(name = "date")
   protected XMLGregorianCalendar expireDate;
   @XmlAttribute(name = "ExpireDateExclusiveIndicator")
   protected Boolean expireDateExclusiveIndicator;

   public List<GuaranteeType> getGuarantee() {
      if (this.guarantee == null) {
         this.guarantee = new ArrayList<>();
      }

      return this.guarantee;
   }

   public ParagraphType getRatePlanDescription() {
      return this.ratePlanDescription;
   }

   public void setRatePlanDescription(ParagraphType value) {
      this.ratePlanDescription = value;
   }

   public String getRatePlanCode() {
      return this.ratePlanCode;
   }

   public void setRatePlanCode(String value) {
      this.ratePlanCode = value;
   }

   public RateIndicatorType getRateIndicator() {
      return this.rateIndicator;
   }

   public void setRateIndicator(RateIndicatorType value) {
      this.rateIndicator = value;
   }

   public String getRatePlanType() {
      return this.ratePlanType;
   }

   public void setRatePlanType(String value) {
      this.ratePlanType = value;
   }

   public String getRatePlanID() {
      return this.ratePlanID;
   }

   public void setRatePlanID(String value) {
      this.ratePlanID = value;
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
}
