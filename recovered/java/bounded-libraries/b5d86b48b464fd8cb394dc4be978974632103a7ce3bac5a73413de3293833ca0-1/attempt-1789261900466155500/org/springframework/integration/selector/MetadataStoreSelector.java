/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 *  org.springframework.util.Assert
 */
package org.springframework.integration.selector;

import java.util.function.BiPredicate;
import org.springframework.integration.core.MessageSelector;
import org.springframework.integration.handler.MessageProcessor;
import org.springframework.integration.metadata.ConcurrentMetadataStore;
import org.springframework.integration.metadata.SimpleMetadataStore;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.util.Assert;

public class MetadataStoreSelector
implements MessageSelector {
    private final ConcurrentMetadataStore metadataStore;
    private final MessageProcessor<String> keyStrategy;
    private final MessageProcessor<String> valueStrategy;
    @Nullable
    private BiPredicate<String, String> compareValues;

    public MetadataStoreSelector(MessageProcessor<String> keyStrategy) {
        this(keyStrategy, (MessageProcessor<String>)null);
    }

    public MetadataStoreSelector(MessageProcessor<String> keyStrategy, MessageProcessor<String> valueStrategy) {
        this(keyStrategy, valueStrategy, new SimpleMetadataStore());
    }

    public MetadataStoreSelector(MessageProcessor<String> keyStrategy, ConcurrentMetadataStore metadataStore) {
        this(keyStrategy, null, metadataStore);
    }

    public MetadataStoreSelector(MessageProcessor<String> keyStrategy, MessageProcessor<String> valueStrategy, ConcurrentMetadataStore metadataStore) {
        Assert.notNull(keyStrategy, (String)"'keyStrategy' must not be null");
        Assert.notNull((Object)metadataStore, (String)"'metadataStore' must not be null");
        this.metadataStore = metadataStore;
        this.keyStrategy = keyStrategy;
        this.valueStrategy = valueStrategy;
    }

    public void setCompareValues(@Nullable BiPredicate<String, String> compareValues) {
        this.compareValues = compareValues;
    }

    public MetadataStoreSelector compareValues(@Nullable BiPredicate<String, String> compareValues) {
        this.setCompareValues(compareValues);
        return this;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean accept(Message<?> message) {
        String key = this.keyStrategy.processMessage(message);
        Long timestamp = message.getHeaders().getTimestamp();
        String value = this.valueStrategy != null ? this.valueStrategy.processMessage(message) : (timestamp == null ? "0" : Long.toString(timestamp));
        BiPredicate<String, String> predicate = this.compareValues;
        if (predicate == null) {
            return this.metadataStore.putIfAbsent(key, value) == null;
        }
        MetadataStoreSelector metadataStoreSelector = this;
        synchronized (metadataStoreSelector) {
            String oldValue = this.metadataStore.get(key);
            if (oldValue == null) {
                return this.metadataStore.putIfAbsent(key, value) == null;
            }
            if (predicate.test(oldValue, value)) {
                return this.metadataStore.replace(key, oldValue, value);
            }
            return false;
        }
    }
}

