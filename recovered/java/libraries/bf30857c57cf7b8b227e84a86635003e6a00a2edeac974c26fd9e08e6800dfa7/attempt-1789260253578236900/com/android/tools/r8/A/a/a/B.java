/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.r8.A.a.a;

public final class B {
    public static final B e = new B(0, "VZCBSIFJD", 0, 1);
    public static final B f = new B(1, "VZCBSIFJD", 1, 2);
    public static final B g = new B(2, "VZCBSIFJD", 2, 3);
    public static final B h = new B(3, "VZCBSIFJD", 3, 4);
    public static final B i = new B(4, "VZCBSIFJD", 4, 5);
    public static final B j = new B(5, "VZCBSIFJD", 5, 6);
    public static final B k = new B(6, "VZCBSIFJD", 6, 7);
    public static final B l = new B(7, "VZCBSIFJD", 7, 8);
    public static final B m = new B(8, "VZCBSIFJD", 8, 9);
    private final int a;
    private final String b;
    private final int c;
    private final int d;

    private B(int n2, String string, int n3, int n4) {
        this.a = n2;
        this.b = string;
        this.c = n3;
        this.d = n4;
    }

    public static B g(String string) {
        String string2 = string;
        return B.a(string2, 0, string2.length());
    }

    public static B d(String string) {
        B b2;
        int n2 = string.charAt(0) == '[' ? 9 : 12;
        B b3 = b2;
        int n3 = string.length();
        b3(n2, string, 0, n3);
        return b3;
    }

    public static B c(String string) {
        int n2 = string.length();
        return new B(11, string, 0, n2);
    }

    public static B[] a(String string) {
        int n2 = 0;
        int n3 = 1;
        while (string.charAt(n3) != ')') {
            while (string.charAt(n3) == '[') {
                ++n3;
            }
            if (string.charAt(n3++) == 'L') {
                n3 = string.indexOf(59, n3) + 1;
            }
            ++n2;
        }
        B[] bArray = new B[n2];
        n3 = 1;
        int n4 = 0;
        while (string.charAt(n3) != ')') {
            int n5 = n3;
            while (string.charAt(n5) == '[') {
                ++n5;
            }
            if (string.charAt(n5++) == 'L') {
                n5 = string.indexOf(59, n5) + 1;
            }
            int n6 = n4 + 1;
            bArray[n4] = B.a(string, n3, n5);
            n4 = n6;
            n3 = n5;
        }
        return bArray;
    }

    public static B e(String string) {
        String string2 = string;
        int n2 = B.f(string2);
        return B.a(string2, n2, string2.length());
    }

    static int f(String string) {
        int n2 = 1;
        while (string.charAt(n2) != ')') {
            while (string.charAt(n2) == '[') {
                ++n2;
            }
            if (string.charAt(n2++) != 'L') continue;
            n2 = string.indexOf(59, n2) + 1;
        }
        return n2 + 1;
    }

    private static B a(String string, int n2, int n3) {
        switch (string.charAt(n2)) {
            default: {
                throw new IllegalArgumentException();
            }
            case '[': {
                return new B(9, string, n2, n3);
            }
            case 'Z': {
                return f;
            }
            case 'V': {
                return e;
            }
            case 'S': {
                return i;
            }
            case 'L': {
                return new B(10, string, ++n2, --n3);
            }
            case 'J': {
                return l;
            }
            case 'I': {
                return j;
            }
            case 'F': {
                return k;
            }
            case 'D': {
                return m;
            }
            case 'C': {
                return g;
            }
            case 'B': {
                return h;
            }
            case '(': 
        }
        return new B(11, string, n2, n3);
    }

    public static int b(String string) {
        int n2 = 1;
        int n3 = 1;
        char c2 = string.charAt(1);
        while (c2 != ')') {
            if (c2 != 'J' && c2 != 'D') {
                while (string.charAt(n3) == '[') {
                    ++n3;
                }
                if (string.charAt(n3++) == 'L') {
                    n3 = string.indexOf(59, n3) + 1;
                }
                ++n2;
            } else {
                ++n3;
                n2 += 2;
            }
            c2 = string.charAt(n3);
        }
        int n4 = string.charAt(n3 + 1);
        if (n4 == 86) {
            return n2 << 2;
        }
        n4 = n4 != 74 && n4 != 68 ? 1 : 2;
        return n2 << 2 | n4;
    }

    public String b() {
        B b2 = this;
        int n2 = b2.c;
        return this.b.substring(n2, b2.d);
    }

    public String a() {
        int n2 = this.a;
        if (n2 == 10) {
            return this.b.substring(this.c - 1, this.d + 1);
        }
        if (n2 == 12) {
            B b2 = this;
            int n3 = b2.c;
            return 'L' + this.b.substring(n3, b2.d) + ';';
        }
        B b3 = this;
        int n4 = b3.c;
        return this.b.substring(n4, b3.d);
    }

    public int c() {
        int n2 = this.a;
        if (n2 == 12) {
            n2 = 10;
        }
        return n2;
    }

    public boolean equals(Object object) {
        int n2;
        if (this == object) {
            return true;
        }
        if (!(object instanceof B)) {
            return false;
        }
        object = (B)object;
        int n3 = this.a;
        if (n3 == 12) {
            n3 = 10;
        }
        if ((n2 = ((B)object).a) == 12) {
            n2 = 10;
        }
        if (n3 != n2) {
            return false;
        }
        B b2 = this;
        n3 = b2.c;
        n2 = b2.d;
        Object object2 = object;
        int n4 = ((B)object2).d;
        int n5 = ((B)object2).c;
        if (n2 - n3 != n4 - n5) {
            return false;
        }
        while (n3 < n2) {
            if (this.b.charAt(n3) != ((B)object).b.charAt(n5)) {
                return false;
            }
            ++n3;
            ++n5;
        }
        return true;
    }

    public int hashCode() {
        int n2 = 13;
        int n3 = this.a;
        if (n3 == 12) {
            n3 = 10;
        }
        n2 = n3 * n2;
        if (this.a >= 9) {
            int n4 = this.d;
            for (n3 = (v294745).c; n3 < n4; ++n3) {
                n2 = (n2 + this.b.charAt(n3)) * 17;
            }
        }
        return n2;
    }

    public String toString() {
        return this.a();
    }
}

