/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.view_models.content;

import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ContentViewModel {
    private String id;
    private String title;
    private String created;
    private String changed;
    private String orientation;
    private String thumbnail;
    private String publishDate;

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

    public String getCreated() {
        return this.created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getChanged() {
        return this.changed;
    }

    public void setChanged(String changed) {
        this.changed = changed;
    }

    public String getOrientation() {
        return this.orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public String getThumbnail() {
        return this.thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getPublishDate() {
        return this.publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof ContentViewModel)) {
            return false;
        }
        ContentViewModel that = (ContentViewModel)object;
        return new EqualsBuilder().append(this.getId(), that.getId()).append(this.getTitle(), that.getTitle()).append(this.getCreated(), that.getCreated()).append(this.getChanged(), that.getChanged()).append(this.getOrientation(), that.getOrientation()).append(this.getThumbnail(), that.getThumbnail()).append(this.getPublishDate(), that.getPublishDate()).isEquals();
    }

    public int hashCode() {
        return Objects.hash(this.getId(), this.getTitle(), this.getCreated(), this.getChanged(), this.getOrientation(), this.getThumbnail(), this.getPublishDate());
    }

    public String toString() {
        return new ToStringBuilder(this).append("id", this.getId()).append("title", this.getTitle()).append("created", this.getCreated()).append("changed", this.getChanged()).append("orientation", this.getOrientation()).append("thumbnail", this.getThumbnail()).append("publishDate", this.getPublishDate()).toString();
    }
}

