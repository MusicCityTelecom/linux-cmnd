/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.crypto.sphincsplus;

import java.util.LinkedList;
import org.bouncycastle.pqc.crypto.sphincsplus.ADRS;
import org.bouncycastle.pqc.crypto.sphincsplus.NodeEntry;
import org.bouncycastle.pqc.crypto.sphincsplus.SIG_FORS;
import org.bouncycastle.pqc.crypto.sphincsplus.SPHINCSPlusEngine;
import org.bouncycastle.pqc.crypto.sphincsplus.WotsPlus;
import org.bouncycastle.util.Arrays;

class Fors {
    private final WotsPlus wots;
    SPHINCSPlusEngine engine;

    public Fors(SPHINCSPlusEngine sPHINCSPlusEngine) {
        this.engine = sPHINCSPlusEngine;
        this.wots = new WotsPlus(sPHINCSPlusEngine);
    }

    byte[] pkGen(byte[] byArray, byte[] byArray2, ADRS aDRS) {
        ADRS aDRS2 = new ADRS(aDRS);
        byte[][] byArrayArray = new byte[this.engine.K][];
        for (int i = 0; i < this.engine.K; ++i) {
            byArrayArray[i] = this.treehash(byArray, i * this.engine.T, this.engine.A, byArray2, aDRS);
        }
        aDRS2.setType(4);
        aDRS2.setKeyPairAddress(aDRS.getKeyPairAddress());
        return this.engine.T_l(byArray2, aDRS2, Arrays.concatenate(byArrayArray));
    }

    byte[] treehash(byte[] byArray, int n, int n2, byte[] byArray2, ADRS aDRS) {
        ADRS aDRS2 = new ADRS(aDRS);
        LinkedList<NodeEntry> linkedList = new LinkedList<NodeEntry>();
        if (n % (1 << n2) != 0) {
            return null;
        }
        for (int i = 0; i < 1 << n2; ++i) {
            aDRS2.setTreeHeight(0);
            aDRS2.setTreeIndex(n + i);
            byte[] byArray3 = this.engine.PRF(byArray, aDRS2);
            byte[] byArray4 = this.engine.F(byArray2, aDRS2, byArray3);
            aDRS2.setTreeHeight(1);
            aDRS2.setTreeIndex(n + i);
            while (!linkedList.isEmpty() && ((NodeEntry)linkedList.get((int)0)).nodeHeight == aDRS2.getTreeHeight()) {
                aDRS2.setTreeIndex((aDRS2.getTreeIndex() - 1) / 2);
                NodeEntry nodeEntry = (NodeEntry)linkedList.remove(0);
                byArray4 = this.engine.H(byArray2, aDRS2, nodeEntry.nodeValue, byArray4);
                aDRS2.setTreeHeight(aDRS2.getTreeHeight() + 1);
            }
            linkedList.add(0, new NodeEntry(byArray4, aDRS2.getTreeHeight()));
        }
        return ((NodeEntry)linkedList.get((int)0)).nodeValue;
    }

    public SIG_FORS[] sign(byte[] byArray, byte[] byArray2, byte[] byArray3, ADRS aDRS) {
        int[] nArray = Fors.message_to_idxs(byArray, this.engine.K, this.engine.A);
        SIG_FORS[] sIG_FORSArray = new SIG_FORS[this.engine.K];
        int n = this.engine.T;
        for (int i = 0; i < this.engine.K; ++i) {
            int n2 = nArray[i];
            aDRS.setTreeHeight(0);
            aDRS.setTreeIndex(i * n + n2);
            byte[] byArray4 = this.engine.PRF(byArray2, aDRS);
            byte[][] byArrayArray = new byte[this.engine.A][];
            for (int j = 0; j < this.engine.A; ++j) {
                int n3 = n2 / (1 << j) ^ 1;
                byArrayArray[j] = this.treehash(byArray2, i * n + n3 * (1 << j), j, byArray3, aDRS);
            }
            sIG_FORSArray[i] = new SIG_FORS(byArray4, byArrayArray);
        }
        return sIG_FORSArray;
    }

    public byte[] pkFromSig(SIG_FORS[] sIG_FORSArray, byte[] byArray, byte[] byArray2, ADRS aDRS) {
        byte[][] byArrayArray = new byte[2][];
        byte[][] byArrayArray2 = new byte[this.engine.K][];
        int n = this.engine.T;
        int[] nArray = Fors.message_to_idxs(byArray, this.engine.K, this.engine.A);
        for (int i = 0; i < this.engine.K; ++i) {
            int n2 = nArray[i];
            byte[] byArray3 = sIG_FORSArray[i].getSK();
            aDRS.setTreeHeight(0);
            aDRS.setTreeIndex(i * n + n2);
            byArrayArray[0] = this.engine.F(byArray2, aDRS, byArray3);
            byte[][] byArray4 = sIG_FORSArray[i].getAuthPath();
            aDRS.setTreeIndex(i * n + n2);
            for (int j = 0; j < this.engine.A; ++j) {
                aDRS.setTreeHeight(j + 1);
                if (n2 / (1 << j) % 2 == 0) {
                    aDRS.setTreeIndex(aDRS.getTreeIndex() / 2);
                    byArrayArray[1] = this.engine.H(byArray2, aDRS, byArrayArray[0], byArray4[j]);
                } else {
                    aDRS.setTreeIndex((aDRS.getTreeIndex() - 1) / 2);
                    byArrayArray[1] = this.engine.H(byArray2, aDRS, byArray4[j], byArrayArray[0]);
                }
                byArrayArray[0] = byArrayArray[1];
            }
            byArrayArray2[i] = byArrayArray[0];
        }
        ADRS aDRS2 = new ADRS(aDRS);
        aDRS2.setType(4);
        aDRS2.setKeyPairAddress(aDRS.getKeyPairAddress());
        return this.engine.T_l(byArray2, aDRS2, Arrays.concatenate(byArrayArray2));
    }

    static int[] message_to_idxs(byte[] byArray, int n, int n2) {
        int n3 = 0;
        int[] nArray = new int[n];
        for (int i = 0; i < n; ++i) {
            nArray[i] = 0;
            for (int j = 0; j < n2; ++j) {
                int n4 = i;
                nArray[n4] = nArray[n4] ^ (byArray[n3 >> 3] >> (n3 & 7) & 1) << j;
                ++n3;
            }
        }
        return nArray;
    }
}

