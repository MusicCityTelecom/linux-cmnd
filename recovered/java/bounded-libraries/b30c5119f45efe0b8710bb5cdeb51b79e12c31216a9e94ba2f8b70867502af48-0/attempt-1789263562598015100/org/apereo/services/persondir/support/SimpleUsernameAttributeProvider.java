/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.commons.lang3.Validate
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.services.persondir.support;

import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apereo.services.persondir.support.IUsernameAttributeProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleUsernameAttributeProvider
implements IUsernameAttributeProvider {
    private static final Logger logger = LoggerFactory.getLogger(SimpleUsernameAttributeProvider.class);
    private static final String DEFAULT_USERNAME_ATTRIBUTE = "username";
    private String usernameAttribute = "username";

    public SimpleUsernameAttributeProvider() {
    }

    public SimpleUsernameAttributeProvider(String usernameAttribute) {
        this.setUsernameAttribute(usernameAttribute);
    }

    public void setUsernameAttribute(String usernameAttribute) {
        Validate.notNull((Object)usernameAttribute);
        this.usernameAttribute = usernameAttribute;
    }

    @Override
    public String getUsernameAttribute() {
        return this.usernameAttribute;
    }

    @Override
    public String getUsernameFromQuery(Map<String, List<Object>> query) {
        List<Object> usernameAttributeValues = this.getUsernameAttributeValues(query);
        logger.debug("Username attribute value found from the query map is {}", usernameAttributeValues);
        if (usernameAttributeValues == null || usernameAttributeValues.size() == 0) {
            return null;
        }
        Object firstValue = usernameAttributeValues.get(0);
        if (firstValue == null) {
            return null;
        }
        String username = StringUtils.trimToNull((String)String.valueOf(firstValue));
        if (username == null || username.contains("*")) {
            return null;
        }
        return username;
    }

    private List<Object> getUsernameAttributeValues(Map<String, List<Object>> query) {
        if (query.containsKey(this.usernameAttribute)) {
            List<Object> usernameAttributeValues = query.get(this.usernameAttribute);
            logger.debug("Using {} attribute to get username from the query map", (Object)this.usernameAttribute);
            return usernameAttributeValues;
        }
        List<Object> usernameAttributeValues = query.get(DEFAULT_USERNAME_ATTRIBUTE);
        logger.debug("Using {} attribute to get username from the query map", (Object)DEFAULT_USERNAME_ATTRIBUTE);
        return usernameAttributeValues;
    }
}

