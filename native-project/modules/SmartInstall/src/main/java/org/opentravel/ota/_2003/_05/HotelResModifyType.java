package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HotelResModifyType", propOrder = {"hotelResModify", "routingHops", "writtenConfInst"})
public class HotelResModifyType {
   @XmlElement(name = "HotelResModify", required = true)
   protected List<HotelResModifyType.HotelResModify> hotelResModify;
   @XmlElement(name = "RoutingHops")
   protected RoutingHopType routingHops;
   @XmlElement(name = "WrittenConfInst")
   protected WrittenConfInstType writtenConfInst;

   public List<HotelResModifyType.HotelResModify> getHotelResModify() {
      if (this.hotelResModify == null) {
         this.hotelResModify = new ArrayList<>();
      }

      return this.hotelResModify;
   }

   public RoutingHopType getRoutingHops() {
      return this.routingHops;
   }

   public void setRoutingHops(RoutingHopType value) {
      this.routingHops = value;
   }

   public WrittenConfInstType getWrittenConfInst() {
      return this.writtenConfInst;
   }

   public void setWrittenConfInst(WrittenConfInstType value) {
      this.writtenConfInst = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "verification")
   public static class HotelResModify extends HotelReservationType {
      @XmlElement(name = "Verification")
      protected List<HotelResModifyType.HotelResModify.Verification> verification;

      public List<HotelResModifyType.HotelResModify.Verification> getVerification() {
         if (this.verification == null) {
            this.verification = new ArrayList<>();
         }

         return this.verification;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = "currentStateReservation")
      public static class Verification extends VerificationType {
         @XmlElement(name = "CurrentStateReservation")
         protected HotelReservationType currentStateReservation;

         public HotelReservationType getCurrentStateReservation() {
            return this.currentStateReservation;
         }

         public void setCurrentStateReservation(HotelReservationType value) {
            this.currentStateReservation = value;
         }
      }
   }
}
