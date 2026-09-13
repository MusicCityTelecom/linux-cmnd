package be.tpvision.smartcontrol.rest.view_models.video;

import be.tpvision.smartcontrol.messages.view_models.video.color_parameters.SetBlueMessages;
import be.tpvision.smartcontrol.messages.view_models.video.color_parameters.SetGreenMessages;
import be.tpvision.smartcontrol.messages.view_models.video.color_parameters.SetRedMessages;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

public class ColorParametersViewModel {
   private ColorParametersViewModel.ColorViewModel red;
   private ColorParametersViewModel.ColorViewModel green;
   private ColorParametersViewModel.ColorViewModel blue;

   protected ColorParametersViewModel() {
   }

   public ColorParametersViewModel(
      final ColorParametersViewModel.ColorViewModel red,
      final ColorParametersViewModel.ColorViewModel green,
      final ColorParametersViewModel.ColorViewModel blue
   ) {
      this.setRed(red);
      this.setGreen(green);
      this.setBlue(blue);
   }

   public ColorParametersViewModel.ColorViewModel getRed() {
      return this.red;
   }

   public void setRed(final ColorParametersViewModel.ColorViewModel red) {
      Assert.notNull(red, SetRedMessages.RED_CAN_NOT_BE_NULL);
      this.red = red;
   }

   public ColorParametersViewModel.ColorViewModel getGreen() {
      return this.green;
   }

   public void setGreen(final ColorParametersViewModel.ColorViewModel green) {
      Assert.notNull(green, SetGreenMessages.GREEN_CAN_NOT_BE_NULL);
      this.green = green;
   }

   public ColorParametersViewModel.ColorViewModel getBlue() {
      return this.blue;
   }

   public void setBlue(final ColorParametersViewModel.ColorViewModel blue) {
      Assert.notNull(blue, SetBlueMessages.BLUE_CAN_NOT_BE_NULL);
      this.blue = blue;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof ColorParametersViewModel)) {
         return false;
      }

      ColorParametersViewModel that = (ColorParametersViewModel)object;
      return new EqualsBuilder()
         .append(this.getRed(), that.getRed())
         .append(this.getGreen(), that.getGreen())
         .append(this.getBlue(), that.getBlue())
         .isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getRed(), this.getGreen(), this.getBlue());
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this).append("red", this.getRed()).append("green", this.getGreen()).append("blue", this.getBlue()).toString();
   }

   public static class ColorViewModel {
      private int gain;
      private int offset;

      protected ColorViewModel() {
      }

      public ColorViewModel(final int gain, final int offset) {
         this.setGain(gain);
         this.setOffset(offset);
      }

      public int getGain() {
         return this.gain;
      }

      public void setGain(final int gain) {
         this.gain = gain;
      }

      public int getOffset() {
         return this.offset;
      }

      public void setOffset(final int offset) {
         this.offset = offset;
      }

      @Override
      public boolean equals(final Object object) {
         if (this == object) {
            return true;
         }

         if (!(object instanceof ColorParametersViewModel.ColorViewModel)) {
            return false;
         }

         ColorParametersViewModel.ColorViewModel that = (ColorParametersViewModel.ColorViewModel)object;
         return new EqualsBuilder().append(this.getGain(), that.getGain()).append(this.getOffset(), that.getOffset()).isEquals();
      }

      @Override
      public int hashCode() {
         return Objects.hash(this.getGain(), this.getOffset());
      }

      @Override
      public String toString() {
         return new ToStringBuilder(this).append("gain", this.getGain()).append("offset", this.getOffset()).toString();
      }
   }
}
