/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.codecs.sicp207.miscellaneous;

import be.tpvision.smartcontrol.codecs.sicp.SingleValueCodec;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.HdmiOneWire;
import java.util.EnumMap;

public class HdmiOneWireCodec
extends SingleValueCodec<HdmiOneWire> {
    private static HdmiOneWireCodec hdmiOneWireCodec;

    private HdmiOneWireCodec() {
        super(HdmiOneWire.class);
    }

    public static synchronized HdmiOneWireCodec getInstance() {
        if (hdmiOneWireCodec == null) {
            hdmiOneWireCodec = new HdmiOneWireCodec();
        }
        return hdmiOneWireCodec;
    }

    @Override
    protected void initializeDeviceSettings() {
        EnumMap<HdmiOneWire, Byte> domainTouch = new EnumMap<HdmiOneWire, Byte>(HdmiOneWire.class);
        for (HdmiOneWire hdmiOneWire : HdmiOneWire.values()) {
            domainTouch.put(hdmiOneWire, (byte)hdmiOneWire.getByteTag());
        }
        super.setDeviceSettings(domainTouch);
    }
}

