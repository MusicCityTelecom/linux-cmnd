/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.view_models.video;

import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class VGAVideoParametersViewModel {
    private int clock;
    private int clockPhase;
    private int horizontalPosition;
    private int verticalPosition;

    protected VGAVideoParametersViewModel() {
    }

    public VGAVideoParametersViewModel(int clock, int clockPhase, int horizontalPosition, int verticalPosition) {
        this.setClock(clock);
        this.setClockPhase(clockPhase);
        this.setHorizontalPosition(horizontalPosition);
        this.setVerticalPosition(verticalPosition);
    }

    public int getClock() {
        return this.clock;
    }

    public void setClock(int clock) {
        this.clock = clock;
    }

    public int getClockPhase() {
        return this.clockPhase;
    }

    public void setClockPhase(int clockPhase) {
        this.clockPhase = clockPhase;
    }

    public int getHorizontalPosition() {
        return this.horizontalPosition;
    }

    public void setHorizontalPosition(int horizontalPosition) {
        this.horizontalPosition = horizontalPosition;
    }

    public int getVerticalPosition() {
        return this.verticalPosition;
    }

    public void setVerticalPosition(int verticalPosition) {
        this.verticalPosition = verticalPosition;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof VGAVideoParametersViewModel)) {
            return false;
        }
        VGAVideoParametersViewModel that = (VGAVideoParametersViewModel)object;
        return new EqualsBuilder().append(this.getClock(), that.getClock()).append(this.getClockPhase(), that.getClockPhase()).append(this.getHorizontalPosition(), that.getHorizontalPosition()).append(this.getVerticalPosition(), that.getVerticalPosition()).isEquals();
    }

    public int hashCode() {
        return Objects.hash(this.getClock(), this.getClockPhase(), this.getHorizontalPosition(), this.getVerticalPosition());
    }

    public String toString() {
        return new ToStringBuilder(this).append("clock", this.getClock()).append("clockPhase", this.getClockPhase()).append("horizontalPosition", this.getHorizontalPosition()).append("verticalPosition", this.getVerticalPosition()).toString();
    }
}

