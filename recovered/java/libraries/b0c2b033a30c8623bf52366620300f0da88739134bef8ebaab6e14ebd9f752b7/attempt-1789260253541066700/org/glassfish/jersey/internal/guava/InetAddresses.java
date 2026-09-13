/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.guava;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import org.glassfish.jersey.internal.guava.Preconditions;

public final class InetAddresses {
    private static final int IPV4_PART_COUNT = 4;
    private static final int IPV6_PART_COUNT = 8;

    private InetAddresses() {
    }

    private static byte[] ipStringToBytes(String ipString) {
        boolean hasColon = false;
        boolean hasDot = false;
        for (int i = 0; i < ipString.length(); ++i) {
            char c = ipString.charAt(i);
            if (c == '.') {
                hasDot = true;
                continue;
            }
            if (c == ':') {
                if (hasDot) {
                    return null;
                }
                hasColon = true;
                continue;
            }
            if (Character.digit(c, 16) != -1) continue;
            return null;
        }
        if (hasColon) {
            if (hasDot && (ipString = InetAddresses.convertDottedQuadToHex(ipString)) == null) {
                return null;
            }
            return InetAddresses.textToNumericFormatV6(ipString);
        }
        if (hasDot) {
            return InetAddresses.textToNumericFormatV4(ipString);
        }
        return null;
    }

    private static byte[] textToNumericFormatV4(String ipString) {
        String[] address = ipString.split("\\.", 5);
        if (address.length != 4) {
            return null;
        }
        byte[] bytes = new byte[4];
        try {
            for (int i = 0; i < bytes.length; ++i) {
                bytes[i] = InetAddresses.parseOctet(address[i]);
            }
        }
        catch (NumberFormatException ex) {
            return null;
        }
        return bytes;
    }

    private static byte[] textToNumericFormatV6(String ipString) {
        int partsLo;
        int partsHi;
        String[] parts = ipString.split(":", 10);
        if (parts.length < 3 || parts.length > 9) {
            return null;
        }
        int skipIndex = -1;
        for (int i = 1; i < parts.length - 1; ++i) {
            if (parts[i].length() != 0) continue;
            if (skipIndex >= 0) {
                return null;
            }
            skipIndex = i;
        }
        if (skipIndex >= 0) {
            partsHi = skipIndex;
            partsLo = parts.length - skipIndex - 1;
            if (parts[0].length() == 0 && --partsHi != 0) {
                return null;
            }
            if (parts[parts.length - 1].length() == 0 && --partsLo != 0) {
                return null;
            }
        } else {
            partsHi = parts.length;
            partsLo = 0;
        }
        int partsSkipped = 8 - (partsHi + partsLo);
        if (!(skipIndex < 0 ? partsSkipped == 0 : partsSkipped >= 1)) {
            return null;
        }
        ByteBuffer rawBytes = ByteBuffer.allocate(16);
        try {
            int i;
            for (i = 0; i < partsHi; ++i) {
                rawBytes.putShort(InetAddresses.parseHextet(parts[i]));
            }
            for (i = 0; i < partsSkipped; ++i) {
                rawBytes.putShort((short)0);
            }
            for (i = partsLo; i > 0; --i) {
                rawBytes.putShort(InetAddresses.parseHextet(parts[parts.length - i]));
            }
        }
        catch (NumberFormatException ex) {
            return null;
        }
        return rawBytes.array();
    }

    private static String convertDottedQuadToHex(String ipString) {
        int lastColon = ipString.lastIndexOf(58);
        String initialPart = ipString.substring(0, lastColon + 1);
        String dottedQuad = ipString.substring(lastColon + 1);
        byte[] quad = InetAddresses.textToNumericFormatV4(dottedQuad);
        if (quad == null) {
            return null;
        }
        String penultimate = Integer.toHexString((quad[0] & 0xFF) << 8 | quad[1] & 0xFF);
        String ultimate = Integer.toHexString((quad[2] & 0xFF) << 8 | quad[3] & 0xFF);
        return initialPart + penultimate + ":" + ultimate;
    }

    private static byte parseOctet(String ipPart) {
        int octet = Integer.parseInt(ipPart);
        if (octet > 255 || ipPart.startsWith("0") && ipPart.length() > 1) {
            throw new NumberFormatException();
        }
        return (byte)octet;
    }

    private static short parseHextet(String ipPart) {
        int hextet = Integer.parseInt(ipPart, 16);
        if (hextet > 65535) {
            throw new NumberFormatException();
        }
        return (short)hextet;
    }

    private static InetAddress bytesToInetAddress(byte[] addr) {
        try {
            return InetAddress.getByAddress(addr);
        }
        catch (UnknownHostException e) {
            throw new AssertionError((Object)e);
        }
    }

    private static InetAddress forUriStringNoThrow(String hostAddr) {
        int expectBytes;
        String ipString;
        Preconditions.checkNotNull(hostAddr);
        if (hostAddr.startsWith("[") && hostAddr.endsWith("]")) {
            ipString = hostAddr.substring(1, hostAddr.length() - 1);
            expectBytes = 16;
        } else {
            ipString = hostAddr;
            expectBytes = 4;
        }
        byte[] addr = InetAddresses.ipStringToBytes(ipString);
        if (addr == null || addr.length != expectBytes) {
            return null;
        }
        return InetAddresses.bytesToInetAddress(addr);
    }

    public static boolean isUriInetAddress(String ipString) {
        return InetAddresses.forUriStringNoThrow(ipString) != null;
    }

    public static boolean isMappedIPv4Address(String ipString) {
        byte[] bytes = InetAddresses.ipStringToBytes(ipString);
        if (bytes != null && bytes.length == 16) {
            int i;
            for (i = 0; i < 10; ++i) {
                if (bytes[i] == 0) continue;
                return false;
            }
            for (i = 10; i < 12; ++i) {
                if (bytes[i] == -1) continue;
                return false;
            }
            return true;
        }
        return false;
    }
}

