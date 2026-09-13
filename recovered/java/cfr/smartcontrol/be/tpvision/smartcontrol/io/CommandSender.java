/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.io;

import be.tpvision.smartcontrol.io.Destination;
import be.tpvision.smartcontrol.protocol.Request;
import be.tpvision.smartcontrol.protocol.Response;

public interface CommandSender<T extends Destination> {
    public Response send(Request var1, T var2);

    public <C> C send(Request var1, T var2, Class<C> var3);
}

