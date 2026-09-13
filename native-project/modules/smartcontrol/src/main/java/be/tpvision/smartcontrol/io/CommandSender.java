package be.tpvision.smartcontrol.io;

import be.tpvision.smartcontrol.protocol.Request;
import be.tpvision.smartcontrol.protocol.Response;

public interface CommandSender<T extends Destination> {
   Response send(Request command, T destination);

   <C> C send(Request command, T destination, Class<C> responseClass);
}
