/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.view_models.twa;

import be.tpvision.smartcontrol.rest.view_models.twa.DeviceAddressViewModel;
import be.tpvision.smartcontrol.rest.view_models.twa.ModuleAddressViewModel;
import be.tpvision.smartcontrol.rest.view_models.twa.TwaDeviceSettingViewModel;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class TwaCommandViewModel {
    private DeviceAddressViewModel deviceAddress;
    private ModuleAddressViewModel moduleAddress;
    private TwaDeviceSettingViewModel twaDeviceSetting;

    public DeviceAddressViewModel getDeviceAddress() {
        return this.deviceAddress;
    }

    public void setDeviceAddress(DeviceAddressViewModel deviceAddress) {
        this.deviceAddress = deviceAddress;
    }

    public ModuleAddressViewModel getModuleAddress() {
        return this.moduleAddress;
    }

    public void setModuleAddress(ModuleAddressViewModel moduleAddress) {
        this.moduleAddress = moduleAddress;
    }

    public TwaDeviceSettingViewModel getTwaDeviceSetting() {
        return this.twaDeviceSetting;
    }

    public void setTwaDeviceSetting(TwaDeviceSettingViewModel twaDeviceSetting) {
        this.twaDeviceSetting = twaDeviceSetting;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof TwaCommandViewModel)) {
            return false;
        }
        TwaCommandViewModel that = (TwaCommandViewModel)object;
        return new EqualsBuilder().append(this.getDeviceAddress(), that.getDeviceAddress()).append(this.getModuleAddress(), that.getModuleAddress()).append(this.getTwaDeviceSetting(), that.getTwaDeviceSetting()).isEquals();
    }

    public int hashCode() {
        return Objects.hash(this.getDeviceAddress(), this.getModuleAddress(), this.getTwaDeviceSetting());
    }

    public String toString() {
        return new ToStringBuilder(this).append("deviceAddress", this.getDeviceAddress()).append("moduleAddress", this.getModuleAddress()).append("twaDeviceSetting", this.getTwaDeviceSetting()).toString();
    }
}

