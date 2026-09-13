package be.tpvision.smartcontrol.service;

import be.tpvision.smartcontrol.domain.Device;
import be.tpvision.smartcontrol.domain.DeviceListItem;
import be.tpvision.smartcontrol.repository.OrderDirection;
import be.tpvision.smartcontrol.repository.order_fields.OrderField;
import java.util.Set;

public interface DeviceService {
   Set<DeviceListItem> getDevices();

   Set<DeviceListItem> detectDevices();

   Set<Device> getDevicesOrderedBy(OrderField<? super Device> orderField, OrderDirection orderDirection);

   Device getDevice(long id);

   Device getDeviceBySerialCode(String serialCode);

   void addDevice(Device device);

   void addDevice(Device device, boolean encodeFtpPassword);

   void updateDevice(Device device);

   void updateDevice(Device device, boolean encodeFtpPassword);

   void deleteDevice(Device device);

   void deleteDevice(long id);

   void logAllDevices();

   void logOneDevice(Device device);
}
