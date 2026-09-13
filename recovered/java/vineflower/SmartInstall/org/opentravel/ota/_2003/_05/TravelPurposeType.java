package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TravelPurposeType", propOrder = "value")
public class TravelPurposeType {
   @XmlValue
   protected TravelPurposeEnum value;
   @XmlAttribute(name = "extension")
   protected String extension;

   public TravelPurposeEnum getValue() {
      return this.value;
   }

   public void setValue(TravelPurposeEnum value) {
      this.value = value;
   }

   public String getExtension() {
      return this.extension;
   }

   public void setExtension(String value) {
      this.extension = value;
   }
}
