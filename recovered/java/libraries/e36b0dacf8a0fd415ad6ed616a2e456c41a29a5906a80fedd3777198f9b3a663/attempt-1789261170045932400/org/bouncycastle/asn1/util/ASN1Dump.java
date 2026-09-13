/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.asn1.util;

import org.bouncycastle.asn1.ASN1ApplicationSpecific;
import org.bouncycastle.asn1.ASN1BMPString;
import org.bouncycastle.asn1.ASN1BitString;
import org.bouncycastle.asn1.ASN1Boolean;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Enumerated;
import org.bouncycastle.asn1.ASN1External;
import org.bouncycastle.asn1.ASN1GeneralizedTime;
import org.bouncycastle.asn1.ASN1GraphicString;
import org.bouncycastle.asn1.ASN1IA5String;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Null;
import org.bouncycastle.asn1.ASN1NumericString;
import org.bouncycastle.asn1.ASN1ObjectDescriptor;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1PrintableString;
import org.bouncycastle.asn1.ASN1RelativeOID;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.ASN1T61String;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.ASN1UTCTime;
import org.bouncycastle.asn1.ASN1UTF8String;
import org.bouncycastle.asn1.ASN1Util;
import org.bouncycastle.asn1.ASN1VideotexString;
import org.bouncycastle.asn1.ASN1VisibleString;
import org.bouncycastle.asn1.BEROctetString;
import org.bouncycastle.asn1.BERSequence;
import org.bouncycastle.asn1.BERSet;
import org.bouncycastle.asn1.BERTaggedObject;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.DLBitString;
import org.bouncycastle.util.Strings;
import org.bouncycastle.util.encoders.Hex;

public class ASN1Dump {
    private static final String TAB = "    ";
    private static final int SAMPLE_SIZE = 32;

