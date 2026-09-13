/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.core.log.LogMessage
 *  org.springframework.security.authentication.AuthenticationDetailsSource
 *  org.springframework.security.core.GrantedAuthority
 *  org.springframework.security.core.authority.mapping.Attributes2GrantedAuthoritiesMapper
 *  org.springframework.security.core.authority.mapping.SimpleAttributes2GrantedAuthoritiesMapper
 */
package org.springframework.security.web.authentication.preauth.websphere;

import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.log.LogMessage;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.Attributes2GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.SimpleAttributes2GrantedAuthoritiesMapper;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails;
import org.springframework.security.web.authentication.preauth.websphere.DefaultWASUsernameAndGroupsExtractor;
import org.springframework.security.web.authentication.preauth.websphere.WASUsernameAndGroupsExtractor;

public class WebSpherePreAuthenticatedWebAuthenticationDetailsSource
implements AuthenticationDetailsSource<HttpServletRequest, PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails> {
    private final Log logger = LogFactory.getLog(this.getClass());
    private Attributes2GrantedAuthoritiesMapper webSphereGroups2GrantedAuthoritiesMapper = new SimpleAttributes2GrantedAuthoritiesMapper();
    private final WASUsernameAndGroupsExtractor wasHelper;

    public WebSpherePreAuthenticatedWebAuthenticationDetailsSource() {
        this(new DefaultWASUsernameAndGroupsExtractor());
    }

    public WebSpherePreAuthenticatedWebAuthenticationDetailsSource(WASUsernameAndGroupsExtractor wasHelper) {
        this.wasHelper = wasHelper;
    }

    public PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails buildDetails(HttpServletRequest context) {
        return new PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails(context, this.getWebSphereGroupsBasedGrantedAuthorities());
    }

    private Collection<? extends GrantedAuthority> getWebSphereGroupsBasedGrantedAuthorities() {
        List<String> webSphereGroups = this.wasHelper.getGroupsForCurrentUser();
        Collection userGas = this.webSphereGroups2GrantedAuthoritiesMapper.getGrantedAuthorities(webSphereGroups);
        this.logger.debug((Object)LogMessage.format((String)"WebSphere groups: %s mapped to Granted Authorities: %s", webSphereGroups, (Object)userGas));
        return userGas;
    }

    public void setWebSphereGroups2GrantedAuthoritiesMapper(Attributes2GrantedAuthoritiesMapper mapper) {
        this.webSphereGroups2GrantedAuthoritiesMapper = mapper;
    }
}

