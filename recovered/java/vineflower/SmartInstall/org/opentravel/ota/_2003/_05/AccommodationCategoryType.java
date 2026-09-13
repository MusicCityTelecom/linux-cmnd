package org.opentravel.ota._2003._05;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AccommodationCategoryType", propOrder = {"accommodation", "ancillaryService"})
public class AccommodationCategoryType {
   @XmlElement(name = "Accommodation")
   protected List<AccommodationCategoryType.Accommodation> accommodation;
   @XmlElement(name = "AncillaryService")
   protected List<AncillaryService> ancillaryService;

   public List<AccommodationCategoryType.Accommodation> getAccommodation() {
      if (this.accommodation == null) {
         this.accommodation = new ArrayList<>();
      }

      return this.accommodation;
   }

   public List<AncillaryService> getAncillaryService() {
      if (this.ancillaryService == null) {
         this.ancillaryService = new ArrayList<>();
      }

      return this.ancillaryService;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class Accommodation extends AccommodationType {
      @XmlAttribute(name = "Quantity")
      @XmlSchemaType(name = "positiveInteger")
      protected BigInteger quantity;

      public BigInteger getQuantity() {
         return this.quantity;
      }

      public void setQuantity(BigInteger value) {
         this.quantity = value;
      }
   }
}
