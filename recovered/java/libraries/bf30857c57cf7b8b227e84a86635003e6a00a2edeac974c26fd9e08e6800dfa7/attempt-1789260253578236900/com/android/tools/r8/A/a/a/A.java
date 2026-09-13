/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.r8.A.a.a;

import com.android.tools.r8.A.a.a.B;
import com.android.tools.r8.A.a.a.e;
import com.android.tools.r8.A.a.a.f;
import com.android.tools.r8.A.a.a.i;
import com.android.tools.r8.A.a.a.j;
import com.android.tools.r8.A.a.a.q;
import com.android.tools.r8.A.a.a.z;

final class A {
    final i a;
    private final f b;
    private int c;
    private String d;
    private int e;
    private z[] f;
    private int g;
    private e h;
    private int i;
    private e j;
    private int k;
    private z[] l;

    A(i i2) {
        A a2 = this;
        a2.a = i2;
        a2.b = null;
        a2.f = new z[256];
        a2.g = 1;
        a2.h = new e();
    }

    private z b(int n2) {
        return this.f[n2 % this.f.length];
    }

    private z b(z z3) {
        z[] zArray = this.f;
        if (this.e > this.f.length * 3 / 4) {
            int n2 = zArray.length * 2 + 1;
            z[] zArray2 = new z[n2];
            for (int i2 = (v292378) - 1; i2 >= 0; --i2) {
                z z4 = this.f[i2];
                while (z4 != null) {
                    z z5 = z4;
                    int n3 = z5.h % n2;
                    z z6 = z5.i;
                    z4.i = zArray2[n3];
                    zArray2[n3] = z4;
                    z4 = z6;
                }
            }
            this.f = zArray2;
        }
        ++this.e;
        int n4 = z3.h;
        zArray = this.f;
        z3.i = zArray[n4 %= zArray.length];
        this.f[n4] = z3;
        return z3;
    }

    private z a(int n2, String string, String string2, String string3) {
        int n3 = n2 + string.hashCode() * string2.hashCode() * string3.hashCode() & Integer.MAX_VALUE;
        z z3 = this.b(n3);
        while (z3 != null) {
            if (z3.b == n2 && z3.h == n3 && z3.c.equals(string) && z3.d.equals(string2) && z3.e.equals(string3)) {
                return z3;
            }
            z3 = z3.i;
        }
        A a2 = this;
        int n4 = this.a((int)7, (String)string).a;
        a2.h.b(n2, n4, this.a(string2, string3));
        int n5 = this.g;
        this.g = n5 + 1;
        return a2.b(new z(n5, n2, string, string2, string3, 0L, n3));
    }

    private z a(int n2, int n3) {
        int n4 = A.c(n2, n3);
        z z3 = this.b(n4);
        while (z3 != null) {
            if (z3.b == n2 && z3.h == n4 && z3.f == (long)n3) {
                return z3;
            }
            z3 = z3.i;
        }
        A a2 = this;
        a2.h.b(n2).c(n3);
        int n5 = this.g;
        this.g = n5 + 1;
        long l2 = n3;
        return a2.b(new z(n5, n2, l2, n4));
    }

    private z a(int n2, long l2) {
        int n3 = n2 + (int)l2 + (int)(l2 >>> 32) & Integer.MAX_VALUE;
        z z3 = this.b(n3);
        while (z3 != null) {
            if (z3.b == n2 && z3.h == n3 && z3.f == l2) {
                return z3;
            }
            z3 = z3.i;
        }
        A a2 = this;
        int n4 = a2.g;
        a2.h.b(n2).a(l2);
        a2.g += 2;
        return a2.b(new z(n4, n2, l2, n3));
    }

    private z a(int n2, String string, String string2, int n3) {
        int n4 = n2 + string.hashCode() * string2.hashCode() * (n3 + 1) & Integer.MAX_VALUE;
        z z3 = this.b(n4);
        while (z3 != null) {
            if (z3.b == n2 && z3.h == n4 && z3.f == (long)n3 && z3.d.equals(string) && z3.e.equals(string2)) {
                return z3;
            }
            z3 = z3.i;
        }
        A a2 = this;
        a2.h.b(n2, n3, this.a(string, string2));
        int n5 = this.g;
        this.g = n5 + 1;
        long l2 = n3;
        return a2.b(new z(n5, n2, null, string, string2, l2, n4));
    }

