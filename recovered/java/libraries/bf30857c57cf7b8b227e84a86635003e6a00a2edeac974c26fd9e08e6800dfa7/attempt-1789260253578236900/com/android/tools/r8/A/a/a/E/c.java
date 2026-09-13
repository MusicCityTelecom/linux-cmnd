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
public class c
extends a_0 {
    public String g;
    public String h;
    public String i;

    public c(int n2, String string, String string2, String string3) {
        super(n2);
        this.g = string;
        this.h = string2;
        this.i = string3;
    }

    @Override
    public int b() {
        return 4;
    }

    @Override
    public void a(u u3) {
        c c2 = this;
        int n2 = c2.a;
        String string = c2.g;
        String string2 = c2.h;
        String string3 = c2.i;
        u3.a(n2, string, string2, string3);
        this.b(u3);
    }

    @Override
    public a_0 a(Map<k, k> object) {
        c c2 = this;
        int n2 = c2.a;
        object = c2.g;
        String string = c2.h;
        String string2 = c2.i;
        return new c(n2, (String)object, string, string2).a(this);
    }
}

