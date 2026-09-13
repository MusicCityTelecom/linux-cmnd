/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.protocol.todelete;

import be.tpvision.smartcontrol.protocol.todelete.TemperatureSensor;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(value=TemperatureSensor.class)
public abstract class TemperatureSensor_ {
    public static volatile SingularAttribute<TemperatureSensor, Integer> temperature;
    public static final String TEMPERATURE = "temperature";
}

