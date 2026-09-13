package be.tpvision.smartcontrol.protocol.sicp;

import be.tpvision.smartcontrol.codecs.sicp.Codec;
import be.tpvision.smartcontrol.domain.device_settings.DeviceSetting;
import be.tpvision.smartcontrol.io.Destination;

public interface SicpCommandFactory {
   SicpVersion getSicpVersion();

   Commands getCommands();

   Codec<? extends DeviceSetting> getDecoder(byte settingByte);

   SicpCommand getSicpCommand(int controlId, int groupId, Command.Type type, Command.Setting setting);

   SicpCommand getSicpCommand(int controlId, int groupId, Command.Type type, Command.Setting setting, DeviceSetting deviceSetting);

   SicpCommand getSicpCommand(Destination destination, Command.Type type, Command.Setting setting);

   SicpCommand getSicpCommand(Destination destination, Command.Type type, Command.Setting setting, DeviceSetting deviceSetting);
}
