package be.tpvision.smartcontrol.domain;

import be.tpvision.smartcontrol.repository.converters.content.LocalDateTimeConverter;
import be.tpvision.smartcontrol.repository.converters.content.OrientationConverter;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@Table(name = "content")
public class Content implements Serializable {
   private static final long serialVersionUID = 1L;
   @Id
   private String id;
   private String title;
   @Convert(converter = LocalDateTimeConverter.class)
   private LocalDateTime created;
   @Convert(converter = LocalDateTimeConverter.class)
   private LocalDateTime changed;
   @Convert(converter = OrientationConverter.class)
   private Content.Orientation orientation;
   private String thumbnail;
   @Convert(converter = LocalDateTimeConverter.class)
   private LocalDateTime localChanged;
   @Convert(converter = LocalDateTimeConverter.class)
   private LocalDateTime publishDate;

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

   public LocalDateTime getCreated() {
      return this.created;
   }

   public void setCreated(final LocalDateTime created) {
      this.created = created;
   }

   public LocalDateTime getChanged() {
      return this.changed;
   }

   public void setChanged(final LocalDateTime changed) {
      this.changed = changed;
   }

   public Content.Orientation getOrientation() {
      return this.orientation;
   }

   public void setOrientation(final Content.Orientation orientation) {
      this.orientation = orientation;
   }

   public String getThumbnail() {
      return this.thumbnail;
   }

   public void setThumbnail(final String thumbnail) {
      this.thumbnail = thumbnail;
   }

   public LocalDateTime getLocalChanged() {
      return this.localChanged;
   }

   public void setLocalChanged(final LocalDateTime localChanged) {
      this.localChanged = localChanged;
   }

   public LocalDateTime getPublishDate() {
      return this.publishDate;
   }

   public void setPublishDate(final LocalDateTime publishDate) {
      this.publishDate = publishDate;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof Content)) {
         return false;
      }

      Content that = (Content)object;
      return new EqualsBuilder()
         .append(this.getId(), that.getId())
         .append(this.getTitle(), that.getTitle())
         .append(this.getCreated(), that.getCreated())
         .append(this.getChanged(), that.getChanged())
         .append(this.getOrientation(), that.getOrientation())
         .append(this.getThumbnail(), that.getThumbnail())
         .append(this.getLocalChanged(), that.getLocalChanged())
         .append(this.getPublishDate(), that.getPublishDate())
         .isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(
         this.getId(),
         this.getTitle(),
         this.getCreated(),
         this.getChanged(),
         this.getOrientation(),
         this.getThumbnail(),
         this.getLocalChanged(),
         this.getPublishDate()
      );
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this)
         .append("id", this.getId())
         .append("title", this.getTitle())
         .append("created", this.getCreated())
         .append("changed", this.getChanged())
         .append("orientation", this.getOrientation())
         .append("thumbnail", this.getThumbnail())
         .append("localChanged", this.getLocalChanged())
         .append("publishDate", this.getPublishDate())
         .toString();
   }

   public enum Orientation {
      PORTRAIT,
      LANDSCAPE;
   }
}
