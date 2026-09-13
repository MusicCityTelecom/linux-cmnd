package be.tpvision.smartcontrol.domain.twa;

import be.tpvision.smartcontrol.messages.domain.twa.brightness.GetBlueMessages;
import be.tpvision.smartcontrol.messages.domain.twa.brightness.GetGreenMessages;
import be.tpvision.smartcontrol.messages.domain.twa.brightness.GetRedMessages;
import be.tpvision.smartcontrol.messages.domain.twa.brightness.SetBlueMessages;
import be.tpvision.smartcontrol.messages.domain.twa.brightness.SetGreenMessages;
import be.tpvision.smartcontrol.messages.domain.twa.brightness.SetRedMessages;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

public class Brightness implements TwaDeviceSetting {
   private int red;
   private int green;
   private int blue;

   public Brightness(final int red, final int green, final int blue) {
      this.setRed(red);
      this.setGreen(green);
      this.setBlue(blue);
   }

   public int getRed() {
      Assert.state(this.red >= 0, GetRedMessages.RED_HAS_TO_BE_A_POSITIVE_NUMBER);
      return this.red;
   }

   public void setRed(final int red) {
      Assert.isTrue(red >= 0, SetRedMessages.RED_HAS_TO_BE_A_POSITIVE_NUMBER);
      this.red = red;
   }

   public int getGreen() {
      Assert.state(this.green >= 0, GetGreenMessages.GREEN_HAS_TO_BE_A_POSITIVE_NUMBER);
      return this.green;
   }

   public void setGreen(final int green) {
      Assert.isTrue(green >= 0, SetGreenMessages.GREEN_HAS_TO_BE_A_POSITIVE_NUMBER);
      this.green = green;
   }

   public int getBlue() {
      Assert.state(this.blue >= 0, GetBlueMessages.BLUE_HAS_TO_BE_A_POSITIVE_NUMBER);
      return this.blue;
   }

   public void setBlue(final int blue) {
      Assert.isTrue(blue >= 0, SetBlueMessages.BLUE_HAS_TO_BE_A_POSITIVE_NUMBER);
      this.blue = blue;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof Brightness)) {
         return false;
      }

      Brightness that = (Brightness)object;
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
}
