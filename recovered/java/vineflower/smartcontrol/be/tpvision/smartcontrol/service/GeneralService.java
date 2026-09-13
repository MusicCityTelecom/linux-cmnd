package be.tpvision.smartcontrol.service;

import be.tpvision.smartcontrol.domain.Device;
import be.tpvision.smartcontrol.domain.device_settings.StringWrapper;
import be.tpvision.smartcontrol.domain.device_settings.general.AutoRestartParameter;
import be.tpvision.smartcontrol.domain.device_settings.general.AutoTimeSync;
import be.tpvision.smartcontrol.domain.device_settings.general.Backlight;
import be.tpvision.smartcontrol.domain.device_settings.general.ClockParameter;
import be.tpvision.smartcontrol.domain.device_settings.general.DateParameter;
import be.tpvision.smartcontrol.domain.device_settings.general.ForceRestartCustomApp;
import be.tpvision.smartcontrol.domain.device_settings.general.KeypadLockState;
import be.tpvision.smartcontrol.domain.device_settings.general.LanguageOSD;
import be.tpvision.smartcontrol.domain.device_settings.general.MonitorSystem;
import be.tpvision.smartcontrol.domain.device_settings.general.OpsSdmSettings;
import be.tpvision.smartcontrol.domain.device_settings.general.PowerLED;
import be.tpvision.smartcontrol.domain.device_settings.general.PowerState;
import be.tpvision.smartcontrol.domain.device_settings.general.PowerStateAtColdStart;
import be.tpvision.smartcontrol.domain.device_settings.general.RemoteControlLockState;
import be.tpvision.smartcontrol.domain.device_settings.general.TimeZone;

public interface GeneralService {
   PowerState getPowerState(Device device);

   void setPowerState(Device device, PowerState powerState);

   RemoteControlLockState getRemoteControlLockState(Device device);

   void setRemoteControlLockState(Device device, RemoteControlLockState remoteControlLockState);

   KeypadLockState getKeypadLockState(Device device);

   void setKeypadLockState(Device device, KeypadLockState keypadLockState);

   PowerStateAtColdStart getPowerStateAtColdStart(Device device);

   void setPowerStateAtColdStart(Device device, PowerStateAtColdStart powerStateAtColdStart);

   void monitorRestart(Device device, MonitorSystem monitorSystem);

   Backlight getBacklight(Device device);

   void setBacklight(Device device, Backlight backlight);

   AutoRestartParameter getAutoRestartParameter(Device device);

   void setAutoRestartParameter(Device device, AutoRestartParameter autoRestartParameter);

   LanguageOSD getLanguageOSD(Device device);

   void setLanguageOSD(Device device, LanguageOSD languageOSD);

   OpsSdmSettings getOpsSdmSettings(Device device);

   void setOpsSdmSettings(Device device, OpsSdmSettings opsSdmSettings);

   PowerLED getPowerLED(Device device);

   void setPowerLED(Device device, PowerLED powerLED);

   ForceRestartCustomApp getForceRestartCustomApp(Device device);

   void setForceRestartCustomApp(Device device, ForceRestartCustomApp forceRestartCustomApp);

   ClockParameter getClockParameter(Device device);

   void setClockParameter(Device device, ClockParameter clockParameter);

   DateParameter getDateParameter(Device device);

   void setDateParameter(Device device, DateParameter dateParameter);

   AutoTimeSync getAutoTimeSync(Device device);

   void setAutoTimeSync(Device device, AutoTimeSync autoTimeSync);

   TimeZone getTimeZone(Device device);

   void setTimeZone(Device device, TimeZone timeZone);

   StringWrapper getNumberOfInputSources(Device device);
}
