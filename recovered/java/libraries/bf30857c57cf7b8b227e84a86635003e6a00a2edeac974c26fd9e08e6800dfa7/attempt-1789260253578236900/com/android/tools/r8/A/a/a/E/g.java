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
public class g
extends a_0 {
    public g(int n2) {
        super(n2);
    }

    @Override
    public int b() {
        return 0;
    }

    @Override
    public void a(u u3) {
        u u4 = u3;
        u4.a(this.a);
        this.b(u4);
    }

    @Override
    public a_0 a(Map<k, k> map) {
        return new g(this.a).a(this);
    }
}

