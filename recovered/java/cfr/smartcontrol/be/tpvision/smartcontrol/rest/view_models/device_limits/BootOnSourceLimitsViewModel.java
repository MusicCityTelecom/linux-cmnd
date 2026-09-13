/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.view_models.device_limits;

import be.tpvision.smartcontrol.domain.device_settings.general.BootOnSource;
import be.tpvision.smartcontrol.rest.view_models.device_limits.EnumLimitsViewModel;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class BootOnSourceLimitsViewModel {
    private EnumLimitsViewModel<BootOnSource.VideoSourceType> videoSourceType;
    private EnumLimitsViewModel<BootOnSource.Tag> tag;

    public BootOnSourceLimitsViewModel() {
        this(new EnumLimitsViewModel<BootOnSource.VideoSourceType>(BootOnSource.VideoSourceType.class), new EnumLimitsViewModel<BootOnSource.Tag>(BootOnSource.Tag.class));
    }

    public BootOnSourceLimitsViewModel(EnumLimitsViewModel<BootOnSource.VideoSourceType> videoSourceType, EnumLimitsViewModel<BootOnSource.Tag> tag) {
        this.videoSourceType = videoSourceType;
        this.tag = tag;
    }

    public EnumLimitsViewModel<BootOnSource.VideoSourceType> getVideoSourceType() {
        return this.videoSourceType;
    }

    public void setVideoSourceType(EnumLimitsViewModel<BootOnSource.VideoSourceType> videoSourceType) {
        this.videoSourceType = videoSourceType;
    }

    public EnumLimitsViewModel<BootOnSource.Tag> getTag() {
        return this.tag;
    }

    public void setTag(EnumLimitsViewModel<BootOnSource.Tag> tag) {
        this.tag = tag;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof BootOnSourceLimitsViewModel)) {
            return false;
        }
        BootOnSourceLimitsViewModel that = (BootOnSourceLimitsViewModel)object;
        return new EqualsBuilder().append(this.getVideoSourceType(), that.getVideoSourceType()).append(this.getTag(), that.getTag()).isEquals();
    }

    public int hashCode() {
        return Objects.hash(this.getVideoSourceType(), this.getTag());
    }

    public String toString() {
        return new ToStringBuilder(this).append("videoSourceType", this.getVideoSourceType()).append("tag", this.getTag()).toString();
    }
}

