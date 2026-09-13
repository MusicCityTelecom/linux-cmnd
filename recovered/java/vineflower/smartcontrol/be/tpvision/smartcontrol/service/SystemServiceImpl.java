package be.tpvision.smartcontrol.service;

import be.tpvision.smartcontrol.domain.Device;
import be.tpvision.smartcontrol.domain.device_settings.StringWrapper;
import be.tpvision.smartcontrol.domain.device_settings.system.ModelNumberFirmwareVersionBuildDateInfo;
import be.tpvision.smartcontrol.domain.device_settings.system.SicpAndPlatformInfo;
import be.tpvision.smartcontrol.messages.services.system.ConstructorMessages;
import be.tpvision.smartcontrol.protocol.sicp.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
@Profile("production")
public class SystemServiceImpl implements SystemService {
   private final CommandService commandService;

   @Autowired
   public SystemServiceImpl(final CommandService commandService) {
      Assert.notNull(commandService, ConstructorMessages.COMMAND_SERVICE_CAN_NOT_BE_NULL);
      this.commandService = commandService;
   }

   private StringWrapper send(final Device device, final SicpAndPlatformInfo sicpAndPlatformInfo) {
      return device != null && sicpAndPlatformInfo != null
         ? this.commandService.send(device, Command.Type.GET, Command.Setting.SICP_VERSION_AND_PLATFORM_INFO, sicpAndPlatformInfo, StringWrapper.class)
         : null;
   }

   @Override
   public StringWrapper getSICPVersion(final Device device) {
      return device == null ? null : this.send(device, SicpAndPlatformInfo.SICP_VERSION);
   }

   @Override
   public StringWrapper getPlatformLabel(final Device device) {
      return device == null ? null : this.send(device, SicpAndPlatformInfo.PLATFORM_LABEL);
   }

   @Override
   public StringWrapper getPlatformVersion(final Device device) {
      return device == null ? null : this.send(device, SicpAndPlatformInfo.PLATFORM_VERSION);
   }

   private StringWrapper send(final Device device, final ModelNumberFirmwareVersionBuildDateInfo modelNumberFirmwareVersionBuildDateInfo) {
      return device != null && modelNumberFirmwareVersionBuildDateInfo != null
         ? this.commandService
            .send(
               device, Command.Type.GET, Command.Setting.MODEL_NUMBER_FIRMWARE_VERSION_BUILD_DATE, modelNumberFirmwareVersionBuildDateInfo, StringWrapper.class
            )
         : null;
   }

   @Override
   public StringWrapper getModelNumber(final Device device) {
      return device == null ? null : this.send(device, ModelNumberFirmwareVersionBuildDateInfo.MODEL_NUMBER);
   }

   @Override
   public StringWrapper getFirmwareVersion(final Device device) {
      return device == null ? null : this.send(device, ModelNumberFirmwareVersionBuildDateInfo.FIRMWARE_VERSION);
   }

   @Override
   public StringWrapper getBuildDate(final Device device) {
      return device == null ? null : this.send(device, ModelNumberFirmwareVersionBuildDateInfo.BUILD_DATE);
   }

   @Override
   public StringWrapper getFirmwareVersionAndroid(Device device) {
      return device == null ? null : this.send(device, ModelNumberFirmwareVersionBuildDateInfo.FIRMWARE_VERSION_ANDROID);
   }
}
