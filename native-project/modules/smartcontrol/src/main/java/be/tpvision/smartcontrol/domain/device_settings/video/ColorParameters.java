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

public class ColorParameters implements DeviceSetting {
   private ColorParameters.Color red;
   private ColorParameters.Color green;
   private ColorParameters.Color blue;

   protected ColorParameters() {
      this.red = this.green = this.blue = null;
   }

   public ColorParameters(final ColorParameters.Color red, final ColorParameters.Color green, final ColorParameters.Color blue) {
      this.setRed(red);
      this.setGreen(green);
      this.setBlue(blue);
   }

   public ColorParameters.Color getRed() {
      return this.red;
   }

   public void setRed(final ColorParameters.Color red) {
      Assert.notNull(red, SetRedMessages.RED_CAN_NOT_BE_NULL);
      this.red = red;
   }

   public ColorParameters.Color getGreen() {
      return this.green;
   }

   public void setGreen(final ColorParameters.Color green) {
      Assert.notNull(green, SetGreenMessages.GREEN_CAN_NOT_BE_NULL);
      this.green = green;
   }

   public ColorParameters.Color getBlue() {
      return this.blue;
   }

   public void setBlue(final ColorParameters.Color blue) {
      Assert.notNull(blue, SetBlueMessages.BLUE_CAN_NOT_BE_NULL);
      this.blue = blue;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof ColorParameters)) {
         return false;
      }

      ColorParameters that = (ColorParameters)object;
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

   public static class Color {
      private int gain;
      private int offset;

      protected Color() {
      }

      public Color(final int gain, final int offset) {
         this.setGain(gain);
         this.setOffset(offset);
      }

      public int getGain() {
         return this.gain;
      }

      public void setGain(final int gain) {
         Assert.isTrue(gain >= 0, SetGainMessages.GAIN_HAS_TO_BE_A_POSITIVE_NUMBER);
         this.gain = gain;
      }

      public int getOffset() {
         return this.offset;
      }

      public void setOffset(final int offset) {
         Assert.isTrue(offset >= 0, SetOffsetMessages.OFFSET_HAS_TO_BE_A_POSITIVE_NUMBER);
         this.offset = offset;
      }

      @Override
      public boolean equals(final Object object) {
         if (this == object) {
            return true;
         }

         if (!(object instanceof ColorParameters.Color)) {
            return false;
         }

         ColorParameters.Color color = (ColorParameters.Color)object;
         return new EqualsBuilder().append(this.getGain(), color.getGain()).append(this.getOffset(), color.getOffset()).isEquals();
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
