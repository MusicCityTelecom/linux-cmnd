/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.domain.device_settings.video;

import be.tpvision.smartcontrol.domain.device_settings.DeviceSetting;
import be.tpvision.smartcontrol.messages.domain.device_settings.video.picture_in_picture.SetStatusMessages;
import be.tpvision.smartcontrol.messages.domain.device_settings.video.picture_in_picture.SetWindowPositionMessages;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

public class PictureInPicture
implements DeviceSetting {
    private Status status;
    private WindowPosition windowPosition;

    protected PictureInPicture() {
    }

    public PictureInPicture(Status status, WindowPosition windowPosition) {
        this.setStatus(status);
        this.setWindowPosition(windowPosition);
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        Assert.notNull((Object)status, SetStatusMessages.STATUS_CAN_NOT_BE_NULL);
        this.status = status;
    }

    public WindowPosition getWindowPosition() {
        return this.windowPosition;
    }

    public void setWindowPosition(WindowPosition windowPosition) {
        Assert.notNull((Object)windowPosition, SetWindowPositionMessages.WINDOW_POSITION_CAN_NOT_BE_NULL);
        this.windowPosition = windowPosition;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof PictureInPicture)) {
            return false;
        }
        PictureInPicture that = (PictureInPicture)object;
        return new EqualsBuilder().append((Object)this.getStatus(), (Object)that.getStatus()).append((Object)this.getWindowPosition(), (Object)that.getWindowPosition()).isEquals();
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.getStatus(), this.getWindowPosition()});
    }

    public String toString() {
        return new ToStringBuilder(this).append("status", (Object)this.getStatus()).append("windowPosition", (Object)this.getWindowPosition()).toString();
    }

    public static enum WindowPosition {
        BOTTOM_LEFT,
        TOP_LEFT,
        TOP_RIGHT,
        BOTTOM_RIGHT,
        CENTER;

    }

    public static enum Status {
        OFF,
        ON,
        POP,
        QUICK_SWAP,
        PBP_2WIN,
        PBP_3WIN,
        PBP_4WIN,
        PBP_3WIN_1,
        PBP_3WIN_2,
        PBP_4WIN_1,
        SICP;

    }
}

