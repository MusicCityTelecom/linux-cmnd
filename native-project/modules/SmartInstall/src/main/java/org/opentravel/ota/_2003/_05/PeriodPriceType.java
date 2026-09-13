package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PeriodPriceType", propOrder = "price")
public class PeriodPriceType extends OperationScheduleType {
   @XmlElement(name = "Price")
   protected List<PkgPriceType> price;
   @XmlAttribute(name = "RPH")
   protected String rph;
   @XmlAttribute(name = "Category")
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   protected String category;
   @XmlAttribute(name = "Type")
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   protected String type;
   @XmlAttribute(name = "DurationPeriod")
   protected String durationPeriod;
   @XmlAttribute(name = "PriceBasis")
   protected PricingType priceBasis;
   @XmlAttribute(name = "BasePeriodRPHs")
   protected List<String> basePeriodRPHs;
   @XmlAttribute(name = "GuidePriceIndicator")
   protected Boolean guidePriceIndicator;
   @XmlAttribute(name = "MaximumPeriod")
   protected String maximumPeriod;

   public List<PkgPriceType> getPrice() {
      if (this.price == null) {
         this.price = new ArrayList<>();
      }

      return this.price;
   }

   public String getRPH() {
      return this.rph;
   }

   public void setRPH(String value) {
      this.rph = value;
   }

   public String getCategory() {
      return this.category;
   }

   public void setCategory(String value) {
      this.category = value;
   }

   public String getType() {
      return this.type;
   }

   public void setType(String value) {
      this.type = value;
   }

   public String getDurationPeriod() {
      return this.durationPeriod;
   }

   public void setDurationPeriod(String value) {
      this.durationPeriod = value;
   }

   public PricingType getPriceBasis() {
      return this.priceBasis;
   }

   public void setPriceBasis(PricingType value) {
      this.priceBasis = value;
   }

   public List<String> getBasePeriodRPHs() {
      if (this.basePeriodRPHs == null) {
         this.basePeriodRPHs = new ArrayList<>();
      }

      return this.basePeriodRPHs;
   }

   public Boolean isGuidePriceIndicator() {
      return this.guidePriceIndicator;
   }

   public void setGuidePriceIndicator(Boolean value) {
      this.guidePriceIndicator = value;
   }

   public String getMaximumPeriod() {
      return this.maximumPeriod;
   }

   public void setMaximumPeriod(String value) {
      this.maximumPeriod = value;
   }
}
