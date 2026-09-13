/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.r8.a.a.a;

import com.android.tools.r8.A.a.a.A;
import com.android.tools.r8.A.a.a.B;
import com.android.tools.r8.A.a.a.C;
import com.android.tools.r8.A.a.a.e;
import com.android.tools.r8.A.a.a.z;
import com.android.tools.r8.a.a.a.a_0;

/*
 * Renamed from com.android.tools.r8.A.a.a.b
 */
final class b_0
extends a_0 {
    private final A a;
    private final boolean b;
    private final e c;
    private final int d;
    private int e;
    private final b_0 f;
    private b_0 g;

    b_0(A a2, boolean bl, e e2, b_0 b_02) {
        super(458752);
        this.a = a2;
        this.b = bl;
        this.c = e2;
        int n2 = e2.b;
        n2 = n2 == 0 ? -1 : (n2 -= 2);
        b_0 b_03 = this;
        b_03.d = n2;
        b_03.f = b_02;
        if (b_02 != null) {
            b_02.g = this;
        }
    }

    static b_0 a(A a2, String string, b_0 b_02) {
        e e2;
        e e3 = e2;
        e3();
        e2.d(a2.d(string)).d(0);
        return new b_0(a2, true, e3, b_02);
    }

    /*
     * Exception decompiling
     */
    static b_0 a(A var0, int var1_1, C var2_2, String var3_3, b_0 var4_4) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [1[CASE]], but top level block is 5[SWITCH]
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:435)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:484)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    static int a(b_0 b_02, b_0 b_03, b_0 b_04, b_0 b_05) {
        int n2 = 0;
        if (b_02 != null) {
            n2 = b_02.b("RuntimeVisibleAnnotations") + n2;
        }
        if (b_03 != null) {
            n2 += b_03.b("RuntimeInvisibleAnnotations");
        }
        if (b_04 != null) {
            n2 += b_04.b("RuntimeVisibleTypeAnnotations");
        }
        if (b_05 != null) {
            n2 += b_05.b("RuntimeInvisibleTypeAnnotations");
        }
        return n2;
    }

    static void a(A a2, b_0 b_02, b_0 b_03, b_0 b_04, b_0 b_05, e e2) {
        if (b_02 != null) {
            b_02.a(a2.d("RuntimeVisibleAnnotations"), e2);
        }
        if (b_03 != null) {
            b_03.a(a2.d("RuntimeInvisibleAnnotations"), e2);
        }
        if (b_04 != null) {
            b_04.a(a2.d("RuntimeVisibleTypeAnnotations"), e2);
        }
        if (b_05 != null) {
            b_05.a(a2.d("RuntimeInvisibleTypeAnnotations"), e2);
        }
    }

    static int a(String string, b_0[] b_0Array, int n2) {
        int n3 = n2 * 2 + 7;
        for (int i2 = 0; i2 < n2; ++i2) {
            b_0 b_02 = b_0Array[i2];
            int n4 = b_02 == null ? 0 : b_02.b(string) - 8;
            n3 += n4;
        }
        return n3;
    }

    static void a(int n2, b_0[] b_0Array, int n3, e e2) {
        int n4;
        int n5 = n3 * 2 + 1;
        for (int i2 = 0; i2 < n3; ++i2) {
            b_0 b_02 = b_0Array[i2];
            n4 = b_02 == null ? 0 : b_02.b(null) - 8;
            n5 += n4;
        }
        e e3 = e2;
        e3.d(n2);
        e3.c(n5);
        e3.b(n3);
        for (n2 = 0; n2 < n3; ++n2) {
            Object object = b_0Array[n2];
            b_0 b_03 = null;
            n4 = 0;
            b_0 b_04 = object;
            object = b_03;
            b_03 = b_04;
            while (b_03 != null) {
                b_0 b_05 = b_03;
                b_05.a();
                ++n4;
                object = b_05.f;
                b_0 b_06 = object;
                object = b_03;
                b_03 = b_06;
            }
            e2.d(n4);
            while (object != null) {
                b_0 b_07 = object;
                e e4 = ((b_0)object).c;
                object = e4.a;
                int n6 = e4.b;
                e2.a((byte[])object, 0, n6);
                object = b_07.g;
            }
        }
    }

    /*
     * WARNING - void declaration
     */
    public void a(String object, Object object2) {
        void n10;
        b_0 b_02 = this;
        ++b_02.e;
        if (b_02.b) {
            ((b_0)this).c.d(((b_0)this).a.d((String)object));
        }
        if (n10 instanceof String) {
            ((b_0)this).c.b(115, ((b_0)this).a.d((String)n10));
        } else if (n10 instanceof Byte) {
            ((b_0)this).c.b(66, ((b_0)this).a.a((int)((Byte)n10).byteValue()).a);
        } else if (n10 instanceof Boolean) {
            int n2 = ((Boolean)n10).booleanValue();
            ((b_0)this).c.b(90, ((b_0)this).a.a((int)n2).a);
        } else if (n10 instanceof Character) {
            ((b_0)this).c.b(67, ((b_0)this).a.a((int)((Character)n10).charValue()).a);
        } else if (n10 instanceof Short) {
            ((b_0)this).c.b(83, ((b_0)this).a.a((int)((Short)n10).shortValue()).a);
        } else if (n10 instanceof B) {
            ((b_0)this).c.b(99, ((b_0)this).a.d(((B)n10).a()));
        } else if (n10 instanceof byte[]) {
            byte[] byArray = (byte[])n10;
            ((b_0)this).c.b(91, byArray.length);
            for (byte by : byArray) {
                ((b_0)this).c.b(66, ((b_0)this).a.a((int)by).a);
            }
        } else if (n10 instanceof boolean[]) {
            boolean[] blArray = (boolean[])n10;
            ((b_0)this).c.b(91, blArray.length);
            for (int n2 : blArray) {
                ((b_0)this).c.b(90, ((b_0)this).a.a((int)n2).a);
            }
        } else if (n10 instanceof short[]) {
            short[] sArray = (short[])n10;
            ((b_0)this).c.b(91, sArray.length);
            for (short s3 : sArray) {
                ((b_0)this).c.b(83, ((b_0)this).a.a((int)s3).a);
            }
        } else if (n10 instanceof char[]) {
            char[] cArray = (char[])n10;
            ((b_0)this).c.b(91, cArray.length);
            for (char c2 : cArray) {
                ((b_0)this).c.b(67, ((b_0)this).a.a((int)c2).a);
            }
        } else if (n10 instanceof int[]) {
            int[] nArray = (int[])n10;
            ((b_0)this).c.b(91, nArray.length);
            for (int n3 : nArray) {
                ((b_0)this).c.b(73, ((b_0)this).a.a((int)n3).a);
            }
        } else if (n10 instanceof long[]) {
            long[] lArray = (long[])n10;
            ((b_0)this).c.b(91, lArray.length);
            for (long l2 : lArray) {
                ((b_0)this).c.b(74, ((b_0)this).a.a((long)l2).a);
            }
        } else if (n10 instanceof float[]) {
            float[] fArray = (float[])n10;
            ((b_0)this).c.b(91, fArray.length);
            for (float f2 : fArray) {
                ((b_0)this).c.b(70, ((b_0)this).a.a((float)f2).a);
            }
        } else if (n10 instanceof double[]) {
            double[] dArray = (double[])n10;
            ((b_0)this).c.b(91, dArray.length);
            for (double d2 : dArray) {
                ((b_0)this).c.b(68, ((b_0)this).a.a((double)d2).a);
            }
        } else {
            b_0 b_03 = this;
            Object object3 = this = b_03.a.a(n10);
            char c2 = ".s.IFJDCS".charAt(((z)object3).b);
            b_03.c.b(c2, ((z)object3).a);
        }
    }

    public void a(String string, String string2, String string3) {
        b_0 b_02 = this;
        ++b_02.e;
        if (b_02.b) {
            this.c.d(this.a.d(string));
        }
        this.c.b(101, this.a.d(string2)).d(this.a.d(string3));
    }

    public a_0 a(String object, String string) {
        b_0 b_02 = a2;
        ++b_02.e;
        if (b_02.b) {
            ((b_0)((Object)a2)).c.d(((b_0)((Object)a2)).a.d((String)object));
        }
        ((b_0)((Object)a2)).c.b(64, ((b_0)((Object)a2)).a.d(string)).d(0);
        b_0 b_03 = a2;
        A a2 = b_03.a;
        object = b_03.c;
        return new b_0(a2, true, (e)object, null);
    }

    public a_0 a(String object) {
        b_0 b_02 = a2;
        ++b_02.e;
        if (b_02.b) {
            ((b_0)((Object)a2)).c.d(((b_0)((Object)a2)).a.d((String)object));
        }
        ((b_0)((Object)a2)).c.b(91, 0);
        b_0 b_03 = a2;
        A a2 = b_03.a;
        object = b_03.c;
        return new b_0(a2, false, (e)object, null);
    }

    public void a() {
        int n2 = this.d;
        if (n2 != -1) {
            byte[] byArray = this.c.a;
            int n3 = this.e;
            int n4 = n3;
            byArray[n2] = (byte)(n4 >>> 8);
            n4 = n2 + 1;
            this.c.a[n4] = (byte)n3;
        }
    }

    int b(String string) {
        if (string != null) {
            b_02.a.d(string);
        }
        int n2 = 8;
        while (b_02 != null) {
            n2 += b_02.c.b;
            b_0 b_02 = b_02.f;
        }
        return n2;
    }

    void a(int n2, e e2) {
        int n3 = 2;
        int n4 = 0;
        b_0 b_02 = null;
        b_0 b_03 = object;
        Object object = b_02;
        b_02 = b_03;
        while (b_02 != null) {
            b_0 b_04 = b_02;
            b_04.a();
            n3 += b_04.c.b;
            ++n4;
            object = b_02.f;
            b_0 b_05 = object;
            object = b_02;
            b_02 = b_05;
        }
        e e3 = e2;
        e3.d(n2);
        e3.c(n3);
        e3.d(n4);
        while (object != null) {
            b_0 b_06 = object;
            e e4 = ((b_0)object).c;
            object = e4.a;
            n2 = e4.b;
            e2.a((byte[])object, 0, n2);
            object = b_06.g;
        }
    }
}

