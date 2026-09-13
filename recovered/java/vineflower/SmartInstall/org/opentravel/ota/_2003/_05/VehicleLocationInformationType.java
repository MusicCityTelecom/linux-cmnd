package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VehicleLocationInformationType")
public class VehicleLocationInformationType extends FormattedTextType {
   @XmlAttribute(name = "Type", required = true)
   protected String type;

   public String getType() {
      return this.type;
   }

   public void setType(String value) {
      this.type = value;
   }
}
