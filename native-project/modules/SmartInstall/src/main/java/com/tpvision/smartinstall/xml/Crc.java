package com.tpvision.smartinstall.xml;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "crc")
public class Crc {
   @XmlAttribute(name = "lineNo", required = true)
   protected BigInteger lineNo;
   @XmlAttribute(name = "ofFile", required = true)
   @XmlSchemaType(name = "anySimpleType")
   protected String ofFile;
   @XmlAttribute(name = "prefix", required = true)
   protected String prefix;

   public BigInteger getLineNo() {
      return this.lineNo;
   }

   public void setLineNo(BigInteger value) {
      this.lineNo = value;
   }

   public String getOfFile() {
      return this.ofFile;
   }

   public void setOfFile(String value) {
      this.ofFile = value;
   }

   public String getPrefix() {
      return this.prefix;
   }

   public void setPrefix(String value) {
      this.prefix = value;
   }
}
