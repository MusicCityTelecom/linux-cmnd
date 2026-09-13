/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.internal;

import java.util.BitSet;
import java.util.function.IntConsumer;

public class LexicalScope {
    protected static final int LONGBITS = 64;
    protected long symbols = 0L;
    protected BitSet moreSymbols = null;

    public LexicalScope() {
    }

    protected LexicalScope(long s, BitSet ms) {
        this.symbols = s;
        this.moreSymbols = ms != null ? (BitSet)ms.clone() : null;
    }

    protected final BitSet moreSymbols() {
        if (this.moreSymbols == null) {
            this.moreSymbols = new BitSet();
        }
        return this.moreSymbols;
    }

    public boolean hasSymbol(int symbol) {
        if (symbol < 64) {
            return (this.symbols & 1L << symbol) != 0L;
        }
        return this.moreSymbols != null && this.moreSymbols.get(symbol - 64);
    }

    public boolean addSymbol(int symbol) {
        if (symbol < 64) {
            if ((this.symbols & 1L << symbol) != 0L) {
                return false;
            }
            this.symbols |= 1L << symbol;
        } else {
            int s = symbol - 64;
            BitSet ms = this.moreSymbols();
            if (ms.get(s)) {
                return false;
            }
            ms.set(s, true);
        }
        return true;
    }

    public final void clearSymbols(IntConsumer cleanSymbol) {
        if (cleanSymbol != null) {
            int s;
            for (long clean = this.symbols; clean != 0L; clean &= 1L << s ^ 0xFFFFFFFFFFFFFFFFL) {
                s = Long.numberOfTrailingZeros(clean);
                cleanSymbol.accept(s);
            }
        }
        this.symbols = 0L;
        if (this.moreSymbols != null) {
            if (cleanSymbol != null) {
                int s = this.moreSymbols.nextSetBit(0);
                while (s != -1) {
                    cleanSymbol.accept(s + 64);
                    s = this.moreSymbols.nextSetBit(s + 1);
                }
            }
            this.moreSymbols.clear();
        }
    }

    public int getSymbolCount() {
        return Long.bitCount(this.symbols) + (this.moreSymbols == null ? 0 : this.moreSymbols.cardinality());
    }
}

