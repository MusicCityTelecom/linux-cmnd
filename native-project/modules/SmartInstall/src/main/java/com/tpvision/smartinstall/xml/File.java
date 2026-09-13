package com.tpvision.smartinstall.xml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = "crc")
@XmlRootElement(name = "file")
public class File {
   protected List<Crc> crc;
   @XmlAttribute(name = "isMust")
   protected Boolean isMust;
   @XmlAttribute(name = "name", required = true)
   protected String name;
   @XmlAttribute(name = "path", required = true)
   @XmlSchemaType(name = "anySimpleType")
   protected String path;
   @XmlAttribute(name = "type")
   protected String type;

   public List<Crc> getCrc() {
      if (null == this.crc) {
         this.crc = new ArrayList<>();
      }

      return this.crc;
   }

   public Boolean isIsMust() {
      return this.isMust;
   }

   public void setIsMust(Boolean value) {
      this.isMust = value;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String value) {
      this.name = value;
   }

   public String getPath() {
      return this.path;
   }

   public void setPath(String value) {
      this.path = value;
   }

   public String getType() {
      return this.type;
   }

   public void setType(String value) {
      this.type = value;
   }
}
