/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.mappers.group;

import be.tpvision.smartcontrol.domain.Device;
import be.tpvision.smartcontrol.domain.Group;
import be.tpvision.smartcontrol.messages.mappers.group.group.ToDetectGroupsViewModelMessages;
import be.tpvision.smartcontrol.messages.mappers.group.group.ToGroupMessages;
import be.tpvision.smartcontrol.messages.mappers.group.group.ToGroupViewModelMessages;
import be.tpvision.smartcontrol.messages.mappers.group.group.ToImportExportGroupViewModelMessages;
import be.tpvision.smartcontrol.rest.mappers.device.DeviceMapper;
import be.tpvision.smartcontrol.rest.view_models.device.DetectDevicesViewModel;
import be.tpvision.smartcontrol.rest.view_models.device.GroupDeviceViewModel;
import be.tpvision.smartcontrol.rest.view_models.device.ImportExportDeviceViewModel;
import be.tpvision.smartcontrol.rest.view_models.group.DetectGroupsViewModel;
import be.tpvision.smartcontrol.rest.view_models.group.GroupViewModel;
import be.tpvision.smartcontrol.rest.view_models.group.ImportExportGroupViewModel;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.util.Assert;

public class GroupMapper {
    private GroupMapper() {
    }

    public static GroupViewModel toGroupViewModel(Group group) {
        Assert.notNull((Object)group, ToGroupViewModelMessages.GROUP_CAN_NOT_BE_NULL);
        Long groupId = group.getId();
        String groupName = group.getName();
        Set<Device> deviceSet = group.getDevices();
        List<GroupDeviceViewModel> groupDeviceViewModelList = deviceSet.stream().map(DeviceMapper::toGroupDeviceViewModel).filter(Objects::nonNull).collect(Collectors.toList());
        return new GroupViewModel(groupId, groupName, groupDeviceViewModelList);
    }

    public static Group toGroup(GroupViewModel groupViewModel) {
        Assert.notNull((Object)groupViewModel, ToGroupMessages.GROUP_VIEW_MODEL_CAN_NOT_BE_NULL);
        Long groupId = groupViewModel.getId();
        String groupName = groupViewModel.getName();
        List<GroupDeviceViewModel> groupDeviceViewModelList = groupViewModel.getDevices();
        Set<Device> deviceSet = groupDeviceViewModelList.stream().filter(Objects::nonNull).map(DeviceMapper::toDevice).filter(Objects::nonNull).collect(Collectors.toSet());
        Group group = new Group(groupName, deviceSet);
        group.setId(groupId);
        return group;
    }

    public static DetectGroupsViewModel toDetectGroupsViewModel(Group group) {
        Assert.notNull((Object)group, ToDetectGroupsViewModelMessages.GROUP_CAN_NOT_BE_NULL);
        String groupName = group.getName();
        Set<Device> deviceSet = group.getDevices();
        List<DetectDevicesViewModel> detectDevicesViewModelList = deviceSet.stream().map(DeviceMapper::toDetectDevicesViewModel).filter(Objects::nonNull).collect(Collectors.toList());
        return new DetectGroupsViewModel(groupName, detectDevicesViewModelList);
    }

    public static Group toGroup(DetectGroupsViewModel detectGroupsViewModel) {
        Assert.notNull((Object)detectGroupsViewModel, ToGroupMessages.DETECT_GROUPS_VIEW_MODEL_CAN_NOT_BE_NULL);
        String groupName = detectGroupsViewModel.getName();
        List<DetectDevicesViewModel> detectDevicesViewModelList = detectGroupsViewModel.getDevices();
        Set<Device> deviceSet = detectDevicesViewModelList.stream().filter(Objects::nonNull).map(DeviceMapper::toDevice).filter(Objects::nonNull).collect(Collectors.toSet());
        return new Group(groupName, deviceSet);
    }

    public static ImportExportGroupViewModel toImportExportGroupViewModel(Group group) {
        Assert.notNull((Object)group, ToImportExportGroupViewModelMessages.GROUP_CAN_NOT_BE_NULL);
        String groupName = group.getName();
        Set<Device> deviceSet = group.getDevices();
        List<ImportExportDeviceViewModel> importExportDeviceViewModelList = deviceSet.stream().map(DeviceMapper::toImportExportDeviceViewModel).filter(Objects::nonNull).collect(Collectors.toList());
        return new ImportExportGroupViewModel(groupName, importExportDeviceViewModelList);
    }

    public static Group toGroup(ImportExportGroupViewModel importExportGroupViewModel) {
        Assert.notNull((Object)importExportGroupViewModel, ToGroupMessages.IMPORT_EXPORT_GROUP_VIEW_MODEL_CAN_NOT_BE_NULL);
        String groupName = importExportGroupViewModel.getName();
        List<ImportExportDeviceViewModel> importExportDeviceViewModelList = importExportGroupViewModel.getDevices();
        Set deviceSet = importExportDeviceViewModelList.stream().filter(Objects::nonNull).map(DeviceMapper::toDevice).filter(Objects::nonNull).collect(Collectors.toCollection(LinkedHashSet::new));
        return new Group(groupName, deviceSet);
    }
}

