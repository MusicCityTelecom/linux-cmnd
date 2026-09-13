package com.tpvision.smartinstall.soap.mychoice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfInteger")
public class ArrayOfInteger extends Array {
   public List<Integer> getIntegerList() {
      List<Integer> integerList = new ArrayList<>();

      for (Object obj : this.getAny()) {
         integerList.add(Integer.parseInt(((Element)obj).getChildNodes().item(0).getNodeValue()));
      }

      return integerList;
   }
}
