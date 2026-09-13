package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "FareAccessPrefType")
@XmlEnum
public enum FareAccessPrefType {
   @XmlEnumValue("PointToPoint")
   POINT_TO_POINT("PointToPoint"),
   @XmlEnumValue("Through")
   THROUGH("Through"),
   @XmlEnumValue("Joint")
   JOINT("Joint"),
   @XmlEnumValue("Private")
   PRIVATE("Private"),
   @XmlEnumValue("Negotiated")
   NEGOTIATED("Negotiated"),
   @XmlEnumValue("Net")
   NET("Net"),
   @XmlEnumValue("Historical")
   HISTORICAL("Historical"),
   @XmlEnumValue("SecurateAir")
   SECURATE_AIR("SecurateAir"),
   @XmlEnumValue("Moneysaver")
   MONEYSAVER("Moneysaver"),
   @XmlEnumValue("MoneysaverRoundtrip")
   MONEYSAVER_ROUNDTRIP("MoneysaverRoundtrip"),
   @XmlEnumValue("MoneysaverNoOneWay")
   MONEYSAVER_NO_ONE_WAY("MoneysaverNoOneWay"),
   @XmlEnumValue("MoneysaverOneWayOnly")
   MONEYSAVER_ONE_WAY_ONLY("MoneysaverOneWayOnly");

   private final String value;

   FareAccessPrefType(String v) {
      this.value = v;
   }

   public String value() {
      return this.value;
   }

   public static FareAccessPrefType fromValue(String v) {
      for (FareAccessPrefType c : values()) {
         if (c.value.equals(v)) {
            return c;
         }
      }

      throw new IllegalArgumentException(v);
   }
}
