/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.io.ip.sicp197;

import be.tpvision.smartcontrol.io.SicpInitializer;
import be.tpvision.smartcontrol.protocol.sicp197.SicpCommandFactory;

public class NettyCommandSender
extends be.tpvision.smartcontrol.io.ip.sicp.NettyCommandSender {
    private static final SicpInitializer sicpInitializer;
    private static NettyCommandSender nettyCommandSender;

    private NettyCommandSender() {
        super(sicpInitializer);
    }

    public static synchronized NettyCommandSender getInstance() {
        if (nettyCommandSender == null) {
            nettyCommandSender = new NettyCommandSender();
        }
        return nettyCommandSender;
    }

    static {
        SicpCommandFactory sicpCommandFactory = SicpCommandFactory.getInstance();
        sicpInitializer = new SicpInitializer(sicpCommandFactory);
    }
}

