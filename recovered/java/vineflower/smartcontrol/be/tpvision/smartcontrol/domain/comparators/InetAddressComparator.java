package be.tpvision.smartcontrol.domain.comparators;

import java.net.InetAddress;
import java.util.Comparator;

public class InetAddressComparator extends DelegatingComparator<InetAddress> {
   public InetAddressComparator(final Comparator<String> hostAddressComparator) {
      super(Comparator.comparing(InetAddress::getHostAddress, hostAddressComparator));
   }
}
