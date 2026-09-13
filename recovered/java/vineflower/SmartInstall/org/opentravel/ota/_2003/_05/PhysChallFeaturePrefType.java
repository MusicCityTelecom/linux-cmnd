package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PhysChallFeaturePrefType", propOrder = "value")
public class PhysChallFeaturePrefType {
   @XmlValue
   protected String value;
   @XmlAttribute(name = "PhysChallFeatureType")
   protected String physChallFeatureType;
   @XmlAttribute(name = "PreferLevel")
   protected PreferLevelType preferLevel;

   public String getValue() {
      return this.value;
   }

   public void setValue(String value) {
      this.value = value;
   }

   public String getPhysChallFeatureType() {
      return this.physChallFeatureType;
   }

   public void setPhysChallFeatureType(String value) {
      this.physChallFeatureType = value;
   }

   public PreferLevelType getPreferLevel() {
      return this.preferLevel;
   }

   public void setPreferLevel(PreferLevelType value) {
      this.preferLevel = value;
   }
}
