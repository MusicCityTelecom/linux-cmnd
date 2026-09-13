package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VendorMessageType")
public class VendorMessageType extends FormattedTextType {
   @XmlAttribute(name = "InfoType", required = true)
   protected String infoType;

   public String getInfoType() {
      return this.infoType;
   }

   public void setInfoType(String value) {
      this.infoType = value;
   }
}
