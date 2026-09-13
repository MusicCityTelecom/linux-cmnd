/*
 * Decompiled with CFR 0.152.
 */
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

public class VGAVideoParameters
implements DeviceSetting {
    private int clock;
    private int clockPhase;
    private int horizontalPosition;
    private int verticalPosition;

    public VGAVideoParameters(int clock, int clockPhase, int horizontalPosition, int verticalPosition) {
        this.setClock(clock);
        this.setClockPhase(clockPhase);
        this.setHorizontalPosition(horizontalPosition);
        this.setVerticalPosition(verticalPosition);
    }

    public int getClock() {
        return this.clock;
    }

    public void setClock(int clock) {
        Assert.isTrue(clock >= 0, SetClockMessages.CLOCK_CAN_NOT_BE_NULL);
        this.clock = clock;
    }

    public int getClockPhase() {
        return this.clockPhase;
    }

    public void setClockPhase(int clockPhase) {
        Assert.notNull((Object)clockPhase, SetClockPhaseMessages.CLOCK_PHASE_CAN_NOT_BE_NULL);
        this.clockPhase = clockPhase;
    }

    public int getHorizontalPosition() {
        return this.horizontalPosition;
    }

    public void setHorizontalPosition(int horizontalPosition) {
        Assert.isTrue(horizontalPosition >= 0, SetHorizontalPositionMessages.HORIZONTAL_POSITION_HAS_TO_BE_A_POSITIVE_NUMBER);
        this.horizontalPosition = horizontalPosition;
    }

    public int getVerticalPosition() {
        return this.verticalPosition;
    }

    public void setVerticalPosition(int verticalPosition) {
        Assert.notNull((Object)verticalPosition, SetVerticalPositionMessages.VERTICAL_POSITION_HAS_TO_BE_A_POSITIVE_NUMBER);
        this.verticalPosition = verticalPosition;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof VGAVideoParameters)) {
            return false;
        }
        VGAVideoParameters that = (VGAVideoParameters)object;
        return new EqualsBuilder().append(this.getClock(), that.getClock()).append(this.getClockPhase(), that.getClockPhase()).append(this.getHorizontalPosition(), that.getHorizontalPosition()).append(this.getVerticalPosition(), that.getVerticalPosition()).isEquals();
    }

    public int hashCode() {
        return Objects.hash(this.getClock(), this.getClockPhase(), this.getHorizontalPosition(), this.getVerticalPosition());
    }

    public String toString() {
        return new ToStringBuilder(this).append("clock", this.getClock()).append("clockPhase", this.getClockPhase()).append("horizontalPosition", this.getHorizontalPosition()).append("verticalPosition", this.getVerticalPosition()).toString();
    }
}

