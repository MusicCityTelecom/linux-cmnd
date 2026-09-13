/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.mappers.group;

import be.tpvision.smartcontrol.domain.Group;
import be.tpvision.smartcontrol.messages.mappers.group.group_result.ToImportGroupResultViewModelMessages;
import be.tpvision.smartcontrol.rest.mappers.device.DeviceResultMapper;
import be.tpvision.smartcontrol.rest.mappers.group.GroupMapper;
import be.tpvision.smartcontrol.rest.view_models.ImportDeviceResultViewModelList;
import be.tpvision.smartcontrol.rest.view_models.ImportGroupResultViewModel;
import be.tpvision.smartcontrol.rest.view_models.group.ImportExportGroupViewModel;
import be.tpvision.smartcontrol.service.exceptions.AddGroupResult;
import java.util.stream.Collectors;
import org.springframework.util.Assert;

public class GroupResultMapper {
    private GroupResultMapper() {
    }

    public static ImportGroupResultViewModel toImportGroupResultViewModel(AddGroupResult addGroupResult) {
        Assert.notNull((Object)addGroupResult, ToImportGroupResultViewModelMessages.ADD_GROUP_RESULT_CAN_NOT_BE_NULL);
        ImportGroupResultViewModel importGroupResultViewModel = new ImportGroupResultViewModel();
        Group group = addGroupResult.getGroup();
        ImportExportGroupViewModel importExportGroupViewModel = GroupMapper.toImportExportGroupViewModel(group);
        importGroupResultViewModel.setImportExportGroupViewModel(importExportGroupViewModel);
        ImportDeviceResultViewModelList importDeviceResultViewModelList = addGroupResult.getAddDeviceResults().stream().map(DeviceResultMapper::toImportDeviceResultViewModel).collect(Collectors.toCollection(ImportDeviceResultViewModelList::new));
        importGroupResultViewModel.setImportDeviceResultViewModelList(importDeviceResultViewModelList);
        AddGroupResult.Result domainResult = addGroupResult.getResult();
        String viewModelResult = domainResult.getName();
        importGroupResultViewModel.setResult(viewModelResult);
        String message = addGroupResult.getMessage();
        importGroupResultViewModel.setMessage(message);
        return importGroupResultViewModel;
    }
}

