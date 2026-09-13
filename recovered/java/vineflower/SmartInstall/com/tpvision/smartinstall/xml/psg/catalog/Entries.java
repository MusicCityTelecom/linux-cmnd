package com.tpvision.smartinstall.xml.psg.catalog;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = "catalogEntry")
@XmlRootElement(name = "Entries")
public class Entries {
   @XmlElement(name = "CatalogEntry", required = true)
   protected List<CatalogEntry> catalogEntry;

   public List<CatalogEntry> getCatalogEntry() {
      if (null == this.catalogEntry) {
         this.catalogEntry = new ArrayList<>();
      }

      return this.catalogEntry;
   }
}
