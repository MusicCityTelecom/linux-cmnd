package org.opentravel.ota._2003._05;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SpecialRequestType", propOrder = "specialRequest")
public class SpecialRequestType {
   @XmlElement(name = "SpecialRequest", required = true)
   protected List<SpecialRequestType.SpecialRequest> specialRequest;

   public List<SpecialRequestType.SpecialRequest> getSpecialRequest() {
      if (this.specialRequest == null) {
         this.specialRequest = new ArrayList<>();
      }

      return this.specialRequest;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class SpecialRequest extends ParagraphType {
      @XmlAttribute(name = "RequestCode")
      protected String requestCode;
      @XmlAttribute(name = "CodeContext")
      protected String codeContext;
      @XmlAttribute(name = "NumberOfUnits")
      protected BigInteger numberOfUnits;

      public String getRequestCode() {
         return this.requestCode;
      }

      public void setRequestCode(String value) {
         this.requestCode = value;
      }

      public String getCodeContext() {
         return this.codeContext;
      }

      public void setCodeContext(String value) {
         this.codeContext = value;
      }

      public BigInteger getNumberOfUnits() {
         return this.numberOfUnits;
      }

      public void setNumberOfUnits(BigInteger value) {
         this.numberOfUnits = value;
      }
   }
}
