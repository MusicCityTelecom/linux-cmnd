/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.view_models.video;

import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class PictureInPictureViewModel {
    private String status;
    private String windowPosition;

    protected PictureInPictureViewModel() {
    }

    public PictureInPictureViewModel(String status, String windowPosition) {
        this.status = status;
        this.windowPosition = windowPosition;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWindowPosition() {
        return this.windowPosition;
    }

    public void setWindowPosition(String windowPosition) {
        this.windowPosition = windowPosition;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof PictureInPictureViewModel)) {
            return false;
        }
        PictureInPictureViewModel that = (PictureInPictureViewModel)object;
        return new EqualsBuilder().append(this.getStatus(), that.getStatus()).append(this.getWindowPosition(), that.getWindowPosition()).isEquals();
    }

    public int hashCode() {
        return Objects.hash(this.getStatus(), this.getWindowPosition());
    }

    public String toString() {
        return new ToStringBuilder(this).append("status", this.getStatus()).append("windowPosition", this.getWindowPosition()).toString();
    }
}

