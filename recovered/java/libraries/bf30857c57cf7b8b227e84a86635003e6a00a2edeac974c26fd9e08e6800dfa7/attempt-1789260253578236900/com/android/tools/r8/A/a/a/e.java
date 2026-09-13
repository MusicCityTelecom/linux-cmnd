/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.r8.A.a.a;

public class e {
    byte[] a;
    int b;

    public e() {
        this.a = new byte[64];
    }

    public e(int n2) {
        this.a = new byte[n2];
    }

    private void a(int n2) {
        int n3 = this.a.length * 2;
        if (n3 > (n2 = this.b + n2)) {
            n2 = n3;
        }
        byte[] byArray = new byte[n2];
        int n4 = this.b;
        System.arraycopy(this.a, 0, byArray, 0, n4);
        this.a = byArray;
    }

    public e b(int n2) {
        int n3 = this.b;
        int n4 = n3 + 1;
        if (n4 > this.a.length) {
            this.a(1);
        }
        this.a[n3] = (byte)n2;
        this.b = n4;
        return this;
    }

    final e a(int n2, int n3) {
        int n4 = this.b;
        if (n4 + 2 > this.a.length) {
            this.a(2);
        }
        e e2 = this;
        int n5 = n4 + 1;
        e2.a[n4] = (byte)n2;
        n2 = n5 + 1;
        e2.a[n5] = (byte)n3;
        e2.b = n2;
        return e2;
    }

    public e d(int n2) {
        int n3 = this.b;
        if (n3 + 2 > this.a.length) {
            this.a(2);
        }
        e e2 = this;
        int n4 = n3 + 1;
        e2.a[n3] = (byte)(n2 >>> 8);
        n2 = n4 + 1;
        e2.a[n4] = (byte)n2;
        e2.b = n2;
        return e2;
    }

    final e b(int n2, int n3) {
        int n4 = ((e)object).b;
        if (n4 + 3 > ((e)object).a.length) {
            ((e)object).a(3);
        }
        e e2 = object;
        Object object = e2.a;
        int n5 = n4 + 1;
        object[n4] = (byte)n2;
        n2 = n5 + 1;
        object[n5] = (byte)(n3 >>> 8);
        n5 = n2 + 1;
        e2.a[n2] = (byte)n3;
        e2.b = n5;
        return e2;
    }

    final e a(int n2, int n3, int n4) {
        int n5 = ((e)object).b;
        if (n5 + 4 > ((e)object).a.length) {
            ((e)object).a(4);
        }
        e e2 = object;
        Object object = e2.a;
        int n6 = n5 + 1;
        object[n5] = (byte)n2;
        n2 = n6 + 1;
        object[n6] = (byte)n3;
        n6 = n2 + 1;
        object[n2] = (byte)(n4 >>> 8);
        n2 = n6 + 1;
        e2.a[n6] = (byte)n4;
        e2.b = n2;
        return e2;
    }

    public e c(int n2) {
        int n3 = ((e)object).b;
        if (n3 + 4 > ((e)object).a.length) {
            ((e)object).a(4);
        }
        e e2 = object;
        Object object = e2.a;
        int n4 = n2;
        int n5 = n2;
        int n6 = n2;
        int n7 = n3 + 1;
        object[n3] = (byte)(n2 >>> 24);
        n2 = n7 + 1;
        object[n7] = (byte)(n6 >>> 16);
        n7 = n2 + 1;
        object[n2] = (byte)(n5 >>> 8);
        n2 = n7 + 1;
        e2.a[n7] = (byte)n4;
        e2.b = n2;
        return e2;
    }

    final e b(int n2, int n3, int n4) {
        int n5 = ((e)object).b;
        if (n5 + 5 > ((e)object).a.length) {
            ((e)object).a(5);
        }
        e e2 = object;
        Object object = e2.a;
        int n6 = n5 + 1;
        object[n5] = (byte)n2;
        n2 = n6 + 1;
        object[n6] = (byte)(n3 >>> 8);
        n6 = n2 + 1;
        object[n2] = (byte)n3;
        n2 = n6 + 1;
        object[n6] = (byte)(n4 >>> 8);
        n6 = n2 + 1;
        e2.a[n2] = (byte)n4;
        e2.b = n6;
        return e2;
    }

