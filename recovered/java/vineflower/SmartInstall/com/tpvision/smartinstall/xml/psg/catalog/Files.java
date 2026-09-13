package com.tpvision.smartinstall.xml.psg.catalog;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = "string")
@XmlRootElement(name = "Files")
public class Files {
   @XmlElement(required = true)
   protected List<String> string;

   public List<String> getString() {
      if (null == this.string) {
         this.string = new ArrayList<>();
      }

      return this.string;
   }
}
