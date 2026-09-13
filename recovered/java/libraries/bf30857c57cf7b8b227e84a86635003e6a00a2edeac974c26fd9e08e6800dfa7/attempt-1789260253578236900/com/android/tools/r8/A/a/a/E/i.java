/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.r8.A.a.a.E;

import com.android.tools.r8.A.a.a.E.k;
import com.android.tools.r8.A.a.a.q;
import com.android.tools.r8.A.a.a.u;
import com.android.tools.r8.a.a.a.e.a_0;
import java.util.Map;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class i
extends a_0 {
    public String g;
    public String h;
    public q i;
    public Object[] j;

    public i(String string, String string2, q q3, Object ... objectArray) {
        super(186);
        this.g = string;
        this.h = string2;
        this.i = q3;
        this.j = objectArray;
    }

    @Override
    public int b() {
        return 6;
    }

    @Override
    public void a(u u3) {
        i i2 = string;
        i i3 = string;
        String string = i3.g;
        String string2 = i3.h;
        q q3 = i3.i;
        Object[] objectArray = i3.j;
        u3.a(string, string2, q3, objectArray);
        i2.b(u3);
    }

    @Override
    public a_0 a(Map<k, k> object) {
        i i2 = string;
        String string = i2.g;
        object = i2.h;
        q q3 = i2.i;
        Object[] objectArray = i2.j;
        return new i(string, (String)object, q3, objectArray).a((a_0)((Object)string));
    }
}

