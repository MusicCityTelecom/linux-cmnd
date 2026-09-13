package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ImageItemsType", propOrder = "imageItem")
public class ImageItemsType {
   @XmlElement(name = "ImageItem", required = true)
   protected List<ImageItemsType.ImageItem> imageItem;

   public List<ImageItemsType.ImageItem> getImageItem() {
      if (this.imageItem == null) {
         this.imageItem = new ArrayList<>();
      }

      return this.imageItem;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class ImageItem extends ImageDescriptionType {
      @XmlAttribute(name = "Version")
      protected String version;
      @XmlAttribute(name = "CreateDateTime")
      @XmlSchemaType(name = "dateTime")
      protected XMLGregorianCalendar createDateTime;
      @XmlAttribute(name = "CreatorID")
      protected String creatorID;
      @XmlAttribute(name = "LastModifyDateTime")
      @XmlSchemaType(name = "dateTime")
      protected XMLGregorianCalendar lastModifyDateTime;
      @XmlAttribute(name = "LastModifierID")
      protected String lastModifierID;
      @XmlAttribute(name = "PurgeDate")
      @XmlSchemaType(name = "date")
      protected XMLGregorianCalendar purgeDate;
      @XmlAttribute(name = "Removal")
      protected Boolean removal;
      @XmlAttribute(name = "ID")
      protected String id;

      public String getVersion() {
         return this.version;
      }

      public void setVersion(String value) {
         this.version = value;
      }

      public XMLGregorianCalendar getCreateDateTime() {
         return this.createDateTime;
      }

      public void setCreateDateTime(XMLGregorianCalendar value) {
         this.createDateTime = value;
      }

      public String getCreatorID() {
         return this.creatorID;
      }

      public void setCreatorID(String value) {
         this.creatorID = value;
      }

      public XMLGregorianCalendar getLastModifyDateTime() {
         return this.lastModifyDateTime;
      }

      public void setLastModifyDateTime(XMLGregorianCalendar value) {
         this.lastModifyDateTime = value;
      }

      public String getLastModifierID() {
         return this.lastModifierID;
      }

      public void setLastModifierID(String value) {
         this.lastModifierID = value;
      }

      public XMLGregorianCalendar getPurgeDate() {
         return this.purgeDate;
      }

      public void setPurgeDate(XMLGregorianCalendar value) {
         this.purgeDate = value;
      }

      public Boolean isRemoval() {
         return this.removal;
      }

      public void setRemoval(Boolean value) {
         this.removal = value;
      }

      public String getID() {
         return this.id;
      }

      public void setID(String value) {
         this.id = value;
      }
   }
}
