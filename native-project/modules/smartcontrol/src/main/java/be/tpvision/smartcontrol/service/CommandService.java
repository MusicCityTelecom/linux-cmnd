package be.tpvision.smartcontrol.service;

import be.tpvision.smartcontrol.domain.Device;
import be.tpvision.smartcontrol.domain.device_settings.DeviceSetting;
import be.tpvision.smartcontrol.protocol.sicp.Command;

public interface CommandService {
   void send(Device device, Command.Type type, Command.Setting setting);

   default <T> T send(final Device device, Command.Type type, Command.Setting setting, final Class<T> responseClass) {
      return this.send(device, type, setting, null, responseClass);
   }

   void send(Device device, Command.Type type, Command.Setting setting, DeviceSetting deviceSetting);

   <T> T send(Device device, Command.Type type, Command.Setting setting, DeviceSetting deviceSetting, Class<T> responseClass);
}
