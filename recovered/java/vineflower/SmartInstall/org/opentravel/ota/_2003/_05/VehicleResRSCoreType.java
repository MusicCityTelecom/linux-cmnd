package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VehicleResRSCoreType", propOrder = {"vehReservation", "tpaExtensions"})
@XmlSeeAlso(OTANotifReportRQ.NotifDetails.VehNotifReport.VehRes.VehNotifReportRQCore.class)
public class VehicleResRSCoreType {
   @XmlElement(name = "VehReservation", required = true)
   protected VehicleReservationType vehReservation;
   @XmlElement(name = "TPA_Extensions")
   protected TPAExtensionsType tpaExtensions;

   public VehicleReservationType getVehReservation() {
      return this.vehReservation;
   }

   public void setVehReservation(VehicleReservationType value) {
      this.vehReservation = value;
   }

   public TPAExtensionsType getTPAExtensions() {
      return this.tpaExtensions;
   }

   public void setTPAExtensions(TPAExtensionsType value) {
      this.tpaExtensions = value;
   }
}
