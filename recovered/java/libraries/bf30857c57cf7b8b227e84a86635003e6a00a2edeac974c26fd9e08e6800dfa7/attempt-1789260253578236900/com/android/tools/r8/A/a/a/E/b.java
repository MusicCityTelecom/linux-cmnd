/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.r8.A.a.a.E;

import com.android.tools.r8.A.a.a.E.z;
import com.android.tools.r8.a.a.a.a_0;
import java.util.ArrayList;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class b
extends a_0 {
    public String a;
    public List<Object> b;

    public b(String string) {
        b b2 = this;
        b2(458752, string);
        if (b2.getClass() == b.class) {
            return;
        }
        throw new IllegalStateException();
    }

    public b(int n2, String string) {
        super(n2);
        this.a = string;
    }

    b(List<Object> list) {
        super(458752);
        this.b = list;
    }

    static void a(a_0 object, String object2, Object object3) {
        if (object != null) {
            if (object3 instanceof String[]) {
                a_0 a_02 = object;
                String[] stringArray = (String[])object3;
                object = stringArray[0];
                object3 = stringArray[1];
                a_02.a((String)object2, (String)object, (String)object3);
            } else if (object3 instanceof b) {
                object3 = (b)object3;
                ((b)object3).a(((a_0)object).a((String)object2, ((b)object3).a));
            } else if (object3 instanceof List) {
                if ((object = ((a_0)object).a((String)object2)) != null) {
                    object2 = (List)object3;
                    int n2 = object2.size();
                    for (int i2 = 0; i2 < n2; ++i2) {
                        com.android.tools.r8.A.a.a.E.b.a((a_0)object, null, object2.get(i2));
                    }
                    ((a_0)object).a();
                }
            } else {
                ((a_0)object).a((String)object2, object3);
            }
        }
    }

    @Override
    public void a(String object, Object object2) {
        List<Object> list;
        int n2;
        if (((b)((Object)list)).b == null) {
            n2 = ((b)((Object)list)).a != null ? 2 : 1;
            ArrayList<Object> arrayList = new ArrayList<Object>(n2);
            ((b)((Object)list)).b = arrayList;
        }
        if (((b)((Object)list)).a != null) {
            ((b)((Object)list)).b.add(object);
        }
        if (object2 instanceof byte[]) {
            list = ((b)((Object)list)).b;
            byte[] byArray = (byte[])object2;
            object = byArray;
            if (byArray == null) {
                ArrayList arrayList;
                object = arrayList;
                arrayList = new ArrayList();
                object2 = object;
            } else {
                ArrayList arrayList;
                object2 = arrayList;
                arrayList = new ArrayList(((Object)object).length);
                int n3 = ((Object)object).length;
                for (n2 = 0; n2 < n3; ++n2) {
                    ((ArrayList)object2).add((byte)object[n2]);
                }
            }
            list.add(object2);
        } else if (object2 instanceof boolean[]) {
            list = ((b)((Object)list)).b;
            boolean[] blArray = (boolean[])object2;
            object = blArray;
            if (blArray == null) {
                ArrayList arrayList;
                object = arrayList;
                arrayList = new ArrayList();
                object2 = object;
            } else {
                ArrayList arrayList;
                object2 = arrayList;
                arrayList = new ArrayList(((Object)object).length);
                int n4 = ((Object)object).length;
                for (n2 = 0; n2 < n4; ++n2) {
                    ((ArrayList)object2).add((boolean)object[n2]);
                }
            }
            list.add(object2);
        } else if (object2 instanceof short[]) {
            list = ((b)((Object)list)).b;
            short[] sArray = (short[])object2;
            object = sArray;
            if (sArray == null) {
                ArrayList arrayList;
                object = arrayList;
                arrayList = new ArrayList();
                object2 = object;
            } else {
                ArrayList arrayList;
                object2 = arrayList;
                arrayList = new ArrayList(((Object)object).length);
                int n5 = ((Object)object).length;
                for (n2 = 0; n2 < n5; ++n2) {
                    ((ArrayList)object2).add((short)object[n2]);
                }
            }
            list.add(object2);
        } else if (object2 instanceof char[]) {
            list = ((b)((Object)list)).b;
            char[] cArray = (char[])object2;
            object = cArray;
            if (cArray == null) {
                ArrayList arrayList;
                object = arrayList;
                arrayList = new ArrayList();
                object2 = object;
            } else {
                ArrayList arrayList;
                object2 = arrayList;
                arrayList = new ArrayList(((Object)object).length);
                int n6 = ((Object)object).length;
                for (n2 = 0; n2 < n6; ++n2) {
                    ((ArrayList)object2).add(Character.valueOf((char)object[n2]));
                }
            }
            list.add(object2);
        } else if (object2 instanceof int[]) {
            ((b)((Object)list)).b.add(z.a((int[])object2));
        } else if (object2 instanceof long[]) {
            list = ((b)((Object)list)).b;
            long[] lArray = (long[])object2;
            object = lArray;
            if (lArray == null) {
                ArrayList arrayList;
                object = arrayList;
                arrayList = new ArrayList();
                object2 = object;
            } else {
                ArrayList arrayList;
                object2 = arrayList;
                arrayList = new ArrayList(((Object)object).length);
                int n7 = ((Object)object).length;
                for (n2 = 0; n2 < n7; ++n2) {
                    ((ArrayList)object2).add((long)object[n2]);
                }
            }
            list.add(object2);
        } else if (object2 instanceof float[]) {
            list = ((b)((Object)list)).b;
            float[] fArray = (float[])object2;
            object = fArray;
            if (fArray == null) {
                ArrayList arrayList;
                object = arrayList;
                arrayList = new ArrayList();
                object2 = object;
            } else {
                ArrayList arrayList;
                object2 = arrayList;
                arrayList = new ArrayList(((Object)object).length);
                int n8 = ((Object)object).length;
                for (n2 = 0; n2 < n8; ++n2) {
                    ((ArrayList)object2).add(Float.valueOf((float)object[n2]));
                }
            }
            list.add(object2);
        } else if (object2 instanceof double[]) {
            list = ((b)((Object)list)).b;
            double[] dArray = (double[])object2;
            object = dArray;
            if (dArray == null) {
                ArrayList arrayList;
                object = arrayList;
                arrayList = new ArrayList();
                object2 = object;
            } else {
                ArrayList arrayList;
                object2 = arrayList;
                arrayList = new ArrayList(((Object)object).length);
                int n9 = ((Object)object).length;
                for (n2 = 0; n2 < n9; ++n2) {
                    ((ArrayList)object2).add((double)object[n2]);
                }
            }
            list.add(object2);
        } else {
            ((b)((Object)list)).b.add(object2);
        }
    }

    @Override
    public void a(String string, String string2, String string3) {
        if (this.b == null) {
            int n2 = this.a != null ? 2 : 1;
            ArrayList<Object> arrayList = new ArrayList<Object>(n2);
            this.b = arrayList;
        }
        if (this.a != null) {
            this.b.add(string);
        }
        this.b.add(new String[]{string2, string3});
    }

    @Override
    public a_0 a(String object, String string) {
        b b2;
        if (this.b == null) {
            int n2 = this.a != null ? 2 : 1;
            ArrayList<Object> arrayList = new ArrayList<Object>(n2);
            this.b = arrayList;
        }
        if (this.a != null) {
            this.b.add(object);
        }
        object = b2;
        ((b)object)(string);
        this.b.add(object);
        return b2;
    }

    @Override
    public a_0 a(String string) {
        ArrayList arrayList;
        if (((b)((Object)arrayList3)).b == null) {
            int n2 = ((b)((Object)arrayList3)).a != null ? 2 : 1;
            ArrayList<Object> arrayList2 = new ArrayList<Object>(n2);
            ((b)((Object)arrayList3)).b = arrayList2;
        }
        if (((b)((Object)arrayList3)).a != null) {
            ((b)((Object)arrayList3)).b.add(string);
        }
        b b2 = arrayList3;
        ArrayList arrayList3 = arrayList;
        arrayList = new ArrayList();
        b2.b.add(arrayList3);
        return new b(arrayList3);
    }

    @Override
    public void a() {
    }

    public void a(a_0 a_02) {
        if (a_02 != null) {
            List<Object> list = this.b;
            if (list != null) {
                int n2 = list.size();
                for (int i2 = 0; i2 < n2; i2 += 2) {
                    com.android.tools.r8.A.a.a.E.b.a(a_02, (String)this.b.get(i2), this.b.get(i2 + 1));
                }
            }
            a_02.a();
        }
    }
}

