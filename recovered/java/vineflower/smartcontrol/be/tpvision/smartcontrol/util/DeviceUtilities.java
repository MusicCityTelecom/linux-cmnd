package be.tpvision.smartcontrol.util;

import be.tpvision.smartcontrol.domain.Device;
import be.tpvision.smartcontrol.repository.OrderDirection;
import be.tpvision.smartcontrol.repository.order_fields.OrderField;
import java.util.Collection;
import java.util.List;

public interface DeviceUtilities {
   List<Device> orderDevices(Collection<Device> deviceCollection, OrderField<? super Device> orderField, OrderDirection orderDirection);
}
