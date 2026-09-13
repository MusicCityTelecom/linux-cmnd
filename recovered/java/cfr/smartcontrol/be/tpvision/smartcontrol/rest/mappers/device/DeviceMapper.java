/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.mappers.device;

import be.tpvision.smartcontrol.domain.Device;
import be.tpvision.smartcontrol.domain.FtpSettings;
import be.tpvision.smartcontrol.domain.MatrixPosition;
import be.tpvision.smartcontrol.domain.device_settings.IntWrapper;
import be.tpvision.smartcontrol.domain.device_settings.StringWrapper;
import be.tpvision.smartcontrol.domain.device_settings.audio.AudioParameters;
import be.tpvision.smartcontrol.domain.device_settings.audio.Mute;
import be.tpvision.smartcontrol.domain.device_settings.audio.Volume;
import be.tpvision.smartcontrol.domain.device_settings.audio.VolumeLimits;
import be.tpvision.smartcontrol.domain.device_settings.general.Backlight;
import be.tpvision.smartcontrol.domain.device_settings.general.BootOnSource;
import be.tpvision.smartcontrol.domain.device_settings.general.KeypadLockState;
import be.tpvision.smartcontrol.domain.device_settings.general.PowerState;
import be.tpvision.smartcontrol.domain.device_settings.general.PowerStateAtColdStart;
import be.tpvision.smartcontrol.domain.device_settings.general.RemoteControlLockState;
import be.tpvision.smartcontrol.domain.device_settings.input_sources.AutoSignalDetecting;
import be.tpvision.smartcontrol.domain.device_settings.input_sources.Failovers;
import be.tpvision.smartcontrol.domain.device_settings.input_sources.InputSource;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.APM;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.EcoMode;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.FanSpeed;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.LightSensor;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.LockUsb;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.MEMCEffect;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.Miscellaneous;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.NavigationBar;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.NoiseReduction;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.OSDRotating;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.PixelShift;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.PowerOnLogo;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.PowerSavingMode;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.ScanConversion;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.ScanMode;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.SmartPower;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.SwitchOnDelay;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.TemperatureSensor;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.Tiling;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.Touch;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.VideoPresent;
import be.tpvision.smartcontrol.domain.device_settings.scheduling.scheduling_parameters.SchedulingParameters;
import be.tpvision.smartcontrol.domain.device_settings.video.ColorParameters;
import be.tpvision.smartcontrol.domain.device_settings.video.ColorTemperature;
import be.tpvision.smartcontrol.domain.device_settings.video.ColorTemperature100K;
import be.tpvision.smartcontrol.domain.device_settings.video.PictureFormat;
import be.tpvision.smartcontrol.domain.device_settings.video.PictureInPicture;
import be.tpvision.smartcontrol.domain.device_settings.video.PictureInPictureSource;
import be.tpvision.smartcontrol.domain.device_settings.video.PictureStyle;
import be.tpvision.smartcontrol.domain.device_settings.video.VGAVideoParameters;
import be.tpvision.smartcontrol.domain.device_settings.video.VideoParameters;
import be.tpvision.smartcontrol.io.ip.IpDestination;
import be.tpvision.smartcontrol.messages.mappers.device.device.ToAddOrEditDeviceViewModelMessages;
import be.tpvision.smartcontrol.messages.mappers.device.device.ToDetectDevicesViewModelMessages;
import be.tpvision.smartcontrol.messages.mappers.device.device.ToDeviceInfoViewModelMessages;
import be.tpvision.smartcontrol.messages.mappers.device.device.ToDeviceMessages;
import be.tpvision.smartcontrol.messages.mappers.device.device.ToDeviceSettingsViewModelMessages;
import be.tpvision.smartcontrol.messages.mappers.device.device.ToDeviceViewModelMessages;
import be.tpvision.smartcontrol.messages.mappers.device.device.ToGroupDeviceViewModelMessages;
import be.tpvision.smartcontrol.messages.mappers.device.device.ToImportExportDeviceViewModelMessages;
import be.tpvision.smartcontrol.rest.mappers.StringWrapperMapper;
import be.tpvision.smartcontrol.rest.mappers.audio.AudioParametersMapper;
import be.tpvision.smartcontrol.rest.mappers.audio.VolumeLimitsMapper;
import be.tpvision.smartcontrol.rest.mappers.audio.VolumeMapper;
import be.tpvision.smartcontrol.rest.mappers.device.FtpSettingsMapper;
import be.tpvision.smartcontrol.rest.mappers.device.IpDestinationMapper;
import be.tpvision.smartcontrol.rest.mappers.input_sources.FailoversMapper;
import be.tpvision.smartcontrol.rest.mappers.input_sources.InputSourceMapper;
import be.tpvision.smartcontrol.rest.mappers.miscellaneous.BootOnSourceMapper;
import be.tpvision.smartcontrol.rest.mappers.miscellaneous.MatrixPositionMapper;
import be.tpvision.smartcontrol.rest.mappers.miscellaneous.MiscellaneousMapper;
import be.tpvision.smartcontrol.rest.mappers.miscellaneous.PixelShiftMapper;
import be.tpvision.smartcontrol.rest.mappers.miscellaneous.TemperatureSensorMapper;
import be.tpvision.smartcontrol.rest.mappers.miscellaneous.TilingMapper;
import be.tpvision.smartcontrol.rest.mappers.scheduling.scheduling_parameters.SchedulingParametersMapper;
import be.tpvision.smartcontrol.rest.mappers.video.ColorParametersMapper;
import be.tpvision.smartcontrol.rest.mappers.video.PictureInPictureMapper;
import be.tpvision.smartcontrol.rest.mappers.video.PictureInPictureSourceMapper;
import be.tpvision.smartcontrol.rest.mappers.video.VGAVideoParametersMapper;
import be.tpvision.smartcontrol.rest.mappers.video.VideoParametersMapper;
import be.tpvision.smartcontrol.rest.view_models.audio.AudioParametersViewModel;
import be.tpvision.smartcontrol.rest.view_models.audio.VolumeLimitsViewModel;
import be.tpvision.smartcontrol.rest.view_models.audio.VolumeViewModel;
import be.tpvision.smartcontrol.rest.view_models.device.AddOrEditDeviceViewModel;
import be.tpvision.smartcontrol.rest.view_models.device.DetectDevicesViewModel;
import be.tpvision.smartcontrol.rest.view_models.device.DeviceInfoViewModel;
import be.tpvision.smartcontrol.rest.view_models.device.DeviceSettingsViewModel;
import be.tpvision.smartcontrol.rest.view_models.device.DeviceViewModel;
import be.tpvision.smartcontrol.rest.view_models.device.FtpSettingsViewModel;
import be.tpvision.smartcontrol.rest.view_models.device.GroupDeviceViewModel;
import be.tpvision.smartcontrol.rest.view_models.device.ImportExportDeviceViewModel;
import be.tpvision.smartcontrol.rest.view_models.device.IpDestinationViewModel;
import be.tpvision.smartcontrol.rest.view_models.device_limits.DeviceLimitsViewModel;
import be.tpvision.smartcontrol.rest.view_models.input_sources.InputSourceViewModel;
import be.tpvision.smartcontrol.rest.view_models.miscellaneous.BootOnSourceViewModel;
import be.tpvision.smartcontrol.rest.view_models.miscellaneous.MatrixPositionViewModel;
import be.tpvision.smartcontrol.rest.view_models.miscellaneous.MiscellaneousViewModel;
import be.tpvision.smartcontrol.rest.view_models.miscellaneous.PixelShiftViewModel;
import be.tpvision.smartcontrol.rest.view_models.miscellaneous.TilingViewModel;
import be.tpvision.smartcontrol.rest.view_models.scheduling.SchedulingParametersViewModel;
import be.tpvision.smartcontrol.rest.view_models.video.ColorParametersViewModel;
import be.tpvision.smartcontrol.rest.view_models.video.PictureInPictureSourceViewModel;
import be.tpvision.smartcontrol.rest.view_models.video.PictureInPictureViewModel;
import be.tpvision.smartcontrol.rest.view_models.video.VGAVideoParametersViewModel;
import be.tpvision.smartcontrol.rest.view_models.video.VideoParametersViewModel;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

