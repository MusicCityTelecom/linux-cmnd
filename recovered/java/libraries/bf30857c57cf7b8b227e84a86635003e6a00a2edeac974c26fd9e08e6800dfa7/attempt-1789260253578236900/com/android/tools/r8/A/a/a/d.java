/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.r8.A.a.a;

import com.android.tools.r8.A.a.a.A;
import com.android.tools.r8.A.a.a.e;
import com.android.tools.r8.A.a.a.f;
import com.android.tools.r8.A.a.a.i;

public class d {
    public final String a;
    private byte[] b;
    d c;

    protected d(String string) {
        this.a = string;
    }

    static int a(A a2, int n2, int n3) {
        int n4 = 0;
        if ((n2 & 0x1000) != 0 && a2.e() < 49) {
            a2.d("Synthetic");
            n4 = 6;
        }
        if (n3 != 0) {
            a2.d("Signature");
            n4 += 8;
        }
        if ((n2 & 0x20000) != 0) {
            a2.d("Deprecated");
            n4 += 6;
        }
        return n4;
    }

    static void a(A a2, int n2, int n3, e e2) {
        if ((n2 & 0x1000) != 0 && a2.e() < 49) {
            e2.d(a2.d("Synthetic")).c(0);
        }
        if (n3 != 0) {
            e2.d(a2.d("Signature")).c(2).d(n3);
        }
        if ((n2 & 0x20000) != 0) {
            e2.d(a2.d("Deprecated")).c(0);
        }
    }

    protected d a(f f2, int n2, int n3) {
        d d2;
        d d3 = d2;
        d3(((d)object).a);
        byte[] byArray = new byte[n3];
        Object object = byArray;
        d3.b = byArray;
        System.arraycopy(f2.b, n2, object, 0, n3);
        return d2;
    }

    final int a() {
        int n2 = 0;
        while (d2 != null) {
            ++n2;
            d d2 = d2.c;
        }
        return n2;
    }

    final int a(A a2) {
        return this.a(a2, null, 0, -1, -1);
    }

    final int a(A a2, byte[] byArray, int n2, int n3, int n4) {
        i cfr_ignored_0 = a2.a;
        int n5 = 0;
        while (d2 != null) {
            a2.d(d2.a);
            n5 += d2.b.length + 6;
            d d2 = d2.c;
        }
        return n5;
    }

    final void a(A a2, e e2) {
        this.a(a2, null, 0, -1, -1, e2);
    }

    final void a(A a2, byte[] byArray, int n2, int n3, int n4, e e2) {
        i cfr_ignored_0 = a2.a;
        while (object != null) {
            d d2 = object;
            e e3 = e2;
            d d3 = object;
            Object object = d3.b;
            int n5 = d3.b.length;
            e3.d(a2.d(d3.a)).c(n5);
            e3.a((byte[])object, 0, n5);
            object = d2.c;
        }
    }
}

