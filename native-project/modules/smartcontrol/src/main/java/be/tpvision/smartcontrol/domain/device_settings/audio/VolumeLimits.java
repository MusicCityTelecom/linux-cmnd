package be.tpvision.smartcontrol.domain.device_settings.audio;

import be.tpvision.smartcontrol.domain.device_settings.DeviceSetting;
import be.tpvision.smartcontrol.util.ValueUtilities;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class VolumeLimits implements DeviceSetting {
   private int minimum;
   private int maximum;
   private int switchOn;

   public VolumeLimits(final int minimum, final int maximum, final int switchOn) {
      this.setMinimum(minimum);
      this.setMaximum(maximum);
      this.setSwitchOn(switchOn);
   }

   public int getMinimum() {
      return this.minimum;
   }

   protected void setMinimum(final int minimum) {
      this.minimum = minimum;
   }

   public int getMaximum() {
      return this.maximum;
   }

   protected void setMaximum(final int maximum) {
      this.maximum = maximum;
   }

   public int getSwitchOn() {
      return this.switchOn;
   }

   public void setSwitchOn(final int switchOn) {
      this.switchOn = ValueUtilities.getValue(switchOn, this.minimum, this.maximum);
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof VolumeLimits)) {
         return false;
      }

      VolumeLimits that = (VolumeLimits)object;
      return new EqualsBuilder()
         .append(this.getMinimum(), that.getMinimum())
         .append(this.getMaximum(), that.getMaximum())
         .append(this.getSwitchOn(), that.getSwitchOn())
         .isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getMinimum(), this.getMaximum(), this.getSwitchOn());
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this)
         .append("minimum", this.getMinimum())
         .append("maximum", this.getMaximum())
         .append("switchOn", this.getSwitchOn())
         .toString();
   }
}
