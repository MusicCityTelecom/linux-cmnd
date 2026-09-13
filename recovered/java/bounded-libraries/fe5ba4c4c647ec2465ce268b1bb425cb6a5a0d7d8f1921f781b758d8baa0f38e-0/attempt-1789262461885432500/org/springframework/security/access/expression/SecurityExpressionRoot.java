/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.security.access.expression;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.SecurityExpressionOperations;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

public abstract class SecurityExpressionRoot
implements SecurityExpressionOperations {
    protected final Authentication authentication;
    private AuthenticationTrustResolver trustResolver;
    private RoleHierarchy roleHierarchy;
    private Set<String> roles;
    private String defaultRolePrefix = "ROLE_";
    public final boolean permitAll = true;
    public final boolean denyAll = false;
    private PermissionEvaluator permissionEvaluator;
    public final String read = "read";
    public final String write = "write";
    public final String create = "create";
    public final String delete = "delete";
    public final String admin = "administration";

    public SecurityExpressionRoot(Authentication authentication) {
        if (authentication == null) {
            throw new IllegalArgumentException("Authentication object cannot be null");
        }
        this.authentication = authentication;
    }

    @Override
    public final boolean hasAuthority(String authority) {
        return this.hasAnyAuthority(authority);
    }

    @Override
    public final boolean hasAnyAuthority(String ... authorities) {
        return this.hasAnyAuthorityName(null, authorities);
    }

    @Override
    public final boolean hasRole(String role) {
        return this.hasAnyRole(role);
    }

    @Override
    public final boolean hasAnyRole(String ... roles) {
        return this.hasAnyAuthorityName(this.defaultRolePrefix, roles);
    }

    private boolean hasAnyAuthorityName(String prefix, String ... roles) {
        Set<String> roleSet = this.getAuthoritySet();
        for (String role : roles) {
            String defaultedRole = SecurityExpressionRoot.getRoleWithDefaultPrefix(prefix, role);
            if (!roleSet.contains(defaultedRole)) continue;
            return true;
        }
        return false;
    }

    @Override
    public final Authentication getAuthentication() {
        return this.authentication;
    }

    @Override
    public final boolean permitAll() {
        return true;
    }

    @Override
    public final boolean denyAll() {
        return false;
    }

    @Override
    public final boolean isAnonymous() {
        return this.trustResolver.isAnonymous(this.authentication);
    }

    @Override
    public final boolean isAuthenticated() {
        return !this.isAnonymous();
    }

    @Override
    public final boolean isRememberMe() {
        return this.trustResolver.isRememberMe(this.authentication);
    }

    @Override
    public final boolean isFullyAuthenticated() {
        return !this.trustResolver.isAnonymous(this.authentication) && !this.trustResolver.isRememberMe(this.authentication);
    }

    public Object getPrincipal() {
        return this.authentication.getPrincipal();
    }

    public void setTrustResolver(AuthenticationTrustResolver trustResolver) {
        this.trustResolver = trustResolver;
    }

    public void setRoleHierarchy(RoleHierarchy roleHierarchy) {
        this.roleHierarchy = roleHierarchy;
    }

    public void setDefaultRolePrefix(String defaultRolePrefix) {
        this.defaultRolePrefix = defaultRolePrefix;
    }

    private Set<String> getAuthoritySet() {
        if (this.roles == null) {
            Collection<? extends GrantedAuthority> userAuthorities = this.authentication.getAuthorities();
            if (this.roleHierarchy != null) {
                userAuthorities = this.roleHierarchy.getReachableGrantedAuthorities(userAuthorities);
            }
            this.roles = AuthorityUtils.authorityListToSet(userAuthorities);
        }
        return this.roles;
    }

    @Override
    public boolean hasPermission(Object target, Object permission) {
        return this.permissionEvaluator.hasPermission(this.authentication, target, permission);
    }

    @Override
    public boolean hasPermission(Object targetId, String targetType, Object permission) {
        return this.permissionEvaluator.hasPermission(this.authentication, (Serializable)targetId, targetType, permission);
    }

    public void setPermissionEvaluator(PermissionEvaluator permissionEvaluator) {
        this.permissionEvaluator = permissionEvaluator;
    }

    private static String getRoleWithDefaultPrefix(String defaultRolePrefix, String role) {
        if (role == null) {
            return role;
        }
        if (defaultRolePrefix == null || defaultRolePrefix.length() == 0) {
            return role;
        }
        if (role.startsWith(defaultRolePrefix)) {
            return role;
        }
        return defaultRolePrefix + role;
    }
}

