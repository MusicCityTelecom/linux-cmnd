package com.tpvision.smartinstall.xml.psg.catalog;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = "entries")
@XmlRootElement(name = "Catalog")
public class Catalog {
   @XmlElement(name = "Entries", required = true)
   protected Entries entries;

   public Entries getEntries() {
      return this.entries;
   }

   public void setEntries(Entries value) {
      this.entries = value;
   }
}
