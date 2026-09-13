/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.configurationprocessor.fieldvalues.javac;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.configurationprocessor.fieldvalues.javac.ReflectionWrapper;

class ExpressionTree
extends ReflectionWrapper {
    private final Class<?> literalTreeType = this.findClass("com.sun.source.tree.LiteralTree");
    private final Method literalValueMethod = ExpressionTree.findMethod(this.literalTreeType, "getValue", new Class[0]);
    private final Class<?> methodInvocationTreeType = this.findClass("com.sun.source.tree.MethodInvocationTree");
    private final Method methodInvocationArgumentsMethod = ExpressionTree.findMethod(this.methodInvocationTreeType, "getArguments", new Class[0]);
    private final Class<?> newArrayTreeType = this.findClass("com.sun.source.tree.NewArrayTree");
    private final Method arrayValueMethod = ExpressionTree.findMethod(this.newArrayTreeType, "getInitializers", new Class[0]);

    ExpressionTree(Object instance) {
        super("com.sun.source.tree.ExpressionTree", instance);
    }

    String getKind() throws Exception {
        return this.findMethod("getKind", new Class[0]).invoke(this.getInstance(), new Object[0]).toString();
    }

    Object getLiteralValue() throws Exception {
        if (this.literalTreeType.isAssignableFrom(this.getInstance().getClass())) {
            return this.literalValueMethod.invoke(this.getInstance(), new Object[0]);
        }
        return null;
    }

    Object getFactoryValue() throws Exception {
        List arguments;
        if (this.methodInvocationTreeType.isAssignableFrom(this.getInstance().getClass()) && (arguments = (List)this.methodInvocationArgumentsMethod.invoke(this.getInstance(), new Object[0])).size() == 1) {
            return new ExpressionTree(arguments.get(0)).getLiteralValue();
        }
        return null;
    }

    List<? extends ExpressionTree> getArrayExpression() throws Exception {
        if (this.newArrayTreeType.isAssignableFrom(this.getInstance().getClass())) {
            List elements = (List)this.arrayValueMethod.invoke(this.getInstance(), new Object[0]);
            ArrayList<ExpressionTree> result = new ArrayList<ExpressionTree>();
            if (elements == null) {
                return result;
            }
            for (Object element : elements) {
                result.add(new ExpressionTree(element));
            }
            return result;
        }
        return null;
    }
}

