package be.tpvision.smartcontrol.protocol.todelete;

import be.tpvision.smartcontrol.util.Convertibles;
import be.tpvision.smartcontrol.util.ValueUtilities;
import java.nio.ByteBuffer;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ColorParameters implements Convertibles {
   private ColorParameters.Color red;
   private ColorParameters.Color green;
   private ColorParameters.Color blue;

   public ColorParameters() {
      this(null, null, null);
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
      this.red = red != null ? red : new ColorParameters.Color();
   }

   public ColorParameters.Color getGreen() {
      return this.green;
   }

   public void setGreen(final ColorParameters.Color green) {
      this.green = green != null ? green : new ColorParameters.Color();
   }

   public ColorParameters.Color getBlue() {
      return this.blue;
   }

   public void setBlue(final ColorParameters.Color blue) {
      this.blue = blue != null ? blue : new ColorParameters.Color();
   }

   @Override
   public byte[] convert() {
      ByteBuffer byteBuffer = ByteBuffer.allocate(6);
      byteBuffer.put((byte)this.red.gain);
      byteBuffer.put((byte)this.green.gain);
      byteBuffer.put((byte)this.blue.gain);
      byteBuffer.put((byte)this.red.offset);
      byteBuffer.put((byte)this.green.offset);
      byteBuffer.put((byte)this.blue.offset);
      return byteBuffer.array();
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

   public static class Color {
      public static final int MINIMUM_VALUE = 0;
      public static final int MAXIMUM_VALUE = 255;
      private int gain;
      private int offset;

      public Color() {
         this(0, 0);
      }

      public Color(final int gain, final int offset) {
         this.setGain(gain);
         this.setOffset(offset);
      }

      public int getGain() {
         return this.gain;
      }

      public void setGain(final int gain) {
         this.gain = this.getValue(gain);
      }

      public int getOffset() {
         return this.offset;
      }

      public void setOffset(final int offset) {
         this.offset = this.getValue(offset);
      }

      private int getValue(final int value) {
         return ValueUtilities.getValue(value, 0, 255);
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
         return new EqualsBuilder().append(this.gain, color.gain).append(this.offset, color.offset).isEquals();
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
