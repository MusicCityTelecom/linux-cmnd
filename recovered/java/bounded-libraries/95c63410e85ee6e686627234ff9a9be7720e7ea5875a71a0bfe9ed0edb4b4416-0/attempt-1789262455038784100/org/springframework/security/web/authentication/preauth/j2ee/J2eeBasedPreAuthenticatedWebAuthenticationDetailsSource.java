/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.core.log.LogMessage
 *  org.springframework.security.authentication.AuthenticationDetailsSource
 *  org.springframework.security.core.authority.mapping.Attributes2GrantedAuthoritiesMapper
 *  org.springframework.security.core.authority.mapping.MappableAttributesRetriever
 *  org.springframework.security.core.authority.mapping.SimpleAttributes2GrantedAuthoritiesMapper
 *  org.springframework.util.Assert
 */
package org.springframework.security.web.authentication.preauth.j2ee;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.log.LogMessage;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.core.authority.mapping.Attributes2GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.MappableAttributesRetriever;
import org.springframework.security.core.authority.mapping.SimpleAttributes2GrantedAuthoritiesMapper;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails;
import org.springframework.util.Assert;

public class J2eeBasedPreAuthenticatedWebAuthenticationDetailsSource
implements AuthenticationDetailsSource<HttpServletRequest, PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails>,
InitializingBean {
    protected final Log logger = LogFactory.getLog(this.getClass());
    protected Set<String> j2eeMappableRoles;
    protected Attributes2GrantedAuthoritiesMapper j2eeUserRoles2GrantedAuthoritiesMapper = new SimpleAttributes2GrantedAuthoritiesMapper();

    public void afterPropertiesSet() {
        Assert.notNull(this.j2eeMappableRoles, (String)"No mappable roles available");
        Assert.notNull((Object)this.j2eeUserRoles2GrantedAuthoritiesMapper, (String)"Roles to granted authorities mapper not set");
    }

    protected Collection<String> getUserRoles(HttpServletRequest request) {
        ArrayList<String> j2eeUserRolesList = new ArrayList<String>();
        for (String role : this.j2eeMappableRoles) {
            if (!request.isUserInRole(role)) continue;
            j2eeUserRolesList.add(role);
        }
        return j2eeUserRolesList;
    }

    public PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails buildDetails(HttpServletRequest context) {
        Collection<String> j2eeUserRoles = this.getUserRoles(context);
        Collection userGrantedAuthorities = this.j2eeUserRoles2GrantedAuthoritiesMapper.getGrantedAuthorities(j2eeUserRoles);
        if (this.logger.isDebugEnabled()) {
            this.logger.debug((Object)LogMessage.format((String)"J2EE roles [%s] mapped to Granted Authorities: [%s]", j2eeUserRoles, (Object)userGrantedAuthorities));
        }
        return new PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails(context, userGrantedAuthorities);
    }

    public void setMappableRolesRetriever(MappableAttributesRetriever aJ2eeMappableRolesRetriever) {
        this.j2eeMappableRoles = Collections.unmodifiableSet(aJ2eeMappableRolesRetriever.getMappableAttributes());
    }

    public void setUserRoles2GrantedAuthoritiesMapper(Attributes2GrantedAuthoritiesMapper mapper) {
        this.j2eeUserRoles2GrantedAuthoritiesMapper = mapper;
    }
}

