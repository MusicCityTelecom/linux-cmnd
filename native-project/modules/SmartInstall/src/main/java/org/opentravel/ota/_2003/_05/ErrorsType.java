package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ErrorsType", propOrder = "error")
public class ErrorsType {
   @XmlElement(name = "Error", required = true)
   protected List<ErrorType> error;

   public List<ErrorType> getError() {
      if (this.error == null) {
         this.error = new ArrayList<>();
      }

      return this.error;
   }
}