public class DeviceMapper {
    private DeviceMapper() {
    }

    public static DeviceViewModel toDeviceViewModel(Device device) {
        Assert.notNull((Object)device, ToDeviceViewModelMessages.DEVICE_CAN_NOT_BE_NULL);
        Long deviceId = device.getId();
        IpDestination ipDestination = device.getAddress();
        Assert.state(ipDestination != null, ToDeviceViewModelMessages.IP_DESTINATION_CAN_NOT_BE_NULL);
        IpDestinationViewModel ipDestinationViewModel = IpDestinationMapper.toIpDestinationViewModel(ipDestination);
        String name = device.getName();
        StringWrapper domainModelNumber = device.getModelNumber();
        String viewModelModelNumber = StringWrapperMapper.toString(domainModelNumber);
        TemperatureSensor temperatureSensor = device.getTemperature();
        Integer temperature = temperatureSensor != null ? Integer.valueOf(TemperatureSensorMapper.toInteger(temperatureSensor)) : null;
        PowerState devicePowerState = device.getPowerState();
        String deviceViewModelPowerState = devicePowerState != null ? String.valueOf(devicePowerState) : null;
        InputSource inputSource = device.getInputSource();
        InputSourceViewModel inputSourceViewModel = inputSource != null ? InputSourceMapper.toInputSourceViewModel(inputSource) : null;
        DeviceViewModel deviceViewModel = new DeviceViewModel(deviceId, ipDestinationViewModel, name, viewModelModelNumber, temperature, deviceViewModelPowerState, inputSourceViewModel);
        StringWrapper supportSources = device.getSupportSources();
        if (supportSources != null && StringUtils.isNoneBlank(supportSources.getValue())) {
            List<String> supportSourceTypeNames = Arrays.asList(supportSources.getValue().split(","));
            List<String> allInputSourceType = deviceViewModel.getDeviceLimits().getInputSource().getSourceType().getValues();
            DeviceMapper.filterTargetSourceTypeNameList(allInputSourceType, supportSourceTypeNames);
        }
        return deviceViewModel;
    }

