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
public class j
extends a_0 {
    public k g;

    public j(int n2, k k2) {
        super(n2);
        this.g = k2;
    }

    @Override
    public int b() {
        return 7;
    }

    @Override
    public void a(u u3) {
        u u4 = u3;
        u4.a(this.a, this.g.c());
        this.b(u4);
    }

    @Override
    public a_0 a(Map<k, k> map) {
        j j2 = this;
        int n2 = j2.a;
        return new j(n2, map.get(j2.g)).a(this);
    }
}

