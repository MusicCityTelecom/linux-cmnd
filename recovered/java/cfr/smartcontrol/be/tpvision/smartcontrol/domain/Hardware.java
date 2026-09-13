/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.domain;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@Table(name="hardware")
public class Hardware
implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
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
        if (!(object instanceof Hardware)) {
            return false;
        }
        Hardware hardware = (Hardware)object;
        return new EqualsBuilder().append(this.getHardwareKey(), hardware.getHardwareKey()).append(this.getContentId(), hardware.getContentId()).isEquals();
    }

    public int hashCode() {
        return Objects.hash(this.getHardwareKey(), this.getContentId());
    }

    public String toString() {
        return new ToStringBuilder(this).append("hardwareKey", this.getHardwareKey()).append("contentId", this.getContentId()).toString();
    }
}

