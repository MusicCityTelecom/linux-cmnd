/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.r8.A.a.a.E;

import com.android.tools.r8.A.a.a.E.k;
import com.android.tools.r8.A.a.a.u;
import com.android.tools.r8.a.a.a.e.a_0;
import java.util.Map;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class A
extends a_0 {
    public int g;

    public A(int n2, int n3) {
        super(n2);
        this.g = n3;
    }

    @Override
    public int b() {
        return 2;
    }

    @Override
    public void a(u u3) {
        A a2 = this;
        int n2 = a2.a;
        u3.d(n2, a2.g);
        this.b(u3);
    }

    @Override
    public a_0 a(Map<k, k> map) {
        A a2 = this;
        int n2 = a2.a;
        return new A(n2, a2.g).a(this);
    }
}

