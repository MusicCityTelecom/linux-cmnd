package be.tpvision.smartcontrol.util;

import be.tpvision.smartcontrol.domain.Device;
import be.tpvision.smartcontrol.domain.comparators.device.DeviceComparatorFactory;
import be.tpvision.smartcontrol.messages.util.device_utilities_impl.ConstructorMessages;
import be.tpvision.smartcontrol.messages.util.device_utilities_impl.OrderDevicesMessages;
import be.tpvision.smartcontrol.repository.OrderDirection;
import be.tpvision.smartcontrol.repository.order_fields.OrderField;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class DeviceUtilitiesImpl implements DeviceUtilities {
   private final DeviceComparatorFactory deviceComparatorFactory;

   @Autowired
   public DeviceUtilitiesImpl(final DeviceComparatorFactory deviceComparatorFactory) {
      Assert.notNull(deviceComparatorFactory, ConstructorMessages.DEVICE_COMPARATOR_FACTORY_CAN_NOT_BE_NULL);
      this.deviceComparatorFactory = deviceComparatorFactory;
   }

   @Override
   public List<Device> orderDevices(final Collection<Device> deviceCollection, final OrderField<? super Device> orderField, final OrderDirection orderDirection) {
      Assert.notNull(deviceCollection, OrderDevicesMessages.DEVICE_COLLECTION_CAN_NOT_BE_NULL);
      Assert.notNull(orderField, OrderDevicesMessages.ORDER_FIELD_CAN_NOT_BE_NULL);
      Assert.notNull(orderDirection, OrderDevicesMessages.ORDER_DIRECTION_CAN_NOT_BE_NULL);
      List<Device> orderedDeviceList = new ArrayList<>(deviceCollection);
      Comparator<? super Device> deviceComparator = this.deviceComparatorFactory.getComparator(orderField, orderDirection);
      Assert.state(deviceComparator != null, OrderDevicesMessages.DEVICE_COMPARATOR_CAN_NOT_BE_NULL);
      orderedDeviceList.sort(deviceComparator);
      return orderedDeviceList;
   }
}
