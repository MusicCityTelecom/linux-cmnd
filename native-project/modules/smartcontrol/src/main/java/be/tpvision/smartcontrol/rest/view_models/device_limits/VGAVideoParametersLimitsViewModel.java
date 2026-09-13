package be.tpvision.smartcontrol.rest.view_models.device_limits;

import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class VGAVideoParametersLimitsViewModel {
   private LimitsViewModel clock;
   private LimitsViewModel clockPhase;
   private LimitsViewModel hPosition;
   private LimitsViewModel vPosition;

   public VGAVideoParametersLimitsViewModel() {
      this(new LimitsViewModel(0, 100), new LimitsViewModel(0, 100), new LimitsViewModel(0, 100), new LimitsViewModel(0, 100));
   }

   public VGAVideoParametersLimitsViewModel(
      final LimitsViewModel clock, final LimitsViewModel clockPhase, final LimitsViewModel hPosition, final LimitsViewModel vPosition
   ) {
      this.clock = clock;
      this.clockPhase = clockPhase;
      this.hPosition = hPosition;
      this.vPosition = vPosition;
   }

   public LimitsViewModel getClock() {
      return this.clock;
   }

   public void setClock(final LimitsViewModel clock) {
      this.clock = clock;
   }

   public LimitsViewModel getClockPhase() {
      return this.clockPhase;
   }

   public void setClockPhase(final LimitsViewModel clockPhase) {
      this.clockPhase = clockPhase;
   }

   public LimitsViewModel gethPosition() {
      return this.hPosition;
   }

   public void sethPosition(final LimitsViewModel hPosition) {
      this.hPosition = hPosition;
   }

   public LimitsViewModel getvPosition() {
      return this.vPosition;
   }

   public void setvPosition(final LimitsViewModel vPosition) {
      this.vPosition = vPosition;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof VGAVideoParametersLimitsViewModel)) {
         return false;
      }

      VGAVideoParametersLimitsViewModel that = (VGAVideoParametersLimitsViewModel)object;
      return new EqualsBuilder()
         .append(this.clock, that.clock)
         .append(this.clockPhase, that.clockPhase)
         .append(this.hPosition, that.hPosition)
         .append(this.vPosition, that.vPosition)
         .isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.clock, this.clockPhase, this.hPosition, this.vPosition);
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this)
         .append("clock", this.clock)
         .append("clockPhase", this.clockPhase)
         .append("hPosition", this.hPosition)
         .append("vPosition", this.vPosition)
         .toString();
   }
}
