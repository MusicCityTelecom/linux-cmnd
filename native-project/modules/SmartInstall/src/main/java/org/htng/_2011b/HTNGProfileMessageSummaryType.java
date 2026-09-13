package org.htng._2011b;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HTNG_ProfileMessageSummaryType")
public class HTNGProfileMessageSummaryType {
   @XmlAttribute(name = "Image")
   protected Integer image;
   @XmlAttribute(name = "Text")
   protected Integer text;
   @XmlAttribute(name = "Video")
   protected Integer video;
   @XmlAttribute(name = "Other")
   protected Integer other;

   public int getImage() {
      return this.image == null ? 0 : this.image;
   }

   public void setImage(Integer value) {
      this.image = value;
   }

   public int getText() {
      return this.text == null ? 0 : this.text;
   }

   public void setText(Integer value) {
      this.text = value;
   }

   public int getVideo() {
      return this.video == null ? 0 : this.video;
   }

   public void setVideo(Integer value) {
      this.video = value;
   }

   public int getOther() {
      return this.other == null ? 0 : this.other;
   }

   public void setOther(Integer value) {
      this.other = value;
   }
}
