package be.tpvision.smartcontrol.rest.view_models.audio;

import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class VolumeLimitsViewModel {
   private int minimum;
   private int maximum;
   private int switchOn;

   protected VolumeLimitsViewModel() {
   }

   public VolumeLimitsViewModel(final int minimum, final int maximum, final int switchOn) {
      this.minimum = minimum;
      this.maximum = maximum;
      this.switchOn = switchOn;
   }

   public int getMinimum() {
      return this.minimum;
   }

   public void setMinimum(final int minimum) {
      this.minimum = minimum;
   }

   public int getMaximum() {
      return this.maximum;
   }

   public void setMaximum(final int maximum) {
      this.maximum = maximum;
   }

   public int getSwitchOn() {
      return this.switchOn;
   }

   public void setSwitchOn(final int switchOn) {
      this.switchOn = switchOn;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof VolumeLimitsViewModel)) {
         return false;
      }

      VolumeLimitsViewModel that = (VolumeLimitsViewModel)object;
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
