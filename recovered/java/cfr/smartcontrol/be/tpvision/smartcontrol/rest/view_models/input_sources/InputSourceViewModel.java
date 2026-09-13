/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.view_models.input_sources;

import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class InputSourceViewModel {
    private String sourceType;
    private String tag;
    private String sourceLabel;

    protected InputSourceViewModel() {
    }

    public InputSourceViewModel(String sourceType, String sourceLabel) {
        this(sourceType, null, sourceLabel);
    }

    public InputSourceViewModel(String sourceType, String tag, String sourceLabel) {
        this.sourceType = sourceType;
        this.tag = tag;
        this.sourceLabel = sourceLabel;
    }

    public String getSourceType() {
        return this.sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getSourceLabel() {
        return this.sourceLabel;
    }

    public void setSourceLabel(String sourceLabel) {
        this.sourceLabel = sourceLabel;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof InputSourceViewModel)) {
            return false;
        }
        InputSourceViewModel that = (InputSourceViewModel)object;
        return new EqualsBuilder().append(this.getSourceType(), that.getSourceType()).append(this.getTag(), that.getTag()).append(this.getSourceLabel(), that.getSourceLabel()).isEquals();
    }

    public int hashCode() {
        return Objects.hash(this.getSourceType(), this.getTag(), this.getSourceLabel());
    }

    public String toString() {
        return new ToStringBuilder(this).append("sourceType", this.getSourceType()).append("tag", this.getTag()).append("sourceLabel", this.getSourceLabel()).toString();
    }
}

