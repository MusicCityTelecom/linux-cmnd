/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1ApplicationSpecific
 *  org.bouncycastle.asn1.ASN1Boolean
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.ASN1Enumerated
 *  org.bouncycastle.asn1.ASN1Integer
 *  org.bouncycastle.asn1.ASN1OctetString
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.ASN1Sequence
 *  org.bouncycastle.asn1.ASN1Set
 *  org.bouncycastle.asn1.ASN1TaggedObject
 *  org.bouncycastle.asn1.ASN1UTF8String
 *  org.bouncycastle.asn1.DERBitString
 *  org.bouncycastle.util.BigIntegers
 *  org.bouncycastle.util.Pack
 *  org.bouncycastle.util.Strings
 *  org.bouncycastle.util.encoders.Hex
 */
package org.bouncycastle.oer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1ApplicationSpecific;
import org.bouncycastle.asn1.ASN1Boolean;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Enumerated;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.ASN1UTF8String;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.oer.BitBuilder;
import org.bouncycastle.oer.OERDefinition;
import org.bouncycastle.oer.OEROptional;
import org.bouncycastle.util.BigIntegers;
import org.bouncycastle.util.Pack;
import org.bouncycastle.util.Strings;
import org.bouncycastle.util.encoders.Hex;

public class OEROutputStream {
    private final OutputStream out;
    private static final int[] bits = new int[]{1, 2, 4, 8, 16, 32, 64, 128};
    protected PrintWriter debugOutput = null;

    public static OEROutputStream create(OutputStream outputStream) {
        return new OEROutputStream(outputStream);
    }

    OEROutputStream(OutputStream outputStream) {
        this.out = outputStream;
    }

