/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.configurationprocessor.fieldvalues.javac;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import org.springframework.boot.configurationprocessor.fieldvalues.javac.ReflectionWrapper;
import org.springframework.boot.configurationprocessor.fieldvalues.javac.Tree;

final class Trees
extends ReflectionWrapper {
    private Trees(Object instance) {
        super("com.sun.source.util.Trees", instance);
    }

    Tree getTree(Element element) throws Exception {
        Object tree = this.findMethod("getTree", Element.class).invoke(this.getInstance(), element);
        return tree != null ? new Tree(tree) : null;
    }

    static Trees instance(ProcessingEnvironment env) throws Exception {
        try {
            ClassLoader classLoader = env.getClass().getClassLoader();
            Class<?> type = Trees.findClass(classLoader, "com.sun.source.util.Trees");
            Method method = Trees.findMethod(type, "instance", ProcessingEnvironment.class);
            return new Trees(method.invoke(null, env));
        }
        catch (Exception ex) {
            return Trees.instance(Trees.unwrap(env));
        }
    }

    private static ProcessingEnvironment unwrap(ProcessingEnvironment wrapper) throws Exception {
        Field delegateField = wrapper.getClass().getDeclaredField("delegate");
        delegateField.setAccessible(true);
        return (ProcessingEnvironment)delegateField.get(wrapper);
    }
}

