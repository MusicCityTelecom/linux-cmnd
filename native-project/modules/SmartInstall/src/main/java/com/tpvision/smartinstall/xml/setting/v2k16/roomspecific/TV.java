package com.tpvision.smartinstall.xml.setting.v2k16.roomspecific;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"serialNumber", "item"})
@XmlRootElement(name = "TV")
public class TV {
   @XmlElement(name = "SerialNumber", required = true)
   protected String serialNumber;
   @XmlElement(required = true)
   protected List<Item> item;

   public String getSerialNumber() {
      return this.serialNumber;
   }

   public void setSerialNumber(String value) {
      this.serialNumber = value;
   }

   public List<Item> getItem() {
      if (null == this.item) {
         this.item = new ArrayList<>();
      }

      return this.item;
   }
}
