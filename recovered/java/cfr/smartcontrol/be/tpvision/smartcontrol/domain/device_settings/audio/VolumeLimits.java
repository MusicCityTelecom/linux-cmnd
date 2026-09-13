/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.domain.device_settings.audio;

import be.tpvision.smartcontrol.domain.device_settings.DeviceSetting;
import be.tpvision.smartcontrol.util.ValueUtilities;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class VolumeLimits
implements DeviceSetting {
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

    protected void setMinimum(int minimum) {
        this.minimum = minimum;
    }

    public int getMaximum() {
        return this.maximum;
    }

    protected void setMaximum(int maximum) {
        this.maximum = maximum;
    }

    public int getSwitchOn() {
        return this.switchOn;
    }

    public void setSwitchOn(int switchOn) {
        this.switchOn = ValueUtilities.getValue(switchOn, this.minimum, this.maximum);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof VolumeLimits)) {
            return false;
        }
        VolumeLimits that = (VolumeLimits)object;
        return new EqualsBuilder().append(this.getMinimum(), that.getMinimum()).append(this.getMaximum(), that.getMaximum()).append(this.getSwitchOn(), that.getSwitchOn()).isEquals();
    }

    public int hashCode() {
        return Objects.hash(this.getMinimum(), this.getMaximum(), this.getSwitchOn());
    }

    public String toString() {
        return new ToStringBuilder(this).append("minimum", this.getMinimum()).append("maximum", this.getMaximum()).append("switchOn", this.getSwitchOn()).toString();
    }
}

