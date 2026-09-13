package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CoverageDetailsType")
public class CoverageDetailsType extends FormattedTextTextType {
   @XmlAttribute(name = "CoverageTextType", required = true)
   protected CoverageTextType coverageTextType;

   public CoverageTextType getCoverageTextType() {
      return this.coverageTextType;
   }

   public void setCoverageTextType(CoverageTextType value) {
      this.coverageTextType = value;
   }
}
