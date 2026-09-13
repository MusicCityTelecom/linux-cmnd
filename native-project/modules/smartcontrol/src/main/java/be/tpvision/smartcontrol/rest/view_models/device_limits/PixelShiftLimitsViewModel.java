package be.tpvision.smartcontrol.rest.view_models.device_limits;

import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.PixelShift;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class PixelShiftLimitsViewModel extends IncrementalLimitsViewModel {
   private EnumLimitsViewModel<PixelShift.State> state = new EnumLimitsViewModel<>(PixelShift.State.class);

   public PixelShiftLimitsViewModel(int minimum, int maximum, int step) {
      super(minimum, maximum, step);
   }

   public EnumLimitsViewModel<PixelShift.State> getState() {
      return this.state;
   }

   public void setState(EnumLimitsViewModel<PixelShift.State> state) {
      this.state = state;
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof PixelShiftLimitsViewModel)) {
         return false;
      }

      PixelShiftLimitsViewModel that = (PixelShiftLimitsViewModel)object;
      return new EqualsBuilder().appendSuper(super.equals(object)).append(this.state, that.state).isEquals();
   }

   @Override
   public int hashCode() {
      return new HashCodeBuilder().appendSuper(super.hashCode()).append(this.state).toHashCode();
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this).append(super.toString()).append("state", this.state).toString();
   }
}
