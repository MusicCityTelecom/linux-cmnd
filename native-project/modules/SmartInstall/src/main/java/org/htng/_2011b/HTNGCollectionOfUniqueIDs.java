package org.htng._2011b;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.UniqueIDType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HTNG_CollectionOfUniqueIDs", propOrder = "uniqueID")
public class HTNGCollectionOfUniqueIDs {
   @XmlElement(name = "UniqueID", required = true)
   protected UniqueIDType uniqueID;

   public UniqueIDType getUniqueID() {
      return this.uniqueID;
   }

   public void setUniqueID(UniqueIDType value) {
      this.uniqueID = value;
   }
}
