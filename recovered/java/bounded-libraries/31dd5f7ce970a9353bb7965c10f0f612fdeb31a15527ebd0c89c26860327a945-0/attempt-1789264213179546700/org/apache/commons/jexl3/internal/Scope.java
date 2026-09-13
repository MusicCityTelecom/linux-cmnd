/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.commons.jexl3.internal.Frame;

public final class Scope {
    static final Object UNDECLARED = new Object(){

        public String toString() {
            return "??";
        }
    };
    static final Object UNDEFINED = new Object(){

        public String toString() {
            return "?";
        }
    };
    private final Scope parent;
    private int parms;
    private int vars;
    private Map<String, Integer> namedVariables = null;
    private Map<Integer, Integer> capturedVariables = null;
    private static final String[] EMPTY_STRS = new String[0];

    public Scope(Scope scope, String ... parameters) {
        if (parameters != null) {
            this.parms = parameters.length;
            this.namedVariables = new LinkedHashMap<String, Integer>();
            for (int p = 0; p < this.parms; ++p) {
                this.namedVariables.put(parameters[p], p);
            }
        } else {
            this.parms = 0;
        }
        this.vars = 0;
        this.parent = scope;
    }

    public int hashCode() {
        return this.namedVariables == null ? 0 : this.parms ^ this.namedVariables.hashCode();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Scope)) {
            return false;
        }
        Scope scope = (Scope)o;
        if (this.parms != scope.parms) {
            return false;
        }
        if (this.namedVariables == null) {
            return scope.namedVariables == null;
        }
        return this.namedVariables.equals(scope.namedVariables);
    }

    public Integer getSymbol(String name) {
        return this.getSymbol(name, true);
    }

    private Integer getSymbol(String name, boolean capture) {
        Integer pr;
        Integer register;
        Integer n = register = this.namedVariables != null ? this.namedVariables.get(name) : null;
        if (register == null && capture && this.parent != null && (pr = this.parent.getSymbol(name, true)) != null) {
            if (this.capturedVariables == null) {
                this.capturedVariables = new LinkedHashMap<Integer, Integer>();
            }
            if (this.namedVariables == null) {
                this.namedVariables = new LinkedHashMap<String, Integer>();
            }
            register = this.namedVariables.size();
            this.namedVariables.put(name, register);
            this.capturedVariables.put(register, pr);
        }
        return register;
    }

    public boolean isCapturedSymbol(int symbol) {
        return this.capturedVariables != null && this.capturedVariables.containsKey(symbol);
    }

    public int declareParameter(String name) {
        if (this.namedVariables == null) {
            this.namedVariables = new LinkedHashMap<String, Integer>();
        } else if (this.vars > 0) {
            throw new IllegalStateException("cant declare parameters after variables");
        }
        Integer register = this.namedVariables.get(name);
        if (register == null) {
            register = this.namedVariables.size();
            this.namedVariables.put(name, register);
            ++this.parms;
        }
        return register;
    }

    public int declareVariable(String name) {
        Integer register;
        if (this.namedVariables == null) {
            this.namedVariables = new LinkedHashMap<String, Integer>();
        }
        if ((register = this.namedVariables.get(name)) == null) {
            Integer pr;
            register = this.namedVariables.size();
            this.namedVariables.put(name, register);
            ++this.vars;
            if (this.parent != null && (pr = this.parent.getSymbol(name, true)) != null) {
                if (this.capturedVariables == null) {
                    this.capturedVariables = new LinkedHashMap<Integer, Integer>();
                }
                this.capturedVariables.put(register, pr);
            }
        }
        return register;
    }

    public Frame createFrame(Frame frame, Object ... args) {
        if (this.namedVariables == null) {
            return null;
        }
        Object[] arguments = new Object[this.namedVariables.size()];
        Arrays.fill(arguments, UNDECLARED);
        if (frame != null && this.capturedVariables != null && this.parent != null) {
            for (Map.Entry<Integer, Integer> capture : this.capturedVariables.entrySet()) {
                Object arg;
                Integer target = capture.getKey();
                Integer source = capture.getValue();
                arguments[target.intValue()] = arg = frame.get(source);
            }
        }
        return new Frame(this, arguments, 0).assign(args);
    }

    public Integer getCaptured(int symbol) {
        if (this.capturedVariables != null) {
            for (Map.Entry<Integer, Integer> capture : this.capturedVariables.entrySet()) {
                Integer source = capture.getValue();
                if (source != symbol) continue;
                return capture.getKey();
            }
        }
        return null;
    }

    public int getArgCount() {
        return this.parms;
    }

    public String[] getSymbols() {
        return this.namedVariables != null ? this.namedVariables.keySet().toArray(new String[0]) : EMPTY_STRS;
    }

    public String[] getParameters() {
        return this.getParameters(0);
    }

    protected String[] getParameters(int bound) {
        int unbound = this.parms - bound;
        if (this.namedVariables == null || unbound <= 0) {
            return EMPTY_STRS;
        }
        String[] pa = new String[unbound];
        int p = 0;
        for (Map.Entry<String, Integer> entry : this.namedVariables.entrySet()) {
            int argn = entry.getValue();
            if (argn < bound || argn >= this.parms) continue;
            pa[p++] = entry.getKey();
        }
        return pa;
    }

    public String[] getLocalVariables() {
        if (this.namedVariables == null || this.vars <= 0) {
            return EMPTY_STRS;
        }
        ArrayList<String> locals = new ArrayList<String>(this.vars);
        for (Map.Entry<String, Integer> entry : this.namedVariables.entrySet()) {
            int symnum = entry.getValue();
            if (symnum < this.parms || this.capturedVariables != null && this.capturedVariables.containsKey(symnum)) continue;
            locals.add(entry.getKey());
        }
        return locals.toArray(new String[locals.size()]);
    }
}

