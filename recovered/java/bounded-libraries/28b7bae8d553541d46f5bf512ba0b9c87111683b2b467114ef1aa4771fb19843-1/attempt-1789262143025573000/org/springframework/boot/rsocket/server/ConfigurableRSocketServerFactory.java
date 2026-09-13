/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.unit.DataSize
 */
package org.springframework.boot.rsocket.server;

import java.net.InetAddress;
import org.springframework.boot.rsocket.server.RSocketServer;
import org.springframework.boot.web.server.Ssl;
import org.springframework.boot.web.server.SslStoreProvider;
import org.springframework.util.unit.DataSize;

public interface ConfigurableRSocketServerFactory {
    public void setPort(int var1);

    public void setFragmentSize(DataSize var1);

    public void setAddress(InetAddress var1);

    public void setTransport(RSocketServer.Transport var1);

    public void setSsl(Ssl var1);

    public void setSslStoreProvider(SslStoreProvider var1);
}

