/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.r8.A.a.a.E;

import com.android.tools.r8.A.a.a.C;
import com.android.tools.r8.A.a.a.E.b;

public class x
extends b {
    public int c;
    public C d;

    public x(int n2, C c2, String string) {
        x x3 = this;
        x3(458752, n2, c2, string);
        if (x3.getClass() == x.class) {
            return;
        }
        throw new IllegalStateException();
    }

    public x(int n2, int n3, C c2, String string) {
        super(n2, string);
        this.c = n3;
        this.d = c2;
    }
}

