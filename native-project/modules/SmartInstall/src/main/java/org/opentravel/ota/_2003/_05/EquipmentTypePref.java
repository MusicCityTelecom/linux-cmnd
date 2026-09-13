package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EquipmentTypePref")
public class EquipmentTypePref extends EquipmentType {
   @XmlAttribute(name = "WideBody")
   protected Boolean wideBody;
   @XmlAttribute(name = "PreferLevel")
   protected PreferLevelType preferLevel;

   public Boolean isWideBody() {
      return this.wideBody;
   }

   public void setWideBody(Boolean value) {
      this.wideBody = value;
   }

   public PreferLevelType getPreferLevel() {
      return this.preferLevel;
   }

   public void setPreferLevel(PreferLevelType value) {
      this.preferLevel = value;
   }
}
