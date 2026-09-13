/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.domain.comparators.device;

import be.tpvision.smartcontrol.domain.Device;
import be.tpvision.smartcontrol.domain.comparators.DelegatingComparator;
import java.util.Comparator;

public class DeviceNameComparator
extends DelegatingComparator<Device> {
    public DeviceNameComparator(Comparator<String> nameComparator) {
        super(Comparator.comparing(Device::getName, nameComparator));
    }
}

