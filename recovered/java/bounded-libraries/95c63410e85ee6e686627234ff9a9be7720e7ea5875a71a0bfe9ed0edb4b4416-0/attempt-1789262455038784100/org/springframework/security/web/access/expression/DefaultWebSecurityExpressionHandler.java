/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.security.access.expression.AbstractSecurityExpressionHandler
 *  org.springframework.security.access.expression.SecurityExpressionHandler
 *  org.springframework.security.access.expression.SecurityExpressionOperations
 *  org.springframework.security.authentication.AuthenticationTrustResolver
 *  org.springframework.security.authentication.AuthenticationTrustResolverImpl
 *  org.springframework.security.core.Authentication
 *  org.springframework.util.Assert
 */
package org.springframework.security.web.access.expression;

import org.springframework.security.access.expression.AbstractSecurityExpressionHandler;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.access.expression.SecurityExpressionOperations;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.WebSecurityExpressionRoot;
import org.springframework.util.Assert;

public class DefaultWebSecurityExpressionHandler
extends AbstractSecurityExpressionHandler<FilterInvocation>
implements SecurityExpressionHandler<FilterInvocation> {
    private AuthenticationTrustResolver trustResolver = new AuthenticationTrustResolverImpl();
    private String defaultRolePrefix = "ROLE_";

    protected SecurityExpressionOperations createSecurityExpressionRoot(Authentication authentication, FilterInvocation fi) {
        WebSecurityExpressionRoot root = new WebSecurityExpressionRoot(authentication, fi);
        root.setPermissionEvaluator(this.getPermissionEvaluator());
        root.setTrustResolver(this.trustResolver);
        root.setRoleHierarchy(this.getRoleHierarchy());
        root.setDefaultRolePrefix(this.defaultRolePrefix);
        return root;
    }

    public void setTrustResolver(AuthenticationTrustResolver trustResolver) {
        Assert.notNull((Object)trustResolver, (String)"trustResolver cannot be null");
        this.trustResolver = trustResolver;
    }

    public void setDefaultRolePrefix(String defaultRolePrefix) {
        this.defaultRolePrefix = defaultRolePrefix;
    }
}

