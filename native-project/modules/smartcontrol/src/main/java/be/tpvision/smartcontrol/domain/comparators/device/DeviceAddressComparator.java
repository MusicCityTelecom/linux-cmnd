package be.tpvision.smartcontrol.domain.comparators.device;

import be.tpvision.smartcontrol.domain.Device;
import be.tpvision.smartcontrol.domain.comparators.DelegatingComparator;
import be.tpvision.smartcontrol.io.ip.IpDestination;
import java.util.Comparator;

public class DeviceAddressComparator extends DelegatingComparator<Device> {
   public DeviceAddressComparator(Comparator<? super IpDestination> addressComparator) {
      super(Comparator.comparing(Device::getAddress, addressComparator));
   }
}