    public void write(ASN1Encodable aSN1Encodable, OERDefinition.Element element) throws IOException {
        if (aSN1Encodable == OEROptional.ABSENT) {
            return;
        }
        if (aSN1Encodable instanceof OEROptional) {
            this.write(((OEROptional)aSN1Encodable).get(), element);
            return;
        }
        aSN1Encodable = aSN1Encodable.toASN1Primitive();
        switch (element.baseType) {
            case SEQ: {
                Object object;
                OERDefinition.Element element2;
                int n;
                ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance((Object)aSN1Encodable);
                int n2 = 7;
                int n3 = 0;
                if (element.extensionsInDefinition) {
                    if (element.hasPopulatedExtension()) {
                        n3 |= bits[n2];
                    }
                    --n2;
                }
                for (n = 0; n < element.children.size(); ++n) {
                    element2 = element.children.get(n);
                    if (n2 < 0) {
                        this.out.write(n3);
                        n2 = 7;
                        n3 = 0;
                    }
                    object = aSN1Sequence.getObjectAt(n);
                    if (element2.explicit && object instanceof OEROptional) {
                        throw new IllegalStateException("absent sequence element that is required by oer definition");
                    }
                    if (element2.explicit) continue;
                    ASN1Encodable aSN1Encodable2 = aSN1Sequence.getObjectAt(n);
                    if (element2.getDefaultValue() != null) {
                        if (aSN1Encodable2 instanceof OEROptional) {
                            if (((OEROptional)aSN1Encodable2).isDefined() && !((OEROptional)aSN1Encodable2).get().equals(element2.defaultValue)) {
                                n3 |= bits[n2];
                            }
                        } else if (!element2.getDefaultValue().equals(aSN1Encodable2)) {
                            n3 |= bits[n2];
                        }
                    } else if (object != OEROptional.ABSENT) {
                        n3 |= bits[n2];
                    }
                    --n2;
                }
                if (n2 != 7) {
                    this.out.write(n3);
                }
                for (n = 0; n < element.children.size(); ++n) {
                    element2 = aSN1Sequence.getObjectAt(n);
                    object = element.children.get(n);
                    if (((OERDefinition.Element)object).getDefaultValue() != null && ((OERDefinition.Element)object).getDefaultValue().equals(element2)) continue;
                    this.write((ASN1Encodable)element2, (OERDefinition.Element)object);
                }
                this.out.flush();
                this.debugPrint(element.appendLabel(""));
                break;
            }
            case SEQ_OF: {
                Enumeration enumeration;
                if (aSN1Encodable instanceof ASN1Set) {
                    enumeration = ((ASN1Set)aSN1Encodable).getObjects();
                    this.encodeQuantity(((ASN1Set)aSN1Encodable).size());
                } else if (aSN1Encodable instanceof ASN1Sequence) {
                    enumeration = ((ASN1Sequence)aSN1Encodable).getObjects();
                    this.encodeQuantity(((ASN1Sequence)aSN1Encodable).size());
                } else {
                    throw new IllegalStateException("encodable at for SEQ_OF is not a container");
                }
                while (enumeration.hasMoreElements()) {
                    Object e = enumeration.nextElement();
                    this.write((ASN1Encodable)e, element.getFirstChid());
                }
                this.out.flush();
                this.debugPrint(element.appendLabel(""));
                break;
            }
            case CHOICE: {
                int n;
                ASN1Primitive aSN1Primitive = aSN1Encodable.toASN1Primitive();
                BitBuilder bitBuilder = new BitBuilder();
                if (aSN1Primitive instanceof ASN1ApplicationSpecific) {
                    n = ((ASN1ApplicationSpecific)aSN1Primitive).getApplicationTag();
                    bitBuilder.writeBit(0).writeBit(1);
                    aSN1Primitive = ((ASN1ApplicationSpecific)aSN1Primitive).getEnclosedObject();
                } else if (aSN1Primitive instanceof ASN1TaggedObject) {
                    ASN1TaggedObject aSN1TaggedObject = (ASN1TaggedObject)aSN1Primitive;
                    int n4 = aSN1TaggedObject.getTagClass();
                    bitBuilder.writeBit(n4 & 0x80).writeBit(n4 & 0x40);
                    n = aSN1TaggedObject.getTagNo();
                    aSN1Primitive = aSN1TaggedObject.getBaseObject().toASN1Primitive();
                } else {
                    throw new IllegalStateException("only support tagged objects");
                }
                if (n <= 63) {
                    bitBuilder.writeBits(n, 6);
                } else {
                    bitBuilder.writeBits(255L, 6);
                    bitBuilder.write7BitBytes(n);
                }
                if (this.debugOutput != null) {
                    if (aSN1Primitive instanceof ASN1ApplicationSpecific) {
                        this.debugPrint(element.appendLabel("AS"));
                    } else if (aSN1Primitive instanceof ASN1TaggedObject) {
                        this.debugPrint(element.appendLabel("CS"));
                    }
                }
                bitBuilder.writeAndClear(this.out);
                this.write((ASN1Encodable)aSN1Primitive, element.children.get(n));
                this.out.flush();
                break;
            }
            case ENUM: {
                BigInteger bigInteger = aSN1Encodable instanceof ASN1Integer ? ASN1Integer.getInstance((Object)aSN1Encodable).getValue() : ASN1Enumerated.getInstance((Object)aSN1Encodable).getValue();
                for (OERDefinition.Element element3 : element.children) {
                    if (!element3.enumValue.equals(bigInteger)) continue;
                    if (bigInteger.compareTo(BigInteger.valueOf(127L)) > 0) {
                        byte[] byArray = bigInteger.toByteArray();
                        int n = 0x80 | byArray.length & 0xFF;
                        this.out.write(n);
                        this.out.write(byArray);
                    } else {
                        this.out.write(bigInteger.intValue() & 0x7F);
                    }
                    this.out.flush();
                    this.debugPrint(element.appendLabel(element.rangeExpression()));
                    return;
                }
                throw new IllegalArgumentException("enum value " + bigInteger + " " + Hex.toHexString((byte[])bigInteger.toByteArray()) + " no in defined child list");
            }
            case INT: {
                ASN1Integer aSN1Integer = ASN1Integer.getInstance((Object)aSN1Encodable);
                int n = element.intBytesForRange();
                if (n > 0) {
                    byte[] byArray = BigIntegers.asUnsignedByteArray((int)n, (BigInteger)aSN1Integer.getValue());
                    switch (n) {
                        case 1: 
                        case 2: 
                        case 4: 
                        case 8: {
                            this.out.write(byArray);
                            break;
                        }
                        default: {
                            throw new IllegalStateException("unknown uint length " + n);
                        }
                    }
                } else if (n < 0) {
                    byte[] byArray;
                    BigInteger bigInteger = aSN1Integer.getValue();
                    switch (n) {
                        case -1: {
                            byArray = new byte[]{BigIntegers.byteValueExact((BigInteger)bigInteger)};
                            break;
                        }
                        case -2: {
                            byArray = Pack.shortToBigEndian((short)BigIntegers.shortValueExact((BigInteger)bigInteger));
                            break;
                        }
                        case -4: {
                            byArray = Pack.intToBigEndian((int)BigIntegers.intValueExact((BigInteger)bigInteger));
                            break;
                        }
                        case -8: {
                            byArray = Pack.longToBigEndian((long)BigIntegers.longValueExact((BigInteger)bigInteger));
                            break;
                        }
                        default: {
                            throw new IllegalStateException("unknown twos compliment length");
                        }
                    }
                    this.out.write(byArray);
                } else {
                    byte[] byArray = element.isLowerRangeZero() ? BigIntegers.asUnsignedByteArray((BigInteger)aSN1Integer.getValue()) : aSN1Integer.getValue().toByteArray();
                    this.encodeLength(byArray.length);
                    this.out.write(byArray);
                }
                this.debugPrint(element.appendLabel(element.rangeExpression()));
                this.out.flush();
                break;
            }
            case OCTET_STRING: {
                ASN1OctetString aSN1OctetString = ASN1OctetString.getInstance((Object)aSN1Encodable);
                byte[] byArray = aSN1OctetString.getOctets();
                if (element.isFixedLength()) {
                    this.out.write(byArray);
                } else {
                    this.encodeLength(byArray.length);
                    this.out.write(byArray);
                }
                this.debugPrint(element.appendLabel(element.rangeExpression()));
                this.out.flush();
                break;
            }
            case UTF8_STRING: {
                ASN1UTF8String aSN1UTF8String = ASN1UTF8String.getInstance((Object)aSN1Encodable);
                byte[] byArray = Strings.toUTF8ByteArray((String)aSN1UTF8String.getString());
                this.encodeLength(byArray.length);
                this.out.write(byArray);
                this.debugPrint(element.appendLabel(""));
                this.out.flush();
                break;
            }
            case BIT_STRING: {
                DERBitString dERBitString = DERBitString.getInstance((Object)aSN1Encodable);
                byte[] byArray = dERBitString.getBytes();
                if (element.isFixedLength()) {
                    this.out.write(byArray);
                    this.debugPrint(element.appendLabel(element.rangeExpression()));
                } else {
                    int n = dERBitString.getPadBits();
                    this.encodeLength(byArray.length + 1);
                    this.out.write(n);
                    this.out.write(byArray);
                    this.debugPrint(element.appendLabel(element.rangeExpression()));
                }
                this.out.flush();
                break;
            }
            case NULL: {
                break;
            }
            case EXTENSION: {
                ASN1OctetString aSN1OctetString = ASN1OctetString.getInstance((Object)aSN1Encodable);
                byte[] byArray = aSN1OctetString.getOctets();
                if (element.isFixedLength()) {
                    this.out.write(byArray);
                } else {
                    this.encodeLength(byArray.length);
                    this.out.write(byArray);
                }
                this.debugPrint(element.appendLabel(element.rangeExpression()));
                this.out.flush();
                break;
            }
            case ENUM_ITEM: {
                break;
            }
            case BOOLEAN: {
                this.debugPrint(element.label);
                ASN1Boolean aSN1Boolean = ASN1Boolean.getInstance((Object)aSN1Encodable);
                if (aSN1Boolean.isTrue()) {
                    this.out.write(255);
                } else {
                    this.out.write(0);
                }
                this.out.flush();
            }
        }
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

    private void encodeLength(long l) throws IOException {
        if (l <= 127L) {
            this.out.write((int)l);
        } else {
            byte[] byArray = BigIntegers.asUnsignedByteArray((BigInteger)BigInteger.valueOf(l));
            this.out.write(byArray.length | 0x80);
            this.out.write(byArray);
        }
    }

    private void encodeQuantity(long l) throws IOException {
        byte[] byArray = BigIntegers.asUnsignedByteArray((BigInteger)BigInteger.valueOf(l));
        this.out.write(byArray.length);
        this.out.write(byArray);
    }

    public static int byteLength(long l) {
        int n;
        long l2 = -72057594037927936L;
        for (n = 8; n > 0 && (l & l2) == 0L; --n) {
            l <<= 8;
        }
        return n;
    }
}

