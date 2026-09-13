/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.r8.A.a.a.E;

import com.android.tools.r8.A.a.a.B;
import com.android.tools.r8.A.a.a.C;
import com.android.tools.r8.A.a.a.E.A;
import com.android.tools.r8.A.a.a.E.b;
import com.android.tools.r8.A.a.a.E.c;
import com.android.tools.r8.A.a.a.E.e;
import com.android.tools.r8.A.a.a.E.f;
import com.android.tools.r8.A.a.a.E.g;
import com.android.tools.r8.A.a.a.E.h;
import com.android.tools.r8.A.a.a.E.i;
import com.android.tools.r8.A.a.a.E.j;
import com.android.tools.r8.A.a.a.E.k;
import com.android.tools.r8.A.a.a.E.l;
import com.android.tools.r8.A.a.a.E.m;
import com.android.tools.r8.A.a.a.E.n;
import com.android.tools.r8.A.a.a.E.o;
import com.android.tools.r8.A.a.a.E.p;
import com.android.tools.r8.A.a.a.E.r;
import com.android.tools.r8.A.a.a.E.t;
import com.android.tools.r8.A.a.a.E.v;
import com.android.tools.r8.A.a.a.E.w;
import com.android.tools.r8.A.a.a.E.x;
import com.android.tools.r8.A.a.a.E.y;
import com.android.tools.r8.A.a.a.E.z;
import com.android.tools.r8.A.a.a.d;
import com.android.tools.r8.A.a.a.q;
import com.android.tools.r8.A.a.a.u;
import com.android.tools.r8.a.a.a.e.a_0;
import java.util.ArrayList;
import java.util.List;

