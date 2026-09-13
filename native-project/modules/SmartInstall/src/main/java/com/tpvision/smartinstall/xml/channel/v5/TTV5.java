package com.tpvision.smartinstall.xml.channel.v5;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "TTV5")
public class TTV5 {
   @XmlAttribute(name = "Name", required = true)
   @XmlSchemaType(name = "anySimpleType")
   protected String name;
   @XmlAttribute(name = "icon", required = true)
   @XmlSchemaType(name = "anySimpleType")
   protected String icon;

   public String getName() {
      return this.name;
   }

   public void setName(String value) {
      this.name = value;
   }

   public String getIcon() {
      return this.icon;
   }

   public void setIcon(String value) {
      this.icon = value;
   }
}
