package be.tpvision.smartcontrol.domain.comparators.device;

import be.tpvision.smartcontrol.domain.Device;
import be.tpvision.smartcontrol.domain.comparators.DelegatingComparator;
import be.tpvision.smartcontrol.domain.device_settings.StringWrapper;
import java.util.Comparator;

public class DeviceModelNumberComparator extends DelegatingComparator<Device> {
   public DeviceModelNumberComparator(final Comparator<? super StringWrapper> modelNumberComparator) {
      super(Comparator.comparing(Device::getModelNumber, modelNumberComparator));
   }
}
