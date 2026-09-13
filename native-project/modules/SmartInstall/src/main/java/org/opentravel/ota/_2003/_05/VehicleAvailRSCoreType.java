package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VehicleAvailRSCoreType", propOrder = {"vehRentalCore", "vehVendorAvails"})
public class VehicleAvailRSCoreType {
   @XmlElement(name = "VehRentalCore", required = true)
   protected VehicleRentalCoreType vehRentalCore;
   @XmlElement(name = "VehVendorAvails", required = true)
   protected VehicleAvailRSCoreType.VehVendorAvails vehVendorAvails;

   public VehicleRentalCoreType getVehRentalCore() {
      return this.vehRentalCore;
   }

   public void setVehRentalCore(VehicleRentalCoreType value) {
      this.vehRentalCore = value;
   }

   public VehicleAvailRSCoreType.VehVendorAvails getVehVendorAvails() {
      return this.vehVendorAvails;
   }

   public void setVehVendorAvails(VehicleAvailRSCoreType.VehVendorAvails value) {
      this.vehVendorAvails = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "vehVendorAvail")
   public static class VehVendorAvails {
      @XmlElement(name = "VehVendorAvail", required = true)
      protected List<VehicleVendorAvailabilityType> vehVendorAvail;

      public List<VehicleVendorAvailabilityType> getVehVendorAvail() {
         if (this.vehVendorAvail == null) {
            this.vehVendorAvail = new ArrayList<>();
         }

         return this.vehVendorAvail;
      }
   }
}
