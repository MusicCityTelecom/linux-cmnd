package be.tpvision.smartcontrol.domain.comparators.device;

import be.tpvision.smartcontrol.domain.Device;
import be.tpvision.smartcontrol.domain.comparators.DelegatingComparator;
import java.util.Comparator;

public class DeviceIdComparator extends DelegatingComparator<Device> {
   public DeviceIdComparator(final Comparator<Long> idComparator) {
      super(Comparator.comparing(Device::getId, idComparator));
   }
}
