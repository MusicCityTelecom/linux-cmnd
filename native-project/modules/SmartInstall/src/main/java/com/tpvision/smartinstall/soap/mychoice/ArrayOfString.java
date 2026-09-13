package com.tpvision.smartinstall.soap.mychoice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfString")
public class ArrayOfString extends Array {
   public List<String> getStringList() {
      List<String> stringList = new ArrayList<>();

      for (Object obj : this.getAny()) {
         stringList.add(String.valueOf(((Element)obj).getChildNodes().item(0).getNodeValue()));
      }

      return stringList;
   }
}
