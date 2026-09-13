/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.protocol.sicp200;

import be.tpvision.smartcontrol.io.CommandSender;
import be.tpvision.smartcontrol.io.ip.IpDestination;
import be.tpvision.smartcontrol.io.ip.sicp200.NettyCommandSender;
import be.tpvision.smartcontrol.protocol.sicp.SicpCommandFactory;
import be.tpvision.smartcontrol.protocol.sicp.SicpFactory;
import be.tpvision.smartcontrol.protocol.sicp200.SicpHimalayaCommandFactory;

public class SicpHimalayaFactory
implements SicpFactory {
    private static SicpHimalayaFactory sicpFactory;

    public static synchronized SicpHimalayaFactory getInstance() {
        if (sicpFactory == null) {
            sicpFactory = new SicpHimalayaFactory();
        }
        return sicpFactory;
    }

    @Override
    public SicpCommandFactory getSicpCommandFactory() {
        return SicpHimalayaCommandFactory.getInstance();
    }

    @Override
    public CommandSender<IpDestination> getCommandSender() {
        return NettyCommandSender.getInstance();
    }
}

