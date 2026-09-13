/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.domain.device_settings.miscellaneous;

import be.tpvision.smartcontrol.domain.device_settings.DeviceSetting;
import be.tpvision.smartcontrol.repository.converters.miscellaneous.pixel_shift.StateConverter;
import java.util.Objects;
import javax.persistence.Convert;

public class PixelShift
implements DeviceSetting {
    @Convert(converter=StateConverter.class)
    private State state;
    private int value;

    public PixelShift() {
    }

    public PixelShift(State state, int value) {
        this.state = state;
        this.value = value;
    }

    public State getState() {
        return this.state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        PixelShift that = (PixelShift)o;
        return this.getState() == that.getState() && Objects.equals(this.getValue(), that.getValue());
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.getState(), this.getValue()});
    }

    public String toString() {
        return "PixelShift{state=" + (Object)((Object)this.state) + ", value=" + this.value + '}';
    }

    public static enum State {
        CUSTOM,
        AUTO;

    }
}

