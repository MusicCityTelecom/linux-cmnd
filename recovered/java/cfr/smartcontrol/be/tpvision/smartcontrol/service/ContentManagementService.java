/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.service;

import be.tpvision.smartcontrol.domain.Hardware;
import be.tpvision.smartcontrol.repository.data_transfer_objects.DeviceDTO;
import java.nio.file.Path;

public interface ContentManagementService {
    public String getContentFileName(String var1);

    public void updateContent();

    public void updateContent(Hardware var1);

    public void updateContent(boolean var1, boolean var2);

    public void updateContent(String var1, boolean var2, boolean var3);

    public void setContent(long var1, String var3);

    public void unassignContent(long var1);

    public void deleteTempContent();

    public void deleteHardwarePath(String var1);

    public Path getHardwareCmsPath(String var1);

    public boolean isHardwareBusy(String var1);

    public Path getRevisionPath(String var1, boolean var2);

    public void uploadSystemConfigToFtp(DeviceDTO var1);
}

