package org.opentravel.ota._2003._05;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DiscountType", propOrder = "discountReason")
@XmlSeeAlso(AmountType.Discount.class)
public class DiscountType extends TotalType {
   @XmlElement(name = "DiscountReason", required = true)
   protected ParagraphType discountReason;
   @XmlAttribute(name = "TaxInclusive")
   protected Boolean taxInclusive;
   @XmlAttribute(name = "Percent")
   protected BigDecimal percent;
   @XmlAttribute(name = "DiscountCode")
   protected String discountCode;
   @XmlAttribute(name = "RestrictedDisplayIndicator")
   protected Boolean restrictedDisplayIndicator;

   public ParagraphType getDiscountReason() {
      return this.discountReason;
   }

   public void setDiscountReason(ParagraphType value) {
      this.discountReason = value;
   }

   public Boolean isTaxInclusive() {
      return this.taxInclusive;
   }

   public void setTaxInclusive(Boolean value) {
      this.taxInclusive = value;
   }

   public BigDecimal getPercent() {
      return this.percent;
   }

   public void setPercent(BigDecimal value) {
      this.percent = value;
   }

   public String getDiscountCode() {
      return this.discountCode;
   }

   public void setDiscountCode(String value) {
      this.discountCode = value;
   }

   public Boolean isRestrictedDisplayIndicator() {
      return this.restrictedDisplayIndicator;
   }

   public void setRestrictedDisplayIndicator(Boolean value) {
      this.restrictedDisplayIndicator = value;
   }
}
