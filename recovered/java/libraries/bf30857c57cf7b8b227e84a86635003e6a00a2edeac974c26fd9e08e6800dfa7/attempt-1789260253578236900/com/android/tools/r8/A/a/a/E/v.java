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
public class v
extends a_0 {
    public int g;
    public int h;
    public k i;
    public List<k> j;

    public v(int n2, int n3, k k2, k ... kArray) {
        super(170);
        this.g = n2;
        this.h = n3;
        this.i = k2;
        this.j = z.a(kArray);
    }

    @Override
    public int b() {
        return 11;
    }

    @Override
    public void a(u u3) {
        int n2 = this.j.size();
        s[] sArray = new s[n2];
        for (int i2 = 0; i2 < n2; ++i2) {
            sArray[i2] = this.j.get(i2).c();
        }
        u u4 = u3;
        v v3 = this;
        int n3 = v3.g;
        n2 = v3.h;
        u4.a(n3, n2, this.i.c(), sArray);
        this.b(u4);
    }

    @Override
    public a_0 a(Map<k, k> map) {
        int n2 = kArray.g;
        int n3 = kArray.h;
        k k2 = map.get(kArray.i);
        k[] kArray = a_0.a(kArray.j, map);
        return new v(n2, n3, k2, kArray).a((a_0)kArray);
    }
}