    public static DetectDevicesViewModel toDetectDevicesViewModel(Device device) {
        Assert.notNull((Object)device, ToDetectDevicesViewModelMessages.DEVICE_CAN_NOT_BE_NULL);
        IpDestination ipDestination = device.getAddress();
        Assert.state(ipDestination != null, ToDetectDevicesViewModelMessages.IP_DESTINATION_CAN_NOT_BE_NULL);
        IpDestinationViewModel ipDestinationViewModel = IpDestinationMapper.toIpDestinationViewModel(ipDestination);
        StringWrapper domainSicpVersion = device.getSicpVersion();
        String viewModelSicpVersion = StringWrapperMapper.toString(domainSicpVersion);
        StringWrapper domainSerialCode = device.getSerialCode();
        String viewModelSerialCode = StringWrapperMapper.toString(domainSerialCode);
        StringWrapper domainModelNumber = device.getModelNumber();
        String viewModelModelNumber = StringWrapperMapper.toString(domainModelNumber);
        return new DetectDevicesViewModel(ipDestinationViewModel, viewModelSicpVersion, viewModelSerialCode, viewModelModelNumber);
    }

    public static Device toDevice(DetectDevicesViewModel detectDevicesViewModel) {
        Assert.notNull((Object)detectDevicesViewModel, ToDeviceMessages.DETECT_DEVICES_VIEW_MODEL_CAN_NOT_BE_NULL);
        Device device = new Device();
        IpDestinationViewModel ipDestinationViewModel = detectDevicesViewModel.getIpDestination();
        IpDestination ipDestination = IpDestinationMapper.toIpDestination(ipDestinationViewModel);
        device.setAddress(ipDestination);
        String viewModelSicpVersion = detectDevicesViewModel.getSicpVersion();
        StringWrapper domainSicpVersion = StringWrapperMapper.toStringWrapper(viewModelSicpVersion);
        device.setSicpVersion(domainSicpVersion);
        String viewModelSerialCode = detectDevicesViewModel.getSerialCode();
        StringWrapper domainSerialCode = StringWrapperMapper.toStringWrapper(viewModelSerialCode);
        device.setSerialCode(domainSerialCode);
        String viewModelModelNumber = detectDevicesViewModel.getModelNumber();
        StringWrapper domainModelNumber = StringWrapperMapper.toStringWrapper(viewModelModelNumber);
        device.setModelNumber(domainModelNumber);
        return device;
    }

    public static AddOrEditDeviceViewModel toAddOrEditDeviceViewModel(Device device) {
        FtpSettings ftpSettings;
        Assert.notNull((Object)device, ToAddOrEditDeviceViewModelMessages.DEVICE_CAN_NOT_BE_NULL);
        Long id = device.getId();
        StringWrapper domainModelNumber = device.getModelNumber();
        String viewModelModelNumber = StringWrapperMapper.toString(domainModelNumber);
        IpDestination ipDestination = device.getAddress();
        Assert.state(ipDestination != null, ToAddOrEditDeviceViewModelMessages.IP_DESTINATION_CAN_NOT_BE_NULL);
        IpDestinationViewModel ipDestinationViewModel = IpDestinationMapper.toIpDestinationViewModel(ipDestination);
        String name = device.getName();
        if (name == null) {
            name = "";
        }
        FtpSettingsViewModel ftpSettingsViewModel = (ftpSettings = device.getFtpSettings()) != null ? FtpSettingsMapper.toFtpSettingsViewModel(ftpSettings) : null;
        boolean contentRotated = device.isContentRotated();
        return new AddOrEditDeviceViewModel(id, viewModelModelNumber, ipDestinationViewModel, name, ftpSettingsViewModel, contentRotated);
    }

    public static Device toDevice(AddOrEditDeviceViewModel addOrEditDeviceViewModel) {
        Assert.notNull((Object)addOrEditDeviceViewModel, ToDeviceMessages.ADD_OR_EDIT_DEVICE_VIEW_MODEL_CAN_NOT_BE_NULL);
        Device device = new Device();
        IpDestinationViewModel ipDestinationViewModel = addOrEditDeviceViewModel.getIpDestination();
        IpDestination ipDestination = IpDestinationMapper.toIpDestination(ipDestinationViewModel);
        device.setAddress(ipDestination);
        String name = addOrEditDeviceViewModel.getName();
        device.setName(name);
        FtpSettingsViewModel ftpSettingsViewModel = addOrEditDeviceViewModel.getFtpSettings();
        FtpSettings ftpSettings = ftpSettingsViewModel != null ? FtpSettingsMapper.toFtpSettings(ftpSettingsViewModel) : null;
        device.setFtpSettings(ftpSettings);
        boolean contentRotated = addOrEditDeviceViewModel.isContentRotated();
        device.setContentRotated(contentRotated);
        return device;
    }

