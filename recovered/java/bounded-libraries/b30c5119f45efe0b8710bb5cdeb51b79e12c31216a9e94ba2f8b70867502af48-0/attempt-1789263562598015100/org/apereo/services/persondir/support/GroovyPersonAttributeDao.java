/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.services.persondir.IPersonAttributeDaoFilter
 *  org.apereo.services.persondir.IPersonAttributeScriptDao
 *  org.apereo.services.persondir.IPersonAttributes
 */
package org.apereo.services.persondir.support;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apereo.services.persondir.IPersonAttributeDaoFilter;
import org.apereo.services.persondir.IPersonAttributeScriptDao;
import org.apereo.services.persondir.IPersonAttributes;
import org.apereo.services.persondir.support.AbstractDefaultAttributePersonAttributeDao;
import org.apereo.services.persondir.support.AttributeNamedPersonImpl;
import org.apereo.services.persondir.support.CaseInsensitiveNamedPersonImpl;
import org.apereo.services.persondir.support.MultivaluedPersonAttributeUtils;
import org.apereo.services.persondir.support.NamedPersonImpl;

public class GroovyPersonAttributeDao
extends AbstractDefaultAttributePersonAttributeDao {
    private final IPersonAttributeScriptDao groovyObject;
    private Set<String> possibleUserAttributeNames = null;
    private Set<String> availableQueryAttributes = null;
    private boolean caseInsensitiveUsername = false;

    public GroovyPersonAttributeDao(IPersonAttributeScriptDao groovyObject) {
        this.groovyObject = groovyObject;
    }

    public void setCaseInsensitiveUsername(boolean caseInsensitiveUsername) {
        this.caseInsensitiveUsername = caseInsensitiveUsername;
    }

    @Override
    public IPersonAttributes getPerson(String uid, IPersonAttributeDaoFilter filter) {
        if (!this.isEnabled()) {
            return null;
        }
        this.logger.debug("Executing groovy script's getAttributesForUser method");
        Map personAttributesMap = this.groovyObject.getAttributesForUser(uid);
        if (personAttributesMap != null) {
            this.logger.debug("Creating person attributes with the username {} and attributes {}", (Object)uid, (Object)personAttributesMap);
            Map<String, List<Object>> personAttributes = MultivaluedPersonAttributeUtils.toMultivaluedMap(personAttributesMap);
            if (this.caseInsensitiveUsername) {
                return new CaseInsensitiveNamedPersonImpl(uid, personAttributes);
            }
            return new NamedPersonImpl(uid, personAttributes);
        }
        this.logger.debug("Groovy script returned null for uid={}", (Object)uid);
        return null;
    }

    public Set<IPersonAttributes> getPeopleWithMultivaluedAttributes(Map<String, List<Object>> attributes, IPersonAttributeDaoFilter filter) {
        this.logger.debug("Executing groovy script's getPersonAttributesFromMultivaluedAttributes method, with parameters {}", attributes);
        Map personAttributesMap = this.groovyObject.getPersonAttributesFromMultivaluedAttributes(attributes);
        if (personAttributesMap != null) {
            this.logger.debug("Creating person attributes: {}", (Object)personAttributesMap);
            return Collections.singleton(new AttributeNamedPersonImpl(personAttributesMap));
        }
        return null;
    }

    public void setPossibleUserAttributeNames(Set<String> possibleUserAttributeNames) {
        this.possibleUserAttributeNames = possibleUserAttributeNames;
    }

    public void setAvailableQueryAttributes(Set<String> availableQueryAttributes) {
        this.availableQueryAttributes = availableQueryAttributes;
    }

    public Set<String> getAvailableQueryAttributes(IPersonAttributeDaoFilter filter) {
        return this.availableQueryAttributes;
    }

    public Set<String> getPossibleUserAttributeNames(IPersonAttributeDaoFilter filter) {
        return this.possibleUserAttributeNames;
    }
}

