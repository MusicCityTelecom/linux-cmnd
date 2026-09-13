package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FormattedTextTextType", propOrder = "value")
@XmlSeeAlso(
   {
         ImageDescriptionType.Description.class,
         TextDescriptionType.Description.class,
         FulfillmentType.PaymentText.class,
         TravelerInfoType.AirTraveler.Comment.class,
         CoverageDetailsType.class,
         VehicleRentalDetailsType.ConditionReport.class,
         RateQualifierType.RateComments.RateComment.class,
         ParagraphType.ListItem.class,
         PkgCautionType.class
   }
)
public class FormattedTextTextType {
   @XmlValue
   protected String value;
   @XmlAttribute(name = "Formatted")
   protected Boolean formatted;
   @XmlAttribute(name = "TextFormat")
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   protected String textFormat;
   @XmlAttribute(name = "Language")
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   @XmlSchemaType(name = "language")
   protected String language;

   public String getValue() {
      return this.value;
   }

   public void setValue(String value) {
      this.value = value;
   }

   public Boolean isFormatted() {
      return this.formatted;
   }

   public void setFormatted(Boolean value) {
      this.formatted = value;
   }

   public String getTextFormat() {
      return this.textFormat;
   }

   public void setTextFormat(String value) {
      this.textFormat = value;
   }

   public String getLanguage() {
      return this.language;
   }

   public void setLanguage(String value) {
      this.language = value;
   }
}
