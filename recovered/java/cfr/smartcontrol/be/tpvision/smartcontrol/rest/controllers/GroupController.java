/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.controllers;

import be.tpvision.smartcontrol.domain.Device;
import be.tpvision.smartcontrol.domain.Group;
import be.tpvision.smartcontrol.messages.controllers.group.GetGroupDevicesMessages;
import be.tpvision.smartcontrol.messages.controllers.group.SendSystemConfigToFtpMessages;
import be.tpvision.smartcontrol.messages.controllers.group.SetContentMessages;
import be.tpvision.smartcontrol.repository.OrderDirection;
import be.tpvision.smartcontrol.repository.data_transfer_objects.DeviceDTO;
import be.tpvision.smartcontrol.repository.order_fields.device.OrderField;
import be.tpvision.smartcontrol.rest.mappers.group.GroupMapper;
import be.tpvision.smartcontrol.rest.view_models.device.GroupDeviceViewModel;
import be.tpvision.smartcontrol.rest.view_models.device.GroupDeviceViewModelList;
import be.tpvision.smartcontrol.rest.view_models.group.GroupViewModel;
import be.tpvision.smartcontrol.rest.view_models.group.GroupViewModelList;
import be.tpvision.smartcontrol.service.ContentManagementService;
import be.tpvision.smartcontrol.service.DeviceServiceJdbc;
import be.tpvision.smartcontrol.service.GroupService;
import be.tpvision.smartcontrol.util.ValueUtilities;
import com.google.common.base.CaseFormat;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/groups"})
public class GroupController {
    private final GroupService groupService;
    private final ContentManagementService contentManagementService;
    private final DeviceServiceJdbc deviceServiceJdbc;

    @Autowired
    public GroupController(GroupService groupService, ContentManagementService contentManagementService, DeviceServiceJdbc deviceServiceJdbc) {
        this.groupService = groupService;
        this.contentManagementService = contentManagementService;
        this.deviceServiceJdbc = deviceServiceJdbc;
    }

    @GetMapping
    public GroupViewModelList getGroups() {
        Set<Group> groupSet = this.groupService.getGroups();
        GroupViewModelList groupViewModels = new GroupViewModelList();
        for (Group group : groupSet) {
            GroupViewModel groupViewModel = GroupMapper.toGroupViewModel(group);
            groupViewModels.add(groupViewModel);
        }
        return groupViewModels;
    }

    @GetMapping(value={"/lazy"})
    public GroupViewModelList getGroupsWithoutDevices() {
        Set<Group> groupSet = this.groupService.getGroups();
        GroupViewModelList groupViewModels = new GroupViewModelList();
        for (Group group : groupSet) {
            GroupViewModel groupViewModel = GroupMapper.toGroupViewModel(group);
            groupViewModel.setDevices(null);
            groupViewModels.add(groupViewModel);
        }
        return groupViewModels;
    }

    @GetMapping(value={"/{groupId}"})
    public GroupViewModel getGroup(@PathVariable(value="groupId") long groupId, @RequestParam(value="orderBy", required=false) String orderBy, @RequestParam(value="orderDirection", required=false) String orderDirection) {
        OrderField orderField;
        boolean hasOrderBy = StringUtils.hasText(orderBy);
        if (hasOrderBy) {
            String enumInput = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, orderBy);
            orderField = Enum.valueOf(OrderField.class, enumInput);
        } else {
            orderField = OrderField.ID;
        }
        boolean hasOrderDirection = StringUtils.hasText(orderDirection);
        OrderDirection orderDirectionEnum = hasOrderDirection ? ValueUtilities.getEnumValue(OrderDirection.class, orderDirection) : OrderDirection.ASC;
        Group group = this.groupService.getGroup(groupId, orderField, orderDirectionEnum);
        return GroupMapper.toGroupViewModel(group);
    }

    @GetMapping(value={"/{groupId}/devices"})
    public GroupDeviceViewModelList getGroupDevices(@PathVariable(value="groupId") long groupId, @RequestParam(value="orderBy", required=false) String orderBy, @RequestParam(value="orderDirection", required=false) String orderDirection) {
        GroupViewModel groupViewModel = this.getGroup(groupId, orderBy, orderDirection);
        Assert.state(groupViewModel != null, GetGroupDevicesMessages.GROUP_VIEW_MODEL_CAN_NOT_BE_NULL);
        GroupDeviceViewModelList groupDeviceViewModelList = new GroupDeviceViewModelList();
        List<GroupDeviceViewModel> groupDeviceViewModels = groupViewModel.getDevices();
        Assert.state(groupDeviceViewModels != null, GetGroupDevicesMessages.GROUP_DEVICE_VIEW_MODELS_CAN_NOT_BE_NULL);
        groupDeviceViewModelList.addAll(groupDeviceViewModels);
        return groupDeviceViewModelList;
    }

    @PostMapping
    public void addGroup(@RequestBody GroupViewModel groupViewModel) {
        Group group = GroupMapper.toGroup(groupViewModel);
        this.groupService.addGroup(group);
    }

    @PutMapping(value={"/{groupId}"})
    public void updateGroup(@PathVariable(value="groupId") long groupId, @RequestBody GroupViewModel groupViewModel) {
        Group group = GroupMapper.toGroup(groupViewModel);
        this.groupService.updateGroup(group);
    }

    @DeleteMapping(value={"/{groupId}"})
    public void deleteGroup(@PathVariable(value="groupId") long groupId) {
        this.groupService.deleteGroup(groupId);
    }

    @PutMapping(value={"/{groupId}/content/{contentId}"})
    public void setContent(@PathVariable(value="groupId") long groupId, @PathVariable(value="contentId") String contentId) {
        Assert.state(this.contentManagementService != null, SetContentMessages.CONTENT_MANAGEMENT_SERVICE_CAN_NOT_BE_NULL);
        Assert.isTrue(groupId >= 0L, SetContentMessages.GROUP_ID_HAS_TO_BE_A_POSITIVE_NUMBER);
        Assert.notNull((Object)contentId, SetContentMessages.CONTENT_ID_CAN_NOT_BE_NULL);
        Assert.isTrue(!contentId.isEmpty(), SetContentMessages.CONTENT_ID_CAN_NOT_BE_EMPTY);
        this.groupService.getGroup(groupId).getDevices().stream().map(Device::getId).forEach(deviceId -> {
            Assert.state(deviceId != null, SetContentMessages.DEVICE_ID_CAN_NOT_BE_NULL);
            Assert.state(deviceId >= 0L, SetContentMessages.DEVICE_ID_HAS_TO_BE_A_POSITIVE_NUMBER);
            this.contentManagementService.setContent((long)deviceId, contentId);
        });
    }

    @PostMapping(value={"/{groupId}/content/sendToFtp"})
    public void sendSystemConfigToFtp(@PathVariable(value="groupId") long groupId) {
        Assert.state(this.contentManagementService != null, SendSystemConfigToFtpMessages.CONTENT_MANAGEMENT_SERVICE_CAN_NOT_BE_NULL);
        Assert.isTrue(groupId >= 0L, SendSystemConfigToFtpMessages.GROUP_ID_HAS_TO_BE_A_POSITIVE_NUMBER);
        this.groupService.getGroup(groupId).getDevices().stream().map(Device::getId).forEach(deviceId -> {
            DeviceDTO deviceDTO = this.deviceServiceJdbc.getDeviceDTO((long)deviceId);
            this.contentManagementService.uploadSystemConfigToFtp(deviceDTO);
        });
    }
}

