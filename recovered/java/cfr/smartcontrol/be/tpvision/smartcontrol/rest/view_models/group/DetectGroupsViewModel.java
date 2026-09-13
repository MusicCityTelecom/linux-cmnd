/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.view_models.group;

import be.tpvision.smartcontrol.rest.view_models.DeviceListItemViewModel;
import be.tpvision.smartcontrol.rest.view_models.device.DetectDevicesViewModel;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class DetectGroupsViewModel
implements DeviceListItemViewModel {
    private String name;
    private List<DetectDevicesViewModel> devices;

    protected DetectGroupsViewModel() {
    }

    public DetectGroupsViewModel(String name, List<DetectDevicesViewModel> devices) {
        this.name = name;
        this.devices = devices;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DetectDevicesViewModel> getDevices() {
        return this.devices;
    }

    public void setDevices(List<DetectDevicesViewModel> devices) {
        this.devices = devices;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof DetectGroupsViewModel)) {
            return false;
        }
        DetectGroupsViewModel that = (DetectGroupsViewModel)object;
        return new EqualsBuilder().append(this.getName(), that.getName()).append(this.getDevices(), that.getDevices()).isEquals();
    }

    public int hashCode() {
        return Objects.hash(this.getName(), this.getDevices());
    }

    public String toString() {
        return new ToStringBuilder(this).append("name", this.getName()).append("devices", this.getDevices()).toString();
    }
}

