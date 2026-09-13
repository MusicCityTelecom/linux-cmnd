package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CruiseProfileType", propOrder = "cruiseProfile")
public class CruiseProfileType {
   @XmlElement(name = "CruiseProfile", required = true)
   protected List<CruiseProfileType.CruiseProfile> cruiseProfile;
   @XmlAttribute(name = "ProfileTypeIdentifier", required = true)
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   protected String profileTypeIdentifier;

   public List<CruiseProfileType.CruiseProfile> getCruiseProfile() {
      if (this.cruiseProfile == null) {
         this.cruiseProfile = new ArrayList<>();
      }

      return this.cruiseProfile;
   }

   public String getProfileTypeIdentifier() {
      return this.profileTypeIdentifier;
   }

   public void setProfileTypeIdentifier(String value) {
      this.profileTypeIdentifier = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class CruiseProfile {
      @XmlAttribute(name = "Code")
      protected String code;
      @XmlAttribute(name = "MaxQuantity")
      protected String maxQuantity;

      public String getCode() {
         return this.code;
      }

      public void setCode(String value) {
         this.code = value;
      }

      public String getMaxQuantity() {
         return this.maxQuantity;
      }

      public void setMaxQuantity(String value) {
         this.maxQuantity = value;
      }
   }
}
