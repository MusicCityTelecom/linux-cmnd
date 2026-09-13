package org.htng._2011b;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "HTNG_QueryResultListType")
@XmlEnum
public enum HTNGQueryResultListType {
   @XmlEnumValue("All Ascending")
   ALL_ASCENDING("All Ascending"),
   @XmlEnumValue("All Descending")
   ALL_DESCENDING("All Descending"),
   @XmlEnumValue("Top Ascending")
   TOP_ASCENDING("Top Ascending"),
   @XmlEnumValue("Top Descending")
   TOP_DESCENDING("Top Descending"),
   @XmlEnumValue("Bytes")
   BYTES("Bytes");

   private final String value;

   HTNGQueryResultListType(String v) {
      this.value = v;
   }

   public String value() {
      return this.value;
   }

   public static HTNGQueryResultListType fromValue(String v) {
      for (HTNGQueryResultListType c : values()) {
         if (c.value.equals(v)) {
            return c;
         }
      }

      throw new IllegalArgumentException(v);
   }
}
