/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.mappers.twa;

import be.tpvision.smartcontrol.domain.twa.DeviceAddress;
import be.tpvision.smartcontrol.messages.mappers.twa.device_address.ToDeviceAddressMessages;
import be.tpvision.smartcontrol.rest.view_models.twa.DeviceAddressViewModel;
import org.springframework.util.Assert;

public class DeviceAddressMapper {
    private DeviceAddressMapper() {
    }

    public static DeviceAddress toDeviceAddress(DeviceAddressViewModel deviceAddressViewModel) {
        Assert.notNull((Object)deviceAddressViewModel, ToDeviceAddressMessages.DEVICE_ADDRESS_VIEW_MODEL_CAN_NOT_BE_NULL);
        int x = deviceAddressViewModel.getX();
        int y = deviceAddressViewModel.getY();
        return new DeviceAddress(x, y);
    }
}

