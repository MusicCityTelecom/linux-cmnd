/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.control.customizers.builder;

import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import org.codehaus.groovy.control.customizers.CompilationCustomizer;
import org.codehaus.groovy.control.customizers.builder.PostCompletionFactory;

public class CustomizersFactory
extends AbstractFactory
implements PostCompletionFactory {
    @Override
    public Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        return new ArrayList();
    }

    @Override
    public void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        if (parent instanceof Collection && child instanceof CompilationCustomizer) {
            ((Collection)parent).add(child);
        }
    }

    @Override
    public Object postCompleteNode(FactoryBuilderSupport factory, Object parent, Object node) {
        if (node instanceof Collection) {
            return ((Collection)node).toArray(new CompilationCustomizer[0]);
        }
        return node;
    }
}

