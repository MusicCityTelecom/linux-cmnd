/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.util;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import lombok.Generated;

public final class SocketUtils {
    public static boolean isTcpPortAvailable(int port) {
        boolean bl;
        ServerSocket serverSocket = new ServerSocket();
        try {
            serverSocket.setReuseAddress(false);
            serverSocket.bind(new InetSocketAddress(InetAddress.getByName("localhost"), port), 1);
            bl = true;
        }
        catch (Throwable throwable) {
            try {
                try {
                    serverSocket.close();
                }
                catch (Throwable throwable2) {
                    throwable.addSuppressed(throwable2);
                }
                throw throwable;
            }
            catch (Exception ex) {
                return false;
            }
        }
        serverSocket.close();
        return bl;
    }

    @Generated
    private SocketUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

