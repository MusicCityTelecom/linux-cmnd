/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.codecs.sicp;

import be.tpvision.smartcontrol.codecs.sicp.SingleValueCodec;
import be.tpvision.smartcontrol.domain.device_settings.CommunicationControl;
import java.util.EnumMap;

public class CommunicationControlCodec
extends SingleValueCodec<CommunicationControl> {
    static final byte ACKNOWLEDGED_BYTE = 6;
    static final byte NOT_ACKNOWLEDGED_BYTE = 21;
    static final byte NOT_AVAILABLE_BYTE = 24;
    private static CommunicationControlCodec communicationControlCodec;

    private CommunicationControlCodec() {
        super(CommunicationControl.class);
    }

    public static synchronized CommunicationControlCodec getInstance() {
        if (communicationControlCodec == null) {
            communicationControlCodec = new CommunicationControlCodec();
        }
        return communicationControlCodec;
    }

    @Override
    protected void initializeDeviceSettings() {
        EnumMap<CommunicationControl, Byte> domainCommunicationControl = new EnumMap<CommunicationControl, Byte>(CommunicationControl.class);
        domainCommunicationControl.put(CommunicationControl.ACKNOWLEDGED, (byte)6);
        domainCommunicationControl.put(CommunicationControl.NOT_ACKNOWLEDGED, (byte)21);
        domainCommunicationControl.put(CommunicationControl.NOT_AVAILABLE, (byte)24);
        super.setDeviceSettings(domainCommunicationControl);
    }
}

