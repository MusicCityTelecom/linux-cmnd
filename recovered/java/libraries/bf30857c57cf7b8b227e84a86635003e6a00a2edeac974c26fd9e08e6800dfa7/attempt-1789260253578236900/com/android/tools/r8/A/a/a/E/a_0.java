/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.r8.a.a.a.e;

import com.android.tools.r8.A.a.a.C;
import com.android.tools.r8.A.a.a.E.k;
import com.android.tools.r8.A.a.a.E.x;
import com.android.tools.r8.A.a.a.u;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 * Renamed from com.android.tools.r8.A.a.a.E.a
 */
public abstract class a_0 {
    protected int a;
    public List<x> b;
    public List<x> c;
    a_0 d;
    a_0 e;
    int f;

    protected a_0(int n2) {
        this.a = n2;
        this.f = -1;
    }

    static k[] a(List<k> list, Map<k, k> map) {
        int n2 = list.size();
        k[] kArray = new k[n2];
        for (int i2 = 0; i2 < n2; ++i2) {
            kArray[i2] = map.get(list.get(i2));
        }
        return kArray;
    }

    public int a() {
        return this.a;
    }

    public abstract int b();

    public abstract void a(u var1);

    protected final void b(u u3) {
        String string;
        C c2;
        int n2;
        int n3;
        List<x> list = this.b;
        if (list != null) {
            n3 = list.size();
            for (int i2 = 0; i2 < n3; ++i2) {
                x x3 = this.b.get(i2);
                n2 = x3.c;
                c2 = x3.d;
                string = x3.a;
                x3.a(u3.a(n2, c2, string, true));
            }
        }
        if ((list = this.c) != null) {
            n3 = list.size();
            for (int i3 = 0; i3 < n3; ++i3) {
                x x4 = this.c.get(i3);
                n2 = x4.c;
                c2 = x4.d;
                string = x4.a;
                x4.a(u3.a(n2, c2, string, false));
            }
        }
    }

    public abstract a_0 a(Map<k, k> var1);

    protected final a_0 a(a_0 a_02) {
        String string;
        C c2;
        int n2;
        x x3;
        int n3;
        int n4;
        if (a_02.b != null) {
            this.b = new ArrayList<x>();
            n4 = a_02.b.size();
            for (n3 = 0; n3 < n4; ++n3) {
                x x4;
                x x5 = a_02.b.get(n3);
                x3 = x4;
                x x6 = x5;
                n2 = x6.c;
                c2 = x6.d;
                string = x6.a;
                x5.a(new x(n2, c2, string));
                this.b.add(x3);
            }
        }
        if (a_02.c != null) {
            this.c = new ArrayList<x>();
            n4 = a_02.c.size();
            for (n3 = 0; n3 < n4; ++n3) {
                x x7;
                x x8 = a_02.c.get(n3);
                x3 = x7;
                x x9 = x8;
                n2 = x9.c;
                c2 = x9.d;
                string = x9.a;
                x8.a(new x(n2, c2, string));
                this.c.add(x3);
            }
        }
        return this;
    }
}

