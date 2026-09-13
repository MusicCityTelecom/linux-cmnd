package be.tpvision.smartcontrol.protocol.sicp208;

import be.tpvision.smartcontrol.codecs.sicp.CommunicationControlCodec;
import be.tpvision.smartcontrol.codecs.sicp.IntWrapperCodec;
import be.tpvision.smartcontrol.codecs.sicp.StringWrapperCodec;
import be.tpvision.smartcontrol.codecs.sicp188.audio.AudioParametersCodec;
import be.tpvision.smartcontrol.codecs.sicp188.audio.VolumeCodec;
import be.tpvision.smartcontrol.codecs.sicp188.audio.VolumeLimitsCodec;
import be.tpvision.smartcontrol.codecs.sicp188.audio.VolumeUpDownCodec;
import be.tpvision.smartcontrol.codecs.sicp188.general.KeypadLockStateCodec;
import be.tpvision.smartcontrol.codecs.sicp188.general.PowerStateAtColdStartCodec;
import be.tpvision.smartcontrol.codecs.sicp188.general.PowerStateCodec;
import be.tpvision.smartcontrol.codecs.sicp188.general.RemoteControlLockStateCodec;
import be.tpvision.smartcontrol.codecs.sicp188.input_sources.AutoSignalDetectingCodec;
import be.tpvision.smartcontrol.codecs.sicp188.miscellaneous.FanSpeedCodec;
import be.tpvision.smartcontrol.codecs.sicp188.miscellaneous.MEMCEffectCodec;
import be.tpvision.smartcontrol.codecs.sicp188.miscellaneous.MiscellaneousCodec;
import be.tpvision.smartcontrol.codecs.sicp188.miscellaneous.OSDRotatingCodec;
import be.tpvision.smartcontrol.codecs.sicp188.miscellaneous.PowerOnLogoCodec;
import be.tpvision.smartcontrol.codecs.sicp188.miscellaneous.ScanConversionCodec;
import be.tpvision.smartcontrol.codecs.sicp188.miscellaneous.ScanModeCodec;
import be.tpvision.smartcontrol.codecs.sicp188.miscellaneous.SmartPowerCodec;
import be.tpvision.smartcontrol.codecs.sicp188.miscellaneous.SwitchOnDelayCodec;
import be.tpvision.smartcontrol.codecs.sicp188.miscellaneous.TemperatureSensorCodec;
import be.tpvision.smartcontrol.codecs.sicp188.miscellaneous.TilingCodec;
import be.tpvision.smartcontrol.codecs.sicp188.miscellaneous.TouchCodec;
import be.tpvision.smartcontrol.codecs.sicp188.miscellaneous.VideoAlignmentCodec;
import be.tpvision.smartcontrol.codecs.sicp188.system.SicpAndPlatformInfoCodec;
import be.tpvision.smartcontrol.codecs.sicp188.video.ColorParametersCodec;
import be.tpvision.smartcontrol.codecs.sicp188.video.ColorTemperature100KCodec;
import be.tpvision.smartcontrol.codecs.sicp188.video.ColorTemperatureCodec;
import be.tpvision.smartcontrol.codecs.sicp188.video.PictureFormatCodec;
import be.tpvision.smartcontrol.codecs.sicp188.video.VGAVideoParametersCodec;
import be.tpvision.smartcontrol.codecs.sicp188.video.VideoParametersCodec;
import be.tpvision.smartcontrol.codecs.sicp197.miscellaneous.LedStripsCodec;
import be.tpvision.smartcontrol.codecs.sicp197.miscellaneous.LightSensorCodec;
import be.tpvision.smartcontrol.codecs.sicp197.miscellaneous.PowerSavingModeCodec;
import be.tpvision.smartcontrol.codecs.sicp197.video.PictureInPictureCodec;
import be.tpvision.smartcontrol.codecs.sicp197.video.PictureInPictureSourceCodec;
import be.tpvision.smartcontrol.codecs.sicp199.input_sources.FailoversCodec;
import be.tpvision.smartcontrol.codecs.sicp199.miscellaneous.LockUsbCodec;
import be.tpvision.smartcontrol.codecs.sicp199.miscellaneous.PixelShiftCodec;
import be.tpvision.smartcontrol.codecs.sicp200.audio.MuteCodec;
import be.tpvision.smartcontrol.codecs.sicp200.miscellaneous.APMCodec;
import be.tpvision.smartcontrol.codecs.sicp200.miscellaneous.EcoModeCodec;
import be.tpvision.smartcontrol.codecs.sicp201.input_sources.InputSourceCodec;
import be.tpvision.smartcontrol.codecs.sicp201.scheduling.scheduling_parameters.PageCodec;
import be.tpvision.smartcontrol.codecs.sicp202.general.MonitorRestartCodec;
import be.tpvision.smartcontrol.codecs.sicp202.miscellaneous.DisplayOrientationCodec;
import be.tpvision.smartcontrol.codecs.sicp202.system.ModelNumberFirmwareVersionBuildDateInfoCodec;
import be.tpvision.smartcontrol.codecs.sicp203.general.BacklightCodec;
import be.tpvision.smartcontrol.codecs.sicp203.miscellaneous.NoiseReductionCodec;
import be.tpvision.smartcontrol.codecs.sicp203.miscellaneous.VideoPresentCodec;
import be.tpvision.smartcontrol.codecs.sicp203.video.PictureStyleCodec;
import be.tpvision.smartcontrol.codecs.sicp204.miscellaneous.NavigationBarCodec;
import be.tpvision.smartcontrol.codecs.sicp205.general.NumberOfInputSourcesCodec;
import be.tpvision.smartcontrol.codecs.sicp205.miscellaneous.BootOnSourceCodec;
import be.tpvision.smartcontrol.codecs.sicp207.audio.AudioSyncCodec;
import be.tpvision.smartcontrol.codecs.sicp207.audio.SpeakersStatusCodec;
import be.tpvision.smartcontrol.codecs.sicp207.general.AutoRestartParameterCodec;
import be.tpvision.smartcontrol.codecs.sicp207.general.AutoTimeSyncCodec;
import be.tpvision.smartcontrol.codecs.sicp207.general.ClockParameterCodec;
import be.tpvision.smartcontrol.codecs.sicp207.general.DateParameterCodec;
import be.tpvision.smartcontrol.codecs.sicp207.general.LanguageOSDCodec;
import be.tpvision.smartcontrol.codecs.sicp207.general.TimeZoneCodec;
import be.tpvision.smartcontrol.codecs.sicp207.miscellaneous.HdmiOneWireCodec;
import be.tpvision.smartcontrol.codecs.sicp207.miscellaneous.RS232RoutingCodec;
import be.tpvision.smartcontrol.codecs.sicp207.miscellaneous.SicpSerialPortForwardingCodec;
import be.tpvision.smartcontrol.codecs.sicp207.miscellaneous.TeamviewerStatusCodec;
import be.tpvision.smartcontrol.codecs.sicp207.miscellaneous.WOLCodec;
import be.tpvision.smartcontrol.codecs.sicp208.general.ForceRestartCustomAppCodec;
import be.tpvision.smartcontrol.codecs.sicp208.general.OpsSdmSettingsCodec;
import be.tpvision.smartcontrol.codecs.sicp208.general.PowerLEDCodec;
import be.tpvision.smartcontrol.codecs.sicp208.miscellaneous.OtaImageTypeCodec;
import be.tpvision.smartcontrol.codecs.sicp208.miscellaneous.OtaUpdateGetCodec;
import be.tpvision.smartcontrol.codecs.sicp208.miscellaneous.OtaUpdateSetCodec;
import be.tpvision.smartcontrol.protocol.sicp.Command;
import be.tpvision.smartcontrol.protocol.sicp.Commands;
import be.tpvision.smartcontrol.protocol.sicp.SicpCommandFactoryImpl;
import be.tpvision.smartcontrol.protocol.sicp.SicpVersion;

