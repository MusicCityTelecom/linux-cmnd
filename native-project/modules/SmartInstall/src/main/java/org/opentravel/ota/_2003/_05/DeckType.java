package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "DeckType")
@XmlEnum
public enum DeckType {
   @XmlEnumValue("Regular-OneLevelOnly")
   REGULAR_ONE_LEVEL_ONLY("Regular-OneLevelOnly"),
   @XmlEnumValue("LowerLevel")
   LOWER_LEVEL("LowerLevel"),
   @XmlEnumValue("UpperLevel")
   UPPER_LEVEL("UpperLevel");

   private final String value;

   DeckType(String v) {
      this.value = v;
   }

   public String value() {
      return this.value;
   }

   public static DeckType fromValue(String v) {
      for (DeckType c : values()) {
         if (c.value.equals(v)) {
            return c;
         }
      }

      throw new IllegalArgumentException(v);
   }
}
