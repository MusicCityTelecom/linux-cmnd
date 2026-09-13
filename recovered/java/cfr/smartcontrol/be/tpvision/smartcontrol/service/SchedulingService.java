/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.service;

import be.tpvision.smartcontrol.domain.Device;
import be.tpvision.smartcontrol.domain.device_settings.IntWrapper;
import be.tpvision.smartcontrol.domain.device_settings.scheduling.scheduling_parameters.Page;

public interface SchedulingService {
    public Page getSchedulingParametersPage(Device var1, IntWrapper var2);

    public void setSchedulingParametersPage(Device var1, Page var2);
}

