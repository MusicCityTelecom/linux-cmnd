package be.tpvision.smartcontrol.domain.device_settings.general;

import be.tpvision.smartcontrol.domain.device_settings.DeviceSetting;

public enum PowerStateAtColdStart implements DeviceSetting {
   OFF,
   ON,
   LAST_STATUS;
}
