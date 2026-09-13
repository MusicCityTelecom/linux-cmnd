/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.ASN1Sequence
 *  org.bouncycastle.asn1.DERSequence
 *  org.bouncycastle.util.Arrays
 */
package org.bouncycastle.oer.its;

import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.util.Arrays;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
class Utils {
    Utils() {
    }

    static byte[] octetStringFixed(byte[] byArray, int n) {
        if (byArray.length != n) {
            throw new IllegalArgumentException("octet string out of range");
        }
        return byArray;
    }

    static byte[] octetStringFixed(byte[] byArray) {
        if (byArray.length < 1 || byArray.length > 32) {
            throw new IllegalArgumentException("octet string out of range");
        }
        return Arrays.clone((byte[])byArray);
    }

    static ASN1Sequence toSequence(List list) {
        return new DERSequence(list.toArray(new ASN1Encodable[0]));
    }

    static ASN1Sequence toSequence(ASN1Encodable ... aSN1EncodableArray) {
        return new DERSequence(aSN1EncodableArray);
    }

    static <T> List<T> fillList(final Class<T> clazz, final ASN1Sequence aSN1Sequence) {
        return (List)AccessController.doPrivileged(new PrivilegedAction<List<T>>(){

            @Override
            public List<T> run() {
                try {
                    ArrayList arrayList = new ArrayList();
                    Iterator iterator = aSN1Sequence.iterator();
                    while (iterator.hasNext()) {
                        Method method = clazz.getMethod("getInstance", Object.class);
                        arrayList.add(clazz.cast(method.invoke(null, iterator.next())));
                    }
                    return arrayList;
                }
                catch (Exception exception) {
                    throw new IllegalStateException("could not invoke getInstance on type " + exception.getMessage(), exception);
                }
            }
        });
    }
}

