/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.commons.jexl3.JexlOptions;

public interface JexlContext {
    public Object get(String var1);

    public void set(String var1, Object var2);

    public boolean has(String var1);

    public static interface CancellationHandle {
        public AtomicBoolean getCancellation();
    }

    public static interface PragmaProcessor {
        public void processPragma(String var1, Object var2);
    }

    public static interface OptionsHandle {
        public JexlOptions getEngineOptions();
    }

    public static interface AnnotationProcessor {
        public Object processAnnotation(String var1, Object[] var2, Callable<Object> var3) throws Exception;
    }

    public static interface ThreadLocal
    extends JexlContext {
    }

    public static interface NamespaceFunctor {
        public Object createFunctor(JexlContext var1);
    }

    public static interface NamespaceResolver {
        public Object resolveNamespace(String var1);
    }
}

