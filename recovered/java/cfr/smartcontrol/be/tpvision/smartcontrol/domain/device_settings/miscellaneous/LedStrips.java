/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.domain.device_settings.miscellaneous;

import be.tpvision.smartcontrol.domain.device_settings.DeviceSetting;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class LedStrips
implements DeviceSetting {
    private Status status;
    private int redValue;
    private int greenValue;
    private int blueValue;

    protected LedStrips() {
    }

    public LedStrips(Status status, int redValue, int greenValue, int blueValue) {
        this.status = status;
        this.redValue = redValue;
        this.greenValue = greenValue;
        this.blueValue = blueValue;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getRedValue() {
        return this.redValue;
    }

    public void setRedValue(int redValue) {
        this.redValue = redValue;
    }

    public int getGreenValue() {
        return this.greenValue;
    }

    public void setGreenValue(int greenValue) {
        this.greenValue = greenValue;
    }

    public int getBlueValue() {
        return this.blueValue;
    }

    public void setBlueValue(int blueValue) {
        this.blueValue = blueValue;
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.getStatus(), this.getRedValue(), this.getGreenValue(), this.getBlueValue()});
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof LedStrips)) {
            return false;
        }
        LedStrips that = (LedStrips)object;
        return new EqualsBuilder().append((Object)this.getStatus(), (Object)that.getStatus()).append(this.getRedValue(), that.getRedValue()).append(this.getGreenValue(), that.getGreenValue()).append(this.getBlueValue(), that.getBlueValue()).isEquals();
    }

    public String toString() {
        return new ToStringBuilder(this).append("status", (Object)this.getStatus()).append("redValue", this.getRedValue()).append("greenValue", this.getGreenValue()).append("blueValue", this.getBlueValue()).toString();
    }

    public static enum Status {
        ON,
        OFF;

    }
}

