/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.protocol.sicp204;

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
import be.tpvision.smartcontrol.protocol.sicp.Command;
import be.tpvision.smartcontrol.protocol.sicp.Commands;
import be.tpvision.smartcontrol.protocol.sicp.SicpCommandFactoryImpl;
import be.tpvision.smartcontrol.protocol.sicp.SicpVersion;

public class SicpCommandFactory
extends SicpCommandFactoryImpl {
    private static final Commands commands = new Commands();
    private static SicpCommandFactory sicpCommandFactory;

    private SicpCommandFactory() {
        super(SicpVersion._204, commands);
    }

    public static synchronized SicpCommandFactory getInstance() {
        if (sicpCommandFactory == null) {
            sicpCommandFactory = new SicpCommandFactory();
        }
        return sicpCommandFactory;
    }

    static {
        commands.add(new Command(Command.Type.GET, Command.Setting.COMMUNICATION_CONTROL, 0, null, CommunicationControlCodec.getInstance()));
        commands.add(new Command(Command.Type.GET, Command.Setting.VOLUME, 69, null, VolumeCodec.getInstance()));
        commands.add(new Command(Command.Type.SET, Command.Setting.VOLUME, 68, VolumeCodec.getInstance()));
        commands.add(new Command(Command.Type.SET, Command.Setting.VOLUME_UP_DOWN, 65, VolumeUpDownCodec.getInstance()));
        commands.add(new Command(Command.Type.GET, Command.Setting.VOLUME_LIMITS_SPEAKER_OUT, -74, null, VolumeLimitsCodec.getInstance()));
        commands.add(new Command(Command.Type.SET, Command.Setting.VOLUME_LIMITS_SPEAKER_OUT, -72, VolumeLimitsCodec.getInstance()));
        commands.add(new Command(Command.Type.GET, Command.Setting.VOLUME_LIMITS_AUDIO_OUT, -73, null, VolumeLimitsCodec.getInstance()));
        commands.add(new Command(Command.Type.SET, Command.Setting.VOLUME_LIMITS_AUDIO_OUT, -71, VolumeLimitsCodec.getInstance()));
        commands.add(new Command(Command.Type.GET, Command.Setting.AUDIO_PARAMETERS, 67, null, AudioParametersCodec.getInstance()));
        commands.add(new Command(Command.Type.SET, Command.Setting.AUDIO_PARAMETERS, 66, AudioParametersCodec.getInstance()));
        commands.add(new Command(Command.Type.GET, Command.Setting.POWER_STATE, 25, null, PowerStateCodec.getInstance()));
        commands.add(new Command(Command.Type.SET, Command.Setting.POWER_STATE, 24, PowerStateCodec.getInstance()));
        commands.add(new Command(Command.Type.GET, Command.Setting.REMOTE_CONTROL_LOCK_STATE, 29, null, RemoteControlLockStateCodec.getInstance()));
        commands.add(new Command(Command.Type.SET, Command.Setting.REMOTE_CONTROL_LOCK_STATE, 28, RemoteControlLockStateCodec.getInstance()));
        commands.add(new Command(Command.Type.GET, Command.Setting.KEYPAD_LOCK_STATE, 27, null, KeypadLockStateCodec.getInstance()));
        commands.add(new Command(Command.Type.SET, Command.Setting.KEYPAD_LOCK_STATE, 26, KeypadLockStateCodec.getInstance()));
        commands.add(new Command(Command.Type.GET, Command.Setting.POWER_STATE_AT_COLD_START, -92, null, PowerStateAtColdStartCodec.getInstance()));
        commands.add(new Command(Command.Type.SET, Command.Setting.POWER_STATE_AT_COLD_START, -93, PowerStateAtColdStartCodec.getInstance()));
        commands.add(new Command(Command.Type.GET, Command.Setting.INPUT_SOURCE, -83, null, InputSourceCodec.getInstance()));
        commands.add(new Command(Command.Type.SET, Command.Setting.INPUT_SOURCE, -84, InputSourceCodec.getInstance()));
        commands.add(new Command(Command.Type.GET, Command.Setting.AUTO_SIGNAL_DETECTING, -81, null, AutoSignalDetectingCodec.getInstance()));
        commands.add(new Command(Command.Type.SET, Command.Setting.AUTO_SIGNAL_DETECTING, -82, AutoSignalDetectingCodec.getInstance()));
        commands.add(new Command(Command.Type.GET, Command.Setting.FAILOVERS, -90, null, FailoversCodec.getInstance()));
        commands.add(new Command(Command.Type.SET, Command.Setting.FAILOVERS, -91, FailoversCodec.getInstance()));
        commands.add(new Command(Command.Type.GET, Command.Setting.MISCELLANEOUS, 15, MiscellaneousCodec.InfoCodec.getInstance(), MiscellaneousCodec.getInstance()));
        commands.add(new Command(Command.Type.GET, Command.Setting.SMART_POWER, -34, null, SmartPowerCodec.getInstance()));
        commands.add(new Command(Command.Type.SET, Command.Setting.SMART_POWER, -35, SmartPowerCodec.getInstance()));
        commands.add(new Command(Command.Type.SET, Command.Setting.VIDEO_ALIGNMENT, 112, VideoAlignmentCodec.getInstance()));
        commands.add(new Command(Command.Type.GET, Command.Setting.TEMPERATURE_SENSOR, 47, null, TemperatureSensorCodec.getInstance()));
        commands.add(new Command(Command.Type.GET, Command.Setting.SERIAL_CODE, 21, null, StringWrapperCodec.getInstance()));
        commands.add(new Command(Command.Type.GET, Command.Setting.TILING, 35, null, TilingCodec.getInstance()));
        commands.add(new Command(Command.Type.SET, Command.Setting.TILING, 34, TilingCodec.getInstance()));
        commands.add(new Command(Command.Type.GET, Command.Setting.FRAME_COMPENSATION_HORIZONTAL, 94, null, IntWrapperCodec.getInstance()));
        commands.add(new Command(Command.Type.SET, Command.Setting.FRAME_COMPENSATION_HORIZONTAL, 95, IntWrapperCodec.getInstance()));
        commands.add(new Command(Command.Type.GET, Command.Setting.FRAME_COMPENSATION_VERTICAL, 103, null, IntWrapperCodec.getInstance()));
        commands.add(new Command(Command.Type.SET, Command.Setting.FRAME_COMPENSATION_VERTICAL, 104, IntWrapperCodec.getInstance()));
        commands.add(new Command(Command.Type.GET, Command.Setting.LIGHT_SENSOR, 37, null, LightSensorCodec.getInstance()));
        commands.add(new Command(Command.Type.SET, Command.Setting.LIGHT_SENSOR, 36, LightSensorCodec.getInstance()));
        commands.add(new Command(Command.Type.GET, Command.Setting.OSD_ROTATING, 39, null, OSDRotatingCodec.getInstance()));
        commands.add(new Command(Command.Type.SET, Command.Setting.OSD_ROTATING, 38, OSDRotatingCodec.getInstance()));
        commands.add(new Command(Command.Type.GET, Command.Setting.OSD_INFORMATION, 45, null, IntWrapperCodec.getInstance()));
        commands.add(new Command(Command.Type.SET, Command.Setting.OSD_INFORMATION, 44, IntWrapperCodec.getInstance()));
        commands.add(new Command(Command.Type.GET, Command.Setting.MEMC_EFFECT, 41, null, MEMCEffectCodec.getInstance()));
        commands.add(new Command(Command.Type.SET, Command.Setting.MEMC_EFFECT, 40, MEMCEffectCodec.getInstance()));
        commands.add(new Command(Command.Type.GET, Command.Setting.TOUCH, 31, null, TouchCodec.getInstance()));
        commands.add(new Command(Command.Type.SET, Command.Setting.TOUCH, 30, TouchCodec.getInstance()));
        commands.add(new Command(Command.Type.GET, Command.Setting.NOISE_REDUCTION, 43, null, NoiseReductionCodec.getInstance()));
        commands.add(new Command(Command.Type.SET, Command.Setting.NOISE_REDUCTION, 42, NoiseReductionCodec.getInstance()));
        commands.add(new Command(Command.Type.GET, Command.Setting.SCAN_MODE, 81, null, ScanModeCodec.getInstance()));
        commands.add(new Command(Command.Type.SET, Command.Setting.SCAN_MODE, 80, ScanModeCodec.getInstance()));
        commands.add(new Command(Command.Type.GET, Command.Setting.SCAN_CONVERSION, 83, null, ScanConversionCodec.getInstance()));
        commands.add(new Command(Command.Type.SET, Command.Setting.SCAN_CONVERSION, 82, ScanConversionCodec.getInstance()));
        commands.add(new Command(Command.Type.GET, Command.Setting.SWITCH_ON_DELAY, 85, null, SwitchOnDelayCodec.getInstance()));
        commands.add(new Command(Command.Type.SET, Command.Setting.SWITCH_ON_DELAY, 84, SwitchOnDelayCodec.getInstance()));
        commands.add(new Command(Command.Type.SET, Command.Setting.FACTORY_RESET, 86));
        commands.add(new Command(Command.Type.GET, Command.Setting.POWER_ON_LOGO, 63, null, PowerOnLogoCodec.getInstance()));
        commands.add(new Command(Command.Type.SET, Command.Setting.POWER_ON_LOGO, 62, PowerOnLogoCodec.getInstance()));
        commands.add(new Command(Command.Type.GET, Command.Setting.FAN_SPEED, 98, null, FanSpeedCodec.getInstance()));
        commands.add(new Command(Command.Type.SET, Command.Setting.FAN_SPEED, 97, FanSpeedCodec.getInstance()));
        commands.add(new Command(Command.Type.GET, Command.Setting.APM, -47, null, APMCodec.getInstance()));
        commands.add(new Command(Command.Type.SET, Command.Setting.APM, -48, APMCodec.getInstance()));
        commands.add(new Command(Command.Type.GET, Command.Setting.POWER_SAVING_MODE, -45, null, PowerSavingModeCodec.getInstance()));
        commands.add(new Command(Command.Type.SET, Command.Setting.POWER_SAVING_MODE, -46, PowerSavingModeCodec.getInstance()));
        commands.add(new Command(Command.Type.GET, Command.Setting.DISPLAY_ORIENTATION, 22, null, DisplayOrientationCodec.getInstance()));
        commands.add(new Command(Command.Type.SET, Command.Setting.DISPLAY_ORIENTATION, 23, DisplayOrientationCodec.getInstance()));
        commands.add(new Command(Command.Type.GET, Command.Setting.LED_STRIPS, -12, null, LedStripsCodec.getInstance()));
        commands.add(new Command(Command.Type.SET, Command.Setting.LED_STRIPS, -13, LedStripsCodec.getInstance()));
        commands.add(new Command(Command.Type.GET, Command.Setting.SICP_VERSION_AND_PLATFORM_INFO, -94, SicpAndPlatformInfoCodec.getInstance(), StringWrapperCodec.getInstance()));
        commands.add(new Command(Command.Type.GET, Command.Setting.MODEL_NUMBER_FIRMWARE_VERSION_BUILD_DATE, -95, ModelNumberFirmwareVersionBuildDateInfoCodec.getInstance(), StringWrapperCodec.getInstance()));
        commands.add(new Command(Command.Type.GET, Command.Setting.VIDEO_PARAMETERS, 51, null, VideoParametersCodec.getInstance()));
        commands.add(new Command(Command.Type.SET, Command.Setting.VIDEO_PARAMETERS, 50, VideoParametersCodec.getInstance()));
        commands.add(new Command(Command.Type.GET, Command.Setting.COLOR_TEMPERATURE, 53, null, ColorTemperatureCodec.getInstance()));
        commands.add(new Command(Command.Type.SET, Command.Setting.COLOR_TEMPERATURE, 52, ColorTemperatureCodec.getInstance()));
        commands.add(new Command(Command.Type.GET, Command.Setting.COLOR_PARAMETERS, 55, null, ColorParametersCodec.getInstance()));
        commands.add(new Command(Command.Type.SET, Command.Setting.COLOR_PARAMETERS, 54, ColorParametersCodec.getInstance()));
        commands.add(new Command(Command.Type.GET, Command.Setting.COLOR_TEMPERATURE_100K, 18, null, ColorTemperature100KCodec.getInstance()));
        commands.add(new Command(Command.Type.SET, Command.Setting.COLOR_TEMPERATURE_100K, 17, ColorTemperature100KCodec.getInstance()));
        commands.add(new Command(Command.Type.GET, Command.Setting.PICTURE_FORMAT, 59, null, PictureFormatCodec.getInstance()));
        commands.add(new Command(Command.Type.SET, Command.Setting.PICTURE_FORMAT, 58, PictureFormatCodec.getInstance()));
        commands.add(new Command(Command.Type.GET, Command.Setting.VGA_VIDEO_PARAMETERS, 57, null, VGAVideoParametersCodec.getInstance()));
        commands.add(new Command(Command.Type.SET, Command.Setting.VGA_VIDEO_PARAMETERS, 56, VGAVideoParametersCodec.getInstance()));
        commands.add(new Command(Command.Type.GET, Command.Setting.PICTURE_IN_PICTURE, 61, null, PictureInPictureCodec.getInstance()));
        commands.add(new Command(Command.Type.SET, Command.Setting.PICTURE_IN_PICTURE, 60, PictureInPictureCodec.getInstance()));
        commands.add(new Command(Command.Type.GET, Command.Setting.PICTURE_IN_PICTURE_SOURCE, -123, null, PictureInPictureSourceCodec.getInstance()));
        commands.add(new Command(Command.Type.SET, Command.Setting.PICTURE_IN_PICTURE_SOURCE, -124, PictureInPictureSourceCodec.getInstance()));
        commands.add(new Command(Command.Type.GET, Command.Setting.SCHEDULING_PARAMETERS, 91, IntWrapperCodec.getInstance(), PageCodec.getInstance()));
        commands.add(new Command(Command.Type.SET, Command.Setting.SCHEDULING_PARAMETERS, 90, PageCodec.getInstance()));
        commands.add(new Command(Command.Type.GET, Command.Setting.GROUP_ID, 93, null, IntWrapperCodec.getInstance()));
        commands.add(new Command(Command.Type.SET, Command.Setting.GROUP_ID, 92, IntWrapperCodec.getInstance()));
        commands.add(new Command(Command.Type.GET, Command.Setting.OFF_TIMER, -111, null, IntWrapperCodec.getInstance()));
        commands.add(new Command(Command.Type.SET, Command.Setting.OFF_TIMER, -110, IntWrapperCodec.getInstance()));
        commands.add(new Command(Command.Type.GET, Command.Setting.LOCK_USB, -14, null, LockUsbCodec.getInstance()));
        commands.add(new Command(Command.Type.SET, Command.Setting.LOCK_USB, -15, LockUsbCodec.getInstance()));
        commands.add(new Command(Command.Type.GET, Command.Setting.HUMAN_SENSOR, -77, null, IntWrapperCodec.getInstance()));
        commands.add(new Command(Command.Type.SET, Command.Setting.HUMAN_SENSOR, -76, IntWrapperCodec.getInstance()));
        commands.add(new Command(Command.Type.GET, Command.Setting.PIXEL_SHIFT, -79, null, PixelShiftCodec.getInstance()));
        commands.add(new Command(Command.Type.SET, Command.Setting.PIXEL_SHIFT, -78, PixelShiftCodec.getInstance()));
        commands.add(new Command(Command.Type.GET, Command.Setting.ECO_MODE, 99, null, EcoModeCodec.getInstance()));
        commands.add(new Command(Command.Type.SET, Command.Setting.ECO_MODE, 100, EcoModeCodec.getInstance()));
        commands.add(new Command(Command.Type.GET, Command.Setting.MUTE, 70, null, MuteCodec.getInstance()));
        commands.add(new Command(Command.Type.SET, Command.Setting.MUTE, 71, MuteCodec.getInstance()));
        commands.add(new Command(Command.Type.GET, Command.Setting.PICTURE_STYLE, 101, null, PictureStyleCodec.getInstance()));
        commands.add(new Command(Command.Type.SET, Command.Setting.PICTURE_STYLE, 102, PictureStyleCodec.getInstance()));
        commands.add(new Command(Command.Type.SET, Command.Setting.MONITOR_RESTART, 87, MonitorRestartCodec.getInstance()));
        commands.add(new Command(Command.Type.SET, Command.Setting.TAKE_SCREENSHOT, 88));
        commands.add(new Command(Command.Type.GET, Command.Setting.VIDEO_PRESENT, 89, null, VideoPresentCodec.getInstance()));
        commands.add(new Command(Command.Type.GET, Command.Setting.BACKLIGHT, 113, null, BacklightCodec.getInstance()));
        commands.add(new Command(Command.Type.SET, Command.Setting.BACKLIGHT, 114, BacklightCodec.getInstance()));
        commands.add(new Command(Command.Type.SET, Command.Setting.OPEN_ANDROID_MENU, 115));
        commands.add(new Command(Command.Type.GET, Command.Setting.NAVIGATION_BAR, 116, null, NavigationBarCodec.getInstance()));
        commands.add(new Command(Command.Type.SET, Command.Setting.NAVIGATION_BAR, 117, NavigationBarCodec.getInstance()));
    }
}

