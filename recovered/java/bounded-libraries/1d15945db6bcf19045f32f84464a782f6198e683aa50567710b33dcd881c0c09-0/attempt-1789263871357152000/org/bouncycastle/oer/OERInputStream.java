/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.ASN1EncodableVector
 *  org.bouncycastle.asn1.ASN1Enumerated
 *  org.bouncycastle.asn1.ASN1Integer
 *  org.bouncycastle.asn1.ASN1Object
 *  org.bouncycastle.asn1.DERBitString
 *  org.bouncycastle.asn1.DERNull
 *  org.bouncycastle.asn1.DEROctetString
 *  org.bouncycastle.asn1.DERSequence
 *  org.bouncycastle.asn1.DERTaggedObject
 *  org.bouncycastle.asn1.DERUTF8String
 *  org.bouncycastle.util.BigIntegers
 *  org.bouncycastle.util.Pack
 *  org.bouncycastle.util.Strings
 *  org.bouncycastle.util.encoders.Hex
 *  org.bouncycastle.util.io.Streams
 */
package org.bouncycastle.oer;

import java.io.EOFException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Enumerated;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.DERUTF8String;
import org.bouncycastle.oer.OERDefinition;
import org.bouncycastle.oer.OEROptional;
import org.bouncycastle.util.BigIntegers;
import org.bouncycastle.util.Pack;
import org.bouncycastle.util.Strings;
import org.bouncycastle.util.encoders.Hex;
import org.bouncycastle.util.io.Streams;

