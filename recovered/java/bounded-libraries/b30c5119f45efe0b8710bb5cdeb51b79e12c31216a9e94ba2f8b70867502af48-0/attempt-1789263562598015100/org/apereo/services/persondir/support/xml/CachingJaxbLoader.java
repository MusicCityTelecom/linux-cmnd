/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.services.persondir.support.xml;

public interface CachingJaxbLoader<T> {
    public T getUnmarshalledObject(UnmarshallingCallback<T> var1);

    public T getUnmarshalledObject();

    public static interface UnmarshallingCallback<T> {
        public void postProcessUnmarshalling(T var1);
    }
}

