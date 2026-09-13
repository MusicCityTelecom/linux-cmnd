package com.tpvision.smartinstall.xml.setting.v2k16;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"name", "value", "cloneIn"})
@XmlRootElement(name = "item")
public class Item {
   @XmlElement(name = "Name", required = true)
   protected String name;
   @XmlElement(name = "Value", required = true)
   protected String value;
   @XmlElement(name = "CloneIn", required = true)
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   @XmlSchemaType(name = "NCName")
   protected String cloneIn;

   public String getName() {
      return this.name;
   }

   public void setName(String value) {
      this.name = value;
   }

   public String getValue() {
      return this.value;
   }

   public void setValue(String value) {
      this.value = value;
   }

   public String getCloneIn() {
      return this.cloneIn;
   }

   public void setCloneIn(String value) {
      this.cloneIn = value;
   }
}
