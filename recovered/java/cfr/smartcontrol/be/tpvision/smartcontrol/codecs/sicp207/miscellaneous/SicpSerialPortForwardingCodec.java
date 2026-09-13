/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.codecs.sicp207.miscellaneous;

import be.tpvision.smartcontrol.codecs.sicp.SingleValueCodec;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.SicpSerialPortForwarding;
import java.util.EnumMap;

public class SicpSerialPortForwardingCodec
extends SingleValueCodec<SicpSerialPortForwarding> {
    static final byte OFF_BYTE = 0;
    static final byte ON_BYTE = 1;
    private static SicpSerialPortForwardingCodec sicpSerialPortForwardingCodec;

    private SicpSerialPortForwardingCodec() {
        super(SicpSerialPortForwarding.class);
    }

    public static synchronized SicpSerialPortForwardingCodec getInstance() {
        if (sicpSerialPortForwardingCodec == null) {
            sicpSerialPortForwardingCodec = new SicpSerialPortForwardingCodec();
        }
        return sicpSerialPortForwardingCodec;
    }

    @Override
    protected void initializeDeviceSettings() {
        EnumMap<SicpSerialPortForwarding, Byte> domainTouch = new EnumMap<SicpSerialPortForwarding, Byte>(SicpSerialPortForwarding.class);
        domainTouch.put(SicpSerialPortForwarding.OFF, (byte)0);
        domainTouch.put(SicpSerialPortForwarding.ON, (byte)1);
        super.setDeviceSettings(domainTouch);
    }
}

