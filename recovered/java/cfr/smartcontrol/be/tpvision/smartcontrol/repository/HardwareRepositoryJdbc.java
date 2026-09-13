/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.repository;

import be.tpvision.smartcontrol.domain.Hardware;
import java.util.Set;

public interface HardwareRepositoryJdbc {
    public Set<Hardware> getHardwareByContentId(String var1);

    public Hardware getHardware(String var1);

    public void addHardware(Hardware var1);

    public void updateHardware(Hardware var1);
}

