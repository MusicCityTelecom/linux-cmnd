package com.tpvision.smartinstall.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "dateFormat")
public class DateFormat {
   @XmlAttribute(name = "format", required = true)
   @XmlSchemaType(name = "anySimpleType")
   protected String format;

   public String getFormat() {
      return this.format;
   }

   public void setFormat(String value) {
      this.format = value;
   }
}
