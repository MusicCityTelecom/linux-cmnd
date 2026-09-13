/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sun.jna.Memory
 *  com.sun.jna.Native
 *  com.sun.jna.Pointer
 *  com.sun.jna.Structure
 *  com.sun.jna.platform.unix.LibCAPI$size_t
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package oshi.util.platform.unix.openbsd;

import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.platform.unix.LibCAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import oshi.annotation.concurrent.ThreadSafe;
import oshi.jna.ByRef;
import oshi.jna.platform.unix.OpenBsdLibc;
import oshi.util.ExecutingCommand;
import oshi.util.ParseUtil;

@ThreadSafe
public final class OpenBsdSysctlUtil {
    private static final String SYSCTL_N = "sysctl -n ";
    private static final Logger LOG = LoggerFactory.getLogger(OpenBsdSysctlUtil.class);
    private static final String SYSCTL_FAIL = "Failed sysctl call: {}, Error code: {}";

    private OpenBsdSysctlUtil() {
    }

    public static int sysctl(int[] name, int def) {
        int intSize = OpenBsdLibc.INT_SIZE;
        try (Memory p = new Memory((long)intSize);){
            ByRef.CloseableSizeTByReference size;
            block12: {
                size = new ByRef.CloseableSizeTByReference(intSize);
                try {
                    if (0 == OpenBsdLibc.INSTANCE.sysctl(name, name.length, (Pointer)p, size, null, LibCAPI.size_t.ZERO)) break block12;
                    LOG.warn(SYSCTL_FAIL, (Object)name, (Object)Native.getLastError());
                    int n = def;
                    size.close();
                    return n;
                }
                catch (Throwable throwable) {
                    try {
                        size.close();
                    }
                    catch (Throwable throwable2) {
                        throwable.addSuppressed(throwable2);
                    }
                    throw throwable;
                }
            }
            int n = p.getInt(0L);
            size.close();
            return n;
        }
    }

    public static long sysctl(int[] name, long def) {
        int uint64Size = OpenBsdLibc.UINT64_SIZE;
        try (Memory p = new Memory((long)uint64Size);){
            ByRef.CloseableSizeTByReference size;
            block12: {
                size = new ByRef.CloseableSizeTByReference(uint64Size);
                try {
                    if (0 == OpenBsdLibc.INSTANCE.sysctl(name, name.length, (Pointer)p, size, null, LibCAPI.size_t.ZERO)) break block12;
                    LOG.warn(SYSCTL_FAIL, (Object)name, (Object)Native.getLastError());
                    long l = def;
                    size.close();
                    return l;
                }
                catch (Throwable throwable) {
                    try {
                        size.close();
                    }
                    catch (Throwable throwable2) {
                        throwable.addSuppressed(throwable2);
                    }
                    throw throwable;
                }
            }
            long l = p.getLong(0L);
            size.close();
            return l;
        }
    }

    public static String sysctl(int[] name, String def) {
        try (ByRef.CloseableSizeTByReference size = new ByRef.CloseableSizeTByReference();){
            Memory p;
            block14: {
                String string;
                if (0 != OpenBsdLibc.INSTANCE.sysctl(name, name.length, null, size, null, LibCAPI.size_t.ZERO)) {
                    LOG.warn(SYSCTL_FAIL, (Object)name, (Object)Native.getLastError());
                    String string2 = def;
                    return string2;
                }
                p = new Memory(size.longValue() + 1L);
                try {
                    if (0 == OpenBsdLibc.INSTANCE.sysctl(name, name.length, (Pointer)p, size, null, LibCAPI.size_t.ZERO)) break block14;
                    LOG.warn(SYSCTL_FAIL, (Object)name, (Object)Native.getLastError());
                    string = def;
                }
                catch (Throwable throwable) {
                    try {
                        p.close();
                    }
                    catch (Throwable throwable2) {
                        throwable.addSuppressed(throwable2);
                    }
                    throw throwable;
                }
                p.close();
                return string;
            }
            String string = p.getString(0L);
            p.close();
            return string;
        }
    }

    public static boolean sysctl(int[] name, Structure struct) {
        try (ByRef.CloseableSizeTByReference size = new ByRef.CloseableSizeTByReference(struct.size());){
            if (0 != OpenBsdLibc.INSTANCE.sysctl(name, name.length, struct.getPointer(), size, null, LibCAPI.size_t.ZERO)) {
                LOG.error(SYSCTL_FAIL, (Object)name, (Object)Native.getLastError());
                boolean bl = false;
                return bl;
            }
        }
        struct.read();
        return true;
    }

    public static Memory sysctl(int[] name) {
        try (ByRef.CloseableSizeTByReference size = new ByRef.CloseableSizeTByReference();){
            if (0 != OpenBsdLibc.INSTANCE.sysctl(name, name.length, null, size, null, LibCAPI.size_t.ZERO)) {
                LOG.error(SYSCTL_FAIL, (Object)name, (Object)Native.getLastError());
                Memory memory = null;
                return memory;
            }
            Memory m = new Memory(size.longValue());
            if (0 != OpenBsdLibc.INSTANCE.sysctl(name, name.length, (Pointer)m, size, null, LibCAPI.size_t.ZERO)) {
                LOG.error(SYSCTL_FAIL, (Object)name, (Object)Native.getLastError());
                m.close();
                Memory memory = null;
                return memory;
            }
            Memory memory = m;
            return memory;
        }
    }

    public static int sysctl(String name, int def) {
        return ParseUtil.parseIntOrDefault(ExecutingCommand.getFirstAnswer(SYSCTL_N + name), def);
    }

    public static long sysctl(String name, long def) {
        return ParseUtil.parseLongOrDefault(ExecutingCommand.getFirstAnswer(SYSCTL_N + name), def);
    }

    public static String sysctl(String name, String def) {
        String v = ExecutingCommand.getFirstAnswer(SYSCTL_N + name);
        if (null == v || v.isEmpty()) {
            return def;
        }
        return v;
    }
}

