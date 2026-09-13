/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.protocol.sicp;

import be.tpvision.smartcontrol.io.CommandSender;
import be.tpvision.smartcontrol.io.ip.IpDestination;
import be.tpvision.smartcontrol.protocol.sicp.SicpCommandFactory;

public interface SicpFactory {
    public SicpCommandFactory getSicpCommandFactory();

    public CommandSender<IpDestination> getCommandSender();
}

