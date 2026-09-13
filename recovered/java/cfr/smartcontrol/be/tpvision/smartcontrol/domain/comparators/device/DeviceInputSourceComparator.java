/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.domain.comparators.device;

import be.tpvision.smartcontrol.domain.Device;
import be.tpvision.smartcontrol.domain.comparators.DelegatingComparator;
import be.tpvision.smartcontrol.domain.device_settings.input_sources.InputSource;
import java.util.Comparator;

public class DeviceInputSourceComparator
extends DelegatingComparator<Device> {
    public DeviceInputSourceComparator(Comparator<? super InputSource> inputSourceComparator) {
        super(Comparator.comparing(Device::getInputSource, inputSourceComparator));
    }
}

