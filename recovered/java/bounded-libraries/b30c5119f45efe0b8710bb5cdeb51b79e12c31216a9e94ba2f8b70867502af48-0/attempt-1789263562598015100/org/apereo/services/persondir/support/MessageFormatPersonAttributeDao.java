/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  org.apache.commons.lang3.Validate
 *  org.apache.commons.lang3.builder.EqualsBuilder
 *  org.apache.commons.lang3.builder.HashCodeBuilder
 *  org.apache.commons.lang3.builder.ToStringBuilder
 *  org.apache.commons.lang3.builder.ToStringStyle
 *  org.apereo.services.persondir.IPersonAttributeDaoFilter
 *  org.apereo.services.persondir.IPersonAttributes
 */
package org.apereo.services.persondir.support;

import com.google.common.collect.ImmutableSet;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apereo.services.persondir.IPersonAttributeDaoFilter;
import org.apereo.services.persondir.IPersonAttributes;
import org.apereo.services.persondir.support.AbstractDefaultAttributePersonAttributeDao;
import org.apereo.services.persondir.support.AttributeNamedPersonImpl;
import org.apereo.services.persondir.support.BasePersonImpl;
import org.apereo.services.persondir.support.IUsernameAttributeProvider;
import org.apereo.services.persondir.support.NamedPersonImpl;

@Deprecated
public class MessageFormatPersonAttributeDao
extends AbstractDefaultAttributePersonAttributeDao {
    private Set<FormatAttribute> formatAttributes;
    private Set<String> possibleUserAttributeNames;

    public Set<FormatAttribute> getFormatAttributes() {
        return this.formatAttributes;
    }

    public void setFormatAttributes(Set<FormatAttribute> formatAttributes) {
        Validate.notNull(formatAttributes, (String)"formatAttributes can not be null", (Object[])new Object[0]);
        LinkedHashSet<String> possibleUserAttributeNames = new LinkedHashSet<String>();
        for (FormatAttribute formatAttribute : formatAttributes) {
            possibleUserAttributeNames.addAll(formatAttribute.attributeNames);
        }
        this.possibleUserAttributeNames = possibleUserAttributeNames;
        this.formatAttributes = formatAttributes;
    }

    public Set<String> getAvailableQueryAttributes(IPersonAttributeDaoFilter filter) {
        return null;
    }

    public Set<IPersonAttributes> getPeopleWithMultivaluedAttributes(Map<String, List<Object>> query, IPersonAttributeDaoFilter filter) {
        BasePersonImpl personAttributes;
        LinkedHashMap<String, List<Object>> formattedAttributes = new LinkedHashMap<String, List<Object>>();
        for (FormatAttribute formatAttribute : this.formatAttributes) {
            String format = formatAttribute.getFormat();
            List<String> sourceAttributes = formatAttribute.getSourceAttributes();
            if (!query.keySet().containsAll(sourceAttributes)) {
                this.logger.debug("Query does not contain all source attributes, skipping FormatAttribute " + formatAttribute);
                continue;
            }
            ArrayList<Object> sourceValues = new ArrayList<Object>(sourceAttributes.size());
            for (String sourceAttribute : sourceAttributes) {
                List<Object> values = query.get(sourceAttribute);
                sourceValues.add(values == null || values.size() == 0 ? null : values.get(0));
            }
            String formattedAttribute = MessageFormat.format(format, sourceValues.toArray());
            for (String attributeName : formatAttribute.getAttributeNames()) {
                formattedAttributes.put(attributeName, Collections.singletonList(formattedAttribute));
            }
        }
        if (formattedAttributes.size() == 0) {
            return null;
        }
        IUsernameAttributeProvider usernameAttributeProvider = this.getUsernameAttributeProvider();
        String usernameFromQuery = usernameAttributeProvider.getUsernameFromQuery(query);
        if (usernameFromQuery != null) {
            personAttributes = new NamedPersonImpl(usernameFromQuery, formattedAttributes);
        } else {
            String usernameAttribute = usernameAttributeProvider.getUsernameAttribute();
            personAttributes = new AttributeNamedPersonImpl(usernameAttribute, formattedAttributes);
        }
        return ImmutableSet.of((Object)personAttributes);
    }

    public Set<String> getPossibleUserAttributeNames(IPersonAttributeDaoFilter filter) {
        return new HashSet<String>(this.possibleUserAttributeNames);
    }

    public static class FormatAttribute {
        private Set<String> attributeNames;
        private String format;
        private List<String> sourceAttributes;

        public FormatAttribute() {
        }

        public FormatAttribute(Set<String> attributeNames, String format, List<String> sourceAttributes) {
            this.attributeNames = attributeNames;
            this.format = format;
            this.sourceAttributes = sourceAttributes;
        }

        public Set<String> getAttributeNames() {
            return this.attributeNames;
        }

        public void setAttributeNames(Set<String> attributeNames) {
            this.attributeNames = attributeNames;
        }

        public String getFormat() {
            return this.format;
        }

        public void setFormat(String format) {
            this.format = format;
        }

        public List<String> getSourceAttributes() {
            return this.sourceAttributes;
        }

        public void setSourceAttributes(List<String> sourceAttributes) {
            this.sourceAttributes = sourceAttributes;
        }

        public boolean equals(Object object) {
            if (object == this) {
                return true;
            }
            if (!(object instanceof FormatAttribute)) {
                return false;
            }
            FormatAttribute rhs = (FormatAttribute)object;
            return new EqualsBuilder().append(this.attributeNames, rhs.attributeNames).append((Object)this.format, (Object)rhs.format).append(this.sourceAttributes, rhs.sourceAttributes).isEquals();
        }

        public int hashCode() {
            return new HashCodeBuilder(-1421658045, 156936851).append(this.attributeNames).append((Object)this.format).append(this.sourceAttributes).toHashCode();
        }

        public String toString() {
            return new ToStringBuilder((Object)this, ToStringStyle.SHORT_PREFIX_STYLE).append("attributeNames", this.attributeNames).append("format", (Object)this.format).append("sourceAttributes", this.sourceAttributes).toString();
        }
    }
}

