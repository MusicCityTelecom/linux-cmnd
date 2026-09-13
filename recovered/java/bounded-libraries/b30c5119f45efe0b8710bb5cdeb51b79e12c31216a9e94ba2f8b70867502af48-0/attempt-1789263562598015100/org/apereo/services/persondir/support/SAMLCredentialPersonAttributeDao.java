/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.services.persondir.IPersonAttributes
 *  org.opensaml.saml2.core.Attribute
 *  org.opensaml.xml.XMLObject
 *  org.opensaml.xml.schema.XSString
 *  org.opensaml.xml.schema.impl.XSAnyImpl
 *  org.springframework.security.core.Authentication
 *  org.springframework.security.core.context.SecurityContextHolder
 *  org.springframework.security.saml.SAMLCredential
 */
package org.apereo.services.persondir.support;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.apereo.services.persondir.IPersonAttributes;
import org.apereo.services.persondir.support.AbstractQueryPersonAttributeDao;
import org.apereo.services.persondir.support.CaseInsensitiveNamedPersonImpl;
import org.apereo.services.persondir.support.ICurrentUserProvider;
import org.opensaml.saml2.core.Attribute;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.schema.XSString;
import org.opensaml.xml.schema.impl.XSAnyImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.saml.SAMLCredential;

public class SAMLCredentialPersonAttributeDao
extends AbstractQueryPersonAttributeDao<QueryBuilder> {
    private ICurrentUserProvider currentUserProvider;

    public void setCurrentUserProvider(ICurrentUserProvider currentUserProvider) {
        this.currentUserProvider = currentUserProvider;
    }

    @Override
    protected List<IPersonAttributes> getPeopleForQuery(QueryBuilder queryBuilder, String queryUserName) {
        String currentUserName = this.currentUserProvider.getCurrentUserName();
        if (currentUserName == null) {
            this.logger.warn("A null name was returned by the currentUserProvider, returning null.");
            return Collections.emptyList();
        }
        if (currentUserName.equals(queryUserName)) {
            SAMLCredential credential;
            Authentication authentication;
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("Adding attributes from the SAMLCredential for user " + currentUserName);
            }
            if ((authentication = SecurityContextHolder.getContext().getAuthentication()) != null && (credential = (SAMLCredential)authentication.getCredentials()) != null) {
                if (this.logger.isTraceEnabled()) {
                    StringBuilder msg = new StringBuilder();
                    msg.append("Credential obtained!");
                    for (Attribute a : credential.getAttributes()) {
                        msg.append("\n    a.getName()=").append(a.getName()).append("\n    a.getFriendlyName()=").append(a.getFriendlyName());
                        for (XMLObject xmlo : a.getAttributeValues()) {
                            String str = this.extractStringValue(xmlo);
                            msg.append("\n        value=" + str);
                        }
                    }
                    this.logger.trace(msg.toString());
                }
                HashMap<String, List<Object>> attributes = new HashMap<String, List<Object>>();
                for (Attribute a : credential.getAttributes()) {
                    ArrayList<String> list = new ArrayList<String>();
                    for (XMLObject xmlo : a.getAttributeValues()) {
                        String str = this.extractStringValue(xmlo);
                        if (str == null) continue;
                        list.add(str);
                    }
                    attributes.put(a.getName(), list);
                }
                CaseInsensitiveNamedPersonImpl personAttributes = new CaseInsensitiveNamedPersonImpl(currentUserName, attributes);
                return Collections.singletonList(personAttributes);
            }
        } else if (this.logger.isTraceEnabled()) {
            this.logger.trace("Skipping this DAO because !currentUserName.equals(queryUserName);  currentUserName=" + currentUserName + ", queryUserName=" + queryUserName);
        }
        return Collections.emptyList();
    }

    private String extractStringValue(XMLObject xmlo) {
        if (xmlo instanceof XSString) {
            return ((XSString)xmlo).getValue();
        }
        if (xmlo instanceof XSAnyImpl) {
            return ((XSAnyImpl)xmlo).getTextContent();
        }
        this.logger.warn("Unable to map attribute class {} to String. Unknown type. Enable TRACE logging to see attribute name", xmlo.getClass());
        return null;
    }

    @Override
    protected QueryBuilder appendAttributeToQuery(QueryBuilder queryBuilder, String dataAttribute, List<Object> queryValues) {
        return new QueryBuilder();
    }

    public static final class QueryBuilder {
    }
}

