/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.errorprone.annotations.CanIgnoreReturnValue
 *  org.springframework.beans.factory.DisposableBean
 */
package org.apereo.cas.util.scripting;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Set;
import org.apereo.cas.util.DigestUtils;
import org.apereo.cas.util.scripting.ExecutableCompiledGroovyScript;
import org.springframework.beans.factory.DisposableBean;

public interface ScriptResourceCacheManager<K extends String, V extends ExecutableCompiledGroovyScript>
extends AutoCloseable,
DisposableBean {
    public static final String BEAN_NAME = "scriptResourceCacheManager";

    public static String computeKey(String ... keys) {
        String rawKey = String.join((CharSequence)":", keys);
        return DigestUtils.sha256(rawKey);
    }

    public V get(K var1);

    public boolean containsKey(K var1);

    public ScriptResourceCacheManager<K, V> put(K var1, V var2);

    public ScriptResourceCacheManager<K, V> remove(K var1);

    public Set<String> getKeys();

    @CanIgnoreReturnValue
    default public ScriptResourceCacheManager<K, V> clear() {
        this.close();
        return this;
    }

    @Override
    public void close();

    default public void destroy() {
        this.close();
    }

    public boolean isEmpty();

    public ExecutableCompiledGroovyScript resolveScriptableResource(String var1, String ... var2);
}

