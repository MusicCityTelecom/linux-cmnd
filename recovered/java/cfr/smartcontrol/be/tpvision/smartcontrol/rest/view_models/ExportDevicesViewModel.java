/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.view_models;

import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ExportDevicesViewModel {
    private List<Long> devices;
    private List<Long> groups;

    public ExportDevicesViewModel() {
    }

    public ExportDevicesViewModel(List<Long> devices, List<Long> groups) {
        this.devices = devices;
        this.groups = groups;
    }

    public List<Long> getDevices() {
        return this.devices;
    }

    public void setDevices(List<Long> devices) {
        this.devices = devices;
    }

    public List<Long> getGroups() {
        return this.groups;
    }

    public void setGroups(List<Long> groups) {
        this.groups = groups;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof ExportDevicesViewModel)) {
            return false;
        }
        ExportDevicesViewModel that = (ExportDevicesViewModel)object;
        return new EqualsBuilder().append(this.getDevices(), that.getDevices()).append(this.getGroups(), that.getGroups()).isEquals();
    }

    public int hashCode() {
        return Objects.hash(this.getDevices(), this.getGroups());
    }

    public String toString() {
        return new ToStringBuilder(this).append("devices", this.getDevices()).append("groups", this.getGroups()).toString();
    }
}

