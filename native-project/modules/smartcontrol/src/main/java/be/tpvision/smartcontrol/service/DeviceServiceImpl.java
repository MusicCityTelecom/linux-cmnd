package be.tpvision.smartcontrol.service;

import be.tpvision.smartcontrol.domain.Content;
import be.tpvision.smartcontrol.domain.Device;
import be.tpvision.smartcontrol.domain.DeviceListItem;
import be.tpvision.smartcontrol.domain.FtpSettings;
import be.tpvision.smartcontrol.domain.Hardware;
import be.tpvision.smartcontrol.domain.Settings;
import be.tpvision.smartcontrol.domain.device_settings.StringWrapper;
import be.tpvision.smartcontrol.io.ip.IpDestination;
import be.tpvision.smartcontrol.io.ip.Scanner;
import be.tpvision.smartcontrol.messages.controllers.device.GetDeviceInfoMessages;
import be.tpvision.smartcontrol.messages.services.device.AddDeviceMessages;
import be.tpvision.smartcontrol.messages.services.device.DeleteDeviceMessages;
import be.tpvision.smartcontrol.messages.services.device.DetectDevicesMessages;
import be.tpvision.smartcontrol.messages.services.device.EncodeFtpPasswordMessages;
import be.tpvision.smartcontrol.messages.services.device.GetDeviceBySerialCodeMessages;
import be.tpvision.smartcontrol.messages.services.device.GetDeviceMessages;
import be.tpvision.smartcontrol.messages.services.device.GetDevicesMessages;
import be.tpvision.smartcontrol.messages.services.device.GetDevicesOrderedByMessages;
import be.tpvision.smartcontrol.messages.services.device.GetTimeoutMessages;
import be.tpvision.smartcontrol.messages.services.device.UpdateDeviceMessages;
import be.tpvision.smartcontrol.repository.DeviceRepository;
import be.tpvision.smartcontrol.repository.OrderDirection;
import be.tpvision.smartcontrol.repository.SettingsRepository;
import be.tpvision.smartcontrol.repository.order_fields.OrderField;
import be.tpvision.smartcontrol.rest.mappers.StringWrapperMapper;
import be.tpvision.smartcontrol.service.exceptions.DeviceAlreadyExistsException;
import be.tpvision.smartcontrol.service.exceptions.SerialCodeAlreadyExistsException;
import be.tpvision.smartcontrol.util.SecurityUtilities;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.net.util.SubnetUtils;
import org.apache.commons.net.util.SubnetUtils.SubnetInfo;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class DeviceServiceImpl implements DeviceService {
   private static final Logger logger = LoggerFactory.getLogger(DeviceServiceImpl.class);
   private final DeviceRepository deviceRepository;
   private final SettingsRepository settingsRepository;
   private HardwareService hardwareService;
   private ContentService contentService;
   private MetricsService metricsService;
   private Scanner scanner;

   @Autowired
   public DeviceServiceImpl(
      final DeviceRepository deviceRepository,
      final SettingsRepository settingsRepository,
      final HardwareService hardwareService,
      final ContentService contentService,
      final MetricsService metricsService
   ) {
      this.deviceRepository = deviceRepository;
      this.settingsRepository = settingsRepository;
      this.hardwareService = hardwareService;
      this.contentService = contentService;
      this.metricsService = metricsService;
   }

   @Autowired
   public void setScanner(final Scanner scanner) {
      this.scanner = scanner;
   }

   @Override
   public Set<DeviceListItem> getDevices() {
      Assert.state(this.deviceRepository != null, GetDevicesMessages.DEVICE_REPOSITORY_CAN_NOT_BE_NULL);
      Set<Device> deviceSet = this.getDevicesOrderedBy(be.tpvision.smartcontrol.repository.order_fields.device.OrderField.ID, OrderDirection.ASC);
      return new HashSet<>(deviceSet);
   }

   @Override
   public Set<DeviceListItem> detectDevices() {
      Assert.state(this.deviceRepository != null, DetectDevicesMessages.DEVICE_REPOSITORY_CAN_NOT_BE_NULL);
      Set<DeviceListItem> deviceListItems = new HashSet<>();
      Set<? extends DeviceListItem> deviceSet = this.getDevicesOrderedBy(
         be.tpvision.smartcontrol.repository.order_fields.device.OrderField.ID, OrderDirection.ASC
      );
      Assert.state(this.scanner != null, DetectDevicesMessages.SCANNER_CAN_NOT_BE_NULL);
      int port = 5000;
      int timeoutInMilliseconds = this.getTimeout();

      try {
         Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
         if (networkInterfaces == null || !networkInterfaces.hasMoreElements()) {
            return deviceListItems;
         }

         while (networkInterfaces.hasMoreElements()) {
            NetworkInterface networkInterface = networkInterfaces.nextElement();
            if (networkInterface.isUp()) {
               List<InterfaceAddress> interfaceAddresses = networkInterface.getInterfaceAddresses();
               if (interfaceAddresses != null && !interfaceAddresses.isEmpty()) {
                  for (InterfaceAddress interfaceAddress : interfaceAddresses) {
                     if (interfaceAddress != null) {
                        InetAddress inetAddress = interfaceAddress.getAddress();
                        if (inetAddress != null && !(inetAddress instanceof Inet6Address)) {
                           String hostAddress = inetAddress.getHostAddress();
                           short networkPrefixLength = interfaceAddress.getNetworkPrefixLength();
                           String subnet = hostAddress + "/" + networkPrefixLength;
                           SubnetUtils subnetUtils = new SubnetUtils(subnet);
                           SubnetInfo subnetInfo = subnetUtils.getInfo();
                           String networkAddress = subnetInfo.getNetworkAddress();
                           String scannerInput = networkAddress.substring(0, networkAddress.length() - 2);
                           List<Device> interfaceDevices = this.scanner.scan(scannerInput, 5000, timeoutInMilliseconds);
                           if (interfaceDevices != null && !interfaceDevices.isEmpty()) {
                              interfaceDevices.stream().filter(deviceListItem -> !deviceSet.contains(deviceListItem)).forEach(deviceListItems::add);
                           }
                        }
                     }
                  }
               }
            }
         }
      } catch (SocketException e) {
         String message = e.getMessage();
         logger.error(message, e);
      }

      return deviceListItems;
   }

   private int getTimeout() {
      Assert.state(this.settingsRepository != null, GetTimeoutMessages.SETTINGS_REPOSITORY_CAN_NOT_BE_NULL);
      Settings settings = this.settingsRepository.getSettings();
      return settings.getDetectDevicesTimeoutInMilliseconds();
   }

   @Override
   public Set<Device> getDevicesOrderedBy(final OrderField<? super Device> orderField, final OrderDirection orderDirection) {
      Assert.notNull(orderField, GetDevicesOrderedByMessages.ORDER_FIELD_CAN_NOT_BE_NULL);
      Assert.notNull(orderDirection, GetDevicesOrderedByMessages.ORDER_DIRECTION_CAN_NOT_BE_NULL);
      List<Device> deviceList = this.deviceRepository.getAllOrderedBy(orderField, orderDirection);
      return deviceList == null ? Collections.emptySet() : new LinkedHashSet<>(deviceList);
   }

   @Override
   public Device getDevice(final long id) {
      Assert.state(this.deviceRepository != null, GetDeviceMessages.DEVICE_REPOSITORY_CAN_NOT_BE_NULL);
      return this.deviceRepository.getById(id);
   }

   @Override
   public Device getDeviceBySerialCode(final String serialCode) {
      Assert.state(this.deviceRepository != null, GetDeviceBySerialCodeMessages.DEVICE_REPOSITORY_CAN_NOT_BE_NULL);
      Assert.notNull(serialCode, GetDeviceBySerialCodeMessages.SERIAL_CODE_CAN_NOT_BE_NULL);
      return this.deviceRepository.getBySerialCode(serialCode);
   }

   private void encodeFtpPassword(final Device device) {
      Assert.notNull(device, EncodeFtpPasswordMessages.DEVICE_CAN_NOT_BE_NULL);
      FtpSettings ftpSettings = device.getFtpSettings();
      if (ftpSettings != null) {
         String password = ftpSettings.getPassword();
         if (password != null) {
            String encodedPassword = SecurityUtilities.encrypt(password);
            Assert.state(encodedPassword != null, EncodeFtpPasswordMessages.ENCODED_PASSWORD_CAN_NOT_BE_NULL);
            ftpSettings.setPassword(encodedPassword);
         }
      }
   }

   @Transactional
   @Override
   public void addDevice(final Device device) {
      Assert.notNull(device, AddDeviceMessages.DEVICE_CAN_NOT_BE_NULL);
      this.addDevice(device, false);
   }

   @Transactional
   @Override
   public void addDevice(final Device device, boolean encodeFtpPassword) {
      Assert.state(this.deviceRepository != null, AddDeviceMessages.DEVICE_REPOSITORY_CAN_NOT_BE_NULL);
      Assert.notNull(device, AddDeviceMessages.DEVICE_CAN_NOT_BE_NULL);
      if (encodeFtpPassword) {
         this.encodeFtpPassword(device);
      }

      this.checkIfSerialCodeAlreadyExists(device);
      this.persistDevice(device);
      this.logOneDevice(device);
   }

   private void checkIfSerialCodeAlreadyExists(Device device) {
      StringWrapper serialCode = device.getSerialCode();
      if (serialCode != null) {
         String serialCodeValue = serialCode.getValue();
         if (serialCodeValue != null) {
            Device existingDevice = this.deviceRepository.getBySerialCode(serialCodeValue);
            if (existingDevice != null) {
               String message = String.format("A device with serial code %s already exists.", serialCodeValue);
               throw new SerialCodeAlreadyExistsException(message);
            }
         }
      }
   }

   private void persistDevice(Device device) {
      try {
         this.deviceRepository.persist(device);
      } catch (DataIntegrityViolationException e) {
         String message = this.getDeviceAlreadyExistsMessage(device);
         throw new DeviceAlreadyExistsException(message);
      }
   }

   private String getDeviceAlreadyExistsMessage(Device device) {
      IpDestination ipDestination = device.getAddress();
      InetSocketAddress inetSocketAddress = ipDestination.getAddress();
      InetAddress inetAddress = inetSocketAddress.getAddress();
      String ip = inetAddress.getHostAddress();
      int port = inetSocketAddress.getPort();
      int controlId = ipDestination.getControlId();
      return String.format("Device %s:%d with control id %d already exists.", ip, port, controlId);
   }

   @Transactional
   @Override
   public void updateDevice(final Device device) {
      Assert.notNull(device, UpdateDeviceMessages.DEVICE_CAN_NOT_BE_NULL);
      this.updateDevice(device, false);
   }

   @Transactional
   @Override
   public void updateDevice(final Device device, final boolean encodeFtpPassword) {
      Assert.state(this.deviceRepository != null, UpdateDeviceMessages.DEVICE_REPOSITORY_CAN_NOT_BE_NULL);
      Assert.notNull(device, UpdateDeviceMessages.DEVICE_CAN_NOT_BE_NULL);
      if (encodeFtpPassword) {
         this.encodeFtpPassword(device);
      }

      if (device.isNeedWriteMetricsLog()) {
         this.logOneDevice(device);
      }

      this.deviceRepository.merge(device);
   }

   @Transactional
   @Override
   public void deleteDevice(final Device device) {
      Assert.state(this.deviceRepository != null, DeleteDeviceMessages.DEVICE_REPOSITORY_CAN_NOT_BE_NULL);
      Assert.notNull(device, DeleteDeviceMessages.DEVICE_CAN_NOT_BE_NULL);
      this.deviceRepository.delete(device);
   }

   @Transactional
   @Override
   public void deleteDevice(final long id) {
      Assert.state(this.deviceRepository != null, DeleteDeviceMessages.DEVICE_REPOSITORY_CAN_NOT_BE_NULL);
      this.deviceRepository.deleteById(id);
   }

   @Override
   public void logAllDevices() {
      for (Device device : this.getDevicesOrderedBy(be.tpvision.smartcontrol.repository.order_fields.device.OrderField.ID, OrderDirection.ASC)) {
         this.logOneDevice(device);
      }
   }

   @Override
   public void logOneDevice(Device device) {
      if (device.getSerialCode() != null) {
         JSONObject jsonObject = new JSONObject();
         jsonObject.put("device_id", DigestUtils.sha256Hex(device.getSerialCode().getValue().getBytes()));
         jsonObject.put("pd_platform", this.fixJsonValue(device.getPlatformLabel()));
         jsonObject.put("pd_platform_version", this.fixJsonValue(device.getPlatformVersion()));
         jsonObject.put("pd_model", this.fixJsonValue(device.getModelNumber()));
         jsonObject.put("current_power_status", this.fixJsonValue(device.getPowerState()));
         jsonObject.put("current_input_source", device.getInputSource() != null ? this.fixJsonValue(device.getInputSource().getSourceType()) : "");
         jsonObject.put("current_firmware", this.fixJsonValue(device.getFirmwareVersion()));
         jsonObject.put("current_android_firmware", this.fixJsonValue(device.getFirmwareVersionAndroid()));
         jsonObject.put("current_content", this.getContentTitleByDevice(device));
         if (this.metricsService != null) {
            this.metricsService.writeMetricsLog(jsonObject);
         }
      }
   }

   private String fixJsonValue(Object value) {
      if (value == null) {
         return "";
      } else {
         return value instanceof StringWrapper ? ((StringWrapper)value).getValue() : String.valueOf(value);
      }
   }

   private String getContentTitleByDevice(Device device) {
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

      return contentTitle;
   }
}
