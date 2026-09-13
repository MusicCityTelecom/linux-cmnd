/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.view_models.device_limits.input_sources;

import be.tpvision.smartcontrol.domain.device_settings.input_sources.InputSource;
import be.tpvision.smartcontrol.rest.view_models.device_limits.EnumLimitsViewModel;
import be.tpvision.smartcontrol.rest.view_models.device_limits.input_sources.SourceTypeLimitsViewModel;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class InputSourceLimitsViewModel {
    private SourceTypeLimitsViewModel sourceType;
    private EnumLimitsViewModel<InputSource.Tag> tag;
    private EnumLimitsViewModel<InputSource.SourceLabel> sourceLabel;

    public InputSourceLimitsViewModel() {
        this(new SourceTypeLimitsViewModel(), new EnumLimitsViewModel<InputSource.Tag>(InputSource.Tag.class), new EnumLimitsViewModel<InputSource.SourceLabel>(InputSource.SourceLabel.class));
    }

    public InputSourceLimitsViewModel(SourceTypeLimitsViewModel sourceType, EnumLimitsViewModel<InputSource.Tag> tag, EnumLimitsViewModel<InputSource.SourceLabel> sourceLabel) {
        this.sourceType = sourceType;
        this.tag = tag;
        this.sourceLabel = sourceLabel;
    }

    public SourceTypeLimitsViewModel getSourceType() {
        return this.sourceType;
    }

    public void setSourceType(SourceTypeLimitsViewModel sourceType) {
        this.sourceType = sourceType;
    }

    public EnumLimitsViewModel<InputSource.Tag> getTag() {
        return this.tag;
    }

    public void setTag(EnumLimitsViewModel<InputSource.Tag> tag) {
        this.tag = tag;
    }

    public EnumLimitsViewModel<InputSource.SourceLabel> getSourceLabel() {
        return this.sourceLabel;
    }

    public void setSourceLabel(EnumLimitsViewModel<InputSource.SourceLabel> sourceLabel) {
        this.sourceLabel = sourceLabel;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof InputSourceLimitsViewModel)) {
            return false;
        }
        InputSourceLimitsViewModel that = (InputSourceLimitsViewModel)object;
        return new EqualsBuilder().append(this.sourceType, that.sourceType).append(this.tag, that.tag).append(this.sourceLabel, that.sourceLabel).isEquals();
    }

    public int hashCode() {
        return Objects.hash(this.sourceType, this.tag, this.sourceLabel);
    }

    public String toString() {
        return new ToStringBuilder(this).append("sourceType", this.sourceType).append("tag", this.tag).append("sourceLabel", this.sourceLabel).toString();
    }
}

