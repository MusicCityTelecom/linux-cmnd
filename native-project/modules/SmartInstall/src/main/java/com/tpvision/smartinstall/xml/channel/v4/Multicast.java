package com.tpvision.smartinstall.xml.channel.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "Multicast")
public class Multicast {
   @XmlAttribute(name = "url", required = true)
   protected String url;

   public String getUrl() {
      return this.url;
   }

   public void setUrl(String value) {
      this.url = value;
   }
}
