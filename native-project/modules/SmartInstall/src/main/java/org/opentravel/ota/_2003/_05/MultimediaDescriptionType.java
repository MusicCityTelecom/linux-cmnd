package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MultimediaDescriptionType", propOrder = {"videoItems", "imageItems", "textItems"})
public class MultimediaDescriptionType {
   @XmlElement(name = "VideoItems")
   protected VideoItemsType videoItems;
   @XmlElement(name = "ImageItems")
   protected ImageItemsType imageItems;
   @XmlElement(name = "TextItems")
   protected TextItemsType textItems;
   @XmlAttribute(name = "InfoCode")
   protected String infoCode;
   @XmlAttribute(name = "AdditionalDetailCode")
   protected String additionalDetailCode;
   @XmlAttribute(name = "LastUpdated")
   @XmlSchemaType(name = "dateTime")
   protected XMLGregorianCalendar lastUpdated;
   @XmlAttribute(name = "Version")
   protected String version;
   @XmlAttribute(name = "ID")
   protected String id;

   public VideoItemsType getVideoItems() {
      return this.videoItems;
   }

   public void setVideoItems(VideoItemsType value) {
      this.videoItems = value;
   }

   public ImageItemsType getImageItems() {
      return this.imageItems;
   }

   public void setImageItems(ImageItemsType value) {
      this.imageItems = value;
   }

   public TextItemsType getTextItems() {
      return this.textItems;
   }

   public void setTextItems(TextItemsType value) {
      this.textItems = value;
   }

   public String getInfoCode() {
      return this.infoCode;
   }

   public void setInfoCode(String value) {
      this.infoCode = value;
   }

   public String getAdditionalDetailCode() {
      return this.additionalDetailCode;
   }

   public void setAdditionalDetailCode(String value) {
      this.additionalDetailCode = value;
   }

   public XMLGregorianCalendar getLastUpdated() {
      return this.lastUpdated;
   }

   public void setLastUpdated(XMLGregorianCalendar value) {
      this.lastUpdated = value;
   }

   public String getVersion() {
      return this.version;
   }

   public void setVersion(String value) {
      this.version = value;
   }

   public String getID() {
      return this.id;
   }

   public void setID(String value) {
      this.id = value;
   }
}
