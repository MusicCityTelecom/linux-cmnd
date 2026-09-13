/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.domain.device_settings.miscellaneous;

import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.TemperatureSensor;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(value=TemperatureSensor.class)
public abstract class TemperatureSensor_ {
    public static volatile SingularAttribute<TemperatureSensor, Integer> temperatureOne;
    public static volatile SingularAttribute<TemperatureSensor, Integer> temperatureTwo;
    public static final String TEMPERATURE_ONE = "temperatureOne";
    public static final String TEMPERATURE_TWO = "temperatureTwo";
}