    public static DeviceInfoViewModel toDeviceInfoViewModel(Device device) {
        Assert.notNull((Object)device, ToDeviceInfoViewModelMessages.DEVICE_CAN_NOT_BE_NULL);
        DeviceInfoViewModel deviceInfoViewModel = new DeviceInfoViewModel();
        Long id = device.getId();
        deviceInfoViewModel.setId(id);
        String name = device.getName();
        deviceInfoViewModel.setName(name);
        StringWrapper domainSicpVersion = device.getSicpVersion();
        String viewModelSicpVersion = StringWrapperMapper.toString(domainSicpVersion);
        deviceInfoViewModel.setSicpVersion(viewModelSicpVersion);
        StringWrapper domainPlatformLabel = device.getPlatformLabel();
        String viewModelPlatformLabel = StringWrapperMapper.toString(domainPlatformLabel);
        deviceInfoViewModel.setPlatformLabel(viewModelPlatformLabel);
        StringWrapper domainPlatformVersion = device.getPlatformVersion();
        String viewModelPlatformVersion = StringWrapperMapper.toString(domainPlatformVersion);
        deviceInfoViewModel.setPlatformVersion(viewModelPlatformVersion);
        StringWrapper domainModelNumber = device.getModelNumber();
        String viewModelModelNumber = StringWrapperMapper.toString(domainModelNumber);
        deviceInfoViewModel.setModelNumber(viewModelModelNumber);
        StringWrapper domainFirmwareVersion = device.getFirmwareVersion();
        String viewModelFirmwareVersion = StringWrapperMapper.toString(domainFirmwareVersion);
        deviceInfoViewModel.setFirmwareVersion(viewModelFirmwareVersion);
        StringWrapper domainBuildDate = device.getBuildDate();
        String viewModelBuildDate = StringWrapperMapper.toString(domainBuildDate);
        deviceInfoViewModel.setBuildDate(viewModelBuildDate);
        StringWrapper domainFirmwareVersionAndroid = device.getFirmwareVersionAndroid();
        String viewModelFirmwareVersionAndroid = StringWrapperMapper.toString(domainFirmwareVersionAndroid);
        deviceInfoViewModel.setFirmwareVersionAndroid(viewModelFirmwareVersionAndroid);
        StringWrapper domainSerialCode = device.getSerialCode();
        String viewModelSerialCode = StringWrapperMapper.toString(domainSerialCode);
        deviceInfoViewModel.setSerialCode(viewModelSerialCode);
        TemperatureSensor temperatureSensor = device.getTemperature();
        Integer temperature = temperatureSensor != null ? Integer.valueOf(TemperatureSensorMapper.toInteger(temperatureSensor)) : null;
        deviceInfoViewModel.setTemperatureSensor(temperature);
        Miscellaneous miscellaneous = device.getMiscellaneous();
        MiscellaneousViewModel miscellaneousViewModel = miscellaneous != null ? MiscellaneousMapper.toMiscellaneousViewModel(miscellaneous) : null;
        deviceInfoViewModel.setMiscellaneous(miscellaneousViewModel);
        return deviceInfoViewModel;
    }

