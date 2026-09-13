/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.domain.device_settings;

import be.tpvision.smartcontrol.domain.device_settings.DeviceSetting;
import java.util.Objects;
import javax.persistence.Embeddable;

@Embeddable
public class StringWrapper
implements DeviceSetting {
    private String value;

    protected StringWrapper() {
    }

    public StringWrapper(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof StringWrapper)) {
            return false;
        }
        StringWrapper that = (StringWrapper)object;
        return Objects.equals(this.getValue(), that.getValue());
    }

    public int hashCode() {
        return Objects.hash(this.getValue());
    }

    public String toString() {
        return this.getValue();
    }
}

