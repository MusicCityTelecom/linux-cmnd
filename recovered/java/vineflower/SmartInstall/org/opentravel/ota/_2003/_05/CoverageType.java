package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CoverageType", propOrder = "details")
public class CoverageType {
   @XmlElement(name = "Details")
   protected List<CoverageDetailsType> details;
   @XmlAttribute(name = "CoverageType", required = true)
   protected String coverageType;
   @XmlAttribute(name = "Code")
   protected String code;

   public List<CoverageDetailsType> getDetails() {
      if (this.details == null) {
         this.details = new ArrayList<>();
      }

      return this.details;
   }

   public String getCoverageType() {
      return this.coverageType;
   }

   public void setCoverageType(String value) {
      this.coverageType = value;
   }

   public String getCode() {
      return this.code;
   }

   public void setCode(String value) {
      this.code = value;
   }
}
