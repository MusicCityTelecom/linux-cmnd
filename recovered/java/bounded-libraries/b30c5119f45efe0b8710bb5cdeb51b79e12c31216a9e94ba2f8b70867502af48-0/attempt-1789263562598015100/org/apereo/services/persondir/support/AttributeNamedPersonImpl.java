/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.services.persondir.IPersonAttributes
 */
package org.apereo.services.persondir.support;

import java.util.List;
import java.util.Map;
import org.apereo.services.persondir.IPersonAttributes;
import org.apereo.services.persondir.support.BasePersonImpl;

public class AttributeNamedPersonImpl
extends BasePersonImpl {
    private static final long serialVersionUID = 1L;
    public static final String DEFAULT_USER_NAME_ATTRIBUTE = "username";
    private final String userNameAttribute;

    public AttributeNamedPersonImpl(Map<String, List<Object>> attributes) {
        super(attributes);
        this.userNameAttribute = DEFAULT_USER_NAME_ATTRIBUTE;
    }

    public AttributeNamedPersonImpl(String userNameAttribute, Map<String, List<Object>> attributes) {
        super(attributes);
        this.userNameAttribute = userNameAttribute;
    }

    public AttributeNamedPersonImpl(IPersonAttributes personAttributes) {
        this(personAttributes.getName(), personAttributes.getAttributes());
    }

    public String getName() {
        Object attributeValue = this.getAttributeValue(this.userNameAttribute);
        if (attributeValue == null) {
            return null;
        }
        return attributeValue.toString();
    }
}

