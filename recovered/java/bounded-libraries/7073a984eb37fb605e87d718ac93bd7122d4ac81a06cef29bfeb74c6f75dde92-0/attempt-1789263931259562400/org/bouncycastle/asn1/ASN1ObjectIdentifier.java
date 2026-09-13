/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.asn1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1OutputStream;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1RelativeOID;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.ASN1UniversalType;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.OIDTokenizer;
import org.bouncycastle.util.Arrays;

public class ASN1ObjectIdentifier
extends ASN1Primitive {
    static final ASN1UniversalType TYPE = new ASN1UniversalType(ASN1ObjectIdentifier.class, 6){

        ASN1Primitive fromImplicitPrimitive(DEROctetString dEROctetString) {
            return ASN1ObjectIdentifier.createPrimitive(dEROctetString.getOctets(), false);
        }
    };
    private static final long LONG_LIMIT = 0xFFFFFFFFFFFF80L;
    private static final ConcurrentMap<OidHandle, ASN1ObjectIdentifier> pool = new ConcurrentHashMap<OidHandle, ASN1ObjectIdentifier>();
    private final String identifier;
    private byte[] contents;

    public static ASN1ObjectIdentifier fromContents(byte[] byArray) {
        return ASN1ObjectIdentifier.createPrimitive(byArray, true);
    }

    public static ASN1ObjectIdentifier getInstance(Object object) {
        if (object == null || object instanceof ASN1ObjectIdentifier) {
            return (ASN1ObjectIdentifier)object;
        }
        if (object instanceof ASN1Encodable) {
            ASN1Primitive aSN1Primitive = ((ASN1Encodable)object).toASN1Primitive();
            if (aSN1Primitive instanceof ASN1ObjectIdentifier) {
                return (ASN1ObjectIdentifier)aSN1Primitive;
            }
        } else if (object instanceof byte[]) {
            try {
                return (ASN1ObjectIdentifier)TYPE.fromByteArray((byte[])object);
            }
            catch (IOException iOException) {
                throw new IllegalArgumentException("failed to construct object identifier from byte[]: " + iOException.getMessage());
            }
        }
        throw new IllegalArgumentException("illegal object in getInstance: " + object.getClass().getName());
    }

    public static ASN1ObjectIdentifier getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        ASN1Primitive aSN1Primitive;
        if (!(bl || aSN1TaggedObject.isParsed() || (aSN1Primitive = aSN1TaggedObject.getObject()) instanceof ASN1ObjectIdentifier)) {
            return ASN1ObjectIdentifier.fromContents(ASN1OctetString.getInstance(aSN1Primitive).getOctets());
        }
        return (ASN1ObjectIdentifier)TYPE.getContextInstance(aSN1TaggedObject, bl);
    }

    ASN1ObjectIdentifier(byte[] byArray, boolean bl) {
        StringBuffer stringBuffer = new StringBuffer();
        long l = 0L;
        BigInteger bigInteger = null;
        boolean bl2 = true;
        for (int i = 0; i != byArray.length; ++i) {
            int n = byArray[i] & 0xFF;
            if (l <= 0xFFFFFFFFFFFF80L) {
                l += (long)(n & 0x7F);
                if ((n & 0x80) == 0) {
                    if (bl2) {
                        if (l < 40L) {
                            stringBuffer.append('0');
                        } else if (l < 80L) {
                            stringBuffer.append('1');
                            l -= 40L;
                        } else {
                            stringBuffer.append('2');
                            l -= 80L;
                        }
                        bl2 = false;
                    }
                    stringBuffer.append('.');
                    stringBuffer.append(l);
                    l = 0L;
                    continue;
                }
                l <<= 7;
                continue;
            }
            if (bigInteger == null) {
                bigInteger = BigInteger.valueOf(l);
            }
            bigInteger = bigInteger.or(BigInteger.valueOf(n & 0x7F));
            if ((n & 0x80) == 0) {
                if (bl2) {
                    stringBuffer.append('2');
                    bigInteger = bigInteger.subtract(BigInteger.valueOf(80L));
                    bl2 = false;
                }
                stringBuffer.append('.');
                stringBuffer.append(bigInteger);
                bigInteger = null;
                l = 0L;
                continue;
            }
            bigInteger = bigInteger.shiftLeft(7);
        }
        this.identifier = stringBuffer.toString();
        this.contents = bl ? Arrays.clone(byArray) : byArray;
    }

    public ASN1ObjectIdentifier(String string) {
        if (string == null) {
            throw new NullPointerException("'identifier' cannot be null");
        }
        if (!ASN1ObjectIdentifier.isValidIdentifier(string)) {
            throw new IllegalArgumentException("string " + string + " not an OID");
        }
        this.identifier = string;
    }

    ASN1ObjectIdentifier(ASN1ObjectIdentifier aSN1ObjectIdentifier, String string) {
        if (!ASN1RelativeOID.isValidIdentifier(string, 0)) {
            throw new IllegalArgumentException("string " + string + " not a valid OID branch");
        }
        this.identifier = aSN1ObjectIdentifier.getId() + "." + string;
    }

    public String getId() {
        return this.identifier;
    }

    public ASN1ObjectIdentifier branch(String string) {
        return new ASN1ObjectIdentifier(this, string);
    }

    public boolean on(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        String string = this.getId();
        String string2 = aSN1ObjectIdentifier.getId();
        return string.length() > string2.length() && string.charAt(string2.length()) == '.' && string.startsWith(string2);
    }

    private void doOutput(ByteArrayOutputStream byteArrayOutputStream) {
        OIDTokenizer oIDTokenizer = new OIDTokenizer(this.identifier);
        int n = Integer.parseInt(oIDTokenizer.nextToken()) * 40;
        String string = oIDTokenizer.nextToken();
        if (string.length() <= 18) {
            ASN1RelativeOID.writeField(byteArrayOutputStream, (long)n + Long.parseLong(string));
        } else {
            ASN1RelativeOID.writeField(byteArrayOutputStream, new BigInteger(string).add(BigInteger.valueOf(n)));
        }
        while (oIDTokenizer.hasMoreTokens()) {
            String string2 = oIDTokenizer.nextToken();
            if (string2.length() <= 18) {
                ASN1RelativeOID.writeField(byteArrayOutputStream, Long.parseLong(string2));
                continue;
            }
            ASN1RelativeOID.writeField(byteArrayOutputStream, new BigInteger(string2));
        }
    }

    private synchronized byte[] getContents() {
        if (this.contents == null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            this.doOutput(byteArrayOutputStream);
            this.contents = byteArrayOutputStream.toByteArray();
        }
        return this.contents;
    }

    boolean encodeConstructed() {
        return false;
    }

    int encodedLength(boolean bl) {
        return ASN1OutputStream.getLengthOfEncodingDL(bl, this.getContents().length);
    }

    void encode(ASN1OutputStream aSN1OutputStream, boolean bl) throws IOException {
        aSN1OutputStream.writeEncodingDL(bl, 6, this.getContents());
    }

    public int hashCode() {
        return this.identifier.hashCode();
    }

    boolean asn1Equals(ASN1Primitive aSN1Primitive) {
        if (aSN1Primitive == this) {
            return true;
        }
        if (!(aSN1Primitive instanceof ASN1ObjectIdentifier)) {
            return false;
        }
        return this.identifier.equals(((ASN1ObjectIdentifier)aSN1Primitive).identifier);
    }

    public String toString() {
        return this.getId();
    }

    private static boolean isValidIdentifier(String string) {
        if (string.length() < 3 || string.charAt(1) != '.') {
            return false;
        }
        char c = string.charAt(0);
        if (c < '0' || c > '2') {
            return false;
        }
        return ASN1RelativeOID.isValidIdentifier(string, 2);
    }

    public ASN1ObjectIdentifier intern() {
        OidHandle oidHandle = new OidHandle(this.getContents());
        ASN1ObjectIdentifier aSN1ObjectIdentifier = (ASN1ObjectIdentifier)pool.get(oidHandle);
        if (aSN1ObjectIdentifier == null && (aSN1ObjectIdentifier = pool.putIfAbsent(oidHandle, this)) == null) {
            aSN1ObjectIdentifier = this;
        }
        return aSN1ObjectIdentifier;
    }

    static ASN1ObjectIdentifier createPrimitive(byte[] byArray, boolean bl) {
        OidHandle oidHandle = new OidHandle(byArray);
        ASN1ObjectIdentifier aSN1ObjectIdentifier = (ASN1ObjectIdentifier)pool.get(oidHandle);
        if (aSN1ObjectIdentifier == null) {
            return new ASN1ObjectIdentifier(byArray, bl);
        }
        return aSN1ObjectIdentifier;
    }

    private static class OidHandle {
        private final int key;
        private final byte[] contents;

        OidHandle(byte[] byArray) {
            this.key = Arrays.hashCode(byArray);
            this.contents = byArray;
        }

        public int hashCode() {
            return this.key;
        }

        public boolean equals(Object object) {
            if (object instanceof OidHandle) {
                return Arrays.areEqual(this.contents, ((OidHandle)object).contents);
            }
            return false;
        }
    }
}

