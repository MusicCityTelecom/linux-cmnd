/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.service;

import be.tpvision.smartcontrol.domain.Settings;
import be.tpvision.smartcontrol.io.ip.NetworkAdapter;
import java.util.List;

public interface SettingsService {
    public Settings getSettings();

    public void setSettings(Settings var1);

    public void setSettings(Settings var1, boolean var2);

    public List<NetworkAdapter> getNetworkAdapters();

    public void autoUpdateServerIp();
}

