package be.tpvision.smartcontrol.domain.comparators.device;

import be.tpvision.smartcontrol.domain.Device;
import be.tpvision.smartcontrol.domain.comparators.DelegatingComparator;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.TemperatureSensor;
import java.util.Comparator;

public class DeviceTemperatureComparator extends DelegatingComparator<Device> {
   public DeviceTemperatureComparator(final Comparator<? super TemperatureSensor> temperatureComparator) {
      super(Comparator.comparing(Device::getTemperature, temperatureComparator));
   }
}
