/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.view_models.twa;

import be.tpvision.smartcontrol.rest.view_models.twa.DeviceAddressViewModel;
import be.tpvision.smartcontrol.rest.view_models.twa.TwaDeviceSettingViewModel;
import java.util.Objects;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class DeviceAddressWrapperViewModel
implements TwaDeviceSettingViewModel {
    private DeviceAddressViewModel deviceAddress;

    protected DeviceAddressWrapperViewModel() {
    }

    public DeviceAddressWrapperViewModel(DeviceAddressViewModel deviceAddress) {
        this.deviceAddress = deviceAddress;
    }

    public DeviceAddressViewModel getDeviceAddress() {
        return this.deviceAddress;
    }

    public void setDeviceAddress(DeviceAddressViewModel deviceAddress) {
        this.deviceAddress = deviceAddress;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof DeviceAddressWrapperViewModel)) {
            return false;
        }
        DeviceAddressWrapperViewModel that = (DeviceAddressWrapperViewModel)object;
        return Objects.equals(this.getDeviceAddress(), that.getDeviceAddress());
    }

    public int hashCode() {
        return Objects.hashCode(this.getDeviceAddress());
    }

    public String toString() {
        return new ToStringBuilder(this).append("deviceAddress", this.getDeviceAddress()).toString();
    }
}