    private z a(int n2, String string) {
        int n3 = n2 + string.hashCode() & Integer.MAX_VALUE;
        z z3 = this.b(n3);
        while (z3 != null) {
            if (z3.b == n2 && z3.h == n3 && z3.e.equals(string)) {
                return z3;
            }
            z3 = z3.i;
        }
        A a2 = this;
        a2.h.b(n2, this.d(string));
        int n4 = this.g;
        this.g = n4 + 1;
        return a2.b(new z(n4, n2, string, n3));
    }

    private int a(z z3) {
        if (this.l == null) {
            this.l = new z[16];
        }
        z[] zArray = this.l;
        if (this.k == this.l.length) {
            zArray = new z[zArray.length * 2];
            int n2 = zArray.length;
            System.arraycopy(zArray, 0, zArray, 0, n2);
            this.l = zArray;
        }
        int n3 = this.k;
        this.k = n3 + 1;
        this.l[n3] = z3;
        return this.b((z)z3).a;
    }

    private static int c(int n2, int n3) {
        return n2 + n3 & Integer.MAX_VALUE;
    }

    f f() {
        return this.b;
    }

    int e() {
        return this.c;
    }

    String b() {
        return this.d;
    }

    int b(int n2, String string) {
        this.c = n2;
        this.d = string;
        return this.a((int)7, (String)string).a;
    }

    int c() {
        return this.g;
    }

    int d() {
        return this.h.b;
    }

    void b(e e2) {
        e e3 = ((A)object).h;
        Object object = e3.a;
        int n2 = e3.b;
        e2.d(((A)object).g).a((byte[])object, 0, n2);
    }

    int a() {
        if (this.j != null) {
            A a2 = this;
            a2.d("BootstrapMethods");
            return a2.j.b + 8;
        }
        return 0;
    }

    void a(e e2) {
        if (((A)object).j != null) {
            e e3 = e2.d(((A)object).d("BootstrapMethods")).c(((A)object).j.b + 2).d(((A)object).i);
            e e4 = ((A)object).j;
            Object object = e4.a;
            int n2 = e4.b;
            e3.a((byte[])object, 0, n2);
        }
    }

    z a(Object object) {
        if (object instanceof Integer) {
            return ((A)((Object)string)).a(3, (int)((Integer)object));
        }
        if (object instanceof Byte) {
            return ((A)((Object)string)).a(3, ((Byte)object).intValue());
        }
        if (object instanceof Character) {
            return ((A)((Object)string)).a(3, (int)((Character)object).charValue());
        }
        if (object instanceof Short) {
            return ((A)((Object)string)).a(3, ((Short)object).intValue());
        }
        if (object instanceof Boolean) {
            return ((A)((Object)string)).a(3, ((Boolean)object).booleanValue() ? 1 : 0);
        }
        if (object instanceof Float) {
            return ((A)((Object)string)).a(4, Float.floatToRawIntBits(((Float)object).floatValue()));
        }
        if (object instanceof Long) {
            long l2 = (Long)object;
            return ((A)((Object)string)).a(5, l2);
        }
        if (object instanceof Double) {
            long l3 = Double.doubleToRawLongBits((Double)object);
            return ((A)((Object)string)).a(6, l3);
        }
        if (object instanceof String) {
            return ((A)((Object)string)).a(8, (String)object);
        }
        if (object instanceof B) {
            int n2 = ((B)(object = (B)object)).c();
            if (n2 == 10) {
                return ((A)((Object)string)).a(7, ((B)object).b());
            }
            if (n2 == 11) {
                return ((A)((Object)string)).a(16, ((B)object).a());
            }
            return ((A)((Object)string)).a(7, ((B)object).a());
        }
        if (object instanceof q) {
            q q3 = (q)object;
            int n3 = q3.d();
            object = q3.c();
            String string = q3.b();
            String string2 = q3.a();
            boolean bl = q3.e();
            return ((A)((Object)string)).a(n3, (String)object, string, string2, bl);
        }
        if (object instanceof j) {
            A a2 = string;
            j j2 = (j)object;
            String string = j2.d();
            object = j2.c();
            q q4 = j2.a();
            int n4 = a2.a((q)q4, (Object[])j2.b()).a;
            return a2.a(17, string, (String)object, n4);
        }
        throw new IllegalArgumentException("value " + object);
    }

