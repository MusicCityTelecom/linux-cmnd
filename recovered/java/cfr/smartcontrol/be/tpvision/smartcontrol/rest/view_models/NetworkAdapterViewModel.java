/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.view_models;

import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class NetworkAdapterViewModel {
    private String displayName;
    private String ipv4Address;

    protected NetworkAdapterViewModel() {
    }

    public NetworkAdapterViewModel(String displayName, String ipv4Address) {
        this.displayName = displayName;
        this.ipv4Address = ipv4Address;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getIpv4Address() {
        return this.ipv4Address;
    }

    public void setIpv4Address(String ipv4Address) {
        this.ipv4Address = ipv4Address;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof NetworkAdapterViewModel)) {
            return false;
        }
        NetworkAdapterViewModel that = (NetworkAdapterViewModel)object;
        return new EqualsBuilder().append(this.displayName, that.displayName).append(this.ipv4Address, that.ipv4Address).isEquals();
    }

    public int hashCode() {
        return Objects.hash(this.displayName, this.ipv4Address);
    }

    public String toString() {
        return new ToStringBuilder(this).append("displayName", this.displayName).append("ipv4Address", this.ipv4Address).toString();
    }
}

