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
@XmlType(name = "RailReservationType", propOrder = {"uniqueID", "itinerary", "passengerInfo", "paymentRules", "fulfillment", "tpaExtensions"})
public class RailReservationType {
   @XmlElement(name = "UniqueID", required = true)
   protected UniqueIDType uniqueID;
   @XmlElement(name = "Itinerary", required = true)
   protected RailReservationType.Itinerary itinerary;
   @XmlElement(name = "PassengerInfo")
   protected List<RailPassengerCategoryDetailType> passengerInfo;
   @XmlElement(name = "PaymentRules")
   protected RailReservationType.PaymentRules paymentRules;
   @XmlElement(name = "Fulfillment")
   protected CompanyNameType fulfillment;
   @XmlElement(name = "TPA_Extensions")
   protected TPAExtensionsType tpaExtensions;
   @XmlAttribute(name = "LastHoldDate")
   @XmlSchemaType(name = "date")
   protected XMLGregorianCalendar lastHoldDate;

   public UniqueIDType getUniqueID() {
      return this.uniqueID;
   }

   public void setUniqueID(UniqueIDType value) {
      this.uniqueID = value;
   }

   public RailReservationType.Itinerary getItinerary() {
      return this.itinerary;
   }

   public void setItinerary(RailReservationType.Itinerary value) {
      this.itinerary = value;
   }

   public List<RailPassengerCategoryDetailType> getPassengerInfo() {
      if (this.passengerInfo == null) {
         this.passengerInfo = new ArrayList<>();
      }

      return this.passengerInfo;
   }

   public RailReservationType.PaymentRules getPaymentRules() {
      return this.paymentRules;
   }

   public void setPaymentRules(RailReservationType.PaymentRules value) {
      this.paymentRules = value;
   }

   public CompanyNameType getFulfillment() {
      return this.fulfillment;
   }

   public void setFulfillment(CompanyNameType value) {
      this.fulfillment = value;
   }

   public TPAExtensionsType getTPAExtensions() {
      return this.tpaExtensions;
   }

   public void setTPAExtensions(TPAExtensionsType value) {
      this.tpaExtensions = value;
   }

   public XMLGregorianCalendar getLastHoldDate() {
      return this.lastHoldDate;
   }

   public void setLastHoldDate(XMLGregorianCalendar value) {
      this.lastHoldDate = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = {"originAndDestination", "railCharges", "vendorMessages"})
   public static class Itinerary {
      @XmlElement(name = "OriginAndDestination", required = true)
      protected List<RailReservationType.Itinerary.OriginAndDestination> originAndDestination;
      @XmlElement(name = "RailCharges")
      protected RailChargesType railCharges;
      @XmlElement(name = "VendorMessages")
      protected VendorMessagesType vendorMessages;

      public List<RailReservationType.Itinerary.OriginAndDestination> getOriginAndDestination() {
         if (this.originAndDestination == null) {
            this.originAndDestination = new ArrayList<>();
         }

         return this.originAndDestination;
      }

      public RailChargesType getRailCharges() {
         return this.railCharges;
      }

      public void setRailCharges(RailChargesType value) {
         this.railCharges = value;
      }

      public VendorMessagesType getVendorMessages() {
         return this.vendorMessages;
      }

      public void setVendorMessages(VendorMessagesType value) {
         this.vendorMessages = value;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = {"originLocation", "destinationLocation", "trainSegment"})
      public static class OriginAndDestination {
         @XmlElement(name = "OriginLocation", required = true)
         protected LocationType originLocation;
         @XmlElement(name = "DestinationLocation", required = true)
         protected LocationType destinationLocation;
         @XmlElement(name = "TrainSegment", required = true)
         protected List<BookedTrainSegmentType> trainSegment;

         public LocationType getOriginLocation() {
            return this.originLocation;
         }

         public void setOriginLocation(LocationType value) {
            this.originLocation = value;
         }

         public LocationType getDestinationLocation() {
            return this.destinationLocation;
         }

         public void setDestinationLocation(LocationType value) {
            this.destinationLocation = value;
         }

         public List<BookedTrainSegmentType> getTrainSegment() {
            if (this.trainSegment == null) {
               this.trainSegment = new ArrayList<>();
            }

            return this.trainSegment;
         }
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "paymentRule")
   public static class PaymentRules {
      @XmlElement(name = "PaymentRule", required = true)
      protected List<MonetaryRuleType> paymentRule;

      public List<MonetaryRuleType> getPaymentRule() {
         if (this.paymentRule == null) {
            this.paymentRule = new ArrayList<>();
         }

         return this.paymentRule;
      }
   }
}
