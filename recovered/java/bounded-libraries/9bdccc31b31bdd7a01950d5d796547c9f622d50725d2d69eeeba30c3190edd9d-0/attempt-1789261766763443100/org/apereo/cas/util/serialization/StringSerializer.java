/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.http.MediaType
 */
package org.apereo.cas.util.serialization;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.http.MediaType;

public interface StringSerializer<T>
extends Serializable {
    public T from(String var1);

    public T from(Reader var1);

    public T from(InputStream var1);

    public T from(File var1);

    public T from(Writer var1);

    public void to(OutputStream var1, T var2);

    public void to(Writer var1, T var2);

    public void to(File var1, T var2);

    public String toString(T var1);

    default public Collection<T> load(InputStream stream) {
        T result = this.from(stream);
        return StringSerializer.makeCollectionOf(result);
    }

    default public Collection<T> load(Reader stream) {
        T result = this.from(stream);
        return StringSerializer.makeCollectionOf(result);
    }

    default public boolean supports(File file) {
        return true;
    }

    default public boolean supports(String content) {
        return true;
    }

    public Class<T> getTypeToSerialize();

    public List<T> fromList(String var1);

    private static <T> Collection<T> makeCollectionOf(T elem) {
        if (elem != null) {
            ArrayList<T> list = new ArrayList<T>(1);
            list.add(elem);
            return list;
        }
        return new ArrayList(0);
    }

    default public List<MediaType> getContentTypes() {
        return List.of(MediaType.TEXT_PLAIN);
    }
}

