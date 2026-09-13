/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.mappers.device;

import be.tpvision.smartcontrol.domain.Device;
import be.tpvision.smartcontrol.messages.mappers.device.device_result.ToImportDeviceResultViewModelMessages;
import be.tpvision.smartcontrol.rest.mappers.device.DeviceMapper;
import be.tpvision.smartcontrol.rest.view_models.ImportDeviceResultViewModel;
import be.tpvision.smartcontrol.rest.view_models.device.ImportExportDeviceViewModel;
import be.tpvision.smartcontrol.service.exceptions.AddDeviceResult;
import org.springframework.util.Assert;

public class DeviceResultMapper {
    private DeviceResultMapper() {
    }

    public static ImportDeviceResultViewModel toImportDeviceResultViewModel(AddDeviceResult addDeviceResult) {
        Assert.notNull((Object)addDeviceResult, ToImportDeviceResultViewModelMessages.ADD_DEVICE_RESULT_CAN_NOT_BE_NULL);
        ImportDeviceResultViewModel importDeviceResultViewModel = new ImportDeviceResultViewModel();
        Device device = addDeviceResult.getDevice();
        ImportExportDeviceViewModel importExportDeviceViewModel = DeviceMapper.toImportExportDeviceViewModel(device);
        importDeviceResultViewModel.setImportExportDeviceViewModel(importExportDeviceViewModel);
        AddDeviceResult.Result domainResult = addDeviceResult.getResult();
        String viewModelResult = domainResult.getName();
        importDeviceResultViewModel.setResult(viewModelResult);
        String message = addDeviceResult.getMessage();
        importDeviceResultViewModel.setMessage(message);
        return importDeviceResultViewModel;
    }
}

