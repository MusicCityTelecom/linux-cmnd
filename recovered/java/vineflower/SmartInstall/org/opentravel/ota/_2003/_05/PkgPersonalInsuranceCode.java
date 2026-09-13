package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "PkgPersonalInsuranceCode")
@XmlEnum
public enum PkgPersonalInsuranceCode {
   @XmlEnumValue("Ski")
   SKI("Ski"),
   @XmlEnumValue("Worldwide")
   WORLDWIDE("Worldwide"),
   @XmlEnumValue("Europe")
   EUROPE("Europe");

   private final String value;

   PkgPersonalInsuranceCode(String v) {
      this.value = v;
   }

   public String value() {
      return this.value;
   }

   public static PkgPersonalInsuranceCode fromValue(String v) {
      for (PkgPersonalInsuranceCode c : values()) {
         if (c.value.equals(v)) {
            return c;
         }
      }

      throw new IllegalArgumentException(v);
   }
}
