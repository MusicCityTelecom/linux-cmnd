package be.tpvision.smartcontrol.rest.view_models.device_limits;

import be.tpvision.smartcontrol.domain.device_settings.video.VideoParameters;
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
      this(
         new LimitsViewModel(0, 100),
         new LimitsViewModel(0, 100),
         new LimitsViewModel(0, 100),
         new LimitsViewModel(0, 10),
         new LimitsViewModel(-50, 50),
         new LimitsViewModel(0, 100),
         new EnumLimitsViewModel<>(VideoParameters.GammaSelection.class)
      );
   }

   public VideoParametersLimitsViewModel(
      final LimitsViewModel brightness,
      final LimitsViewModel color,
      final LimitsViewModel contrast,
      final LimitsViewModel sharpness,
      final LimitsViewModel hue,
      final LimitsViewModel backlight,
      final EnumLimitsViewModel<VideoParameters.GammaSelection> gammaSelection
   ) {
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

   public void setBrightness(final LimitsViewModel brightness) {
      this.brightness = brightness;
   }

   public LimitsViewModel getColor() {
      return this.color;
   }

   public void setColor(final LimitsViewModel color) {
      this.color = color;
   }

   public LimitsViewModel getContrast() {
      return this.contrast;
   }

   public void setContrast(final LimitsViewModel contrast) {
      this.contrast = contrast;
   }

   public LimitsViewModel getSharpness() {
      return this.sharpness;
   }

   public void setSharpness(final LimitsViewModel sharpness) {
      this.sharpness = sharpness;
   }

   public LimitsViewModel getHue() {
      return this.hue;
   }

   public void setHue(final LimitsViewModel hue) {
      this.hue = hue;
   }

   public LimitsViewModel getBacklight() {
      return this.backlight;
   }

   public void setBacklight(final LimitsViewModel backlight) {
      this.backlight = backlight;
   }

   public EnumLimitsViewModel<VideoParameters.GammaSelection> getGammaSelection() {
      return this.gammaSelection;
   }

   public void setGammaSelection(final EnumLimitsViewModel<VideoParameters.GammaSelection> gammaSelection) {
      this.gammaSelection = gammaSelection;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof VideoParametersLimitsViewModel)) {
         return false;
      }

      VideoParametersLimitsViewModel that = (VideoParametersLimitsViewModel)object;
      return new EqualsBuilder()
         .append(this.brightness, that.brightness)
         .append(this.color, that.color)
         .append(this.contrast, that.contrast)
         .append(this.sharpness, that.sharpness)
         .append(this.hue, that.hue)
         .append(this.backlight, that.backlight)
         .append(this.gammaSelection, that.gammaSelection)
         .isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.brightness, this.color, this.contrast, this.sharpness, this.hue, this.backlight, this.gammaSelection);
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this)
         .append("brightness", this.brightness)
         .append("color", this.color)
         .append("contrast", this.contrast)
         .append("sharpness", this.sharpness)
         .append("hue", this.hue)
         .append("backlight", this.backlight)
         .append("gammaSelection", this.gammaSelection)
         .toString();
   }
}
