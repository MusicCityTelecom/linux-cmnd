package com.tpvision.smartinstall.xml.channel.v4;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = "application")
@XmlRootElement(name = "ApplicationMap")
public class ApplicationMap {
   @XmlElement(name = "Application", required = true)
   protected List<Application> application;

   public List<Application> getApplication() {
      if (null == this.application) {
         this.application = new ArrayList<>();
      }

      return this.application;
   }
}
