/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.codecs.sicp197.miscellaneous;

import be.tpvision.smartcontrol.codecs.sicp.SingleValueCodec;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.PortStatus;
import java.util.EnumMap;

public class PortStatusCodec
extends SingleValueCodec<PortStatus> {
    static final byte UNLOCKED_BYTE = 0;
    static final byte LOCKED_BYTE = 1;
    private static PortStatusCodec portStatusCodec;

    public PortStatusCodec() {
        super(PortStatus.class);
    }

    public static synchronized PortStatusCodec getInstance() {
        if (portStatusCodec == null) {
            portStatusCodec = new PortStatusCodec();
        }
        return portStatusCodec;
    }

    @Override
    protected void initializeDeviceSettings() {
        EnumMap<PortStatus, Byte> domainPortStatuses = new EnumMap<PortStatus, Byte>(PortStatus.class);
        domainPortStatuses.put(PortStatus.UNLOCKED, (byte)0);
        domainPortStatuses.put(PortStatus.LOCKED, (byte)1);
        super.setDeviceSettings(domainPortStatuses);
    }
}

