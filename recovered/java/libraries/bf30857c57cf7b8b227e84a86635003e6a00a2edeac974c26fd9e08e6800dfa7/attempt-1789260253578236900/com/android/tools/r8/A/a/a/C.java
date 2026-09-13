/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.r8.A.a.a;

import com.android.tools.r8.A.a.a.e;

public final class C {
    private final byte[] a;
    private final int b;

    C(byte[] byArray, int n2) {
        this.a = byArray;
        this.b = n2;
    }

    static void a(C c2, e e2) {
        if (c2 == null) {
            e2.b(0);
        } else {
            int n2 = c2.b;
            e2.a(c2.a, n2, c2.a[n2] * 2 + 1);
        }
    }

    public String toString() {
        StringBuilder stringBuilder;
        int n2 = this.a[this.b];
        StringBuilder stringBuilder2 = stringBuilder;
        stringBuilder = new StringBuilder(n2 * 2);
        block6: for (int i2 = 0; i2 < n2; ++i2) {
            byte[] byArray = this.a;
            int n3 = this.b;
            int n4 = i2 * 2;
            switch (this.a[n3 + n4 + 1]) {
                default: {
                    throw new AssertionError();
                }
                case 3: {
                    stringBuilder2.append(byArray[n3 + n4 + 2]).append(';');
                    continue block6;
                }
                case 2: {
                    stringBuilder2.append('*');
                    continue block6;
                }
                case 1: {
                    stringBuilder2.append('.');
                    continue block6;
                }
                case 0: {
                    stringBuilder2.append('[');
                }
            }
        }
        return stringBuilder2.toString();
    }
}

