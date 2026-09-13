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
import be.tpvision.smartcontrol.messages.services.general.ConstructorMessages;
import be.tpvision.smartcontrol.messages.services.general.SetBacklightMessages;
import be.tpvision.smartcontrol.messages.services.general.SetKeyPadLockStateMessages;
import be.tpvision.smartcontrol.messages.services.general.SetPowerStateAtColdStartMessages;
import be.tpvision.smartcontrol.messages.services.general.SetPowerStateMessages;
import be.tpvision.smartcontrol.messages.services.general.SetRemoteControlLockStateMessages;
import be.tpvision.smartcontrol.protocol.sicp.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
@Profile("production")
public class GeneralServiceImpl implements GeneralService {
   private final CommandService commandService;

   @Autowired
   GeneralServiceImpl(final CommandService commandService) {
      Assert.notNull(commandService, ConstructorMessages.COMMAND_SERVICE_CAN_NOT_BE_NULL);
      this.commandService = commandService;
   }

   @Override
   public PowerState getPowerState(final Device device) {
      return device == null ? null : this.commandService.send(device, Command.Type.GET, Command.Setting.POWER_STATE, PowerState.class);
   }

   @Override
   public void setPowerState(final Device device, final PowerState powerState) {
      Assert.notNull(device, SetPowerStateMessages.DEVICE_CAN_NOT_BE_NULL);
      Assert.notNull(powerState, SetPowerStateMessages.POWER_STATE_CAN_NOT_BE_NULL);
      this.commandService.send(device, Command.Type.SET, Command.Setting.POWER_STATE, powerState);
   }

   @Override
   public RemoteControlLockState getRemoteControlLockState(final Device device) {
      return device == null
         ? null
         : this.commandService.send(device, Command.Type.GET, Command.Setting.REMOTE_CONTROL_LOCK_STATE, RemoteControlLockState.class);
   }

   @Override
   public void setRemoteControlLockState(final Device device, final RemoteControlLockState remoteControlLockState) {
      Assert.notNull(device, SetRemoteControlLockStateMessages.DEVICE_CAN_NOT_BE_NULL);
      Assert.notNull(remoteControlLockState, SetRemoteControlLockStateMessages.REMOTE_CONTROL_LOCK_STATE_CAN_NOT_BE_NULL);
      this.commandService.send(device, Command.Type.SET, Command.Setting.REMOTE_CONTROL_LOCK_STATE, remoteControlLockState);
   }

   @Override
   public KeypadLockState getKeypadLockState(final Device device) {
      return device == null ? null : this.commandService.send(device, Command.Type.GET, Command.Setting.KEYPAD_LOCK_STATE, KeypadLockState.class);
   }

   @Override
   public void setKeypadLockState(final Device device, final KeypadLockState keypadLockState) {
      Assert.notNull(device, SetKeyPadLockStateMessages.DEVICE_CAN_NOT_BE_NULL);
      Assert.notNull(keypadLockState, SetKeyPadLockStateMessages.KEYPAD_LOCK_STATE_CAN_NOT_BE_NULL);
      this.commandService.send(device, Command.Type.SET, Command.Setting.KEYPAD_LOCK_STATE, keypadLockState);
   }

   @Override
   public PowerStateAtColdStart getPowerStateAtColdStart(final Device device) {
      return device == null ? null : this.commandService.send(device, Command.Type.GET, Command.Setting.POWER_STATE_AT_COLD_START, PowerStateAtColdStart.class);
   }

   @Override
   public void setPowerStateAtColdStart(final Device device, final PowerStateAtColdStart powerStateAtColdStart) {
      Assert.notNull(device, SetPowerStateAtColdStartMessages.DEVICE_CAN_NOT_BE_NULL);
      Assert.notNull(powerStateAtColdStart, SetPowerStateAtColdStartMessages.POWER_STATE_AT_COLD_START_CAN_NOT_BE_NULL);
      this.commandService.send(device, Command.Type.SET, Command.Setting.POWER_STATE_AT_COLD_START, powerStateAtColdStart);
   }

   @Override
   public void monitorRestart(Device device, MonitorSystem monitorSystem) {
      this.commandService.send(device, Command.Type.SET, Command.Setting.MONITOR_RESTART, monitorSystem);
   }

   @Override
   public Backlight getBacklight(Device device) {
      return device == null ? null : this.commandService.send(device, Command.Type.GET, Command.Setting.BACKLIGHT, Backlight.class);
   }

