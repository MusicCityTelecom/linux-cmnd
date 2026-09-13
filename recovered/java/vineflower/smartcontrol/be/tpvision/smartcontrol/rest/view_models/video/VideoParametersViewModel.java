package be.tpvision.smartcontrol.rest.view_models.video;

import be.tpvision.smartcontrol.messages.view_models.video.video_parameters.SetGammaSelectionMessages;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

public class VideoParametersViewModel {
   private int brightness;
   private int color;
   private int contrast;
   private int sharpness;
   private int hue;
   private int backlight;
   private String gammaSelection;

   protected VideoParametersViewModel() {
   }

   public VideoParametersViewModel(
      final int brightness, final int color, final int contrast, final int sharpness, final int hue, final int backlight, final String gammaSelection
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
      this.brightness = brightness;
   }

   public int getColor() {
      return this.color;
   }

   public void setColor(final int color) {
      this.color = color;
   }

   public int getContrast() {
      return this.contrast;
   }

   public void setContrast(final int contrast) {
      this.contrast = contrast;
   }

   public int getSharpness() {
      return this.sharpness;
   }

   public void setSharpness(final int sharpness) {
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
      this.backlight = backlight;
   }

   public String getGammaSelection() {
      return this.gammaSelection;
   }

   public void setGammaSelection(final String gammaSelection) {
      Assert.notNull(gammaSelection, SetGammaSelectionMessages.GAMMA_SELECTION_CAN_NOT_BE_NULL);
      this.gammaSelection = gammaSelection;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof VideoParametersViewModel)) {
         return false;
      }

      VideoParametersViewModel that = (VideoParametersViewModel)object;
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
}
