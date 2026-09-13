/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  lombok.Generated
 */
package org.apereo.cas.util.cache;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;
import lombok.Generated;
import org.apereo.cas.util.PublisherIdentifier;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public class DistributedCacheObject<V extends Serializable>
implements Serializable {
    private static final long serialVersionUID = -6776499291439952013L;
    private Map<String, String> properties;
    private long timestamp;
    private V value;
    private PublisherIdentifier publisherIdentifier;

    public <T> T getProperty(String name, Class<T> clazz) {
        if (this.containsProperty(name)) {
            String item = this.properties.get(name);
            if (item == null) {
                return null;
            }
            if (!clazz.isAssignableFrom(item.getClass())) {
                throw new ClassCastException("Object [" + item + " is of type " + item.getClass() + " when we were expecting " + clazz);
            }
            return (T)item;
        }
        return null;
    }

    public boolean containsProperty(String name) {
        return this.properties.containsKey(name);
    }

    @Generated
    private static <V extends Serializable> Map<String, String> $default$properties() {
        return new TreeMap<String, String>();
    }

    @Generated
    private static <V extends Serializable> long $default$timestamp() {
        return System.currentTimeMillis();
    }

    @Generated
    protected DistributedCacheObject(DistributedCacheObjectBuilder<V, ?, ?> b) {
        this.properties = b.properties$set ? b.properties$value : DistributedCacheObject.$default$properties();
        this.timestamp = b.timestamp$set ? b.timestamp$value : DistributedCacheObject.$default$timestamp();
        this.value = b.value;
        this.publisherIdentifier = b.publisherIdentifier;
    }

    @Generated
    public static <V extends Serializable> DistributedCacheObjectBuilder<V, ?, ?> builder() {
        return new DistributedCacheObjectBuilderImpl();
    }

    @Generated
    public String toString() {
        return "DistributedCacheObject(properties=" + this.properties + ", timestamp=" + this.timestamp + ", value=" + this.value + ", publisherIdentifier=" + this.publisherIdentifier + ")";
    }

    @Generated
    public Map<String, String> getProperties() {
        return this.properties;
    }

    @Generated
    public long getTimestamp() {
        return this.timestamp;
    }

    @Generated
    public V getValue() {
        return this.value;
    }

    @Generated
    public PublisherIdentifier getPublisherIdentifier() {
        return this.publisherIdentifier;
    }

    @Generated
    public DistributedCacheObject() {
        this.properties = DistributedCacheObject.$default$properties();
        this.timestamp = DistributedCacheObject.$default$timestamp();
    }

    @Generated
    public DistributedCacheObject(Map<String, String> properties, long timestamp, V value, PublisherIdentifier publisherIdentifier) {
        this.properties = properties;
        this.timestamp = timestamp;
        this.value = value;
        this.publisherIdentifier = publisherIdentifier;
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DistributedCacheObject)) {
            return false;
        }
        DistributedCacheObject other = (DistributedCacheObject)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.timestamp != other.timestamp) {
            return false;
        }
        Map<String, String> this$properties = this.properties;
        Map<String, String> other$properties = other.properties;
        if (this$properties == null ? other$properties != null : !((Object)this$properties).equals(other$properties)) {
            return false;
        }
        V this$value = this.value;
        V other$value = other.value;
        if (this$value == null ? other$value != null : !this$value.equals(other$value)) {
            return false;
        }
        PublisherIdentifier this$publisherIdentifier = this.publisherIdentifier;
        PublisherIdentifier other$publisherIdentifier = other.publisherIdentifier;
        return !(this$publisherIdentifier == null ? other$publisherIdentifier != null : !((Object)this$publisherIdentifier).equals(other$publisherIdentifier));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof DistributedCacheObject;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        long $timestamp = this.timestamp;
        result = result * 59 + (int)($timestamp >>> 32 ^ $timestamp);
        Map<String, String> $properties = this.properties;
        result = result * 59 + ($properties == null ? 43 : ((Object)$properties).hashCode());
        V $value = this.value;
        result = result * 59 + ($value == null ? 43 : $value.hashCode());
        PublisherIdentifier $publisherIdentifier = this.publisherIdentifier;
        result = result * 59 + ($publisherIdentifier == null ? 43 : ((Object)$publisherIdentifier).hashCode());
        return result;
    }

    @Generated
    private static final class DistributedCacheObjectBuilderImpl<V extends Serializable>
    extends DistributedCacheObjectBuilder<V, DistributedCacheObject<V>, DistributedCacheObjectBuilderImpl<V>> {
        @Generated
        private DistributedCacheObjectBuilderImpl() {
        }

        @Override
        @Generated
        protected DistributedCacheObjectBuilderImpl<V> self() {
            return this;
        }

        @Override
        @Generated
        public DistributedCacheObject<V> build() {
            return new DistributedCacheObject(this);
        }
    }

    @Generated
    public static abstract class DistributedCacheObjectBuilder<V extends Serializable, C extends DistributedCacheObject<V>, B extends DistributedCacheObjectBuilder<V, C, B>> {
        @Generated
        private boolean properties$set;
        @Generated
        private Map<String, String> properties$value;
        @Generated
        private boolean timestamp$set;
        @Generated
        private long timestamp$value;
        @Generated
        private V value;
        @Generated
        private PublisherIdentifier publisherIdentifier;

        @Generated
        protected abstract B self();

        @Generated
        public abstract C build();

        @Generated
        public B properties(Map<String, String> properties) {
            this.properties$value = properties;
            this.properties$set = true;
            return this.self();
        }

        @Generated
        public B timestamp(long timestamp) {
            this.timestamp$value = timestamp;
            this.timestamp$set = true;
            return this.self();
        }

        @Generated
        public B value(V value) {
            this.value = value;
            return this.self();
        }

        @Generated
        public B publisherIdentifier(PublisherIdentifier publisherIdentifier) {
            this.publisherIdentifier = publisherIdentifier;
            return this.self();
        }

        @Generated
        public String toString() {
            return "DistributedCacheObject.DistributedCacheObjectBuilder(properties$value=" + this.properties$value + ", timestamp$value=" + this.timestamp$value + ", value=" + this.value + ", publisherIdentifier=" + this.publisherIdentifier + ")";
        }
    }
}