    public static DeviceSettingsViewModel toDeviceSettingsViewModel(Device device) {
        StringWrapper supportSources;
        SchedulingParameters schedulingParameters;
        BootOnSource bootOnSource;
        NavigationBar navigationBar;
        VideoPresent videoPresent;
        PixelShift pixelShift;
        IntWrapper humanSensor;
        IntWrapper offTimer;
        Mute mute;
        PictureStyle pictureStyle;
        EcoMode ecoMode;
        LockUsb lockUsb;
        PowerSavingMode powerSavingMode;
        APM apm;
        FanSpeed fanSpeed;
        PowerOnLogo powerOnLogo;
        SwitchOnDelay switchOnDelay;
        ScanConversion scanConversion;
        ScanMode scanMode;
        NoiseReduction noiseReduction;
        Touch touch;
        MEMCEffect memcEffect;
        IntWrapper osdInformation;
        OSDRotating osdRotating;
        LightSensor lightSensor;
        IntWrapper frameCompensationVertical;
        IntWrapper frameCompensationHorizontal;
        Tiling tiling;
        SmartPower smartPower;
        AudioParameters audioParameters;
        VolumeLimits volumeLimitsAudioOut;
        VolumeLimits volumeLimitsSpeakerOut;
        Volume volume;
        PictureInPictureSource pictureInPictureSource;
        PictureInPicture pictureInPicture;
        VGAVideoParameters vgaVideoParameters;
        PictureFormat pictureFormat;
        ColorTemperature100K colorTemperature100K;
        ColorParameters colorParameters;
        ColorTemperature colorTemperature;
        VideoParameters videoParameters;
        Failovers failovers;
        AutoSignalDetecting autoSignalDetecting;
        InputSource inputSource;
        Backlight backlight;
        PowerStateAtColdStart powerStateAtColdStart;
        KeypadLockState keypadLockState;
        RemoteControlLockState remoteControlLockState;
        PowerState powerState;
        Assert.notNull((Object)device, ToDeviceSettingsViewModelMessages.DEVICE_CAN_NOT_BE_NULL);
        DeviceSettingsViewModel deviceSettingsViewModel = new DeviceSettingsViewModel();
        Long id = device.getId();
        deviceSettingsViewModel.setId(id);
        String name = device.getName();
        deviceSettingsViewModel.setName(name);
        StringWrapper domainModelNumber = device.getModelNumber();
        if (domainModelNumber != null) {
            String viewModelModelNumber = StringWrapperMapper.toString(domainModelNumber);
            deviceSettingsViewModel.setModelNumber(viewModelModelNumber);
        }
        if ((powerState = device.getPowerState()) != null) {
            String powerStateString = String.valueOf(powerState);
            deviceSettingsViewModel.setPowerState(powerStateString);
        }
        if ((remoteControlLockState = device.getRemoteControlLockState()) != null) {
            String remoteControlLockStateString = String.valueOf(remoteControlLockState);
            deviceSettingsViewModel.setRemoteControlLockState(remoteControlLockStateString);
        }
        if ((keypadLockState = device.getKeypadLockState()) != null) {
            String keypadLockStateString = String.valueOf(keypadLockState);
            deviceSettingsViewModel.setKeypadLockState(keypadLockStateString);
        }
        if ((powerStateAtColdStart = device.getPowerStateAtColdStart()) != null) {
            String powerStateAtColdStartString = String.valueOf(powerStateAtColdStart);
            deviceSettingsViewModel.setPowerStateAtColdStart(powerStateAtColdStartString);
        }
        if ((backlight = device.getBacklight()) != null) {
            String backlightString = String.valueOf(backlight);
            deviceSettingsViewModel.setBacklight(backlightString);
        }
        if ((inputSource = device.getInputSource()) != null) {
            InputSourceViewModel inputSourceViewModel = InputSourceMapper.toInputSourceViewModel(inputSource);
            deviceSettingsViewModel.setInputSource(inputSourceViewModel);
        }
        if ((autoSignalDetecting = device.getAutoSignalDetecting()) != null) {
            String autoSignalDetectingString = String.valueOf(autoSignalDetecting);
            deviceSettingsViewModel.setAutoSignalDetecting(autoSignalDetectingString);
        }
        if ((failovers = device.getFailovers()) != null) {
            List<String> failoverList = FailoversMapper.toStringList(failovers);
            deviceSettingsViewModel.setFailovers(failoverList);
        }
        if ((videoParameters = device.getVideoParameters()) != null) {
            VideoParametersViewModel videoParametersViewModel = VideoParametersMapper.toVideoParametersViewModel(videoParameters);
            deviceSettingsViewModel.setVideoParameters(videoParametersViewModel);
        }
        if ((colorTemperature = device.getColorTemperature()) != null) {
            String colorTemperatureString = String.valueOf(colorTemperature);
            deviceSettingsViewModel.setColorTemperature(colorTemperatureString);
        }
        if ((colorParameters = device.getColorParameters()) != null) {
            ColorParametersViewModel colorParametersViewModel = ColorParametersMapper.toColorParametersViewModel(colorParameters);
            deviceSettingsViewModel.setColorParameters(colorParametersViewModel);
        }
        if ((colorTemperature100K = device.getColorTemperature100K()) != null) {
            String colorTemperature100KString = String.valueOf(colorTemperature100K);
            deviceSettingsViewModel.setColorTemperature100K(colorTemperature100KString);
        }
        if ((pictureFormat = device.getPictureFormat()) != null) {
            String pictureFormatString = String.valueOf(pictureFormat);
            deviceSettingsViewModel.setPictureFormat(pictureFormatString);
        }
        if ((vgaVideoParameters = device.getVgaVideoParameters()) != null) {
            VGAVideoParametersViewModel vgaVideoParametersViewModel = VGAVideoParametersMapper.toVGAVideoParametersViewModel(vgaVideoParameters);
            deviceSettingsViewModel.setVgaVideoParameters(vgaVideoParametersViewModel);
        }
        if ((pictureInPicture = device.getPictureInPicture()) != null) {
            PictureInPictureViewModel pictureInPictureViewModel = PictureInPictureMapper.toPictureInPictureViewModel(pictureInPicture);
            deviceSettingsViewModel.setPictureInPicture(pictureInPictureViewModel);
        }
        if ((pictureInPictureSource = device.getPictureInPictureSource()) != null) {
            PictureInPictureSourceViewModel pictureInPictureSourceViewModel = PictureInPictureSourceMapper.toPictureInPictureSourceViewModel(pictureInPictureSource);
            deviceSettingsViewModel.setPictureInPictureSource(pictureInPictureSourceViewModel);
        }
        if ((volume = device.getVolume()) != null) {
            VolumeViewModel volumeViewModel = VolumeMapper.toVolumeViewModel(volume);
            deviceSettingsViewModel.setVolume(volumeViewModel);
        }
        if ((volumeLimitsSpeakerOut = device.getVolumeLimitsSpeakerOut()) != null) {
            VolumeLimitsViewModel volumeLimitsSpeakerOutViewModel = VolumeLimitsMapper.toVolumeLimitsViewModel(volumeLimitsSpeakerOut);
            deviceSettingsViewModel.setVolumeLimitsSpeakerOut(volumeLimitsSpeakerOutViewModel);
        }
        if ((volumeLimitsAudioOut = device.getVolumeLimitsAudioOut()) != null) {
            VolumeLimitsViewModel volumeLimitsAudioOutViewModel = VolumeLimitsMapper.toVolumeLimitsViewModel(volumeLimitsAudioOut);
            deviceSettingsViewModel.setVolumeLimitsAudioOut(volumeLimitsAudioOutViewModel);
        }
        if ((audioParameters = device.getAudioParameters()) != null) {
            AudioParametersViewModel audioParametersViewModel = AudioParametersMapper.toAudioParametersViewModel(audioParameters);
            deviceSettingsViewModel.setAudioParameters(audioParametersViewModel);
        }
        if ((smartPower = device.getSmartPower()) != null) {
            String smartPowerString = String.valueOf(smartPower);
            deviceSettingsViewModel.setSmartPower(smartPowerString);
        }
        if ((tiling = device.getTiling()) != null) {
            TilingViewModel tilingViewModel = TilingMapper.toTilingViewModel(tiling);
            deviceSettingsViewModel.setTiling(tilingViewModel);
        }
        if ((frameCompensationHorizontal = device.getFrameCompensationHorizontal()) != null) {
            Integer frameCompensationHorizontalInteger = frameCompensationHorizontal.getValue();
            deviceSettingsViewModel.setFrameCompensationHorizontal(frameCompensationHorizontalInteger);
        }
        if ((frameCompensationVertical = device.getFrameCompensationVertical()) != null) {
            Integer frameCompensationVerticalInteger = frameCompensationVertical.getValue();
            deviceSettingsViewModel.setFrameCompensationVertical(frameCompensationVerticalInteger);
        }
        if ((lightSensor = device.getLightSensor()) != null) {
            String lightSensorString = String.valueOf(lightSensor);
            deviceSettingsViewModel.setLightSensor(lightSensorString);
        }
        if ((osdRotating = device.getOsdRotating()) != null) {
            String osdRotatingString = String.valueOf(osdRotating);
            deviceSettingsViewModel.setOsdRotating(osdRotatingString);
        }
        if ((osdInformation = device.getOsdInformation()) != null) {
            int osdInformationInteger = osdInformation.getValue();
            deviceSettingsViewModel.setOsdInformation(osdInformationInteger);
        }
        if ((memcEffect = device.getMemcEffect()) != null) {
            String memcEffectString = String.valueOf(memcEffect);
            deviceSettingsViewModel.setMemcEffect(memcEffectString);
        }
        if ((touch = device.getTouch()) != null) {
            String touchString = String.valueOf(touch);
            deviceSettingsViewModel.setTouch(touchString);
        }
        if ((noiseReduction = device.getNoiseReduction()) != null) {
            String noiseReductionString = String.valueOf(noiseReduction);
            deviceSettingsViewModel.setNoiseReduction(noiseReductionString);
        }
        if ((scanMode = device.getScanMode()) != null) {
            String scanModeString = String.valueOf(scanMode);
            deviceSettingsViewModel.setScanMode(scanModeString);
        }
        if ((scanConversion = device.getScanConversion()) != null) {
            String scanConversionString = String.valueOf(scanConversion);
            deviceSettingsViewModel.setScanConversion(scanConversionString);
        }
        if ((switchOnDelay = device.getSwitchOnDelay()) != null) {
            String switchOnDelayString = String.valueOf(switchOnDelay);
            deviceSettingsViewModel.setSwitchOnDelay(switchOnDelayString);
        }
        if ((powerOnLogo = device.getPowerOnLogo()) != null) {
            String powerOnLogoString = String.valueOf(powerOnLogo);
            deviceSettingsViewModel.setPowerOnLogo(powerOnLogoString);
        }
        if ((fanSpeed = device.getFanSpeed()) != null) {
            String fanSpeedString = String.valueOf(fanSpeed);
            deviceSettingsViewModel.setFanSpeed(fanSpeedString);
        }
        if ((apm = device.getApm()) != null) {
            String apmString = String.valueOf(apm);
            deviceSettingsViewModel.setApm(apmString);
        }
        if ((powerSavingMode = device.getPowerSavingMode()) != null) {
            String powerSavingModeString = String.valueOf(powerSavingMode);
            deviceSettingsViewModel.setPowerSavingMode(powerSavingModeString);
        }
        if ((lockUsb = device.getLockUsb()) != null) {
            String lockUsbString = String.valueOf(lockUsb);
            deviceSettingsViewModel.setLockUsb(lockUsbString);
        }
        if ((ecoMode = device.getEcoMode()) != null) {
            String ecoModeString = String.valueOf(ecoMode);
            deviceSettingsViewModel.setEcoMode(ecoModeString);
        }
        if ((pictureStyle = device.getPictureStyle()) != null) {
            String pictureStyleString = String.valueOf(pictureStyle);
            deviceSettingsViewModel.setPictureStyle(pictureStyleString);
        }
        if ((mute = device.getMute()) != null) {
            String muteString = String.valueOf(mute);
            deviceSettingsViewModel.setMute(muteString);
        }
        if ((offTimer = device.getOffTimer()) != null) {
            int offTimerValue = offTimer.getValue();
            deviceSettingsViewModel.setOffTimer(offTimerValue);
        }
        if ((humanSensor = device.getHumanSensor()) != null) {
            int humanSensorValue = humanSensor.getValue();
            deviceSettingsViewModel.setHumanSensor(humanSensorValue);
        }
        if ((pixelShift = device.getPixelShift()) != null) {
            PixelShiftViewModel pixelShiftViewModel = PixelShiftMapper.toPixelShiftViewModel(pixelShift);
            deviceSettingsViewModel.setPixelShift(pixelShiftViewModel);
        }
        if ((videoPresent = device.getVideoPresent()) != null) {
            String videoPresentString = String.valueOf(videoPresent);
            deviceSettingsViewModel.setVideoPresent(videoPresentString);
        }
        if ((navigationBar = device.getNavigationBar()) != null) {
            String navigationBarString = String.valueOf(navigationBar);
            deviceSettingsViewModel.setNavigationBar(navigationBarString);
        }
        if ((bootOnSource = device.getBootOnSource()) != null) {
            BootOnSourceViewModel bootOnSourceViewModel = BootOnSourceMapper.toBootOnSourceViewModel(bootOnSource);
            deviceSettingsViewModel.setBootOnSource(bootOnSourceViewModel);
        }
        if ((schedulingParameters = device.getSchedulingParameters()) != null) {
            SchedulingParametersViewModel schedulingParametersViewModel = SchedulingParametersMapper.toSchedulingParametersViewModel(schedulingParameters);
            deviceSettingsViewModel.setSchedulingParameters(schedulingParametersViewModel);
        }
        if ((supportSources = device.getSupportSources()) != null && StringUtils.isNoneBlank(supportSources.getValue())) {
            List<String> supportSourceTypeNames = Arrays.asList(supportSources.getValue().split(","));
            DeviceLimitsViewModel deviceLimitsViewModel = deviceSettingsViewModel.getDeviceLimits();
            List<String> allInputSourceType = deviceLimitsViewModel.getInputSource().getSourceType().getValues();
            DeviceMapper.filterTargetSourceTypeNameList(allInputSourceType, supportSourceTypeNames);
            List<String> allBootOnSourceSourceType = deviceLimitsViewModel.getBootOnSource().getVideoSourceType().getValues();
            DeviceMapper.filterTargetSourceTypeNameList(allBootOnSourceSourceType, supportSourceTypeNames);
            List<String> allSchedulingSourceType = deviceLimitsViewModel.getSchedulingParameters().getPage().getSourceType().getValues();
            DeviceMapper.filterTargetSourceTypeNameList(allSchedulingSourceType, supportSourceTypeNames);
            List<String> allInputSourceSourceTypeQ2 = deviceLimitsViewModel.getPictureInPictureSource().getInputSourceSourceTypeQ2().getValues();
            DeviceMapper.filterTargetSourceTypeNameList(allInputSourceSourceTypeQ2, supportSourceTypeNames);
            List<String> allInputSourceSourceTypeQ3 = deviceLimitsViewModel.getPictureInPictureSource().getInputSourceSourceTypeQ3().getValues();
            DeviceMapper.filterTargetSourceTypeNameList(allInputSourceSourceTypeQ3, supportSourceTypeNames);
            List<String> allInputSourceSourceTypeQ4 = deviceLimitsViewModel.getPictureInPictureSource().getInputSourceSourceTypeQ4().getValues();
            DeviceMapper.filterTargetSourceTypeNameList(allInputSourceSourceTypeQ4, supportSourceTypeNames);
            List<String> allFailOverSourceType = deviceLimitsViewModel.getFailover().getValues();
            DeviceMapper.filterTargetSourceTypeNameList(allFailOverSourceType, supportSourceTypeNames);
        }
        return deviceSettingsViewModel;
    }

