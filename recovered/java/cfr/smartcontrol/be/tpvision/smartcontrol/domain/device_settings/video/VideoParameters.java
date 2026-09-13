/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.domain.device_settings.video;

import be.tpvision.smartcontrol.domain.device_settings.DeviceSetting;
import be.tpvision.smartcontrol.messages.domain.device_settings.video.video_parameters.SetBacklightMessages;
import be.tpvision.smartcontrol.messages.domain.device_settings.video.video_parameters.SetBrightnessMessages;
import be.tpvision.smartcontrol.messages.domain.device_settings.video.video_parameters.SetColorMessages;
import be.tpvision.smartcontrol.messages.domain.device_settings.video.video_parameters.SetContrastMessages;
import be.tpvision.smartcontrol.messages.domain.device_settings.video.video_parameters.SetGammaSelectionMessages;
import be.tpvision.smartcontrol.messages.domain.device_settings.video.video_parameters.SetSharpnessMessages;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

public class VideoParameters
implements DeviceSetting {
    private int brightness;
    private int color;
    private int contrast;
    private int sharpness;
    private int hue;
    private int backlight;
    private GammaSelection gammaSelection;

    protected VideoParameters() {
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
        Assert.isTrue(brightness >= 0, SetBrightnessMessages.BRIGHTNESS_HAS_TO_BE_A_POSITIVE_NUMBER);
        this.brightness = brightness;
    }

    public int getColor() {
        return this.color;
    }

    public void setColor(int color) {
        Assert.isTrue(color >= 0, SetColorMessages.COLOR_HAS_TO_BE_A_POSITIVE_NUMBER);
        this.color = color;
    }

    public int getContrast() {
        return this.contrast;
    }

    public void setContrast(int contrast) {
        Assert.isTrue(contrast >= 0, SetContrastMessages.CONTRAST_HAS_TO_BE_A_POSITIVE_NUMBER);
        this.contrast = contrast;
    }

    public int getSharpness() {
        return this.sharpness;
    }

    public void setSharpness(int sharpness) {
        Assert.isTrue(sharpness >= 0, SetSharpnessMessages.SHARPNESS_HAS_TO_BE_A_POSITIVE_NUMBER);
        this.sharpness = sharpness;
    }

    public int getHue() {
        return this.hue;
    }

    public void setHue(int hue) {
        this.hue = hue;
    }

    public int getBacklight() {
        return this.backlight;
    }

    public void setBacklight(int backlight) {
        Assert.isTrue(backlight >= 0, SetBacklightMessages.BACKLIGHT_HAS_TO_BE_A_POSITIVE_NUMBER);
        this.backlight = backlight;
    }

    public GammaSelection getGammaSelection() {
        return this.gammaSelection;
    }

    public void setGammaSelection(GammaSelection gammaSelection) {
        Assert.notNull((Object)(gammaSelection != null ? 1 : 0), SetGammaSelectionMessages.GAMMA_SELECTION_CAN_NOT_BE_NULL);
        this.gammaSelection = gammaSelection;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof VideoParameters)) {
            return false;
        }
        VideoParameters that = (VideoParameters)object;
        return new EqualsBuilder().append(this.getBrightness(), that.getBrightness()).append(this.getColor(), that.getColor()).append(this.getContrast(), that.getContrast()).append(this.getSharpness(), that.getSharpness()).append(this.getHue(), that.getHue()).append(this.getBacklight(), that.getBacklight()).append((Object)this.getGammaSelection(), (Object)that.getGammaSelection()).isEquals();
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.getBrightness(), this.getColor(), this.getContrast(), this.getSharpness(), this.getHue(), this.getBacklight(), this.getGammaSelection()});
    }

    public String toString() {
        return new ToStringBuilder(this).append("brightness", this.getBrightness()).append("color", this.getColor()).append("contrast", this.getContrast()).append("sharpness", this.getSharpness()).append("hue", this.getHue()).append("backlight", this.getBacklight()).append("gammaSelection", (Object)this.getGammaSelection()).toString();
    }

    public static enum GammaSelection {
        NATIVE,
        S,
        TWO_DOT_TWO,
        TWO_DOT_FOUR,
        D_IMAGE;

    }
}

