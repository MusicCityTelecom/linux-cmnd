/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.protocol.todelete;

import be.tpvision.smartcontrol.util.Convertibles;
import be.tpvision.smartcontrol.util.ValueUtilities;
import java.nio.ByteBuffer;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ColorParameters
implements Convertibles {
    private Color red;
    private Color green;
    private Color blue;

    public ColorParameters() {
        this(null, null, null);
    }

    public ColorParameters(Color red, Color green, Color blue) {
        this.setRed(red);
        this.setGreen(green);
        this.setBlue(blue);
    }

    public Color getRed() {
        return this.red;
    }

    public void setRed(Color red) {
        this.red = red != null ? red : new Color();
    }

    public Color getGreen() {
        return this.green;
    }

    public void setGreen(Color green) {
        this.green = green != null ? green : new Color();
    }

    public Color getBlue() {
        return this.blue;
    }

    public void setBlue(Color blue) {
        this.blue = blue != null ? blue : new Color();
    }

    @Override
    public byte[] convert() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(6);
        byteBuffer.put((byte)this.red.gain);
        byteBuffer.put((byte)this.green.gain);
        byteBuffer.put((byte)this.blue.gain);
        byteBuffer.put((byte)this.red.offset);
        byteBuffer.put((byte)this.green.offset);
        byteBuffer.put((byte)this.blue.offset);
        return byteBuffer.array();
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof ColorParameters)) {
            return false;
        }
        ColorParameters that = (ColorParameters)object;
        return new EqualsBuilder().append(this.red, that.red).append(this.green, that.green).append(this.blue, that.blue).isEquals();
    }

    public int hashCode() {
        return Objects.hash(this.red, this.green, this.blue);
    }

    public String toString() {
        return new ToStringBuilder(this).append("red", this.red).append("green", this.green).append("blue", this.blue).toString();
    }

    public static class Color {
        public static final int MINIMUM_VALUE = 0;
        public static final int MAXIMUM_VALUE = 255;
        private int gain;
        private int offset;

        public Color() {
            this(0, 0);
        }

        public Color(int gain, int offset) {
            this.setGain(gain);
            this.setOffset(offset);
        }

        public int getGain() {
            return this.gain;
        }

        public void setGain(int gain) {
            this.gain = this.getValue(gain);
        }

        public int getOffset() {
            return this.offset;
        }

        public void setOffset(int offset) {
            this.offset = this.getValue(offset);
        }

        private int getValue(int value) {
            return ValueUtilities.getValue(value, 0, 255);
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof Color)) {
                return false;
            }
            Color color = (Color)object;
            return new EqualsBuilder().append(this.gain, color.gain).append(this.offset, color.offset).isEquals();
        }

        public int hashCode() {
            return Objects.hash(this.gain, this.offset);
        }

        public String toString() {
            return new ToStringBuilder(this).append("gain", this.gain).append("offset", this.offset).toString();
        }
    }
}

