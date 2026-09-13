/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.r8.A.a.a.E;

import com.android.tools.r8.A.a.a.E.s;
import java.util.ArrayList;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
class r
extends ArrayList<Object> {
    final /* synthetic */ s a;

    r(s s3, int n2) {
        this.a = s3;
        super(n2);
    }

    @Override
    public boolean add(Object object) {
        this.a.q = object;
        return super.add(object);
    }
}

