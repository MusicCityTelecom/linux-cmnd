package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AdditionalDetailsType", propOrder = "additionalDetail")
public class AdditionalDetailsType {
   @XmlElement(name = "AdditionalDetail", required = true)
   protected List<AdditionalDetailType> additionalDetail;

   public List<AdditionalDetailType> getAdditionalDetail() {
      if (this.additionalDetail == null) {
         this.additionalDetail = new ArrayList<>();
      }

      return this.additionalDetail;
   }
}