public class OERInputStream
extends FilterInputStream {
    private static final int[] bits = new int[]{1, 2, 4, 8, 16, 32, 64, 128};
    private int maxByteAllocation = 0x100000;
    protected PrintWriter debugOutput = null;

    public OERInputStream(InputStream inputStream) {
        super(inputStream);
    }

    public OERInputStream(InputStream inputStream, int n) {
        super(inputStream);
        this.maxByteAllocation = n;
    }

    private int countOptionalChildTypes(OERDefinition.Element element) {
        int n = 0;
        for (OERDefinition.Element element2 : element.children) {
            n += element2.explicit ? 0 : 1;
        }
        return n;
    }

    /*
     * Unable to fully structure code
     */
    public ASN1Object parse(OERDefinition.Element var1_1) throws Exception {
        switch (1.$SwitchMap$org$bouncycastle$oer$OERDefinition$BaseType[var1_1.baseType.ordinal()]) {
            case 1: {
                var2_2 = LengthInfo.access$000(this.readLength());
                var3_10 = this.allocateArray(var2_2);
                if (Streams.readFully((InputStream)this, (byte[])var3_10) != var3_10.length) {
                    throw new IOException("could not read all of count of seq-of values");
                }
                var4_18 = BigIntegers.fromUnsignedByteArray((byte[])var3_10).intValue();
                this.debugPrint(var1_1.appendLabel("(len = " + var4_18 + ")"));
                var5_22 = new ASN1EncodableVector();
                for (var6_27 = 0; var6_27 < var4_18; ++var6_27) {
                    var5_22.add((ASN1Encodable)this.parse(var1_1.children.get(0)));
                }
                return new DERSequence(var5_22);
            }
            case 2: {
                var2_3 = this.sequence(this.countOptionalChildTypes(var1_1), var1_1.hasDefaultChildren(), var1_1.extensionsInDefinition);
                this.debugPrint(var1_1.appendLabel(var2_3.toString()));
                var3_11 = new ASN1EncodableVector();
                for (var4_19 = 0; var4_19 < var1_1.children.size(); ++var4_19) {
                    var5_23 = var1_1.children.get(var4_19);
                    if (var5_23.explicit) {
                        var3_11.add((ASN1Encodable)this.parse(var5_23));
                        continue;
                    }
                    if (var2_3.hasOptional(var1_1.optionalOrDefaultChildrenInOrder().indexOf(var5_23))) {
                        var3_11.add((ASN1Encodable)OEROptional.getInstance(this.parse(var5_23)));
                        continue;
                    }
                    if (var5_23.getDefaultValue() != null) {
                        var3_11.add(var5_23.defaultValue);
                        this.debugPrint("Using default.");
                        continue;
                    }
                    var3_11.add(this.absent(var5_23));
                }
                return new DERSequence(var3_11);
            }
            case 3: {
                var2_4 = this.choice();
                this.debugPrint(var1_1.appendLabel(var2_4.toString()));
                if (var2_4.isContextSpecific()) {
                    var3_12 = var1_1.children.get(var2_4.getTag());
                    return new DERTaggedObject(var2_4.tag, (ASN1Encodable)this.parse(var1_1.children.get(var2_4.getTag())));
                }
                if (var2_4.isApplicationTagClass()) {
                    throw new IllegalStateException("Unimplemented tag type");
                }
                if (var2_4.isPrivateTagClass()) {
                    throw new IllegalStateException("Unimplemented tag type");
                }
                if (var2_4.isUniversalTagClass()) {
                    var2_4.getTag();
                } else {
                    throw new IllegalStateException("Unimplemented tag type");
                }
            }
            case 4: {
                var2_4 = this.enumeration();
                this.debugPrint(var1_1.appendLabel("ENUM(" + var2_4 + ") = " + var1_1.children.get((int)var2_4.intValue()).label));
                return new ASN1Enumerated((BigInteger)var2_4);
            }
            case 5: {
                var4_20 = var1_1.intBytesForRange();
                if (var4_20 == 0) ** GOTO lbl73
                var2_5 = this.allocateArray(Math.abs(var4_20));
                Streams.readFully((InputStream)this, (byte[])var2_5);
                switch (var2_5.length) {
                    case 1: {
                        var3_13 = BigInteger.valueOf(var2_5[0]);
                        ** GOTO lbl85
                    }
                    case 2: {
                        var3_13 = BigInteger.valueOf(Pack.bigEndianToShort((byte[])var2_5, (int)0));
                        ** GOTO lbl85
                    }
                    case 4: {
                        var3_13 = BigInteger.valueOf(Pack.bigEndianToInt((byte[])var2_5, (int)0));
                        ** GOTO lbl85
                    }
                    case 8: {
                        var3_13 = BigInteger.valueOf(Pack.bigEndianToLong((byte[])var2_5, (int)0));
                        ** GOTO lbl85
                    }
                    default: {
                        throw new IllegalStateException("Unknown size");
                    }
                }
lbl73:
                // 1 sources

                if (var1_1.isLowerRangeZero()) {
                    var5_24 = this.readLength();
                    var2_5 = this.allocateArray(LengthInfo.access$000(var5_24));
                    Streams.readFully((InputStream)this, (byte[])var2_5);
                    var3_13 = var2_5.length == 0 ? BigInteger.ZERO : BigIntegers.fromUnsignedByteArray((byte[])var2_5);
                } else {
                    var5_25 = this.readLength();
                    var2_5 = this.allocateArray(LengthInfo.access$000(var5_25));
                    Streams.readFully((InputStream)this, (byte[])var2_5);
                    var3_13 = var2_5.length == 0 ? BigInteger.ZERO : new BigInteger(var2_5);
                }
lbl85:
                // 6 sources

                if (this.debugOutput != null) {
                    this.debugPrint(var1_1.appendLabel("INTEGER(" + var2_5.length + " " + var3_13.toString(16) + ")"));
                }
                return new ASN1Integer(var3_13);
            }
            case 6: {
                var3_14 = 0;
                var3_14 = var1_1.upperBound != null && var1_1.upperBound.equals(var1_1.lowerBound) != false ? var1_1.upperBound.intValue() : LengthInfo.access$000(this.readLength());
                var2_6 = this.allocateArray(var3_14);
                if (Streams.readFully((InputStream)this, (byte[])var2_6) != var3_14) {
                    throw new IOException("did not read all of " + var1_1.label);
                }
                if (this.debugOutput != null) {
                    this.debugPrint(var1_1.appendLabel("OCTET STRING (" + var2_6.length + ") = " + Hex.toHexString((byte[])var2_6, (int)0, (int)Math.min(var2_6.length, 32))));
                }
                return new DEROctetString(var2_6);
            }
            case 7: {
                var2_7 = this.allocateArray(LengthInfo.access$000(this.readLength()));
                if (Streams.readFully((InputStream)this, (byte[])var2_7) != var2_7.length) {
                    throw new IOException("could not read all of utf 8 string");
                }
                var3_15 = Strings.fromUTF8ByteArray((byte[])var2_7);
                if (this.debugOutput != null) {
                    this.debugPrint(var1_1.appendLabel("UTF8 String (" + var2_7.length + ") = " + var3_15));
                }
                return new DERUTF8String(var3_15);
            }
            case 8: {
                var2_8 = var1_1.isFixedLength() != false ? new byte[var1_1.lowerBound.intValue() / 8] : (BigInteger.ZERO.compareTo(var1_1.upperBound) > 0 ? this.allocateArray(var1_1.upperBound.intValue() / 8) : this.allocateArray(LengthInfo.access$000(this.readLength()) / 8));
                Streams.readFully((InputStream)this, (byte[])var2_8);
                if (this.debugOutput != null) {
                    var3_16 = new StringBuffer();
                    var3_16.append("BIT STRING(" + var2_8.length * 8 + ") = ");
                    for (var4_21 = 0; var4_21 != var2_8.length; ++var4_21) {
                        var5_26 = var2_8[var4_21];
                        for (var6_28 = 0; var6_28 < 8; ++var6_28) {
                            var3_16.append((var5_26 & 128) > 0 ? "1" : "0");
                            var5_26 = (byte)(var5_26 << 1);
                        }
                    }
                    this.debugPrint(var1_1.appendLabel(var3_16.toString()));
                }
                return new DERBitString(var2_8);
            }
            case 9: {
                this.debugPrint(var1_1.appendLabel("NULL"));
                return DERNull.INSTANCE;
            }
            case 10: {
                var2_9 = this.readLength();
                var3_17 = new byte[LengthInfo.access$000(var2_9)];
                if (Streams.readFully((InputStream)this, (byte[])var3_17) != LengthInfo.access$000(var2_9)) {
                    throw new IOException("could not read all of count of open value in choice (...) ");
                }
                this.debugPrint("ext " + LengthInfo.access$000(var2_9) + " " + Hex.toHexString((byte[])var3_17));
                return new DEROctetString(var3_17);
            }
        }
        throw new IllegalStateException("Unhandled type " + (Object)var1_1.baseType);
    }

    private ASN1Encodable absent(OERDefinition.Element element) {
        this.debugPrint(element.appendLabel("Absent"));
        return OEROptional.ABSENT;
    }

    private byte[] allocateArray(int n) {
        if (n > this.maxByteAllocation) {
            throw new IllegalArgumentException("required byte array size " + n + " was greater than " + this.maxByteAllocation);
        }
        return new byte[n];
    }

    public BigInteger parseInt(boolean bl, int n) throws Exception {
        byte[] byArray = new byte[n];
        int n2 = Streams.readFully((InputStream)this, (byte[])byArray);
        if (n2 != byArray.length) {
            throw new IllegalStateException("integer not fully read");
        }
        return bl ? new BigInteger(1, byArray) : new BigInteger(byArray);
    }

    public BigInteger uint8() throws Exception {
        return this.parseInt(true, 1);
    }

    public BigInteger uint16() throws Exception {
        return this.parseInt(true, 2);
    }

    public BigInteger uint32() throws Exception {
        return this.parseInt(true, 4);
    }

    public BigInteger uint64() throws Exception {
        return this.parseInt(false, 8);
    }

    public BigInteger int8() throws Exception {
        return this.parseInt(false, 1);
    }

    public BigInteger int16() throws Exception {
        return this.parseInt(false, 2);
    }

    public BigInteger int32() throws Exception {
        return this.parseInt(false, 4);
    }

    public BigInteger int64() throws Exception {
        return this.parseInt(false, 8);
    }

    public LengthInfo readLength() throws Exception {
        boolean bl = false;
        int n = this.read();
        if (n == -1) {
            throw new EOFException("expecting length");
        }
        if ((n & 0x80) == 0) {
            return new LengthInfo(BigInteger.valueOf(n & 0x7F), true);
        }
        byte[] byArray = new byte[n & 0x7F];
        if (Streams.readFully((InputStream)this, (byte[])byArray) != byArray.length) {
            throw new EOFException("did not read all bytes of length definition");
        }
        String string = Hex.toHexString((byte[])byArray);
        return new LengthInfo(BigIntegers.fromUnsignedByteArray((byte[])byArray), false);
    }

    public BigInteger enumeration() throws Exception {
        int n = this.read();
        if (n == -1) {
            throw new EOFException("expecting prefix of enumeration");
        }
        if ((n & 0x80) == 128) {
            int n2 = n & 0x7F;
            if (n2 == 0) {
                return BigInteger.ZERO;
            }
            byte[] byArray = new byte[n2];
            int n3 = Streams.readFully((InputStream)this, (byte[])byArray);
            if (n3 != byArray.length) {
                throw new EOFException("unable to fully read integer component of enumeration");
            }
            return new BigInteger(1, byArray);
        }
        return BigInteger.valueOf(n);
    }

    public Sequence sequence(int n, boolean bl, boolean bl2) throws Exception {
        return new Sequence(this, n, bl, bl2);
    }

    public Choice choice() throws Exception {
        return new Choice(this);
    }

    protected void debugPrint(String string) {
        if (this.debugOutput != null) {
            StackTraceElement[] stackTraceElementArray = Thread.currentThread().getStackTrace();
            int n = -1;
            for (int i = 0; i != stackTraceElementArray.length; ++i) {
                StackTraceElement stackTraceElement = stackTraceElementArray[i];
                if (stackTraceElement.getMethodName().equals("debugPrint")) {
                    n = 0;
                    continue;
                }
                if (!stackTraceElement.getClassName().contains("OERInput")) continue;
                ++n;
            }
            while (n > 0) {
                this.debugOutput.append("    ");
                --n;
            }
            this.debugOutput.append(string).append("\n");
            this.debugOutput.flush();
        }
    }

    public static class Choice
    extends OERInputStream {
        final int preamble = this.read();
        final int tag;
        final int tagClass;

        public Choice(InputStream inputStream) throws Exception {
            super(inputStream);
            if (this.preamble < 0) {
                throw new EOFException("expecting preamble byte of choice");
            }
            this.tagClass = this.preamble & 0xC0;
            int n = this.preamble & 0x3F;
            if (n >= 63) {
                n = 0;
                int n2 = 0;
                do {
                    if ((n2 = inputStream.read()) < 0) {
                        throw new EOFException("expecting further tag bytes");
                    }
                    n <<= 7;
                    n |= n2 & 0x7F;
                } while ((n2 & 0x80) != 0);
            }
            this.tag = n;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("CHOICE(");
            switch (this.tagClass) {
                case 0: {
                    stringBuilder.append("Universal ");
                    break;
                }
                case 64: {
                    stringBuilder.append("Application ");
                    break;
                }
                case 192: {
                    stringBuilder.append("Private ");
                    break;
                }
                case 128: {
                    stringBuilder.append("ContextSpecific ");
                }
            }
            stringBuilder.append("Tag = " + this.tag);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }

        public int getTagClass() {
            return this.tagClass;
        }

        public int getTag() {
            return this.tag;
        }

        public boolean isContextSpecific() {
            return this.tagClass == 128;
        }

        public boolean isUniversalTagClass() {
            return this.tagClass == 0;
        }

        public boolean isApplicationTagClass() {
            return this.tagClass == 64;
        }

        public boolean isPrivateTagClass() {
            return this.tagClass == 192;
        }
    }

    private final class LengthInfo {
        private final BigInteger length;
        private final boolean shortForm;

        public LengthInfo(BigInteger bigInteger, boolean bl) {
            this.length = bigInteger;
            this.shortForm = bl;
        }

        private int intLength() {
            return this.length.intValue();
        }

        static /* synthetic */ int access$000(LengthInfo lengthInfo) {
            return lengthInfo.intLength();
        }
    }

    public static class Sequence
    extends OERInputStream {
        final int preamble;
        private final boolean[] optionalPresent;
        private final boolean extensionFlagSet;

        public Sequence(InputStream inputStream, int n, boolean bl, boolean bl2) throws IOException {
            super(inputStream);
            if (n == 0 && !bl2 && !bl) {
                this.preamble = 0;
                this.optionalPresent = new boolean[0];
                this.extensionFlagSet = false;
                return;
            }
            this.preamble = inputStream.read();
            if (this.preamble < 0) {
                throw new EOFException("expecting preamble byte of sequence");
            }
            this.extensionFlagSet = bl2 && (this.preamble & 0x80) == 128;
            int n2 = bl2 ? 6 : 7;
            this.optionalPresent = new boolean[n];
            int n3 = this.preamble;
            for (int i = 0; i < this.optionalPresent.length; ++i) {
                if (n2 < 0) {
                    n3 = inputStream.read();
                    if (n3 < 0) {
                        throw new EOFException("expecting mask byte sequence");
                    }
                    n2 = 7;
                }
                this.optionalPresent[i] = (n3 & bits[n2]) > 0;
                --n2;
            }
        }

        public boolean hasOptional(int n) {
            return this.optionalPresent[n];
        }

        public boolean hasExtension() {
            return this.extensionFlagSet;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("SEQ(");
            stringBuilder.append(this.hasExtension() ? "Ext " : "");
            for (int i = 0; i < this.optionalPresent.length; ++i) {
                if (this.optionalPresent[i]) {
                    stringBuilder.append("1");
                    continue;
                }
                stringBuilder.append("0");
            }
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }
}

