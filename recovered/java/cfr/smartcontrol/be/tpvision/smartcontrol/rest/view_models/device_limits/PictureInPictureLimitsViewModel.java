/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.view_models.device_limits;

import be.tpvision.smartcontrol.domain.device_settings.video.PictureInPicture;
import be.tpvision.smartcontrol.rest.view_models.device_limits.EnumLimitsViewModel;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class PictureInPictureLimitsViewModel {
    private EnumLimitsViewModel<PictureInPicture.Status> status;
    private EnumLimitsViewModel<PictureInPicture.WindowPosition> windowPosition;

    public PictureInPictureLimitsViewModel() {
        this(new EnumLimitsViewModel<PictureInPicture.Status>(PictureInPicture.Status.class), new EnumLimitsViewModel<PictureInPicture.WindowPosition>(PictureInPicture.WindowPosition.class));
    }

    public PictureInPictureLimitsViewModel(EnumLimitsViewModel<PictureInPicture.Status> status, EnumLimitsViewModel<PictureInPicture.WindowPosition> windowPosition) {
        this.status = status;
        this.windowPosition = windowPosition;
    }

    public EnumLimitsViewModel<PictureInPicture.Status> getStatus() {
        return this.status;
    }

    public void setStatus(EnumLimitsViewModel<PictureInPicture.Status> status) {
        this.status = status;
    }

    public EnumLimitsViewModel<PictureInPicture.WindowPosition> getWindowPosition() {
        return this.windowPosition;
    }

    public void setWindowPosition(EnumLimitsViewModel<PictureInPicture.WindowPosition> windowPosition) {
        this.windowPosition = windowPosition;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof PictureInPictureLimitsViewModel)) {
            return false;
        }
        PictureInPictureLimitsViewModel that = (PictureInPictureLimitsViewModel)object;
        return new EqualsBuilder().append(this.status, that.status).append(this.windowPosition, that.windowPosition).isEquals();
    }

    public int hashCode() {
        return Objects.hash(this.status, this.windowPosition);
    }

    public String toString() {
        return new ToStringBuilder(this).append("status", this.status).append("windowPosition", this.windowPosition).toString();
    }
}

