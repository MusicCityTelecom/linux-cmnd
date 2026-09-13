package be.tpvision.smartcontrol.service;

import be.tpvision.smartcontrol.domain.Device;
import be.tpvision.smartcontrol.domain.device_settings.input_sources.AutoSignalDetecting;
import be.tpvision.smartcontrol.domain.device_settings.input_sources.Failovers;
import be.tpvision.smartcontrol.domain.device_settings.input_sources.InputSource;
import be.tpvision.smartcontrol.messages.services.input_sources.ConstructorMessages;
import be.tpvision.smartcontrol.messages.services.input_sources.SetAutoSignalDetectingMessages;
import be.tpvision.smartcontrol.messages.services.input_sources.SetFailoversMessages;
import be.tpvision.smartcontrol.messages.services.input_sources.SetInputSourceMessages;
import be.tpvision.smartcontrol.protocol.sicp.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
@Profile("production")
public class InputSourcesServiceImpl implements InputSourcesService {
   private final CommandService commandService;

   @Autowired
   InputSourcesServiceImpl(final CommandService commandService) {
      Assert.notNull(commandService, ConstructorMessages.COMMAND_SERVICE_CAN_NOT_BE_NULL);
      this.commandService = commandService;
   }

   @Override
   public InputSource getInputSource(final Device device) {
      return device == null ? null : this.commandService.send(device, Command.Type.GET, Command.Setting.INPUT_SOURCE, InputSource.class);
   }

   @Override
   public void setInputSource(final Device device, final InputSource inputSource) {
      Assert.notNull(device, SetInputSourceMessages.DEVICE_CAN_NOT_BE_NULL);
      Assert.notNull(inputSource, SetInputSourceMessages.INPUT_SOURCE_CAN_NOT_BE_NULL);
      this.commandService.send(device, Command.Type.SET, Command.Setting.INPUT_SOURCE, inputSource);
   }

   @Override
   public AutoSignalDetecting getAutoSignalDetecting(final Device device) {
      return device == null ? null : this.commandService.send(device, Command.Type.GET, Command.Setting.AUTO_SIGNAL_DETECTING, AutoSignalDetecting.class);
   }

   @Override
   public void setAutoSignalDetecting(final Device device, final AutoSignalDetecting autoSignalDetecting) {
      Assert.notNull(device, SetAutoSignalDetectingMessages.DEVICE_CAN_NOT_BE_NULL);
      Assert.notNull(autoSignalDetecting, SetAutoSignalDetectingMessages.AUTO_SIGNAL_DETECTING_CAN_NOT_BE_NULL);
      this.commandService.send(device, Command.Type.SET, Command.Setting.AUTO_SIGNAL_DETECTING, autoSignalDetecting);
   }

   @Override
   public Failovers getFailovers(final Device device) {
      return device == null ? null : this.commandService.send(device, Command.Type.GET, Command.Setting.FAILOVERS, Failovers.class);
   }

   @Override
   public void setFailovers(final Device device, final Failovers failovers) {
      Assert.notNull(device, SetFailoversMessages.DEVICE_CAN_NOT_BE_NULL);
      Assert.notNull(failovers, SetFailoversMessages.FAILOVERS_CAN_NOT_BE_NULL);
      this.commandService.send(device, Command.Type.SET, Command.Setting.FAILOVERS, failovers);
   }
}
