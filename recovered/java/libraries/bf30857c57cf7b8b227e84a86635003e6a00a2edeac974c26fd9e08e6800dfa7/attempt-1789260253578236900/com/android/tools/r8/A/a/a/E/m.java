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
public class m
extends a_0 {
    public int g;
    public k h;

    public m(int n2, k k2) {
        super(-1);
        this.g = n2;
        this.h = k2;
    }

    @Override
    public int b() {
        return 15;
    }

    @Override
    public void a(u u3) {
        u3.b(this.g, this.h.c());
    }

    @Override
    public a_0 a(Map<k, k> map) {
        m m3 = this;
        int n2 = m3.g;
        return new m(n2, map.get(m3.h));
    }
}

