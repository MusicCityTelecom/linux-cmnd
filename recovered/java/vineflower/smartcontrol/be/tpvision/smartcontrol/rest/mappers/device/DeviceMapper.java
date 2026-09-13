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

   public static DeviceViewModel toDeviceViewModel(final Device device) {
      Assert.notNull(device, ToDeviceViewModelMessages.DEVICE_CAN_NOT_BE_NULL);
      Long deviceId = device.getId();
      IpDestination ipDestination = device.getAddress();
      Assert.state(ipDestination != null, ToDeviceViewModelMessages.IP_DESTINATION_CAN_NOT_BE_NULL);
      IpDestinationViewModel ipDestinationViewModel = IpDestinationMapper.toIpDestinationViewModel(ipDestination);
      String name = device.getName();
      StringWrapper domainModelNumber = device.getModelNumber();
      String viewModelModelNumber = StringWrapperMapper.toString(domainModelNumber);
      TemperatureSensor temperatureSensor = device.getTemperature();
      Integer temperature;
      if (temperatureSensor != null) {
         temperature = TemperatureSensorMapper.toInteger(temperatureSensor);
      } else {
         temperature = null;
      }

      PowerState devicePowerState = device.getPowerState();
      String deviceViewModelPowerState;
      if (devicePowerState != null) {
         deviceViewModelPowerState = String.valueOf(devicePowerState);
      } else {
         deviceViewModelPowerState = null;
      }

      InputSource inputSource = device.getInputSource();
      InputSourceViewModel inputSourceViewModel;
      if (inputSource != null) {
         inputSourceViewModel = InputSourceMapper.toInputSourceViewModel(inputSource);
      } else {
         inputSourceViewModel = null;
      }

      DeviceViewModel deviceViewModel = new DeviceViewModel(
         deviceId, ipDestinationViewModel, name, viewModelModelNumber, temperature, deviceViewModelPowerState, inputSourceViewModel
      );
      StringWrapper supportSources = device.getSupportSources();
      if (supportSources != null && StringUtils.isNoneBlank(supportSources.getValue())) {
         List<String> supportSourceTypeNames = Arrays.asList(supportSources.getValue().split(","));
         List<String> allInputSourceType = deviceViewModel.getDeviceLimits().getInputSource().getSourceType().getValues();
         filterTargetSourceTypeNameList(allInputSourceType, supportSourceTypeNames);
      }

      return deviceViewModel;
   }

   public static DetectDevicesViewModel toDetectDevicesViewModel(final Device device) {
      Assert.notNull(device, ToDetectDevicesViewModelMessages.DEVICE_CAN_NOT_BE_NULL);
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

   public static Device toDevice(final DetectDevicesViewModel detectDevicesViewModel) {
      Assert.notNull(detectDevicesViewModel, ToDeviceMessages.DETECT_DEVICES_VIEW_MODEL_CAN_NOT_BE_NULL);
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

   public static AddOrEditDeviceViewModel toAddOrEditDeviceViewModel(final Device device) {
      Assert.notNull(device, ToAddOrEditDeviceViewModelMessages.DEVICE_CAN_NOT_BE_NULL);
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

      FtpSettings ftpSettings = device.getFtpSettings();
      FtpSettingsViewModel ftpSettingsViewModel;
      if (ftpSettings != null) {
         ftpSettingsViewModel = FtpSettingsMapper.toFtpSettingsViewModel(ftpSettings);
      } else {
         ftpSettingsViewModel = null;
      }

      boolean contentRotated = device.isContentRotated();
      return new AddOrEditDeviceViewModel(id, viewModelModelNumber, ipDestinationViewModel, name, ftpSettingsViewModel, contentRotated);
   }

   public static Device toDevice(final AddOrEditDeviceViewModel addOrEditDeviceViewModel) {
      Assert.notNull(addOrEditDeviceViewModel, ToDeviceMessages.ADD_OR_EDIT_DEVICE_VIEW_MODEL_CAN_NOT_BE_NULL);
      Device device = new Device();
      IpDestinationViewModel ipDestinationViewModel = addOrEditDeviceViewModel.getIpDestination();
      IpDestination ipDestination = IpDestinationMapper.toIpDestination(ipDestinationViewModel);
      device.setAddress(ipDestination);
      String name = addOrEditDeviceViewModel.getName();
      device.setName(name);
      FtpSettingsViewModel ftpSettingsViewModel = addOrEditDeviceViewModel.getFtpSettings();
      FtpSettings ftpSettings;
      if (ftpSettingsViewModel != null) {
         ftpSettings = FtpSettingsMapper.toFtpSettings(ftpSettingsViewModel);
      } else {
         ftpSettings = null;
      }

      device.setFtpSettings(ftpSettings);
      boolean contentRotated = addOrEditDeviceViewModel.isContentRotated();
      device.setContentRotated(contentRotated);
      return device;
   }

   public static DeviceInfoViewModel toDeviceInfoViewModel(final Device device) {
      Assert.notNull(device, ToDeviceInfoViewModelMessages.DEVICE_CAN_NOT_BE_NULL);
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
      Integer temperature;
      if (temperatureSensor != null) {
         temperature = TemperatureSensorMapper.toInteger(temperatureSensor);
      } else {
         temperature = null;
      }

      deviceInfoViewModel.setTemperatureSensor(temperature);
      Miscellaneous miscellaneous = device.getMiscellaneous();
      MiscellaneousViewModel miscellaneousViewModel;
      if (miscellaneous != null) {
         miscellaneousViewModel = MiscellaneousMapper.toMiscellaneousViewModel(miscellaneous);
      } else {
         miscellaneousViewModel = null;
      }

      deviceInfoViewModel.setMiscellaneous(miscellaneousViewModel);
      return deviceInfoViewModel;
   }

   public static DeviceSettingsViewModel toDeviceSettingsViewModel(final Device device) {
      Assert.notNull(device, ToDeviceSettingsViewModelMessages.DEVICE_CAN_NOT_BE_NULL);
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

      PowerState powerState = device.getPowerState();
      if (powerState != null) {
         String powerStateString = String.valueOf(powerState);
         deviceSettingsViewModel.setPowerState(powerStateString);
      }

      RemoteControlLockState remoteControlLockState = device.getRemoteControlLockState();
      if (remoteControlLockState != null) {
         String remoteControlLockStateString = String.valueOf(remoteControlLockState);
         deviceSettingsViewModel.setRemoteControlLockState(remoteControlLockStateString);
      }

      KeypadLockState keypadLockState = device.getKeypadLockState();
      if (keypadLockState != null) {
         String keypadLockStateString = String.valueOf(keypadLockState);
         deviceSettingsViewModel.setKeypadLockState(keypadLockStateString);
      }

      PowerStateAtColdStart powerStateAtColdStart = device.getPowerStateAtColdStart();
      if (powerStateAtColdStart != null) {
         String powerStateAtColdStartString = String.valueOf(powerStateAtColdStart);
         deviceSettingsViewModel.setPowerStateAtColdStart(powerStateAtColdStartString);
      }

      Backlight backlight = device.getBacklight();
      if (backlight != null) {
         String backlightString = String.valueOf(backlight);
         deviceSettingsViewModel.setBacklight(backlightString);
      }

      InputSource inputSource = device.getInputSource();
      if (inputSource != null) {
         InputSourceViewModel inputSourceViewModel = InputSourceMapper.toInputSourceViewModel(inputSource);
         deviceSettingsViewModel.setInputSource(inputSourceViewModel);
      }

      AutoSignalDetecting autoSignalDetecting = device.getAutoSignalDetecting();
      if (autoSignalDetecting != null) {
         String autoSignalDetectingString = String.valueOf(autoSignalDetecting);
         deviceSettingsViewModel.setAutoSignalDetecting(autoSignalDetectingString);
      }

      Failovers failovers = device.getFailovers();
      if (failovers != null) {
         List<String> failoverList = FailoversMapper.toStringList(failovers);
         deviceSettingsViewModel.setFailovers(failoverList);
      }

      VideoParameters videoParameters = device.getVideoParameters();
      if (videoParameters != null) {
         VideoParametersViewModel videoParametersViewModel = VideoParametersMapper.toVideoParametersViewModel(videoParameters);
         deviceSettingsViewModel.setVideoParameters(videoParametersViewModel);
      }

      ColorTemperature colorTemperature = device.getColorTemperature();
      if (colorTemperature != null) {
         String colorTemperatureString = String.valueOf(colorTemperature);
         deviceSettingsViewModel.setColorTemperature(colorTemperatureString);
      }

      ColorParameters colorParameters = device.getColorParameters();
      if (colorParameters != null) {
         ColorParametersViewModel colorParametersViewModel = ColorParametersMapper.toColorParametersViewModel(colorParameters);
         deviceSettingsViewModel.setColorParameters(colorParametersViewModel);
      }

      ColorTemperature100K colorTemperature100K = device.getColorTemperature100K();
      if (colorTemperature100K != null) {
         String colorTemperature100KString = String.valueOf(colorTemperature100K);
         deviceSettingsViewModel.setColorTemperature100K(colorTemperature100KString);
      }

      PictureFormat pictureFormat = device.getPictureFormat();
      if (pictureFormat != null) {
         String pictureFormatString = String.valueOf(pictureFormat);
         deviceSettingsViewModel.setPictureFormat(pictureFormatString);
      }

      VGAVideoParameters vgaVideoParameters = device.getVgaVideoParameters();
      if (vgaVideoParameters != null) {
         VGAVideoParametersViewModel vgaVideoParametersViewModel = VGAVideoParametersMapper.toVGAVideoParametersViewModel(vgaVideoParameters);
         deviceSettingsViewModel.setVgaVideoParameters(vgaVideoParametersViewModel);
      }

      PictureInPicture pictureInPicture = device.getPictureInPicture();
      if (pictureInPicture != null) {
         PictureInPictureViewModel pictureInPictureViewModel = PictureInPictureMapper.toPictureInPictureViewModel(pictureInPicture);
         deviceSettingsViewModel.setPictureInPicture(pictureInPictureViewModel);
      }

      PictureInPictureSource pictureInPictureSource = device.getPictureInPictureSource();
      if (pictureInPictureSource != null) {
         PictureInPictureSourceViewModel pictureInPictureSourceViewModel = PictureInPictureSourceMapper.toPictureInPictureSourceViewModel(
            pictureInPictureSource
         );
         deviceSettingsViewModel.setPictureInPictureSource(pictureInPictureSourceViewModel);
      }

      Volume volume = device.getVolume();
      if (volume != null) {
         VolumeViewModel volumeViewModel = VolumeMapper.toVolumeViewModel(volume);
         deviceSettingsViewModel.setVolume(volumeViewModel);
      }

      VolumeLimits volumeLimitsSpeakerOut = device.getVolumeLimitsSpeakerOut();
      if (volumeLimitsSpeakerOut != null) {
         VolumeLimitsViewModel volumeLimitsSpeakerOutViewModel = VolumeLimitsMapper.toVolumeLimitsViewModel(volumeLimitsSpeakerOut);
         deviceSettingsViewModel.setVolumeLimitsSpeakerOut(volumeLimitsSpeakerOutViewModel);
      }

      VolumeLimits volumeLimitsAudioOut = device.getVolumeLimitsAudioOut();
      if (volumeLimitsAudioOut != null) {
         VolumeLimitsViewModel volumeLimitsAudioOutViewModel = VolumeLimitsMapper.toVolumeLimitsViewModel(volumeLimitsAudioOut);
         deviceSettingsViewModel.setVolumeLimitsAudioOut(volumeLimitsAudioOutViewModel);
      }

      AudioParameters audioParameters = device.getAudioParameters();
      if (audioParameters != null) {
         AudioParametersViewModel audioParametersViewModel = AudioParametersMapper.toAudioParametersViewModel(audioParameters);
         deviceSettingsViewModel.setAudioParameters(audioParametersViewModel);
      }

      SmartPower smartPower = device.getSmartPower();
      if (smartPower != null) {
         String smartPowerString = String.valueOf(smartPower);
         deviceSettingsViewModel.setSmartPower(smartPowerString);
      }

      Tiling tiling = device.getTiling();
      if (tiling != null) {
         TilingViewModel tilingViewModel = TilingMapper.toTilingViewModel(tiling);
         deviceSettingsViewModel.setTiling(tilingViewModel);
      }

      IntWrapper frameCompensationHorizontal = device.getFrameCompensationHorizontal();
      if (frameCompensationHorizontal != null) {
         Integer frameCompensationHorizontalInteger = frameCompensationHorizontal.getValue();
         deviceSettingsViewModel.setFrameCompensationHorizontal(frameCompensationHorizontalInteger);
      }

      IntWrapper frameCompensationVertical = device.getFrameCompensationVertical();
      if (frameCompensationVertical != null) {
         Integer frameCompensationVerticalInteger = frameCompensationVertical.getValue();
         deviceSettingsViewModel.setFrameCompensationVertical(frameCompensationVerticalInteger);
      }

      LightSensor lightSensor = device.getLightSensor();
      if (lightSensor != null) {
         String lightSensorString = String.valueOf(lightSensor);
         deviceSettingsViewModel.setLightSensor(lightSensorString);
      }

      OSDRotating osdRotating = device.getOsdRotating();
      if (osdRotating != null) {
         String osdRotatingString = String.valueOf(osdRotating);
         deviceSettingsViewModel.setOsdRotating(osdRotatingString);
      }

      IntWrapper osdInformation = device.getOsdInformation();
      if (osdInformation != null) {
         int osdInformationInteger = osdInformation.getValue();
         deviceSettingsViewModel.setOsdInformation(osdInformationInteger);
      }

      MEMCEffect memcEffect = device.getMemcEffect();
      if (memcEffect != null) {
         String memcEffectString = String.valueOf(memcEffect);
         deviceSettingsViewModel.setMemcEffect(memcEffectString);
      }

      Touch touch = device.getTouch();
      if (touch != null) {
         String touchString = String.valueOf(touch);
         deviceSettingsViewModel.setTouch(touchString);
      }

      NoiseReduction noiseReduction = device.getNoiseReduction();
      if (noiseReduction != null) {
         String noiseReductionString = String.valueOf(noiseReduction);
         deviceSettingsViewModel.setNoiseReduction(noiseReductionString);
      }

      ScanMode scanMode = device.getScanMode();
      if (scanMode != null) {
         String scanModeString = String.valueOf(scanMode);
         deviceSettingsViewModel.setScanMode(scanModeString);
      }

      ScanConversion scanConversion = device.getScanConversion();
      if (scanConversion != null) {
         String scanConversionString = String.valueOf(scanConversion);
         deviceSettingsViewModel.setScanConversion(scanConversionString);
      }

      SwitchOnDelay switchOnDelay = device.getSwitchOnDelay();
      if (switchOnDelay != null) {
         String switchOnDelayString = String.valueOf(switchOnDelay);
         deviceSettingsViewModel.setSwitchOnDelay(switchOnDelayString);
      }

      PowerOnLogo powerOnLogo = device.getPowerOnLogo();
      if (powerOnLogo != null) {
         String powerOnLogoString = String.valueOf(powerOnLogo);
         deviceSettingsViewModel.setPowerOnLogo(powerOnLogoString);
      }

      FanSpeed fanSpeed = device.getFanSpeed();
      if (fanSpeed != null) {
         String fanSpeedString = String.valueOf(fanSpeed);
         deviceSettingsViewModel.setFanSpeed(fanSpeedString);
      }

      APM apm = device.getApm();
      if (apm != null) {
         String apmString = String.valueOf(apm);
         deviceSettingsViewModel.setApm(apmString);
      }

      PowerSavingMode powerSavingMode = device.getPowerSavingMode();
      if (powerSavingMode != null) {
         String powerSavingModeString = String.valueOf(powerSavingMode);
         deviceSettingsViewModel.setPowerSavingMode(powerSavingModeString);
      }

      LockUsb lockUsb = device.getLockUsb();
      if (lockUsb != null) {
         String lockUsbString = String.valueOf(lockUsb);
         deviceSettingsViewModel.setLockUsb(lockUsbString);
      }

      EcoMode ecoMode = device.getEcoMode();
      if (ecoMode != null) {
         String ecoModeString = String.valueOf(ecoMode);
         deviceSettingsViewModel.setEcoMode(ecoModeString);
      }

      PictureStyle pictureStyle = device.getPictureStyle();
      if (pictureStyle != null) {
         String pictureStyleString = String.valueOf(pictureStyle);
         deviceSettingsViewModel.setPictureStyle(pictureStyleString);
      }

      Mute mute = device.getMute();
      if (mute != null) {
         String muteString = String.valueOf(mute);
         deviceSettingsViewModel.setMute(muteString);
      }

      IntWrapper offTimer = device.getOffTimer();
      if (offTimer != null) {
         int offTimerValue = offTimer.getValue();
         deviceSettingsViewModel.setOffTimer(offTimerValue);
      }

      IntWrapper humanSensor = device.getHumanSensor();
      if (humanSensor != null) {
         int humanSensorValue = humanSensor.getValue();
         deviceSettingsViewModel.setHumanSensor(humanSensorValue);
      }

      PixelShift pixelShift = device.getPixelShift();
      if (pixelShift != null) {
         PixelShiftViewModel pixelShiftViewModel = PixelShiftMapper.toPixelShiftViewModel(pixelShift);
         deviceSettingsViewModel.setPixelShift(pixelShiftViewModel);
      }

      VideoPresent videoPresent = device.getVideoPresent();
      if (videoPresent != null) {
         String videoPresentString = String.valueOf(videoPresent);
         deviceSettingsViewModel.setVideoPresent(videoPresentString);
      }

      NavigationBar navigationBar = device.getNavigationBar();
      if (navigationBar != null) {
         String navigationBarString = String.valueOf(navigationBar);
         deviceSettingsViewModel.setNavigationBar(navigationBarString);
      }

      BootOnSource bootOnSource = device.getBootOnSource();
      if (bootOnSource != null) {
         BootOnSourceViewModel bootOnSourceViewModel = BootOnSourceMapper.toBootOnSourceViewModel(bootOnSource);
         deviceSettingsViewModel.setBootOnSource(bootOnSourceViewModel);
      }

      SchedulingParameters schedulingParameters = device.getSchedulingParameters();
      if (schedulingParameters != null) {
         SchedulingParametersViewModel schedulingParametersViewModel = SchedulingParametersMapper.toSchedulingParametersViewModel(schedulingParameters);
         deviceSettingsViewModel.setSchedulingParameters(schedulingParametersViewModel);
      }

      StringWrapper supportSources = device.getSupportSources();
      if (supportSources != null && StringUtils.isNoneBlank(supportSources.getValue())) {
         List<String> supportSourceTypeNames = Arrays.asList(supportSources.getValue().split(","));
         DeviceLimitsViewModel deviceLimitsViewModel = deviceSettingsViewModel.getDeviceLimits();
         List<String> allInputSourceType = deviceLimitsViewModel.getInputSource().getSourceType().getValues();
         filterTargetSourceTypeNameList(allInputSourceType, supportSourceTypeNames);
         List<String> allBootOnSourceSourceType = deviceLimitsViewModel.getBootOnSource().getVideoSourceType().getValues();
         filterTargetSourceTypeNameList(allBootOnSourceSourceType, supportSourceTypeNames);
         List<String> allSchedulingSourceType = deviceLimitsViewModel.getSchedulingParameters().getPage().getSourceType().getValues();
         filterTargetSourceTypeNameList(allSchedulingSourceType, supportSourceTypeNames);
         List<String> allInputSourceSourceTypeQ2 = deviceLimitsViewModel.getPictureInPictureSource().getInputSourceSourceTypeQ2().getValues();
         filterTargetSourceTypeNameList(allInputSourceSourceTypeQ2, supportSourceTypeNames);
         List<String> allInputSourceSourceTypeQ3 = deviceLimitsViewModel.getPictureInPictureSource().getInputSourceSourceTypeQ3().getValues();
         filterTargetSourceTypeNameList(allInputSourceSourceTypeQ3, supportSourceTypeNames);
         List<String> allInputSourceSourceTypeQ4 = deviceLimitsViewModel.getPictureInPictureSource().getInputSourceSourceTypeQ4().getValues();
         filterTargetSourceTypeNameList(allInputSourceSourceTypeQ4, supportSourceTypeNames);
         List<String> allFailOverSourceType = deviceLimitsViewModel.getFailover().getValues();
         filterTargetSourceTypeNameList(allFailOverSourceType, supportSourceTypeNames);
      }

      return deviceSettingsViewModel;
   }

   private static void filterTargetSourceTypeNameList(List<String> allSourceTypeList, List<String> supportedSourceList) {
      Iterator<String> iterator = allSourceTypeList.iterator();

      while (iterator.hasNext()) {
         String checkedType = iterator.next();
         if (!supportedSourceList.contains(checkedType)) {
            iterator.remove();
         }
      }
   }

   public static GroupDeviceViewModel toGroupDeviceViewModel(final Device device) {
      Assert.notNull(device, ToGroupDeviceViewModelMessages.DEVICE_CAN_NOT_BE_NULL);
      Long id = device.getId();
      IpDestination ipDestination = device.getAddress();
      Assert.state(ipDestination != null, ToGroupDeviceViewModelMessages.IP_DESTINATION_CAN_NOT_BE_NULL);
      IpDestinationViewModel ipDestinationViewModel = IpDestinationMapper.toIpDestinationViewModel(ipDestination);
      String name = device.getName();
      StringWrapper domainModelNumber = device.getModelNumber();
      String viewModelModelNumber = StringWrapperMapper.toString(domainModelNumber);
      MatrixPosition matrixPosition = device.getMatrixPosition();
      MatrixPositionViewModel matrixPositionViewModel;
      if (matrixPosition != null) {
         matrixPositionViewModel = MatrixPositionMapper.toMatrixPositionViewModel(matrixPosition);
      } else {
         matrixPositionViewModel = null;
      }

      return new GroupDeviceViewModel(id, ipDestinationViewModel, name, viewModelModelNumber, matrixPositionViewModel);
   }

   public static Device toDevice(final GroupDeviceViewModel groupDeviceViewModel) {
      Assert.notNull(groupDeviceViewModel, ToDeviceMessages.GROUP_DEVICE_VIEW_MODEL_CAN_NOT_BE_NULL);
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
      MatrixPosition matrixPosition;
      if (matrixPositionViewModel != null) {
         matrixPosition = MatrixPositionMapper.toMatrixPosition(matrixPositionViewModel);
      } else {
         matrixPosition = null;
      }

      device.setMatrixPosition(matrixPosition);
      return device;
   }

   public static ImportExportDeviceViewModel toImportExportDeviceViewModel(final Device device) {
      Assert.notNull(device, ToImportExportDeviceViewModelMessages.DEVICE_CAN_NOT_BE_NULL);
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

   public static Device toDevice(final ImportExportDeviceViewModel importExportDeviceViewModel) {
      Assert.notNull(importExportDeviceViewModel, ToDeviceMessages.IMPORT_EXPORT_VIEW_MODEL_CAN_NOT_BE_NULL);
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
