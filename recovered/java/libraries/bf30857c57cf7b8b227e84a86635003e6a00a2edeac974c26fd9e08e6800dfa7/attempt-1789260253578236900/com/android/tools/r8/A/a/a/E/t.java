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
public class t
extends a_0 {
    public String g;
    public int h;

    public t(String string, int n2) {
        super(197);
        this.g = string;
        this.h = n2;
    }

    @Override
    public int b() {
        return 13;
    }

    @Override
    public void a(u u3) {
        t t3 = string;
        t t4 = string;
        String string = t4.g;
        u3.a(string, t4.h);
        t3.b(u3);
    }

    @Override
    public a_0 a(Map<k, k> map) {
        t t3 = string;
        String string = t3.g;
        return new t(string, t3.h).a((a_0)((Object)string));
    }
}

