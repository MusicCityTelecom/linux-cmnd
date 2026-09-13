package com.tpvision.smartinstall.xml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = "type")
@XmlRootElement(name = "themeTVImageSpec")
public class ThemeTVImageSpec {
   @XmlElement(required = true)
   protected List<Type> type;

   public List<Type> getType() {
      if (null == this.type) {
         this.type = new ArrayList<>();
      }

      return this.type;
   }
}
