package be.tpvision.smartcontrol.domain.comparators;

import be.tpvision.smartcontrol.io.Destination;
import be.tpvision.smartcontrol.io.ip.IpDestination;
import java.net.InetSocketAddress;
import java.util.Comparator;

public class IpDestinationComparator extends DelegatingComparator<IpDestination> {
   public IpDestinationComparator(
      final Comparator<? super InetSocketAddress> addressComparator,
      final Comparator<? super Integer> groupIdComparator,
      final Comparator<? super Integer> controlIdComparator
   ) {
      super(
         Comparator.comparing(IpDestination::getAddress, addressComparator)
            .thenComparing(Destination::getGroupId, groupIdComparator)
            .thenComparing(Destination::getControlId, controlIdComparator)
      );
   }
}
