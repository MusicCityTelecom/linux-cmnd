/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.configurationprocessor.fieldvalues.javac;

import java.util.Collections;
import java.util.Set;
import javax.lang.model.element.Modifier;
import org.springframework.boot.configurationprocessor.fieldvalues.javac.ExpressionTree;
import org.springframework.boot.configurationprocessor.fieldvalues.javac.ReflectionWrapper;

class VariableTree
extends ReflectionWrapper {
    VariableTree(Object instance) {
        super("com.sun.source.tree.VariableTree", instance);
    }

    String getName() throws Exception {
        return this.findMethod("getName", new Class[0]).invoke(this.getInstance(), new Object[0]).toString();
    }

    String getType() throws Exception {
        return this.findMethod("getType", new Class[0]).invoke(this.getInstance(), new Object[0]).toString();
    }

    ExpressionTree getInitializer() throws Exception {
        Object instance = this.findMethod("getInitializer", new Class[0]).invoke(this.getInstance(), new Object[0]);
        return instance != null ? new ExpressionTree(instance) : null;
    }

    Set<Modifier> getModifierFlags() throws Exception {
        Object modifiers = this.findMethod("getModifiers", new Class[0]).invoke(this.getInstance(), new Object[0]);
        if (modifiers == null) {
            return Collections.emptySet();
        }
        return (Set)VariableTree.findMethod(this.findClass("com.sun.source.tree.ModifiersTree"), "getFlags", new Class[0]).invoke(modifiers, new Object[0]);
    }
}

