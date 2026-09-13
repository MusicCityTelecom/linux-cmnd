/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.view_models.device_limits;

import be.tpvision.smartcontrol.rest.view_models.device_limits.LimitsViewModel;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class OSDInformationLimitsViewModel {
    private LimitsViewModel osdInformation;

    public OSDInformationLimitsViewModel() {
        this(new LimitsViewModel(0, 60));
    }

    public OSDInformationLimitsViewModel(LimitsViewModel osdInformation) {
        this.osdInformation = osdInformation;
    }

    public LimitsViewModel getOsdInformation() {
        return this.osdInformation;
    }

    public void setOsdInformation(LimitsViewModel osdInformation) {
        this.osdInformation = osdInformation;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof OSDInformationLimitsViewModel)) {
            return false;
        }
        OSDInformationLimitsViewModel that = (OSDInformationLimitsViewModel)object;
        return new EqualsBuilder().append(this.osdInformation, that.osdInformation).isEquals();
    }

    public int hashCode() {
        return Objects.hash(this.osdInformation);
    }

    public String toString() {
        return new ToStringBuilder(this).append("osdInformation", this.osdInformation).toString();
    }
}

