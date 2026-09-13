package be.tpvision.smartcontrol.domain.comparators;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Comparator;

public class InetSocketAddressComparator extends DelegatingComparator<InetSocketAddress> {
   public InetSocketAddressComparator(final Comparator<? super InetAddress> addressComparator, final Comparator<? super Integer> portComparator) {
      super(Comparator.comparing(InetSocketAddress::getAddress, addressComparator).thenComparing(InetSocketAddress::getPort, portComparator));
   }
}