public abstract class s
extends u {
    public String j;
    public List<com.android.tools.r8.A.a.a.E.u> k;
    public List<b> l;
    public List<b> m;
    public List<x> n;
    public List<x> o;
    public List<d> p;
    public Object q;
    public int r;
    public List<b>[] s;
    public int t;
    public List<b>[] u;
    public f v;
    public List<w> w;
    public int x;
    public int y;
    public List<o> z;
    public List<n> A;
    public List<n> B;
    private boolean C;

    public s(int n2, int n3, String string, String string2, String string3, String[] stringArray) {
        super(n2);
        f f2;
        ArrayList arrayList;
        ((s)object).j = string2;
        com.android.tools.r8.A.a.a.E.z.a(stringArray);
        if ((n3 & 0x400) == 0) {
            ArrayList<o> arrayList2;
            ArrayList<o> arrayList3 = arrayList2;
            arrayList2 = new ArrayList<o>(5);
            ((s)object).z = arrayList3;
        }
        s s3 = object;
        Object object = arrayList;
        arrayList = new ArrayList();
        s3.w = object;
        object = f2;
        f2 = new f();
        s3.v = object;
    }

    private k[] a(com.android.tools.r8.A.a.a.s[] sArray) {
        k[] kArray = new k[sArray.length];
        int n2 = sArray.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            kArray[i2] = this.b(sArray[i2]);
        }
        return kArray;
    }

    private Object[] a(Object[] objectArray) {
        Object[] objectArray2 = new Object[objectArray.length];
        for (Object object : objectArray) {
            if (object instanceof com.android.tools.r8.A.a.a.s) {
                object = this.b((com.android.tools.r8.A.a.a.s)object);
            }
            objectArray2[var3_3] = object;
        }
        return objectArray2;
    }

    public void b(String string, int n2) {
        if (this.k == null) {
            ArrayList<com.android.tools.r8.A.a.a.E.u> arrayList;
            ArrayList<com.android.tools.r8.A.a.a.E.u> arrayList2 = arrayList;
            arrayList = new ArrayList<com.android.tools.r8.A.a.a.E.u>(5);
            this.k = arrayList2;
        }
        this.k.add(new com.android.tools.r8.A.a.a.E.u(string, n2));
    }

    public com.android.tools.r8.a.a.a.a_0 a() {
        r r3;
        r r4 = r3;
        r3 = new r(this, 0);
        return new b(r4);
    }

    public com.android.tools.r8.a.a.a.a_0 a(String object, boolean bl) {
        b b2;
        b b3 = b2;
        b2 = new b((String)object);
        if (bl) {
            if (this.l == null) {
                ArrayList arrayList;
                object = arrayList;
                arrayList = new ArrayList(1);
                this.l = object;
            }
            this.l.add(b3);
        } else {
            if (this.m == null) {
                ArrayList arrayList;
                object = arrayList;
                arrayList = new ArrayList(1);
                this.m = object;
            }
            this.m.add(b3);
        }
        return b3;
    }

    public com.android.tools.r8.a.a.a.a_0 c(int n2, C c2, String string, boolean bl) {
        x x3;
        x x4 = x3;
        x3 = new x(n2, c2, string);
        if (bl) {
            if (this.n == null) {
                ArrayList<x> arrayList;
                ArrayList<x> arrayList2 = arrayList;
                arrayList = new ArrayList<x>(1);
                this.n = arrayList2;
            }
            this.n.add(x4);
        } else {
            if (this.o == null) {
                ArrayList<x> arrayList;
                ArrayList<x> arrayList3 = arrayList;
                arrayList = new ArrayList<x>(1);
                this.o = arrayList3;
            }
            this.o.add(x4);
        }
        return x4;
    }

    public void a(int n2, boolean bl) {
        if (bl) {
            this.r = n2;
        } else {
            this.t = n2;
        }
    }

    public com.android.tools.r8.a.a.a.a_0 a(int n2, String object, boolean bl) {
        b b2;
        b b3 = b2;
        b2 = new b((String)object);
        if (bl) {
            if (this.s == null) {
                this.s = new List[com.android.tools.r8.A.a.a.B.a(this.j).length];
            }
            object = this.s;
            if (this.s[n2] == null) {
                ArrayList arrayList;
                List<b>[] listArray = object;
                object = arrayList;
                arrayList = new ArrayList(1);
                listArray[n2] = object;
            }
            this.s[n2].add(b3);
        } else {
            if (this.u == null) {
                this.u = new List[com.android.tools.r8.A.a.a.B.a(this.j).length];
            }
            object = this.u;
            if (this.u[n2] == null) {
                ArrayList arrayList;
                List<b>[] listArray = object;
                object = arrayList;
                arrayList = new ArrayList(1);
                listArray[n2] = object;
            }
            this.u[n2].add(b3);
        }
        return b3;
    }

    public void a(d d2) {
        if (this.p == null) {
            ArrayList<d> arrayList;
            ArrayList<d> arrayList2 = arrayList;
            arrayList = new ArrayList<d>(1);
            this.p = arrayList2;
        }
        this.p.add(d2);
    }

    public void b() {
    }

    public void a(int n2, int n3, Object[] object, int n4, Object[] objectArray) {
        f f2 = object2.v;
        object = object == null ? null : object2.a((Object[])object);
        Object object2 = objectArray == null ? null : object2.a(objectArray);
        com.android.tools.r8.A.a.a.E.d d2 = new com.android.tools.r8.A.a.a.E.d(n2, n3, (Object[])object, n4, (Object[])object2);
        f2.a(d2);
    }

    public void a(int n2) {
        this.v.a(new g(n2));
    }

    public void b(int n2, int n3) {
        this.v.a(new h(n2, n3));
    }

    public void d(int n2, int n3) {
        this.v.a(new A(n2, n3));
    }

    public void a(int n2, String string) {
        this.v.a(new y(n2, string));
    }

    public void a(int n2, String string, String string2, String string3) {
        this.v.a(new c(n2, string, string2, string3));
    }

    public void a(int n2, String string, String string2, String string3, boolean bl) {
        if (this.h < 327680 && (n2 & 0x100) == 0) {
            super.a(n2, string, string2, string3, bl);
            return;
        }
        int n3 = n2 & 0xFFFFFEFF;
        this.v.a(new com.android.tools.r8.A.a.a.E.q(n3, string, string2, string3, bl));
    }

    public void a(String string, String string2, q q3, Object ... objectArray) {
        this.v.a(new i(string, string2, q3, objectArray));
    }

    public void a(int n2, com.android.tools.r8.A.a.a.s s3) {
        this.v.a(new j(n2, this.b(s3)));
    }

    public void a(com.android.tools.r8.A.a.a.s s3) {
        this.v.a(this.b(s3));
    }

    public void a(Object object) {
        this.v.a(new l(object));
    }

    public void a(int n2, int n3) {
        this.v.a(new e(n2, n3));
    }

    public void a(int n2, int n3, com.android.tools.r8.A.a.a.s s3, com.android.tools.r8.A.a.a.s ... sArray) {
        s s4 = k2;
        k k2 = ((s)((Object)k2)).b(s3);
        k[] kArray = s4.a(sArray);
        ((s)((Object)k2)).v.a(new v(n2, n3, k2, kArray));
    }

    public void a(com.android.tools.r8.A.a.a.s kArray, int[] nArray, com.android.tools.r8.A.a.a.s[] sArray) {
        s s3 = k2;
        k k2 = ((s)((Object)k2)).b((com.android.tools.r8.A.a.a.s)kArray);
        kArray = s3.a(sArray);
        ((s)((Object)k2)).v.a(new p(k2, nArray, kArray));
    }

    public void a(String string, int n2) {
        this.v.a(new t(string, n2));
    }

    public com.android.tools.r8.a.a.a.a_0 a(int n2, C c2, String string, boolean bl) {
        x x3;
        Object object = ((s)object).v.a();
        while (((a_0)object).a == -1) {
            object = ((a_0)object).d;
        }
        x x4 = x3;
        x3 = new x(n2, c2, string);
        if (bl) {
            if (((a_0)object).b == null) {
                ArrayList<x> arrayList;
                ArrayList<x> arrayList2 = arrayList;
                arrayList = new ArrayList<x>(1);
                ((a_0)object).b = arrayList2;
            }
            ((a_0)object).b.add(x4);
        } else {
            if (((a_0)object).c == null) {
                ArrayList<x> arrayList;
                ArrayList<x> arrayList3 = arrayList;
                arrayList = new ArrayList<x>(1);
                ((a_0)object).c = arrayList3;
            }
            ((a_0)object).c.add(x4);
        }
        return x4;
    }

    public void a(com.android.tools.r8.A.a.a.s object, com.android.tools.r8.A.a.a.s object2, com.android.tools.r8.A.a.a.s s3, String string) {
        if (((s)((Object)k2)).w == null) {
            ArrayList<w> arrayList;
            ArrayList<w> arrayList2 = arrayList;
            arrayList = new ArrayList<w>(1);
            ((s)((Object)k2)).w = arrayList2;
        }
        s s4 = k2;
        s s5 = k2;
        k k2 = ((s)((Object)k2)).b((com.android.tools.r8.A.a.a.s)object);
        object = s5.b((com.android.tools.r8.A.a.a.s)object2);
        object2 = s4.b(s3);
        ((s)((Object)k2)).w.add(new w(k2, (k)object, (k)object2, string));
    }

    public com.android.tools.r8.a.a.a.a_0 b(int n2, C c2, String string, boolean bl) {
        x x3;
        w w3 = ((s)((Object)w3)).w.get((n2 & 0xFFFF00) >> 8);
        x x4 = x3;
        x3 = new x(n2, c2, string);
        if (bl) {
            if (w3.e == null) {
                ArrayList<x> arrayList;
                ArrayList<x> arrayList2 = arrayList;
                arrayList = new ArrayList<x>(1);
                w3.e = arrayList2;
            }
            w3.e.add(x4);
        } else {
            if (w3.f == null) {
                ArrayList<x> arrayList;
                ArrayList<x> arrayList3 = arrayList;
                arrayList = new ArrayList<x>(1);
                w3.f = arrayList3;
            }
            w3.f.add(x4);
        }
        return x4;
    }

    public void a(String object, String string, String string2, com.android.tools.r8.A.a.a.s s3, com.android.tools.r8.A.a.a.s s4, int n2) {
        if (((s)((Object)k2)).z == null) {
            ArrayList<o> arrayList;
            ArrayList<o> arrayList2 = arrayList;
            arrayList = new ArrayList<o>(1);
            ((s)((Object)k2)).z = arrayList2;
        }
        String string3 = object;
        s s5 = k2;
        k k2 = ((s)((Object)k2)).b(s3);
        object = s5.b(s4);
        ((s)((Object)k2)).z.add(new o(string3, string, string2, k2, (k)object, n2));
    }

    public com.android.tools.r8.a.a.a.a_0 a(int n2, C c2, com.android.tools.r8.A.a.a.s[] objectArray, com.android.tools.r8.A.a.a.s[] objectArray2, int[] nArray, String string, boolean bl) {
        n n3;
        n n4 = n3;
        objectArray = this.a((com.android.tools.r8.A.a.a.s[])objectArray);
        objectArray2 = this.a((com.android.tools.r8.A.a.a.s[])objectArray2);
        n3 = new n(458752, n2, c2, (k[])objectArray, (k[])objectArray2, nArray, string);
        if (bl) {
            if (this.A == null) {
                ArrayList<n> arrayList;
                ArrayList<n> arrayList2 = arrayList;
                arrayList = new ArrayList<n>(1);
                this.A = arrayList2;
            }
            this.A.add(n4);
        } else {
            if (this.B == null) {
                ArrayList<n> arrayList;
                ArrayList<n> arrayList3 = arrayList;
                arrayList = new ArrayList<n>(1);
                this.B = arrayList3;
            }
            this.B.add(n4);
        }
        return n4;
    }

    public void b(int n2, com.android.tools.r8.A.a.a.s s3) {
        this.v.a(new m(n2, this.b(s3)));
    }

    public void c(int n2, int n3) {
        s s3 = this;
        s3.x = n2;
        s3.y = n3;
    }

    protected k b(com.android.tools.r8.A.a.a.s s3) {
        if (!(s3.a instanceof k)) {
            k k2;
            k k3 = k2;
            k2 = new k();
            s3.a = k3;
        }
        return (k)s3.a;
    }

    public void a(u u3) {
        List<d> list;
        int n2;
        b b2;
        int n3;
        List<x> list2;
        String string;
        C c2;
        List<x> list3;
        List<b> list4;
        int n4;
        List<com.android.tools.r8.A.a.a.E.u> list5 = this.k;
        if (list5 != null) {
            n4 = list5.size();
            for (int i2 = 0; i2 < n4; ++i2) {
                com.android.tools.r8.A.a.a.E.u u4 = this.k.get(i2);
                String string2 = u4.a;
                u3.b(string2, u4.b);
            }
        }
        if (this.q != null) {
            list5 = u3.a();
            b.a((com.android.tools.r8.a.a.a.a_0)((Object)list5), null, this.q);
            if (list5 != null) {
                ((com.android.tools.r8.a.a.a.a_0)((Object)list5)).a();
            }
        }
        if ((list5 = this.l) != null) {
            n4 = list5.size();
            for (int i3 = 0; i3 < n4; ++i3) {
                b b3 = this.l.get(i3);
                b3.a(u3.a(b3.a, true));
            }
        }
        if ((list4 = this.m) != null) {
            n4 = list4.size();
            for (int i4 = 0; i4 < n4; ++i4) {
                b b4 = this.m.get(i4);
                b4.a(u3.a(b4.a, false));
            }
        }
        if ((list3 = this.n) != null) {
            n4 = list3.size();
            for (int i5 = 0; i5 < n4; ++i5) {
                x x3 = this.n.get(i5);
                int n5 = x3.c;
                c2 = x3.d;
                string = x3.a;
                x3.a(u3.c(n5, c2, string, true));
            }
        }
        if ((list2 = this.o) != null) {
            n4 = list2.size();
            for (int i6 = 0; i6 < n4; ++i6) {
                x x4 = this.o.get(i6);
                int n6 = x4.c;
                c2 = x4.d;
                string = x4.a;
                x4.a(u3.c(n6, c2, string, false));
            }
        }
        if ((n3 = this.r) > 0) {
            u3.a(n3, true);
        }
        List<b>[] listArray = this.s;
        if (this.s != null) {
            n4 = listArray.length;
            for (int i7 = 0; i7 < n4; ++i7) {
                List<b> list6 = this.s[i7];
                if (list6 == null) continue;
                int n7 = list6.size();
                for (int i8 = 0; i8 < n7; ++i8) {
                    b2 = list6.get(i8);
                    b2.a(u3.a(i7, b2.a, true));
                }
            }
        }
        if ((n2 = this.t) > 0) {
            u3.a(n2, false);
        }
        List<b>[] listArray2 = this.u;
        if (this.u != null) {
            n4 = listArray2.length;
            for (int i9 = 0; i9 < n4; ++i9) {
                List<b> list7 = this.u[i9];
                if (list7 == null) continue;
                int n8 = list7.size();
                for (int i10 = 0; i10 < n8; ++i10) {
                    b2 = list7.get(i10);
                    b2.a(u3.a(i9, b2.a, false));
                }
            }
        }
        if (this.C) {
            this.v.b();
        }
        if ((list = this.p) != null) {
            n4 = list.size();
            for (int i11 = 0; i11 < n4; ++i11) {
                u3.a(this.p.get(i11));
            }
        }
        if (this.v.c() > 0) {
            List<n> list8;
            List<n> list9;
            Object object;
            u3.b();
            List<w> list10 = this.w;
            if (list10 != null) {
                n4 = list10.size();
                for (int i12 = 0; i12 < n4; ++i12) {
                    List<x> list11;
                    String string3;
                    List<x> list12;
                    w w3 = this.w.get(i12);
                    int n9 = i12 << 8 | 0x42000000;
                    List<x> list13 = w3.e;
                    if (list13 != null) {
                        int n10 = list13.size();
                        for (int i13 = 0; i13 < n10; ++i13) {
                            w3.e.get((int)i13).c = n9;
                        }
                    }
                    if ((list12 = w3.f) != null) {
                        int n11 = list12.size();
                        for (int i14 = 0; i14 < n11; ++i14) {
                            w3.f.get((int)i14).c = n9;
                        }
                    }
                    w3 = this.w.get(i12);
                    Object object2 = w3.a.c();
                    com.android.tools.r8.A.a.a.s s3 = w3.b.c();
                    Object object3 = w3.c;
                    object3 = object3 == null ? null : ((k)object3).c();
                    object = w3.d;
                    u3.a((com.android.tools.r8.A.a.a.s)object2, s3, (com.android.tools.r8.A.a.a.s)object3, (String)object);
                    object2 = w3.e;
                    if (object2 != null) {
                        int n12 = object2.size();
                        for (int i15 = 0; i15 < n12; ++i15) {
                            x x5 = w3.e.get(i15);
                            int n13 = x5.c;
                            object = x5.d;
                            string3 = x5.a;
                            x5.a(u3.b(n13, (C)object, string3, true));
                        }
                    }
                    if ((list11 = w3.f) == null) continue;
                    int n14 = list11.size();
                    for (int i16 = 0; i16 < n14; ++i16) {
                        x x6 = w3.f.get(i16);
                        int n15 = x6.c;
                        object = x6.d;
                        string3 = x6.a;
                        x6.a(u3.b(n15, (C)object, string3, false));
                    }
                }
            }
            s s4 = this;
            s4.v.a(u3);
            List<o> list14 = s4.z;
            if (list14 != null) {
                n4 = list14.size();
                for (int i17 = 0; i17 < n4; ++i17) {
                    o o3 = this.z.get(i17);
                    String string4 = o3.a;
                    String string5 = o3.b;
                    String string6 = o3.c;
                    com.android.tools.r8.A.a.a.s s5 = o3.d.c();
                    object = o3.e.c();
                    int n16 = o3.f;
                    u3.a(string4, string5, string6, s5, (com.android.tools.r8.A.a.a.s)object, n16);
                }
            }
            if ((list9 = this.A) != null) {
                n4 = list9.size();
                for (int i18 = 0; i18 < n4; ++i18) {
                    this.A.get(i18).a(u3, true);
                }
            }
            if ((list8 = this.B) != null) {
                n4 = list8.size();
                for (int i19 = 0; i19 < n4; ++i19) {
                    this.B.get(i19).a(u3, false);
                }
            }
            s s6 = this;
            int n17 = s6.x;
            u3.c(n17, s6.y);
            this.C = true;
        }
        u3.c();
    }
}

