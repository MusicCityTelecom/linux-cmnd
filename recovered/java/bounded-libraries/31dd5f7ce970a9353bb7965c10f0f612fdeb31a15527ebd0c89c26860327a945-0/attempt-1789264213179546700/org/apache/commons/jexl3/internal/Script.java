/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.internal;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlExpression;
import org.apache.commons.jexl3.JexlFeatures;
import org.apache.commons.jexl3.JexlInfo;
import org.apache.commons.jexl3.JexlOptions;
import org.apache.commons.jexl3.JexlScript;
import org.apache.commons.jexl3.internal.Closure;
import org.apache.commons.jexl3.internal.Debugger;
import org.apache.commons.jexl3.internal.Engine;
import org.apache.commons.jexl3.internal.Frame;
import org.apache.commons.jexl3.internal.Interpreter;
import org.apache.commons.jexl3.parser.ASTJexlScript;

public class Script
implements JexlScript,
JexlExpression {
    protected final Engine jexl;
    protected final String source;
    protected final ASTJexlScript script;
    protected int version;

    protected ASTJexlScript getScript() {
        return this.script;
    }

    protected Script(Engine engine, String expr, ASTJexlScript ref) {
        this.jexl = engine;
        this.source = expr;
        this.script = ref;
        this.version = this.jexl.getUberspect().getVersion();
    }

    protected void checkCacheVersion() {
        int uberVersion = this.jexl.getUberspect().getVersion();
        if (this.version != uberVersion) {
            if (this.version > 0) {
                this.script.clearCache();
            }
            this.version = uberVersion;
        }
    }

    protected Frame createFrame(Object[] args) {
        return this.script.createFrame(args);
    }

    protected Interpreter createInterpreter(JexlContext context, Frame frame) {
        JexlOptions opts = this.jexl.options(this.script, context);
        return this.jexl.createInterpreter(context, frame, opts);
    }

    public JexlEngine getEngine() {
        return this.jexl;
    }

    @Override
    public String getSourceText() {
        return this.source;
    }

    @Override
    public String getParsedText() {
        return this.getParsedText(2);
    }

    @Override
    public String getParsedText(int indent) {
        Debugger debug = new Debugger();
        debug.setIndentation(indent);
        debug.debug(this.script, false);
        return debug.toString();
    }

    public String toString() {
        String src = this.source;
        if (src == null) {
            Debugger debug = new Debugger();
            debug.debug(this.script, false);
            src = debug.toString();
        }
        return src.toString();
    }

    public int hashCode() {
        int hash = 17;
        hash = 31 * hash + (this.jexl != null ? this.jexl.hashCode() : 0);
        hash = 31 * hash + (this.source != null ? this.source.hashCode() : 0);
        return hash;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        Script other = (Script)obj;
        if (this.jexl != other.jexl) {
            return false;
        }
        return Objects.equals(this.source, other.source);
    }

    @Override
    public Object evaluate(JexlContext context) {
        return this.execute(context);
    }

    @Override
    public Object execute(JexlContext context) {
        this.checkCacheVersion();
        Frame frame = this.createFrame(null);
        Interpreter interpreter = this.createInterpreter(context, frame);
        return interpreter.interpret(this.script);
    }

    @Override
    public Object execute(JexlContext context, Object ... args) {
        this.checkCacheVersion();
        Frame frame = this.createFrame(args != null && args.length > 0 ? args : null);
        Interpreter interpreter = this.createInterpreter(context, frame);
        return interpreter.interpret(this.script);
    }

    @Override
    public JexlScript curry(Object ... args) {
        String[] parms = this.script.getParameters();
        if (parms == null || parms.length == 0) {
            return this;
        }
        return new Closure(this, args);
    }

    @Override
    public String[] getParameters() {
        return this.script.getParameters();
    }

    @Override
    public String[] getUnboundParameters() {
        return this.getParameters();
    }

    @Override
    public String[] getLocalVariables() {
        return this.script.getLocalVariables();
    }

    public JexlInfo getInfo() {
        return this.script.jexlInfo();
    }

    public JexlFeatures getFeatures() {
        return this.script.getFeatures();
    }

    @Override
    public Set<List<String>> getVariables() {
        return this.jexl.getVariables(this.script);
    }

    @Override
    public Map<String, Object> getPragmas() {
        return this.script.getPragmas();
    }

    public Callable callable(JexlContext context) {
        return this.callable(context, null);
    }

    public Callable callable(JexlContext context, Object ... args) {
        return new Callable(this.createInterpreter(context, this.script.createFrame(args)));
    }

    public class Callable
    implements java.util.concurrent.Callable<Object> {
        protected final Interpreter interpreter;
        protected volatile Object result;

        protected Callable(Interpreter intrprtr) {
            this.interpreter = intrprtr;
            this.result = intrprtr;
        }

        protected Object interpret() {
            return this.interpreter.interpret(Script.this.script);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Object call() throws Exception {
            Callable callable = this;
            synchronized (callable) {
                if (this.result == this.interpreter) {
                    Script.this.checkCacheVersion();
                    this.result = this.interpret();
                }
                return this.result;
            }
        }

        public boolean cancel() {
            return this.interpreter.cancel();
        }

        public boolean isCancelled() {
            return this.interpreter.isCancelled();
        }

        public boolean isCancellable() {
            return this.interpreter.isCancellable();
        }
    }
}