public class SicpCommandFactory extends SicpCommandFactoryImpl {
   private static final Commands commands = new Commands();
   private static SicpCommandFactory sicpCommandFactory;

   private SicpCommandFactory() {
      super(SicpVersion._208, commands);
   }

   public static synchronized SicpCommandFactory getInstance() {
      if (sicpCommandFactory == null) {
         sicpCommandFactory = new SicpCommandFactory();
      }

      return sicpCommandFactory;
   }

   static {
      commands.add(new Command(Command.Type.GET, Command.Setting.COMMUNICATION_CONTROL, (byte)0, null, CommunicationControlCodec.getInstance()));
      commands.add(new Command(Command.Type.GET, Command.Setting.VOLUME, (byte)69, null, VolumeCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.VOLUME, (byte)68, VolumeCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.VOLUME_UP_DOWN, (byte)65, VolumeUpDownCodec.getInstance()));
      commands.add(new Command(Command.Type.GET, Command.Setting.VOLUME_LIMITS_SPEAKER_OUT, (byte)-74, null, VolumeLimitsCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.VOLUME_LIMITS_SPEAKER_OUT, (byte)-72, VolumeLimitsCodec.getInstance()));
      commands.add(new Command(Command.Type.GET, Command.Setting.VOLUME_LIMITS_AUDIO_OUT, (byte)-73, null, VolumeLimitsCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.VOLUME_LIMITS_AUDIO_OUT, (byte)-71, VolumeLimitsCodec.getInstance()));
      commands.add(new Command(Command.Type.GET, Command.Setting.AUDIO_PARAMETERS, (byte)67, null, AudioParametersCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.AUDIO_PARAMETERS, (byte)66, AudioParametersCodec.getInstance()));
      commands.add(new Command(Command.Type.GET, Command.Setting.POWER_STATE, (byte)25, null, PowerStateCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.POWER_STATE, (byte)24, PowerStateCodec.getInstance()));
      commands.add(new Command(Command.Type.GET, Command.Setting.REMOTE_CONTROL_LOCK_STATE, (byte)29, null, RemoteControlLockStateCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.REMOTE_CONTROL_LOCK_STATE, (byte)28, RemoteControlLockStateCodec.getInstance()));
      commands.add(new Command(Command.Type.GET, Command.Setting.KEYPAD_LOCK_STATE, (byte)27, null, KeypadLockStateCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.KEYPAD_LOCK_STATE, (byte)26, KeypadLockStateCodec.getInstance()));
      commands.add(new Command(Command.Type.GET, Command.Setting.POWER_STATE_AT_COLD_START, (byte)-92, null, PowerStateAtColdStartCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.POWER_STATE_AT_COLD_START, (byte)-93, PowerStateAtColdStartCodec.getInstance()));
      commands.add(new Command(Command.Type.GET, Command.Setting.INPUT_SOURCE, (byte)-83, null, InputSourceCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.INPUT_SOURCE, (byte)-84, InputSourceCodec.getInstance()));
      commands.add(new Command(Command.Type.GET, Command.Setting.AUTO_SIGNAL_DETECTING, (byte)-81, null, AutoSignalDetectingCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.AUTO_SIGNAL_DETECTING, (byte)-82, AutoSignalDetectingCodec.getInstance()));
      commands.add(new Command(Command.Type.GET, Command.Setting.FAILOVERS, (byte)-90, null, FailoversCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.FAILOVERS, (byte)-91, FailoversCodec.getInstance()));
      commands.add(
         new Command(Command.Type.GET, Command.Setting.MISCELLANEOUS, (byte)15, MiscellaneousCodec.InfoCodec.getInstance(), MiscellaneousCodec.getInstance())
      );
      commands.add(new Command(Command.Type.GET, Command.Setting.SMART_POWER, (byte)-34, null, SmartPowerCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.SMART_POWER, (byte)-35, SmartPowerCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.VIDEO_ALIGNMENT, (byte)112, VideoAlignmentCodec.getInstance()));
      commands.add(new Command(Command.Type.GET, Command.Setting.TEMPERATURE_SENSOR, (byte)47, null, TemperatureSensorCodec.getInstance()));
      commands.add(new Command(Command.Type.GET, Command.Setting.SERIAL_CODE, (byte)21, null, StringWrapperCodec.getInstance()));
      commands.add(new Command(Command.Type.GET, Command.Setting.TILING, (byte)35, null, TilingCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.TILING, (byte)34, TilingCodec.getInstance()));
      commands.add(new Command(Command.Type.GET, Command.Setting.FRAME_COMPENSATION_HORIZONTAL, (byte)94, null, IntWrapperCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.FRAME_COMPENSATION_HORIZONTAL, (byte)95, IntWrapperCodec.getInstance()));
      commands.add(new Command(Command.Type.GET, Command.Setting.FRAME_COMPENSATION_VERTICAL, (byte)103, null, IntWrapperCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.FRAME_COMPENSATION_VERTICAL, (byte)104, IntWrapperCodec.getInstance()));
      commands.add(new Command(Command.Type.GET, Command.Setting.LIGHT_SENSOR, (byte)37, null, LightSensorCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.LIGHT_SENSOR, (byte)36, LightSensorCodec.getInstance()));
      commands.add(new Command(Command.Type.GET, Command.Setting.OSD_ROTATING, (byte)39, null, OSDRotatingCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.OSD_ROTATING, (byte)38, OSDRotatingCodec.getInstance()));
      commands.add(new Command(Command.Type.GET, Command.Setting.OSD_INFORMATION, (byte)45, null, IntWrapperCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.OSD_INFORMATION, (byte)44, IntWrapperCodec.getInstance()));
      commands.add(new Command(Command.Type.GET, Command.Setting.MEMC_EFFECT, (byte)41, null, MEMCEffectCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.MEMC_EFFECT, (byte)40, MEMCEffectCodec.getInstance()));
      commands.add(new Command(Command.Type.GET, Command.Setting.TOUCH, (byte)31, null, TouchCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.TOUCH, (byte)30, TouchCodec.getInstance()));
      commands.add(new Command(Command.Type.GET, Command.Setting.NOISE_REDUCTION, (byte)43, null, NoiseReductionCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.NOISE_REDUCTION, (byte)42, NoiseReductionCodec.getInstance()));
      commands.add(new Command(Command.Type.GET, Command.Setting.SCAN_MODE, (byte)81, null, ScanModeCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.SCAN_MODE, (byte)80, ScanModeCodec.getInstance()));
      commands.add(new Command(Command.Type.GET, Command.Setting.SCAN_CONVERSION, (byte)83, null, ScanConversionCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.SCAN_CONVERSION, (byte)82, ScanConversionCodec.getInstance()));
      commands.add(new Command(Command.Type.GET, Command.Setting.SWITCH_ON_DELAY, (byte)85, null, SwitchOnDelayCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.SWITCH_ON_DELAY, (byte)84, SwitchOnDelayCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.FACTORY_RESET, (byte)86));
      commands.add(new Command(Command.Type.GET, Command.Setting.POWER_ON_LOGO, (byte)63, null, PowerOnLogoCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.POWER_ON_LOGO, (byte)62, PowerOnLogoCodec.getInstance()));
      commands.add(new Command(Command.Type.GET, Command.Setting.FAN_SPEED, (byte)98, null, FanSpeedCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.FAN_SPEED, (byte)97, FanSpeedCodec.getInstance()));
      commands.add(new Command(Command.Type.GET, Command.Setting.APM, (byte)-47, null, APMCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.APM, (byte)-48, APMCodec.getInstance()));
      commands.add(new Command(Command.Type.GET, Command.Setting.POWER_SAVING_MODE, (byte)-45, null, PowerSavingModeCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.POWER_SAVING_MODE, (byte)-46, PowerSavingModeCodec.getInstance()));
      commands.add(new Command(Command.Type.GET, Command.Setting.DISPLAY_ORIENTATION, (byte)22, null, DisplayOrientationCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.DISPLAY_ORIENTATION, (byte)23, DisplayOrientationCodec.getInstance()));
      commands.add(new Command(Command.Type.GET, Command.Setting.LED_STRIPS, (byte)-12, null, LedStripsCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.LED_STRIPS, (byte)-13, LedStripsCodec.getInstance()));
      commands.add(
         new Command(
            Command.Type.GET,
            Command.Setting.SICP_VERSION_AND_PLATFORM_INFO,
            (byte)-94,
            SicpAndPlatformInfoCodec.getInstance(),
            StringWrapperCodec.getInstance()
         )
      );
      commands.add(
         new Command(
            Command.Type.GET,
            Command.Setting.MODEL_NUMBER_FIRMWARE_VERSION_BUILD_DATE,
            (byte)-95,
            ModelNumberFirmwareVersionBuildDateInfoCodec.getInstance(),
            StringWrapperCodec.getInstance()
         )
      );
      commands.add(new Command(Command.Type.GET, Command.Setting.VIDEO_PARAMETERS, (byte)51, null, VideoParametersCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.VIDEO_PARAMETERS, (byte)50, VideoParametersCodec.getInstance()));
      commands.add(new Command(Command.Type.GET, Command.Setting.COLOR_TEMPERATURE, (byte)53, null, ColorTemperatureCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.COLOR_TEMPERATURE, (byte)52, ColorTemperatureCodec.getInstance()));
      commands.add(new Command(Command.Type.GET, Command.Setting.COLOR_PARAMETERS, (byte)55, null, ColorParametersCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.COLOR_PARAMETERS, (byte)54, ColorParametersCodec.getInstance()));
      commands.add(new Command(Command.Type.GET, Command.Setting.COLOR_TEMPERATURE_100K, (byte)18, null, ColorTemperature100KCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.COLOR_TEMPERATURE_100K, (byte)17, ColorTemperature100KCodec.getInstance()));
      commands.add(new Command(Command.Type.GET, Command.Setting.PICTURE_FORMAT, (byte)59, null, PictureFormatCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.PICTURE_FORMAT, (byte)58, PictureFormatCodec.getInstance()));
      commands.add(new Command(Command.Type.GET, Command.Setting.VGA_VIDEO_PARAMETERS, (byte)57, null, VGAVideoParametersCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.VGA_VIDEO_PARAMETERS, (byte)56, VGAVideoParametersCodec.getInstance()));
      commands.add(new Command(Command.Type.GET, Command.Setting.PICTURE_IN_PICTURE, (byte)61, null, PictureInPictureCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.PICTURE_IN_PICTURE, (byte)60, PictureInPictureCodec.getInstance()));
      commands.add(new Command(Command.Type.GET, Command.Setting.PICTURE_IN_PICTURE_SOURCE, (byte)-123, null, PictureInPictureSourceCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.PICTURE_IN_PICTURE_SOURCE, (byte)-124, PictureInPictureSourceCodec.getInstance()));
      commands.add(new Command(Command.Type.GET, Command.Setting.SCHEDULING_PARAMETERS, (byte)91, IntWrapperCodec.getInstance(), PageCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.SCHEDULING_PARAMETERS, (byte)90, PageCodec.getInstance()));
      commands.add(new Command(Command.Type.GET, Command.Setting.GROUP_ID, (byte)93, null, IntWrapperCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.GROUP_ID, (byte)92, IntWrapperCodec.getInstance()));
      commands.add(new Command(Command.Type.GET, Command.Setting.OFF_TIMER, (byte)-111, null, IntWrapperCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.OFF_TIMER, (byte)-110, IntWrapperCodec.getInstance()));
      commands.add(new Command(Command.Type.GET, Command.Setting.LOCK_USB, (byte)-14, null, LockUsbCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.LOCK_USB, (byte)-15, LockUsbCodec.getInstance()));
      commands.add(new Command(Command.Type.GET, Command.Setting.HUMAN_SENSOR, (byte)-77, null, IntWrapperCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.HUMAN_SENSOR, (byte)-76, IntWrapperCodec.getInstance()));
      commands.add(new Command(Command.Type.GET, Command.Setting.PIXEL_SHIFT, (byte)-79, null, PixelShiftCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.PIXEL_SHIFT, (byte)-78, PixelShiftCodec.getInstance()));
      commands.add(new Command(Command.Type.GET, Command.Setting.ECO_MODE, (byte)99, null, EcoModeCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.ECO_MODE, (byte)100, EcoModeCodec.getInstance()));
      commands.add(new Command(Command.Type.GET, Command.Setting.MUTE, (byte)70, null, MuteCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.MUTE, (byte)71, MuteCodec.getInstance()));
      commands.add(new Command(Command.Type.GET, Command.Setting.PICTURE_STYLE, (byte)101, null, PictureStyleCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.PICTURE_STYLE, (byte)102, PictureStyleCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.MONITOR_RESTART, (byte)87, MonitorRestartCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.TAKE_SCREENSHOT, (byte)88));
      commands.add(new Command(Command.Type.GET, Command.Setting.VIDEO_PRESENT, (byte)89, null, VideoPresentCodec.getInstance()));
      commands.add(new Command(Command.Type.GET, Command.Setting.BACKLIGHT, (byte)113, null, BacklightCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.BACKLIGHT, (byte)114, BacklightCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.OPEN_ANDROID_MENU, (byte)115));
      commands.add(new Command(Command.Type.GET, Command.Setting.NAVIGATION_BAR, (byte)116, null, NavigationBarCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.NAVIGATION_BAR, (byte)117, NavigationBarCodec.getInstance()));
      commands.add(new Command(Command.Type.GET, Command.Setting.BOOT_ON_SOURCE, (byte)-70, null, BootOnSourceCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.BOOT_ON_SOURCE, (byte)-69, BootOnSourceCodec.getInstance()));
      commands.add(new Command(Command.Type.GET, Command.Setting.CLOCK, (byte)-121, null, ClockParameterCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.CLOCK, (byte)-122, ClockParameterCodec.getInstance()));
      commands.add(new Command(Command.Type.GET, Command.Setting.DATE, (byte)-107, null, DateParameterCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.DATE, (byte)-106, DateParameterCodec.getInstance()));
      commands.add(new Command(Command.Type.GET, Command.Setting.AUTO_TIME_SYNC, (byte)-119, null, AutoTimeSyncCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.AUTO_TIME_SYNC, (byte)-120, AutoTimeSyncCodec.getInstance()));
      commands.add(new Command(Command.Type.GET, Command.Setting.TIME_ZONE, (byte)-117, null, TimeZoneCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.TIME_ZONE, (byte)-118, TimeZoneCodec.getInstance()));
      commands.add(new Command(Command.Type.GET, Command.Setting.AUTO_RESTART, (byte)-98, null, AutoRestartParameterCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.AUTO_RESTART, (byte)-97, AutoRestartParameterCodec.getInstance()));
      commands.add(new Command(Command.Type.GET, Command.Setting.LANGUAGE_OSD, (byte)-89, null, LanguageOSDCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.LANGUAGE_OSD, (byte)-88, LanguageOSDCodec.getInstance()));
      commands.add(new Command(Command.Type.GET, Command.Setting.AUDIO_SYNC, (byte)-115, null, AudioSyncCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.AUDIO_SYNC, (byte)-116, AudioSyncCodec.getInstance()));
      commands.add(new Command(Command.Type.GET, Command.Setting.SPEAKERS_STATUS, (byte)-113, null, SpeakersStatusCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.SPEAKERS_STATUS, (byte)-114, SpeakersStatusCodec.getInstance()));
      commands.add(new Command(Command.Type.GET, Command.Setting.TEAMVIEWER_STATUS, (byte)-109, null, TeamviewerStatusCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.TEAMVIEWER_STATUS, (byte)-108, TeamviewerStatusCodec.getInstance()));
      commands.add(new Command(Command.Type.GET, Command.Setting.RS232_ROUTING, (byte)-102, null, RS232RoutingCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.RS232_ROUTING, (byte)-101, RS232RoutingCodec.getInstance()));
      commands.add(new Command(Command.Type.GET, Command.Setting.SICP_SERIAL_PORT_FORWARDING, (byte)-66, null, SicpSerialPortForwardingCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.SICP_SERIAL_PORT_FORWARDING, (byte)-65, SicpSerialPortForwardingCodec.getInstance()));
      commands.add(new Command(Command.Type.GET, Command.Setting.WOL, (byte)-100, null, WOLCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.WOL, (byte)-99, WOLCodec.getInstance()));
      commands.add(new Command(Command.Type.GET, Command.Setting.HDMI_ONE_WIRE, (byte)-68, null, HdmiOneWireCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.HDMI_ONE_WIRE, (byte)-67, HdmiOneWireCodec.getInstance()));
      commands.add(new Command(Command.Type.GET, Command.Setting.OPS_SDM_SETTINGS, (byte)110, null, OpsSdmSettingsCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.OPS_SDM_SETTINGS, (byte)111, OpsSdmSettingsCodec.getInstance()));
      commands.add(new Command(Command.Type.GET, Command.Setting.POWER_LED, (byte)72, null, PowerLEDCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.POWER_LED, (byte)73, PowerLEDCodec.getInstance()));
      commands.add(new Command(Command.Type.GET, Command.Setting.FORCE_RESTART_CUSTOM_APP, (byte)120, null, ForceRestartCustomAppCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.FORCE_RESTART_CUSTOM_APP, (byte)121, ForceRestartCustomAppCodec.getInstance()));
      commands.add(new Command(Command.Type.SET, Command.Setting.OTA_UPDATE_SET, (byte)-31, OtaUpdateSetCodec.getInstance()));
      commands.add(new Command(Command.Type.GET, Command.Setting.OTA_UPDATE_GET, (byte)-30, OtaImageTypeCodec.getInstance(), OtaUpdateGetCodec.getInstance()));
      commands.add(
         new Command(Command.Type.GET, Command.Setting.OTA_GET_FW_VERSION, (byte)-29, OtaImageTypeCodec.getInstance(), StringWrapperCodec.getInstance())
      );
      commands.add(new Command(Command.Type.GET, Command.Setting.GET_NUMBER_OF_INPUT_SOURCES, (byte)-85, NumberOfInputSourcesCodec.getInstance()));
   }
}
