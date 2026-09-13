/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.services.persondir.IPersonAttributes
 *  org.springframework.beans.factory.BeanInitializationException
 *  org.springframework.beans.factory.InitializingBean
 */
package org.apereo.services.persondir.support.rule;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.apereo.services.persondir.IPersonAttributes;
import org.apereo.services.persondir.support.IUsernameAttributeProvider;
import org.apereo.services.persondir.support.NamedPersonImpl;
import org.apereo.services.persondir.support.rule.AttributeRule;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.InitializingBean;

public final class StringFormatAttributeRule
implements AttributeRule,
InitializingBean {
    private String formatString;
    private List<String> formatArguments;
    private String outputAttribute;
    private IUsernameAttributeProvider usernameAttributeProvider;

    public void setFormatString(String formatString) {
        this.formatString = formatString;
    }

    public void setFormatArguments(List<String> formatArguments) {
        this.formatArguments = formatArguments;
    }

    public void setOutputAttribute(String outputAttribute) {
        this.outputAttribute = outputAttribute;
    }

    public void setUsernameAttributeProvider(IUsernameAttributeProvider usernameAttributeProvider) {
        this.usernameAttributeProvider = usernameAttributeProvider;
    }

    @Override
    public boolean appliesTo(Map<String, List<Object>> userInfo) {
        if (userInfo == null) {
            String msg = "Argument 'userInfo' cannot be null.";
            throw new IllegalArgumentException(msg);
        }
        boolean rslt = true;
        for (String attributeName : this.formatArguments) {
            if (userInfo.containsKey(attributeName)) continue;
            rslt = false;
            break;
        }
        return rslt;
    }

    @Override
    public Set<IPersonAttributes> evaluate(Map<String, List<Object>> userInfo) {
        if (userInfo == null) {
            String msg = "Argument 'userInfo' cannot be null.";
            throw new IllegalArgumentException(msg);
        }
        if (!this.appliesTo(userInfo)) {
            String msg = "May not evaluate.  This rule does not apply.";
            throw new IllegalArgumentException(msg);
        }
        Object[] args = new Object[this.formatArguments.size()];
        for (int i = 0; i < this.formatArguments.size(); ++i) {
            String key = this.formatArguments.get(i);
            List<Object> values = userInfo.get(key);
            args[i] = values.isEmpty() ? null : values.get(0);
        }
        String outputAttributeValue = String.format(this.formatString, args);
        HashMap<String, List<Object>> rslt = new HashMap<String, List<Object>>();
        rslt.put(this.outputAttribute, Arrays.asList(outputAttributeValue));
        String username = this.usernameAttributeProvider.getUsernameFromQuery(userInfo);
        NamedPersonImpl person = new NamedPersonImpl(username, rslt);
        return Collections.singleton(person);
    }

    @Override
    public Set<String> getAvailableQueryAttributes() {
        return new HashSet<String>(this.formatArguments);
    }

    @Override
    public Set<String> getPossibleUserAttributeNames() {
        return Collections.singleton(this.outputAttribute);
    }

    public void afterPropertiesSet() {
        if (StringUtils.isEmpty((CharSequence)this.formatString)) {
            throw new BeanInitializationException("formatString is required");
        }
        if (this.usernameAttributeProvider == null) {
            throw new BeanInitializationException("usernameAttributeProvider is required");
        }
        if (this.outputAttribute == null) {
            throw new BeanInitializationException("outputAttribute is required");
        }
    }
}

