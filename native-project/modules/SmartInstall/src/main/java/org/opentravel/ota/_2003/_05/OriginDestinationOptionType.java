package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OriginDestinationOptionType", propOrder = "flightSegment")
@XmlSeeAlso(AirItineraryType.OriginDestinationOptions.OriginDestinationOption.class)
public class OriginDestinationOptionType {
   @XmlElement(name = "FlightSegment", required = true)
   protected List<OriginDestinationOptionType.FlightSegment> flightSegment;

   public List<OriginDestinationOptionType.FlightSegment> getFlightSegment() {
      if (this.flightSegment == null) {
         this.flightSegment = new ArrayList<>();
      }

      return this.flightSegment;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "tpaExtensions")
   public static class FlightSegment extends BookFlightSegmentType {
      @XmlElement(name = "TPA_Extensions")
      protected TPAExtensionsType tpaExtensions;

      public TPAExtensionsType getTPAExtensions() {
         return this.tpaExtensions;
      }

      public void setTPAExtensions(TPAExtensionsType value) {
         this.tpaExtensions = value;
      }
   }
}
