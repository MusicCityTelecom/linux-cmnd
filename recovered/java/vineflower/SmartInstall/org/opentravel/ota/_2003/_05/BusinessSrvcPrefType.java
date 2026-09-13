package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BusinessSrvcPrefType", propOrder = "value")
public class BusinessSrvcPrefType {
   @XmlValue
   protected String value;
   @XmlAttribute(name = "BusinessSrvcType")
   protected String businessSrvcType;
   @XmlAttribute(name = "PreferLevel")
   protected PreferLevelType preferLevel;

   public String getValue() {
      return this.value;
   }

   public void setValue(String value) {
      this.value = value;
   }

   public String getBusinessSrvcType() {
      return this.businessSrvcType;
   }

   public void setBusinessSrvcType(String value) {
      this.businessSrvcType = value;
   }

   public PreferLevelType getPreferLevel() {
      return this.preferLevel;
   }

   public void setPreferLevel(PreferLevelType value) {
      this.preferLevel = value;
   }
}
