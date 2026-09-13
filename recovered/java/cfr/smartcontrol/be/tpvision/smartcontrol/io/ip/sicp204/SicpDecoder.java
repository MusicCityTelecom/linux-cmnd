/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.io.ip.sicp204;

import be.tpvision.smartcontrol.protocol.sicp204.SicpCommandFactory;

public class SicpDecoder
extends be.tpvision.smartcontrol.io.ip.sicp.SicpDecoder {
    public SicpDecoder() {
        super(SicpCommandFactory.getInstance());
    }
}

