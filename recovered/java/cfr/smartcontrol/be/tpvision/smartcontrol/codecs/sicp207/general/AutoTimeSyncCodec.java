/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.codecs.sicp207.general;

import be.tpvision.smartcontrol.codecs.sicp.SingleValueCodec;
import be.tpvision.smartcontrol.domain.device_settings.general.AutoTimeSync;
import java.util.EnumMap;

public class AutoTimeSyncCodec
extends SingleValueCodec<AutoTimeSync> {
    static final byte OFF_BYTE = 0;
    static final byte ON_BYTE = 1;
    private static AutoTimeSyncCodec autoTimeSyncCodec;

    private AutoTimeSyncCodec() {
        super(AutoTimeSync.class);
    }

    public static synchronized AutoTimeSyncCodec getInstance() {
        if (autoTimeSyncCodec == null) {
            autoTimeSyncCodec = new AutoTimeSyncCodec();
        }
        return autoTimeSyncCodec;
    }

    @Override
    protected void initializeDeviceSettings() {
        EnumMap<AutoTimeSync, Byte> domainTouch = new EnumMap<AutoTimeSync, Byte>(AutoTimeSync.class);
        domainTouch.put(AutoTimeSync.OFF, (byte)0);
        domainTouch.put(AutoTimeSync.ON, (byte)1);
        super.setDeviceSettings(domainTouch);
    }
}

