/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.protocol.todelete;

import be.tpvision.smartcontrol.util.Convertible;
import be.tpvision.smartcontrol.util.Convertibles;
import be.tpvision.smartcontrol.util.MessageUtilities;
import be.tpvision.smartcontrol.util.ValueUtilities;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class VideoParameters
implements Convertibles {
    public static final int MINIMUM_VALUE = 0;
    public static final int MAXIMUM_VALUE = 100;
    private int brightness;
    private int color;
    private int contrast;
    private int sharpness;
    private int hue;
    private int backlight;
    private GammaSelection gammaSelection;

    public VideoParameters() {
        this(0, 0, 0, 0, 0, 0, GammaSelection.NATIVE);
    }

    public VideoParameters(int brightness, int color, int contrast, int sharpness, int hue, int backlight, GammaSelection gammaSelection) {
        this.setBrightness(brightness);
        this.setColor(color);
        this.setContrast(contrast);
        this.setSharpness(sharpness);
        this.setHue(hue);
        this.setBacklight(backlight);
        this.setGammaSelection(gammaSelection);
    }

    public int getBrightness() {
        return this.brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = this.getValue(brightness);
    }

    public int getColor() {
        return this.color;
    }

    public void setColor(int color) {
        this.color = this.getValue(color);
    }

    public int getContrast() {
        return this.contrast;
    }

    public void setContrast(int contrast) {
        this.contrast = this.getValue(contrast);
    }

    public int getSharpness() {
        return this.sharpness;
    }

    public void setSharpness(int sharpness) {
        this.sharpness = this.getValue(sharpness);
    }

    public int getHue() {
        return this.hue;
    }

    public void setHue(int hue) {
        this.hue = this.getValue(hue);
    }

    public int getBacklight() {
        return this.backlight;
    }

    public void setBacklight(int backlight) {
        this.backlight = this.getValue(backlight);
    }

    public GammaSelection getGammaSelection() {
        return this.gammaSelection;
    }

    public void setGammaSelection(GammaSelection gammaSelection) {
        Objects.requireNonNull(gammaSelection, MessageUtilities.VIDEO_PARAMETERS_GAMMA_SELECTION_NOT_NULL_MESSAGE);
        this.gammaSelection = gammaSelection;
    }

    private int getValue(int value) {
        return ValueUtilities.getValue(value, 0, 100);
    }

    @Override
    public byte[] convert() {
        byte brightnessByte = (byte)this.brightness;
        byte colorByte = (byte)this.color;
        byte contrastByte = (byte)this.contrast;
        byte sharpnessByte = (byte)this.sharpness;
        byte hueByte = (byte)this.hue;
        byte backlightByte = (byte)this.backlight;
        byte gammaSelectionByte = this.gammaSelection.convert();
        return new byte[]{brightnessByte, colorByte, contrastByte, sharpnessByte, hueByte, backlightByte, gammaSelectionByte};
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof VideoParameters)) {
            return false;
        }
        VideoParameters that = (VideoParameters)object;
        return new EqualsBuilder().append(this.brightness, that.brightness).append(this.color, that.color).append(this.contrast, that.contrast).append(this.sharpness, that.sharpness).append(this.hue, that.hue).append(this.backlight, that.backlight).append(this.gammaSelection, that.gammaSelection).isEquals();
    }

    public int hashCode() {
        return Objects.hash(this.brightness, this.color, this.contrast, this.sharpness, this.hue, this.backlight, this.gammaSelection);
    }

    public String toString() {
        return new ToStringBuilder(this).append("brightness", this.brightness).append("color", this.color).append("contrast", this.contrast).append("sharpness", this.sharpness).append("hue", this.hue).append("backlight", this.backlight).append("gammaSelection", this.gammaSelection).toString();
    }

    public static enum GammaSelection implements Convertible
    {
        NATIVE(1),
        S(2),
        TWO_DOT_TWO(3),
        TWO_DOT_FOUR(4),
        D_IMAGE(5);

        private byte data;

        private GammaSelection(byte data) {
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

