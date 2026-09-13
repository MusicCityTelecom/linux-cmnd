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
public class h
extends a_0 {
    public int g;

    public h(int n2, int n3) {
        super(n2);
        this.g = n3;
    }

    @Override
    public int b() {
        return 1;
    }

    @Override
    public void a(u u3) {
        h h2 = this;
        int n2 = h2.a;
        u3.b(n2, h2.g);
        this.b(u3);
    }

    @Override
    public a_0 a(Map<k, k> map) {
        h h2 = this;
        int n2 = h2.a;
        return new h(n2, h2.g).a(this);
    }
}

