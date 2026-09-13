package be.tpvision.smartcontrol.rest.view_models.content;

import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class SmartCmsContentViewModel {
   private String id;
   private String title;
   private String created;
   private String changed;
   private String publishDate;
   private Integer orientation;
   private String thumbnail;

   public String getId() {
      return this.id;
   }

   public void setId(final String id) {
      this.id = id;
   }

   public String getTitle() {
      return this.title;
   }

   public void setTitle(final String title) {
      this.title = title;
   }

   public String getCreated() {
      return this.created;
   }

   public void setCreated(final String created) {
      this.created = created;
   }

   public String getChanged() {
      return this.changed;
   }

   public void setChanged(final String changed) {
      this.changed = changed;
   }

   public String getPublishDate() {
      return this.publishDate;
   }

   public void setPublishDate(final String publishDate) {
      this.publishDate = publishDate;
   }

   public Integer getOrientation() {
      return this.orientation;
   }

   public void setOrientation(final Integer orientation) {
      this.orientation = orientation;
   }

   public String getThumbnail() {
      return this.thumbnail;
   }

   public void setThumbnail(final String thumbnail) {
      this.thumbnail = thumbnail;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof SmartCmsContentViewModel)) {
         return false;
      }

      SmartCmsContentViewModel that = (SmartCmsContentViewModel)object;
      return new EqualsBuilder()
         .append(this.getId(), that.getId())
         .append(this.getTitle(), that.getTitle())
         .append(this.getCreated(), that.getCreated())
         .append(this.getChanged(), that.getChanged())
         .append(this.getPublishDate(), that.getPublishDate())
         .append(this.getOrientation(), that.getOrientation())
         .append(this.getThumbnail(), that.getThumbnail())
         .isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(
         this.getId(), this.getTitle(), this.getCreated(), this.getChanged(), this.getPublishDate(), this.getOrientation(), this.getThumbnail()
      );
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this)
         .append("id", this.getId())
         .append("title", this.getTitle())
         .append("created", this.getCreated())
         .append("changed", this.getChanged())
         .append("publishDate", this.getPublishDate())
         .append("orientation", this.getOrientation())
         .append("thumbnail", this.getThumbnail())
         .toString();
   }
}
