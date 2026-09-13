/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.Validate
 *  org.apereo.services.persondir.IPersonAttributeDaoFilter
 *  org.apereo.services.persondir.IPersonAttributes
 */
package org.apereo.services.persondir.support;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.Validate;
import org.apereo.services.persondir.IPersonAttributeDaoFilter;
import org.apereo.services.persondir.IPersonAttributes;
import org.apereo.services.persondir.support.AbstractFlatteningPersonAttributeDao;
import org.apereo.services.persondir.support.IUsernameAttributeProvider;
import org.apereo.services.persondir.support.NamedPersonImpl;
import org.apereo.services.persondir.support.SimpleUsernameAttributeProvider;

public abstract class AbstractDefaultAttributePersonAttributeDao
extends AbstractFlatteningPersonAttributeDao {
    private IUsernameAttributeProvider usernameAttributeProvider = new SimpleUsernameAttributeProvider();

    public IPersonAttributes getPerson(String uid, IPersonAttributeDaoFilter filter) {
        if (!this.isEnabled()) {
            return null;
        }
        Validate.notNull((Object)uid, (String)"uid may not be null.", (Object[])new Object[0]);
        Map<String, List<Object>> seed = this.toSeedMap(uid);
        Set people = this.getPeopleWithMultivaluedAttributes(seed, filter);
        IPersonAttributes person = this.getSinglePerson(people);
        if (person == null) {
            return null;
        }
        if (person.getName() == null) {
            person = new NamedPersonImpl(uid, person.getAttributes());
        }
        return person;
    }

    protected Map<String, List<Object>> toSeedMap(String uid) {
        List<String> values = Collections.singletonList(uid);
        String usernameAttribute = this.usernameAttributeProvider.getUsernameAttribute();
        Map<String, List<Object>> seed = Collections.singletonMap(usernameAttribute, values);
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("Created seed map='" + seed + "' for uid='" + uid + "'");
        }
        return seed;
    }

    public IUsernameAttributeProvider getUsernameAttributeProvider() {
        return this.usernameAttributeProvider;
    }

    public void setUsernameAttributeProvider(IUsernameAttributeProvider usernameAttributeProvider) {
        Validate.notNull((Object)usernameAttributeProvider);
        this.usernameAttributeProvider = usernameAttributeProvider;
    }
}

