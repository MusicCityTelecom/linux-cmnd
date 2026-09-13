/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.view_models.miscellaneous;

import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class PixelShiftViewModel {
    private String state;
    private int value;

    protected PixelShiftViewModel() {
    }

    public PixelShiftViewModel(String state, int value) {
        this.state = state;
        this.value = value;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
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
        if (!(object instanceof PixelShiftViewModel)) {
            return false;
        }
        PixelShiftViewModel that = (PixelShiftViewModel)object;
        return new EqualsBuilder().append(this.state, that.state).append(this.value, that.value).isEquals();
    }

    public int hashCode() {
        return Objects.hash(this.state, this.value);
    }

    public String toString() {
        return new ToStringBuilder(this).append("state", this.state).append("value", this.value).toString();
    }
}

