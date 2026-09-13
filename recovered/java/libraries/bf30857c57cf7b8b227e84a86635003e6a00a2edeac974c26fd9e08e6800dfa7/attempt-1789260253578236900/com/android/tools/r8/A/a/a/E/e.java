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
public class e
extends a_0 {
    public int g;
    public int h;

    public e(int n2, int n3) {
        super(132);
        this.g = n2;
        this.h = n3;
    }

    @Override
    public int b() {
        return 10;
    }

    @Override
    public void a(u u3) {
        e e2 = this;
        int n2 = e2.g;
        u3.a(n2, e2.h);
        this.b(u3);
    }

    @Override
    public a_0 a(Map<k, k> map) {
        e e2 = this;
        int n2 = e2.g;
        return new e(n2, e2.h).a(this);
    }
}

