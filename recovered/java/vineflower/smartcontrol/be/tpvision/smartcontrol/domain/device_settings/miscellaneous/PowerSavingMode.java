package be.tpvision.smartcontrol.domain.device_settings.miscellaneous;

import be.tpvision.smartcontrol.domain.device_settings.DeviceSetting;

public enum PowerSavingMode implements DeviceSetting {
   RGB_OFF_VIDEO_OFF,
   RGB_OFF_VIDEO_ON,
   RGB_ON_VIDEO_OFF,
   RGB_ON_VIDEO_ON,
   MODE_ONE,
   MODE_TWO,
   MODE_THREE,
   MODE_FOUR;
}
