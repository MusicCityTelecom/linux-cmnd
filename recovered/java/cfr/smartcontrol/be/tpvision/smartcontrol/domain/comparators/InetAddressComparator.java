/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.domain.comparators;

import be.tpvision.smartcontrol.domain.comparators.DelegatingComparator;
import java.net.InetAddress;
import java.util.Comparator;

public class InetAddressComparator
extends DelegatingComparator<InetAddress> {
    public InetAddressComparator(Comparator<String> hostAddressComparator) {
        super(Comparator.comparing(InetAddress::getHostAddress, hostAddressComparator));
    }
}