   @Override
   public void setBacklight(final Device device, final Backlight backlight) {
      Assert.notNull(device, SetBacklightMessages.DEVICE_CAN_NOT_BE_NULL);
      Assert.notNull(backlight, SetBacklightMessages.BACKLIGHT_CAN_NOT_BE_NULL);
      this.commandService.send(device, Command.Type.SET, Command.Setting.BACKLIGHT, backlight);
   }

   @Override
   public AutoRestartParameter getAutoRestartParameter(Device device) {
      return device == null ? null : this.commandService.send(device, Command.Type.GET, Command.Setting.AUTO_RESTART, AutoRestartParameter.class);
   }

   @Override
   public void setAutoRestartParameter(Device device, AutoRestartParameter autoRestartParameter) {
      this.commandService.send(device, Command.Type.SET, Command.Setting.AUTO_RESTART, autoRestartParameter);
   }

   @Override
   public LanguageOSD getLanguageOSD(Device device) {
      return device == null ? null : this.commandService.send(device, Command.Type.GET, Command.Setting.LANGUAGE_OSD, LanguageOSD.class);
   }

   @Override
   public void setLanguageOSD(Device device, LanguageOSD languageOSD) {
      this.commandService.send(device, Command.Type.SET, Command.Setting.LANGUAGE_OSD, languageOSD);
   }

   @Override
   public ClockParameter getClockParameter(Device device) {
      return device == null ? null : this.commandService.send(device, Command.Type.GET, Command.Setting.CLOCK, ClockParameter.class);
   }

   @Override
   public void setClockParameter(Device device, ClockParameter clockParameter) {
      this.commandService.send(device, Command.Type.SET, Command.Setting.CLOCK, clockParameter);
   }

   @Override
   public DateParameter getDateParameter(Device device) {
      return device == null ? null : this.commandService.send(device, Command.Type.GET, Command.Setting.DATE, DateParameter.class);
   }

   @Override
   public void setDateParameter(Device device, DateParameter dateParameter) {
      this.commandService.send(device, Command.Type.SET, Command.Setting.DATE, dateParameter);
   }

   @Override
   public AutoTimeSync getAutoTimeSync(Device device) {
      return device == null ? null : this.commandService.send(device, Command.Type.GET, Command.Setting.AUTO_TIME_SYNC, AutoTimeSync.class);
   }

   @Override
   public void setAutoTimeSync(Device device, AutoTimeSync autoTimeSync) {
      this.commandService.send(device, Command.Type.SET, Command.Setting.AUTO_TIME_SYNC, autoTimeSync);
   }

   @Override
   public TimeZone getTimeZone(Device device) {
      return device == null ? null : this.commandService.send(device, Command.Type.GET, Command.Setting.TIME_ZONE, TimeZone.class);
   }

   @Override
   public void setTimeZone(Device device, TimeZone timeZone) {
      this.commandService.send(device, Command.Type.SET, Command.Setting.TIME_ZONE, timeZone);
   }

   @Override
   public OpsSdmSettings getOpsSdmSettings(Device device) {
      return device == null ? null : this.commandService.send(device, Command.Type.GET, Command.Setting.OPS_SDM_SETTINGS, OpsSdmSettings.class);
   }

   @Override
   public void setOpsSdmSettings(Device device, OpsSdmSettings opsSdmSettings) {
      this.commandService.send(device, Command.Type.SET, Command.Setting.OPS_SDM_SETTINGS, opsSdmSettings);
   }

   @Override
   public PowerLED getPowerLED(Device device) {
      return device == null ? null : this.commandService.send(device, Command.Type.GET, Command.Setting.POWER_LED, PowerLED.class);
   }

   @Override
   public void setPowerLED(Device device, PowerLED powerLED) {
      this.commandService.send(device, Command.Type.SET, Command.Setting.POWER_LED, powerLED);
   }

   @Override
   public ForceRestartCustomApp getForceRestartCustomApp(Device device) {
      return device == null ? null : this.commandService.send(device, Command.Type.GET, Command.Setting.FORCE_RESTART_CUSTOM_APP, ForceRestartCustomApp.class);
   }

   @Override
   public void setForceRestartCustomApp(Device device, ForceRestartCustomApp forceRestartCustomApp) {
      this.commandService.send(device, Command.Type.SET, Command.Setting.FORCE_RESTART_CUSTOM_APP, forceRestartCustomApp);
   }

   @Override
   public StringWrapper getNumberOfInputSources(Device device) {
      return device == null ? null : this.commandService.send(device, Command.Type.GET, Command.Setting.GET_NUMBER_OF_INPUT_SOURCES, StringWrapper.class);
   }
}
