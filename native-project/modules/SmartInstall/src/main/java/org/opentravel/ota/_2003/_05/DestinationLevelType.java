package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "DestinationLevelType")
@XmlEnum
public enum DestinationLevelType {
   @XmlEnumValue("Continent")
   CONTINENT("Continent"),
   @XmlEnumValue("Country")
   COUNTRY("Country"),
   @XmlEnumValue("State")
   STATE("State"),
   @XmlEnumValue("Area")
   AREA("Area"),
   @XmlEnumValue("SubArea")
   SUB_AREA("SubArea"),
   @XmlEnumValue("Resort")
   RESORT("Resort"),
   @XmlEnumValue("District")
   DISTRICT("District"),
   @XmlEnumValue("Region")
   REGION("Region");

   private final String value;

   DestinationLevelType(String v) {
      this.value = v;
   }

   public String value() {
      return this.value;
   }

   public static DestinationLevelType fromValue(String v) {
      for (DestinationLevelType c : values()) {
         if (c.value.equals(v)) {
            return c;
         }
      }

      throw new IllegalArgumentException(v);
   }
}
