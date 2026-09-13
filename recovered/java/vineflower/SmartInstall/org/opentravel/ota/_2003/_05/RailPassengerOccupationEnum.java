package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "RailPassengerOccupationEnum")
@XmlEnum
public enum RailPassengerOccupationEnum {
   @XmlEnumValue("NotSignificant")
   NOT_SIGNIFICANT("NotSignificant"),
   @XmlEnumValue("RailEmployee")
   RAIL_EMPLOYEE("RailEmployee"),
   @XmlEnumValue("GovernmentEmployee")
   GOVERNMENT_EMPLOYEE("GovernmentEmployee"),
   @XmlEnumValue("Farmer")
   FARMER("Farmer"),
   @XmlEnumValue("Military")
   MILITARY("Military"),
   @XmlEnumValue("Journalist")
   JOURNALIST("Journalist"),
   @XmlEnumValue("Student")
   STUDENT("Student"),
   VIP("VIP"),
   @XmlEnumValue("Other_")
   OTHER("Other_");

   private final String value;

   RailPassengerOccupationEnum(String v) {
      this.value = v;
   }

   public String value() {
      return this.value;
   }

   public static RailPassengerOccupationEnum fromValue(String v) {
      for (RailPassengerOccupationEnum c : values()) {
         if (c.value.equals(v)) {
            return c;
         }
      }

      throw new IllegalArgumentException(v);
   }
}
