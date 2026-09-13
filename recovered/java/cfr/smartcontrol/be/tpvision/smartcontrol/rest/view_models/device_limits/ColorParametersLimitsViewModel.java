/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.view_models.device_limits;

import be.tpvision.smartcontrol.rest.view_models.device_limits.LimitsViewModel;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ColorParametersLimitsViewModel {
    private ColorLimitsViewModel red;
    private ColorLimitsViewModel green;
    private ColorLimitsViewModel blue;

    protected ColorParametersLimitsViewModel() {
        this(null, null, null);
        ColorLimitsViewModel red = new ColorLimitsViewModel();
        this.setRed(red);
        ColorLimitsViewModel green = new ColorLimitsViewModel();
        this.setGreen(green);
        ColorLimitsViewModel blue = new ColorLimitsViewModel();
        this.setBlue(blue);
    }

    public ColorParametersLimitsViewModel(ColorLimitsViewModel red, ColorLimitsViewModel green, ColorLimitsViewModel blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public ColorLimitsViewModel getRed() {
        return this.red;
    }

    public void setRed(ColorLimitsViewModel red) {
        this.red = red;
    }

    public ColorLimitsViewModel getGreen() {
        return this.green;
    }

    public void setGreen(ColorLimitsViewModel green) {
        this.green = green;
    }

    public ColorLimitsViewModel getBlue() {
        return this.blue;
    }

    public void setBlue(ColorLimitsViewModel blue) {
        this.blue = blue;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof ColorParametersLimitsViewModel)) {
            return false;
        }
        ColorParametersLimitsViewModel that = (ColorParametersLimitsViewModel)object;
        return new EqualsBuilder().append(this.red, that.red).append(this.green, that.green).append(this.blue, that.blue).isEquals();
    }

    public int hashCode() {
        return Objects.hash(this.red, this.green, this.blue);
    }

    public String toString() {
        return new ToStringBuilder(this).append("red", this.red).append("green", this.green).append("blue", this.blue).toString();
    }

    public class ColorLimitsViewModel {
        private LimitsViewModel gain;
        private LimitsViewModel offset;

        public ColorLimitsViewModel() {
            this(new LimitsViewModel(0, 255), new LimitsViewModel(0, 255));
        }

        public ColorLimitsViewModel(LimitsViewModel gain, LimitsViewModel offset) {
            this.gain = gain;
            this.offset = offset;
        }

        public LimitsViewModel getGain() {
            return this.gain;
        }

        public void setGain(LimitsViewModel gain) {
            this.gain = gain;
        }

        public LimitsViewModel getOffset() {
            return this.offset;
        }

        public void setOffset(LimitsViewModel offset) {
            this.offset = offset;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof ColorLimitsViewModel)) {
                return false;
            }
            ColorLimitsViewModel that = (ColorLimitsViewModel)object;
            return new EqualsBuilder().append(this.gain, that.gain).append(this.offset, that.offset).isEquals();
        }

        public int hashCode() {
            return Objects.hash(this.gain, this.offset);
        }

        public String toString() {
            return new ToStringBuilder(this).append("gain", this.gain).append("offset", this.offset).toString();
        }
    }
}

