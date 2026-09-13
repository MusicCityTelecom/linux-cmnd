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
public class y
extends a_0 {
    public String g;

    public y(int n2, String string) {
        super(n2);
        this.g = string;
    }

    @Override
    public int b() {
        return 3;
    }

    @Override
    public void a(u u3) {
        y y3 = this;
        int n2 = y3.a;
        u3.a(n2, y3.g);
        this.b(u3);
    }

    @Override
    public a_0 a(Map<k, k> map) {
        y y3 = this;
        int n2 = y3.a;
        return new y(n2, y3.g).a(this);
    }
}