    static void _dumpAsString(String string, boolean bl, ASN1Primitive aSN1Primitive, StringBuffer stringBuffer) {
        String string2 = Strings.lineSeparator();
        if (aSN1Primitive instanceof ASN1Null) {
            stringBuffer.append(string);
            stringBuffer.append("NULL");
            stringBuffer.append(string2);
        } else if (aSN1Primitive instanceof ASN1Sequence) {
            stringBuffer.append(string);
            if (aSN1Primitive instanceof BERSequence) {
                stringBuffer.append("BER Sequence");
            } else if (aSN1Primitive instanceof DERSequence) {
                stringBuffer.append("DER Sequence");
            } else {
                stringBuffer.append("Sequence");
            }
            stringBuffer.append(string2);
            ASN1Sequence aSN1Sequence = (ASN1Sequence)aSN1Primitive;
            String string3 = string + TAB;
            int n = aSN1Sequence.size();
            for (int i = 0; i < n; ++i) {
                ASN1Dump._dumpAsString(string3, bl, aSN1Sequence.getObjectAt(i).toASN1Primitive(), stringBuffer);
            }
        } else if (aSN1Primitive instanceof ASN1Set) {
            stringBuffer.append(string);
            if (aSN1Primitive instanceof BERSet) {
                stringBuffer.append("BER Set");
            } else if (aSN1Primitive instanceof DERSet) {
                stringBuffer.append("DER Set");
            } else {
                stringBuffer.append("Set");
            }
            stringBuffer.append(string2);
            ASN1Set aSN1Set = (ASN1Set)aSN1Primitive;
            String string4 = string + TAB;
            int n = aSN1Set.size();
            for (int i = 0; i < n; ++i) {
                ASN1Dump._dumpAsString(string4, bl, aSN1Set.getObjectAt(i).toASN1Primitive(), stringBuffer);
            }
        } else if (aSN1Primitive instanceof ASN1ApplicationSpecific) {
            ASN1Dump._dumpAsString(string, bl, ((ASN1ApplicationSpecific)aSN1Primitive).getTaggedObject(), stringBuffer);
        } else if (aSN1Primitive instanceof ASN1TaggedObject) {
            stringBuffer.append(string);
            if (aSN1Primitive instanceof BERTaggedObject) {
                stringBuffer.append("BER Tagged ");
            } else if (aSN1Primitive instanceof DERTaggedObject) {
                stringBuffer.append("DER Tagged ");
            } else {
                stringBuffer.append("Tagged ");
            }
            ASN1TaggedObject aSN1TaggedObject = (ASN1TaggedObject)aSN1Primitive;
            stringBuffer.append(ASN1Util.getTagText(aSN1TaggedObject));
            if (!aSN1TaggedObject.isExplicit()) {
                stringBuffer.append(" IMPLICIT ");
            }
            stringBuffer.append(string2);
            String string5 = string + TAB;
            ASN1Dump._dumpAsString(string5, bl, aSN1TaggedObject.getBaseObject().toASN1Primitive(), stringBuffer);
        } else if (aSN1Primitive instanceof ASN1OctetString) {
            ASN1OctetString aSN1OctetString = (ASN1OctetString)aSN1Primitive;
            if (aSN1Primitive instanceof BEROctetString) {
                stringBuffer.append(string + "BER Constructed Octet String[" + aSN1OctetString.getOctets().length + "] ");
            } else {
                stringBuffer.append(string + "DER Octet String[" + aSN1OctetString.getOctets().length + "] ");
            }
            if (bl) {
                stringBuffer.append(ASN1Dump.dumpBinaryDataAsString(string, aSN1OctetString.getOctets()));
            } else {
                stringBuffer.append(string2);
            }
        } else if (aSN1Primitive instanceof ASN1ObjectIdentifier) {
            stringBuffer.append(string + "ObjectIdentifier(" + ((ASN1ObjectIdentifier)aSN1Primitive).getId() + ")" + string2);
        } else if (aSN1Primitive instanceof ASN1RelativeOID) {
            stringBuffer.append(string + "RelativeOID(" + ((ASN1RelativeOID)aSN1Primitive).getId() + ")" + string2);
        } else if (aSN1Primitive instanceof ASN1Boolean) {
            stringBuffer.append(string + "Boolean(" + ((ASN1Boolean)aSN1Primitive).isTrue() + ")" + string2);
        } else if (aSN1Primitive instanceof ASN1Integer) {
            stringBuffer.append(string + "Integer(" + ((ASN1Integer)aSN1Primitive).getValue() + ")" + string2);
        } else if (aSN1Primitive instanceof ASN1BitString) {
            ASN1BitString aSN1BitString = (ASN1BitString)aSN1Primitive;
            byte[] byArray = aSN1BitString.getBytes();
            int n = aSN1BitString.getPadBits();
            if (aSN1BitString instanceof DERBitString) {
                stringBuffer.append(string + "DER Bit String[" + byArray.length + ", " + n + "] ");
            } else if (aSN1BitString instanceof DLBitString) {
                stringBuffer.append(string + "DL Bit String[" + byArray.length + ", " + n + "] ");
            } else {
                stringBuffer.append(string + "BER Bit String[" + byArray.length + ", " + n + "] ");
            }
            if (bl) {
                stringBuffer.append(ASN1Dump.dumpBinaryDataAsString(string, byArray));
            } else {
                stringBuffer.append(string2);
            }
        } else if (aSN1Primitive instanceof ASN1IA5String) {
            stringBuffer.append(string + "IA5String(" + ((ASN1IA5String)aSN1Primitive).getString() + ") " + string2);
        } else if (aSN1Primitive instanceof ASN1UTF8String) {
            stringBuffer.append(string + "UTF8String(" + ((ASN1UTF8String)aSN1Primitive).getString() + ") " + string2);
        } else if (aSN1Primitive instanceof ASN1NumericString) {
            stringBuffer.append(string + "NumericString(" + ((ASN1NumericString)aSN1Primitive).getString() + ") " + string2);
        } else if (aSN1Primitive instanceof ASN1PrintableString) {
            stringBuffer.append(string + "PrintableString(" + ((ASN1PrintableString)aSN1Primitive).getString() + ") " + string2);
        } else if (aSN1Primitive instanceof ASN1VisibleString) {
            stringBuffer.append(string + "VisibleString(" + ((ASN1VisibleString)aSN1Primitive).getString() + ") " + string2);
        } else if (aSN1Primitive instanceof ASN1BMPString) {
            stringBuffer.append(string + "BMPString(" + ((ASN1BMPString)aSN1Primitive).getString() + ") " + string2);
        } else if (aSN1Primitive instanceof ASN1T61String) {
            stringBuffer.append(string + "T61String(" + ((ASN1T61String)aSN1Primitive).getString() + ") " + string2);
        } else if (aSN1Primitive instanceof ASN1GraphicString) {
            stringBuffer.append(string + "GraphicString(" + ((ASN1GraphicString)aSN1Primitive).getString() + ") " + string2);
        } else if (aSN1Primitive instanceof ASN1VideotexString) {
            stringBuffer.append(string + "VideotexString(" + ((ASN1VideotexString)aSN1Primitive).getString() + ") " + string2);
        } else if (aSN1Primitive instanceof ASN1UTCTime) {
            stringBuffer.append(string + "UTCTime(" + ((ASN1UTCTime)aSN1Primitive).getTime() + ") " + string2);
        } else if (aSN1Primitive instanceof ASN1GeneralizedTime) {
            stringBuffer.append(string + "GeneralizedTime(" + ((ASN1GeneralizedTime)aSN1Primitive).getTime() + ") " + string2);
        } else if (aSN1Primitive instanceof ASN1Enumerated) {
            ASN1Enumerated aSN1Enumerated = (ASN1Enumerated)aSN1Primitive;
            stringBuffer.append(string + "DER Enumerated(" + aSN1Enumerated.getValue() + ")" + string2);
        } else if (aSN1Primitive instanceof ASN1ObjectDescriptor) {
            ASN1ObjectDescriptor aSN1ObjectDescriptor = (ASN1ObjectDescriptor)aSN1Primitive;
            stringBuffer.append(string + "ObjectDescriptor(" + aSN1ObjectDescriptor.getBaseGraphicString().getString() + ") " + string2);
        } else if (aSN1Primitive instanceof ASN1External) {
            ASN1External aSN1External = (ASN1External)aSN1Primitive;
            stringBuffer.append(string + "External " + string2);
            String string6 = string + TAB;
            if (aSN1External.getDirectReference() != null) {
                stringBuffer.append(string6 + "Direct Reference: " + aSN1External.getDirectReference().getId() + string2);
            }
            if (aSN1External.getIndirectReference() != null) {
                stringBuffer.append(string6 + "Indirect Reference: " + aSN1External.getIndirectReference().toString() + string2);
            }
            if (aSN1External.getDataValueDescriptor() != null) {
                ASN1Dump._dumpAsString(string6, bl, aSN1External.getDataValueDescriptor(), stringBuffer);
            }
            stringBuffer.append(string6 + "Encoding: " + aSN1External.getEncoding() + string2);
            ASN1Dump._dumpAsString(string6, bl, aSN1External.getExternalContent(), stringBuffer);
        } else {
            stringBuffer.append(string + aSN1Primitive.toString() + string2);
        }
    }

