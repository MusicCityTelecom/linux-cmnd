/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.r8.A.a.a.E;

import com.android.tools.r8.A.a.a.C;
import com.android.tools.r8.A.a.a.E.k;
import com.android.tools.r8.A.a.a.E.x;
import com.android.tools.r8.A.a.a.E.z;
import com.android.tools.r8.A.a.a.s;
import com.android.tools.r8.A.a.a.u;
import java.util.List;

public class n
extends x {
    public List<k> e;
    public List<k> f;
    public List<Integer> g;

    public n(int n2, int n3, C c2, k[] kArray, k[] kArray2, int[] nArray, String string) {
        super(n2, n3, c2, string);
        this.e = z.a(kArray);
        this.f = z.a(kArray2);
        this.g = z.a(nArray);
    }

    public void a(u object, boolean bl) {
        n n2 = this;
        int n3 = n2.e.size();
        s[] sArray = new s[n3];
        s[] sArray2 = new s[n2.f.size()];
        int[] nArray = new int[n2.g.size()];
        for (int i2 = 0; i2 < n3; ++i2) {
            sArray[i2] = this.e.get(i2).c();
            sArray2[i2] = this.f.get(i2).c();
            nArray[i2] = this.g.get(i2);
        }
        u u3 = object;
        n n4 = this;
        int n5 = n4.c;
        object = n4.d;
        String string = n4.a;
        this.a(u3.a(n5, (C)object, sArray, sArray2, nArray, string, bl));
    }
}

