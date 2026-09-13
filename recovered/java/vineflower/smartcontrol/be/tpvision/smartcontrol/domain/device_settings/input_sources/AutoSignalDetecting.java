package be.tpvision.smartcontrol.domain.device_settings.input_sources;

import be.tpvision.smartcontrol.domain.device_settings.DeviceSetting;

public enum AutoSignalDetecting implements DeviceSetting {
   OFF,
   ALL,
   RESERVED,
   PC_ONLY,
   VIDEO_ONLY,
   FAILOVER;
}
