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
@XmlType(name = "VehicleLocationAdditionalFeesType", propOrder = {"taxes", "fees", "surcharges", "miscellaneousCharges", "tpaExtensions"})
public class VehicleLocationAdditionalFeesType {
   @XmlElement(name = "Taxes")
   protected VehicleLocationAdditionalFeesType.Taxes taxes;
   @XmlElement(name = "Fees")
   protected VehicleLocationAdditionalFeesType.Fees fees;
   @XmlElement(name = "Surcharges")
   protected VehicleLocationAdditionalFeesType.Surcharges surcharges;
   @XmlElement(name = "MiscellaneousCharges")
   protected VehicleLocationAdditionalFeesType.MiscellaneousCharges miscellaneousCharges;
   @XmlElement(name = "TPA_Extensions")
   protected TPAExtensionsType tpaExtensions;

   public VehicleLocationAdditionalFeesType.Taxes getTaxes() {
      return this.taxes;
   }

   public void setTaxes(VehicleLocationAdditionalFeesType.Taxes value) {
      this.taxes = value;
   }

   public VehicleLocationAdditionalFeesType.Fees getFees() {
      return this.fees;
   }

   public void setFees(VehicleLocationAdditionalFeesType.Fees value) {
      this.fees = value;
   }

   public VehicleLocationAdditionalFeesType.Surcharges getSurcharges() {
      return this.surcharges;
   }

   public void setSurcharges(VehicleLocationAdditionalFeesType.Surcharges value) {
      this.surcharges = value;
   }

   public VehicleLocationAdditionalFeesType.MiscellaneousCharges getMiscellaneousCharges() {
      return this.miscellaneousCharges;
   }

   public void setMiscellaneousCharges(VehicleLocationAdditionalFeesType.MiscellaneousCharges value) {
      this.miscellaneousCharges = value;
   }

   public TPAExtensionsType getTPAExtensions() {
      return this.tpaExtensions;
   }

   public void setTPAExtensions(TPAExtensionsType value) {
      this.tpaExtensions = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = {"fee", "info"})
   public static class Fees {
      @XmlElement(name = "Fee")
      protected List<VehicleLocationAdditionalFeesType.Fees.Fee> fee;
      @XmlElement(name = "Info")
      protected FormattedTextType info;

      public List<VehicleLocationAdditionalFeesType.Fees.Fee> getFee() {
         if (this.fee == null) {
            this.fee = new ArrayList<>();
         }

         return this.fee;
      }

      public FormattedTextType getInfo() {
         return this.info;
      }

      public void setInfo(FormattedTextType value) {
         this.info = value;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = "info")
      public static class Fee extends VehicleChargeType {
         @XmlElement(name = "Info")
         protected FormattedTextType info;

         public FormattedTextType getInfo() {
            return this.info;
         }

         public void setInfo(FormattedTextType value) {
            this.info = value;
         }
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = {"miscellaneousCharge", "info"})
   public static class MiscellaneousCharges {
      @XmlElement(name = "MiscellaneousCharge")
      protected List<VehicleLocationAdditionalFeesType.MiscellaneousCharges.MiscellaneousCharge> miscellaneousCharge;
      @XmlElement(name = "Info")
      protected FormattedTextType info;

      public List<VehicleLocationAdditionalFeesType.MiscellaneousCharges.MiscellaneousCharge> getMiscellaneousCharge() {
         if (this.miscellaneousCharge == null) {
            this.miscellaneousCharge = new ArrayList<>();
         }

         return this.miscellaneousCharge;
      }

      public FormattedTextType getInfo() {
         return this.info;
      }

      public void setInfo(FormattedTextType value) {
         this.info = value;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = "info")
      public static class MiscellaneousCharge extends VehicleChargeType {
         @XmlElement(name = "Info")
         protected FormattedTextType info;

         public FormattedTextType getInfo() {
            return this.info;
         }

         public void setInfo(FormattedTextType value) {
            this.info = value;
         }
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = {"surcharge", "info"})
   public static class Surcharges {
      @XmlElement(name = "Surcharge")
      protected List<VehicleLocationAdditionalFeesType.Surcharges.Surcharge> surcharge;
      @XmlElement(name = "Info")
      protected FormattedTextType info;
      @XmlAttribute(name = "Start")
      protected String start;
      @XmlAttribute(name = "Duration")
      protected String duration;
      @XmlAttribute(name = "End")
      protected String end;

      public List<VehicleLocationAdditionalFeesType.Surcharges.Surcharge> getSurcharge() {
         if (this.surcharge == null) {
            this.surcharge = new ArrayList<>();
         }

         return this.surcharge;
      }

      public FormattedTextType getInfo() {
         return this.info;
      }

      public void setInfo(FormattedTextType value) {
         this.info = value;
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

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = "info")
      public static class Surcharge extends VehicleChargeType {
         @XmlElement(name = "Info")
         protected FormattedTextType info;

         public FormattedTextType getInfo() {
            return this.info;
         }

         public void setInfo(FormattedTextType value) {
            this.info = value;
         }
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = {"tax", "info"})
   public static class Taxes {
      @XmlElement(name = "Tax")
      protected List<VehicleLocationAdditionalFeesType.Taxes.Tax> tax;
      @XmlElement(name = "Info")
      protected FormattedTextType info;
      @XmlAttribute(name = "Start")
      protected String start;
      @XmlAttribute(name = "Duration")
      protected String duration;
      @XmlAttribute(name = "End")
      protected String end;

      public List<VehicleLocationAdditionalFeesType.Taxes.Tax> getTax() {
         if (this.tax == null) {
            this.tax = new ArrayList<>();
         }

         return this.tax;
      }

      public FormattedTextType getInfo() {
         return this.info;
      }

      public void setInfo(FormattedTextType value) {
         this.info = value;
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

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = "info")
      public static class Tax {
         @XmlElement(name = "Info")
         protected FormattedTextType info;
         @XmlAttribute(name = "TaxCode")
         protected String taxCode;
         @XmlAttribute(name = "Percentage")
         protected BigDecimal percentage;

         public FormattedTextType getInfo() {
            return this.info;
         }

         public void setInfo(FormattedTextType value) {
            this.info = value;
         }

         public String getTaxCode() {
            return this.taxCode;
         }

         public void setTaxCode(String value) {
            this.taxCode = value;
         }

         public BigDecimal getPercentage() {
            return this.percentage;
         }

         public void setPercentage(BigDecimal value) {
            this.percentage = value;
         }
      }
   }
}