    public static String dumpAsString(Object object) {
        return ASN1Dump.dumpAsString(object, false);
    }

    public static String dumpAsString(Object object, boolean bl) {
        ASN1Primitive aSN1Primitive;
        if (object instanceof ASN1Primitive) {
            aSN1Primitive = (ASN1Primitive)object;
        } else if (object instanceof ASN1Encodable) {
            aSN1Primitive = ((ASN1Encodable)object).toASN1Primitive();
        } else {
            return "unknown object type " + object.toString();
        }
        StringBuffer stringBuffer = new StringBuffer();
        ASN1Dump._dumpAsString("", bl, aSN1Primitive, stringBuffer);
        return stringBuffer.toString();
    }

    private static String dumpBinaryDataAsString(String string, byte[] byArray) {
        String string2 = Strings.lineSeparator();
        StringBuffer stringBuffer = new StringBuffer();
        string = string + TAB;
        stringBuffer.append(string2);
        for (int i = 0; i < byArray.length; i += 32) {
            if (byArray.length - i > 32) {
                stringBuffer.append(string);
                stringBuffer.append(Strings.fromByteArray(Hex.encode(byArray, i, 32)));
                stringBuffer.append(TAB);
                stringBuffer.append(ASN1Dump.calculateAscString(byArray, i, 32));
                stringBuffer.append(string2);
                continue;
            }
            stringBuffer.append(string);
            stringBuffer.append(Strings.fromByteArray(Hex.encode(byArray, i, byArray.length - i)));
            for (int j = byArray.length - i; j != 32; ++j) {
                stringBuffer.append("  ");
            }
            stringBuffer.append(TAB);
            stringBuffer.append(ASN1Dump.calculateAscString(byArray, i, byArray.length - i));
            stringBuffer.append(string2);
        }
        return stringBuffer.toString();
    }

    private static String calculateAscString(byte[] byArray, int n, int n2) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = n; i != n + n2; ++i) {
            if (byArray[i] < 32 || byArray[i] > 126) continue;
            stringBuffer.append((char)byArray[i]);
        }
        return stringBuffer.toString();
    }
}

