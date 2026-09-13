/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.view_models.device_limits;

import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.Tiling;
import be.tpvision.smartcontrol.rest.view_models.device_limits.EnumLimitsViewModel;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class TilingLimitsViewModel {
    private EnumLimitsViewModel<Tiling.Enable> enable;
    private EnumLimitsViewModel<Tiling.FrameComp> frameComp;

    protected TilingLimitsViewModel() {
        this(new EnumLimitsViewModel<Tiling.Enable>(Tiling.Enable.class), new EnumLimitsViewModel<Tiling.FrameComp>(Tiling.FrameComp.class));
    }

    public TilingLimitsViewModel(EnumLimitsViewModel<Tiling.Enable> enable, EnumLimitsViewModel<Tiling.FrameComp> frameComp) {
        this.enable = enable;
        this.frameComp = frameComp;
    }

    public EnumLimitsViewModel<Tiling.Enable> getEnable() {
        return this.enable;
    }

    public void setEnable(EnumLimitsViewModel<Tiling.Enable> enable) {
        this.enable = enable;
    }

    public EnumLimitsViewModel<Tiling.FrameComp> getFrameComp() {
        return this.frameComp;
    }

    public void setFrameComp(EnumLimitsViewModel<Tiling.FrameComp> frameComp) {
        this.frameComp = frameComp;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof TilingLimitsViewModel)) {
            return false;
        }
        TilingLimitsViewModel that = (TilingLimitsViewModel)object;
        return new EqualsBuilder().append(this.getEnable(), that.getEnable()).append(this.getFrameComp(), that.getFrameComp()).isEquals();
    }

    public int hashCode() {
        return Objects.hash(this.getEnable(), this.getFrameComp());
    }

    public String toString() {
        return new ToStringBuilder(this).append("enable", this.getEnable()).append("frameComp", this.getFrameComp()).toString();
    }
}

