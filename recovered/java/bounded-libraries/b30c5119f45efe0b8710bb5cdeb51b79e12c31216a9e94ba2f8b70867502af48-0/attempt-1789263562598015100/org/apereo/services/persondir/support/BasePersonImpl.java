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
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.services.persondir.support;

import java.sql.Array;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apereo.services.persondir.IPersonAttributes;
import org.apereo.services.persondir.util.CollectionsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BasePersonImpl
implements IPersonAttributes {
    private static final long serialVersionUID = 1L;
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Map<String, List<Object>> attributes;

    public BasePersonImpl(Map<String, List<Object>> attributes) {
        Validate.notNull(attributes, (String)"attributes can not be null", (Object[])new Object[0]);
        Map<String, List<Object>> immutableValuesBuilder = this.buildImmutableAttributeMap(attributes);
        this.attributes = CollectionsUtil.safelyWrapAsUnmodifiableMap(immutableValuesBuilder);
    }

    protected Map<String, List<Object>> buildImmutableAttributeMap(Map<String, List<Object>> attributes) {
        Map<String, List<Object>> immutableValuesBuilder = this.createImmutableAttributeMap(attributes.size());
        Pattern arrayPattern = Pattern.compile("\\{(.*)\\}");
        for (Map.Entry<String, List<Object>> attrEntry : attributes.entrySet()) {
            Object result;
            String key = attrEntry.getKey();
            List<Object> value = attrEntry.getValue();
            if (value != null && !value.isEmpty() && (result = value.get(0)) instanceof Array) {
                Matcher matcher;
                if (this.logger.isTraceEnabled()) {
                    this.logger.trace("Column {} is classified as a SQL array", (Object)key);
                }
                String values = result.toString();
                if (this.logger.isTraceEnabled()) {
                    this.logger.trace("Converting SQL array values {} using pattern {}", (Object)values, (Object)arrayPattern.pattern());
                }
                if ((matcher = arrayPattern.matcher(values)).matches()) {
                    String[] groups = matcher.group(1).split(",");
                    value = Arrays.asList(groups);
                    if (this.logger.isTraceEnabled()) {
                        this.logger.trace("Converted SQL array values {}", (Object)values);
                    }
                }
            }
            if (this.logger.isTraceEnabled()) {
                this.logger.trace("Collecting attribute {} with value(s) {}", (Object)key, value);
            }
            immutableValuesBuilder.put(key, value);
        }
        return immutableValuesBuilder;
    }

    protected Map<String, List<Object>> createImmutableAttributeMap(int size) {
        return new LinkedHashMap<String, List<Object>>(size > 0 ? size : 1);
    }

    public Object getAttributeValue(String name) {
        List<Object> values = this.attributes.get(name);
        if (values == null || values.size() == 0) {
            return null;
        }
        return values.get(0);
    }

    public List<Object> getAttributeValues(String name) {
        return this.attributes.get(name);
    }

    public Map<String, List<Object>> getAttributes() {
        return this.attributes;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof IPersonAttributes)) {
            return false;
        }
        IPersonAttributes rhs = (IPersonAttributes)object;
        return new EqualsBuilder().append((Object)this.getName(), (Object)rhs.getName()).isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder(1574945487, 827742191).append((Object)this.getName()).toHashCode();
    }

    public String toString() {
        return new ToStringBuilder((Object)this, ToStringStyle.SHORT_PREFIX_STYLE).append("name", (Object)this.getName()).append("attributes", this.attributes).toString();
    }
}

