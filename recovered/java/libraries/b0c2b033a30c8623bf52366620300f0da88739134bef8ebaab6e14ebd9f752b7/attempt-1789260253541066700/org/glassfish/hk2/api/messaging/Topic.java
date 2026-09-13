/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.api.messaging;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Set;

public interface Topic<T> {
    public void publish(T var1);

    public Topic<T> named(String var1);

    public <U> Topic<U> ofType(Type var1);

    public Topic<T> qualifiedWith(Annotation ... var1);

    public Type getTopicType();

    public Set<Annotation> getTopicQualifiers();
}

