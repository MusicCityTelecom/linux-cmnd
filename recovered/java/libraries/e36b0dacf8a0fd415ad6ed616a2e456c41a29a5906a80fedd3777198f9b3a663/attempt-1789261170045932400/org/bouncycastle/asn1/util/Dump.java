/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.asn1.util;

import java.io.FileInputStream;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.util.ASN1Dump;

public class Dump {
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void main(String[] stringArray) throws Exception {
        try (FileInputStream fileInputStream = new FileInputStream(stringArray[0]);){
            ASN1InputStream aSN1InputStream = new ASN1InputStream(fileInputStream);
            ASN1Primitive aSN1Primitive = null;
            while ((aSN1Primitive = aSN1InputStream.readObject()) != null) {
                System.out.println(ASN1Dump.dumpAsString(aSN1Primitive));
            }
        }
    }
}

