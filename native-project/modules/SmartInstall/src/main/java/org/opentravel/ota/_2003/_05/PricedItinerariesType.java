package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PricedItinerariesType", propOrder = "pricedItinerary")
public class PricedItinerariesType {
   @XmlElement(name = "PricedItinerary", required = true)
   protected List<PricedItinerariesType.PricedItinerary> pricedItinerary;

   public List<PricedItinerariesType.PricedItinerary> getPricedItinerary() {
      if (this.pricedItinerary == null) {
         this.pricedItinerary = new ArrayList<>();
      }

      return this.pricedItinerary;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class PricedItinerary extends PricedItineraryType {
      @XmlAttribute(name = "OriginDestinationRefNumber")
      protected Integer originDestinationRefNumber;

      public Integer getOriginDestinationRefNumber() {
         return this.originDestinationRefNumber;
      }

      public void setOriginDestinationRefNumber(Integer value) {
         this.originDestinationRefNumber = value;
      }
   }
}
