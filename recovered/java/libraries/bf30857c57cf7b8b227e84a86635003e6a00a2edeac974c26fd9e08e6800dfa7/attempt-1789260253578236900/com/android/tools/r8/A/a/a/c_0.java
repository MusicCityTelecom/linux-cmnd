/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.r8.a.a.a;

import com.android.tools.r8.A.a.a.d;

/*
 * Renamed from com.android.tools.r8.A.a.a.c
 */
final class c_0 {
    private int a;
    private d[] b = new d[6];

    c_0() {
    }

    void a(d d2) {
        while (d2 != null) {
            int n2;
            block4: {
                for (n2 = 0; n2 < this.a; ++n2) {
                    if (!this.b[n2].a.equals(d2.a)) continue;
                    n2 = 1;
                    break block4;
                }
                n2 = 0;
            }
            if (n2 == 0) {
                n2 = this.a;
                d[] dArray = this.b;
                if (n2 >= this.b.length) {
                    dArray = new d[dArray.length + 6];
                    System.arraycopy(dArray, 0, dArray, 0, n2);
                    this.b = dArray;
                }
                n2 = this.a;
                this.a = n2 + 1;
                this.b[n2] = d2;
            }
            d2 = d2.c;
        }
    }

    d[] a() {
        int n2 = this.a;
        d[] dArray = new d[n2];
        System.arraycopy(this.b, 0, dArray, 0, n2);
        return dArray;
    }
}

