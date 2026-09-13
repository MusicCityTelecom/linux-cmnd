/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.protocol.sicp;

import be.tpvision.smartcontrol.domain.Device;
import be.tpvision.smartcontrol.protocol.sicp.SicpFactory;

public interface SicpFactoryProvider {
    public SicpFactory getSicpFactory(Device var1);
}

