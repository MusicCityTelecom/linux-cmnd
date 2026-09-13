package org.opentravel.ota._2003._05;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SpecialServiceType", propOrder = "comment")
public class SpecialServiceType {
   @XmlElement(name = "Comment")
   protected ParagraphType comment;
   @XmlAttribute(name = "Type")
   protected String type;
   @XmlAttribute(name = "Code")
   protected String code;
   @XmlAttribute(name = "CodeName")
   protected String codeName;
   @XmlAttribute(name = "CodeDetail")
   protected String codeDetail;
   @XmlAttribute(name = "AssociationType")
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   protected String associationType;
   @XmlAttribute(name = "Date")
   protected String date;
   @XmlAttribute(name = "NbrOfYears")
   @XmlSchemaType(name = "positiveInteger")
   protected BigInteger nbrOfYears;

   public ParagraphType getComment() {
      return this.comment;
   }

   public void setComment(ParagraphType value) {
      this.comment = value;
   }

   public String getType() {
      return this.type;
   }

   public void setType(String value) {
      this.type = value;
   }

   public String getCode() {
      return this.code;
   }

   public void setCode(String value) {
      this.code = value;
   }

   public String getCodeName() {
      return this.codeName;
   }

   public void setCodeName(String value) {
      this.codeName = value;
   }

   public String getCodeDetail() {
      return this.codeDetail;
   }

   public void setCodeDetail(String value) {
      this.codeDetail = value;
   }

   public String getAssociationType() {
      return this.associationType;
   }

   public void setAssociationType(String value) {
      this.associationType = value;
   }

   public String getDate() {
      return this.date;
   }

   public void setDate(String value) {
      this.date = value;
   }

   public BigInteger getNbrOfYears() {
      return this.nbrOfYears;
   }

   public void setNbrOfYears(BigInteger value) {
      this.nbrOfYears = value;
   }
}
