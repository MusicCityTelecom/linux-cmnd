/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.ast;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.Variable;

public class VariableScope {
    private VariableScope parent;
    private ClassNode classScope;
    private boolean inStaticContext;
    private Map<String, Variable> declaredVariables = Collections.emptyMap();
    private Map<String, Variable> referencedLocalVariables = Collections.emptyMap();
    private Map<String, Variable> referencedClassVariables = Collections.emptyMap();

    public VariableScope() {
    }

    public VariableScope(VariableScope parent) {
        this.parent = parent;
    }

    public VariableScope getParent() {
        return this.parent;
    }

    public boolean isRoot() {
        return this.parent == null;
    }

    public ClassNode getClassScope() {
        return this.classScope;
    }

    public boolean isClassScope() {
        return this.classScope != null;
    }

    public void setClassScope(ClassNode classScope) {
        this.classScope = classScope;
    }

    public boolean isInStaticContext() {
        return this.inStaticContext;
    }

    public void setInStaticContext(boolean inStaticContext) {
        this.inStaticContext = inStaticContext;
    }

    public Variable getDeclaredVariable(String name) {
        return this.declaredVariables.get(name);
    }

    public Variable getReferencedLocalVariable(String name) {
        return this.referencedLocalVariables.get(name);
    }

    public Variable getReferencedClassVariable(String name) {
        return this.referencedClassVariables.get(name);
    }

    public boolean isReferencedLocalVariable(String name) {
        return this.referencedLocalVariables.containsKey(name);
    }

    public boolean isReferencedClassVariable(String name) {
        return this.referencedClassVariables.containsKey(name);
    }

    public Map<String, Variable> getDeclaredVariables() {
        if (this.declaredVariables == Collections.EMPTY_MAP) {
            return this.declaredVariables;
        }
        return Collections.unmodifiableMap(this.declaredVariables);
    }

    public Map<String, Variable> getReferencedClassVariables() {
        if (this.referencedClassVariables == Collections.EMPTY_MAP) {
            return this.referencedClassVariables;
        }
        return Collections.unmodifiableMap(this.referencedClassVariables);
    }

    public int getReferencedLocalVariablesCount() {
        return this.referencedLocalVariables.size();
    }

    public Iterator<Variable> getDeclaredVariablesIterator() {
        return this.getDeclaredVariables().values().iterator();
    }

    public Iterator<Variable> getReferencedLocalVariablesIterator() {
        return this.referencedLocalVariables.values().iterator();
    }

    public Iterator<Variable> getReferencedClassVariablesIterator() {
        return this.getReferencedClassVariables().values().iterator();
    }

    public void putDeclaredVariable(Variable var) {
        if (this.declaredVariables == Collections.EMPTY_MAP) {
            this.declaredVariables = new LinkedHashMap<String, Variable>();
        }
        this.declaredVariables.put(var.getName(), var);
    }

    public void putReferencedLocalVariable(Variable var) {
        if (this.referencedLocalVariables == Collections.EMPTY_MAP) {
            this.referencedLocalVariables = new LinkedHashMap<String, Variable>();
        }
        this.referencedLocalVariables.put(var.getName(), var);
    }

    public void putReferencedClassVariable(Variable var) {
        if (this.referencedClassVariables == Collections.EMPTY_MAP) {
            this.referencedClassVariables = new LinkedHashMap<String, Variable>();
        }
        this.referencedClassVariables.put(var.getName(), var);
    }

    public Object removeReferencedClassVariable(String name) {
        if (this.referencedClassVariables.isEmpty()) {
            return null;
        }
        return this.referencedClassVariables.remove(name);
    }

    public VariableScope copy() {
        VariableScope that = new VariableScope(this.parent);
        that.classScope = this.classScope;
        that.inStaticContext = this.inStaticContext;
        if (!this.declaredVariables.isEmpty()) {
            that.declaredVariables = new LinkedHashMap<String, Variable>(this.declaredVariables);
        }
        if (!this.referencedLocalVariables.isEmpty()) {
            that.referencedLocalVariables = new LinkedHashMap<String, Variable>(this.referencedLocalVariables);
        }
        if (!this.referencedClassVariables.isEmpty()) {
            that.referencedClassVariables = new LinkedHashMap<String, Variable>(this.referencedClassVariables);
        }
        return that;
    }
}

