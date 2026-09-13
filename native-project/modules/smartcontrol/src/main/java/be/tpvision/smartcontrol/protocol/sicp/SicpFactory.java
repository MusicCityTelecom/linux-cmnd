package be.tpvision.smartcontrol.protocol.sicp;

import be.tpvision.smartcontrol.io.CommandSender;
import be.tpvision.smartcontrol.io.ip.IpDestination;

public interface SicpFactory {
   SicpCommandFactory getSicpCommandFactory();

   CommandSender<IpDestination> getCommandSender();
}
