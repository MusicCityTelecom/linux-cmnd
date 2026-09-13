/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sun.jna.Native
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package oshi.software.os.unix.freebsd;

import com.sun.jna.Native;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import oshi.annotation.concurrent.ThreadSafe;
import oshi.jna.ByRef;
import oshi.jna.platform.unix.CLibrary;
import oshi.jna.platform.unix.FreeBsdLibc;
import oshi.software.common.AbstractNetworkParams;
import oshi.util.ExecutingCommand;

@ThreadSafe
final class FreeBsdNetworkParams
extends AbstractNetworkParams {
    private static final Logger LOG = LoggerFactory.getLogger(FreeBsdNetworkParams.class);
    private static final FreeBsdLibc LIBC = FreeBsdLibc.INSTANCE;

    FreeBsdNetworkParams() {
    }

    @Override
    public String getDomainName() {
        try (CLibrary.Addrinfo hint = new CLibrary.Addrinfo();){
            ByRef.CloseablePointerByReference ptr;
            block13: {
                hint.ai_flags = 2;
                String hostname = this.getHostName();
                ptr = new ByRef.CloseablePointerByReference();
                try {
                    int res = LIBC.getaddrinfo(hostname, null, hint, ptr);
                    if (res <= 0) break block13;
                    if (LOG.isErrorEnabled()) {
                        LOG.warn("Failed getaddrinfo(): {}", (Object)LIBC.gai_strerror(res));
                    }
                    String string = "";
                    ptr.close();
                    return string;
                }
                catch (Throwable throwable) {
                    try {
                        ptr.close();
                    }
                    catch (Throwable throwable2) {
                        throwable.addSuppressed(throwable2);
                    }
                    throw throwable;
                }
            }
            CLibrary.Addrinfo info = new CLibrary.Addrinfo(ptr.getValue());
            String canonname = info.ai_canonname.trim();
            LIBC.freeaddrinfo(ptr.getValue());
            String string = canonname;
            ptr.close();
            return string;
        }
    }

    @Override
    public String getHostName() {
        byte[] hostnameBuffer = new byte[256];
        if (0 != LIBC.gethostname(hostnameBuffer, hostnameBuffer.length)) {
            return super.getHostName();
        }
        return Native.toString((byte[])hostnameBuffer);
    }

    @Override
    public String getIpv4DefaultGateway() {
        return FreeBsdNetworkParams.searchGateway(ExecutingCommand.runNative("route -4 get default"));
    }

    @Override
    public String getIpv6DefaultGateway() {
        return FreeBsdNetworkParams.searchGateway(ExecutingCommand.runNative("route -6 get default"));
    }
}

