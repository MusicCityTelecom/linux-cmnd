/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.protocol.sicp;

import be.tpvision.smartcontrol.codecs.sicp.Codec;
import be.tpvision.smartcontrol.domain.device_settings.DeviceSetting;
import be.tpvision.smartcontrol.io.Destination;
import be.tpvision.smartcontrol.protocol.sicp.Command;
import be.tpvision.smartcontrol.protocol.sicp.Commands;
import be.tpvision.smartcontrol.protocol.sicp.SicpCommand;
import be.tpvision.smartcontrol.protocol.sicp.SicpVersion;

public interface SicpCommandFactory {
    public SicpVersion getSicpVersion();

    public Commands getCommands();

    public Codec<? extends DeviceSetting> getDecoder(byte var1);

    public SicpCommand getSicpCommand(int var1, int var2, Command.Type var3, Command.Setting var4);

    public SicpCommand getSicpCommand(int var1, int var2, Command.Type var3, Command.Setting var4, DeviceSetting var5);

    public SicpCommand getSicpCommand(Destination var1, Command.Type var2, Command.Setting var3);

    public SicpCommand getSicpCommand(Destination var1, Command.Type var2, Command.Setting var3, DeviceSetting var4);
}

