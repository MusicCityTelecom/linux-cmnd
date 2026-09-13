/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.domain.comparators;

import be.tpvision.smartcontrol.domain.comparators.DelegatingComparator;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Comparator;

public class InetSocketAddressComparator
extends DelegatingComparator<InetSocketAddress> {
    public InetSocketAddressComparator(Comparator<? super InetAddress> addressComparator, Comparator<? super Integer> portComparator) {
        super(Comparator.comparing(InetSocketAddress::getAddress, addressComparator).thenComparing(InetSocketAddress::getPort, portComparator));
    }
}

