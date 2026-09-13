package be.tpvision.smartcontrol.rest.mappers.device;

import be.tpvision.smartcontrol.io.ip.IpDestination;
import be.tpvision.smartcontrol.messages.mappers.device.ip_destination.ToIpDestinationMessages;
import be.tpvision.smartcontrol.messages.mappers.device.ip_destination.ToIpDestinationViewModelMessages;
import be.tpvision.smartcontrol.rest.view_models.device.IpDestinationViewModel;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import org.springframework.util.Assert;

public class IpDestinationMapper {
   private IpDestinationMapper() {
   }

   public static IpDestinationViewModel toIpDestinationViewModel(final IpDestination ipDestination) {
      Assert.notNull(ipDestination, ToIpDestinationViewModelMessages.IP_DESTINATION_CAN_NOT_BE_NULL);
      InetSocketAddress inetSocketAddress = ipDestination.getAddress();
      Assert.state(inetSocketAddress != null, ToIpDestinationViewModelMessages.INET_SOCKET_ADDRESS_CAN_NOT_BE_NULL);
      InetAddress inetAddress = inetSocketAddress.getAddress();
      Assert.state(inetAddress != null, ToIpDestinationViewModelMessages.INET_ADDRESS_CAN_NOT_BE_NULL);
      String ip = inetAddress.getHostAddress();
      int port = inetSocketAddress.getPort();
      int controlId = ipDestination.getControlId();
      int groupId = ipDestination.getGroupId();
      return new IpDestinationViewModel(ip, port, controlId, groupId);
   }

   public static IpDestination toIpDestination(final IpDestinationViewModel ipDestinationViewModel) {
      Assert.notNull(ipDestinationViewModel, ToIpDestinationMessages.IP_DESTINATION_CAN_NOT_BE_NULL);
      String ip = ipDestinationViewModel.getIp();
      Assert.state(ip != null, ToIpDestinationMessages.IP_CAN_NOT_BE_NULL);
      int port = ipDestinationViewModel.getPort();
      InetSocketAddress inetSocketAddress = new InetSocketAddress(ip, port);
      int controlId = ipDestinationViewModel.getControlId();
      int groupId = ipDestinationViewModel.getGroupId();
      return new IpDestination(inetSocketAddress, controlId, groupId);
   }
}
