/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.authentication;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import lombok.Generated;

public class AttributeMappingRequest {
    private final String attributeName;
    private final String mappedAttributeName;
    private final Map<String, List<Object>> resolvedAttributes;
    private final List<Object> attributeValue;

    @Generated
    private static Map<String, List<Object>> $default$resolvedAttributes() {
        return new TreeMap<String, List<Object>>();
    }

    @Generated
    private static List<Object> $default$attributeValue() {
        return new ArrayList<Object>();
    }

    @Generated
    protected AttributeMappingRequest(AttributeMappingRequestBuilder<?, ?> b) {
        this.attributeName = b.attributeName;
        this.mappedAttributeName = b.mappedAttributeName;
        this.resolvedAttributes = b.resolvedAttributes$set ? b.resolvedAttributes$value : AttributeMappingRequest.$default$resolvedAttributes();
        this.attributeValue = b.attributeValue$set ? b.attributeValue$value : AttributeMappingRequest.$default$attributeValue();
    }

    @Generated
    public static AttributeMappingRequestBuilder<?, ?> builder() {
        return new AttributeMappingRequestBuilderImpl();
    }

    @Generated
    public String getAttributeName() {
        return this.attributeName;
    }

    @Generated
    public String getMappedAttributeName() {
        return this.mappedAttributeName;
    }

    @Generated
    public Map<String, List<Object>> getResolvedAttributes() {
        return this.resolvedAttributes;
    }

    @Generated
    public List<Object> getAttributeValue() {
        return this.attributeValue;
    }

    @Generated
    private static final class AttributeMappingRequestBuilderImpl
    extends AttributeMappingRequestBuilder<AttributeMappingRequest, AttributeMappingRequestBuilderImpl> {
        @Generated
        private AttributeMappingRequestBuilderImpl() {
        }

        @Override
        @Generated
        protected AttributeMappingRequestBuilderImpl self() {
            return this;
        }

        @Override
        @Generated
        public AttributeMappingRequest build() {
            return new AttributeMappingRequest(this);
        }
    }

    @Generated
    public static abstract class AttributeMappingRequestBuilder<C extends AttributeMappingRequest, B extends AttributeMappingRequestBuilder<C, B>> {
        @Generated
        private String attributeName;
        @Generated
        private String mappedAttributeName;
        @Generated
        private boolean resolvedAttributes$set;
        @Generated
        private Map<String, List<Object>> resolvedAttributes$value;
        @Generated
        private boolean attributeValue$set;
        @Generated
        private List<Object> attributeValue$value;

        @Generated
        protected abstract B self();

        @Generated
        public abstract C build();

        @Generated
        public B attributeName(String attributeName) {
            this.attributeName = attributeName;
            return this.self();
        }

        @Generated
        public B mappedAttributeName(String mappedAttributeName) {
            this.mappedAttributeName = mappedAttributeName;
            return this.self();
        }

        @Generated
        public B resolvedAttributes(Map<String, List<Object>> resolvedAttributes) {
            this.resolvedAttributes$value = resolvedAttributes;
            this.resolvedAttributes$set = true;
            return this.self();
        }

        @Generated
        public B attributeValue(List<Object> attributeValue) {
            this.attributeValue$value = attributeValue;
            this.attributeValue$set = true;
            return this.self();
        }

        @Generated
        public String toString() {
            return "AttributeMappingRequest.AttributeMappingRequestBuilder(attributeName=" + this.attributeName + ", mappedAttributeName=" + this.mappedAttributeName + ", resolvedAttributes$value=" + this.resolvedAttributes$value + ", attributeValue$value=" + this.attributeValue$value + ")";
        }
    }
}

