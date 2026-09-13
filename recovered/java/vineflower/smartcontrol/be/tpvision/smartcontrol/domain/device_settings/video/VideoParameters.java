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

public class VideoParameters implements DeviceSetting {
   private int brightness;
   private int color;
   private int contrast;
   private int sharpness;
   private int hue;
   private int backlight;
   private VideoParameters.GammaSelection gammaSelection;

   protected VideoParameters() {
   }

   public VideoParameters(
      final int brightness,
      final int color,
      final int contrast,
      final int sharpness,
      final int hue,
      final int backlight,
      final VideoParameters.GammaSelection gammaSelection
   ) {
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

   public void setBrightness(final int brightness) {
      Assert.isTrue(brightness >= 0, SetBrightnessMessages.BRIGHTNESS_HAS_TO_BE_A_POSITIVE_NUMBER);
      this.brightness = brightness;
   }

   public int getColor() {
      return this.color;
   }

   public void setColor(final int color) {
      Assert.isTrue(color >= 0, SetColorMessages.COLOR_HAS_TO_BE_A_POSITIVE_NUMBER);
      this.color = color;
   }

   public int getContrast() {
      return this.contrast;
   }

   public void setContrast(final int contrast) {
      Assert.isTrue(contrast >= 0, SetContrastMessages.CONTRAST_HAS_TO_BE_A_POSITIVE_NUMBER);
      this.contrast = contrast;
   }

   public int getSharpness() {
      return this.sharpness;
   }

   public void setSharpness(final int sharpness) {
      Assert.isTrue(sharpness >= 0, SetSharpnessMessages.SHARPNESS_HAS_TO_BE_A_POSITIVE_NUMBER);
      this.sharpness = sharpness;
   }

   public int getHue() {
      return this.hue;
   }

   public void setHue(final int hue) {
      this.hue = hue;
   }

   public int getBacklight() {
      return this.backlight;
   }

   public void setBacklight(final int backlight) {
      Assert.isTrue(backlight >= 0, SetBacklightMessages.BACKLIGHT_HAS_TO_BE_A_POSITIVE_NUMBER);
      this.backlight = backlight;
   }

   public VideoParameters.GammaSelection getGammaSelection() {
      return this.gammaSelection;
   }

   public void setGammaSelection(final VideoParameters.GammaSelection gammaSelection) {
      Assert.notNull(gammaSelection != null, SetGammaSelectionMessages.GAMMA_SELECTION_CAN_NOT_BE_NULL);
      this.gammaSelection = gammaSelection;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof VideoParameters)) {
         return false;
      }

      VideoParameters that = (VideoParameters)object;
      return new EqualsBuilder()
         .append(this.getBrightness(), that.getBrightness())
         .append(this.getColor(), that.getColor())
         .append(this.getContrast(), that.getContrast())
         .append(this.getSharpness(), that.getSharpness())
         .append(this.getHue(), that.getHue())
         .append(this.getBacklight(), that.getBacklight())
         .append(this.getGammaSelection(), that.getGammaSelection())
         .isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(
         this.getBrightness(), this.getColor(), this.getContrast(), this.getSharpness(), this.getHue(), this.getBacklight(), this.getGammaSelection()
      );
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this)
         .append("brightness", this.getBrightness())
         .append("color", this.getColor())
         .append("contrast", this.getContrast())
         .append("sharpness", this.getSharpness())
         .append("hue", this.getHue())
         .append("backlight", this.getBacklight())
         .append("gammaSelection", this.getGammaSelection())
         .toString();
   }

   public enum GammaSelection {
      NATIVE,
      S,
      TWO_DOT_TWO,
      TWO_DOT_FOUR,
      D_IMAGE;
   }
}
