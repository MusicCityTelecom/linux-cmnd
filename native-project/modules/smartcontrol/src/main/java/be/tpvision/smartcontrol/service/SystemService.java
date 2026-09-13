package be.tpvision.smartcontrol.service;

import be.tpvision.smartcontrol.domain.Device;
import be.tpvision.smartcontrol.domain.device_settings.StringWrapper;

public interface SystemService {
   StringWrapper getSICPVersion(Device device);

   StringWrapper getPlatformLabel(Device device);

   StringWrapper getPlatformVersion(Device device);

   StringWrapper getModelNumber(Device device);

   StringWrapper getFirmwareVersion(Device device);

   StringWrapper getBuildDate(Device device);

   StringWrapper getFirmwareVersionAndroid(Device device);
}
