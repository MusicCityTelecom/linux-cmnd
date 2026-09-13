/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.configurationprocessor.fieldvalues.javac;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import org.springframework.boot.configurationprocessor.fieldvalues.javac.ReflectionWrapper;
import org.springframework.boot.configurationprocessor.fieldvalues.javac.TreeVisitor;
import org.springframework.boot.configurationprocessor.fieldvalues.javac.VariableTree;

class Tree
extends ReflectionWrapper {
    private final Class<?> treeVisitorType = this.findClass("com.sun.source.tree.TreeVisitor");
    private final Method acceptMethod = this.findMethod("accept", this.treeVisitorType, Object.class);
    private final Method getClassTreeMembers = Tree.findMethod(this.findClass("com.sun.source.tree.ClassTree"), "getMembers", new Class[0]);

    Tree(Object instance) {
        super("com.sun.source.tree.Tree", instance);
    }

    void accept(TreeVisitor visitor) throws Exception {
        this.acceptMethod.invoke(this.getInstance(), Proxy.newProxyInstance(this.getInstance().getClass().getClassLoader(), new Class[]{this.treeVisitorType}, (InvocationHandler)new TreeVisitorInvocationHandler(visitor)), 0);
    }

    private class TreeVisitorInvocationHandler
    implements InvocationHandler {
        private TreeVisitor treeVisitor;

        TreeVisitorInvocationHandler(TreeVisitor treeVisitor) {
            this.treeVisitor = treeVisitor;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getName().equals("visitClass") && (Integer)args[1] == 0) {
                Iterable members = (Iterable)Tree.this.getClassTreeMembers.invoke(args[0], new Object[0]);
                for (Object member : members) {
                    if (member == null) continue;
                    Tree.this.acceptMethod.invoke(member, proxy, (Integer)args[1] + 1);
                }
            }
            if (method.getName().equals("visitVariable")) {
                this.treeVisitor.visitVariable(new VariableTree(args[0]));
            }
            return null;
        }
    }
}

