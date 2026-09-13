/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.protocol.todelete;

import be.tpvision.smartcontrol.util.Convertibles;
import be.tpvision.smartcontrol.util.ValueUtilities;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class VolumeLimits
implements Convertibles {
    public static final int MINIMUM_VALUE = 0;
    public static final int MAXIMUM_VALUE = 100;
    private int minimum;
    private int maximum;
    private int switchOn;

    public VolumeLimits(int minimum, int maximum, int switchOn) {
        this.setMinimum(minimum);
        this.setMaximum(maximum);
        this.setSwitchOn(switchOn);
    }

    public int getMinimum() {
        return this.minimum;
    }

    public void setMinimum(int minimum) {
        this.minimum = this.getValue(minimum);
    }

    public int getMaximum() {
        return this.maximum;
    }

    public void setMaximum(int maximum) {
        this.maximum = this.getValue(maximum);
    }

    public int getSwitchOn() {
        return this.switchOn;
    }

    public void setSwitchOn(int switchOn) {
        this.switchOn = this.getValue(switchOn);
    }

    private int getValue(int value) {
        return ValueUtilities.getValue(value, 0, 100);
    }

    @Override
    public byte[] convert() {
        byte minimumByte = (byte)this.minimum;
        byte maximumByte = (byte)this.maximum;
        byte switchOnByte = (byte)this.switchOn;
        return new byte[]{minimumByte, maximumByte, switchOnByte};
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof VolumeLimits)) {
            return false;
        }
        VolumeLimits that = (VolumeLimits)object;
        return new EqualsBuilder().append(this.minimum, that.minimum).append(this.maximum, that.maximum).append(this.switchOn, that.switchOn).isEquals();
    }

    public int hashCode() {
        return Objects.hash(this.minimum, this.maximum, this.switchOn);
    }

    public String toString() {
        return new ToStringBuilder(this).append("minimum", this.minimum).append("maximum", this.maximum).append("switchOn", this.switchOn).toString();
    }
}

