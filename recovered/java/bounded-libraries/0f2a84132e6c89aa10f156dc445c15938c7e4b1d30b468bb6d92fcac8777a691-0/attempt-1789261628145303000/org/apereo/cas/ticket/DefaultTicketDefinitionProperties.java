/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.ticket.TicketDefinitionProperties
 */
package org.apereo.cas.ticket;

import lombok.Generated;
import org.apereo.cas.ticket.TicketDefinitionProperties;

public class DefaultTicketDefinitionProperties
implements TicketDefinitionProperties {
    private boolean cascadeRemovals;
    private String storageName;
    private long storageTimeout;
    private String storagePassword;
    private boolean excludeFromCascade;

    @Generated
    public String toString() {
        return "DefaultTicketDefinitionProperties(cascadeRemovals=" + this.cascadeRemovals + ", storageName=" + this.storageName + ", storageTimeout=" + this.storageTimeout + ", storagePassword=" + this.storagePassword + ", excludeFromCascade=" + this.excludeFromCascade + ")";
    }

    @Generated
    public boolean isCascadeRemovals() {
        return this.cascadeRemovals;
    }

    @Generated
    public String getStorageName() {
        return this.storageName;
    }

    @Generated
    public long getStorageTimeout() {
        return this.storageTimeout;
    }

    @Generated
    public String getStoragePassword() {
        return this.storagePassword;
    }

    @Generated
    public boolean isExcludeFromCascade() {
        return this.excludeFromCascade;
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DefaultTicketDefinitionProperties)) {
            return false;
        }
        DefaultTicketDefinitionProperties other = (DefaultTicketDefinitionProperties)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.cascadeRemovals != other.cascadeRemovals) {
            return false;
        }
        if (this.storageTimeout != other.storageTimeout) {
            return false;
        }
        if (this.excludeFromCascade != other.excludeFromCascade) {
            return false;
        }
        String this$storageName = this.storageName;
        String other$storageName = other.storageName;
        if (this$storageName == null ? other$storageName != null : !this$storageName.equals(other$storageName)) {
            return false;
        }
        String this$storagePassword = this.storagePassword;
        String other$storagePassword = other.storagePassword;
        return !(this$storagePassword == null ? other$storagePassword != null : !this$storagePassword.equals(other$storagePassword));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof DefaultTicketDefinitionProperties;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + (this.cascadeRemovals ? 79 : 97);
        long $storageTimeout = this.storageTimeout;
        result = result * 59 + (int)($storageTimeout >>> 32 ^ $storageTimeout);
        result = result * 59 + (this.excludeFromCascade ? 79 : 97);
        String $storageName = this.storageName;
        result = result * 59 + ($storageName == null ? 43 : $storageName.hashCode());
        String $storagePassword = this.storagePassword;
        result = result * 59 + ($storagePassword == null ? 43 : $storagePassword.hashCode());
        return result;
    }

    @Generated
    public void setCascadeRemovals(boolean cascadeRemovals) {
        this.cascadeRemovals = cascadeRemovals;
    }

    @Generated
    public void setStorageName(String storageName) {
        this.storageName = storageName;
    }

    @Generated
    public void setStorageTimeout(long storageTimeout) {
        this.storageTimeout = storageTimeout;
    }

    @Generated
    public void setStoragePassword(String storagePassword) {
        this.storagePassword = storagePassword;
    }

    @Generated
    public void setExcludeFromCascade(boolean excludeFromCascade) {
        this.excludeFromCascade = excludeFromCascade;
    }
}

