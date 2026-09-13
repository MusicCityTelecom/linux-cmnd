/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.view_models.miscellaneous;

import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class MiscellaneousViewModel {
    private int operatingHours;

    protected MiscellaneousViewModel() {
    }

    public MiscellaneousViewModel(int operatingHours) {
        this.operatingHours = operatingHours;
    }

    public int getOperatingHours() {
        return this.operatingHours;
    }

    public void setOperatingHours(int operatingHours) {
        this.operatingHours = operatingHours;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof MiscellaneousViewModel)) {
            return false;
        }
        MiscellaneousViewModel that = (MiscellaneousViewModel)object;
        return new EqualsBuilder().append(this.getOperatingHours(), that.getOperatingHours()).isEquals();
    }

    public int hashCode() {
        return Objects.hash(this.getOperatingHours());
    }

    public String toString() {
        return new ToStringBuilder(this).append("operatingHours", this.getOperatingHours()).toString();
    }
}

