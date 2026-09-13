/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.internal;

import java.util.Objects;
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.internal.Frame;
import org.apache.commons.jexl3.internal.Interpreter;
import org.apache.commons.jexl3.internal.Scope;
import org.apache.commons.jexl3.internal.Script;
import org.apache.commons.jexl3.parser.ASTJexlLambda;

public class Closure
extends Script {
    protected final Frame frame;

    protected Closure(Interpreter theCaller, ASTJexlLambda lambda) {
        super(theCaller.jexl, null, lambda);
        this.frame = lambda.createFrame(theCaller.frame, new Object[0]);
    }

    protected Closure(Script base, Object[] args) {
        super(base.jexl, base.source, base.script);
        Frame sf = base instanceof Closure ? ((Closure)base).frame : null;
        this.frame = sf == null ? this.script.createFrame(args) : sf.assign(args);
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = 31 * hash + (this.jexl != null ? this.jexl.hashCode() : 0);
        hash = 31 * hash + (this.source != null ? this.source.hashCode() : 0);
        hash = 31 * hash + (this.frame != null ? this.frame.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        Closure other = (Closure)obj;
        if (this.jexl != other.jexl) {
            return false;
        }
        if (!Objects.equals(this.source, other.source)) {
            return false;
        }
        return Objects.equals(this.frame, other.frame);
    }

    @Override
    public String[] getUnboundParameters() {
        return this.frame.getUnboundParameters();
    }

    public void setCaptured(int symbol, Object value) {
        Integer reg;
        ASTJexlLambda lambda;
        Scope scope;
        if (this.script instanceof ASTJexlLambda && (scope = (lambda = (ASTJexlLambda)this.script).getScope()) != null && (reg = scope.getCaptured(symbol)) != null) {
            this.frame.set(reg, value);
        }
    }

    @Override
    public Object evaluate(JexlContext context) {
        return this.execute(context, null);
    }

    @Override
    public Object execute(JexlContext context) {
        return this.execute(context, null);
    }

    @Override
    public Object execute(JexlContext context, Object ... args) {
        Frame local = this.frame != null ? this.frame.assign(args) : null;
        Interpreter interpreter = this.createInterpreter(context, local);
        return interpreter.runClosure(this, null);
    }

    @Override
    public Script.Callable callable(JexlContext context, Object ... args) {
        Frame local = this.frame != null ? this.frame.assign(args) : null;
        return new Script.Callable(this.createInterpreter(context, local)){

            @Override
            public Object interpret() {
                return this.interpreter.runClosure(Closure.this, null);
            }
        };
    }
}

