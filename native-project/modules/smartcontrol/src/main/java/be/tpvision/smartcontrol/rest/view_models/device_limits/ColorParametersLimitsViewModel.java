package be.tpvision.smartcontrol.rest.view_models.device_limits;

import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ColorParametersLimitsViewModel {
   private ColorParametersLimitsViewModel.ColorLimitsViewModel red;
   private ColorParametersLimitsViewModel.ColorLimitsViewModel green;
   private ColorParametersLimitsViewModel.ColorLimitsViewModel blue;

   protected ColorParametersLimitsViewModel() {
      this(null, null, null);
      ColorParametersLimitsViewModel.ColorLimitsViewModel red = new ColorParametersLimitsViewModel.ColorLimitsViewModel();
      this.setRed(red);
      ColorParametersLimitsViewModel.ColorLimitsViewModel green = new ColorParametersLimitsViewModel.ColorLimitsViewModel();
      this.setGreen(green);
      ColorParametersLimitsViewModel.ColorLimitsViewModel blue = new ColorParametersLimitsViewModel.ColorLimitsViewModel();
      this.setBlue(blue);
   }

   public ColorParametersLimitsViewModel(
      final ColorParametersLimitsViewModel.ColorLimitsViewModel red,
      final ColorParametersLimitsViewModel.ColorLimitsViewModel green,
      final ColorParametersLimitsViewModel.ColorLimitsViewModel blue
   ) {
      this.red = red;
      this.green = green;
      this.blue = blue;
   }

   public ColorParametersLimitsViewModel.ColorLimitsViewModel getRed() {
      return this.red;
   }

   public void setRed(ColorParametersLimitsViewModel.ColorLimitsViewModel red) {
      this.red = red;
   }

   public ColorParametersLimitsViewModel.ColorLimitsViewModel getGreen() {
      return this.green;
   }

   public void setGreen(ColorParametersLimitsViewModel.ColorLimitsViewModel green) {
      this.green = green;
   }

   public ColorParametersLimitsViewModel.ColorLimitsViewModel getBlue() {
      return this.blue;
   }

   public void setBlue(ColorParametersLimitsViewModel.ColorLimitsViewModel blue) {
      this.blue = blue;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof ColorParametersLimitsViewModel)) {
         return false;
      }

      ColorParametersLimitsViewModel that = (ColorParametersLimitsViewModel)object;
      return new EqualsBuilder().append(this.red, that.red).append(this.green, that.green).append(this.blue, that.blue).isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.red, this.green, this.blue);
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this).append("red", this.red).append("green", this.green).append("blue", this.blue).toString();
   }

   public class ColorLimitsViewModel {
      private LimitsViewModel gain;
      private LimitsViewModel offset;

      public ColorLimitsViewModel() {
         this(new LimitsViewModel(0, 255), new LimitsViewModel(0, 255));
      }

      public ColorLimitsViewModel(final LimitsViewModel gain, final LimitsViewModel offset) {
         this.gain = gain;
         this.offset = offset;
      }

      public LimitsViewModel getGain() {
         return this.gain;
      }

      public void setGain(final LimitsViewModel gain) {
         this.gain = gain;
      }

      public LimitsViewModel getOffset() {
         return this.offset;
      }

      public void setOffset(final LimitsViewModel offset) {
         this.offset = offset;
      }

      @Override
      public boolean equals(final Object object) {
         if (this == object) {
            return true;
         }

         if (!(object instanceof ColorParametersLimitsViewModel.ColorLimitsViewModel)) {
            return false;
         }

         ColorParametersLimitsViewModel.ColorLimitsViewModel that = (ColorParametersLimitsViewModel.ColorLimitsViewModel)object;
         return new EqualsBuilder().append(this.gain, that.gain).append(this.offset, that.offset).isEquals();
      }

      @Override
      public int hashCode() {
         return Objects.hash(this.gain, this.offset);
      }

      @Override
      public String toString() {
         return new ToStringBuilder(this).append("gain", this.gain).append("offset", this.offset).toString();
      }
   }
}
