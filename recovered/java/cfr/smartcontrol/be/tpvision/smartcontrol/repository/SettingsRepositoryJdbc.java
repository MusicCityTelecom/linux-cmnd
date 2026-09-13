/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.repository;

import be.tpvision.smartcontrol.domain.FtpSettings;

public interface SettingsRepositoryJdbc {
    public String getServerIp();

    public FtpSettings getDefaultFtpSettings();
}

