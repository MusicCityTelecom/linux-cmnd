/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.util.cache.DistributedCacheManager
 */
package org.apereo.cas.util.cache;

import java.io.Serializable;
import org.apereo.cas.util.PublisherIdentifier;
import org.apereo.cas.util.cache.DistributedCacheManager;
import org.apereo.cas.util.cache.DistributedCacheObject;

public abstract class BaseDistributedCacheManager<K extends Serializable, V extends DistributedCacheObject>
implements DistributedCacheManager<K, V, PublisherIdentifier> {
    public void close() {
    }
}

