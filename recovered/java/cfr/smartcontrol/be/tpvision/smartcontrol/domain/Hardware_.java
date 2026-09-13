/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.domain;

import be.tpvision.smartcontrol.domain.Hardware;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(value=Hardware.class)
public abstract class Hardware_ {
    public static volatile SingularAttribute<Hardware, String> contentId;
    public static volatile SingularAttribute<Hardware, String> hardwareKey;
    public static final String CONTENT_ID = "contentId";
    public static final String HARDWARE_KEY = "hardwareKey";
}

