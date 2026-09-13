package com.tpvision.smartinstall.xml.channel.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = "setup")
@XmlRootElement(name = "Application")
public class Application {
   @XmlElement(name = "Setup", required = true)
   protected Setup setup;

   public Setup getSetup() {
      return this.setup;
   }

   public void setSetup(Setup value) {
      this.setup = value;
   }
}
