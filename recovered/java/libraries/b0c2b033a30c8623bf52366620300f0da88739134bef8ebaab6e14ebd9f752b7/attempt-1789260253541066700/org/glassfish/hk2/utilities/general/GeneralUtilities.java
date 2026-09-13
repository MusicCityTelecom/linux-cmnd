/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.utilities.general;

import java.lang.constant.Constable;
import java.lang.reflect.Array;
import org.glassfish.hk2.utilities.general.WeakHashClock;
import org.glassfish.hk2.utilities.general.WeakHashLRU;
import org.glassfish.hk2.utilities.general.internal.WeakHashClockImpl;
import org.glassfish.hk2.utilities.general.internal.WeakHashLRUImpl;

public class GeneralUtilities {
    public static boolean safeEquals(Object a, Object b) {
        if (a == b) {
            return true;
        }
        if (a == null) {
            return false;
        }
        if (b == null) {
            return false;
        }
        return a.equals(b);
    }

    private static Class<?> loadArrayClass(ClassLoader cl, String aName) {
        Class<Constable> componentType = null;
        int[] dimensions = null;
        int dot = 0;
        while (componentType == null) {
            char dotChar = aName.charAt(dot);
            if (dotChar == '[') {
                ++dot;
                continue;
            }
            dimensions = new int[dot];
            for (int lcv = 0; lcv < dot; ++lcv) {
                dimensions[lcv] = 0;
            }
            if (dotChar == 'B') {
                componentType = Byte.TYPE;
                continue;
            }
            if (dotChar == 'I') {
                componentType = Integer.TYPE;
                continue;
            }
            if (dotChar == 'J') {
                componentType = Long.TYPE;
                continue;
            }
            if (dotChar == 'Z') {
                componentType = Boolean.TYPE;
                continue;
            }
            if (dotChar == 'S') {
                componentType = Short.TYPE;
                continue;
            }
            if (dotChar == 'C') {
                componentType = Character.TYPE;
                continue;
            }
            if (dotChar == 'D') {
                componentType = Double.TYPE;
                continue;
            }
            if (dotChar == 'F') {
                componentType = Float.TYPE;
                continue;
            }
            if (dotChar != 'L') {
                throw new IllegalArgumentException("Unknown array type " + aName);
            }
            if (aName.charAt(aName.length() - 1) != ';') {
                throw new IllegalArgumentException("Badly formed L array expresion: " + aName);
            }
            String cName = aName.substring(dot + 1, aName.length() - 1);
            componentType = GeneralUtilities.loadClass(cl, cName);
            if (componentType != null) continue;
            return null;
        }
        Object retArray = Array.newInstance(componentType, dimensions);
        return retArray.getClass();
    }

    public static Class<?> loadClass(ClassLoader cl, String cName) {
        if (cName.startsWith("[")) {
            return GeneralUtilities.loadArrayClass(cl, cName);
        }
        try {
            return cl.loadClass(cName);
        }
        catch (Throwable th) {
            return null;
        }
    }

    public static <K, V> WeakHashClock<K, V> getWeakHashClock(boolean isWeak) {
        return new WeakHashClockImpl(isWeak);
    }

    public static <K> WeakHashLRU<K> getWeakHashLRU(boolean isWeak) {
        return new WeakHashLRUImpl(isWeak);
    }

    public static String prettyPrintBytes(byte[] bytes) {
        StringBuffer sb = new StringBuffer("Total buffer length: " + bytes.length + "\n");
        int numEntered = 0;
        for (byte b : bytes) {
            if (numEntered % 16 == 0) {
                if (numEntered != 0) {
                    sb.append("\n");
                }
                String desc = String.format("%08X ", numEntered);
                sb.append(desc);
            }
            String singleByte = String.format("%02X ", b);
            sb.append(singleByte);
            if (++numEntered % 8 != 0 || numEntered % 16 == 0) continue;
            sb.append(" ");
        }
        return sb.toString();
    }
}

