/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.r8.A.a.a.D;

import com.android.tools.r8.A.a.a.D.a;
import com.android.tools.r8.A.a.a.E.f;
import com.android.tools.r8.A.a.a.E.g;
import com.android.tools.r8.A.a.a.E.j;
import com.android.tools.r8.A.a.a.E.k;
import com.android.tools.r8.A.a.a.E.o;
import com.android.tools.r8.A.a.a.E.p;
import com.android.tools.r8.A.a.a.E.v;
import com.android.tools.r8.A.a.a.E.w;
import com.android.tools.r8.A.a.a.s;
import com.android.tools.r8.A.a.a.u;
import com.android.tools.r8.A.a.a.y;
import com.android.tools.r8.a.a.a.e.a_0;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class b
extends com.android.tools.r8.A.a.a.E.s
implements y {
    private final BitSet D;
    private final Map<k, BitSet> E;
    final BitSet F;

    public b(u u3, int n2, String string, String string2, String string3, String[] stringArray) {
        this(458752, u3, n2, string, string2, string3, stringArray);
        if (b.class == b.class) {
            return;
        }
        throw new IllegalStateException();
    }

    protected b(int n2, u u3, int n3, String string, String string2, String string3, String[] stringArray) {
        super(n2, n3, string, string2, string3, stringArray);
        BitSet bitSet;
        HashMap hashMap;
        BitSet bitSet2;
        Object object = bitSet2;
        bitSet2 = new BitSet();
        v1.D = object;
        object = hashMap;
        hashMap = new HashMap();
        v1.E = object;
        object = bitSet;
        bitSet = new BitSet();
        v1.F = object;
        v1.i = u3;
    }

    private void b(int n2, BitSet bitSet, BitSet bitSet2) {
        this.a(n2, bitSet, bitSet2);
        do {
            n2 = 0;
            for (w w3 : this.w) {
                int n3 = this.v.b(w3.c);
                if (bitSet.get(n3)) continue;
                int n4 = this.v.b(w3.a);
                int n5 = this.v.b(w3.b);
                int n6 = bitSet.nextSetBit(n4);
                if (n6 < n4 || n6 >= n5) continue;
                this.a(n3, bitSet, bitSet2);
                n2 = 1;
            }
        } while (n2 != 0);
    }

    private void a(int n2, BitSet bitSet, BitSet bitSet2) {
        block3: while (n2 < this.v.c()) {
            k k2;
            if (bitSet.get(n2)) {
                return;
            }
            bitSet.set(n2);
            if (bitSet2.get(n2)) {
                this.F.set(n2);
            }
            bitSet2.set(n2);
            Iterator<k> iterator2 = this.v.a(n2);
            if (((a_0)((Object)iterator2)).b() == 7 && ((a_0)((Object)iterator2)).a() != 168) {
                b b2 = this;
                iterator2 = (j)((Object)iterator2);
                b2.a(b2.v.b(((j)((Object)iterator2)).g), bitSet, bitSet2);
            } else if (((a_0)((Object)iterator2)).b() == 11) {
                b b3 = this;
                iterator2 = (v)((Object)iterator2);
                b3.a(b3.v.b(((v)((Object)iterator2)).i), bitSet, bitSet2);
                iterator2 = ((v)((Object)iterator2)).j.iterator();
                while (iterator2.hasNext()) {
                    b b4 = this;
                    k2 = iterator2.next();
                    b4.a(b4.v.b(k2), bitSet, bitSet2);
                }
            } else if (((a_0)((Object)iterator2)).b() == 12) {
                b b5 = this;
                iterator2 = (p)((Object)iterator2);
                b5.a(b5.v.b(((p)((Object)iterator2)).g), bitSet, bitSet2);
                iterator2 = ((p)((Object)iterator2)).i.iterator();
                while (iterator2.hasNext()) {
                    b b6 = this;
                    k2 = iterator2.next();
                    b6.a(b6.v.b(k2), bitSet, bitSet2);
                }
            }
            int n3 = this.v.a(n2).a();
            if (n3 != 167 && n3 != 191) {
                switch (n3) {
                    default: {
                        ++n2;
                        continue block3;
                    }
                    case 169: 
                    case 170: 
                    case 171: 
                    case 172: 
                    case 173: 
                    case 174: 
                    case 175: 
                    case 176: 
                    case 177: 
                }
            }
            return;
        }
    }

    public void a(int n2, s s3) {
        b b2 = bitSet2;
        super.a(n2, s3);
        k k2 = ((j)b2.v.a()).g;
        if (n2 == 168 && !((b)((Object)bitSet2)).E.containsKey(k2)) {
            BitSet bitSet;
            BitSet bitSet2 = bitSet;
            bitSet = new BitSet();
            ((b)((Object)bitSet2)).E.put(k2, bitSet2);
        }
    }

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void c() {
        Object object;
        block12: {
            ArrayList arrayList;
            ArrayList arrayList2;
            f f2;
            a a2;
            LinkedList<Object> linkedList;
            Cloneable cloneable;
            Object object2;
            BitSet bitSet;
            if (this.E.isEmpty()) break block12;
            b b2 = this;
            object = bitSet;
            bitSet = new BitSet();
            b2.b(0, b2.D, (BitSet)object);
            Object object3 = b2.E.entrySet().iterator();
            while (object3.hasNext()) {
                b b3 = this;
                Map.Entry<k, BitSet> entry = object3.next();
                object2 = entry.getKey();
                cloneable = entry.getValue();
                b3.b(b3.v.b((a_0)object2), (BitSet)cloneable, (BitSet)object);
            }
            object = linkedList;
            object3 = a2;
            object2 = this.D;
            a2 = new a(this, null, (BitSet)object2);
            new LinkedList<Object>().add(object3);
            object3 = f2;
            f2 = new f();
            object2 = arrayList2;
            arrayList2 = new ArrayList();
            cloneable = arrayList;
            arrayList = new ArrayList();
            while (!((AbstractCollection)object).isEmpty()) {
                Object object4;
                Object object5;
                Object object6;
                Object object7;
                void var7_8;
                a a3 = (a)((LinkedList)object).removeFirst();
                Object object8 = null;
                boolean bl = false;
                while (var7_8 < this.v.c()) {
                    block15: {
                        block14: {
                            block13: {
                                object7 = this.v.a((int)var7_8);
                                if (((a_0)object7).b() != 8) break block13;
                                object7 = (k)object7;
                                if ((object7 = a3.c.get(object7)) == object8) break block14;
                                ((f)object3).a((a_0)object7);
                                break block15;
                            }
                            if (a3.a((int)var7_8) == a3) {
                                if (((a_0)object7).a() == 169) {
                                    j j2;
                                    object7 = null;
                                    object6 = a3;
                                    while (object6 != null) {
                                        if (((a)object6).b.get((int)var7_8)) {
                                            object7 = ((a)object6).d;
                                        }
                                        object6 = ((a)object6).a;
                                    }
                                    if (object7 == null) throw new IllegalArgumentException("Instruction #" + (int)var7_8 + " is a RET not owned by any subroutine");
                                    object6 = j2;
                                    j2 = new j(167, (k)object7);
                                    ((f)object3).a((a_0)object6);
                                } else if (((a_0)object7).a() == 168) {
                                    a a4;
                                    Object object9 = object3;
                                    object7 = ((j)object7).g;
                                    object6 = this.E.get(object7);
                                    object5 = a4;
                                    ((a)object5)(this, a3, (BitSet)object6);
                                    object7 = a4.b((k)object7);
                                    ((f)object9).a(new g(1));
                                    ((f)object9).a(new j(167, (k)object7));
                                    ((f)object9).a(((a)object5).d);
                                    ((LinkedList)object).add(object5);
                                } else {
                                    ((f)object3).a(((a_0)object7).a(a3));
                                }
                            }
                        }
                        object7 = object8;
                    }
                    ++var7_8;
                    object8 = object7;
                }
                for (w w3 : this.w) {
                    w w4;
                    object7 = a3.a(w3.a);
                    if (object7 == (object6 = a3.a(w3.b))) continue;
                    object5 = a3.b(w3.c);
                    if (object7 == null || object6 == null || object5 == null) throw new AssertionError((Object)"Internal error!");
                    object4 = w4;
                    String string = w3.d;
                    w4 = new w((k)object7, (k)object6, (k)object5, string);
                    ((ArrayList)object2).add(object4);
                }
                for (o o3 : this.z) {
                    o o4;
                    object7 = a3.a(o3.d);
                    if (object7 == (object6 = a3.a(o3.e))) continue;
                    object5 = o4;
                    o o5 = o3;
                    String string = o5.a;
                    object4 = o5.b;
                    String string2 = o5.c;
                    int n2 = o5.f;
                    o4 = new o(string, (String)object4, string2, (k)object7, (k)object6, n2);
                    ((ArrayList)cloneable).add(object5);
                }
            }
            this.v = object3;
            this.w = object2;
            this.z = cloneable;
        }
        if ((object = this.i) == null) return;
        this.a((u)object);
    }
}

