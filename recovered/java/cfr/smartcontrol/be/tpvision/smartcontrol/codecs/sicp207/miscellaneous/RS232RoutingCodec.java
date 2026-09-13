/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.codecs.sicp207.miscellaneous;

import be.tpvision.smartcontrol.codecs.sicp.SingleValueCodec;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.RS232Routing;
import java.util.EnumMap;

public class RS232RoutingCodec
extends SingleValueCodec<RS232Routing> {
    private static RS232RoutingCodec rs232RoutingCodec;

    private RS232RoutingCodec() {
        super(RS232Routing.class);
    }

    public static synchronized RS232RoutingCodec getInstance() {
        if (rs232RoutingCodec == null) {
            rs232RoutingCodec = new RS232RoutingCodec();
        }
        return rs232RoutingCodec;
    }

    @Override
    protected void initializeDeviceSettings() {
        EnumMap<RS232Routing, Byte> domainTouch = new EnumMap<RS232Routing, Byte>(RS232Routing.class);
        for (RS232Routing routing : RS232Routing.values()) {
            domainTouch.put(routing, (byte)routing.ordinal());
        }
        super.setDeviceSettings(domainTouch);
    }
}

