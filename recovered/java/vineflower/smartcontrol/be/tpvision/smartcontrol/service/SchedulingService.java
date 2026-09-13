package be.tpvision.smartcontrol.service;

import be.tpvision.smartcontrol.domain.Device;
import be.tpvision.smartcontrol.domain.device_settings.IntWrapper;
import be.tpvision.smartcontrol.domain.device_settings.scheduling.scheduling_parameters.Page;

public interface SchedulingService {
   Page getSchedulingParametersPage(Device device, IntWrapper pageNumber);

   void setSchedulingParametersPage(Device device, Page page);
}
