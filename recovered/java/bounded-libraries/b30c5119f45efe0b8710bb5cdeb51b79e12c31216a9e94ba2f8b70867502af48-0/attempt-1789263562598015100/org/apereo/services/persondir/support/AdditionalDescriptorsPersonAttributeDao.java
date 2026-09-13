/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  org.apereo.services.persondir.IPersonAttributeDaoFilter
 *  org.apereo.services.persondir.IPersonAttributes
 *  org.springframework.beans.factory.annotation.Required
 */
package org.apereo.services.persondir.support;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.apereo.services.persondir.IPersonAttributeDaoFilter;
import org.apereo.services.persondir.IPersonAttributes;
import org.apereo.services.persondir.support.AbstractDefaultAttributePersonAttributeDao;
import org.apereo.services.persondir.support.AbstractQueryPersonAttributeDao;
import org.apereo.services.persondir.support.CaseInsensitiveNamedPersonImpl;
import org.apereo.services.persondir.support.ICurrentUserProvider;
import org.apereo.services.persondir.support.IUsernameAttributeProvider;
import org.apereo.services.persondir.util.CaseCanonicalizationMode;
import org.springframework.beans.factory.annotation.Required;

public class AdditionalDescriptorsPersonAttributeDao
extends AbstractDefaultAttributePersonAttributeDao {
    private IPersonAttributes descriptors;
    private ICurrentUserProvider currentUserProvider;
    private CaseCanonicalizationMode usernameCaseCanonicalizationMode = AbstractQueryPersonAttributeDao.DEFAULT_USERNAME_CASE_CANONICALIZATION_MODE;
    private Locale usernameCaseCanonicalizationLocale = Locale.getDefault();
    private Set<String> possibleUserAttributeNames = null;

    @Required
    public void setDescriptors(IPersonAttributes descriptors) {
        if (descriptors == null) {
            String msg = "Argument 'descriptors' cannot be null";
            throw new IllegalArgumentException(msg);
        }
        this.descriptors = descriptors;
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("invoking setDescriptors(" + descriptors + ")");
        }
    }

    public ICurrentUserProvider getCurrentUserProvider() {
        return this.currentUserProvider;
    }

    public void setCurrentUserProvider(ICurrentUserProvider currentUserProvider) {
        this.currentUserProvider = currentUserProvider;
    }

    @JsonIgnore
    public Set<String> getAvailableQueryAttributes(IPersonAttributeDaoFilter filter) {
        IUsernameAttributeProvider usernameAttributeProvider = super.getUsernameAttributeProvider();
        return Collections.singleton(usernameAttributeProvider.getUsernameAttribute());
    }

    @JsonIgnore
    public Set<IPersonAttributes> getPeopleWithMultivaluedAttributes(Map<String, List<Object>> query, IPersonAttributeDaoFilter filter) {
        IUsernameAttributeProvider usernameAttributeProvider;
        String uid;
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("invoking getPeopleWithMultivaluedAttributes(" + query + ")");
        }
        if ((uid = (usernameAttributeProvider = super.getUsernameAttributeProvider()).getUsernameFromQuery(query)) == null) {
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("No username attribute found in query, returning null");
            }
            return null;
        }
        uid = this.usernameCaseCanonicalizationMode.canonicalize(uid, this.usernameCaseCanonicalizationLocale);
        String targetName = this.descriptors.getName();
        if (targetName == null) {
            if (this.currentUserProvider != null) {
                targetName = this.currentUserProvider.getCurrentUserName();
            }
            if (targetName == null) {
                this.logger.warn("AdditionalDescriptors has a null name and a null name was returned by the currentUserProvider, returning null. " + this.descriptors);
                return null;
            }
        }
        if (uid.equals(targetName = this.usernameCaseCanonicalizationMode.canonicalize(targetName, this.usernameCaseCanonicalizationLocale))) {
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("Adding additional descriptors " + this.descriptors);
            }
            CaseInsensitiveNamedPersonImpl personAttributes = new CaseInsensitiveNamedPersonImpl(targetName, this.descriptors.getAttributes());
            return Collections.singleton(personAttributes);
        }
        return null;
    }

    public Set<String> getPossibleUserAttributeNames(IPersonAttributeDaoFilter filter) {
        return this.possibleUserAttributeNames;
    }

    public void setPossibleUserAttributeNames(Set<String> possibleUserAttributeNames) {
        this.possibleUserAttributeNames = possibleUserAttributeNames;
    }

    public CaseCanonicalizationMode getUsernameCaseCanonicalizationMode() {
        return this.usernameCaseCanonicalizationMode;
    }

    public void setUsernameCaseCanonicalizationMode(CaseCanonicalizationMode usernameCaseCanonicalizationMode) {
        this.usernameCaseCanonicalizationMode = usernameCaseCanonicalizationMode == null ? AbstractQueryPersonAttributeDao.DEFAULT_USERNAME_CASE_CANONICALIZATION_MODE : usernameCaseCanonicalizationMode;
    }

    public Locale getUsernameCaseCanonicalizationLocale() {
        return this.usernameCaseCanonicalizationLocale;
    }

    public void setUsernameCaseCanonicalizationLocale(Locale usernameCaseCanonicalizationLocale) {
        this.usernameCaseCanonicalizationLocale = usernameCaseCanonicalizationLocale == null ? Locale.getDefault() : usernameCaseCanonicalizationLocale;
    }
}

