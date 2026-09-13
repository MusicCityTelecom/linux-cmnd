/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.service;

import be.tpvision.smartcontrol.domain.Hardware;
import java.util.Set;

public interface HardwareService {
    public Set<Hardware> getHardware();

    public Hardware getHardware(String var1);

    public void addHardware(Hardware var1);

    public void updateHardware(Hardware var1);

    public void deleteHardware(Hardware var1);

    public void deleteHardware(String var1);
}

