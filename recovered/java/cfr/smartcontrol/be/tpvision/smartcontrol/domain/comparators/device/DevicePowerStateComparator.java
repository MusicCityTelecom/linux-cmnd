/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.domain.comparators.device;

import be.tpvision.smartcontrol.domain.Device;
import be.tpvision.smartcontrol.domain.comparators.DelegatingComparator;
import be.tpvision.smartcontrol.domain.device_settings.general.PowerState;
import java.util.Comparator;

public class DevicePowerStateComparator
extends DelegatingComparator<Device> {
    public DevicePowerStateComparator(Comparator<? super PowerState> powerStateComparator) {
        super(Comparator.comparing(Device::getPowerState, powerStateComparator));
    }
}

