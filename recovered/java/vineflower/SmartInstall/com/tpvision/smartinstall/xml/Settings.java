package com.tpvision.smartinstall.xml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = "setting")
@XmlRootElement(name = "settings")
public class Settings {
   protected List<Setting> setting;

   public List<Setting> getSetting() {
      if (null == this.setting) {
         this.setting = new ArrayList<>();
      }

      return this.setting;
   }
}
