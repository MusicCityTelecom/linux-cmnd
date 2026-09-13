package be.tpvision.smartcontrol.service;

import be.tpvision.smartcontrol.domain.Device;
import be.tpvision.smartcontrol.domain.DeviceListItem;
import be.tpvision.smartcontrol.domain.device_settings.StringWrapper;
import be.tpvision.smartcontrol.domain.device_settings.general.PowerState;
import be.tpvision.smartcontrol.domain.device_settings.input_sources.InputSource;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.Miscellaneous;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.TemperatureSensor;
import be.tpvision.smartcontrol.io.CommandNotAcknowledgedException;
import be.tpvision.smartcontrol.io.CommandNotAvailableException;
import be.tpvision.smartcontrol.io.FetchInfoDataTaskAlreadyRunningException;
import be.tpvision.smartcontrol.io.FetchOverviewDataTaskAlreadyRunningException;
import be.tpvision.smartcontrol.io.ip.sicp.DestinationUnreachableException;
import be.tpvision.smartcontrol.messages.services.device_data_management.ConstructorMessages;
import be.tpvision.smartcontrol.messages.services.device_data_management.FetchInfoDataMessages;
import be.tpvision.smartcontrol.messages.services.device_data_management.FetchOverviewDataMessages;
import be.tpvision.smartcontrol.messages.services.device_data_management.ShouldUpdateMessages;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class DeviceDataManagementServiceImpl implements DeviceDataManagementService {
   private static final Logger logger = LoggerFactory.getLogger(DeviceDataManagementServiceImpl.class);
   private static final AtomicBoolean fetchInfoDataRunning = new AtomicBoolean(false);
   private static final AtomicBoolean fetchOverviewDataRunning = new AtomicBoolean(false);
   private final DeviceService deviceService;
   private final MiscellaneousService miscellaneousService;
   private final GeneralService generalService;
   private final InputSourcesService inputSourcesService;
   private final SystemService systemService;

   @Autowired
   public DeviceDataManagementServiceImpl(
      final DeviceService deviceService,
      final MiscellaneousService miscellaneousService,
      final GeneralService generalService,
      final InputSourcesService inputSourcesService,
      final SystemService systemService
   ) {
      Assert.notNull(deviceService, ConstructorMessages.DEVICE_SERVICE_CAN_NOT_BE_NULL);
      this.deviceService = deviceService;
      Assert.notNull(miscellaneousService, ConstructorMessages.MISCELLANEOUS_SERVICE_CAN_NOT_BE_NULL);
      this.miscellaneousService = miscellaneousService;
      this.generalService = generalService;
      this.inputSourcesService = inputSourcesService;
      this.systemService = systemService;
   }

   private boolean shouldUpdate(final Device device) {
      Assert.state(this.miscellaneousService != null, ShouldUpdateMessages.MISCELLANEOUS_SERVICE_CAN_NOT_BE_NULL);
      Assert.notNull(device, ShouldUpdateMessages.DEVICE_CAN_NOT_BE_NULL);

      try {
         StringWrapper newSerialCode = this.miscellaneousService.getSerialCode(device);
         if (newSerialCode != null) {
            StringWrapper oldSerialCode = device.getSerialCode();
            boolean isOldSerialCodeNull = oldSerialCode == null || oldSerialCode.getValue() == null;
            boolean hasSerialCodeChanged = !Objects.equals(oldSerialCode, newSerialCode);
            if (isOldSerialCodeNull || !hasSerialCodeChanged) {
               return true;
            }
         }
      } catch (CommandNotAvailableException | CommandNotAcknowledgedException e) {
         String message = e.getMessage();
         logger.info(message);
      }

      return false;
   }

   @Override
   public void fetchInfoData() {
      Assert.state(this.deviceService != null, FetchInfoDataMessages.DEVICE_SERVICE_CAN_NOT_BE_NULL);
      Assert.state(this.systemService != null, FetchInfoDataMessages.SYSTEM_SERVICE_CAN_NOT_BE_NULL);
      Assert.state(this.miscellaneousService != null, FetchInfoDataMessages.MISCELLANEOUS_SERVICE_CAN_NOT_BE_NULL);
      boolean isStatusChanged = false;

      try {
         isStatusChanged = fetchInfoDataRunning.compareAndSet(false, true);
         if (!isStatusChanged) {
            throw new FetchInfoDataTaskAlreadyRunningException();
         }

         Set<DeviceListItem> deviceListItemSet = this.deviceService.getDevices();
         this.fetchInfoData(deviceListItemSet);
      } finally {
         if (isStatusChanged) {
            fetchInfoDataRunning.compareAndSet(true, false);
         }
      }
   }

   private void fetchInfoData(Set<DeviceListItem> deviceListItemSet) {
      for (DeviceListItem deviceListItem : deviceListItemSet) {
         if (deviceListItem instanceof Device) {
            Device device = (Device)deviceListItem;

            try {
               boolean shouldUpdate = this.shouldUpdate(device);
               if (shouldUpdate) {
                  this.fetchInfoData(device);
                  this.deviceService.updateDevice(device);
               }
            } catch (DestinationUnreachableException destinationUnreachableException) {
               String message = destinationUnreachableException.getMessage();
               logger.info(message);
            }
         }
      }
   }

   private void fetchInfoData(Device device) {
      this.fetchSicpVersion(device);
      this.fetchPlatformLabel(device);
      this.fetchPlatformVersion(device);
      this.fetchModelNumber(device);
      this.fetchFirmwareVersionScaler(device);
      this.fetchBuildDate(device);
      this.fetchFirmwareVersionAndroid(device);
      this.fetchSerialCode(device);
      this.fetchMiscellaneous(device);
      this.fetchSupportSources(device);
   }

   private void fetchSupportSources(Device device) {
      try {
         StringWrapper numberOfInputSources = this.generalService.getNumberOfInputSources(device);
         if (numberOfInputSources != null) {
            device.setSupportSources(numberOfInputSources);
         }
      } catch (Exception e) {
         String message = e.getMessage();
         logger.info(message);
      }
   }

   private void fetchSicpVersion(Device device) {
      try {
         StringWrapper sicpVersion = this.systemService.getSICPVersion(device);
         if (sicpVersion != null) {
            device.setSicpVersion(sicpVersion);
         }
      } catch (CommandNotAvailableException | CommandNotAcknowledgedException e) {
         String message = e.getMessage();
         logger.info(message);
      }
   }

   private void fetchPlatformLabel(Device device) {
      try {
         StringWrapper platformLabel = this.systemService.getPlatformLabel(device);
         if (platformLabel != null) {
            device.setPlatformLabel(platformLabel);
         }
      } catch (CommandNotAvailableException | CommandNotAcknowledgedException e) {
         String message = e.getMessage();
         logger.info(message);
      }
   }

   private void fetchPlatformVersion(Device device) {
      try {
         StringWrapper platformVersion = this.systemService.getPlatformVersion(device);
         if (platformVersion != null) {
            device.setPlatformVersion(platformVersion);
         }
      } catch (CommandNotAvailableException | CommandNotAcknowledgedException e) {
         String message = e.getMessage();
         logger.info(message);
      }
   }

   private void fetchModelNumber(Device device) {
      try {
         StringWrapper modelNumber = this.systemService.getModelNumber(device);
         if (modelNumber != null) {
            device.setModelNumber(modelNumber);
         }
      } catch (CommandNotAvailableException | CommandNotAcknowledgedException e) {
         String message = e.getMessage();
         logger.info(message);
      }
   }

   private void fetchFirmwareVersionScaler(Device device) {
      try {
         StringWrapper firmwareVersion = this.systemService.getFirmwareVersion(device);
         if (firmwareVersion != null) {
            device.setFirmwareVersion(firmwareVersion);
         }
      } catch (CommandNotAvailableException | CommandNotAcknowledgedException e) {
         String message = e.getMessage();
         logger.info(message);
      }
   }

   private void fetchBuildDate(Device device) {
      try {
         StringWrapper buildDate = this.systemService.getBuildDate(device);
         if (buildDate != null) {
            device.setBuildDate(buildDate);
         }
      } catch (CommandNotAvailableException | CommandNotAcknowledgedException e) {
         String message = e.getMessage();
         logger.info(message);
      }
   }

   private void fetchFirmwareVersionAndroid(Device device) {
      try {
         StringWrapper firmwareVersion = this.systemService.getFirmwareVersionAndroid(device);
         if (firmwareVersion != null) {
            device.setFirmwareVersionAndroid(firmwareVersion);
         }
      } catch (CommandNotAvailableException | CommandNotAcknowledgedException | UnsupportedOperationException e) {
         String message = e.getMessage();
         logger.info(message);
      }
   }

   private void fetchSerialCode(Device device) {
      try {
         StringWrapper newSerialCode = this.miscellaneousService.getSerialCode(device);
         if (newSerialCode != null) {
            device.setSerialCode(newSerialCode);
         }
      } catch (CommandNotAvailableException | CommandNotAcknowledgedException e) {
         String message = e.getMessage();
         logger.info(message);
      }
   }

   private void fetchMiscellaneous(Device device) {
      try {
         Miscellaneous miscellaneous = this.miscellaneousService.getMiscellaneous(device);
         if (miscellaneous != null) {
            device.setMiscellaneous(miscellaneous);
         }
      } catch (CommandNotAvailableException | CommandNotAcknowledgedException e) {
         String message = e.getMessage();
         logger.info(message);
      }
   }

   private void clearOverviewData(final Device device) {
      Assert.notNull(device, "Device can not be null.");
      device.setTemperature(null);
      device.setPowerState(null);
      device.setInputSource(null);
   }

   @Override
   public void fetchOverviewData() {
      Assert.state(this.deviceService != null, FetchOverviewDataMessages.DEVICE_SERVICE_CAN_NOT_BE_NULL);
      Assert.state(this.generalService != null, FetchOverviewDataMessages.GENERAL_SERVICE_CAN_NOT_BE_NULL);
      Assert.state(this.miscellaneousService != null, FetchOverviewDataMessages.MISCELLANEOUS_SERVICE_CAN_NOT_BE_NULL);
      Assert.state(this.inputSourcesService != null, FetchOverviewDataMessages.INPUT_SOURCES_SERVICE_CAN_NOT_BE_NULL);
      boolean isStatusChanged = false;

      try {
         isStatusChanged = fetchOverviewDataRunning.compareAndSet(false, true);
         if (!isStatusChanged) {
            throw new FetchOverviewDataTaskAlreadyRunningException();
         }

         Set<DeviceListItem> deviceListItemSet = this.deviceService.getDevices();
         this.fetchOverviewData(deviceListItemSet);
      } finally {
         if (isStatusChanged) {
            fetchOverviewDataRunning.compareAndSet(true, false);
         }
      }
   }

   private void fetchOverviewData(Set<DeviceListItem> deviceListItemSet) {
      for (DeviceListItem deviceListItem : deviceListItemSet) {
         if (deviceListItem instanceof Device) {
            Device device = (Device)deviceListItem;
            this.clearOverviewData(device);

            try {
               boolean shouldUpdate = this.shouldUpdate(device);
               if (shouldUpdate) {
                  this.fetchOverviewData(device);
               }
            } catch (DestinationUnreachableException destinationUnreachableException) {
               String message = destinationUnreachableException.getMessage();
               logger.info(message);
            } finally {
               this.deviceService.updateDevice(device);
            }
         }
      }
   }

   private void fetchOverviewData(Device device) {
      this.fetchTemperatureSensor(device);
      this.fetchPowerState(device);
      this.fetchInputSource(device);
   }

   private void fetchTemperatureSensor(Device device) {
      try {
         TemperatureSensor temperatureSensor = this.miscellaneousService.getTemperatureSensor(device);
         device.setTemperature(temperatureSensor);
      } catch (CommandNotAvailableException | CommandNotAcknowledgedException e) {
         String message = e.getMessage();
         logger.info(message);
      }
   }

   private void fetchPowerState(Device device) {
      try {
         PowerState powerState = this.generalService.getPowerState(device);
         device.setPowerState(powerState);
      } catch (CommandNotAvailableException | CommandNotAcknowledgedException e) {
         String message = e.getMessage();
         logger.info(message);
      }
   }

   private void fetchInputSource(Device device) {
      try {
         InputSource inputSource = this.inputSourcesService.getInputSource(device);
         device.setInputSource(inputSource);
      } catch (CommandNotAvailableException | CommandNotAcknowledgedException e) {
         String message = e.getMessage();
         logger.info(message);
      }
   }

   @Override
   public boolean isFetchInfoDataRunning() {
      return fetchInfoDataRunning.get();
   }

   @Override
   public boolean isFetchOverviewDataRunning() {
      return fetchOverviewDataRunning.get();
   }
}
