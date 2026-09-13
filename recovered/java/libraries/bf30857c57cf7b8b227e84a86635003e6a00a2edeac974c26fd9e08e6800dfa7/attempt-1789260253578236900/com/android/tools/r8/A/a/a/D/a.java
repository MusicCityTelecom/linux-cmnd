/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.r8.A.a.a.D;

import com.android.tools.r8.A.a.a.D.b;
import com.android.tools.r8.A.a.a.E.k;
import com.android.tools.r8.a.a.a.e.a_0;
import java.util.AbstractMap;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
class a
extends AbstractMap<k, k> {
    final a a;
    final BitSet b;
    final Map<k, k> c;
    final k d;
    final /* synthetic */ b e;

    a(b b2, a object, BitSet bitSet) {
        HashMap hashMap;
        this.e = b2;
        Object object2 = object;
        while (object2 != null) {
            if (((a)object2).b != bitSet) {
                object2 = ((a)object2).a;
                continue;
            }
            throw new IllegalArgumentException("Recursive invocation of " + bitSet);
        }
        a a2 = this;
        a2.a = object;
        a2.b = bitSet;
        if (object == null) {
            object = null;
        } else {
            k k2;
            object = k2;
            k2 = new k();
        }
        a a3 = this;
        a3.d = object;
        object = hashMap;
        hashMap = new HashMap();
        a3.c = object;
        object = null;
        for (int i2 = 0; i2 < b2.v.c(); ++i2) {
            object2 = b2.v.a(i2);
            if (((a_0)object2).b() == 8) {
                object2 = (k)object2;
                if (object == null) {
                    k k3;
                    object = k3;
                    k3 = new k();
                }
                this.c.put((k)object2, (k)object);
                continue;
            }
            if (this.a(i2) != this) continue;
            object = null;
        }
    }

    a a(int n2) {
        if (!a3.b.get(n2)) {
            return null;
        }
        if (!a3.e.F.get(n2)) {
            return a3;
        }
        a a2 = a3.a;
        while (a2 != null) {
            if (a2.b.get(n2)) {
                a a3 = a2;
            }
            a2 = a2.a;
        }
        return a3;
    }

    k b(k k2) {
        a a2 = this;
        return a2.a((int)a2.e.v.b((a_0)k2)).c.get(k2);
    }

    k a(k k2) {
        return this.c.get(k2);
    }

    @Override
    public Set<Map.Entry<k, k>> entrySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(Object object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object get(Object object) {
        return this.b((k)object);
    }
}

