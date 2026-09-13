package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContactInfosType", propOrder = "contactInfo")
public class ContactInfosType {
   @XmlElement(name = "ContactInfo")
   protected List<ContactInfoRootType> contactInfo;

   public List<ContactInfoRootType> getContactInfo() {
      if (this.contactInfo == null) {
         this.contactInfo = new ArrayList<>();
      }

      return this.contactInfo;
   }
}
