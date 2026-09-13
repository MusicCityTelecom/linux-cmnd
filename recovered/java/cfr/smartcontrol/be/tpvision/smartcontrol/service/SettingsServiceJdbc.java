/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.service;

import be.tpvision.smartcontrol.domain.FtpSettings;

public interface SettingsServiceJdbc {
    public String getServerIp();

    public FtpSettings getDefaultFtpSettings();
}

