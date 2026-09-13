/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.crypto.sphincsplus;

import org.bouncycastle.pqc.crypto.sphincsplus.ADRS;
import org.bouncycastle.pqc.crypto.sphincsplus.SPHINCSPlusEngine;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Pack;

class WotsPlus {
    private final SPHINCSPlusEngine engine;
    private final int w;

    WotsPlus(SPHINCSPlusEngine sPHINCSPlusEngine) {
        this.engine = sPHINCSPlusEngine;
        this.w = this.engine.WOTS_W;
    }

    byte[] pkGen(byte[] byArray, byte[] byArray2, ADRS aDRS) {
        ADRS aDRS2 = new ADRS(aDRS);
        byte[][] byArrayArray = new byte[this.engine.WOTS_LEN][];
        for (int i = 0; i < this.engine.WOTS_LEN; ++i) {
            ADRS aDRS3 = new ADRS(aDRS);
            aDRS3.setChainAddress(i);
            aDRS3.setHashAddress(0);
            byte[] byArray3 = this.engine.PRF(byArray, aDRS3);
            byArrayArray[i] = this.chain(byArray3, 0, this.w - 1, byArray2, aDRS3);
        }
        aDRS2.setType(1);
        aDRS2.setKeyPairAddress(aDRS.getKeyPairAddress());
        return this.engine.T_l(byArray2, aDRS2, Arrays.concatenate(byArrayArray));
    }

    byte[] chain(byte[] byArray, int n, int n2, byte[] byArray2, ADRS aDRS) {
        if (n2 == 0) {
            return Arrays.clone(byArray);
        }
        if (n + n2 > this.w - 1) {
            return null;
        }
        byte[] byArray3 = this.chain(byArray, n, n2 - 1, byArray2, aDRS);
        aDRS.setHashAddress(n + n2 - 1);
        byArray3 = this.engine.F(byArray2, aDRS, byArray3);
        return byArray3;
    }

    public byte[] sign(byte[] byArray, byte[] byArray2, byte[] byArray3, ADRS aDRS) {
        int n;
        ADRS aDRS2 = new ADRS(aDRS);
        int n2 = 0;
        int[] nArray = this.base_w(byArray, this.w, this.engine.WOTS_LEN1);
        for (n = 0; n < this.engine.WOTS_LEN1; ++n) {
            n2 += this.w - 1 - nArray[n];
        }
        if (this.engine.WOTS_LOGW % 8 != 0) {
            n2 <<= 8 - this.engine.WOTS_LEN2 * this.engine.WOTS_LOGW % 8;
        }
        n = (this.engine.WOTS_LEN2 * this.engine.WOTS_LOGW + 7) / 8;
        byte[] byArray4 = Pack.intToBigEndian(n2);
        nArray = Arrays.concatenate(nArray, this.base_w(Arrays.copyOfRange(byArray4, n, byArray4.length), this.w, this.engine.WOTS_LEN2));
        byte[][] byArrayArray = new byte[this.engine.WOTS_LEN][];
        for (int i = 0; i < this.engine.WOTS_LEN; ++i) {
            aDRS2.setChainAddress(i);
            aDRS2.setHashAddress(0);
            byte[] byArray5 = this.engine.PRF(byArray2, aDRS2);
            byArrayArray[i] = this.chain(byArray5, 0, nArray[i], byArray3, aDRS2);
        }
        return Arrays.concatenate(byArrayArray);
    }

    int[] base_w(byte[] byArray, int n, int n2) {
        int n3 = 0;
        int n4 = 0;
        byte by = 0;
        int n5 = 0;
        int[] nArray = new int[n2];
        for (int i = 0; i < n2; ++i) {
            if (n5 == 0) {
                by = byArray[n3];
                ++n3;
                n5 += 8;
            }
            nArray[n4] = by >>> (n5 -= this.engine.WOTS_LOGW) & n - 1;
            ++n4;
        }
        return nArray;
    }

    public byte[] pkFromSig(byte[] byArray, byte[] byArray2, byte[] byArray3, ADRS aDRS) {
        int n;
        int n2 = 0;
        ADRS aDRS2 = new ADRS(aDRS);
        int[] nArray = this.base_w(byArray2, this.w, this.engine.WOTS_LEN1);
        for (n = 0; n < this.engine.WOTS_LEN1; ++n) {
            n2 += this.w - 1 - nArray[n];
        }
        n = (this.engine.WOTS_LEN2 * this.engine.WOTS_LOGW + 7) / 8;
        nArray = Arrays.concatenate(nArray, this.base_w(Arrays.copyOfRange(Pack.intToBigEndian(n2 <<= 8 - this.engine.WOTS_LEN2 * this.engine.WOTS_LOGW % 8), 4 - n, 4), this.w, this.engine.WOTS_LEN2));
        byte[] byArray4 = new byte[this.engine.N];
        byte[][] byArrayArray = new byte[this.engine.WOTS_LEN][];
        for (int i = 0; i < this.engine.WOTS_LEN; ++i) {
            aDRS.setChainAddress(i);
            System.arraycopy(byArray, i * this.engine.N, byArray4, 0, this.engine.N);
            byArrayArray[i] = this.chain(byArray4, nArray[i], this.w - 1 - nArray[i], byArray3, aDRS);
        }
        aDRS2.setType(1);
        aDRS2.setKeyPairAddress(aDRS.getKeyPairAddress());
        return this.engine.T_l(byArray3, aDRS2, Arrays.concatenate(byArrayArray));
    }
}

