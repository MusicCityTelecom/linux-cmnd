/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.domain.comparators.device;

import be.tpvision.smartcontrol.domain.Device;
import be.tpvision.smartcontrol.domain.comparators.DelegatingComparator;
import java.util.Comparator;

public class DeviceIdComparator
extends DelegatingComparator<Device> {
    public DeviceIdComparator(Comparator<Long> idComparator) {
        super(Comparator.comparing(Device::getId, idComparator));
    }
}

