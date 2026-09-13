/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.view_models.twa;

import be.tpvision.smartcontrol.rest.view_models.twa.TwaDeviceSettingViewModel;
import java.util.Objects;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class IntegerViewModel
implements TwaDeviceSettingViewModel {
    private Integer value;

    public IntegerViewModel() {
    }

    public IntegerViewModel(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return this.value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof IntegerViewModel)) {
            return false;
        }
        IntegerViewModel that = (IntegerViewModel)object;
        return Objects.equals(this.getValue(), that.getValue());
    }

    public int hashCode() {
        return Objects.hash(this.getValue());
    }

    public String toString() {
        return new ToStringBuilder(this).append("value", this.getValue()).toString();
    }
}

