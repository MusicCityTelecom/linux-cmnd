/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.view_models.twa;

import be.tpvision.smartcontrol.rest.view_models.twa.TwaDeviceSettingViewModel;
import java.util.Objects;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class StringViewModel
implements TwaDeviceSettingViewModel {
    private String value;

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
        if (!(object instanceof StringViewModel)) {
            return false;
        }
        StringViewModel that = (StringViewModel)object;
        return Objects.equals(this.getValue(), that.getValue());
    }

    public int hashCode() {
        return Objects.hash(this.getValue());
    }

    public String toString() {
        return new ToStringBuilder(this).append("value", this.getValue()).toString();
    }
}

