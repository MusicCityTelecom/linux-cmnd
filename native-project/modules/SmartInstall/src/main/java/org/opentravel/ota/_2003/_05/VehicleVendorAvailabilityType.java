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
@XmlType(name = "VehicleVendorAvailabilityType", propOrder = {"vendor", "vehAvails", "info"})
public class VehicleVendorAvailabilityType {
   @XmlElement(name = "Vendor")
   protected CompanyNameType vendor;
   @XmlElement(name = "VehAvails", required = true)
   protected VehicleVendorAvailabilityType.VehAvails vehAvails;
   @XmlElement(name = "Info")
   protected VehicleAvailVendorInfoType info;

   public CompanyNameType getVendor() {
      return this.vendor;
   }

   public void setVendor(CompanyNameType value) {
      this.vendor = value;
   }

   public VehicleVendorAvailabilityType.VehAvails getVehAvails() {
      return this.vehAvails;
   }

   public void setVehAvails(VehicleVendorAvailabilityType.VehAvails value) {
      this.vehAvails = value;
   }

   public VehicleAvailVendorInfoType getInfo() {
      return this.info;
   }

   public void setInfo(VehicleAvailVendorInfoType value) {
      this.info = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "vehAvail")
   public static class VehAvails {
      @XmlElement(name = "VehAvail", required = true)
      protected List<VehicleVendorAvailabilityType.VehAvails.VehAvail> vehAvail;
      @XmlAttribute(name = "RateCategory")
      protected String rateCategory;
      @XmlAttribute(name = "RatePeriod")
      protected RatePeriodSimpleType ratePeriod;

      public List<VehicleVendorAvailabilityType.VehAvails.VehAvail> getVehAvail() {
         if (this.vehAvail == null) {
            this.vehAvail = new ArrayList<>();
         }

         return this.vehAvail;
      }

      public String getRateCategory() {
         return this.rateCategory;
      }

      public void setRateCategory(String value) {
         this.rateCategory = value;
      }

      public RatePeriodSimpleType getRatePeriod() {
         return this.ratePeriod;
      }

      public void setRatePeriod(RatePeriodSimpleType value) {
         this.ratePeriod = value;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = {"vehAvailCore", "vehAvailInfo", "advanceBooking"})
      public static class VehAvail {
         @XmlElement(name = "VehAvailCore", required = true)
         protected VehicleAvailCoreType vehAvailCore;
         @XmlElement(name = "VehAvailInfo")
         protected VehicleAvailAdditionalInfoType vehAvailInfo;
         @XmlElement(name = "AdvanceBooking")
         protected VehicleVendorAvailabilityType.VehAvails.VehAvail.AdvanceBooking advanceBooking;

         public VehicleAvailCoreType getVehAvailCore() {
            return this.vehAvailCore;
         }

         public void setVehAvailCore(VehicleAvailCoreType value) {
            this.vehAvailCore = value;
         }

         public VehicleAvailAdditionalInfoType getVehAvailInfo() {
            return this.vehAvailInfo;
         }

         public void setVehAvailInfo(VehicleAvailAdditionalInfoType value) {
            this.vehAvailInfo = value;
         }

         public VehicleVendorAvailabilityType.VehAvails.VehAvail.AdvanceBooking getAdvanceBooking() {
            return this.advanceBooking;
         }

         public void setAdvanceBooking(VehicleVendorAvailabilityType.VehAvails.VehAvail.AdvanceBooking value) {
            this.advanceBooking = value;
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "")
         public static class AdvanceBooking {
            @XmlAttribute(name = "RulesApplyInd")
            protected Boolean rulesApplyInd;
            @XmlAttribute(name = "AbsoluteDeadline")
            protected String absoluteDeadline;
            @XmlAttribute(name = "OffsetTimeUnit")
            protected TimeUnitType offsetTimeUnit;
            @XmlAttribute(name = "OffsetUnitMultiplier")
            protected Integer offsetUnitMultiplier;
            @XmlAttribute(name = "OffsetDropTime")
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            protected String offsetDropTime;

            public Boolean isRulesApplyInd() {
               return this.rulesApplyInd;
            }

            public void setRulesApplyInd(Boolean value) {
               this.rulesApplyInd = value;
            }

            public String getAbsoluteDeadline() {
               return this.absoluteDeadline;
            }

            public void setAbsoluteDeadline(String value) {
               this.absoluteDeadline = value;
            }

            public TimeUnitType getOffsetTimeUnit() {
               return this.offsetTimeUnit;
            }

            public void setOffsetTimeUnit(TimeUnitType value) {
               this.offsetTimeUnit = value;
            }

            public Integer getOffsetUnitMultiplier() {
               return this.offsetUnitMultiplier;
            }

            public void setOffsetUnitMultiplier(Integer value) {
               this.offsetUnitMultiplier = value;
            }

            public String getOffsetDropTime() {
               return this.offsetDropTime;
            }

            public void setOffsetDropTime(String value) {
               this.offsetDropTime = value;
            }
         }
      }
   }
}
