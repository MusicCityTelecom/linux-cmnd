package be.tpvision.smartcontrol.rest.controllers;

import be.tpvision.smartcontrol.domain.Content;
import be.tpvision.smartcontrol.domain.Device;
import be.tpvision.smartcontrol.domain.DeviceListItem;
import be.tpvision.smartcontrol.domain.FtpSettings;
import be.tpvision.smartcontrol.domain.Group;
import be.tpvision.smartcontrol.domain.Hardware;
import be.tpvision.smartcontrol.domain.MatrixPosition;
import be.tpvision.smartcontrol.domain.Settings;
import be.tpvision.smartcontrol.domain.device_settings.IntWrapper;
import be.tpvision.smartcontrol.domain.device_settings.StringWrapper;
import be.tpvision.smartcontrol.domain.device_settings.audio.AudioParameters;
import be.tpvision.smartcontrol.domain.device_settings.audio.AudioSync;
import be.tpvision.smartcontrol.domain.device_settings.audio.Mute;
import be.tpvision.smartcontrol.domain.device_settings.audio.SpeakersStatus;
import be.tpvision.smartcontrol.domain.device_settings.audio.Volume;
import be.tpvision.smartcontrol.domain.device_settings.audio.VolumeLimits;
import be.tpvision.smartcontrol.domain.device_settings.audio.VolumeUpDown;
import be.tpvision.smartcontrol.domain.device_settings.general.AutoRestartParameter;
import be.tpvision.smartcontrol.domain.device_settings.general.AutoTimeSync;
import be.tpvision.smartcontrol.domain.device_settings.general.Backlight;
import be.tpvision.smartcontrol.domain.device_settings.general.BootOnSource;
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
import be.tpvision.smartcontrol.domain.device_settings.general.WOL;
import be.tpvision.smartcontrol.domain.device_settings.input_sources.AutoSignalDetecting;
import be.tpvision.smartcontrol.domain.device_settings.input_sources.Failovers;
import be.tpvision.smartcontrol.domain.device_settings.input_sources.InputSource;
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
import be.tpvision.smartcontrol.domain.device_settings.scheduling.scheduling_parameters.Page;
import be.tpvision.smartcontrol.domain.device_settings.video.ColorParameters;
import be.tpvision.smartcontrol.domain.device_settings.video.ColorTemperature;
import be.tpvision.smartcontrol.domain.device_settings.video.ColorTemperature100K;
import be.tpvision.smartcontrol.domain.device_settings.video.PictureFormat;
import be.tpvision.smartcontrol.domain.device_settings.video.PictureInPicture;
import be.tpvision.smartcontrol.domain.device_settings.video.PictureInPictureSource;
import be.tpvision.smartcontrol.domain.device_settings.video.PictureStyle;
import be.tpvision.smartcontrol.domain.device_settings.video.VGAVideoParameters;
import be.tpvision.smartcontrol.domain.device_settings.video.VideoParameters;
import be.tpvision.smartcontrol.io.CommandNotAcknowledgedException;
import be.tpvision.smartcontrol.io.CommandNotAvailableException;
import be.tpvision.smartcontrol.io.ip.IpDestination;
import be.tpvision.smartcontrol.io.ip.sicp.DestinationUnreachableException;
import be.tpvision.smartcontrol.messages.controllers.device.DeleteDeviceMessages;
import be.tpvision.smartcontrol.messages.controllers.device.DownloadContentMessages;
import be.tpvision.smartcontrol.messages.controllers.device.ExportDevicesMessages;
import be.tpvision.smartcontrol.messages.controllers.device.GetDeviceInfoMessages;
import be.tpvision.smartcontrol.messages.controllers.device.SetContentMessages;
import be.tpvision.smartcontrol.messages.controllers.device.SetDefaultFtpSettingsMessages;
import be.tpvision.smartcontrol.messages.controllers.device.SetDeviceContentRotatedMessages;
import be.tpvision.smartcontrol.messages.controllers.device.UnassignContentMessages;
import be.tpvision.smartcontrol.repository.OrderDirection;
import be.tpvision.smartcontrol.repository.data_transfer_objects.DeviceDTO;
import be.tpvision.smartcontrol.repository.order_fields.device.OrderField;
import be.tpvision.smartcontrol.rest.ResponseWrapper;
import be.tpvision.smartcontrol.rest.mappers.StringWrapperMapper;
import be.tpvision.smartcontrol.rest.mappers.audio.AudioParametersMapper;
import be.tpvision.smartcontrol.rest.mappers.audio.VolumeLimitsMapper;
import be.tpvision.smartcontrol.rest.mappers.audio.VolumeMapper;
import be.tpvision.smartcontrol.rest.mappers.audio.VolumeUpDownMapper;
import be.tpvision.smartcontrol.rest.mappers.device.DeviceMapper;
import be.tpvision.smartcontrol.rest.mappers.group.GroupMapper;
import be.tpvision.smartcontrol.rest.mappers.group.GroupResultMapper;
import be.tpvision.smartcontrol.rest.mappers.input_sources.FailoversMapper;
import be.tpvision.smartcontrol.rest.mappers.input_sources.InputSourceMapper;
import be.tpvision.smartcontrol.rest.mappers.miscellaneous.AutoRestartParameterMapper;
import be.tpvision.smartcontrol.rest.mappers.miscellaneous.BootOnSourceMapper;
import be.tpvision.smartcontrol.rest.mappers.miscellaneous.ClockParameterMapper;
import be.tpvision.smartcontrol.rest.mappers.miscellaneous.DateParameterMapper;
import be.tpvision.smartcontrol.rest.mappers.miscellaneous.DisplayOrientationMapper;
import be.tpvision.smartcontrol.rest.mappers.miscellaneous.LedStripsMapper;
import be.tpvision.smartcontrol.rest.mappers.miscellaneous.MatrixPositionMapper;
import be.tpvision.smartcontrol.rest.mappers.miscellaneous.MiscellaneousMapper;
import be.tpvision.smartcontrol.rest.mappers.miscellaneous.OtaUpdateSetMapper;
import be.tpvision.smartcontrol.rest.mappers.miscellaneous.PixelShiftMapper;
import be.tpvision.smartcontrol.rest.mappers.miscellaneous.TemperatureSensorMapper;
import be.tpvision.smartcontrol.rest.mappers.miscellaneous.TilingMapper;
import be.tpvision.smartcontrol.rest.mappers.scheduling.scheduling_parameters.PageMapper;
import be.tpvision.smartcontrol.rest.mappers.video.ColorParametersMapper;
import be.tpvision.smartcontrol.rest.mappers.video.PictureInPictureMapper;
import be.tpvision.smartcontrol.rest.mappers.video.PictureInPictureSourceMapper;
import be.tpvision.smartcontrol.rest.mappers.video.VGAVideoParametersMapper;
import be.tpvision.smartcontrol.rest.mappers.video.VideoAlignmentMapper;
import be.tpvision.smartcontrol.rest.mappers.video.VideoParametersMapper;
import be.tpvision.smartcontrol.rest.view_models.DeviceListItemViewModel;
import be.tpvision.smartcontrol.rest.view_models.DeviceListItemViewModelList;
import be.tpvision.smartcontrol.rest.view_models.ExportDevicesViewModel;
import be.tpvision.smartcontrol.rest.view_models.ImportDeviceResultViewModel;
import be.tpvision.smartcontrol.rest.view_models.ImportDeviceResultViewModelList;
import be.tpvision.smartcontrol.rest.view_models.ImportExportItemListViewModel;
import be.tpvision.smartcontrol.rest.view_models.ImportGroupResultViewModel;
import be.tpvision.smartcontrol.rest.view_models.ImportResultViewModelList;
import be.tpvision.smartcontrol.rest.view_models.audio.AudioParametersViewModel;
import be.tpvision.smartcontrol.rest.view_models.audio.VolumeLimitsViewModel;
import be.tpvision.smartcontrol.rest.view_models.audio.VolumeUpDownViewModel;
import be.tpvision.smartcontrol.rest.view_models.audio.VolumeViewModel;
import be.tpvision.smartcontrol.rest.view_models.device.AddOrEditDeviceViewModel;
import be.tpvision.smartcontrol.rest.view_models.device.DetectDevicesViewModel;
import be.tpvision.smartcontrol.rest.view_models.device.DeviceInfoViewModel;
import be.tpvision.smartcontrol.rest.view_models.device.DeviceSettingsViewModel;
import be.tpvision.smartcontrol.rest.view_models.device.DeviceViewModel;
import be.tpvision.smartcontrol.rest.view_models.device.FtpSettingsViewModel;
import be.tpvision.smartcontrol.rest.view_models.device.ImportExportDeviceViewModel;
import be.tpvision.smartcontrol.rest.view_models.device.IpDestinationViewModel;
import be.tpvision.smartcontrol.rest.view_models.device.UpdateDeviceFtpSettingsPasswordViewModel;
import be.tpvision.smartcontrol.rest.view_models.group.DetectGroupsViewModel;
import be.tpvision.smartcontrol.rest.view_models.group.ImportExportGroupViewModel;
import be.tpvision.smartcontrol.rest.view_models.input_sources.InputSourceViewModel;
import be.tpvision.smartcontrol.rest.view_models.miscellaneous.AutoRestartParameterViewModel;
import be.tpvision.smartcontrol.rest.view_models.miscellaneous.BootOnSourceViewModel;
import be.tpvision.smartcontrol.rest.view_models.miscellaneous.DisplayOrientationViewModel;
import be.tpvision.smartcontrol.rest.view_models.miscellaneous.LedStripsViewModel;
import be.tpvision.smartcontrol.rest.view_models.miscellaneous.MatrixPositionViewModel;
import be.tpvision.smartcontrol.rest.view_models.miscellaneous.MiscellaneousViewModel;
import be.tpvision.smartcontrol.rest.view_models.miscellaneous.OtaUpdateSetViewModel;
import be.tpvision.smartcontrol.rest.view_models.miscellaneous.PixelShiftViewModel;
import be.tpvision.smartcontrol.rest.view_models.miscellaneous.TilingViewModel;
import be.tpvision.smartcontrol.rest.view_models.scheduling.PageViewModel;
import be.tpvision.smartcontrol.rest.view_models.video.ColorParametersViewModel;
import be.tpvision.smartcontrol.rest.view_models.video.PictureInPictureSourceViewModel;
import be.tpvision.smartcontrol.rest.view_models.video.PictureInPictureViewModel;
import be.tpvision.smartcontrol.rest.view_models.video.VGAVideoParametersViewModel;
import be.tpvision.smartcontrol.rest.view_models.video.VideoAlignmentViewModel;
import be.tpvision.smartcontrol.rest.view_models.video.VideoParametersViewModel;
import be.tpvision.smartcontrol.service.AudioService;
import be.tpvision.smartcontrol.service.ContentManagementService;
import be.tpvision.smartcontrol.service.ContentService;
import be.tpvision.smartcontrol.service.DeviceService;
import be.tpvision.smartcontrol.service.DeviceServiceJdbc;
import be.tpvision.smartcontrol.service.GeneralService;
import be.tpvision.smartcontrol.service.GroupService;
import be.tpvision.smartcontrol.service.HardwareService;
import be.tpvision.smartcontrol.service.HardwareServiceJdbc;
import be.tpvision.smartcontrol.service.InputSourcesService;
import be.tpvision.smartcontrol.service.MiscellaneousService;
import be.tpvision.smartcontrol.service.SchedulingService;
import be.tpvision.smartcontrol.service.SettingsService;
import be.tpvision.smartcontrol.service.SystemService;
import be.tpvision.smartcontrol.service.VideoService;
import be.tpvision.smartcontrol.service.exceptions.AddDeviceResult;
import be.tpvision.smartcontrol.service.exceptions.AddGroupResult;
import be.tpvision.smartcontrol.service.exceptions.DeviceAlreadyExistsException;
import be.tpvision.smartcontrol.service.exceptions.FailedToAddGroupException;
import be.tpvision.smartcontrol.service.exceptions.SerialCodeAlreadyExistsException;
import be.tpvision.smartcontrol.util.DownloadUtilities;
import be.tpvision.smartcontrol.util.ValueUtilities;
import com.google.common.base.CaseFormat;
import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {
   private static final Logger logger = LoggerFactory.getLogger(DeviceController.class);
   @Autowired
   private DeviceService deviceService;
   @Autowired
   private AudioService audioService;
   @Autowired
   private GeneralService generalService;
   @Autowired
   private InputSourcesService inputSourcesService;
   @Autowired
   private MiscellaneousService miscellaneousService;
   @Autowired
   private SchedulingService schedulingService;
   @Autowired
   private SystemService systemService;
   @Autowired
   private VideoService videoService;
   @Autowired
   private GroupService groupService;
   @Autowired
   private HardwareService hardwareService;
   @Autowired
   private ContentService contentService;
   @Autowired
   private ContentManagementService contentManagementService;
   @Autowired
   private SettingsService settingsService;
   @Autowired
   private DeviceServiceJdbc deviceServiceJdbc;
   @Autowired
   private HardwareServiceJdbc hardwareServiceJdbc;
   private Path exportsPath;

   @Autowired
   public void setExportsPath(@Value("${smartcontrol.system.exports-path}") final String exportsPath) {
      this.exportsPath = Paths.get(exportsPath);
   }

   @ExceptionHandler(DestinationUnreachableException.class)
   @ResponseStatus(value = HttpStatus.GATEWAY_TIMEOUT, reason = "Destination unreachable.")
   private void destinationUnreachableExceptionHandler() {
   }

   @ExceptionHandler(CommandNotAcknowledgedException.class)
   @ResponseStatus(value = HttpStatus.GATEWAY_TIMEOUT, reason = "Command not acknowledged.")
   private void commandNotAcknowledgedExceptionHandler() {
   }

   @ExceptionHandler(CommandNotAvailableException.class)
   @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Command not available.")
   private void commandNotAvailableExceptionHandler() {
   }

   @GetMapping
   public List<DeviceViewModel> getDevicesOrderedBy(
      @RequestParam(value = "orderBy", required = false) final String orderBy,
      @RequestParam(value = "orderDirection", required = false) final String orderDirection
   ) {
      boolean hasOrderBy = StringUtils.hasText(orderBy);
      OrderField orderField;
      if (hasOrderBy) {
         String enumInput = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, orderBy);
         orderField = Enum.valueOf(OrderField.class, enumInput);
      } else {
         orderField = OrderField.ID;
      }

      boolean hasOrderDirection = StringUtils.hasText(orderDirection);
      OrderDirection orderDirectionEnum;
      if (hasOrderDirection) {
         orderDirectionEnum = ValueUtilities.getEnumValue(OrderDirection.class, orderDirection);
      } else {
         orderDirectionEnum = OrderDirection.ASC;
      }

      Set<Device> deviceSet = this.deviceService.getDevicesOrderedBy(orderField, orderDirectionEnum);
      return deviceSet.stream().map(DeviceMapper::toDeviceViewModel).filter(Objects::nonNull).collect(Collectors.toList());
   }

   @PostMapping
   public void addDevice(@RequestBody final AddOrEditDeviceViewModel addOrEditDeviceViewModel) {
      Device device = DeviceMapper.toDevice(addOrEditDeviceViewModel);
      FtpSettingsViewModel ftpSettingsViewModel = addOrEditDeviceViewModel.getFtpSettings();
      if (ftpSettingsViewModel == null) {
         this.deviceService.addDevice(device);
      } else {
         boolean useDefaultFtpSettings = ftpSettingsViewModel.isUseDefault();
         if (useDefaultFtpSettings) {
            FtpSettings ftpSettings = new FtpSettings(true);
            device.setFtpSettings(ftpSettings);
            this.deviceService.addDevice(device);
         } else {
            this.deviceService.addDevice(device, true);
         }
      }
   }

   @PutMapping("/{deviceId}")
   public void updateDevice(@PathVariable("deviceId") final long deviceId, @RequestBody final AddOrEditDeviceViewModel addOrEditDeviceViewModel) {
      Device device = DeviceMapper.toDevice(addOrEditDeviceViewModel);
      device.setId(deviceId);
      this.deviceService.updateDevice(device, true);
   }

   @DeleteMapping("/{deviceId}")
   public void deleteDevice(@PathVariable("deviceId") final long deviceId) {
      Assert.state(this.deviceService != null, DeleteDeviceMessages.DEVICE_SERVICE_CAN_NOT_BE_NULL);
      Device device = this.getDevice(deviceId);
      StringWrapper serialCode = device.getSerialCode();
      this.deviceService.deleteDevice(deviceId);
      if (serialCode != null) {
         Assert.state(this.contentManagementService != null, DeleteDeviceMessages.CONTENT_MANAGEMENT_SERVICE_CAN_NOT_BE_NULL);
         Assert.state(this.hardwareService != null, DeleteDeviceMessages.HARDWARE_SERVICE_CAN_NOT_BE_NULL);
         String hardwareKey = serialCode.getValue();
         Assert.state(hardwareKey != null, DeleteDeviceMessages.HARDWARE_KEY_CAN_NOT_BE_NULL);
         this.contentManagementService.deleteHardwarePath(hardwareKey);
         this.hardwareService.deleteHardware(hardwareKey);
      }
   }

   @GetMapping("/detect")
   public DeviceListItemViewModelList detectDevices() {
      DeviceListItemViewModelList deviceListItemViewModels = new DeviceListItemViewModelList();

      for (DeviceListItem deviceListItem : this.deviceService.detectDevices()) {
         if (deviceListItem instanceof Device) {
            Device device = (Device)deviceListItem;
            DetectDevicesViewModel detectDevicesViewModel = DeviceMapper.toDetectDevicesViewModel(device);
            deviceListItemViewModels.add(detectDevicesViewModel);
         } else if (deviceListItem instanceof Group) {
            Group group = (Group)deviceListItem;
            DetectGroupsViewModel detectGroupsViewModel = GroupMapper.toDetectGroupsViewModel(group);
            deviceListItemViewModels.add(detectGroupsViewModel);
         }
      }

      return deviceListItemViewModels;
   }

   private void setDefaultFtpSettings(final Device device) {
      Assert.state(this.settingsService != null, SetDefaultFtpSettingsMessages.SETTINGS_SERVICE_CAN_NOT_BE_NULL);
      Assert.notNull(device, SetDefaultFtpSettingsMessages.DEVICE_CAN_NOT_BE_NULL);
      Settings settings = this.settingsService.getSettings();
      Assert.state(settings != null, SetDefaultFtpSettingsMessages.SETTINGS_CAN_NOT_BE_NULL);
      FtpSettings defaultFtpSettings = settings.getDefaultFtpSettings();
      Assert.state(defaultFtpSettings != null, SetDefaultFtpSettingsMessages.DEFAULT_FTP_SETTINGS_CAN_NOT_BE_NULL);
      device.setFtpSettings(defaultFtpSettings);
   }

   private void setSupportSourcesForDevices(final Device device) {
      StringWrapper supportSources = device.getSupportSources();
      if (supportSources == null || !StringUtils.hasText(supportSources.getValue())) {
         try {
            StringWrapper numberOfInputSources = this.generalService.getNumberOfInputSources(device);
            if (numberOfInputSources != null) {
               device.setSupportSources(numberOfInputSources);
            }
         } catch (Exception ex) {
            logger.error("failed to update support source type", ex);
         }
      }
   }

   @PostMapping("/detect/import")
   public void importDetectedDevices(@RequestBody final List<DeviceListItemViewModel> deviceListItemViewModels) {
      for (DeviceListItemViewModel deviceListItemViewModel : deviceListItemViewModels) {
         if (deviceListItemViewModel instanceof DetectDevicesViewModel) {
            DetectDevicesViewModel detectDevicesViewModel = (DetectDevicesViewModel)deviceListItemViewModel;
            Device device = DeviceMapper.toDevice(detectDevicesViewModel);
            this.setDefaultFtpSettings(device);
            this.setSupportSourcesForDevices(device);
            this.deviceService.addDevice(device);
         } else if (deviceListItemViewModel instanceof DetectGroupsViewModel) {
            DetectGroupsViewModel detectGroupsViewModel = (DetectGroupsViewModel)deviceListItemViewModel;
            Group group = GroupMapper.toGroup(detectGroupsViewModel);
            Set<Device> deviceSet = group.getDevices();
            deviceSet.forEach(this::setDefaultFtpSettings);
            deviceSet.forEach(this::setSupportSourcesForDevices);
            this.groupService.addGroup(group);
         }
      }
   }

   @PostMapping("/import")
   public ImportResultViewModelList importDevices(@RequestBody final List<ImportExportItemListViewModel> importExportItemListViewModelList) {
      List<ImportExportGroupViewModel> importExportGroupViewModelList = importExportItemListViewModelList.stream()
         .filter(ImportExportGroupViewModel.class::isInstance)
         .map(ImportExportGroupViewModel.class::cast)
         .collect(Collectors.toList());
      List<ImportGroupResultViewModel> importGroupResultViewModelList = new ArrayList<>();

      for (ImportExportGroupViewModel importExportGroupViewModel : importExportGroupViewModelList) {
         AddGroupResult addGroupResult;
         try {
            Group group = GroupMapper.toGroup(importExportGroupViewModel);
            addGroupResult = this.groupService.addGroup(group);
         } catch (FailedToAddGroupException e) {
            addGroupResult = e.getAddGroupResult();
         }

         ImportGroupResultViewModel importGroupResultViewModel = GroupResultMapper.toImportGroupResultViewModel(addGroupResult);
         importGroupResultViewModelList.add(importGroupResultViewModel);
      }

      ImportDeviceResultViewModelList successfullyImportedGroupDevices = importGroupResultViewModelList.stream().filter(importGroupResultViewModel -> {
         String viewModelResult = importGroupResultViewModel.getResult();
         AddGroupResult.Result domainResult = AddGroupResult.Result.SUCCESS;
         String domainResultName = domainResult.getName();
         return viewModelResult.equals(domainResultName);
      }).map(ImportGroupResultViewModel::getImportDeviceResultViewModelList).flatMap(Collection::stream).filter(importDeviceResultViewModelx -> {
         String viewModelResult = importDeviceResultViewModelx.getResult();
         AddDeviceResult.Result domainResult = AddDeviceResult.Result.SUCCESS;
         String domainResultName = domainResult.getName();
         return viewModelResult.equals(domainResultName);
      }).collect(Collectors.toCollection(ImportDeviceResultViewModelList::new));
      List<ImportExportDeviceViewModel> importExportDeviceViewModelList = importExportItemListViewModelList.stream()
         .filter(ImportExportDeviceViewModel.class::isInstance)
         .map(ImportExportDeviceViewModel.class::cast)
         .collect(Collectors.toList());
      ImportDeviceResultViewModelList importDeviceResultViewModelList = new ImportDeviceResultViewModelList();

      for (ImportExportDeviceViewModel importExportDeviceViewModel : importExportDeviceViewModelList) {
         Optional<ImportDeviceResultViewModel> importDeviceResultViewModelOptional = successfullyImportedGroupDevices.stream()
            .filter(importDeviceResultViewModelx -> {
               ImportExportDeviceViewModel importExportDeviceViewModelToCheck = importDeviceResultViewModelx.getImportExportDeviceViewModel();
               return importExportDeviceViewModelToCheck.equals(importExportDeviceViewModel);
            })
            .findFirst();
         ImportDeviceResultViewModel importDeviceResultViewModel;
         if (importDeviceResultViewModelOptional.isPresent()) {
            importDeviceResultViewModel = importDeviceResultViewModelOptional.get();
         } else {
            Device device = DeviceMapper.toDevice(importExportDeviceViewModel);
            importDeviceResultViewModel = new ImportDeviceResultViewModel();
            importDeviceResultViewModel.setImportExportDeviceViewModel(importExportDeviceViewModel);

            try {
               this.deviceService.addDevice(device);
               importDeviceResultViewModel.setResult("Success");
               importDeviceResultViewModel.setMessage("Device imported successfully.");
            } catch (SerialCodeAlreadyExistsException | DeviceAlreadyExistsException e) {
               importDeviceResultViewModel.setResult("Failed");
               String message = e.getMessage();
               importDeviceResultViewModel.setMessage(message);
            }
         }

         importDeviceResultViewModelList.add(importDeviceResultViewModel);
      }

      ImportResultViewModelList importResultViewModelList = new ImportResultViewModelList();
      importResultViewModelList.addAll(importDeviceResultViewModelList);
      importResultViewModelList.addAll(importGroupResultViewModelList);
      return importResultViewModelList;
   }

   @PostMapping("/export")
   public void exportDevices(@RequestBody final ExportDevicesViewModel exportDevicesViewModel) {
      Assert.notNull(exportDevicesViewModel, ExportDevicesMessages.EXPORT_DEVICES_VIEW_MODEL_CAN_NOT_BE_NULL);
      Set<ImportExportItemListViewModel> importExportItemListViewModelSet = new LinkedHashSet<>();
      List<Long> deviceIdList = exportDevicesViewModel.getDevices();
      if (deviceIdList != null) {
         deviceIdList.stream()
            .filter(Objects::nonNull)
            .map(this.deviceService::getDevice)
            .map(DeviceMapper::toImportExportDeviceViewModel)
            .forEach(importExportItemListViewModelSet::add);
      }

      Set<ImportExportGroupViewModel> importExportGroupViewModelSet = new LinkedHashSet<>();
      List<Long> groupIdList = exportDevicesViewModel.getGroups();
      if (groupIdList != null) {
         groupIdList.stream()
            .filter(Objects::nonNull)
            .map(this.groupService::getGroup)
            .map(GroupMapper::toImportExportGroupViewModel)
            .forEach(importExportGroupViewModel -> {
               List<ImportExportDeviceViewModel> devices = importExportGroupViewModel.getDevices();
               importExportItemListViewModelSet.addAll(devices);
               importExportGroupViewModelSet.add(importExportGroupViewModel);
            });
      }

      importExportItemListViewModelSet.addAll(importExportGroupViewModelSet);
      GensonBuilder gensonBuilder = new GensonBuilder();
      Genson genson = gensonBuilder.useIndentation(true)
         .useClassMetadata(true)
         .addAlias("device", ImportExportDeviceViewModel.class)
         .addAlias("group", ImportExportGroupViewModel.class)
         .addAlias("ipDestination", IpDestinationViewModel.class)
         .create();
      String json = genson.serialize(importExportItemListViewModelSet);
      Path devicesPath = this.exportsPath.resolve("devices");

      try {
         Files.createDirectories(devicesPath);
         DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH.mm.ss.n");
         ZonedDateTime zonedDateTime = ZonedDateTime.now();
         String dateTime = dateTimeFormatter.format(zonedDateTime);
         String fileName = dateTime + ".json";
         Path jsonPath = devicesPath.resolve(fileName);
         byte[] jsonBytes = json.getBytes();
         Files.write(jsonPath, jsonBytes);
      } catch (IOException e) {
         String message = e.getMessage();
         logger.error(message, e);
      }
   }

   private Device getDevice(final long deviceId) {
      Device device = this.deviceService.getDevice(deviceId);
      String deviceNotFoundMessage = String.format("Device with id %s not found.", deviceId);
      Assert.state(device != null, deviceNotFoundMessage);
      return device;
   }

   @GetMapping("/{deviceId}/info")
   public DeviceInfoViewModel getDeviceInfo(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      DeviceInfoViewModel deviceInfoViewModel = DeviceMapper.toDeviceInfoViewModel(device);
      StringWrapper serialCodeWrapper = device.getSerialCode();
      String contentTitle = "None";
      if (serialCodeWrapper != null) {
         String serialCode = StringWrapperMapper.toString(serialCodeWrapper);
         Assert.state(serialCode != null, GetDeviceInfoMessages.SERIAL_CODE_CAN_NOT_BE_NULL);
         Assert.state(this.hardwareService != null, GetDeviceInfoMessages.HARDWARE_SERVICE_CAN_NOT_BE_NULL);
         Hardware hardware = this.hardwareService.getHardware(serialCode);
         if (hardware != null) {
            String contentId = hardware.getContentId();
            if (contentId != null) {
               Assert.state(this.contentService != null, GetDeviceInfoMessages.CONTENT_SERVICE_CAN_NOT_BE_NULL);
               Content content = this.contentService.getContent(contentId);
               if (content != null) {
                  contentTitle = content.getTitle();
               }
            }
         }
      }

      deviceInfoViewModel.setContentTitle(contentTitle);
      return deviceInfoViewModel;
   }

   @GetMapping("/{deviceId}/settings")
   public DeviceSettingsViewModel getDeviceSettings(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      return DeviceMapper.toDeviceSettingsViewModel(device);
   }

   @GetMapping("/{deviceId}/edit")
   public AddOrEditDeviceViewModel getDeviceEdit(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      return DeviceMapper.toAddOrEditDeviceViewModel(device);
   }

   @PutMapping("/{deviceId}/edit/ip/{ip}")
   public void setDeviceIp(@PathVariable("deviceId") final long deviceId, @PathVariable("ip") final String ip) {
      Device device = this.getDevice(deviceId);
      IpDestination ipDestination = device.getAddress();
      InetSocketAddress inetSocketAddress = ipDestination.getAddress();
      int port = inetSocketAddress.getPort();
      inetSocketAddress = new InetSocketAddress(ip, port);
      ipDestination.setAddress(inetSocketAddress);
      this.deviceService.updateDevice(device);
   }

   @PutMapping("/{deviceId}/edit/port/{port}")
   public void setDevicePort(@PathVariable("deviceId") final long deviceId, @PathVariable("port") final int port) {
      Device device = this.getDevice(deviceId);
      IpDestination ipDestination = device.getAddress();
      InetSocketAddress inetSocketAddress = ipDestination.getAddress();
      InetAddress inetAddress = inetSocketAddress.getAddress();
      String ip = inetAddress.getHostAddress();
      inetSocketAddress = new InetSocketAddress(ip, port);
      ipDestination.setAddress(inetSocketAddress);
      this.deviceService.updateDevice(device);
   }

   @PutMapping("/{deviceId}/edit/controlId/{controlId}")
   public void setDeviceControlId(@PathVariable("deviceId") final long deviceId, @PathVariable("controlId") final int controlId) {
      Device device = this.getDevice(deviceId);
      IpDestination ipDestination = device.getAddress();
      ipDestination.setControlId(controlId);
      this.deviceService.updateDevice(device);
   }

   @PutMapping("/{deviceId}/edit/groupId/{groupId}")
   public void setDeviceGroupId(@PathVariable("deviceId") final long deviceId, @PathVariable("groupId") final int groupId) {
      Device device = this.getDevice(deviceId);
      IpDestination ipDestination = device.getAddress();
      ipDestination.setGroupId(groupId);
      this.deviceService.updateDevice(device);
   }

   @PutMapping("/{deviceId}/edit/name")
   public void setDeviceName(@PathVariable("deviceId") final long deviceId) {
      this.setDeviceName(deviceId, null);
   }

   @PutMapping("/{deviceId}/edit/name/{name}")
   public void setDeviceName(@PathVariable("deviceId") final long deviceId, @PathVariable("name") final String name) {
      Device device = this.getDevice(deviceId);
      device.setName(name);
      this.deviceService.updateDevice(device);
   }

   @PutMapping("/{deviceId}/edit/ftpSettings/port")
   public void setDeviceFtpSettingsPort(@PathVariable("deviceId") final long deviceId) {
      this.setDeviceFtpSettingsPort(deviceId, null);
   }

   @PutMapping("/{deviceId}/edit/ftpSettings/port/{port}")
   public void setDeviceFtpSettingsPort(@PathVariable("deviceId") final long deviceId, @PathVariable("port") final Integer port) {
      Device device = this.getDevice(deviceId);
      FtpSettings ftpSettings = device.getFtpSettings();
      if (ftpSettings == null) {
         ftpSettings = new FtpSettings(false);
         device.setFtpSettings(ftpSettings);
      }

      ftpSettings.setPort(port);
      this.deviceService.updateDevice(device);
   }

   @PutMapping("/{deviceId}/edit/ftpSettings/useDefault/{useDefault}")
   public void setDeviceFtpSettingsUseDefault(@PathVariable("deviceId") final long deviceId, @PathVariable("useDefault") final boolean useDefault) {
      Device device = this.getDevice(deviceId);
      FtpSettings ftpSettings = device.getFtpSettings();
      if (ftpSettings == null) {
         ftpSettings = new FtpSettings(useDefault);
         device.setFtpSettings(ftpSettings);
      }

      ftpSettings.setUseDefault(useDefault);
      this.deviceService.updateDevice(device);
   }

   @PutMapping("/{deviceId}/edit/ftpSettings/username")
   public void setDeviceFtpSettingsUsername(@PathVariable("deviceId") final long deviceId) {
      this.setDeviceFtpSettingsUsername(deviceId, null);
   }

   @PutMapping("/{deviceId}/edit/ftpSettings/username/{username}")
   public void setDeviceFtpSettingsUsername(@PathVariable("deviceId") final long deviceId, @PathVariable("username") final String username) {
      Device device = this.getDevice(deviceId);
      FtpSettings ftpSettings = device.getFtpSettings();
      if (ftpSettings == null) {
         ftpSettings = new FtpSettings(false);
         device.setFtpSettings(ftpSettings);
      }

      ftpSettings.setUsername(username);
      this.deviceService.updateDevice(device);
   }

   @PutMapping("/{deviceId}/edit/ftpSettings/password")
   public void setDeviceFtpSettingsPassword(
      @PathVariable("deviceId") final long deviceId, @RequestBody final UpdateDeviceFtpSettingsPasswordViewModel updateDeviceFtpSettingsPasswordViewModel
   ) {
      Device device = this.getDevice(deviceId);
      String password = updateDeviceFtpSettingsPasswordViewModel.getPassword();
      boolean isPasswordEmpty = "".equals(password);
      FtpSettings ftpSettings = device.getFtpSettings();
      if (ftpSettings == null) {
         ftpSettings = new FtpSettings(false);
         device.setFtpSettings(ftpSettings);
      }

      if (isPasswordEmpty) {
         ftpSettings.setPassword(null);
         this.deviceService.updateDevice(device);
      } else {
         ftpSettings.setPassword(password);
         this.deviceService.updateDevice(device, true);
      }
   }

   @PutMapping("/{deviceId}/edit/contentRotated/{contentRotated}")
   public void setDeviceContentRotated(@PathVariable("deviceId") final long deviceId, @PathVariable("contentRotated") final boolean contentRotated) {
      Assert.isTrue(deviceId >= 0L, SetDeviceContentRotatedMessages.DEVICE_ID_HAS_TO_BE_A_POSITIVE_NUMBER);
      Device device = this.getDevice(deviceId);
      device.setContentRotated(contentRotated);
      this.deviceService.updateDevice(device);
   }

   @GetMapping("/{deviceId}/system/sicpVersion")
   public ResponseWrapper<String> getSicpVersion(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      StringWrapper sicpVersion = this.systemService.getSICPVersion(device);
      if (sicpVersion == null) {
         return new ResponseWrapper<>(null);
      }

      device.setSicpVersion(sicpVersion);
      this.deviceService.updateDevice(device);
      String sicpVersionValue = sicpVersion.getValue();
      return new ResponseWrapper<>(sicpVersionValue);
   }

   @GetMapping("/{deviceId}/system/platformLabel")
   public ResponseWrapper<String> getPlatformLabel(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      StringWrapper platformLabel = this.systemService.getPlatformLabel(device);
      if (platformLabel == null) {
         return new ResponseWrapper<>(null);
      }

      device.setPlatformLabel(platformLabel);
      this.deviceService.updateDevice(device);
      String platformLabelValue = platformLabel.getValue();
      return new ResponseWrapper<>(platformLabelValue);
   }

   @GetMapping("/{deviceId}/system/platformVersion")
   public ResponseWrapper<String> getPlatformVersion(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      StringWrapper platformVersion = this.systemService.getPlatformVersion(device);
      if (platformVersion == null) {
         return new ResponseWrapper<>(null);
      }

      device.setPlatformVersion(platformVersion);
      this.deviceService.updateDevice(device);
      String platformVersionValue = platformVersion.getValue();
      return new ResponseWrapper<>(platformVersionValue);
   }

   @GetMapping("/{deviceId}/system/modelNumber")
   public ResponseWrapper<String> getModelNumber(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      StringWrapper modelNumber = this.systemService.getModelNumber(device);
      if (modelNumber == null) {
         return new ResponseWrapper<>(null);
      }

      device.setModelNumber(modelNumber);
      this.deviceService.updateDevice(device);
      String modelNumberValue = modelNumber.getValue();
      return new ResponseWrapper<>(modelNumberValue);
   }

   @GetMapping("/{deviceId}/system/firmwareVersion")
   public ResponseWrapper<String> getFirmwareVersion(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      StringWrapper firmwareVersion = this.systemService.getFirmwareVersion(device);
      if (firmwareVersion == null) {
         return new ResponseWrapper<>(null);
      }

      device.setFirmwareVersion(firmwareVersion);
      this.deviceService.updateDevice(device);
      String firmwareVersionValue = firmwareVersion.getValue();
      return new ResponseWrapper<>(firmwareVersionValue);
   }

   @GetMapping("/{deviceId}/system/buildDate")
   public ResponseWrapper<String> getBuildDate(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      StringWrapper buildDate = this.systemService.getBuildDate(device);
      if (buildDate == null) {
         return new ResponseWrapper<>(null);
      }

      device.setBuildDate(buildDate);
      this.deviceService.updateDevice(device);
      String buildDateValue = buildDate.getValue();
      return new ResponseWrapper<>(buildDateValue);
   }

   @GetMapping("/{deviceId}/system/firmwareVersionAndroid")
   public ResponseWrapper<String> getFirmwareVersionAndroid(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      StringWrapper firmwareVersionAndroid = this.systemService.getFirmwareVersionAndroid(device);
      if (firmwareVersionAndroid == null) {
         return new ResponseWrapper<>(null);
      }

      device.setFirmwareVersionAndroid(firmwareVersionAndroid);
      this.deviceService.updateDevice(device);
      String firmwareVersionAndroidValue = firmwareVersionAndroid.getValue();
      return new ResponseWrapper<>(firmwareVersionAndroidValue);
   }

   @GetMapping("/{deviceId}/general/powerState")
   public ResponseWrapper<String> getPowerState(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      PowerState powerState = this.generalService.getPowerState(device);
      device.setPowerState(powerState);
      this.deviceService.updateDevice(device);
      return this.getResponseWrapper(powerState);
   }

   @PutMapping("/{deviceId}/general/powerState/{powerState}")
   public void setPowerState(@PathVariable("deviceId") final long deviceId, @PathVariable("powerState") final String powerState) {
      Device device = this.getDevice(deviceId);
      PowerState devicePowerState = ValueUtilities.getEnumValue(PowerState.class, powerState);
      this.generalService.setPowerState(device, devicePowerState);
      device.setPowerState(devicePowerState);
      this.deviceService.updateDevice(device);
   }

   private <T extends Enum> ResponseWrapper<String> getResponseWrapper(final T enumValue) {
      String enumString = String.valueOf(enumValue);
      return new ResponseWrapper<>(enumString);
   }

   @GetMapping("/{deviceId}/general/remoteControlLockState")
   public ResponseWrapper<String> getRemoteControlLockState(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      RemoteControlLockState remoteControlLockState = this.generalService.getRemoteControlLockState(device);
      return this.getResponseWrapper(remoteControlLockState);
   }

   @PutMapping("/{deviceId}/general/remoteControlLockState/{remoteControlLockState}")
   public void setRemoteControlLockState(@PathVariable("deviceId") final long deviceId, @PathVariable("remoteControlLockState") String remoteControlLockState) {
      Device device = this.getDevice(deviceId);
      RemoteControlLockState deviceRemoteControlLockState = ValueUtilities.getEnumValue(RemoteControlLockState.class, remoteControlLockState);
      this.generalService.setRemoteControlLockState(device, deviceRemoteControlLockState);
   }

   @GetMapping("/{deviceId}/general/keypadLockState")
   public ResponseWrapper<String> getKeypadLockState(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      KeypadLockState keypadLockState = this.generalService.getKeypadLockState(device);
      return this.getResponseWrapper(keypadLockState);
   }

   @PutMapping("/{deviceId}/general/keypadLockState/{keypadLockState}")
   public void setKeypadLockState(@PathVariable("deviceId") final long deviceId, @PathVariable("keypadLockState") final String keypadLockState) {
      Device device = this.getDevice(deviceId);
      KeypadLockState deviceKeypadLockState = ValueUtilities.getEnumValue(KeypadLockState.class, keypadLockState);
      this.generalService.setKeypadLockState(device, deviceKeypadLockState);
   }

   @GetMapping("/{deviceId}/general/powerStateAtColdStart")
   public ResponseWrapper<String> getPowerStateAtColdStart(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      PowerStateAtColdStart powerStateAtColdStart = this.generalService.getPowerStateAtColdStart(device);
      return this.getResponseWrapper(powerStateAtColdStart);
   }

   @PutMapping("/{deviceId}/general/powerStateAtColdStart/{powerStateAtColdStart}")
   public void setPowerStateAtColdStart(
      @PathVariable("deviceId") final long deviceId, @PathVariable("powerStateAtColdStart") final String powerStateAtColdStart
   ) {
      Device device = this.getDevice(deviceId);
      PowerStateAtColdStart devicePowerStateAtColdStart = ValueUtilities.getEnumValue(PowerStateAtColdStart.class, powerStateAtColdStart);
      this.generalService.setPowerStateAtColdStart(device, devicePowerStateAtColdStart);
   }

   @PutMapping("/{deviceId}/general/monitorRestart/{monitorSystem}")
   public void monitorRestart(@PathVariable("deviceId") long deviceId, @PathVariable("monitorSystem") String monitorSystem) {
      Device device = this.getDevice(deviceId);
      MonitorSystem deviceMonitorSystem = ValueUtilities.getEnumValue(MonitorSystem.class, monitorSystem);
      this.generalService.monitorRestart(device, deviceMonitorSystem);
   }

   @GetMapping("/{deviceId}/general/backlight")
   public ResponseWrapper<String> getBacklight(@PathVariable("deviceId") long deviceId) {
      Device device = this.getDevice(deviceId);
      Backlight backlight = this.generalService.getBacklight(device);
      return this.getResponseWrapper(backlight);
   }

   @PutMapping("/{deviceId}/general/backlight/{backlight}")
   public void setBacklight(@PathVariable("deviceId") long deviceId, @PathVariable("backlight") String backlight) {
      Device device = this.getDevice(deviceId);
      Backlight deviceBacklight = ValueUtilities.getEnumValue(Backlight.class, backlight);
      this.generalService.setBacklight(device, deviceBacklight);
   }

   @GetMapping("/{deviceId}/inputSources/inputSource")
   public InputSourceViewModel getInputSource(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      InputSource inputSource = this.inputSourcesService.getInputSource(device);
      device.setInputSource(inputSource);
      this.deviceService.updateDevice(device);
      return InputSourceMapper.toInputSourceViewModel(inputSource);
   }

   @PutMapping("/{deviceId}/inputSources/inputSource")
   public void setInputSource(@PathVariable("deviceId") final long deviceId, @RequestBody final InputSourceViewModel inputSourceViewModel) {
      Device device = this.getDevice(deviceId);
      InputSource inputSource = InputSourceMapper.toInputSource(inputSourceViewModel);
      this.inputSourcesService.setInputSource(device, inputSource);
      device.setInputSource(inputSource);
      this.deviceService.updateDevice(device);
   }

   @GetMapping("/{deviceId}/inputSources/autoSignalDetecting")
   public ResponseWrapper<String> getAutoSignalDetecting(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      AutoSignalDetecting autoSignalDetecting = this.inputSourcesService.getAutoSignalDetecting(device);
      return this.getResponseWrapper(autoSignalDetecting);
   }

   @PutMapping("/{deviceId}/inputSources/autoSignalDetecting/{autoSignalDetecting}")
   public void setAutoSignalDetecting(@PathVariable("deviceId") final long deviceId, @PathVariable("autoSignalDetecting") final String autoSignalDetecting) {
      Device device = this.getDevice(deviceId);
      AutoSignalDetecting deviceAutoSignalDetecting = ValueUtilities.getEnumValue(AutoSignalDetecting.class, autoSignalDetecting);
      this.inputSourcesService.setAutoSignalDetecting(device, deviceAutoSignalDetecting);
   }

   @GetMapping("/{deviceId}/inputSources/failovers")
   public List<String> getFailovers(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      Failovers failovers = this.inputSourcesService.getFailovers(device);
      return FailoversMapper.toStringList(failovers);
   }

   @PutMapping("/{deviceId}/inputSources/failovers")
   public void setFailovers(@PathVariable("deviceId") final long deviceId, @RequestBody final List<String> failovers) {
      Device device = this.getDevice(deviceId);
      Failovers deviceFailovers = FailoversMapper.toFailovers(failovers);
      this.inputSourcesService.setFailovers(device, deviceFailovers);
   }

   @GetMapping("/{deviceId}/video/videoParameters")
   public VideoParametersViewModel getVideoParameters(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      VideoParameters videoParameters = this.videoService.getVideoParameters(device);
      return VideoParametersMapper.toVideoParametersViewModel(videoParameters);
   }

   @PutMapping("/{deviceId}/video/videoParameters")
   public void setVideoParameters(@PathVariable("deviceId") final long deviceId, @RequestBody final VideoParametersViewModel videoParametersViewModel) {
      Device device = this.getDevice(deviceId);
      VideoParameters videoParameters = VideoParametersMapper.toVideoParameters(videoParametersViewModel);
      this.videoService.setVideoParameters(device, videoParameters);
   }

   @GetMapping("/{deviceId}/video/colorTemperature")
   public ResponseWrapper<String> getColorTemperature(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      ColorTemperature colorTemperature = this.videoService.getColorTemperature(device);
      return this.getResponseWrapper(colorTemperature);
   }

   @PutMapping("/{deviceId}/video/colorTemperature/{colorTemperature}")
   public void setColorTemperature(@PathVariable("deviceId") final long deviceId, @PathVariable("colorTemperature") final String colorTemperature) {
      Device device = this.getDevice(deviceId);
      ColorTemperature deviceColorTemperature = ValueUtilities.getEnumValue(ColorTemperature.class, colorTemperature);
      this.videoService.setColorTemperature(device, deviceColorTemperature);
   }

   @GetMapping("/{deviceId}/video/colorParameters")
   public ColorParametersViewModel getColorParameters(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      ColorParameters colorParameters = this.videoService.getColorParameters(device);
      return ColorParametersMapper.toColorParametersViewModel(colorParameters);
   }

   @PutMapping("/{deviceId}/video/colorParameters")
   public void setColorParameters(@PathVariable("deviceId") final long deviceId, @RequestBody final ColorParametersViewModel colorParametersViewModel) {
      Device device = this.getDevice(deviceId);
      ColorParameters colorParameters = ColorParametersMapper.toColorParameters(colorParametersViewModel);
      this.videoService.setColorParameters(device, colorParameters);
   }

   @GetMapping("/{deviceId}/video/colorTemperature100K")
   public ResponseWrapper<String> getColorTemperature100K(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      ColorTemperature100K colorTemperature100K = this.videoService.getColorTemperature100K(device);
      return this.getResponseWrapper(colorTemperature100K);
   }

   @PutMapping("/{deviceId}/video/colorTemperature100K/{colorTemperature100K}")
   public void setColorTemperature100K(@PathVariable("deviceId") final long deviceId, @PathVariable("colorTemperature100K") final String colorTemperature100K) {
      Device device = this.getDevice(deviceId);
      ColorTemperature100K deviceColorTemperature100K = ValueUtilities.getEnumValue(ColorTemperature100K.class, colorTemperature100K);
      this.videoService.setColorTemperature100K(device, deviceColorTemperature100K);
   }

   @GetMapping("/{deviceId}/video/pictureFormat")
   public ResponseWrapper<String> getPictureFormat(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      PictureFormat pictureFormat = this.videoService.getPictureFormat(device);
      return this.getResponseWrapper(pictureFormat);
   }

   @PutMapping("/{deviceId}/video/pictureFormat/{pictureFormat}")
   public void setPictureFormat(@PathVariable("deviceId") final long deviceId, @PathVariable("pictureFormat") final String pictureFormat) {
      Device device = this.getDevice(deviceId);
      PictureFormat devicePictureFormat = ValueUtilities.getEnumValue(PictureFormat.class, pictureFormat);
      this.videoService.setPictureFormat(device, devicePictureFormat);
   }

   @GetMapping("/{deviceId}/video/vgaVideoParameters")
   public VGAVideoParametersViewModel getVGAVideoParameters(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      VGAVideoParameters vgaVideoParameters = this.videoService.getVGAVideoParameters(device);
      return VGAVideoParametersMapper.toVGAVideoParametersViewModel(vgaVideoParameters);
   }

   @PutMapping("/{deviceId}/video/vgaVideoParameters")
   public void setVGAVideoParameters(@PathVariable("deviceId") final long deviceId, @RequestBody final VGAVideoParametersViewModel vgaVideoParametersViewModel) {
      Device device = this.getDevice(deviceId);
      VGAVideoParameters deviceVGAVideoParameters = VGAVideoParametersMapper.toVGAVideoParameters(vgaVideoParametersViewModel);
      this.videoService.setVGAVideoParameters(device, deviceVGAVideoParameters);
   }

   @GetMapping("/{deviceId}/video/pictureInPicture")
   public PictureInPictureViewModel getPictureInPicture(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      PictureInPicture pictureInPicture = this.videoService.getPictureInPicture(device);
      return PictureInPictureMapper.toPictureInPictureViewModel(pictureInPicture);
   }

   @PutMapping("/{deviceId}/video/pictureInPicture")
   public void setPictureInPicture(@PathVariable("deviceId") final long deviceId, @RequestBody final PictureInPictureViewModel pictureInPictureViewModel) {
      Device device = this.getDevice(deviceId);
      PictureInPicture devicePictureInPicture = PictureInPictureMapper.toPictureInPicture(pictureInPictureViewModel);
      this.videoService.setPictureInPicture(device, devicePictureInPicture);
   }

   @GetMapping("/{deviceId}/video/pictureInPicture/source")
   public PictureInPictureSourceViewModel getPictureInPictureSource(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      PictureInPictureSource pictureInPictureSource = this.videoService.getPictureInPictureSource(device);
      return PictureInPictureSourceMapper.toPictureInPictureSourceViewModel(pictureInPictureSource);
   }

   @PutMapping("/{deviceId}/video/pictureInPicture/source")
   public void setPictureInPictureSource(
      @PathVariable("deviceId") final long deviceId, @RequestBody final PictureInPictureSourceViewModel pictureInPictureSourceViewModel
   ) {
      Device device = this.getDevice(deviceId);
      PictureInPictureSource devicePictureInPictureSource = PictureInPictureSourceMapper.toPictureInPictureSource(pictureInPictureSourceViewModel);
      this.videoService.setPictureInPictureSource(device, devicePictureInPictureSource);
   }

   @GetMapping("/{deviceId}/audio/volume")
   public VolumeViewModel getVolume(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      Volume volume = this.audioService.getVolume(device);
      return VolumeMapper.toVolumeViewModel(volume);
   }

   @PutMapping("/{deviceId}/audio/volume")
   public void setVolume(@PathVariable("deviceId") final long deviceId, @RequestBody final VolumeViewModel volumeViewModel) {
      Device device = this.getDevice(deviceId);
      Volume volume = VolumeMapper.toVolume(volumeViewModel);
      this.audioService.setVolume(device, volume);
   }

   @PutMapping("/{deviceId}/audio/volumeUpDown")
   public void setVolume(@PathVariable("deviceId") final long deviceId, @RequestBody final VolumeUpDownViewModel volumeUpDownViewModel) {
      Device device = this.getDevice(deviceId);
      VolumeUpDown volumeUpDown = VolumeUpDownMapper.toVolumeUpDown(volumeUpDownViewModel);
      this.audioService.setVolume(device, volumeUpDown);
   }

   @GetMapping("/{deviceId}/audio/volumeLimits/speakerOut")
   public VolumeLimitsViewModel getVolumeLimitsSpeakerOut(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      VolumeLimits volumeLimits = this.audioService.getVolumeLimitsSpeakerOut(device);
      return VolumeLimitsMapper.toVolumeLimitsViewModel(volumeLimits);
   }

   @PutMapping("/{deviceId}/audio/volumeLimits/speakerOut")
   public void setVolumeLimitsSpeakerOut(@PathVariable("deviceId") final long deviceId, @RequestBody final VolumeLimitsViewModel volumeLimitsViewModel) {
      Device device = this.getDevice(deviceId);
      VolumeLimits volumeLimits = VolumeLimitsMapper.toVolumeLimits(volumeLimitsViewModel);
      this.audioService.setVolumeLimitsSpeakerOut(device, volumeLimits);
   }

   @GetMapping("/{deviceId}/audio/volumeLimits/audioOut")
   public VolumeLimitsViewModel getVolumeLimitsAudioOut(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      VolumeLimits volumeLimits = this.audioService.getVolumeLimitsAudioOut(device);
      return VolumeLimitsMapper.toVolumeLimitsViewModel(volumeLimits);
   }

   @PutMapping("/{deviceId}/audio/volumeLimits/audioOut")
   public void setVolumeLimitsAudioOut(@PathVariable("deviceId") final long deviceId, @RequestBody final VolumeLimitsViewModel volumeLimitsViewModel) {
      Device device = this.getDevice(deviceId);
      VolumeLimits volumeLimits = VolumeLimitsMapper.toVolumeLimits(volumeLimitsViewModel);
      this.audioService.setVolumeLimitsAudioOut(device, volumeLimits);
   }

   @GetMapping("/{deviceId}/audio/audioParameters")
   public AudioParametersViewModel getAudioParameters(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      AudioParameters audioParameters = this.audioService.getAudioParameters(device);
      return AudioParametersMapper.toAudioParametersViewModel(audioParameters);
   }

   @PutMapping("/{deviceId}/audio/audioParameters")
   public void setAudioParameters(@PathVariable("deviceId") final long deviceId, @RequestBody final AudioParametersViewModel audioParametersViewModel) {
      Device device = this.getDevice(deviceId);
      AudioParameters deviceAudioParameters = AudioParametersMapper.toAudioParameters(audioParametersViewModel);
      this.audioService.setAudioParameters(device, deviceAudioParameters);
   }

   @GetMapping("/{deviceId}/miscellaneous/miscellaneous")
   public MiscellaneousViewModel getMiscellaneous(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      Miscellaneous miscellaneous = this.miscellaneousService.getMiscellaneous(device);
      device.setMiscellaneous(miscellaneous);
      this.deviceService.updateDevice(device);
      return MiscellaneousMapper.toMiscellaneousViewModel(miscellaneous);
   }

   @GetMapping("/{deviceId}/miscellaneous/smartPower")
   public ResponseWrapper<String> getSmartPower(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      SmartPower smartPower = this.miscellaneousService.getSmartPower(device);
      return this.getResponseWrapper(smartPower);
   }

   @PutMapping("/{deviceId}/miscellaneous/smartPower/{smartPower}")
   public void setSmartPower(@PathVariable("deviceId") final long deviceId, @PathVariable("smartPower") final String smartPower) {
      Device device = this.getDevice(deviceId);
      SmartPower deviceSmartPower = ValueUtilities.getEnumValue(SmartPower.class, smartPower);
      this.miscellaneousService.setSmartPower(device, deviceSmartPower);
   }

   @PutMapping("/{deviceId}/miscellaneous/videoAlignment")
   public void setVideoAlignment(@PathVariable("deviceId") final long deviceId, @RequestBody final VideoAlignmentViewModel videoAlignmentViewModel) {
      Device device = this.getDevice(deviceId);
      VideoAlignment videoAlignment = VideoAlignmentMapper.toVideoAlignment(videoAlignmentViewModel);
      this.miscellaneousService.setVideoAlignment(device, videoAlignment);
   }

   @GetMapping("/{deviceId}/miscellaneous/temperatureSensor")
   public ResponseWrapper<Integer> getTemperatureSensor(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      TemperatureSensor temperatureSensor = this.miscellaneousService.getTemperatureSensor(device);
      if (temperatureSensor == null) {
         return new ResponseWrapper<>(null);
      }

      device.setTemperature(temperatureSensor);
      this.deviceService.updateDevice(device);
      int temperature = TemperatureSensorMapper.toInteger(temperatureSensor);
      return new ResponseWrapper<>(temperature);
   }

   @GetMapping("/{deviceId}/miscellaneous/serialCode")
   public ResponseWrapper<String> getSerialCode(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      StringWrapper serialCode = this.miscellaneousService.getSerialCode(device);
      if (serialCode == null) {
         return new ResponseWrapper<>(null);
      }

      device.setSerialCode(serialCode);
      this.deviceService.updateDevice(device);
      String serialCodeValue = serialCode.getValue();
      return new ResponseWrapper<>(serialCodeValue);
   }

   @GetMapping("/{deviceId}/miscellaneous/tiling")
   public TilingViewModel getTiling(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      Tiling tiling = this.miscellaneousService.getTiling(device);
      return TilingMapper.toTilingViewModel(tiling);
   }

   @PutMapping("/{deviceId}/miscellaneous/tiling")
   public void setTiling(@PathVariable("deviceId") final long deviceId, @RequestBody final TilingViewModel tilingViewModel) {
      Device device = this.getDevice(deviceId);
      Tiling tiling = TilingMapper.toTiling(tilingViewModel);
      this.miscellaneousService.setTiling(device, tiling);
   }

   @GetMapping("/{deviceId}/miscellaneous/frameCompensationHorizontal")
   public ResponseWrapper<Integer> getFrameCompensationHorizontal(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      IntWrapper deviceFrameCompensationHorizontal = this.miscellaneousService.getFrameCompensationHorizontal(device);
      if (deviceFrameCompensationHorizontal == null) {
         return new ResponseWrapper<>(null);
      }

      int frameCompensationHorizontal = deviceFrameCompensationHorizontal.getValue();
      return new ResponseWrapper<>(frameCompensationHorizontal);
   }

   @PutMapping("/{deviceId}/miscellaneous/frameCompensationHorizontal/{frameCompensationHorizontal}")
   public void setFrameCompensationHorizontal(
      @PathVariable("deviceId") final long deviceId, @PathVariable("frameCompensationHorizontal") final int frameCompensationHorizontal
   ) {
      Device device = this.getDevice(deviceId);
      IntWrapper deviceFrameCompensationHorizontal = new IntWrapper(frameCompensationHorizontal);
      this.miscellaneousService.setFrameCompensationHorizontal(device, deviceFrameCompensationHorizontal);
   }

   @GetMapping("/{deviceId}/miscellaneous/frameCompensationVertical")
   public ResponseWrapper<Integer> getFrameCompensationVertical(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      IntWrapper deviceFrameCompensationVertical = this.miscellaneousService.getFrameCompensationVertical(device);
      if (deviceFrameCompensationVertical == null) {
         return new ResponseWrapper<>(null);
      }

      int frameCompensationVertical = deviceFrameCompensationVertical.getValue();
      return new ResponseWrapper<>(frameCompensationVertical);
   }

   @PutMapping("/{deviceId}/miscellaneous/frameCompensationVertical/{frameCompensationVertical}")
   public void setFrameCompensationVertical(
      @PathVariable("deviceId") final long deviceId, @PathVariable("frameCompensationVertical") final int frameCompensationVertical
   ) {
      Device device = this.getDevice(deviceId);
      IntWrapper deviceFrameCompensationVertical = new IntWrapper(frameCompensationVertical);
      this.miscellaneousService.setFrameCompensationVertical(device, deviceFrameCompensationVertical);
   }

   @GetMapping("/{deviceId}/miscellaneous/lightSensor")
   public ResponseWrapper<String> getLightSensor(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      LightSensor lightSensor = this.miscellaneousService.getLightSensor(device);
      return this.getResponseWrapper(lightSensor);
   }

   @PutMapping("/{deviceId}/miscellaneous/lightSensor/{lightSensor}")
   public void setLightSensor(@PathVariable("deviceId") final long deviceId, @PathVariable("lightSensor") final String lightSensor) {
      Device device = this.getDevice(deviceId);
      LightSensor deviceLightSensor = ValueUtilities.getEnumValue(LightSensor.class, lightSensor);
      this.miscellaneousService.setLightSensor(device, deviceLightSensor);
   }

   @GetMapping("/{deviceId}/miscellaneous/osdRotating")
   public ResponseWrapper<String> getOsdRotating(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      OSDRotating osdRotating = this.miscellaneousService.getOSDRotating(device);
      return this.getResponseWrapper(osdRotating);
   }

   @PutMapping("/{deviceId}/miscellaneous/osdRotating/{osdRotating}")
   public void setOsdRotating(@PathVariable("deviceId") final long deviceId, @PathVariable("osdRotating") final String osdRotating) {
      Device device = this.getDevice(deviceId);
      OSDRotating deviceOSDRotating = ValueUtilities.getEnumValue(OSDRotating.class, osdRotating);
      this.miscellaneousService.setOSDRotating(device, deviceOSDRotating);
   }

   @GetMapping("/{deviceId}/miscellaneous/osdInformation")
   public ResponseWrapper<Integer> getOsdInformation(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      IntWrapper deviceOSDInformation = this.miscellaneousService.getOSDInformation(device);
      if (deviceOSDInformation == null) {
         return new ResponseWrapper<>(null);
      }

      int osdInformation = deviceOSDInformation.getValue();
      return new ResponseWrapper<>(osdInformation);
   }

   @PutMapping("/{deviceId}/miscellaneous/osdInformation/{osdInformation}")
   public void setOsdInformation(@PathVariable("deviceId") final long deviceId, @PathVariable("osdInformation") final int osdInformation) {
      Device device = this.getDevice(deviceId);
      IntWrapper deviceOSDInformation = new IntWrapper(osdInformation);
      this.miscellaneousService.setOSDInformation(device, deviceOSDInformation);
   }

   @GetMapping("/{deviceId}/miscellaneous/memcEffect")
   public ResponseWrapper<String> getMemcEffect(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      MEMCEffect memcEffect = this.miscellaneousService.getMEMCEffect(device);
      return this.getResponseWrapper(memcEffect);
   }

   @PutMapping("/{deviceId}/miscellaneous/memcEffect/{memcEffect}")
   public void setMemcEffect(@PathVariable("deviceId") final long deviceId, @PathVariable("memcEffect") final String memcEffect) {
      Device device = this.getDevice(deviceId);
      MEMCEffect deviceMEMCEffect = ValueUtilities.getEnumValue(MEMCEffect.class, memcEffect);
      this.miscellaneousService.setMEMCEffect(device, deviceMEMCEffect);
   }

   @GetMapping("/{deviceId}/miscellaneous/touch")
   public ResponseWrapper<String> getTouch(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      Touch touch = this.miscellaneousService.getTouch(device);
      return this.getResponseWrapper(touch);
   }

   @PutMapping("/{deviceId}/miscellaneous/touch/{touch}")
   public void setTouch(@PathVariable("deviceId") final long deviceId, @PathVariable("touch") final String touch) {
      Device device = this.getDevice(deviceId);
      Touch deviceTouch = ValueUtilities.getEnumValue(Touch.class, touch);
      this.miscellaneousService.setTouch(device, deviceTouch);
   }

   @GetMapping("/{deviceId}/miscellaneous/noiseReduction")
   public ResponseWrapper<String> getNoiseReduction(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      NoiseReduction noiseReduction = this.miscellaneousService.getNoiseReduction(device);
      return this.getResponseWrapper(noiseReduction);
   }

   @PutMapping("/{deviceId}/miscellaneous/noiseReduction/{noiseReduction}")
   public void setNoiseReduction(@PathVariable("deviceId") final long deviceId, @PathVariable("noiseReduction") final String noiseReduction) {
      Device device = this.getDevice(deviceId);
      NoiseReduction deviceNoiseReduction = ValueUtilities.getEnumValue(NoiseReduction.class, noiseReduction);
      this.miscellaneousService.setNoiseReduction(device, deviceNoiseReduction);
   }

   @GetMapping("/{deviceId}/miscellaneous/scanMode")
   public ResponseWrapper<String> getScanMode(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      ScanMode scanMode = this.miscellaneousService.getScanMode(device);
      return this.getResponseWrapper(scanMode);
   }

   @PutMapping("/{deviceId}/miscellaneous/scanMode/{scanMode}")
   public void setScanMode(@PathVariable("deviceId") final long deviceId, @PathVariable("scanMode") final String scanMode) {
      Device device = this.getDevice(deviceId);
      ScanMode deviceScanMode = ValueUtilities.getEnumValue(ScanMode.class, scanMode);
      this.miscellaneousService.setScanMode(device, deviceScanMode);
   }

   @GetMapping("/{deviceId}/miscellaneous/scanConversion")
   public ResponseWrapper<String> getScanConversion(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      ScanConversion scanConversion = this.miscellaneousService.getScanConversion(device);
      return this.getResponseWrapper(scanConversion);
   }

   @PutMapping("/{deviceId}/miscellaneous/scanConversion/{scanConversion}")
   public void setScanConversion(@PathVariable("deviceId") final long deviceId, @PathVariable("scanConversion") final String scanConversion) {
      Device device = this.getDevice(deviceId);
      ScanConversion deviceScanConversion = ValueUtilities.getEnumValue(ScanConversion.class, scanConversion);
      this.miscellaneousService.setScanConversion(device, deviceScanConversion);
   }

   @GetMapping("/{deviceId}/miscellaneous/switchOnDelay")
   public ResponseWrapper<String> getSwitchOnDelay(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      SwitchOnDelay switchOnDelay = this.miscellaneousService.getSwitchOnDelay(device);
      return this.getResponseWrapper(switchOnDelay);
   }

   @PutMapping("/{deviceId}/miscellaneous/switchOnDelay/{switchOnDelay}")
   public void setSwitchOnDelay(@PathVariable("deviceId") final long deviceId, @PathVariable("switchOnDelay") final String switchOnDelay) {
      Device device = this.getDevice(deviceId);
      SwitchOnDelay deviceSwitchOnDelay = ValueUtilities.getEnumValue(SwitchOnDelay.class, switchOnDelay);
      this.miscellaneousService.setSwitchOnDelay(device, deviceSwitchOnDelay);
   }

   @PutMapping("/{deviceId}/miscellaneous/factoryReset")
   public void factoryReset(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      this.miscellaneousService.factoryReset(device);
   }

   @GetMapping("/{deviceId}/miscellaneous/powerOnLogo")
   public ResponseWrapper<String> getPowerOnLogo(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      PowerOnLogo powerOnLogo = this.miscellaneousService.getPowerOnLogo(device);
      return this.getResponseWrapper(powerOnLogo);
   }

   @PutMapping("/{deviceId}/miscellaneous/powerOnLogo/{powerOnLogo}")
   public void setPowerOnLogo(@PathVariable("deviceId") final long deviceId, @PathVariable("powerOnLogo") final String powerOnLogo) {
      Device device = this.getDevice(deviceId);
      PowerOnLogo devicePowerOnLogo = ValueUtilities.getEnumValue(PowerOnLogo.class, powerOnLogo);
      this.miscellaneousService.setPowerOnLogo(device, devicePowerOnLogo);
   }

   @GetMapping("/{deviceId}/miscellaneous/fanSpeed")
   public ResponseWrapper<String> getFanSpeed(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      FanSpeed fanSpeed = this.miscellaneousService.getFanSpeed(device);
      return this.getResponseWrapper(fanSpeed);
   }

   @PutMapping("/{deviceId}/miscellaneous/fanSpeed/{fanSpeed}")
   public void setFanSpeed(@PathVariable("deviceId") final long deviceId, @PathVariable("fanSpeed") final String fanSpeed) {
      Device device = this.getDevice(deviceId);
      FanSpeed deviceFanSpeed = ValueUtilities.getEnumValue(FanSpeed.class, fanSpeed);
      this.miscellaneousService.setFanSpeed(device, deviceFanSpeed);
   }

   @GetMapping("/{deviceId}/miscellaneous/apm")
   public ResponseWrapper<String> getAPM(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      APM apm = this.miscellaneousService.getAPM(device);
      return this.getResponseWrapper(apm);
   }

   @PutMapping("/{deviceId}/miscellaneous/apm/{apm}")
   public void setAPM(@PathVariable("deviceId") final long deviceId, @PathVariable("apm") final String apm) {
      Device device = this.getDevice(deviceId);
      APM deviceAPM = ValueUtilities.getEnumValue(APM.class, apm);
      this.miscellaneousService.setAPM(device, deviceAPM);
   }

   @GetMapping("/{deviceId}/miscellaneous/powerSavingMode")
   public ResponseWrapper<String> getPowerSavingMode(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      PowerSavingMode powerSavingMode = this.miscellaneousService.getPowerSavingMode(device);
      return this.getResponseWrapper(powerSavingMode);
   }

   @PutMapping("/{deviceId}/miscellaneous/powerSavingMode/{powerSavingMode}")
   public void setPowerSavingMode(@PathVariable("deviceId") final long deviceId, @PathVariable("powerSavingMode") final String powerSavingMode) {
      Device device = this.getDevice(deviceId);
      PowerSavingMode devicePowerSavingMode = ValueUtilities.getEnumValue(PowerSavingMode.class, powerSavingMode);
      this.miscellaneousService.setPowerSavingMode(device, devicePowerSavingMode);
   }

   @GetMapping("/{deviceId}/miscellaneous/displayOrientation")
   public DisplayOrientationViewModel getDisplayOrientation(@PathVariable("deviceId") final long deviceId) {
      Device device = this.deviceService.getDevice(deviceId);
      DisplayOrientation displayOrientation = this.miscellaneousService.getDisplayOrientation(device);
      return DisplayOrientationMapper.toDisplayOrientationViewModel(displayOrientation);
   }

   @PutMapping("/{deviceId}/miscellaneous/displayOrientation")
   public void setDisplayOrientation(@PathVariable("deviceId") final long deviceId, @RequestBody final DisplayOrientationViewModel displayOrientationViewModel) {
      Device device = this.getDevice(deviceId);
      DisplayOrientation displayOrientation = DisplayOrientationMapper.toDisplayOrientation(displayOrientationViewModel);
      this.miscellaneousService.setDisplayOrientation(device, displayOrientation);
   }

   @GetMapping("/{deviceId}/miscellaneous/ledStrips")
   public LedStripsViewModel getLedStrips(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      LedStrips ledStrips = this.miscellaneousService.getLedStrips(device);
      return LedStripsMapper.toLedStripsViewModel(ledStrips);
   }

   @PutMapping("/{deviceId}/miscellaneous/ledStrips")
   public void setLedStrips(@PathVariable("deviceId") final long deviceId, @RequestBody final LedStripsViewModel ledStripsViewModel) {
      Device device = this.deviceService.getDevice(deviceId);
      LedStrips ledStrips = LedStripsMapper.toLedStrips(ledStripsViewModel);
      this.miscellaneousService.setLedStrips(device, ledStrips);
   }

   @GetMapping("/{deviceId}/miscellaneous/portStatus")
   public ResponseWrapper<String> getPortStatus(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      PortStatus portStatus = this.miscellaneousService.getPortStatus(device);
      return this.getResponseWrapper(portStatus);
   }

   @PutMapping("/{deviceId}/miscellaneous/portStatus/{portStatus}")
   public void setPortStatus(@PathVariable("deviceId") final long deviceId, @PathVariable("portStatus") final String portStatus) {
      Device device = this.getDevice(deviceId);
      PortStatus devicePortStatus = ValueUtilities.getEnumValue(PortStatus.class, portStatus);
      this.miscellaneousService.setPortStatus(device, devicePortStatus);
   }

   @PutMapping("/{deviceId}/content/{contentId}")
   public void setContent(@PathVariable("deviceId") final long deviceId, @PathVariable("contentId") final String contentId) {
      Assert.state(this.contentManagementService != null, SetContentMessages.CONTENT_MANAGEMENT_SERVICE_CAN_NOT_BE_NULL);
      Assert.isTrue(deviceId >= 0L, SetContentMessages.DEVICE_ID_HAS_TO_BE_A_POSITIVE_NUMBER);
      this.contentManagementService.setContent(deviceId, contentId);
   }

   @PutMapping("/{deviceId}/content/unassign")
   public void unassignContent(@PathVariable("deviceId") final long deviceId) {
      Assert.state(this.contentManagementService != null, UnassignContentMessages.CONTENT_MANAGEMENT_SERVICE_CAN_NOT_BE_NULL);
      Assert.isTrue(deviceId >= 0L, UnassignContentMessages.DEVICE_ID_HAS_TO_BE_A_POSITIVE_NUMBER);
      this.contentManagementService.unassignContent(deviceId);
   }

   @GetMapping("/{deviceId}/content/download")
   public ResponseEntity<InputStreamResource> downloadContent(@PathVariable("deviceId") final long deviceId) {
      Assert.state(this.deviceServiceJdbc != null, DownloadContentMessages.DEVICE_SERVICE_JDBC_CAN_NOT_BE_NULL);
      Assert.state(this.hardwareServiceJdbc != null, DownloadContentMessages.HARDWARE_SERVICE_JDBC_CAN_NOT_BE_NULL);
      Assert.state(this.contentManagementService != null, DownloadContentMessages.CONTENT_MANAGEMENT_SERVICE_CAN_NOT_BE_NULL);
      Assert.isTrue(deviceId >= 0L, DownloadContentMessages.DEVICE_ID_HAS_TO_BE_A_POSITIVE_NUMBER);
      String serialCode = this.deviceServiceJdbc.getSerialCode(deviceId);
      Assert.state(serialCode != null, "Serial code is unknown.");
      Hardware hardware = this.hardwareServiceJdbc.getHardware(serialCode);
      Assert.state(hardware != null, "No content assigned to device.");
      String contentId = hardware.getContentId();
      Assert.state(contentId != null, "No content assigned to device.");
      boolean isHardwareBusy = this.contentManagementService.isHardwareBusy(serialCode);
      Assert.isTrue(!isHardwareBusy, "Hardware is busy.");
      this.contentManagementService.updateContent(contentId, true, true);
      Path cmsPath = this.contentManagementService.getHardwareCmsPath(serialCode);
      Assert.state(cmsPath != null, DownloadContentMessages.CMS_PATH_CAN_NOT_BE_NULL);
      String contentType = "application/zip";
      return DownloadUtilities.getResponseEntity(cmsPath, "application/zip");
   }

   @PostMapping("/{deviceId}/content/sendToFtp")
   public void sendSystemConfigToFtp(@PathVariable("deviceId") final long deviceId) {
      DeviceDTO deviceDTO = this.deviceServiceJdbc.getDeviceDTO(deviceId);
      this.contentManagementService.uploadSystemConfigToFtp(deviceDTO);
   }

   @GetMapping("/{deviceId}/scheduling/schedulingParameters/page{pageNumber}")
   public PageViewModel getSchedulingParametersPage(@PathVariable("deviceId") final long deviceId, @PathVariable("pageNumber") final int pageNumber) {
      Device device = this.getDevice(deviceId);
      IntWrapper number = new IntWrapper(pageNumber);
      Page page = this.schedulingService.getSchedulingParametersPage(device, number);
      PageViewModel pageViewModel = PageMapper.toPageViewModel(page);
      pageViewModel.setNumber(pageNumber);
      return pageViewModel;
   }

   @PutMapping("/{deviceId}/scheduling/schedulingParameters/page{pageNumber}")
   public void setSchedulingParametersPage(
      @PathVariable("deviceId") final long deviceId, @PathVariable("pageNumber") final int pageNumber, @RequestBody final PageViewModel pageViewModel
   ) {
      Device device = this.getDevice(deviceId);
      Page page = PageMapper.toPage(pageViewModel);
      page.setNumber(pageNumber);
      this.schedulingService.setSchedulingParametersPage(device, page);
   }

   @GetMapping("/{deviceId}/matrixPosition")
   public MatrixPositionViewModel getMatrixPosition(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      MatrixPosition matrixPosition = device.getMatrixPosition();
      return MatrixPositionMapper.toMatrixPositionViewModel(matrixPosition);
   }

   @PutMapping("/{deviceId}/matrixPosition")
   public void setMatrixPosition(@PathVariable("deviceId") final long deviceId, @RequestBody final MatrixPositionViewModel matrixPositionViewModel) {
      Device device = this.getDevice(deviceId);
      MatrixPosition matrixPosition = MatrixPositionMapper.toMatrixPosition(matrixPositionViewModel);
      device.setMatrixPosition(matrixPosition);
      this.deviceService.updateDevice(device);
   }

   @GetMapping("/{deviceId}/miscellaneous/offTimer")
   public ResponseWrapper<Integer> getOffTimer(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      IntWrapper deviceOffTimer = this.miscellaneousService.getOffTimer(device);
      if (deviceOffTimer == null) {
         return new ResponseWrapper<>(null);
      }

      int offTimer = deviceOffTimer.getValue();
      return new ResponseWrapper<>(offTimer);
   }

   @PutMapping("/{deviceId}/miscellaneous/offTimer/{offTimer}")
   public void setOffTimer(@PathVariable("deviceId") final long deviceId, @PathVariable("offTimer") final int offTimer) {
      Device device = this.getDevice(deviceId);
      IntWrapper deviceOffTimer = new IntWrapper(offTimer);
      this.miscellaneousService.setOffTimer(device, deviceOffTimer);
   }

   @GetMapping("/{deviceId}/miscellaneous/lockUsb")
   public ResponseWrapper<String> getLockUsb(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      LockUsb lockUsb = this.miscellaneousService.getLockUsb(device);
      return this.getResponseWrapper(lockUsb);
   }

   @PutMapping("/{deviceId}/miscellaneous/lockUsb/{lockUsb}")
   public void setLockUsb(@PathVariable("deviceId") final long deviceId, @PathVariable("lockUsb") final String lockUsb) {
      Device device = this.getDevice(deviceId);
      LockUsb deviceLockUsb = ValueUtilities.getEnumValue(LockUsb.class, lockUsb);
      this.miscellaneousService.setLockUsb(device, deviceLockUsb);
   }

   @GetMapping("/{deviceId}/miscellaneous/humanSensor")
   public ResponseWrapper<Integer> getHumanSensor(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      IntWrapper deviceHumanSensor = this.miscellaneousService.getHumanSensor(device);
      if (deviceHumanSensor == null) {
         return new ResponseWrapper<>(null);
      }

      int humanSensor = deviceHumanSensor.getValue();
      return new ResponseWrapper<>(humanSensor);
   }

   @PutMapping("/{deviceId}/miscellaneous/humanSensor/{humanSensor}")
   public void setHumanSensor(@PathVariable("deviceId") final long deviceId, @PathVariable("humanSensor") final int humanSensor) {
      Device device = this.getDevice(deviceId);
      IntWrapper deviceHumanSensor = new IntWrapper(humanSensor);
      this.miscellaneousService.setHumanSensor(device, deviceHumanSensor);
   }

   @GetMapping("/{deviceId}/miscellaneous/pixelShift")
   public PixelShiftViewModel getPixelShift(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      PixelShift pixelShift = this.miscellaneousService.getPixelShift(device);
      return PixelShiftMapper.toPixelShiftViewModel(pixelShift);
   }

   @PutMapping("/{deviceId}/miscellaneous/pixelShift")
   public void setPixelShift(@PathVariable("deviceId") final long deviceId, @RequestBody final PixelShiftViewModel pixelShift) {
      Device device = this.getDevice(deviceId);
      PixelShift devicePixelShift = PixelShiftMapper.toPixelShift(pixelShift);
      this.miscellaneousService.setPixelShift(device, devicePixelShift);
   }

   @GetMapping("/{deviceId}/miscellaneous/ecoMode")
   public ResponseWrapper<String> getEcoMode(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      EcoMode ecoMode = this.miscellaneousService.getEcoMode(device);
      return this.getResponseWrapper(ecoMode);
   }

   @PutMapping("/{deviceId}/miscellaneous/ecoMode/{ecoMode}")
   public void setEcoMode(@PathVariable("deviceId") final long deviceId, @PathVariable("ecoMode") final String ecoMode) {
      Device device = this.getDevice(deviceId);
      EcoMode deviceEcoMode = ValueUtilities.getEnumValue(EcoMode.class, ecoMode);
      this.miscellaneousService.setEcoMode(device, deviceEcoMode);
   }

   @PutMapping("/{deviceId}/miscellaneous/takeScreenshot")
   public void takeScreenshot(@PathVariable("deviceId") long deviceId) {
      Device device = this.getDevice(deviceId);
      this.miscellaneousService.takeScreenshot(device);
   }

   @GetMapping("/{deviceId}/miscellaneous/videoPresent")
   public ResponseWrapper<String> getVideoPresent(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      VideoPresent videoPresent = this.miscellaneousService.getVideoPresent(device);
      return this.getResponseWrapper(videoPresent);
   }

   @PutMapping("/{deviceId}/miscellaneous/openAndroidMenu")
   public void openAndroidMenu(@PathVariable("deviceId") long deviceId) {
      Device device = this.getDevice(deviceId);
      this.miscellaneousService.openAndroidMenu(device);
   }

   @GetMapping("/{deviceId}/miscellaneous/navigationBar")
   public ResponseWrapper<String> getNavigationBar(@PathVariable("deviceId") long deviceId) {
      Device device = this.getDevice(deviceId);
      NavigationBar navigationBar = this.miscellaneousService.getNavigationBar(device);
      return this.getResponseWrapper(navigationBar);
   }

   @PutMapping("/{deviceId}/miscellaneous/navigationBar/{navigationBar}")
   public void setNavigationBar(@PathVariable("deviceId") long deviceId, @PathVariable("navigationBar") String navigationBar) {
      Device device = this.getDevice(deviceId);
      NavigationBar deviceNavigationBar = ValueUtilities.getEnumValue(NavigationBar.class, navigationBar);
      this.miscellaneousService.setNavigationBar(device, deviceNavigationBar);
   }

   @GetMapping("/{deviceId}/audio/mute")
   public ResponseWrapper<String> getMute(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      Mute mute = this.audioService.getMute(device);
      return this.getResponseWrapper(mute);
   }

   @PutMapping("/{deviceId}/audio/mute/{mute}")
   public void setMute(@PathVariable("deviceId") final long deviceId, @PathVariable("mute") final String mute) {
      Device device = this.getDevice(deviceId);
      Mute deviceMute = ValueUtilities.getEnumValue(Mute.class, mute);
      this.audioService.setMute(device, deviceMute);
   }

   @GetMapping("/{deviceId}/video/pictureStyle")
   public ResponseWrapper<String> getPictureStyle(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      PictureStyle pictureStyle = this.videoService.getPictureStyle(device);
      return this.getResponseWrapper(pictureStyle);
   }

   @PutMapping("/{deviceId}/video/pictureStyle/{pictureStyle}")
   public void setPictureStyle(@PathVariable("deviceId") final long deviceId, @PathVariable("pictureStyle") final String pictureStyle) {
      Device device = this.getDevice(deviceId);
      PictureStyle devicePictureStyle = ValueUtilities.getEnumValue(PictureStyle.class, pictureStyle);
      this.videoService.setPictureStyle(device, devicePictureStyle);
   }

   @GetMapping("/{deviceId}/miscellaneous/bootOnSource")
   public BootOnSourceViewModel getBootOnSource(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      BootOnSource bootOnSource = this.miscellaneousService.getBootOnSource(device);
      return BootOnSourceMapper.toBootOnSourceViewModel(bootOnSource);
   }

   @PutMapping("/{deviceId}/miscellaneous/bootOnSource")
   public void setBootOnSource(@PathVariable("deviceId") final long deviceId, @RequestBody final BootOnSourceViewModel bootOnSourceViewModel) {
      Device device = this.getDevice(deviceId);
      BootOnSource bootOnSource = BootOnSourceMapper.toBootOnSource(bootOnSourceViewModel);
      this.miscellaneousService.setBootOnSource(device, bootOnSource);
   }

   @GetMapping("/{deviceId}/general/clockParameter")
   public ResponseWrapper<String> getClockParameter(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      ClockParameter clockParameter = this.generalService.getClockParameter(device);
      return new ResponseWrapper<>(ClockParameterMapper.toClockParameterString(clockParameter));
   }

   @PutMapping("/{deviceId}/general/clockParameter/{clock}")
   public void setClockParameter(@PathVariable("deviceId") final long deviceId, @PathVariable("clock") final String clock) {
      Device device = this.getDevice(deviceId);
      ClockParameter clockParameter = ClockParameterMapper.toClockParameter(clock);
      this.generalService.setClockParameter(device, clockParameter);
   }

   @GetMapping("/{deviceId}/general/dateParameter")
   public ResponseWrapper<String> getDateParameter(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      DateParameter dateParameter = this.generalService.getDateParameter(device);
      return new ResponseWrapper<>(DateParameterMapper.toDateParameterViewString(dateParameter));
   }

   @PutMapping("/{deviceId}/general/dateParameter/{date}")
   public void setDateParameter(@PathVariable("deviceId") final long deviceId, @PathVariable("date") final String date) {
      Device device = this.getDevice(deviceId);
      DateParameter dateParameter = DateParameterMapper.toDateParameter(date);
      this.generalService.setDateParameter(device, dateParameter);
   }

   @GetMapping("/{deviceId}/general/autoTimeSync")
   public ResponseWrapper<String> getAutoTimeSync(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      AutoTimeSync autoTimeSync = this.generalService.getAutoTimeSync(device);
      return this.getResponseWrapper(autoTimeSync);
   }

   @PutMapping("/{deviceId}/general/autoTimeSync/{autoTimeSync}")
   public void setAutoTimeSync(@PathVariable("deviceId") final long deviceId, @PathVariable("autoTimeSync") final String autoTimeSync) {
      Device device = this.getDevice(deviceId);
      AutoTimeSync deviceAutoTimeSync = ValueUtilities.getEnumValue(AutoTimeSync.class, autoTimeSync);
      this.generalService.setAutoTimeSync(device, deviceAutoTimeSync);
   }

   @GetMapping("/{deviceId}/general/timeZone")
   public ResponseWrapper<String> getTimeZone(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      TimeZone timeZone = this.generalService.getTimeZone(device);
      return this.getResponseWrapper(timeZone);
   }

   @PutMapping("/{deviceId}/general/timeZone/{timeZone}")
   public void setTimeZone(@PathVariable("deviceId") final long deviceId, @PathVariable("timeZone") final String timeZone) {
      Device device = this.getDevice(deviceId);
      TimeZone deviceTimeZone = ValueUtilities.getEnumValue(TimeZone.class, timeZone);
      this.generalService.setTimeZone(device, deviceTimeZone);
   }

   @GetMapping("/{deviceId}/general/autoRestartParameter")
   public AutoRestartParameterViewModel getAutoRestartParameter(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      AutoRestartParameter autoRestartParameter = this.generalService.getAutoRestartParameter(device);
      return AutoRestartParameterMapper.toAutoRestartParameterViewModel(autoRestartParameter);
   }

   @PutMapping("/{deviceId}/general/autoRestartParameter")
   public void setAutoRestartParameter(
      @PathVariable("deviceId") final long deviceId, @RequestBody final AutoRestartParameterViewModel autoRestartParameterViewModel
   ) {
      Device device = this.getDevice(deviceId);
      AutoRestartParameter autoRestartParameter = AutoRestartParameterMapper.toAutoRestartParameter(autoRestartParameterViewModel);
      this.generalService.setAutoRestartParameter(device, autoRestartParameter);
   }

   @GetMapping("/{deviceId}/general/languageOSD")
   public ResponseWrapper<String> getLanguageOSD(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      LanguageOSD languageOSD = this.generalService.getLanguageOSD(device);
      return this.getResponseWrapper(languageOSD);
   }

   @PutMapping("/{deviceId}/general/languageOSD/{languageOSD}")
   public void setLanguageOSD(@PathVariable("deviceId") final long deviceId, @PathVariable("languageOSD") final String languageOSD) {
      Device device = this.getDevice(deviceId);
      LanguageOSD deviceTLanguageOSD = ValueUtilities.getEnumValue(LanguageOSD.class, languageOSD);
      this.generalService.setLanguageOSD(device, deviceTLanguageOSD);
   }

   @GetMapping("/{deviceId}/general/opsSdmSettings")
   public ResponseWrapper<String> getOpsSdmSettings(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      OpsSdmSettings opsSdmSettings = this.generalService.getOpsSdmSettings(device);
      return this.getResponseWrapper(opsSdmSettings);
   }

   @PutMapping("/{deviceId}/general/opsSdmSettings/{opsSdmSettings}")
   public void setOpsSdmSettings(@PathVariable("deviceId") final long deviceId, @PathVariable("opsSdmSettings") final String opsSdmSettings) {
      Device device = this.getDevice(deviceId);
      OpsSdmSettings deviceOpsSdmSettings = ValueUtilities.getEnumValue(OpsSdmSettings.class, opsSdmSettings);
      this.generalService.setOpsSdmSettings(device, deviceOpsSdmSettings);
   }

   @GetMapping("/{deviceId}/general/powerLED")
   public ResponseWrapper<String> getPowerLED(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      PowerLED powerLED = this.generalService.getPowerLED(device);
      return this.getResponseWrapper(powerLED);
   }

   @PutMapping("/{deviceId}/general/powerLED/{powerLED}")
   public void setPowerLED(@PathVariable("deviceId") final long deviceId, @PathVariable("powerLED") final String powerLED) {
      Device device = this.getDevice(deviceId);
      PowerLED devicePowerLED = ValueUtilities.getEnumValue(PowerLED.class, powerLED);
      this.generalService.setPowerLED(device, devicePowerLED);
   }

   @GetMapping("/{deviceId}/general/forceRestartCustomApp")
   public ResponseWrapper<String> getForceRestartCustomApp(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      ForceRestartCustomApp forceRestartCustomApp = this.generalService.getForceRestartCustomApp(device);
      return this.getResponseWrapper(forceRestartCustomApp);
   }

   @PutMapping("/{deviceId}/general/forceRestartCustomApp/{forceRestartCustomApp}")
   public void setForceRestartCustomApp(
      @PathVariable("deviceId") final long deviceId, @PathVariable("forceRestartCustomApp") final String forceRestartCustomApp
   ) {
      Device device = this.getDevice(deviceId);
      ForceRestartCustomApp deviceForceRestartCustomApp = ValueUtilities.getEnumValue(ForceRestartCustomApp.class, forceRestartCustomApp);
      this.generalService.setForceRestartCustomApp(device, deviceForceRestartCustomApp);
   }

   @GetMapping("/{deviceId}/audio/audioSync")
   public ResponseWrapper<String> getAudioSync(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      AudioSync audioSync = this.audioService.getAudioSync(device);
      return this.getResponseWrapper(audioSync);
   }

   @PutMapping("/{deviceId}/audio/audioSync/{audioSync}")
   public void setAudioSync(@PathVariable("deviceId") final long deviceId, @PathVariable("audioSync") final String audioSync) {
      Device device = this.getDevice(deviceId);
      AudioSync deviceAudioSync = ValueUtilities.getEnumValue(AudioSync.class, audioSync);
      this.audioService.setAudioSync(device, deviceAudioSync);
   }

   @GetMapping("/{deviceId}/audio/speakersStatus")
   public ResponseWrapper<String> getSpeakersStatus(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      SpeakersStatus speakersStatus = this.audioService.getSpeakersStatus(device);
      return this.getResponseWrapper(speakersStatus);
   }

   @PutMapping("/{deviceId}/audio/speakersStatus/{speakersStatus}")
   public void setSpeakersStatus(@PathVariable("deviceId") final long deviceId, @PathVariable("speakersStatus") final String speakersStatus) {
      Device device = this.getDevice(deviceId);
      SpeakersStatus deviceSpeakersStatus = ValueUtilities.getEnumValue(SpeakersStatus.class, speakersStatus);
      this.audioService.setSpeakersStatus(device, deviceSpeakersStatus);
   }

   @GetMapping("/{deviceId}/miscellaneous/teamviewerStatus")
   public ResponseWrapper<String> getTeamviewerStatus(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      TeamviewerStatus teamviewerStatus = this.miscellaneousService.getTeamviewerStatus(device);
      return this.getResponseWrapper(teamviewerStatus);
   }

   @PutMapping("/{deviceId}/miscellaneous/teamviewerStatus/{teamviewerStatus}")
   public void setTeamviewerStatus(@PathVariable("deviceId") final long deviceId, @PathVariable("teamviewerStatus") final String teamviewerStatus) {
      Device device = this.getDevice(deviceId);
      TeamviewerStatus deviceTeamviewerStatus = ValueUtilities.getEnumValue(TeamviewerStatus.class, teamviewerStatus);
      this.miscellaneousService.setTeamviewerStatus(device, deviceTeamviewerStatus);
   }

   @GetMapping("/{deviceId}/miscellaneous/rS232Routing")
   public ResponseWrapper<String> getRS232Routing(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      RS232Routing rS232Routing = this.miscellaneousService.getRS232Routing(device);
      return this.getResponseWrapper(rS232Routing);
   }

   @PutMapping("/{deviceId}/miscellaneous/rS232Routing/{rS232Routing}")
   public void setRS232Routing(@PathVariable("deviceId") final long deviceId, @PathVariable("rS232Routing") final String rS232Routing) {
      Device device = this.getDevice(deviceId);
      RS232Routing deviceRS232Routing = ValueUtilities.getEnumValue(RS232Routing.class, rS232Routing);
      this.miscellaneousService.setRS232Routing(device, deviceRS232Routing);
   }

   @GetMapping("/{deviceId}/miscellaneous/sicpSerialPortForwarding")
   public ResponseWrapper<String> getSicpSerialPortForwarding(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      SicpSerialPortForwarding sicpSerialPortForwarding = this.miscellaneousService.getSicpSerialPortForwarding(device);
      return this.getResponseWrapper(sicpSerialPortForwarding);
   }

   @PutMapping("/{deviceId}/miscellaneous/sicpSerialPortForwarding/{sicpSerialPortForwarding}")
   public void setSicpSerialPortForwarding(
      @PathVariable("deviceId") final long deviceId, @PathVariable("sicpSerialPortForwarding") final String sicpSerialPortForwarding
   ) {
      Device device = this.getDevice(deviceId);
      SicpSerialPortForwarding deviceSicpSerialPortForwarding = ValueUtilities.getEnumValue(SicpSerialPortForwarding.class, sicpSerialPortForwarding);
      this.miscellaneousService.setSicpSerialPortForwarding(device, deviceSicpSerialPortForwarding);
   }

   @GetMapping("/{deviceId}/miscellaneous/wOL")
   public ResponseWrapper<String> getWOL(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      WOL wOL = this.miscellaneousService.getWOL(device);
      return this.getResponseWrapper(wOL);
   }

   @PutMapping("/{deviceId}/miscellaneous/wOL/{wOL}")
   public void setWOL(@PathVariable("deviceId") final long deviceId, @PathVariable("wOL") final String wOL) {
      Device device = this.getDevice(deviceId);
      WOL deviceWOL = ValueUtilities.getEnumValue(WOL.class, wOL);
      this.miscellaneousService.setWOL(device, deviceWOL);
   }

   @GetMapping("/{deviceId}/miscellaneous/hdmiOneWire")
   public ResponseWrapper<String> getHdmiOneWire(@PathVariable("deviceId") final long deviceId) {
      Device device = this.getDevice(deviceId);
      HdmiOneWire hdmiOneWire = this.miscellaneousService.getHdmiOneWire(device);
      return this.getResponseWrapper(hdmiOneWire);
   }

   @PutMapping("/{deviceId}/miscellaneous/hdmiOneWire/{hdmiOneWire}")
   public void setHdmiOneWire(@PathVariable("deviceId") final long deviceId, @PathVariable("hdmiOneWire") final String hdmiOneWire) {
      Device device = this.getDevice(deviceId);
      HdmiOneWire deviceHdmiOneWire = ValueUtilities.getEnumValue(HdmiOneWire.class, hdmiOneWire);
      this.miscellaneousService.setHdmiOneWire(device, deviceHdmiOneWire);
   }

   @PutMapping("/{deviceId}/miscellaneous/otaUpdateSet")
   public void setOtaUpdateStatus(@PathVariable("deviceId") final long deviceId, @RequestBody final OtaUpdateSetViewModel otaUpdateSetViewModel) {
      Device device = this.getDevice(deviceId);
      OtaUpdateSet otaUpdateSet = OtaUpdateSetMapper.toOtaUpdateSet(otaUpdateSetViewModel);
      this.miscellaneousService.setOtaUpdateStatus(device, otaUpdateSet);
   }

   @GetMapping("/{deviceId}/miscellaneous/otaUpdateGet/{imageType}")
   public ResponseWrapper<String> getOtaUpdateStatus(@PathVariable("deviceId") final long deviceId, @PathVariable("imageType") final String imageType) {
      Device device = this.getDevice(deviceId);
      OtaImageType deviceImageType = ValueUtilities.getEnumValue(OtaImageType.class, imageType);
      OtaUpdateGet otaUpdateGet = this.miscellaneousService.getOtaUpdateStatus(device, deviceImageType);
      return new ResponseWrapper<>(otaUpdateGet.getStatus().getDescription());
   }

   @GetMapping("/{deviceId}/miscellaneous/otaFwVersion/{imageType}")
   public ResponseWrapper<String> getOtaFwVersion(@PathVariable("deviceId") final long deviceId, @PathVariable("imageType") final String imageType) {
      Device device = this.getDevice(deviceId);
      OtaImageType deviceImageType = ValueUtilities.getEnumValue(OtaImageType.class, imageType);
      StringWrapper fwVersion = this.miscellaneousService.getOtaFwVersion(device, deviceImageType);
      return new ResponseWrapper<>(fwVersion.getValue());
   }
}
