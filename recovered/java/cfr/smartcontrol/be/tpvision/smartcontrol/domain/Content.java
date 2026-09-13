/*
 * Decompiled with CFR 0.152.
 */
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
@Table(name="content")
public class Content
implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    private String title;
    @Convert(converter=LocalDateTimeConverter.class)
    private LocalDateTime created;
    @Convert(converter=LocalDateTimeConverter.class)
    private LocalDateTime changed;
    @Convert(converter=OrientationConverter.class)
    private Orientation orientation;
    private String thumbnail;
    @Convert(converter=LocalDateTimeConverter.class)
    private LocalDateTime localChanged;
    @Convert(converter=LocalDateTimeConverter.class)
    private LocalDateTime publishDate;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getCreated() {
        return this.created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getChanged() {
        return this.changed;
    }

    public void setChanged(LocalDateTime changed) {
        this.changed = changed;
    }

    public Orientation getOrientation() {
        return this.orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public String getThumbnail() {
        return this.thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public LocalDateTime getLocalChanged() {
        return this.localChanged;
    }

    public void setLocalChanged(LocalDateTime localChanged) {
        this.localChanged = localChanged;
    }

    public LocalDateTime getPublishDate() {
        return this.publishDate;
    }

    public void setPublishDate(LocalDateTime publishDate) {
        this.publishDate = publishDate;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Content)) {
            return false;
        }
        Content that = (Content)object;
        return new EqualsBuilder().append(this.getId(), that.getId()).append(this.getTitle(), that.getTitle()).append(this.getCreated(), that.getCreated()).append(this.getChanged(), that.getChanged()).append((Object)this.getOrientation(), (Object)that.getOrientation()).append(this.getThumbnail(), that.getThumbnail()).append(this.getLocalChanged(), that.getLocalChanged()).append(this.getPublishDate(), that.getPublishDate()).isEquals();
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.getId(), this.getTitle(), this.getCreated(), this.getChanged(), this.getOrientation(), this.getThumbnail(), this.getLocalChanged(), this.getPublishDate()});
    }

    public String toString() {
        return new ToStringBuilder(this).append("id", this.getId()).append("title", this.getTitle()).append("created", this.getCreated()).append("changed", this.getChanged()).append("orientation", (Object)this.getOrientation()).append("thumbnail", this.getThumbnail()).append("localChanged", this.getLocalChanged()).append("publishDate", this.getPublishDate()).toString();
    }

    public static enum Orientation {
        PORTRAIT,
        LANDSCAPE;

    }
}

