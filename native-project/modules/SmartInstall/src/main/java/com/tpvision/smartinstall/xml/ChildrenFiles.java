package com.tpvision.smartinstall.xml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = "file")
@XmlRootElement(name = "childrenFiles")
public class ChildrenFiles {
   @XmlElement(required = true)
   protected List<File> file;

   public List<File> getFile() {
      if (null == this.file) {
         this.file = new ArrayList<>();
      }

      return this.file;
   }
}
