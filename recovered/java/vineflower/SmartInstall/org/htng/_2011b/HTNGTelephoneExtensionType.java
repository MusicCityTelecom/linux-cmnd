package org.htng._2011b;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HTNG_TelephoneExtensionType", propOrder = "telephoneExtention")
public class HTNGTelephoneExtensionType {
   @XmlElement(name = "TelephoneExtention", required = true)
   protected List<String> telephoneExtention;

   public List<String> getTelephoneExtention() {
      if (this.telephoneExtention == null) {
         this.telephoneExtention = new ArrayList<>();
      }

      return this.telephoneExtention;
   }
}
