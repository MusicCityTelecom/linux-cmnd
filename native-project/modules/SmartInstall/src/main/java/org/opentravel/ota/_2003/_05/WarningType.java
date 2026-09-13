package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WarningType")
public class WarningType extends FreeTextType {
   @XmlAttribute(name = "Type", required = true)
   protected String type;
   @XmlAttribute(name = "RPH")
   protected String rph;
   @XmlAttribute(name = "ShortText")
   protected String shortText;
   @XmlAttribute(name = "Code")
   protected String code;
   @XmlAttribute(name = "DocURL")
   @XmlSchemaType(name = "anyURI")
   protected String docURL;
   @XmlAttribute(name = "Status")
   protected String status;
   @XmlAttribute(name = "Tag")
   protected String tag;
   @XmlAttribute(name = "RecordID")
   protected String recordID;

   public String getType() {
      return this.type;
   }

   public void setType(String value) {
      this.type = value;
   }

   public String getRPH() {
      return this.rph;
   }

   public void setRPH(String value) {
      this.rph = value;
   }

   public String getShortText() {
      return this.shortText;
   }

   public void setShortText(String value) {
      this.shortText = value;
   }

   public String getCode() {
      return this.code;
   }

   public void setCode(String value) {
      this.code = value;
   }

   public String getDocURL() {
      return this.docURL;
   }

   public void setDocURL(String value) {
      this.docURL = value;
   }

   public String getStatus() {
      return this.status;
   }

   public void setStatus(String value) {
      this.status = value;
   }

   public String getTag() {
      return this.tag;
   }

   public void setTag(String value) {
      this.tag = value;
   }

   public String getRecordID() {
      return this.recordID;
   }

   public void setRecordID(String value) {
      this.recordID = value;
   }
}
