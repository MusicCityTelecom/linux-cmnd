/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.service;

public interface DeviceDataManagementService {
    public void fetchInfoData();

    public void fetchOverviewData();

    public boolean isFetchInfoDataRunning();

    public boolean isFetchOverviewDataRunning();
}

