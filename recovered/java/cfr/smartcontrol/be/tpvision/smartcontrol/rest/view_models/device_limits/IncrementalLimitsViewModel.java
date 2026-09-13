/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.view_models.device_limits;

import be.tpvision.smartcontrol.rest.view_models.device_limits.LimitsViewModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class IncrementalLimitsViewModel
extends LimitsViewModel {
    private Integer step;

    public IncrementalLimitsViewModel(int minimum, int maximum, int step) {
        super(minimum, maximum);
        this.step = step;
    }

    public Integer getStep() {
        return this.step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof IncrementalLimitsViewModel)) {
            return false;
        }
        IncrementalLimitsViewModel that = (IncrementalLimitsViewModel)object;
        return new EqualsBuilder().appendSuper(super.equals(object)).append(this.step, that.step).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(this.step).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("step", this.step).toString();
    }
}

