/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.domain.device_settings.video;

import be.tpvision.smartcontrol.domain.device_settings.DeviceSetting;
import be.tpvision.smartcontrol.messages.domain.device_settings.video.color_parameters.SetBlueMessages;
import be.tpvision.smartcontrol.messages.domain.device_settings.video.color_parameters.SetGreenMessages;
import be.tpvision.smartcontrol.messages.domain.device_settings.video.color_parameters.SetRedMessages;
import be.tpvision.smartcontrol.messages.domain.device_settings.video.color_parameters.color.SetGainMessages;
import be.tpvision.smartcontrol.messages.domain.device_settings.video.color_parameters.color.SetOffsetMessages;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

public class ColorParameters
implements DeviceSetting {
    private Color red;
    private Color green;
    private Color blue;

    protected ColorParameters() {
        this.blue = null;
        this.green = null;
        this.red = null;
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
        Assert.notNull((Object)red, SetRedMessages.RED_CAN_NOT_BE_NULL);
        this.red = red;
    }

    public Color getGreen() {
        return this.green;
    }

    public void setGreen(Color green) {
        Assert.notNull((Object)green, SetGreenMessages.GREEN_CAN_NOT_BE_NULL);
        this.green = green;
    }

    public Color getBlue() {
        return this.blue;
    }

    public void setBlue(Color blue) {
        Assert.notNull((Object)blue, SetBlueMessages.BLUE_CAN_NOT_BE_NULL);
        this.blue = blue;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof ColorParameters)) {
            return false;
        }
        ColorParameters that = (ColorParameters)object;
        return new EqualsBuilder().append(this.getRed(), that.getRed()).append(this.getGreen(), that.getGreen()).append(this.getBlue(), that.getBlue()).isEquals();
    }

    public int hashCode() {
        return Objects.hash(this.getRed(), this.getGreen(), this.getBlue());
    }

    public String toString() {
        return new ToStringBuilder(this).append("red", this.getRed()).append("green", this.getGreen()).append("blue", this.getBlue()).toString();
    }

    public static class Color {
        private int gain;
        private int offset;

        protected Color() {
        }

        public Color(int gain, int offset) {
            this.setGain(gain);
            this.setOffset(offset);
        }

        public int getGain() {
            return this.gain;
        }

        public void setGain(int gain) {
            Assert.isTrue(gain >= 0, SetGainMessages.GAIN_HAS_TO_BE_A_POSITIVE_NUMBER);
            this.gain = gain;
        }

        public int getOffset() {
            return this.offset;
        }

        public void setOffset(int offset) {
            Assert.isTrue(offset >= 0, SetOffsetMessages.OFFSET_HAS_TO_BE_A_POSITIVE_NUMBER);
            this.offset = offset;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof Color)) {
                return false;
            }
            Color color = (Color)object;
            return new EqualsBuilder().append(this.getGain(), color.getGain()).append(this.getOffset(), color.getOffset()).isEquals();
        }

        public int hashCode() {
            return Objects.hash(this.getGain(), this.getOffset());
        }

        public String toString() {
            return new ToStringBuilder(this).append("gain", this.getGain()).append("offset", this.getOffset()).toString();
        }
    }
}

