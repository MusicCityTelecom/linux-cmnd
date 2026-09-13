package org.opentravel.ota._2003._05;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AdditionalGuestAmountType", propOrder = {"amount", "addlGuestAmtDescription"})
public class AdditionalGuestAmountType {
   @XmlElement(name = "Amount", required = true)
   protected TotalType amount;
   @XmlElement(name = "AddlGuestAmtDescription")
   protected List<ParagraphType> addlGuestAmtDescription;
   @XmlAttribute(name = "MaxAdditionalGuests")
   protected Integer maxAdditionalGuests;
   @XmlAttribute(name = "Type")
   protected String type;
   @XmlAttribute(name = "Percent")
   protected BigDecimal percent;
   @XmlAttribute(name = "RPH")
   protected String rph;
   @XmlAttribute(name = "AgeQualifyingCode")
   protected String ageQualifyingCode;
   @XmlAttribute(name = "MinAge")
   protected Integer minAge;
   @XmlAttribute(name = "MaxAge")
   protected Integer maxAge;
   @XmlAttribute(name = "AgeTimeUnit")
   protected TimeUnitType ageTimeUnit;
   @XmlAttribute(name = "AgeBucket")
   protected String ageBucket;

   public TotalType getAmount() {
      return this.amount;
   }

   public void setAmount(TotalType value) {
      this.amount = value;
   }

   public List<ParagraphType> getAddlGuestAmtDescription() {
      if (this.addlGuestAmtDescription == null) {
         this.addlGuestAmtDescription = new ArrayList<>();
      }

      return this.addlGuestAmtDescription;
   }

   public Integer getMaxAdditionalGuests() {
      return this.maxAdditionalGuests;
   }

   public void setMaxAdditionalGuests(Integer value) {
      this.maxAdditionalGuests = value;
   }

   public String getType() {
      return this.type;
   }

   public void setType(String value) {
      this.type = value;
   }

   public BigDecimal getPercent() {
      return this.percent;
   }

   public void setPercent(BigDecimal value) {
      this.percent = value;
   }

   public String getRPH() {
      return this.rph;
   }

   public void setRPH(String value) {
      this.rph = value;
   }

   public String getAgeQualifyingCode() {
      return this.ageQualifyingCode;
   }

   public void setAgeQualifyingCode(String value) {
      this.ageQualifyingCode = value;
   }

   public Integer getMinAge() {
      return this.minAge;
   }

   public void setMinAge(Integer value) {
      this.minAge = value;
   }

   public Integer getMaxAge() {
      return this.maxAge;
   }

   public void setMaxAge(Integer value) {
      this.maxAge = value;
   }

   public TimeUnitType getAgeTimeUnit() {
      return this.ageTimeUnit;
   }

   public void setAgeTimeUnit(TimeUnitType value) {
      this.ageTimeUnit = value;
   }

   public String getAgeBucket() {
      return this.ageBucket;
   }

   public void setAgeBucket(String value) {
      this.ageBucket = value;
   }
}
