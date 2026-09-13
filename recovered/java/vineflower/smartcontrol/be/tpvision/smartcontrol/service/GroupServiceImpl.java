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
import be.tpvision.smartcontrol.repository.order_fields.OrderField;
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
public class GroupServiceImpl implements GroupService {
   private final GroupRepository groupRepository;
   private final DeviceRepository deviceRepository;

   @Autowired
   public GroupServiceImpl(final GroupRepository groupRepository, final DeviceRepository deviceRepository) {
      Assert.notNull(groupRepository, ConstructorMessages.GROUP_REPOSITORY_CAN_NOT_BE_NULL);
      this.groupRepository = groupRepository;
      this.deviceRepository = deviceRepository;
   }

   @Override
   public Set<Group> getGroups() {
      List<Group> groupList = this.groupRepository
         .getAllOrderedByWithDevicesOrderedBy(
            be.tpvision.smartcontrol.repository.order_fields.group.OrderField.ID,
            OrderDirection.ASC,
            be.tpvision.smartcontrol.repository.order_fields.device.OrderField.ID,
            OrderDirection.ASC
         );
      Set<Group> groupSet = new LinkedHashSet<>();
      groupSet.addAll(groupList);
      return groupSet;
   }

   @Override
   public Group getGroup(final long id) {
      return this.getGroup(id, be.tpvision.smartcontrol.repository.order_fields.device.OrderField.ID, OrderDirection.ASC);
   }

   @Override
   public Group getGroup(final long id, final OrderField<? super Device> deviceOrderField, final OrderDirection deviceOrderDirection) {
      return this.groupRepository.getByIdWithDevicesOrderedBy(id, deviceOrderField, deviceOrderDirection);
   }

   @Transactional(rollbackFor = FailedToAddGroupException.class)
   @Override
   public AddGroupResult addGroup(final Group group) {
      Assert.state(this.deviceRepository != null, AddGroupMessages.DEVICE_REPOSITORY_CAN_NOT_BE_NULL);
      Assert.notNull(group, AddGroupMessages.GROUP_CAN_NOT_BE_NULL);
      Set<Device> deviceSet = group.getDevices();
      Set<Device> processedDeviceSet = new LinkedHashSet<>();
      List<AddDeviceResult> addDeviceResultList = new ArrayList<>();

      for (Device device : deviceSet) {
         Long id = device.getId();
         if (id != null) {
            AddDeviceResult.Result result = AddDeviceResult.Result.SUCCESS;
            String message = String.format("Device with id %d already existed.", id);
            AddDeviceResult addDeviceResult = new AddDeviceResult(device, result, message);
            addDeviceResultList.add(addDeviceResult);
            processedDeviceSet.add(device);
         } else {
            StringWrapper serialCode = device.getSerialCode();
            if (serialCode != null) {
               String serialCodeValue = serialCode.getValue();
               if (serialCodeValue != null) {
                  Device existingDevice = this.deviceRepository.getBySerialCode(serialCodeValue);
                  AddDeviceResult.Result result = AddDeviceResult.Result.SUCCESS;
                  String message = String.format("A device with serial code %s already existed.", serialCodeValue);
                  AddDeviceResult addDeviceResult = new AddDeviceResult(device, result, message);
                  addDeviceResultList.add(addDeviceResult);
                  processedDeviceSet.add(existingDevice);
                  continue;
               }
            }

            AddDeviceResult.Result result;
            String message;
            try {
               this.deviceRepository.persist(device);
               result = AddDeviceResult.Result.SUCCESS;
               message = "Device added successfully.";
               processedDeviceSet.add(device);
            } catch (DataIntegrityViolationException e) {
               result = AddDeviceResult.Result.FAILED;
               IpDestination ipDestination = device.getAddress();
               InetSocketAddress inetSocketAddress = ipDestination.getAddress();
               InetAddress inetAddress = inetSocketAddress.getAddress();
               String ip = inetAddress.getHostAddress();
               int port = inetSocketAddress.getPort();
               int controlId = ipDestination.getControlId();
               message = String.format("Device %s:%d with control id %d already exists.", ip, port, controlId);
            }

            AddDeviceResult addDeviceResult = new AddDeviceResult(device, result, message);
            addDeviceResultList.add(addDeviceResult);
         }
      }

      boolean failed = addDeviceResultList.stream().map(AddDeviceResult::getResult).anyMatch(AddDeviceResult.Result.FAILED::equals);
      if (failed) {
         AddGroupResult addGroupResult = new AddGroupResult(group, addDeviceResultList, AddGroupResult.Result.FAILED, "Failed to add group.");
         throw new FailedToAddGroupException(addGroupResult);
      } else {
         group.setDevices(processedDeviceSet);
         this.groupRepository.persist(group);
         AddGroupResult.Result result = AddGroupResult.Result.SUCCESS;
         String message = "Added group successfully.";
         return new AddGroupResult(group, addDeviceResultList, result, "Added group successfully.");
      }
   }

   @Transactional
   @Override
   public void updateGroup(final Group group) {
      Assert.notNull(group, UpdateGroupMessages.GROUP_CAN_NOT_BE_NULL);
      this.groupRepository.merge(group);
   }

   @Transactional
   @Override
   public void deleteGroup(final Group group) {
      Assert.notNull(group, DeleteGroupMessages.GROUP_CAN_NOT_BE_NULL);
      this.groupRepository.delete(group);
   }

   @Transactional
   @Override
   public void deleteGroup(final long id) {
      this.groupRepository.deleteById(id);
   }
}
