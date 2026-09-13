package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MultimediaDescriptionsType", propOrder = "multimediaDescription")
@XmlSeeAlso(
   {
         AreaInfoType.Recreations.Recreation.MultimediaDescriptions.class,
         HotelInfoType.Descriptions.MultimediaDescriptions.class,
         RestaurantType.MultimediaDescriptions.class
   }
)
public class MultimediaDescriptionsType {
   @XmlElement(name = "MultimediaDescription", required = true)
   protected List<MultimediaDescriptionType> multimediaDescription;
   @XmlAttribute(name = "LastUpdated")
   @XmlSchemaType(name = "dateTime")
   protected XMLGregorianCalendar lastUpdated;

   public List<MultimediaDescriptionType> getMultimediaDescription() {
      if (this.multimediaDescription == null) {
         this.multimediaDescription = new ArrayList<>();
      }

      return this.multimediaDescription;
   }

   public XMLGregorianCalendar getLastUpdated() {
      return this.lastUpdated;
   }

   public void setLastUpdated(XMLGregorianCalendar value) {
      this.lastUpdated = value;
   }
}
