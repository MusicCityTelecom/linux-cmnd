/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.internal;

import java.util.ArrayDeque;
import java.util.Deque;
import org.apache.commons.jexl3.internal.Frame;
import org.apache.commons.jexl3.internal.LexicalScope;
import org.apache.commons.jexl3.internal.Scope;

public class LexicalFrame
extends LexicalScope {
    private final Frame frame;
    protected final LexicalFrame previous;
    private Deque<Object> stack = null;

    public LexicalFrame(Frame scriptf, LexicalFrame outerf) {
        this.previous = outerf;
        this.frame = scriptf;
    }

    public LexicalFrame(LexicalFrame src) {
        super(src.symbols, src.moreSymbols);
        this.frame = src.frame;
        this.previous = src.previous;
        this.stack = src.stack != null ? new ArrayDeque<Object>(src.stack) : null;
    }

    public LexicalFrame defineArgs() {
        if (this.frame != null) {
            int argc = this.frame.getScope().getArgCount();
            for (int a = 0; a < argc; ++a) {
                super.addSymbol(a);
            }
        }
        return this;
    }

    public boolean defineSymbol(int symbol, boolean capture) {
        boolean declared = this.addSymbol(symbol);
        if (declared && capture) {
            if (this.stack == null) {
                this.stack = new ArrayDeque<Object>();
            }
            this.stack.push(symbol);
            Object value = this.frame.get(symbol);
            if (value == null) {
                value = this;
            }
            this.stack.push(value);
        }
        return declared;
    }

    public LexicalFrame pop() {
        this.clearSymbols(s -> this.frame.set(s, Scope.UNDEFINED));
        if (this.stack != null) {
            while (!this.stack.isEmpty()) {
                Object value = this.stack.pop();
                if (value == Scope.UNDECLARED) {
                    value = Scope.UNDEFINED;
                } else if (value == this) {
                    value = null;
                }
                int symbol = (Integer)this.stack.pop();
                this.frame.set(symbol, value);
            }
        }
        return this.previous;
    }
}

