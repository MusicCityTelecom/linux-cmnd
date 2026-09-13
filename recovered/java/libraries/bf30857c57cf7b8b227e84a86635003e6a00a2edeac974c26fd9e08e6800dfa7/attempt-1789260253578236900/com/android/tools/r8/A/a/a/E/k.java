/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.r8.A.a.a.E;

import com.android.tools.r8.A.a.a.s;
import com.android.tools.r8.A.a.a.u;
import com.android.tools.r8.a.a.a.e.a_0;
import java.util.Map;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class k
extends a_0 {
    private s g;

    public k() {
        super(-1);
    }

    @Override
    public int b() {
        return 8;
    }

    public s c() {
        if (this.g == null) {
            s s3;
            s s4 = s3;
            s3 = new s();
            this.g = s4;
        }
        return this.g;
    }

    @Override
    public void a(u u3) {
        u3.a(this.c());
    }

    @Override
    public a_0 a(Map<k, k> map) {
        return map.get(this);
    }

    public void d() {
        this.g = null;
    }
}

