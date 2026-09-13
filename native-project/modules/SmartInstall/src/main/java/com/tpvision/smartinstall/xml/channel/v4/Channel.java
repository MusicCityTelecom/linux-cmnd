package com.tpvision.smartinstall.xml.channel.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"broadcast", "multicast", "setup"})
@XmlRootElement(name = "Channel")
public class Channel {
   @XmlElement(name = "Broadcast")
   protected Broadcast broadcast;
   @XmlElement(name = "Multicast")
   protected Multicast multicast;
   @XmlElement(name = "Setup", required = true)
   protected Setup setup;

   public Broadcast getBroadcast() {
      return this.broadcast;
   }

   public void setBroadcast(Broadcast value) {
      this.broadcast = value;
   }

   public Multicast getMulticast() {
      return this.multicast;
   }

   public void setMulticast(Multicast value) {
      this.multicast = value;
   }

   public Setup getSetup() {
      return this.setup;
   }

   public void setSetup(Setup value) {
      this.setup = value;
   }
}
