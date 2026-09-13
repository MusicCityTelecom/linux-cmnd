/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.Validate
 *  org.apache.commons.lang3.builder.EqualsBuilder
 *  org.apache.commons.lang3.builder.HashCodeBuilder
 *  org.apache.commons.lang3.builder.ToStringBuilder
 *  org.apache.commons.lang3.builder.ToStringStyle
 *  org.apereo.services.persondir.IPersonAttributes
 */
package org.apereo.services.persondir.support;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apereo.services.persondir.IPersonAttributes;
import org.apereo.services.persondir.support.IAdditionalDescriptors;
import org.apereo.services.persondir.util.CollectionsUtil;

public class AdditionalDescriptors
implements IAdditionalDescriptors {
    private static final long serialVersionUID = 1L;
    private String name = null;
    private Map<String, List<Object>> attributes = new ConcurrentHashMap<String, List<Object>>();

    public Object getAttributeValue(String name) {
        List<Object> values = this.attributes.get(name);
        return values == null || values.size() == 0 ? null : values.get(0);
    }

    public List<Object> getAttributeValues(String name) {
        List<Object> values = this.attributes.get(name);
        if (values == null) {
            return null;
        }
        return CollectionsUtil.safelyWrapAsUnmodifiableList(values);
    }

    public Map<String, List<Object>> getAttributes() {
        return CollectionsUtil.safelyWrapAsUnmodifiableMap(this.attributes);
    }

    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void addAttributes(Map<String, List<Object>> attributes) {
        for (Map.Entry<String, List<Object>> newAttribute : attributes.entrySet()) {
            String name = newAttribute.getKey();
            List<Object> values = newAttribute.getValue();
            if (values == null) {
                this.attributes.put(name, null);
                continue;
            }
            this.attributes.put(name, new ArrayList<Object>(values));
        }
    }

    @Override
    public void setAttributes(Map<String, List<Object>> attributes) {
        Validate.notNull(attributes, (String)"Argument 'attributes' cannot be null", (Object[])new Object[0]);
        ConcurrentHashMap<String, List<Object>> newAttributes = new ConcurrentHashMap<String, List<Object>>();
        for (Map.Entry<String, List<Object>> newAttribute : attributes.entrySet()) {
            String name = newAttribute.getKey();
            List<Object> values = newAttribute.getValue();
            if (values == null) {
                newAttributes.put(name, null);
                continue;
            }
            newAttributes.put(name, new ArrayList<Object>(values));
        }
        this.attributes = newAttributes;
    }

    @Override
    public List<Object> setAttributeValues(String name, List<Object> values) {
        if (name == null) {
            String msg = "Argument 'name' cannot be null.";
            throw new IllegalArgumentException(msg);
        }
        if (values == null) {
            return this.attributes.put(name, null);
        }
        return this.attributes.put(name, new ArrayList<Object>(values));
    }

    @Override
    public List<Object> removeAttribute(String name) {
        Validate.notNull((Object)name, (String)"Argument 'name' cannot be null", (Object[])new Object[0]);
        return this.attributes.remove(name);
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof IPersonAttributes)) {
            return false;
        }
        IPersonAttributes rhs = (IPersonAttributes)object;
        return new EqualsBuilder().append((Object)this.name, (Object)rhs.getName()).isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder(1574945487, 827742191).append((Object)this.name).toHashCode();
    }

    public String toString() {
        return new ToStringBuilder((Object)this, ToStringStyle.SHORT_PREFIX_STYLE).append("name", (Object)this.name).append("attributes", this.attributes).toString();
    }
}

