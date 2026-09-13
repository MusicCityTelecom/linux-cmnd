package be.tpvision.smartcontrol.service;

import be.tpvision.smartcontrol.domain.Device;
import be.tpvision.smartcontrol.domain.device_settings.IntWrapper;
import be.tpvision.smartcontrol.domain.device_settings.scheduling.scheduling_parameters.Page;
import be.tpvision.smartcontrol.messages.services.scheduling.scheduling_parameters.ConstructorMessages;
import be.tpvision.smartcontrol.messages.services.scheduling.scheduling_parameters.SetSchedulingParametersMessages;
import be.tpvision.smartcontrol.protocol.sicp.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class SchedulingServiceImpl implements SchedulingService {
   private final CommandService commandService;

   @Autowired
   SchedulingServiceImpl(final CommandService commandService) {
      Assert.notNull(commandService, ConstructorMessages.COMMAND_SERVICE_CAN_NOT_BE_NULL);
      this.commandService = commandService;
   }

   @Override
   public Page getSchedulingParametersPage(final Device device, final IntWrapper pageNumber) {
      return device == null ? null : this.commandService.send(device, Command.Type.GET, Command.Setting.SCHEDULING_PARAMETERS, pageNumber, Page.class);
   }

   @Override
   public void setSchedulingParametersPage(final Device device, final Page page) {
      Assert.notNull(device, SetSchedulingParametersMessages.DEVICE_CAN_NOT_BE_NULL);
      Assert.notNull(page, SetSchedulingParametersMessages.PAGE_CAN_NOT_BE_NULL);
      this.commandService.send(device, Command.Type.SET, Command.Setting.SCHEDULING_PARAMETERS, page);
   }
}
