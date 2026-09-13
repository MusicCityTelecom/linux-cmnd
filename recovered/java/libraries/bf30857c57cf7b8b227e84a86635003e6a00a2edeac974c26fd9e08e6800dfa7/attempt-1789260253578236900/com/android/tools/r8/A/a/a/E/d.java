/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.r8.A.a.a.E;

import com.android.tools.r8.A.a.a.E.k;
import com.android.tools.r8.A.a.a.E.z;
import com.android.tools.r8.A.a.a.u;
import com.android.tools.r8.a.a.a.e.a_0;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class d
extends a_0 {
    public int g;
    public List<Object> h;
    public List<Object> i;

    private d() {
        super(-1);
    }

    public d(int n2, int n3, Object[] objectArray, int n4, Object[] objectArray2) {
        super(-1);
        this.g = n2;
        switch (n2) {
            default: {
                throw new IllegalArgumentException();
            }
            case 4: {
                this.i = z.a(1, objectArray2);
                break;
            }
            case 2: {
                this.h = z.a(n3);
                break;
            }
            case 1: {
                this.h = z.a(n3, objectArray);
                break;
            }
            case -1: 
            case 0: {
                this.h = z.a(n3, objectArray);
                this.i = z.a(n4, objectArray2);
            }
            case 3: 
        }
    }

    private static Object[] a(List<Object> list) {
        int n2 = list.size();
        Object[] objectArray = new Object[n2];
        for (int i2 = 0; i2 < n2; ++i2) {
            Object object = list.get(i2);
            if (object instanceof k) {
                object = ((k)object).c();
            }
            objectArray[i2] = object;
        }
        return objectArray;
    }

    @Override
    public int b() {
        return 14;
    }

    @Override
    public void a(u objectArray) {
        int n2 = objectArray2.g;
        switch (n2) {
            default: {
                throw new IllegalArgumentException();
            }
            case 4: {
                Object[] objectArray2 = d.a(objectArray2.i);
                objectArray.a(n2, 0, null, 1, objectArray2);
                break;
            }
            case 3: {
                objectArray.a(n2, 0, null, 0, null);
                break;
            }
            case 2: {
                objectArray.a(n2, objectArray2.h.size(), null, 0, null);
                break;
            }
            case 1: {
                int n3 = objectArray2.h.size();
                objectArray.a(n2, n3, d.a(objectArray2.h), 0, null);
                break;
            }
            case -1: 
            case 0: {
                Object[] objectArray3 = objectArray;
                int n4 = n2;
                int n5 = objectArray2.h.size();
                objectArray = d.a(objectArray2.h);
                n2 = objectArray2.i.size();
                Object[] objectArray4 = d.a(objectArray2.i);
                objectArray3.a(n4, n5, objectArray, n2, objectArray4);
            }
        }
    }

    @Override
    public a_0 a(Map<k, k> map) {
        Object object;
        int n2;
        int n3;
        d d2;
        d d3 = d2;
        d3();
        d2.g = this.g;
        if (this.h != null) {
            d3.h = new ArrayList<Object>();
            n3 = this.h.size();
            for (n2 = 0; n2 < n3; ++n2) {
                object = this.h.get(n2);
                if (object instanceof k) {
                    object = map.get(object);
                }
                d3.h.add(object);
            }
        }
        if (this.i != null) {
            d3.i = new ArrayList<Object>();
            n3 = this.i.size();
            for (n2 = 0; n2 < n3; ++n2) {
                object = this.i.get(n2);
                if (object instanceof k) {
                    object = map.get(object);
                }
                d3.i.add(object);
            }
        }
        return d3;
    }
}

