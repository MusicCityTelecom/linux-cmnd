package org.opentravel.ota._2003._05;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
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
@XmlType(name = "TextDescriptionType", propOrder = {"url", "description"})
@XmlSeeAlso(TextItemsType.TextItem.class)
public class TextDescriptionType {
   @XmlElement(name = "URL")
   @XmlSchemaType(name = "anyURI")
   protected String url;
   @XmlElement(name = "Description")
   protected List<TextDescriptionType.Description> description;
   @XmlAttribute(name = "Category")
   protected String category;
   @XmlAttribute(name = "Language")
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   @XmlSchemaType(name = "language")
   protected String language;
   @XmlAttribute(name = "ContentID")
   protected String contentID;
   @XmlAttribute(name = "Title")
   protected String title;
   @XmlAttribute(name = "Author")
   protected String author;
   @XmlAttribute(name = "CopyrightNotice")
   protected String copyrightNotice;
   @XmlAttribute(name = "CopyrightOwner")
   protected String copyrightOwner;
   @XmlAttribute(name = "CopyrightStart")
   protected String copyrightStart;
   @XmlAttribute(name = "CopyrightEnd")
   protected String copyrightEnd;
   @XmlAttribute(name = "EffectiveStart")
   protected String effectiveStart;
   @XmlAttribute(name = "EffectiveEnd")
   protected String effectiveEnd;
   @XmlAttribute(name = "ApplicableStart")
   protected String applicableStart;
   @XmlAttribute(name = "ApplicableEnd")
   protected String applicableEnd;
   @XmlAttribute(name = "RecordID")
   protected String recordID;
   @XmlAttribute(name = "SourceID")
   protected String sourceID;

   public String getURL() {
      return this.url;
   }

   public void setURL(String value) {
      this.url = value;
   }

   public List<TextDescriptionType.Description> getDescription() {
      if (this.description == null) {
         this.description = new ArrayList<>();
      }

      return this.description;
   }

   public String getCategory() {
      return this.category;
   }

   public void setCategory(String value) {
      this.category = value;
   }

   public String getLanguage() {
      return this.language;
   }

   public void setLanguage(String value) {
      this.language = value;
   }

   public String getContentID() {
      return this.contentID;
   }

   public void setContentID(String value) {
      this.contentID = value;
   }

   public String getTitle() {
      return this.title;
   }

   public void setTitle(String value) {
      this.title = value;
   }

   public String getAuthor() {
      return this.author;
   }

   public void setAuthor(String value) {
      this.author = value;
   }

   public String getCopyrightNotice() {
      return this.copyrightNotice;
   }

   public void setCopyrightNotice(String value) {
      this.copyrightNotice = value;
   }

   public String getCopyrightOwner() {
      return this.copyrightOwner;
   }

   public void setCopyrightOwner(String value) {
      this.copyrightOwner = value;
   }

   public String getCopyrightStart() {
      return this.copyrightStart;
   }

   public void setCopyrightStart(String value) {
      this.copyrightStart = value;
   }

   public String getCopyrightEnd() {
      return this.copyrightEnd;
   }

   public void setCopyrightEnd(String value) {
      this.copyrightEnd = value;
   }

   public String getEffectiveStart() {
      return this.effectiveStart;
   }

   public void setEffectiveStart(String value) {
      this.effectiveStart = value;
   }

   public String getEffectiveEnd() {
      return this.effectiveEnd;
   }

   public void setEffectiveEnd(String value) {
      this.effectiveEnd = value;
   }

   public String getApplicableStart() {
      return this.applicableStart;
   }

   public void setApplicableStart(String value) {
      this.applicableStart = value;
   }

   public String getApplicableEnd() {
      return this.applicableEnd;
   }

   public void setApplicableEnd(String value) {
      this.applicableEnd = value;
   }

   public String getRecordID() {
      return this.recordID;
   }

   public void setRecordID(String value) {
      this.recordID = value;
   }

   public String getSourceID() {
      return this.sourceID;
   }

   public void setSourceID(String value) {
      this.sourceID = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class Description extends FormattedTextTextType {
      @XmlAttribute(name = "ListItem")
      protected BigInteger listItem;

      public BigInteger getListItem() {
         return this.listItem;
      }

      public void setListItem(BigInteger value) {
         this.listItem = value;
      }
   }
}