    public e a(long l2) {
        int n2;
        int n3 = ((e)object).b;
        if (n3 + 8 > ((e)object).a.length) {
            ((e)object).a(8);
        }
        e e2 = object;
        Object object = e2.a;
        int n4 = (int)(l2 >>> 32);
        int n5 = n2 = n4;
        int n6 = n2;
        int n7 = n2;
        n2 = n3 + 1;
        object[n3] = (byte)(n7 >>> 24);
        int n8 = n2 + 1;
        object[n2] = (byte)(n6 >>> 16);
        n2 = n8 + 1;
        object[n8] = (byte)(n5 >>> 8);
        n8 = n2 + 1;
        object[n2] = (byte)n4;
        int n9 = (int)l2;
        int n10 = n2 = n9;
        int n11 = n2;
        int n12 = n8 + 1;
        object[n8] = (byte)(n2 >>> 24);
        n2 = n12 + 1;
        object[n12] = (byte)(n11 >>> 16);
        n12 = n2 + 1;
        object[n2] = (byte)(n10 >>> 8);
        n2 = n12 + 1;
        e2.a[n12] = (byte)n9;
        e2.b = n2;
        return e2;
    }

    public e a(String string) {
        int n2 = string.length();
        if (n2 <= 65535) {
            int n3 = this.b;
            if (n3 + 2 + n2 > this.a.length) {
                this.a(n2 + 2);
            }
            byte[] byArray = this.a;
            byArray[n3++] = (byte)(n2 >>> 8);
            int n4 = n3 + 1;
            this.a[n3] = (byte)n2;
            for (n3 = 0; n3 < n2; ++n3) {
                int n5 = string.charAt(n3);
                if (n5 >= 1 && n5 <= 127) {
                    int n6 = n5;
                    n5 = n4 + 1;
                    byArray[n4] = (byte)n6;
                    n4 = n5;
                    continue;
                }
                this.b = n4;
                return this.a(string, n3, 65535);
            }
            this.b = n4;
            return this;
        }
        throw new IllegalArgumentException("UTF8 string too large");
    }

    final e a(String string, int n2, int n3) {
        int n4;
        int n5 = string.length();
        int n6 = n2;
        for (n4 = n2; n4 < n5; ++n4) {
            char c2 = string.charAt(n4);
            if (c2 >= '\u0001' && c2 <= '\u007f') {
                ++n6;
                continue;
            }
            if (c2 <= '\u07ff') {
                n6 += 2;
                continue;
            }
            n6 += 3;
        }
        if (n6 <= n3) {
            n3 = this.b - n2 - 2;
            if (n3 >= 0) {
                this.a[n3] = (byte)(n6 >>> 8);
                this.a[++n3] = (byte)n6;
            }
            if (this.b + n6 - n2 > this.a.length) {
                this.a(n6 - n2);
            }
            n3 = this.b;
            while (n2 < n5) {
                char c3 = string.charAt(n2);
                n4 = c3;
                if (c3 >= '\u0001' && n4 <= 127) {
                    this.a[n3++] = (byte)n4;
                } else if (n4 <= 2047) {
                    this.a[n3++] = (byte)(n4 >> 6 & 0x1F | 0xC0);
                    n4 = n3 + 1;
                    this.a[n3] = (byte)(n4 & 0x3F | 0x80);
                    n3 = n4;
                } else {
                    byte[] byArray = this.a;
                    int n7 = n4;
                    int n8 = n4;
                    byArray[n3++] = (byte)(n4 >> 12 & 0xF | 0xE0);
                    n4 = n3 + 1;
                    byArray[n3] = (byte)(n8 >> 6 & 0x3F | 0x80);
                    n3 = n4 + 1;
                    this.a[n4] = (byte)(n7 & 0x3F | 0x80);
                }
                ++n2;
            }
            this.b = n3;
            return this;
        }
        throw new IllegalArgumentException("UTF8 string too large");
    }

    public e a(byte[] byArray, int n2, int n3) {
        if (this.b + n3 > this.a.length) {
            this.a(n3);
        }
        if (byArray != null) {
            byte[] byArray2 = byArray;
            e e2 = this;
            byArray = e2.a;
            int n4 = e2.b;
            System.arraycopy(byArray2, n2, byArray, n4, n3);
        }
        e e3 = this;
        e3.b += n3;
        return e3;
    }
}

