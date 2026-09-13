/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.protocol.todelete;

import be.tpvision.smartcontrol.util.Convertible;
import be.tpvision.smartcontrol.util.Convertibles;
import be.tpvision.smartcontrol.util.MessageUtilities;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class PictureInPicture
implements Convertibles {
    private Status status;
    private WindowPosition windowPosition;

    public PictureInPicture(Status status, WindowPosition windowPosition) {
        this.setStatus(status);
        this.setWindowPosition(windowPosition);
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        Objects.requireNonNull(status, MessageUtilities.PICTURE_IN_PICTURE_STATUS_NOT_NULL_MESSAGE);
        this.status = status;
    }

    public WindowPosition getWindowPosition() {
        return this.windowPosition;
    }

    public void setWindowPosition(WindowPosition windowPosition) {
        Objects.requireNonNull(windowPosition, MessageUtilities.PICTURE_IN_PICTURE_WINDOW_POSITION_NOT_NULL_MESSAGE);
        this.windowPosition = windowPosition;
    }

    @Override
    public byte[] convert() {
        byte statusByte = this.status.convert();
        byte windowPositionByte = this.windowPosition.convert();
        boolean reserved = false;
        return new byte[]{statusByte, windowPositionByte, 0, 0};
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof PictureInPicture)) {
            return false;
        }
        PictureInPicture that = (PictureInPicture)object;
        return new EqualsBuilder().append(this.status, that.status).append(this.windowPosition, that.windowPosition).isEquals();
    }

    public int hashCode() {
        return Objects.hash(this.status, this.windowPosition);
    }

    public String toString() {
        return new ToStringBuilder(this).append("status", this.status).append("windowPosition", this.windowPosition).toString();
    }

    public static enum WindowPosition implements Convertible
    {
        BOTTOM_LEFT(0),
        TOP_LEFT(1),
        TOP_RIGHT(2),
        BOTTOM_RIGHT(3),
        OTHER(4);

        private byte data;

        private WindowPosition(byte data) {
            this.data = data;
        }

        public int getData() {
            return this.data;
        }

        @Override
        public byte convert() {
            return this.data;
        }
    }

    public static enum Status implements Convertible
    {
        OFF(0),
        ON(1);

        private byte data;

        private Status(byte data) {
            this.data = data;
        }

        public byte getData() {
            return this.data;
        }

        @Override
        public byte convert() {
            return this.data;
        }
    }
}

