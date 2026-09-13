package org.opentravel.ota._2003._05;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ImageItemType", propOrder = "url")
@XmlSeeAlso(ImageDescriptionType.ImageFormat.class)
public class ImageItemType {
   @XmlElement(name = "URL")
   @XmlSchemaType(name = "anyURI")
   protected String url;
   @XmlAttribute(name = "UnitOfMeasureCode")
   protected String unitOfMeasureCode;
   @XmlAttribute(name = "Width")
   @XmlSchemaType(name = "positiveInteger")
   protected BigInteger width;
   @XmlAttribute(name = "Height")
   @XmlSchemaType(name = "positiveInteger")
   protected BigInteger height;

   public String getURL() {
      return this.url;
   }

   public void setURL(String value) {
      this.url = value;
   }

   public String getUnitOfMeasureCode() {
      return this.unitOfMeasureCode;
   }

   public void setUnitOfMeasureCode(String value) {
      this.unitOfMeasureCode = value;
   }

   public BigInteger getWidth() {
      return this.width;
   }

   public void setWidth(BigInteger value) {
      this.width = value;
   }

   public BigInteger getHeight() {
      return this.height;
   }

   public void setHeight(BigInteger value) {
      this.height = value;
   }
}
