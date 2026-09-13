/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  org.apereo.services.persondir.IPersonAttributes
 */
package org.apereo.services.persondir.support.rule;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apereo.services.persondir.IPersonAttributes;
import org.apereo.services.persondir.support.NamedPersonImpl;
import org.apereo.services.persondir.support.rule.AttributeRule;

public final class SimpleAttributeRule
implements AttributeRule {
    private final String whenKey;
    private final String whenPattern;
    private final String setUserName;
    private final String setKey;
    private final String setValue;
    private final Set<String> possibleAttributeNames;

    public SimpleAttributeRule() {
        this.whenKey = null;
        this.whenPattern = null;
        this.setUserName = null;
        this.setKey = null;
        this.setValue = null;
        this.possibleAttributeNames = new HashSet<String>();
    }

    public SimpleAttributeRule(String whenKey, String whenPattern, String setUserName, String setKey, String setValue) {
        if (whenKey == null) {
            String msg = "Argument 'whenKey' cannot be null.";
            throw new IllegalArgumentException(msg);
        }
        if (whenPattern == null) {
            String msg = "Argument 'whenPattern' cannot be null.";
            throw new IllegalArgumentException(msg);
        }
        if (setKey == null) {
            String msg = "Argument 'setKey' cannot be null.";
            throw new IllegalArgumentException(msg);
        }
        if (setUserName == null) {
            String msg = "Argument 'setUserName' cannot be null.";
            throw new IllegalArgumentException(msg);
        }
        if (setValue == null) {
            String msg = "Argument 'setValue' cannot be null.";
            throw new IllegalArgumentException(msg);
        }
        this.whenKey = whenKey;
        this.whenPattern = whenPattern;
        this.setUserName = setUserName;
        this.setKey = setKey;
        this.setValue = setValue;
        HashSet<String> list = new HashSet<String>();
        list.add(this.setKey);
        this.possibleAttributeNames = list;
    }

    @Override
    public boolean appliesTo(Map<String, List<Object>> userInfo) {
        if (userInfo == null) {
            String msg = "Argument 'userInfo' cannot be null.";
            throw new IllegalArgumentException(msg);
        }
        List<Object> value = userInfo.get(this.whenKey);
        if (value == null) {
            return false;
        }
        String[] compare = null;
        try {
            compare = value.toArray(new String[value.size()]);
        }
        catch (ClassCastException cce) {
            String msg = "List values may contain only String instances.";
            throw new RuntimeException(msg, cce);
        }
        boolean rslt = false;
        for (int i = 0; i < compare.length; ++i) {
            if (!compare[i].matches(this.whenPattern)) continue;
            rslt = true;
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
        LinkedHashMap<String, List<Object>> rslt = new LinkedHashMap<String, List<Object>>();
        ArrayList<String> value = new ArrayList<String>(1);
        value.add(this.setValue);
        rslt.put(this.setKey, value);
        NamedPersonImpl person = new NamedPersonImpl(this.setUserName, rslt);
        return Collections.singleton(person);
    }

    @Override
    @JsonIgnore
    public Set<String> getPossibleUserAttributeNames() {
        return this.possibleAttributeNames;
    }

    @Override
    @JsonIgnore
    public Set<String> getAvailableQueryAttributes() {
        HashSet<String> list = new HashSet<String>();
        list.add(this.whenKey);
        return list;
    }
}

