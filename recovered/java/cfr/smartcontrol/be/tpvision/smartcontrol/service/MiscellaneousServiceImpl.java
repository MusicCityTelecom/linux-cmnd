/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.service;

import be.tpvision.smartcontrol.domain.Device;
import be.tpvision.smartcontrol.domain.device_settings.IntWrapper;
import be.tpvision.smartcontrol.domain.device_settings.StringWrapper;
import be.tpvision.smartcontrol.domain.device_settings.general.BootOnSource;
import be.tpvision.smartcontrol.domain.device_settings.general.WOL;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.APM;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.DisplayOrientation;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.EcoMode;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.FanSpeed;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.HdmiOneWire;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.LedStrips;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.LightSensor;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.LockUsb;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.MEMCEffect;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.Miscellaneous;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.NavigationBar;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.NoiseReduction;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.OSDRotating;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.OtaImageType;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.OtaUpdateGet;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.OtaUpdateSet;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.PixelShift;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.PortStatus;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.PowerOnLogo;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.PowerSavingMode;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.RS232Routing;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.ScanConversion;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.ScanMode;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.SicpSerialPortForwarding;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.SmartPower;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.SwitchOnDelay;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.TeamviewerStatus;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.TemperatureSensor;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.Tiling;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.Touch;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.VideoAlignment;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.VideoPresent;
import be.tpvision.smartcontrol.messages.services.miscellaneous.ConstructorMessages;
import be.tpvision.smartcontrol.messages.services.miscellaneous.FactoryResetMessages;
import be.tpvision.smartcontrol.messages.services.miscellaneous.GetDisplayOrientationMessages;
import be.tpvision.smartcontrol.messages.services.miscellaneous.GetLedStripsMessages;
import be.tpvision.smartcontrol.messages.services.miscellaneous.GetPortStatusMessages;
import be.tpvision.smartcontrol.messages.services.miscellaneous.OpenAndroidMenuMessages;
import be.tpvision.smartcontrol.messages.services.miscellaneous.SetApmMessages;
import be.tpvision.smartcontrol.messages.services.miscellaneous.SetDisplayOrientationMessages;
import be.tpvision.smartcontrol.messages.services.miscellaneous.SetEcoModeMessages;
import be.tpvision.smartcontrol.messages.services.miscellaneous.SetFanSpeedMessages;
import be.tpvision.smartcontrol.messages.services.miscellaneous.SetFrameCompensationHorizontalMessages;
import be.tpvision.smartcontrol.messages.services.miscellaneous.SetFrameCompensationVerticalMessages;
import be.tpvision.smartcontrol.messages.services.miscellaneous.SetHumanSensorMessages;
import be.tpvision.smartcontrol.messages.services.miscellaneous.SetLedStripsMessages;
import be.tpvision.smartcontrol.messages.services.miscellaneous.SetLightSensorMessages;
import be.tpvision.smartcontrol.messages.services.miscellaneous.SetLockUsbMessages;
import be.tpvision.smartcontrol.messages.services.miscellaneous.SetMemcEffectMessages;
import be.tpvision.smartcontrol.messages.services.miscellaneous.SetNavigationBarMessages;
import be.tpvision.smartcontrol.messages.services.miscellaneous.SetNoiseReductionMessages;
import be.tpvision.smartcontrol.messages.services.miscellaneous.SetOffTimerMessages;
import be.tpvision.smartcontrol.messages.services.miscellaneous.SetOsdInformationMessages;
import be.tpvision.smartcontrol.messages.services.miscellaneous.SetOsdRotatingMessages;
import be.tpvision.smartcontrol.messages.services.miscellaneous.SetPixelShiftMessages;
import be.tpvision.smartcontrol.messages.services.miscellaneous.SetPortStatusMessages;
import be.tpvision.smartcontrol.messages.services.miscellaneous.SetPowerOnLogoMessages;
import be.tpvision.smartcontrol.messages.services.miscellaneous.SetPowerSavingModeMessages;
import be.tpvision.smartcontrol.messages.services.miscellaneous.SetScanConversionMessages;
import be.tpvision.smartcontrol.messages.services.miscellaneous.SetScanModeMessages;
import be.tpvision.smartcontrol.messages.services.miscellaneous.SetSmartPowerMessages;
import be.tpvision.smartcontrol.messages.services.miscellaneous.SetSwitchOnDelayMessages;
import be.tpvision.smartcontrol.messages.services.miscellaneous.SetTilingMessages;
import be.tpvision.smartcontrol.messages.services.miscellaneous.SetTouchMessages;
import be.tpvision.smartcontrol.messages.services.miscellaneous.SetVideoAlignmentMessages;
import be.tpvision.smartcontrol.messages.services.miscellaneous.TakeScreenshotMessages;
import be.tpvision.smartcontrol.protocol.sicp.Command;
import be.tpvision.smartcontrol.service.CommandService;
import be.tpvision.smartcontrol.service.MiscellaneousService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
@Profile(value={"production"})
public class MiscellaneousServiceImpl
implements MiscellaneousService {
    private final CommandService commandService;

    @Autowired
    MiscellaneousServiceImpl(CommandService commandService) {
        Assert.notNull((Object)commandService, ConstructorMessages.COMMAND_SERVICE_CAN_NOT_BE_NULL);
        this.commandService = commandService;
    }

    @Override
    public Miscellaneous getMiscellaneous(Device device) {
        if (device == null) {
            return null;
        }
        return this.commandService.send(device, Command.Type.GET, Command.Setting.MISCELLANEOUS, Miscellaneous.Info.OPERATING_HOURS, Miscellaneous.class);
    }

    @Override
    public SmartPower getSmartPower(Device device) {
        if (device == null) {
            return null;
        }
        return this.commandService.send(device, Command.Type.GET, Command.Setting.SMART_POWER, SmartPower.class);
    }

    @Override
    public void setSmartPower(Device device, SmartPower smartPower) {
        Assert.notNull((Object)device, SetSmartPowerMessages.DEVICE_CAN_NOT_BE_NULL);
        Assert.notNull((Object)smartPower, SetSmartPowerMessages.SMART_POWER_CAN_NOT_BE_NULL);
        this.commandService.send(device, Command.Type.SET, Command.Setting.SMART_POWER, smartPower);
    }

    @Override
    public void setVideoAlignment(Device device, VideoAlignment videoAlignment) {
        Assert.notNull((Object)device, SetVideoAlignmentMessages.DEVICE_CAN_NOT_BE_NULL);
        Assert.notNull((Object)videoAlignment, SetVideoAlignmentMessages.VIDEO_ALIGNMENT_CAN_NOT_BE_NULL);
        this.commandService.send(device, Command.Type.SET, Command.Setting.VIDEO_ALIGNMENT, videoAlignment);
    }

    @Override
    public TemperatureSensor getTemperatureSensor(Device device) {
        if (device == null) {
            return null;
        }
        return this.commandService.send(device, Command.Type.GET, Command.Setting.TEMPERATURE_SENSOR, TemperatureSensor.class);
    }

    @Override
    public StringWrapper getSerialCode(Device device) {
        if (device == null) {
            return null;
        }
        return this.commandService.send(device, Command.Type.GET, Command.Setting.SERIAL_CODE, StringWrapper.class);
    }

    @Override
    public Tiling getTiling(Device device) {
        if (device == null) {
            return null;
        }
        return this.commandService.send(device, Command.Type.GET, Command.Setting.TILING, Tiling.class);
    }

    @Override
    public void setTiling(Device device, Tiling tiling) {
        Assert.notNull((Object)device, SetTilingMessages.DEVICE_CAN_NOT_BE_NULL);
        Assert.notNull((Object)tiling, SetTilingMessages.TILING_CAN_NOT_BE_NULL);
        this.commandService.send(device, Command.Type.SET, Command.Setting.TILING, tiling);
    }

    @Override
    public IntWrapper getFrameCompensationHorizontal(Device device) {
        if (device == null) {
            return null;
        }
        return this.commandService.send(device, Command.Type.GET, Command.Setting.FRAME_COMPENSATION_HORIZONTAL, IntWrapper.class);
    }

    @Override
    public void setFrameCompensationHorizontal(Device device, IntWrapper frameCompensationHorizontal) {
        Assert.notNull((Object)device, SetFrameCompensationHorizontalMessages.DEVICE_CAN_NOT_BE_NULL);
        Assert.notNull((Object)frameCompensationHorizontal, SetFrameCompensationHorizontalMessages.FRAME_COMPENSATION_HORIZONTAL_CAN_NOT_BE_NULL);
        this.commandService.send(device, Command.Type.SET, Command.Setting.FRAME_COMPENSATION_HORIZONTAL, frameCompensationHorizontal);
    }

    @Override
    public IntWrapper getFrameCompensationVertical(Device device) {
        if (device == null) {
            return null;
        }
        return this.commandService.send(device, Command.Type.GET, Command.Setting.FRAME_COMPENSATION_VERTICAL, IntWrapper.class);
    }

    @Override
    public void setFrameCompensationVertical(Device device, IntWrapper frameCompensationVertical) {
        Assert.notNull((Object)device, SetFrameCompensationVerticalMessages.DEVICE_CAN_NOT_BE_NULL);
        Assert.notNull((Object)frameCompensationVertical, SetFrameCompensationVerticalMessages.FRAME_COMPENSATION_VERTICAL_CAN_NOT_BE_NULL);
        this.commandService.send(device, Command.Type.SET, Command.Setting.FRAME_COMPENSATION_VERTICAL, frameCompensationVertical);
    }

    @Override
    public LightSensor getLightSensor(Device device) {
        if (device == null) {
            return null;
        }
        return this.commandService.send(device, Command.Type.GET, Command.Setting.LIGHT_SENSOR, LightSensor.class);
    }

    @Override
    public void setLightSensor(Device device, LightSensor lightSensor) {
        Assert.notNull((Object)device, SetLightSensorMessages.DEVICE_CAN_NOT_BE_NULL);
        Assert.notNull((Object)lightSensor, SetLightSensorMessages.LIGHT_SENSOR_CAN_NOT_BE_NULL);
        this.commandService.send(device, Command.Type.SET, Command.Setting.LIGHT_SENSOR, lightSensor);
    }

    @Override
    public OSDRotating getOSDRotating(Device device) {
        if (device == null) {
            return null;
        }
        return this.commandService.send(device, Command.Type.GET, Command.Setting.OSD_ROTATING, OSDRotating.class);
    }

    @Override
    public void setOSDRotating(Device device, OSDRotating osdRotating) {
        Assert.notNull((Object)device, SetOsdRotatingMessages.DEVICE_CAN_NOT_BE_NULL);
        Assert.notNull((Object)osdRotating, SetOsdRotatingMessages.OSD_ROTATING_CAN_NOT_BE_NULL);
        this.commandService.send(device, Command.Type.SET, Command.Setting.OSD_ROTATING, osdRotating);
    }

    @Override
    public IntWrapper getOSDInformation(Device device) {
        if (device == null) {
            return null;
        }
        return this.commandService.send(device, Command.Type.GET, Command.Setting.OSD_INFORMATION, IntWrapper.class);
    }

    @Override
    public void setOSDInformation(Device device, IntWrapper osdInformation) {
        Assert.notNull((Object)device, SetOsdInformationMessages.DEVICE_CAN_NOT_BE_NULL);
        Assert.notNull((Object)osdInformation, SetOsdInformationMessages.OSD_INFORMATION_CAN_NOT_BE_NULL);
        this.commandService.send(device, Command.Type.SET, Command.Setting.OSD_INFORMATION, osdInformation);
    }

    @Override
    public MEMCEffect getMEMCEffect(Device device) {
        if (device == null) {
            return null;
        }
        return this.commandService.send(device, Command.Type.GET, Command.Setting.MEMC_EFFECT, MEMCEffect.class);
    }

    @Override
    public void setMEMCEffect(Device device, MEMCEffect memcEffect) {
        Assert.notNull((Object)device, SetMemcEffectMessages.DEVICE_CAN_NOT_BE_NULL);
        Assert.notNull((Object)memcEffect, SetMemcEffectMessages.MEMC_EFFECT_CAN_NOT_BE_NULL);
        this.commandService.send(device, Command.Type.SET, Command.Setting.MEMC_EFFECT, memcEffect);
    }

    @Override
    public Touch getTouch(Device device) {
        if (device == null) {
            return null;
        }
        return this.commandService.send(device, Command.Type.GET, Command.Setting.TOUCH, Touch.class);
    }

    @Override
    public void setTouch(Device device, Touch touch) {
        Assert.notNull((Object)device, SetTouchMessages.DEVICE_CAN_NOT_BE_NULL);
        Assert.notNull((Object)touch, SetTouchMessages.TOUCH_CAN_NOT_BE_NULL);
        this.commandService.send(device, Command.Type.SET, Command.Setting.TOUCH, touch);
    }

    @Override
    public NoiseReduction getNoiseReduction(Device device) {
        if (device == null) {
            return null;
        }
        return this.commandService.send(device, Command.Type.GET, Command.Setting.NOISE_REDUCTION, NoiseReduction.class);
    }

    @Override
    public void setNoiseReduction(Device device, NoiseReduction noiseReduction) {
        Assert.notNull((Object)device, SetNoiseReductionMessages.DEVICE_CAN_NOT_BE_NULL);
        Assert.notNull((Object)noiseReduction, SetNoiseReductionMessages.NOISE_REDUCTION_CAN_NOT_BE_NULL);
        this.commandService.send(device, Command.Type.SET, Command.Setting.NOISE_REDUCTION, noiseReduction);
    }

    @Override
    public ScanMode getScanMode(Device device) {
        if (device == null) {
            return null;
        }
        return this.commandService.send(device, Command.Type.GET, Command.Setting.SCAN_MODE, ScanMode.class);
    }

    @Override
    public void setScanMode(Device device, ScanMode scanMode) {
        Assert.notNull((Object)device, SetScanModeMessages.DEVICE_CAN_NOT_BE_NULL);
        Assert.notNull((Object)scanMode, SetScanModeMessages.SCAN_MODE_CAN_NOT_BE_NULL);
        this.commandService.send(device, Command.Type.SET, Command.Setting.SCAN_MODE, scanMode);
    }

    @Override
    public ScanConversion getScanConversion(Device device) {
        if (device == null) {
            return null;
        }
        return this.commandService.send(device, Command.Type.GET, Command.Setting.SCAN_CONVERSION, ScanConversion.class);
    }

    @Override
    public void setScanConversion(Device device, ScanConversion scanConversion) {
        Assert.notNull((Object)device, SetScanConversionMessages.DEVICE_CAN_NOT_BE_NULL);
        Assert.notNull((Object)scanConversion, SetScanConversionMessages.SCAN_CONVERSION_CAN_NOT_BE_NULL);
        this.commandService.send(device, Command.Type.SET, Command.Setting.SCAN_CONVERSION, scanConversion);
    }

    @Override
    public SwitchOnDelay getSwitchOnDelay(Device device) {
        if (device == null) {
            return null;
        }
        return this.commandService.send(device, Command.Type.GET, Command.Setting.SWITCH_ON_DELAY, SwitchOnDelay.class);
    }

    @Override
    public void setSwitchOnDelay(Device device, SwitchOnDelay switchOnDelay) {
        Assert.notNull((Object)device, SetSwitchOnDelayMessages.DEVICE_CAN_NOT_BE_NULL);
        Assert.notNull((Object)switchOnDelay, SetSwitchOnDelayMessages.SWITCH_ON_DELAY_CAN_NOT_BE_NULL);
        this.commandService.send(device, Command.Type.SET, Command.Setting.SWITCH_ON_DELAY, switchOnDelay);
    }

    @Override
    public void factoryReset(Device device) {
        Assert.notNull((Object)device, FactoryResetMessages.DEVICE_CAN_NOT_BE_NULL);
        this.commandService.send(device, Command.Type.SET, Command.Setting.FACTORY_RESET);
    }

    @Override
    public PowerOnLogo getPowerOnLogo(Device device) {
        if (device == null) {
            return null;
        }
        return this.commandService.send(device, Command.Type.GET, Command.Setting.POWER_ON_LOGO, PowerOnLogo.class);
    }

    @Override
    public void setPowerOnLogo(Device device, PowerOnLogo powerOnLogo) {
        Assert.notNull((Object)device, SetPowerOnLogoMessages.DEVICE_CAN_NOT_BE_NULL);
        Assert.notNull((Object)powerOnLogo, SetPowerOnLogoMessages.POWER_ON_LOGO_CAN_NOT_BE_NULL);
        this.commandService.send(device, Command.Type.SET, Command.Setting.POWER_ON_LOGO, powerOnLogo);
    }

    @Override
    public FanSpeed getFanSpeed(Device device) {
        if (device == null) {
            return null;
        }
        return this.commandService.send(device, Command.Type.GET, Command.Setting.FAN_SPEED, FanSpeed.class);
    }

    @Override
    public void setFanSpeed(Device device, FanSpeed fanSpeed) {
        Assert.notNull((Object)device, SetFanSpeedMessages.DEVICE_CAN_NOT_BE_NULL);
        Assert.notNull((Object)fanSpeed, SetFanSpeedMessages.FAN_SPEED_CAN_NOT_BE_NULL);
        this.commandService.send(device, Command.Type.SET, Command.Setting.FAN_SPEED, fanSpeed);
    }

    @Override
    public APM getAPM(Device device) {
        if (device == null) {
            return null;
        }
        return this.commandService.send(device, Command.Type.GET, Command.Setting.APM, APM.class);
    }

    @Override
    public void setAPM(Device device, APM apm) {
        Assert.notNull((Object)device, SetApmMessages.DEVICE_CAN_NOT_BE_NULL);
        Assert.notNull((Object)apm, SetApmMessages.APM_CAN_NOT_BE_NULL);
        this.commandService.send(device, Command.Type.SET, Command.Setting.APM, apm);
    }

    @Override
    public PowerSavingMode getPowerSavingMode(Device device) {
        if (device == null) {
            return null;
        }
        return this.commandService.send(device, Command.Type.GET, Command.Setting.POWER_SAVING_MODE, PowerSavingMode.class);
    }

    @Override
    public void setPowerSavingMode(Device device, PowerSavingMode powerSavingMode) {
        Assert.notNull((Object)device, SetPowerSavingModeMessages.DEVICE_CAN_NOT_BE_NULL);
        Assert.notNull((Object)powerSavingMode, SetPowerSavingModeMessages.POWER_SAVING_MODE_CAN_NOT_BE_NULL);
        this.commandService.send(device, Command.Type.SET, Command.Setting.POWER_SAVING_MODE, powerSavingMode);
    }

    @Override
    public DisplayOrientation getDisplayOrientation(Device device) {
        Assert.notNull((Object)device, GetDisplayOrientationMessages.DEVICE_CAN_NOT_BE_NULL);
        return this.commandService.send(device, Command.Type.GET, Command.Setting.DISPLAY_ORIENTATION, DisplayOrientation.class);
    }

    @Override
    public void setDisplayOrientation(Device device, DisplayOrientation displayOrientation) {
        Assert.notNull((Object)device, SetDisplayOrientationMessages.DEVICE_CAN_NOT_BE_NULL);
        Assert.notNull((Object)displayOrientation, SetDisplayOrientationMessages.DISPLAY_ORIENTATION_CAN_NOT_BE_NULL);
        this.commandService.send(device, Command.Type.SET, Command.Setting.DISPLAY_ORIENTATION, displayOrientation);
    }

    @Override
    public LedStrips getLedStrips(Device device) {
        Assert.notNull((Object)device, GetLedStripsMessages.DEVICE_CAN_NOT_BE_NULL);
        return this.commandService.send(device, Command.Type.GET, Command.Setting.LED_STRIPS, LedStrips.class);
    }

    @Override
    public void setLedStrips(Device device, LedStrips ledStrips) {
        Assert.notNull((Object)device, SetLedStripsMessages.DEVICE_CAN_NOT_BE_NULL);
        Assert.notNull((Object)ledStrips, SetLedStripsMessages.LED_STRIPS_CAN_NOT_BE_NULL);
        this.commandService.send(device, Command.Type.SET, Command.Setting.LED_STRIPS, ledStrips);
    }

    @Override
    public PortStatus getPortStatus(Device device) {
        Assert.notNull((Object)device, GetPortStatusMessages.DEVICE_CAN_NOT_BE_NULL);
        return this.commandService.send(device, Command.Type.GET, Command.Setting.PORT_STATUS, PortStatus.class);
    }

    @Override
    public void setPortStatus(Device device, PortStatus portStatus) {
        Assert.notNull((Object)device, SetPortStatusMessages.DEVICE_CAN_NOT_BE_NULL);
        Assert.notNull((Object)portStatus, SetPortStatusMessages.PORT_STATUS_CAN_NOT_BE_NULL);
        this.commandService.send(device, Command.Type.SET, Command.Setting.PORT_STATUS, portStatus);
    }

    @Override
    public IntWrapper getOffTimer(Device device) {
        if (device == null) {
            return null;
        }
        return this.commandService.send(device, Command.Type.GET, Command.Setting.OFF_TIMER, IntWrapper.class);
    }

    @Override
    public void setOffTimer(Device device, IntWrapper offTimer) {
        Assert.notNull((Object)device, SetOffTimerMessages.DEVICE_CAN_NOT_BE_NULL);
        Assert.notNull((Object)offTimer, SetOffTimerMessages.OFF_TIMER_CAN_NOT_BE_NULL);
        this.commandService.send(device, Command.Type.SET, Command.Setting.OFF_TIMER, offTimer);
    }

    @Override
    public LockUsb getLockUsb(Device device) {
        if (device == null) {
            return null;
        }
        return this.commandService.send(device, Command.Type.GET, Command.Setting.LOCK_USB, LockUsb.class);
    }

    @Override
    public void setLockUsb(Device device, LockUsb lockUsb) {
        Assert.notNull((Object)device, SetLockUsbMessages.DEVICE_CAN_NOT_BE_NULL);
        Assert.notNull((Object)lockUsb, SetLockUsbMessages.LOCK_USB_CAN_NOT_BE_NULL);
        this.commandService.send(device, Command.Type.SET, Command.Setting.LOCK_USB, lockUsb);
    }

    @Override
    public IntWrapper getHumanSensor(Device device) {
        if (device == null) {
            return null;
        }
        return this.commandService.send(device, Command.Type.GET, Command.Setting.HUMAN_SENSOR, IntWrapper.class);
    }

    @Override
    public void setHumanSensor(Device device, IntWrapper humanSensor) {
        Assert.notNull((Object)device, SetHumanSensorMessages.DEVICE_CAN_NOT_BE_NULL);
        Assert.notNull((Object)humanSensor, SetHumanSensorMessages.HUMAN_SENSOR_CAN_NOT_BE_NULL);
        this.commandService.send(device, Command.Type.SET, Command.Setting.HUMAN_SENSOR, humanSensor);
    }

    @Override
    public PixelShift getPixelShift(Device device) {
        if (device == null) {
            return null;
        }
        return this.commandService.send(device, Command.Type.GET, Command.Setting.PIXEL_SHIFT, PixelShift.class);
    }

    @Override
    public void setPixelShift(Device device, PixelShift pixelShift) {
        Assert.notNull((Object)device, SetPixelShiftMessages.DEVICE_CAN_NOT_BE_NULL);
        Assert.notNull((Object)pixelShift, SetPixelShiftMessages.PIXEL_SHIFT_CAN_NOT_BE_NULL);
        this.commandService.send(device, Command.Type.SET, Command.Setting.PIXEL_SHIFT, pixelShift);
    }

    @Override
    public EcoMode getEcoMode(Device device) {
        if (device == null) {
            return null;
        }
        return this.commandService.send(device, Command.Type.GET, Command.Setting.ECO_MODE, EcoMode.class);
    }

    @Override
    public void setEcoMode(Device device, EcoMode ecoMode) {
        Assert.notNull((Object)device, SetEcoModeMessages.DEVICE_CAN_NOT_BE_NULL);
        Assert.notNull((Object)ecoMode, SetEcoModeMessages.ECO_MODE_CAN_NOT_BE_NULL);
        this.commandService.send(device, Command.Type.SET, Command.Setting.ECO_MODE, ecoMode);
    }

    @Override
    public void takeScreenshot(Device device) {
        Assert.notNull((Object)device, TakeScreenshotMessages.DEVICE_CAN_NOT_BE_NULL);
        this.commandService.send(device, Command.Type.SET, Command.Setting.TAKE_SCREENSHOT);
    }

    @Override
    public VideoPresent getVideoPresent(Device device) {
        if (device == null) {
            return null;
        }
        return this.commandService.send(device, Command.Type.GET, Command.Setting.VIDEO_PRESENT, VideoPresent.class);
    }

    @Override
    public void openAndroidMenu(Device device) {
        Assert.notNull((Object)device, OpenAndroidMenuMessages.DEVICE_CAN_NOT_BE_NULL);
        this.commandService.send(device, Command.Type.SET, Command.Setting.OPEN_ANDROID_MENU);
    }

    @Override
    public NavigationBar getNavigationBar(Device device) {
        if (device == null) {
            return null;
        }
        return this.commandService.send(device, Command.Type.GET, Command.Setting.NAVIGATION_BAR, NavigationBar.class);
    }

    @Override
    public void setNavigationBar(Device device, NavigationBar navigationBar) {
        Assert.notNull((Object)device, SetNavigationBarMessages.DEVICE_CAN_NOT_BE_NULL);
        this.commandService.send(device, Command.Type.SET, Command.Setting.NAVIGATION_BAR, navigationBar);
    }

    @Override
    public BootOnSource getBootOnSource(Device device) {
        if (device == null) {
            return null;
        }
        return this.commandService.send(device, Command.Type.GET, Command.Setting.BOOT_ON_SOURCE, BootOnSource.class);
    }

    @Override
    public void setBootOnSource(Device device, BootOnSource bootOnSource) {
        Assert.notNull((Object)device, SetNavigationBarMessages.DEVICE_CAN_NOT_BE_NULL);
        this.commandService.send(device, Command.Type.SET, Command.Setting.BOOT_ON_SOURCE, bootOnSource);
    }

    @Override
    public void setOtaUpdateStatus(Device device, OtaUpdateSet otaUpdateSet) {
        this.commandService.send(device, Command.Type.SET, Command.Setting.OTA_UPDATE_SET, otaUpdateSet);
    }

    @Override
    public OtaUpdateGet getOtaUpdateStatus(Device device, OtaImageType otaImageType) {
        if (device == null) {
            return null;
        }
        return this.commandService.send(device, Command.Type.GET, Command.Setting.OTA_UPDATE_GET, otaImageType, OtaUpdateGet.class);
    }

    @Override
    public StringWrapper getOtaFwVersion(Device device, OtaImageType otaImageType) {
        if (device == null) {
            return null;
        }
        return this.commandService.send(device, Command.Type.GET, Command.Setting.OTA_GET_FW_VERSION, otaImageType, StringWrapper.class);
    }

    @Override
    public TeamviewerStatus getTeamviewerStatus(Device device) {
        if (device == null) {
            return null;
        }
        return this.commandService.send(device, Command.Type.GET, Command.Setting.TEAMVIEWER_STATUS, TeamviewerStatus.class);
    }

    @Override
    public void setTeamviewerStatus(Device device, TeamviewerStatus teamviewerStatus) {
        this.commandService.send(device, Command.Type.SET, Command.Setting.TEAMVIEWER_STATUS, teamviewerStatus);
    }

    @Override
    public RS232Routing getRS232Routing(Device device) {
        if (device == null) {
            return null;
        }
        return this.commandService.send(device, Command.Type.GET, Command.Setting.RS232_ROUTING, RS232Routing.class);
    }

    @Override
    public void setRS232Routing(Device device, RS232Routing rS232Routing) {
        this.commandService.send(device, Command.Type.SET, Command.Setting.RS232_ROUTING, rS232Routing);
    }

    @Override
    public SicpSerialPortForwarding getSicpSerialPortForwarding(Device device) {
        if (device == null) {
            return null;
        }
        return this.commandService.send(device, Command.Type.GET, Command.Setting.SICP_SERIAL_PORT_FORWARDING, SicpSerialPortForwarding.class);
    }

    @Override
    public void setSicpSerialPortForwarding(Device device, SicpSerialPortForwarding sicpSerialPortForwarding) {
        this.commandService.send(device, Command.Type.SET, Command.Setting.SICP_SERIAL_PORT_FORWARDING, sicpSerialPortForwarding);
    }

    @Override
    public WOL getWOL(Device device) {
        if (device == null) {
            return null;
        }
        return this.commandService.send(device, Command.Type.GET, Command.Setting.WOL, WOL.class);
    }

    @Override
    public void setWOL(Device device, WOL wOL) {
        this.commandService.send(device, Command.Type.SET, Command.Setting.WOL, wOL);
    }

    @Override
    public HdmiOneWire getHdmiOneWire(Device device) {
        if (device == null) {
            return null;
        }
        return this.commandService.send(device, Command.Type.GET, Command.Setting.HDMI_ONE_WIRE, HdmiOneWire.class);
    }

    @Override
    public void setHdmiOneWire(Device device, HdmiOneWire hdmiOneWire) {
        this.commandService.send(device, Command.Type.SET, Command.Setting.HDMI_ONE_WIRE, hdmiOneWire);
    }
}

