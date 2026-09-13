/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.io.ip.sicp203;

import be.tpvision.smartcontrol.protocol.sicp203.SicpCommandFactory;

public class SicpDecoder
extends be.tpvision.smartcontrol.io.ip.sicp.SicpDecoder {
    public SicpDecoder() {
        super(SicpCommandFactory.getInstance());
    }
}

