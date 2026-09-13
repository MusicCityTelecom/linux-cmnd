/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.codecs.sicp208.general;

import be.tpvision.smartcontrol.codecs.sicp.SingleValueCodec;
import be.tpvision.smartcontrol.domain.device_settings.general.ForceRestartCustomApp;
import java.util.EnumMap;

public class ForceRestartCustomAppCodec
extends SingleValueCodec<ForceRestartCustomApp> {
    private static ForceRestartCustomAppCodec forceRestartCustomAppCodec;

    private ForceRestartCustomAppCodec() {
        super(ForceRestartCustomApp.class);
    }

    public static synchronized ForceRestartCustomAppCodec getInstance() {
        if (forceRestartCustomAppCodec == null) {
            forceRestartCustomAppCodec = new ForceRestartCustomAppCodec();
        }
        return forceRestartCustomAppCodec;
    }

    @Override
    protected void initializeDeviceSettings() {
        EnumMap<ForceRestartCustomApp, Byte> domainTouch = new EnumMap<ForceRestartCustomApp, Byte>(ForceRestartCustomApp.class);
        for (ForceRestartCustomApp forceRestartCustomApp : ForceRestartCustomApp.values()) {
            domainTouch.put(forceRestartCustomApp, (byte)forceRestartCustomApp.ordinal());
        }
        super.setDeviceSettings(domainTouch);
    }
}

