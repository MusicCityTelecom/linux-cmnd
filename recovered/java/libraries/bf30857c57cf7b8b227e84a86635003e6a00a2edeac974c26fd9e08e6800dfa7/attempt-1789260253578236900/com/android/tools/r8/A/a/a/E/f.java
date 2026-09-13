/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.r8.A.a.a.E;

import com.android.tools.r8.A.a.a.E.k;
import com.android.tools.r8.A.a.a.u;
import com.android.tools.r8.a.a.a.e.a_0;

public class f {
    private int a;
    private a_0 b;
    private a_0 c;
    a_0[] d;

    public int c() {
        return this.a;
    }

    public a_0 a() {
        return this.c;
    }

    public a_0 a(int n2) {
        if (n2 >= 0 && n2 < this.a) {
            if (this.d == null) {
                this.d = this.d();
            }
            return this.d[n2];
        }
        throw new IndexOutOfBoundsException();
    }

    public int b(a_0 a_02) {
        if (this.d == null) {
            this.d = this.d();
        }
        return a_02.f;
    }

    public void a(u u3) {
        Object object = ((f)object).b;
        while (object != null) {
            Object object2 = object;
            ((a_0)object2).a(u3);
            object = ((a_0)object2).e;
        }
    }

    public a_0[] d() {
        f f2 = this;
        int n2 = 0;
        a_0 a_02 = f2.b;
        a_0[] a_0Array = new a_0[f2.a];
        while (a_02 != null) {
            a_0Array[n2] = a_02;
            int n3 = n2 + 1;
            a_02.f = n2;
            a_0 a_03 = a_02.e;
            n2 = n3;
            a_02 = a_03;
        }
        return a_0Array;
    }

    public void a(a_0 a_02) {
        f f2 = this;
        ++f2.a;
        a_0 a_03 = f2.c;
        if (a_03 == null) {
            f f3 = this;
            f3.b = a_02;
            f3.c = a_02;
        } else {
            a_03.e = a_02;
            a_02.d = a_03;
        }
        f f4 = this;
        f4.c = a_02;
        f4.d = null;
        a_02.f = 0;
    }

    public void b() {
        Object object = ((f)object).b;
        while (object != null) {
            if (object instanceof k) {
                ((k)object).d();
            }
            object = ((a_0)object).e;
        }
    }
}