    private static void filterTargetSourceTypeNameList(List<String> allSourceTypeList, List<String> supportedSourceList) {
        Iterator<String> iterator = allSourceTypeList.iterator();
        while (iterator.hasNext()) {
            String checkedType = iterator.next();
            if (supportedSourceList.contains(checkedType)) continue;
            iterator.remove();
        }
    }

    public static GroupDeviceViewModel toGroupDeviceViewModel(Device device) {
        Assert.notNull((Object)device, ToGroupDeviceViewModelMessages.DEVICE_CAN_NOT_BE_NULL);
        Long id = device.getId();
        IpDestination ipDestination = device.getAddress();
        Assert.state(ipDestination != null, ToGroupDeviceViewModelMessages.IP_DESTINATION_CAN_NOT_BE_NULL);
        IpDestinationViewModel ipDestinationViewModel = IpDestinationMapper.toIpDestinationViewModel(ipDestination);
        String name = device.getName();
        StringWrapper domainModelNumber = device.getModelNumber();
        String viewModelModelNumber = StringWrapperMapper.toString(domainModelNumber);
        MatrixPosition matrixPosition = device.getMatrixPosition();
        MatrixPositionViewModel matrixPositionViewModel = matrixPosition != null ? MatrixPositionMapper.toMatrixPositionViewModel(matrixPosition) : null;
        return new GroupDeviceViewModel(id, ipDestinationViewModel, name, viewModelModelNumber, matrixPositionViewModel);
    }

