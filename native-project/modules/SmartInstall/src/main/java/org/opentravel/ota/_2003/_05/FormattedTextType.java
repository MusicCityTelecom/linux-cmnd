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
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FormattedTextType", propOrder = "subSection")
@XmlSeeAlso(
   {
         VendorMessageType.class,
         VehicleAdditionalDriverRequirementsType.AddlDriverInfos.AddlDriverInfo.class,
         VehicleAgeRequirementsType.Age.AgeInfos.AgeInfo.class,
         VehicleLocationAdditionalDetailsType.Shuttle.ShuttleInfos.ShuttleInfo.class,
         VehicleLocationInformationType.class,
         VehicleLocationVehiclesType.VehicleInfos.VehicleInfo.class
   }
)
public class FormattedTextType {
   @XmlElement(name = "SubSection", required = true)
   protected List<FormattedTextSubSectionType> subSection;
   @XmlAttribute(name = "Title")
   protected String title;
   @XmlAttribute(name = "Language")
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   @XmlSchemaType(name = "language")
   protected String language;

   public List<FormattedTextSubSectionType> getSubSection() {
      if (this.subSection == null) {
         this.subSection = new ArrayList<>();
      }

      return this.subSection;
   }

   public String getTitle() {
      return this.title;
   }

   public void setTitle(String value) {
      this.title = value;
   }

   public String getLanguage() {
      return this.language;
   }

   public void setLanguage(String value) {
      this.language = value;
   }
}
