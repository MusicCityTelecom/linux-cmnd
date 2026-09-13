package be.tpvision.smartcontrol.domain.device_settings.video;

import be.tpvision.smartcontrol.domain.device_settings.DeviceSetting;
import be.tpvision.smartcontrol.messages.domain.device_settings.video.vga_video_parameters.SetClockMessages;
import be.tpvision.smartcontrol.messages.domain.device_settings.video.vga_video_parameters.SetClockPhaseMessages;
import be.tpvision.smartcontrol.messages.domain.device_settings.video.vga_video_parameters.SetHorizontalPositionMessages;
import be.tpvision.smartcontrol.messages.domain.device_settings.video.vga_video_parameters.SetVerticalPositionMessages;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

public class VGAVideoParameters implements DeviceSetting {
   private int clock;
   private int clockPhase;
   private int horizontalPosition;
   private int verticalPosition;

   public VGAVideoParameters(final int clock, final int clockPhase, final int horizontalPosition, final int verticalPosition) {
      this.setClock(clock);
      this.setClockPhase(clockPhase);
      this.setHorizontalPosition(horizontalPosition);
      this.setVerticalPosition(verticalPosition);
   }

   public int getClock() {
      return this.clock;
   }

   public void setClock(final int clock) {
      Assert.isTrue(clock >= 0, SetClockMessages.CLOCK_CAN_NOT_BE_NULL);
      this.clock = clock;
   }

   public int getClockPhase() {
      return this.clockPhase;
   }

   public void setClockPhase(final int clockPhase) {
      Assert.notNull(clockPhase, SetClockPhaseMessages.CLOCK_PHASE_CAN_NOT_BE_NULL);
      this.clockPhase = clockPhase;
   }

   public int getHorizontalPosition() {
      return this.horizontalPosition;
   }

   public void setHorizontalPosition(final int horizontalPosition) {
      Assert.isTrue(horizontalPosition >= 0, SetHorizontalPositionMessages.HORIZONTAL_POSITION_HAS_TO_BE_A_POSITIVE_NUMBER);
      this.horizontalPosition = horizontalPosition;
   }

   public int getVerticalPosition() {
      return this.verticalPosition;
   }

   public void setVerticalPosition(final int verticalPosition) {
      Assert.notNull(verticalPosition, SetVerticalPositionMessages.VERTICAL_POSITION_HAS_TO_BE_A_POSITIVE_NUMBER);
      this.verticalPosition = verticalPosition;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof VGAVideoParameters)) {
         return false;
      }

      VGAVideoParameters that = (VGAVideoParameters)object;
      return new EqualsBuilder()
         .append(this.getClock(), that.getClock())
         .append(this.getClockPhase(), that.getClockPhase())
         .append(this.getHorizontalPosition(), that.getHorizontalPosition())
         .append(this.getVerticalPosition(), that.getVerticalPosition())
         .isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getClock(), this.getClockPhase(), this.getHorizontalPosition(), this.getVerticalPosition());
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this)
         .append("clock", this.getClock())
         .append("clockPhase", this.getClockPhase())
         .append("horizontalPosition", this.getHorizontalPosition())
         .append("verticalPosition", this.getVerticalPosition())
         .toString();
   }
}
