/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.protocol.sicp204;

import be.tpvision.smartcontrol.io.CommandSender;
import be.tpvision.smartcontrol.io.ip.IpDestination;
import be.tpvision.smartcontrol.io.ip.sicp204.NettyCommandSender;
import be.tpvision.smartcontrol.protocol.sicp.SicpCommandFactory;

public class SicpFactory
implements be.tpvision.smartcontrol.protocol.sicp.SicpFactory {
    private static SicpFactory sicpFactory;

    public static synchronized SicpFactory getInstance() {
        if (sicpFactory == null) {
            sicpFactory = new SicpFactory();
        }
        return sicpFactory;
    }

    @Override
    public SicpCommandFactory getSicpCommandFactory() {
        return be.tpvision.smartcontrol.protocol.sicp204.SicpCommandFactory.getInstance();
    }

    @Override
    public CommandSender<IpDestination> getCommandSender() {
        return NettyCommandSender.getInstance();
    }
}

