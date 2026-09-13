package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DescriptionType")
public class DescriptionType extends ParagraphType {
   @XmlAttribute(name = "Location")
   protected Boolean location;
   @XmlAttribute(name = "RefDirectionTo")
   protected Boolean refDirectionTo;

   public Boolean isLocation() {
      return this.location;
   }

   public void setLocation(Boolean value) {
      this.location = value;
   }

   public Boolean isRefDirectionTo() {
      return this.refDirectionTo;
   }

   public void setRefDirectionTo(Boolean value) {
      this.refDirectionTo = value;
   }
}