    z a(String string) {
        return this.a(7, string);
    }

    z a(String string, String string2, String string3) {
        return this.a(9, string, string2, string3);
    }

    /*
     * WARNING - void declaration
     */
    z a(String string, String string2, String string3, boolean bl) {
        void var4_7;
        if (bl) {
            int n2 = 11;
        } else {
            int n3 = 10;
        }
        return this.a((int)var4_7, string, string2, string3);
    }

    z a(int n2) {
        return this.a(3, n2);
    }

    z a(float f2) {
        return this.a(4, Float.floatToRawIntBits(f2));
    }

    z a(long l2) {
        return this.a(5, l2);
    }

    z a(double d2) {
        long l2 = Double.doubleToRawLongBits(d2);
        return this.a(6, l2);
    }

    int a(String string, String string2) {
        int n2 = string.hashCode() * string2.hashCode() + 12 & Integer.MAX_VALUE;
        z z3 = this.b(n2);
        while (z3 != null) {
            if (z3.b == 12 && z3.h == n2 && z3.d.equals(string) && z3.e.equals(string2)) {
                return z3.a;
            }
            z3 = z3.i;
        }
        A a2 = this;
        int n3 = this.d(string);
        int n4 = this.d(string2);
        a2.h.b(12, n3, n4);
        int n5 = this.g;
        this.g = n5 + 1;
        return a2.b((z)new z((int)n5, (int)12, (String)string, (String)string2, (int)n2)).a;
    }

    int d(String string) {
        int n2 = 1 + string.hashCode() & Integer.MAX_VALUE;
        z z3 = this.b(n2);
        while (z3 != null) {
            if (z3.b == 1 && z3.h == n2 && z3.e.equals(string)) {
                return z3.a;
            }
            z3 = z3.i;
        }
        A a2 = this;
        a2.h.b(1).a(string);
        int n3 = this.g;
        this.g = n3 + 1;
        return a2.b((z)new z((int)n3, (int)1, (String)string, (int)n2)).a;
    }

    z a(int n2, String string, String string2, String string3, boolean bl) {
        int n3 = string.hashCode() * string2.hashCode() * string3.hashCode() * n2 + 15 & Integer.MAX_VALUE;
        z n4 = this.b(n3);
        while (n4 != null) {
            if (n4.b == 15 && n4.h == n3 && n4.f == (long)n2 && n4.c.equals(string) && n4.d.equals(string2) && n4.e.equals(string3)) {
                return n4;
            }
            n4 = n4.i;
        }
        if (n2 <= 4) {
            int e2 = this.a((int)9, (String)string, (String)string2, (String)string3).a;
            this.h.a(15, n2, e2);
        } else {
            e e2 = this.h;
            int l2 = 15;
            int n5 = bl ? 11 : 10;
            e2.a(l2, n2, this.a((int)n5, (String)string, (String)string2, (String)string3).a);
        }
        int n6 = this.g;
        this.g = n6 + 1;
        long l2 = n2;
        return this.b(new z(n6, 15, string, string2, string3, l2, n3));
    }

    z a(String string, String string2, q q3, Object ... objectArray) {
        A a2 = this;
        int n2 = a2.a((q)q3, (Object[])objectArray).a;
        return a2.a(18, string, string2, n2);
    }

    z b(String string) {
        return this.a(19, string);
    }

    z c(String string) {
        return this.a(20, string);
    }

