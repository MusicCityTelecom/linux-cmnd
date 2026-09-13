package org.htng._2011b;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.UniqueIDType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = "wakeupID")
@XmlRootElement(name = "HTNG_WakeupSchedulingNotifRS")
public class HTNGWakeupSchedulingNotifRS extends HTNGResponseBaseType {
   @XmlElement(name = "WakeupID")
   protected UniqueIDType wakeupID;

   public UniqueIDType getWakeupID() {
      return this.wakeupID;
   }

   public void setWakeupID(UniqueIDType value) {
      this.wakeupID = value;
   }
}
