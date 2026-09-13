package org.opentravel.ota._2003._05;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VideoItemType", propOrder = "url")
@XmlSeeAlso(VideoDescriptionType.VideoFormat.class)
public class VideoItemType {
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
   @XmlAttribute(name = "BitRate")
   @XmlSchemaType(name = "positiveInteger")
   protected BigInteger bitRate;
   @XmlAttribute(name = "Length")
   @XmlSchemaType(name = "positiveInteger")
   protected BigInteger length;
   @XmlAttribute(name = "Language")
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   @XmlSchemaType(name = "language")
   protected String language;
   @XmlAttribute(name = "Format")
   protected String format;
   @XmlAttribute(name = "FileSize")
   @XmlSchemaType(name = "positiveInteger")
   protected BigInteger fileSize;
   @XmlAttribute(name = "FileName")
   protected String fileName;

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

   public BigInteger getBitRate() {
      return this.bitRate;
   }

   public void setBitRate(BigInteger value) {
      this.bitRate = value;
   }

   public BigInteger getLength() {
      return this.length;
   }

   public void setLength(BigInteger value) {
      this.length = value;
   }

   public String getLanguage() {
      return this.language;
   }

   public void setLanguage(String value) {
      this.language = value;
   }

   public String getFormat() {
      return this.format;
   }

   public void setFormat(String value) {
      this.format = value;
   }

   public BigInteger getFileSize() {
      return this.fileSize;
   }

   public void setFileSize(BigInteger value) {
      this.fileSize = value;
   }

   public String getFileName() {
      return this.fileName;
   }

   public void setFileName(String value) {
      this.fileName = value;
   }
}
