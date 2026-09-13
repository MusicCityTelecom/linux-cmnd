/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.asn1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1OutputStream;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.ASN1UniversalType;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.OIDTokenizer;
import org.bouncycastle.util.Arrays;

public class ASN1RelativeOID
extends ASN1Primitive {
    static final ASN1UniversalType TYPE = new ASN1UniversalType(ASN1RelativeOID.class, 13){

        @Override
        ASN1Primitive fromImplicitPrimitive(DEROctetString dEROctetString) {
            return ASN1RelativeOID.createPrimitive(dEROctetString.getOctets(), false);
        }
    };
    private static final long LONG_LIMIT = 0xFFFFFFFFFFFF80L;
    private final String identifier;
    private byte[] contents;

    public static ASN1RelativeOID fromContents(byte[] byArray) {
        return ASN1RelativeOID.createPrimitive(byArray, true);
    }

    public static ASN1RelativeOID getInstance(Object object) {
        if (object == null || object instanceof ASN1RelativeOID) {
            return (ASN1RelativeOID)object;
        }
        if (object instanceof ASN1Encodable) {
            ASN1Primitive aSN1Primitive = ((ASN1Encodable)object).toASN1Primitive();
            if (aSN1Primitive instanceof ASN1RelativeOID) {
                return (ASN1RelativeOID)aSN1Primitive;
            }
        } else if (object instanceof byte[]) {
            byte[] byArray = (byte[])object;
            try {
                return (ASN1RelativeOID)TYPE.fromByteArray(byArray);
            }
            catch (IOException iOException) {
                throw new IllegalArgumentException("failed to construct relative OID from byte[]: " + iOException.getMessage());
            }
        }
        throw new IllegalArgumentException("illegal object in getInstance: " + object.getClass().getName());
    }

    public static ASN1RelativeOID getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        return (ASN1RelativeOID)TYPE.getContextInstance(aSN1TaggedObject, bl);
    }

    public ASN1RelativeOID(String string) {
        if (string == null) {
            throw new NullPointerException("'identifier' cannot be null");
        }
        if (!ASN1RelativeOID.isValidIdentifier(string, 0)) {
            throw new IllegalArgumentException("string " + string + " not a relative OID");
        }
        this.identifier = string;
    }

    ASN1RelativeOID(ASN1RelativeOID aSN1RelativeOID, String string) {
        if (!ASN1RelativeOID.isValidIdentifier(string, 0)) {
            throw new IllegalArgumentException("string " + string + " not a valid OID branch");
        }
        this.identifier = aSN1RelativeOID.getId() + "." + string;
    }

    private ASN1RelativeOID(byte[] byArray, boolean bl) {
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
                        bl2 = false;
                    } else {
                        stringBuffer.append('.');
                    }
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
                    bl2 = false;
                } else {
                    stringBuffer.append('.');
                }
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

    public ASN1RelativeOID branch(String string) {
        return new ASN1RelativeOID(this, string);
    }

    public String getId() {
        return this.identifier;
    }

    @Override
    public int hashCode() {
        return this.identifier.hashCode();
    }

    public String toString() {
        return this.getId();
    }

    @Override
    boolean asn1Equals(ASN1Primitive aSN1Primitive) {
        if (this == aSN1Primitive) {
            return true;
        }
        if (!(aSN1Primitive instanceof ASN1RelativeOID)) {
            return false;
        }
        ASN1RelativeOID aSN1RelativeOID = (ASN1RelativeOID)aSN1Primitive;
        return this.identifier.equals(aSN1RelativeOID.identifier);
    }

    @Override
    int encodedLength(boolean bl) {
        return ASN1OutputStream.getLengthOfEncodingDL(bl, this.getContents().length);
    }

    @Override
    void encode(ASN1OutputStream aSN1OutputStream, boolean bl) throws IOException {
        aSN1OutputStream.writeEncodingDL(bl, 13, this.getContents());
    }

    @Override
    boolean encodeConstructed() {
        return false;
    }

    private void doOutput(ByteArrayOutputStream byteArrayOutputStream) {
        OIDTokenizer oIDTokenizer = new OIDTokenizer(this.identifier);
        while (oIDTokenizer.hasMoreTokens()) {
            String string = oIDTokenizer.nextToken();
            if (string.length() <= 18) {
                ASN1RelativeOID.writeField(byteArrayOutputStream, Long.parseLong(string));
                continue;
            }
            ASN1RelativeOID.writeField(byteArrayOutputStream, new BigInteger(string));
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

    static ASN1RelativeOID createPrimitive(byte[] byArray, boolean bl) {
        return new ASN1RelativeOID(byArray, bl);
    }

    static boolean isValidIdentifier(String string, int n) {
        int n2 = 0;
        int n3 = string.length();
        while (--n3 >= n) {
            char c = string.charAt(n3);
            if (c == '.') {
                if (0 == n2 || n2 > 1 && string.charAt(n3 + 1) == '0') {
                    return false;
                }
                n2 = 0;
                continue;
            }
            if ('0' <= c && c <= '9') {
                ++n2;
                continue;
            }
            return false;
        }
        return 0 != n2 && (n2 <= true || string.charAt(n3 + 1) != '0');
    }

    static void writeField(ByteArrayOutputStream byteArrayOutputStream, long l) {
        byte[] byArray = new byte[9];
        int n = 8;
        byArray[n] = (byte)((int)l & 0x7F);
        while (l >= 128L) {
            byArray[--n] = (byte)((int)(l >>= 7) | 0x80);
        }
        byteArrayOutputStream.write(byArray, n, 9 - n);
    }

    static void writeField(ByteArrayOutputStream byteArrayOutputStream, BigInteger bigInteger) {
        int n = (bigInteger.bitLength() + 6) / 7;
        if (n == 0) {
            byteArrayOutputStream.write(0);
        } else {
            BigInteger bigInteger2 = bigInteger;
            byte[] byArray = new byte[n];
            for (int i = n - 1; i >= 0; --i) {
                byArray[i] = (byte)(bigInteger2.intValue() | 0x80);
                bigInteger2 = bigInteger2.shiftRight(7);
            }
            int n2 = n - 1;
            byArray[n2] = (byte)(byArray[n2] & 0x7F);
            byteArrayOutputStream.write(byArray, 0, byArray.length);
        }
    }
}

