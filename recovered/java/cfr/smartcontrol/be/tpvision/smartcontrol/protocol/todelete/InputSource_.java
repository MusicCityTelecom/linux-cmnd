/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.protocol.todelete;

import be.tpvision.smartcontrol.protocol.todelete.InputSource;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(value=InputSource.class)
public abstract class InputSource_ {
    public static volatile SingularAttribute<InputSource, InputSource.SourceType> sourceType;
    public static volatile SingularAttribute<InputSource, InputSource.SourceLabel> sourceLabel;
    public static final String SOURCE_TYPE = "sourceType";
    public static final String SOURCE_LABEL = "sourceLabel";
}

