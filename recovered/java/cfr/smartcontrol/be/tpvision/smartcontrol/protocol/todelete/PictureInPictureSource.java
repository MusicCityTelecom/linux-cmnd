/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.protocol.todelete;

import be.tpvision.smartcontrol.protocol.todelete.InputSource;
import be.tpvision.smartcontrol.util.Convertible;
import be.tpvision.smartcontrol.util.Convertibles;
import be.tpvision.smartcontrol.util.MessageUtilities;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class PictureInPictureSource
implements Convertibles {
    private SourceType pictureInPictureSourceSourceType;
    private InputSource.SourceType inputSourceSourceType;
    private Quadrant quadrant;

    public PictureInPictureSource(SourceType pictureInPictureSourceSourceType, InputSource.SourceType inputSourceSourceType, Quadrant quadrant) {
        this.setPictureInPictureSourceSourceType(pictureInPictureSourceSourceType);
        this.setInputSourceSourceType(inputSourceSourceType);
        this.setQuadrant(quadrant);
    }

    public SourceType getPictureInPictureSourceSourceType() {
        return this.pictureInPictureSourceSourceType;
    }

    public void setPictureInPictureSourceSourceType(SourceType pictureInPictureSourceSourceType) {
        Objects.requireNonNull(pictureInPictureSourceSourceType, MessageUtilities.PICTURE_IN_PICTURE_SOURCE_SOURCE_TYPE_NOT_NULL_MESSAGE);
        this.pictureInPictureSourceSourceType = pictureInPictureSourceSourceType;
    }

    public InputSource.SourceType getInputSourceSourceType() {
        return this.inputSourceSourceType;
    }

    public void setInputSourceSourceType(InputSource.SourceType inputSourceSourceType) {
        this.inputSourceSourceType = inputSourceSourceType;
    }

    public Quadrant getQuadrant() {
        return this.quadrant;
    }

    public void setQuadrant(Quadrant quadrant) {
        Objects.requireNonNull(quadrant, MessageUtilities.PICTURE_IN_PICTURE_SOURCE_QUADRANT_NOT_NULL_MESSAGE);
        this.quadrant = quadrant;
    }

    @Override
    public byte[] convert() {
        byte sourceTypeByte = this.pictureInPictureSourceSourceType.convert();
        byte inputSourceByte = this.inputSourceSourceType != null ? this.inputSourceSourceType.convert() : (byte)0;
        byte quadrantByte = this.quadrant.convert();
        return new byte[]{sourceTypeByte, inputSourceByte, quadrantByte};
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof PictureInPictureSource)) {
            return false;
        }
        PictureInPictureSource that = (PictureInPictureSource)object;
        return new EqualsBuilder().append(this.pictureInPictureSourceSourceType, that.pictureInPictureSourceSourceType).append(this.inputSourceSourceType, that.inputSourceSourceType).append(this.quadrant, that.quadrant).isEquals();
    }

    public int hashCode() {
        return Objects.hash(this.pictureInPictureSourceSourceType, this.inputSourceSourceType, this.quadrant);
    }

    public String toString() {
        return new ToStringBuilder(this).append("pictureInPictureSourceSourceType", this.pictureInPictureSourceSourceType).append("inputSourceSourceType", this.inputSourceSourceType).append("quadrant", this.quadrant).toString();
    }

    public static enum Quadrant implements Convertible
    {
        Q2(0),
        Q3(1),
        Q4(2);

        private byte data;

        private Quadrant(byte data) {
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

    public static enum SourceType implements Convertible
    {
        INPUT_SOURCE(-3),
        SMART_CARD(-2);

        private byte data;

        private SourceType(byte data) {
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