    z a(q q3, Object ... objectArray) {
        z z3;
        block8: {
            int n2;
            int n3;
            e e2 = this.j;
            if (e2 == null) {
                e e3;
                e2 = e3;
                e3 = new e();
                this.j = e2;
            }
            int n4 = objectArray.length;
            for (n3 = 0; n3 < n4; ++n3) {
                this.a(objectArray[n3]);
            }
            q q4 = q3;
            n4 = e2.b;
            n3 = q4.d();
            String string = q4.c();
            String string2 = q4.b();
            String string3 = q4.a();
            boolean bl = q4.e();
            e2.d(this.a((int)n3, (String)string, (String)string2, (String)string3, (boolean)bl).a);
            e2.d(objectArray.length);
            n3 = objectArray.length;
            for (n2 = 0; n2 < n3; ++n2) {
                e2.d(this.a((Object)objectArray[n2]).a);
            }
            int n5 = e2.b - n4;
            int n6 = q3.hashCode();
            n3 = objectArray.length;
            for (n2 = 0; n2 < n3; ++n2) {
                n6 ^= objectArray[n2].hashCode();
            }
            A a2 = this;
            int n7 = n6 & Integer.MAX_VALUE;
            byte[] byArray = a2.j.a;
            z3 = a2.f[n7 % a2.f.length];
            while (z3 != null) {
                if (z3.b == 64 && z3.h == n7) {
                    n2 = (int)z3.f;
                    boolean bl2 = true;
                    for (int i2 = 0; i2 < n5; ++i2) {
                        if (byArray[n4 + i2] == byArray[n2 + i2]) continue;
                        bl2 = false;
                        break;
                    }
                    if (bl2) {
                        this.j.b = n4;
                        break block8;
                    }
                }
                z3 = z3.i;
            }
            int n8 = this.i;
            this.i = n8 + 1;
            long l2 = n4;
            z3 = this.b(new z(n8, 64, l2, n7));
        }
        return z3;
    }

    z c(int n2) {
        return this.l[n2];
    }

    int e(String string) {
        int n2 = 128 + string.hashCode() & Integer.MAX_VALUE;
        z z3 = this.b(n2);
        while (z3 != null) {
            if (z3.b == 128 && z3.h == n2 && z3.e.equals(string)) {
                return z3.a;
            }
            z3 = z3.i;
        }
        return this.a(new z(this.k, 128, string, n2));
    }

    int a(String string, int n2) {
        int n3 = 129 + string.hashCode() + n2 & Integer.MAX_VALUE;
        z z3 = this.b(n3);
        while (z3 != null) {
            if (z3.b == 129 && z3.h == n3 && z3.f == (long)n2 && z3.e.equals(string)) {
                return z3.a;
            }
            z3 = z3.i;
        }
        int n4 = this.k;
        long l2 = n2;
        return this.a(new z(n4, 129, string, l2, n3));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    int b(int n2, int n3) {
        String string;
        int n4;
        long l2;
        block9: {
            block11: {
                Class<?> clazz2;
                Class<?> clazz;
                block10: {
                    l2 = n2 < n3 ? (long)n2 | (long)n3 << 32 : (long)n3 | (long)n2 << 32;
                    n4 = A.c(130, n2 + n3);
                    clazz = this.b(n4);
                    while (clazz != null) {
                        if (((z)((Object)clazz)).b == 130 && ((z)((Object)clazz)).h == n4 && ((z)((Object)clazz)).f == l2) {
                            return ((z)((Object)clazz)).g;
                        }
                        clazz = ((z)((Object)clazz)).i;
                    }
                    A a2 = this;
                    String string2 = a2.l[n2].e;
                    string = a2.l[n3].e;
                    if (a2.a == null) {
                        throw null;
                    }
                    clazz = i.class.getClassLoader();
                    try {
                        clazz2 = Class.forName(string2.replace('/', '.'), false, (ClassLoader)((Object)clazz));
                    }
                    catch (ClassNotFoundException classNotFoundException) {
                        throw new TypeNotPresentException(string2, classNotFoundException);
                    }
                    try {
                        clazz = Class.forName(string.replace('/', '.'), false, (ClassLoader)((Object)clazz));
                        if (clazz2.isAssignableFrom(clazz)) {
                            string = string2;
                            break block9;
                        }
                        if (clazz.isAssignableFrom(clazz2)) break block9;
                        if (!clazz2.isInterface() && !clazz.isInterface()) break block10;
                        break block11;
                    }
                    catch (ClassNotFoundException classNotFoundException) {
                        throw new TypeNotPresentException(string, classNotFoundException);
                    }
                }
                while (!(clazz2 = clazz2.getSuperclass()).isAssignableFrom(clazz)) {
                }
                string = clazz2.getName().replace('.', '/');
                break block9;
            }
            string = "java/lang/Object";
        }
        this.b((z)new z((int)this.k, (int)130, (long)l2, (int)n4)).g = this.e(string);
        return this.b((z)new z((int)this.k, (int)130, (long)l2, (int)n4)).g;
    }
}