    public static Device toDevice(GroupDeviceViewModel groupDeviceViewModel) {
        Assert.notNull((Object)groupDeviceViewModel, ToDeviceMessages.GROUP_DEVICE_VIEW_MODEL_CAN_NOT_BE_NULL);
        Device device = new Device();
        Long id = groupDeviceViewModel.getId();
        device.setId(id);
        IpDestinationViewModel ipDestinationViewModel = groupDeviceViewModel.getIpDestination();
        IpDestination ipDestination = IpDestinationMapper.toIpDestination(ipDestinationViewModel);
        device.setAddress(ipDestination);
        String name = groupDeviceViewModel.getName();
        device.setName(name);
        String viewModelModelNumber = groupDeviceViewModel.getModelNumber();
        StringWrapper domainModelNumber = StringWrapperMapper.toStringWrapper(viewModelModelNumber);
        device.setModelNumber(domainModelNumber);
        MatrixPositionViewModel matrixPositionViewModel = groupDeviceViewModel.getMatrixPosition();
        MatrixPosition matrixPosition = matrixPositionViewModel != null ? MatrixPositionMapper.toMatrixPosition(matrixPositionViewModel) : null;
        device.setMatrixPosition(matrixPosition);
        return device;
    }

    public static ImportExportDeviceViewModel toImportExportDeviceViewModel(Device device) {
        Assert.notNull((Object)device, ToImportExportDeviceViewModelMessages.DEVICE_CAN_NOT_BE_NULL);
        IpDestination ipDestination = device.getAddress();
        Assert.state(ipDestination != null, ToImportExportDeviceViewModelMessages.IP_DESTINATION_CAN_NOT_BE_NULL);
        IpDestinationViewModel ipDestinationViewModel = IpDestinationMapper.toIpDestinationViewModel(ipDestination);
        StringWrapper domainSicpVersion = device.getSicpVersion();
        String viewModelSicpVersion = StringWrapperMapper.toString(domainSicpVersion);
        StringWrapper domainSerialCode = device.getSerialCode();
        String viewModelSerialCode = StringWrapperMapper.toString(domainSerialCode);
        StringWrapper domainModelNumber = device.getModelNumber();
        String viewModelModelNumber = StringWrapperMapper.toString(domainModelNumber);
        String name = device.getName();
        return new ImportExportDeviceViewModel(ipDestinationViewModel, viewModelSicpVersion, viewModelSerialCode, viewModelModelNumber, name);
    }

