/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.io;

import be.tpvision.smartcontrol.io.Destination;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(value=Destination.class)
public abstract class Destination_ {
    public static volatile SingularAttribute<Destination, Integer> groupId;
    public static volatile SingularAttribute<Destination, Integer> controlId;
    public static final String GROUP_ID = "groupId";
    public static final String CONTROL_ID = "controlId";
}

