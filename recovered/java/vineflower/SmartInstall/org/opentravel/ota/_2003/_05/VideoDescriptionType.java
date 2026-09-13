package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VideoDescriptionType", propOrder = "videoFormat")
@XmlSeeAlso(VideoItemsType.VideoItem.class)
public class VideoDescriptionType {
   @XmlElement(name = "VideoFormat")
   protected List<VideoDescriptionType.VideoFormat> videoFormat;
   @XmlAttribute(name = "Category")
   protected String category;

   public List<VideoDescriptionType.VideoFormat> getVideoFormat() {
      if (this.videoFormat == null) {
         this.videoFormat = new ArrayList<>();
      }

      return this.videoFormat;
   }

   public String getCategory() {
      return this.category;
   }

   public void setCategory(String value) {
      this.category = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class VideoFormat extends VideoItemType {
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
      @XmlAttribute(name = "ID")
      protected String id;

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

      public String getID() {
         return this.id;
      }

      public void setID(String value) {
         this.id = value;
      }
   }
}
