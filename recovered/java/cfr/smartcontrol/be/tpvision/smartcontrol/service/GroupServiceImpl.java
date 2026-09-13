/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.service;

import be.tpvision.smartcontrol.domain.Device;
import be.tpvision.smartcontrol.domain.Group;
import be.tpvision.smartcontrol.domain.device_settings.StringWrapper;
import be.tpvision.smartcontrol.io.ip.IpDestination;
import be.tpvision.smartcontrol.messages.services.group.AddGroupMessages;
import be.tpvision.smartcontrol.messages.services.group.ConstructorMessages;
import be.tpvision.smartcontrol.messages.services.group.DeleteGroupMessages;
import be.tpvision.smartcontrol.messages.services.group.UpdateGroupMessages;
import be.tpvision.smartcontrol.repository.DeviceRepository;
import be.tpvision.smartcontrol.repository.GroupRepository;
import be.tpvision.smartcontrol.repository.OrderDirection;
import be.tpvision.smartcontrol.repository.order_fields.device.OrderField;
import be.tpvision.smartcontrol.service.GroupService;
import be.tpvision.smartcontrol.service.exceptions.AddDeviceResult;
import be.tpvision.smartcontrol.service.exceptions.AddGroupResult;
import be.tpvision.smartcontrol.service.exceptions.FailedToAddGroupException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class GroupServiceImpl
implements GroupService {
    private final GroupRepository groupRepository;
    private final DeviceRepository deviceRepository;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository, DeviceRepository deviceRepository) {
        Assert.notNull((Object)groupRepository, ConstructorMessages.GROUP_REPOSITORY_CAN_NOT_BE_NULL);
        this.groupRepository = groupRepository;
        this.deviceRepository = deviceRepository;
    }

    @Override
    public Set<Group> getGroups() {
        List<Group> groupList = this.groupRepository.getAllOrderedByWithDevicesOrderedBy(be.tpvision.smartcontrol.repository.order_fields.group.OrderField.ID, OrderDirection.ASC, OrderField.ID, OrderDirection.ASC);
        LinkedHashSet<Group> groupSet = new LinkedHashSet<Group>();
        groupSet.addAll(groupList);
        return groupSet;
    }

    @Override
    public Group getGroup(long id) {
        return this.getGroup(id, OrderField.ID, OrderDirection.ASC);
    }

    @Override
    public Group getGroup(long id, be.tpvision.smartcontrol.repository.order_fields.OrderField<? super Device> deviceOrderField, OrderDirection deviceOrderDirection) {
        return this.groupRepository.getByIdWithDevicesOrderedBy(id, deviceOrderField, deviceOrderDirection);
    }

    @Override
    @Transactional(rollbackFor={FailedToAddGroupException.class})
    public AddGroupResult addGroup(Group group) {
        Assert.state(this.deviceRepository != null, AddGroupMessages.DEVICE_REPOSITORY_CAN_NOT_BE_NULL);
        Assert.notNull((Object)group, AddGroupMessages.GROUP_CAN_NOT_BE_NULL);
        Set<Device> deviceSet = group.getDevices();
        LinkedHashSet<Device> processedDeviceSet = new LinkedHashSet<Device>();
        ArrayList<AddDeviceResult> addDeviceResultList = new ArrayList<AddDeviceResult>();
        for (Device device : deviceSet) {
            String serialCodeValue;
            AddDeviceResult addDeviceResult;
            String message;
            AddDeviceResult.Result result;
            Long id = device.getId();
            if (id != null) {
                result = AddDeviceResult.Result.SUCCESS;
                message = String.format("Device with id %d already existed.", id);
                addDeviceResult = new AddDeviceResult(device, result, message);
                addDeviceResultList.add(addDeviceResult);
                processedDeviceSet.add(device);
                continue;
            }
            StringWrapper serialCode = device.getSerialCode();
            if (serialCode != null && (serialCodeValue = serialCode.getValue()) != null) {
                Device existingDevice = this.deviceRepository.getBySerialCode(serialCodeValue);
                result = AddDeviceResult.Result.SUCCESS;
                message = String.format("A device with serial code %s already existed.", serialCodeValue);
                addDeviceResult = new AddDeviceResult(device, result, message);
                addDeviceResultList.add(addDeviceResult);
                processedDeviceSet.add(existingDevice);
                continue;
            }
            try {
                this.deviceRepository.persist(device);
                result = AddDeviceResult.Result.SUCCESS;
                message = "Device added successfully.";
                processedDeviceSet.add(device);
            }
            catch (DataIntegrityViolationException e) {
                result = AddDeviceResult.Result.FAILED;
                IpDestination ipDestination = device.getAddress();
                InetSocketAddress inetSocketAddress = ipDestination.getAddress();
                InetAddress inetAddress = inetSocketAddress.getAddress();
                String ip = inetAddress.getHostAddress();
                int port = inetSocketAddress.getPort();
                int controlId = ipDestination.getControlId();
                message = String.format("Device %s:%d with control id %d already exists.", ip, port, controlId);
            }
            addDeviceResult = new AddDeviceResult(device, result, message);
            addDeviceResultList.add(addDeviceResult);
        }
        boolean failed = addDeviceResultList.stream().map(AddDeviceResult::getResult).anyMatch(AddDeviceResult.Result.FAILED::equals);
        if (failed) {
            AddGroupResult addGroupResult = new AddGroupResult(group, addDeviceResultList, AddGroupResult.Result.FAILED, "Failed to add group.");
            throw new FailedToAddGroupException(addGroupResult);
        }
        group.setDevices(processedDeviceSet);
        this.groupRepository.persist(group);
        AddGroupResult.Result result = AddGroupResult.Result.SUCCESS;
        String message = "Added group successfully.";
        return new AddGroupResult(group, addDeviceResultList, result, "Added group successfully.");
    }

    @Override
    @Transactional
    public void updateGroup(Group group) {
        Assert.notNull((Object)group, UpdateGroupMessages.GROUP_CAN_NOT_BE_NULL);
        this.groupRepository.merge(group);
    }

    @Override
    @Transactional
    public void deleteGroup(Group group) {
        Assert.notNull((Object)group, DeleteGroupMessages.GROUP_CAN_NOT_BE_NULL);
        this.groupRepository.delete(group);
    }

    @Override
    @Transactional
    public void deleteGroup(long id) {
        this.groupRepository.deleteById(id);
    }
}

