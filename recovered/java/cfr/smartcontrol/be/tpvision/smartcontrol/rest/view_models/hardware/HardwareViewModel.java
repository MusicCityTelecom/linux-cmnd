/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.view_models.hardware;

import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class HardwareViewModel {
    private String hardwareKey;
    private String contentId;

    public String getHardwareKey() {
        return this.hardwareKey;
    }

    public void setHardwareKey(String hardwareKey) {
        this.hardwareKey = hardwareKey;
    }

    public String getContentId() {
        return this.contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof HardwareViewModel)) {
            return false;
        }
        HardwareViewModel that = (HardwareViewModel)object;
        return new EqualsBuilder().append(this.getHardwareKey(), that.getHardwareKey()).append(this.getContentId(), that.getContentId()).isEquals();
    }

    public int hashCode() {
        return Objects.hash(this.getHardwareKey(), this.getContentId());
    }

    public String toString() {
        return new ToStringBuilder(this).append("hardwareKey", this.getHardwareKey()).append("contentId", this.getContentId()).toString();
    }
}

