/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.r8.A.a.a.E;

import java.util.ArrayList;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
abstract class z {
    static <T> List<T> a(int n2) {
        ArrayList<Object> arrayList;
        ArrayList<Object> arrayList2 = arrayList;
        arrayList = new ArrayList<Object>(n2);
        for (int i2 = 0; i2 < n2; ++i2) {
            arrayList2.add(null);
        }
        return arrayList2;
    }

    static <T> List<T> a(T[] TArray) {
        ArrayList<T> arrayList;
        if (TArray == null) {
            return new ArrayList();
        }
        ArrayList<T> arrayList2 = arrayList;
        arrayList = new ArrayList<T>(TArray.length);
        int n2 = TArray.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            arrayList2.add(TArray[i2]);
        }
        return arrayList2;
    }

    static List<Integer> a(int[] nArray) {
        ArrayList<Integer> arrayList;
        if (nArray == null) {
            return new ArrayList<Integer>();
        }
        ArrayList<Integer> arrayList2 = arrayList;
        arrayList = new ArrayList<Integer>(nArray.length);
        int n2 = nArray.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            arrayList2.add(nArray[i2]);
        }
        return arrayList2;
    }

    static <T> List<T> a(int n2, T[] TArray) {
        ArrayList<T> arrayList;
        ArrayList<T> arrayList2 = arrayList;
        arrayList = new ArrayList<T>(n2);
        for (int i2 = 0; i2 < n2; ++i2) {
            arrayList2.add(TArray[i2]);
        }
        return arrayList2;
    }
}

