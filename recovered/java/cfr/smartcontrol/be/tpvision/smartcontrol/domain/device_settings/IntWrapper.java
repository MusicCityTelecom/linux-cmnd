/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.domain.device_settings;

import be.tpvision.smartcontrol.domain.device_settings.DeviceSetting;
import java.util.Objects;
import javax.persistence.Embeddable;

@Embeddable
public class IntWrapper
implements DeviceSetting {
    private int value;

    protected IntWrapper() {
    }

    public IntWrapper(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof IntWrapper)) {
            return false;
        }
        IntWrapper that = (IntWrapper)object;
        return Objects.equals(this.getValue(), that.getValue());
    }

    public int hashCode() {
        return Objects.hash(this.getValue());
    }

    public String toString() {
        return String.valueOf(this.getValue());
    }
}

