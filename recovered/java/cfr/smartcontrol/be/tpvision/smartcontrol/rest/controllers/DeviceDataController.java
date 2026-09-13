/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.controllers;

import be.tpvision.smartcontrol.io.FetchInfoDataTaskAlreadyRunningException;
import be.tpvision.smartcontrol.io.FetchOverviewDataTaskAlreadyRunningException;
import be.tpvision.smartcontrol.messages.controllers.device_data.ConstructorMessages;
import be.tpvision.smartcontrol.rest.ResponseWrapper;
import be.tpvision.smartcontrol.service.DeviceDataManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/data/devices"})
public class DeviceDataController {
    private final DeviceDataManagementService deviceDataManagementService;

    @Autowired
    public DeviceDataController(DeviceDataManagementService deviceDataManagementService) {
        Assert.notNull((Object)deviceDataManagementService, ConstructorMessages.DEVICE_DATA_MANAGEMENT_SERVICE_CAN_NOT_BE_NULL);
        this.deviceDataManagementService = deviceDataManagementService;
    }

    private boolean isBackgroundTaskRunning() {
        boolean isFetchInfoDataTaskRunning = this.deviceDataManagementService.isFetchInfoDataRunning();
        boolean isFetchOverviewDataTaskRunning = this.deviceDataManagementService.isFetchOverviewDataRunning();
        return isFetchInfoDataTaskRunning || isFetchOverviewDataTaskRunning;
    }

    @GetMapping(value={"/info"})
    public void fetchInfoData() {
        try {
            this.deviceDataManagementService.fetchInfoData();
        }
        catch (FetchInfoDataTaskAlreadyRunningException e) {
            throw new FetchInfoDataTaskAlreadyRunningException();
        }
    }

    @GetMapping(value={"/overview"})
    public void fetchOverviewData() {
        this.deviceDataManagementService.fetchOverviewData();
    }

    @GetMapping
    public ResponseWrapper<Boolean> getBackgroundTaskRunning() {
        boolean isBackgroundTaskRunning = this.isBackgroundTaskRunning();
        return new ResponseWrapper<Boolean>(isBackgroundTaskRunning);
    }

    @PostMapping
    public void runBackgroundTask() {
        boolean isFetchInfoDataRunning = this.deviceDataManagementService.isFetchInfoDataRunning();
        if (isFetchInfoDataRunning) {
            throw new FetchInfoDataTaskAlreadyRunningException();
        }
        this.fetchInfoData();
        boolean isFetchOverviewDataRunning = this.deviceDataManagementService.isFetchOverviewDataRunning();
        if (isFetchOverviewDataRunning) {
            throw new FetchOverviewDataTaskAlreadyRunningException();
        }
        this.fetchOverviewData();
    }
}

