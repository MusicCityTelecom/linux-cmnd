/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.r8.A.a.a.E;

import com.android.tools.r8.A.a.a.E.k;
import com.android.tools.r8.A.a.a.E.z;
import com.android.tools.r8.A.a.a.s;
import com.android.tools.r8.A.a.a.u;
import com.android.tools.r8.a.a.a.e.a_0;
import java.util.List;
import java.util.Map;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class p
extends a_0 {
    public k g;
    public List<Integer> h;
    public List<k> i;

    public p(k k2, int[] nArray, k[] kArray) {
        super(171);
        this.g = k2;
        this.h = z.a(nArray);
        this.i = z.a(kArray);
    }

    @Override
    public int b() {
        return 12;
    }

    @Override
    public void a(u u3) {
        int n2 = this.h.size();
        int[] nArray = new int[n2];
        for (int i2 = 0; i2 < n2; ++i2) {
            nArray[i2] = this.h.get(i2);
        }
        n2 = this.i.size();
        s[] sArray = new s[n2];
        for (int i3 = 0; i3 < n2; ++i3) {
            sArray[i3] = this.i.get(i3).c();
        }
        u u4 = u3;
        u4.a(this.g.c(), nArray, sArray);
        this.b(u4);
    }

    @Override
    public a_0 a(Map<k, k> map) {
        p p3 = new p(map.get(this.g), null, a_0.a(this.i, map));
        p3.h.addAll(this.h);
        return p3.a(this);
    }
}

