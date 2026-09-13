package be.tpvision.smartcontrol.domain.comparators.device;

import be.tpvision.smartcontrol.domain.Device;
import be.tpvision.smartcontrol.repository.OrderDirection;
import be.tpvision.smartcontrol.repository.order_fields.OrderField;
import java.util.Comparator;

public interface DeviceComparatorFactory {
   Comparator<? super Device> getComparator(OrderField<? super Device> orderField, OrderDirection orderDirection);
}
