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
@XmlType(name = "TextItemsType", propOrder = "textItem")
public class TextItemsType {
   @XmlElement(name = "TextItem", required = true)
   protected List<TextItemsType.TextItem> textItem;

   public List<TextItemsType.TextItem> getTextItem() {
      if (this.textItem == null) {
         this.textItem = new ArrayList<>();
      }

      return this.textItem;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class TextItem extends TextDescriptionType {
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
   }
}
