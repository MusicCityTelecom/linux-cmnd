/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.config.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

public interface MethodAnnotationPostProcessor<T extends Annotation> {
    public Object postProcess(Object var1, String var2, Method var3, List<Annotation> var4);

    public boolean shouldCreateEndpoint(Method var1, List<Annotation> var2);
}

