/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.domain.device_settings.miscellaneous;

import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.Miscellaneous;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(value=Miscellaneous.class)
public abstract class Miscellaneous_ {
    public static volatile SingularAttribute<Miscellaneous, Integer> operatingHours;
    public static final String OPERATING_HOURS = "operatingHours";
}

