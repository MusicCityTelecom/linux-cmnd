/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.service;

import be.tpvision.smartcontrol.domain.Device;
import be.tpvision.smartcontrol.domain.device_settings.input_sources.AutoSignalDetecting;
import be.tpvision.smartcontrol.domain.device_settings.input_sources.Failovers;
import be.tpvision.smartcontrol.domain.device_settings.input_sources.InputSource;

public interface InputSourcesService {
    public InputSource getInputSource(Device var1);

    public void setInputSource(Device var1, InputSource var2);

    public AutoSignalDetecting getAutoSignalDetecting(Device var1);

    public void setAutoSignalDetecting(Device var1, AutoSignalDetecting var2);

    public Failovers getFailovers(Device var1);

    public void setFailovers(Device var1, Failovers var2);
}

