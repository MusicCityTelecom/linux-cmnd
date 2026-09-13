package com.tpvision.smartinstall.xml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = "child")
@XmlRootElement(name = "childVariants")
public class ChildVariants {
   @XmlElement(required = true)
   protected List<Child> child;

   public List<Child> getChild() {
      if (null == this.child) {
         this.child = new ArrayList<>();
      }

      return this.child;
   }
}
