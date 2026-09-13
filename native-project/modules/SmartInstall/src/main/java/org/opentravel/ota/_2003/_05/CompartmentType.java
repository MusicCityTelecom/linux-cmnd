package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CompartmentType", propOrder = "value")
@XmlSeeAlso(RailAccommDetailType.Compartment.class)
public class CompartmentType {
   @XmlValue
   protected CompartmentTypeEnum value;
   @XmlAttribute(name = "extension")
   protected String extension;

   public CompartmentTypeEnum getValue() {
      return this.value;
   }

   public void setValue(CompartmentTypeEnum value) {
      this.value = value;
   }

   public String getExtension() {
      return this.extension;
   }

   public void setExtension(String value) {
      this.extension = value;
   }
}
