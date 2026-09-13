package be.tpvision.smartcontrol.messages.io.ip.sicp.netty_command_sender;

import be.tpvision.smartcontrol.messages.Messages;
import org.springframework.util.Assert;

public class SendMessages {
   private SendMessages() {
   }

   public static String getDestinationUnreachableMessage(final String ip, final int port) {
      String ipCanNotBeNullMessage = Messages.getCanNotBeNullMessage("IP");
      Assert.notNull(ip, ipCanNotBeNullMessage);
      return String.format("Destination %s:%d unreachable.", ip, port);
   }
}
