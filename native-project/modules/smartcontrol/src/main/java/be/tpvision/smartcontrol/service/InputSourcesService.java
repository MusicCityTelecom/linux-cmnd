package be.tpvision.smartcontrol.service;

import be.tpvision.smartcontrol.domain.Device;
import be.tpvision.smartcontrol.domain.device_settings.input_sources.AutoSignalDetecting;
import be.tpvision.smartcontrol.domain.device_settings.input_sources.Failovers;
import be.tpvision.smartcontrol.domain.device_settings.input_sources.InputSource;

public interface InputSourcesService {
   InputSource getInputSource(Device device);

   void setInputSource(Device device, InputSource inputSource);

   AutoSignalDetecting getAutoSignalDetecting(Device device);

   void setAutoSignalDetecting(Device device, AutoSignalDetecting autoSignalDetecting);

   Failovers getFailovers(Device device);

   void setFailovers(Device device, Failovers failovers);
}
