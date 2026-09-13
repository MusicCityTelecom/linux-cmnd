/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.io.ip;

import be.tpvision.smartcontrol.io.Destination_;
import be.tpvision.smartcontrol.io.ip.IpDestination;
import java.net.InetSocketAddress;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(value=IpDestination.class)
public abstract class IpDestination_
extends Destination_ {
    public static volatile SingularAttribute<IpDestination, InetSocketAddress> address;
    public static final String ADDRESS = "address";
}

