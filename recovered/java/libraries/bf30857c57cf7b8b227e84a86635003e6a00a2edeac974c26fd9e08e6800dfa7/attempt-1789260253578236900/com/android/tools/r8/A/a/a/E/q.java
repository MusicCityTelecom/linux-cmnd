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
public class q
extends a_0 {
    public String g;
    public String h;
    public String i;
    public boolean j;

    public q(int n2, String string, String string2, String string3, boolean bl) {
        super(n2);
        this.g = string;
        this.h = string2;
        this.i = string3;
        this.j = bl;
    }

    @Override
    public int b() {
        return 5;
    }

    @Override
    public void a(u u3) {
        q q3 = this;
        int n2 = q3.a;
        String string = q3.g;
        String string2 = q3.h;
        String string3 = q3.i;
        boolean bl = q3.j;
        u3.a(n2, string, string2, string3, bl);
        this.b(u3);
    }

    @Override
    public a_0 a(Map<k, k> object) {
        q q3 = this;
        int n2 = q3.a;
        object = q3.g;
        String string = q3.h;
        String string2 = q3.i;
        boolean bl = q3.j;
        return new q(n2, (String)object, string, string2, bl).a(this);
    }
}

