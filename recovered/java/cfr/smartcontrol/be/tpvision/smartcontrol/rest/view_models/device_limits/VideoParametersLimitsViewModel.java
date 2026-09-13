/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.view_models.device_limits;

import be.tpvision.smartcontrol.domain.device_settings.video.VideoParameters;
import be.tpvision.smartcontrol.rest.view_models.device_limits.EnumLimitsViewModel;
import be.tpvision.smartcontrol.rest.view_models.device_limits.LimitsViewModel;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class VideoParametersLimitsViewModel {
    private LimitsViewModel brightness;
    private LimitsViewModel color;
    private LimitsViewModel contrast;
    private LimitsViewModel sharpness;
    private LimitsViewModel hue;
    private LimitsViewModel backlight;
    private EnumLimitsViewModel<VideoParameters.GammaSelection> gammaSelection;

    public VideoParametersLimitsViewModel() {
        this(new LimitsViewModel(0, 100), new LimitsViewModel(0, 100), new LimitsViewModel(0, 100), new LimitsViewModel(0, 10), new LimitsViewModel(-50, 50), new LimitsViewModel(0, 100), new EnumLimitsViewModel<VideoParameters.GammaSelection>(VideoParameters.GammaSelection.class));
    }

    public VideoParametersLimitsViewModel(LimitsViewModel brightness, LimitsViewModel color, LimitsViewModel contrast, LimitsViewModel sharpness, LimitsViewModel hue, LimitsViewModel backlight, EnumLimitsViewModel<VideoParameters.GammaSelection> gammaSelection) {
        this.brightness = brightness;
        this.color = color;
        this.contrast = contrast;
        this.sharpness = sharpness;
        this.hue = hue;
        this.backlight = backlight;
        this.gammaSelection = gammaSelection;
    }

    public LimitsViewModel getBrightness() {
        return this.brightness;
    }

    public void setBrightness(LimitsViewModel brightness) {
        this.brightness = brightness;
    }

    public LimitsViewModel getColor() {
        return this.color;
    }

    public void setColor(LimitsViewModel color) {
        this.color = color;
    }

    public LimitsViewModel getContrast() {
        return this.contrast;
    }

    public void setContrast(LimitsViewModel contrast) {
        this.contrast = contrast;
    }

    public LimitsViewModel getSharpness() {
        return this.sharpness;
    }

    public void setSharpness(LimitsViewModel sharpness) {
        this.sharpness = sharpness;
    }

    public LimitsViewModel getHue() {
        return this.hue;
    }

    public void setHue(LimitsViewModel hue) {
        this.hue = hue;
    }

    public LimitsViewModel getBacklight() {
        return this.backlight;
    }

    public void setBacklight(LimitsViewModel backlight) {
        this.backlight = backlight;
    }

    public EnumLimitsViewModel<VideoParameters.GammaSelection> getGammaSelection() {
        return this.gammaSelection;
    }

    public void setGammaSelection(EnumLimitsViewModel<VideoParameters.GammaSelection> gammaSelection) {
        this.gammaSelection = gammaSelection;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof VideoParametersLimitsViewModel)) {
            return false;
        }
        VideoParametersLimitsViewModel that = (VideoParametersLimitsViewModel)object;
        return new EqualsBuilder().append(this.brightness, that.brightness).append(this.color, that.color).append(this.contrast, that.contrast).append(this.sharpness, that.sharpness).append(this.hue, that.hue).append(this.backlight, that.backlight).append(this.gammaSelection, that.gammaSelection).isEquals();
    }

    public int hashCode() {
        return Objects.hash(this.brightness, this.color, this.contrast, this.sharpness, this.hue, this.backlight, this.gammaSelection);
    }

    public String toString() {
        return new ToStringBuilder(this).append("brightness", this.brightness).append("color", this.color).append("contrast", this.contrast).append("sharpness", this.sharpness).append("hue", this.hue).append("backlight", this.backlight).append("gammaSelection", this.gammaSelection).toString();
    }
}

