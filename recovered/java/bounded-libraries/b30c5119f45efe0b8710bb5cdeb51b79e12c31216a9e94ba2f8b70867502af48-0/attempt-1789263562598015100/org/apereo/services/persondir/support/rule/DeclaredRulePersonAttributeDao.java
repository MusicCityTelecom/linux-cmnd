/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  org.apache.commons.lang3.Validate
 *  org.apereo.services.persondir.IPersonAttributeDaoFilter
 *  org.apereo.services.persondir.IPersonAttributes
 */
package org.apereo.services.persondir.support.rule;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.Validate;
import org.apereo.services.persondir.IPersonAttributeDaoFilter;
import org.apereo.services.persondir.IPersonAttributes;
import org.apereo.services.persondir.support.AbstractDefaultAttributePersonAttributeDao;
import org.apereo.services.persondir.support.SimpleUsernameAttributeProvider;
import org.apereo.services.persondir.support.rule.AttributeRule;
import org.apereo.services.persondir.util.CollectionsUtil;

@Deprecated
public final class DeclaredRulePersonAttributeDao
extends AbstractDefaultAttributePersonAttributeDao {
    private List<AttributeRule> rules;

    public DeclaredRulePersonAttributeDao() {
    }

    public DeclaredRulePersonAttributeDao(String attributeName, List<AttributeRule> rules) {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("Creating DeclaredRulePersonAttributeDao with attributeName='" + attributeName + "' and rules='" + rules + "'");
        }
        SimpleUsernameAttributeProvider usernameAttributeProvider = new SimpleUsernameAttributeProvider(attributeName);
        this.setUsernameAttributeProvider(usernameAttributeProvider);
        this.setRules(rules);
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("Created DeclaredRulePersonAttributeDao with attributeName='" + attributeName + "' and rules='" + rules + "'");
        }
    }

    public List<AttributeRule> getRules() {
        return this.rules;
    }

    public void setRules(List<AttributeRule> rules) {
        Validate.notEmpty(rules, (String)"Argument 'rules' cannot be null or empty.", (Object[])new Object[0]);
        this.rules = CollectionsUtil.safelyWrapAsUnmodifiableList(new ArrayList<AttributeRule>(rules));
    }

    public Set<IPersonAttributes> getPeopleWithMultivaluedAttributes(Map<String, List<Object>> seed, IPersonAttributeDaoFilter filter) {
        Validate.notNull(seed, (String)"Argument 'seed' cannot be null.", (Object[])new Object[0]);
        for (AttributeRule rule : this.rules) {
            if (!rule.appliesTo(seed)) continue;
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("Evaluating rule='" + rule + "' from the rules List");
            }
            return rule.evaluate(seed);
        }
        return null;
    }

    @JsonIgnore
    public Set<String> getPossibleUserAttributeNames(IPersonAttributeDaoFilter filter) {
        LinkedHashSet<String> rslt = new LinkedHashSet<String>();
        for (AttributeRule rule : this.rules) {
            Set<String> possibleUserAttributeNames = rule.getPossibleUserAttributeNames();
            rslt.addAll(possibleUserAttributeNames);
        }
        return rslt;
    }

    @JsonIgnore
    public Set<String> getAvailableQueryAttributes(IPersonAttributeDaoFilter filter) {
        LinkedHashSet<String> rslt = new LinkedHashSet<String>();
        for (AttributeRule rule : this.rules) {
            Set<String> possibleUserAttributeNames = rule.getAvailableQueryAttributes();
            rslt.addAll(possibleUserAttributeNames);
        }
        return rslt;
    }
}

