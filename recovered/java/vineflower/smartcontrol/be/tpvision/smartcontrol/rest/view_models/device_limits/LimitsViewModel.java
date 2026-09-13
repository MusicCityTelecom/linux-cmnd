package be.tpvision.smartcontrol.rest.view_models.device_limits;

import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class LimitsViewModel {
   private int minimum;
   private int maximum;

   protected LimitsViewModel() {
      this(0, 0);
   }

   public LimitsViewModel(final int minimum, final int maximum) {
      this.minimum = minimum;
      this.maximum = maximum;
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

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof LimitsViewModel)) {
         return false;
      }

      LimitsViewModel limits = (LimitsViewModel)object;
      return new EqualsBuilder().append(this.minimum, limits.minimum).append(this.maximum, limits.maximum).isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.minimum, this.maximum);
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this).append("minimum", this.minimum).append("maximum", this.maximum).toString();
   }
}
