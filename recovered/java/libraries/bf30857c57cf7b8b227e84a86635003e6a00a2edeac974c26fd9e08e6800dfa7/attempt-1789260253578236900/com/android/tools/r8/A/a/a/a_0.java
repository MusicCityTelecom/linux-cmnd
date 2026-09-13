/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.r8.a.a.a;

/*
 * Renamed from com.android.tools.r8.A.a.a.a
 */
public abstract class a_0 {
    public a_0(int n2) {
        this(n2, null);
    }

    public a_0(int n2, a_0 a_02) {
        if (n2 != 458752 && n2 != 393216 && n2 != 327680 && n2 != 262144) {
            throw new IllegalArgumentException("Unsupported api " + n2);
        }
    }

    public abstract void a(String var1, Object var2);

    public abstract void a(String var1, String var2, String var3);

    public abstract a_0 a(String var1, String var2);

    public abstract a_0 a(String var1);

    public abstract void a();
}

