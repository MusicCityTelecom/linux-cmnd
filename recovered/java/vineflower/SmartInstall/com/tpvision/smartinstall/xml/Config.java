package com.tpvision.smartinstall.xml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = "platform")
@XmlRootElement(name = "config")
public class Config {
   @XmlElement(required = true)
   protected List<Platform> platform;
   @XmlAttribute(name = "siver", required = true)
   protected String siver;

   public List<Platform> getPlatform() {
      if (null == this.platform) {
         this.platform = new ArrayList<>();
      }

      return this.platform;
   }

   public String getSiver() {
      return this.siver;
   }

   public void setSiver(String value) {
      this.siver = value;
   }
}
