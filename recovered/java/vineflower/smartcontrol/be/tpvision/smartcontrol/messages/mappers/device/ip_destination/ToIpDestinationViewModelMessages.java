package be.tpvision.smartcontrol.messages.mappers.device.ip_destination;

import be.tpvision.smartcontrol.messages.Messages;

public class ToIpDestinationViewModelMessages {
   public static final String IP_DESTINATION_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("IP destination");
   public static final String INET_SOCKET_ADDRESS_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Inet socket address");
   public static final String INET_ADDRESS_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Inet address");

   private ToIpDestinationViewModelMessages() {
   }
}
