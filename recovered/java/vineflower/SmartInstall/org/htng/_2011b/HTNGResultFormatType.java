package org.htng._2011b;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "HTNG_ResultFormatType")
@XmlEnum
public enum HTNGResultFormatType {
   XML("XML"),
   CSV("CSV"),
   @XmlEnumValue("PlainText")
   PLAIN_TEXT("PlainText"),
   @XmlEnumValue("Base64Binary")
   BASE_64_BINARY("Base64Binary");

   private final String value;

   HTNGResultFormatType(String v) {
      this.value = v;
   }

   public String value() {
      return this.value;
   }

   public static HTNGResultFormatType fromValue(String v) {
      for (HTNGResultFormatType c : values()) {
         if (c.value.equals(v)) {
            return c;
         }
      }

      throw new IllegalArgumentException(v);
   }
}
