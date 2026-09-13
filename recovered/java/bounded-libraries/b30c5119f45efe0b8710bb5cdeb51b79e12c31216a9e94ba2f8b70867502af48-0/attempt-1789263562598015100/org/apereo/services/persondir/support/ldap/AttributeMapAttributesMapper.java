/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.ldap.core.AttributesMapper
 */
package org.apereo.services.persondir.support.ldap;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import org.springframework.ldap.core.AttributesMapper;

class AttributeMapAttributesMapper
implements AttributesMapper {
    private final boolean ignoreNull;

    public AttributeMapAttributesMapper() {
        this(false);
    }

    public AttributeMapAttributesMapper(boolean ignoreNull) {
        this.ignoreNull = ignoreNull;
    }

    public Object mapFromAttributes(Attributes attributes) throws NamingException {
        int attributeCount = attributes.size();
        Map<String, Object> mapOfAttrValues = this.createAttributeMap(attributeCount);
        NamingEnumeration<? extends Attribute> attributesEnum = attributes.getAll();
        while (attributesEnum.hasMore()) {
            Attribute attribute = attributesEnum.next();
            if (this.ignoreNull && attribute.size() <= 0) continue;
            String attrName = attribute.getID();
            String key = this.getAttributeKey(attrName);
            NamingEnumeration<?> valuesEnum = attribute.getAll();
            List<?> values = this.getAttributeValues(valuesEnum);
            mapOfAttrValues.put(key, values);
        }
        return mapOfAttrValues;
    }

    protected Map<String, Object> createAttributeMap(int attributeCount) {
        return new TreeMap<String, Object>(String.CASE_INSENSITIVE_ORDER);
    }

    protected String getAttributeKey(String attributeName) {
        return attributeName;
    }

    protected List<?> getAttributeValues(NamingEnumeration<?> values) {
        return Collections.list(values);
    }
}

