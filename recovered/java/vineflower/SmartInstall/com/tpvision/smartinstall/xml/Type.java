package com.tpvision.smartinstall.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "type")
public class Type {
   @XmlAttribute(name = "maxSize", required = true)
   @XmlSchemaType(name = "anySimpleType")
   protected String maxSize;
   @XmlAttribute(name = "name", required = true)
   @XmlSchemaType(name = "anySimpleType")
   protected String name;

   public String getMaxSize() {
      return this.maxSize;
   }

   public void setMaxSize(String value) {
      this.maxSize = value;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String value) {
      this.name = value;
   }
}
