/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.domain.device_settings.miscellaneous;

import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.Tiling;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(value=Tiling.class)
public abstract class Tiling_ {
    public static volatile SingularAttribute<Tiling, Integer> numberOfHorizontalMonitors;
    public static volatile SingularAttribute<Tiling, Tiling.Enable> enable;
    public static volatile SingularAttribute<Tiling, Integer> numberOfVerticalMonitors;
    public static volatile SingularAttribute<Tiling, Tiling.FrameComp> frameComp;
    public static volatile SingularAttribute<Tiling, Integer> position;
    public static final String NUMBER_OF_HORIZONTAL_MONITORS = "numberOfHorizontalMonitors";
    public static final String ENABLE = "enable";
    public static final String NUMBER_OF_VERTICAL_MONITORS = "numberOfVerticalMonitors";
    public static final String FRAME_COMP = "frameComp";
    public static final String POSITION = "position";
}