    public static Device toDevice(ImportExportDeviceViewModel importExportDeviceViewModel) {
        Assert.notNull((Object)importExportDeviceViewModel, ToDeviceMessages.IMPORT_EXPORT_VIEW_MODEL_CAN_NOT_BE_NULL);
        Device device = new Device();
        IpDestinationViewModel ipDestinationViewModel = importExportDeviceViewModel.getIpDestination();
        IpDestination ipDestination = IpDestinationMapper.toIpDestination(ipDestinationViewModel);
        device.setAddress(ipDestination);
        String viewModelSicpVersion = importExportDeviceViewModel.getSicpVersion();
        StringWrapper domainSicpVersion = StringWrapperMapper.toStringWrapper(viewModelSicpVersion);
        device.setSicpVersion(domainSicpVersion);
        String viewModelSerialCode = importExportDeviceViewModel.getSerialCode();
        StringWrapper domainSerialCode = StringWrapperMapper.toStringWrapper(viewModelSerialCode);
        device.setSerialCode(domainSerialCode);
        String viewModelModelNumber = importExportDeviceViewModel.getModelNumber();
        StringWrapper domainModelNumber = StringWrapperMapper.toStringWrapper(viewModelModelNumber);
        device.setModelNumber(domainModelNumber);
        String name = importExportDeviceViewModel.getName();
        device.setName(name);
        return device;
    }
}

