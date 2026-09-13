/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.protocol.todelete;

import be.tpvision.smartcontrol.util.Convertibles;
import be.tpvision.smartcontrol.util.ValueUtilities;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class VGAVideoParameters
implements Convertibles {
    public static final int MINIMUM_VALUE = 0;
    public static final int MAXIMUM_VALUE = 100;
    private int clock;
    private int clockPhase;
    private int hPosition;
    private int vPosition;

    public VGAVideoParameters() {
        this(0, 0, 0, 0);
    }

    public VGAVideoParameters(int clock, int clockPhase, int hPosition, int vPosition) {
        this.setClock(clock);
        this.setClockPhase(clockPhase);
        this.sethPosition(hPosition);
        this.setvPosition(vPosition);
    }

    public int getClock() {
        return this.clock;
    }

    public void setClock(int clock) {
        this.clock = this.getValue(clock);
    }

    public int getClockPhase() {
        return this.clockPhase;
    }

    public void setClockPhase(int clockPhase) {
        this.clockPhase = this.getValue(clockPhase);
    }

    public int gethPosition() {
        return this.hPosition;
    }

    public void sethPosition(int hPosition) {
        this.hPosition = this.getValue(hPosition);
    }

    public int getvPosition() {
        return this.vPosition;
    }

    public void setvPosition(int vPosition) {
        this.vPosition = this.getValue(vPosition);
    }

    private int getValue(int value) {
        return ValueUtilities.getValue(value, 0, 100);
    }

    @Override
    public byte[] convert() {
        byte clockByte = (byte)this.clock;
        byte clockPhaseByte = (byte)this.clockPhase;
        byte hPositionByte = (byte)this.hPosition;
        byte vPositionByte = (byte)this.vPosition;
        return new byte[]{clockByte, clockPhaseByte, hPositionByte, vPositionByte};
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof VGAVideoParameters)) {
            return false;
        }
        VGAVideoParameters that = (VGAVideoParameters)object;
        return new EqualsBuilder().append(this.clock, that.clock).append(this.clockPhase, that.clockPhase).append(this.hPosition, that.hPosition).append(this.vPosition, that.vPosition).isEquals();
    }

    public int hashCode() {
        return Objects.hash(this.clock, this.clockPhase, this.hPosition, this.vPosition);
    }

    public String toString() {
        return new ToStringBuilder(this).append("clock", this.clock).append("clockPhase", this.clockPhase).append("hPosition", this.hPosition).append("vPosition", this.vPosition).toString();
    }
}

