/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.security;

import java.io.Serializable;
import java.util.Set;
import javax.servlet.ServletRequest;
import org.eclipse.jetty.security.Authenticator;
import org.eclipse.jetty.security.ConstraintAware;
import org.eclipse.jetty.security.LoggedOutAuthentication;
import org.eclipse.jetty.security.SecurityHandler;
import org.eclipse.jetty.security.authentication.LoginAuthenticator;
import org.eclipse.jetty.server.Authentication;
import org.eclipse.jetty.server.UserIdentity;

public abstract class AbstractUserAuthentication
implements Authentication.User,
Serializable {
    private static final long serialVersionUID = -6290411814232723403L;
    protected String _method;
    protected transient UserIdentity _userIdentity;

    public AbstractUserAuthentication(String method, UserIdentity userIdentity) {
        this._method = method;
        this._userIdentity = userIdentity;
    }

    @Override
    public String getAuthMethod() {
        return this._method;
    }

    @Override
    public UserIdentity getUserIdentity() {
        return this._userIdentity;
    }

    @Override
    public boolean isUserInRole(UserIdentity.Scope scope, String role) {
        String roleToTest = null;
        if (scope != null && scope.getRoleRefMap() != null) {
            roleToTest = scope.getRoleRefMap().get(role);
        }
        if (roleToTest == null) {
            roleToTest = role;
        }
        if ("**".equals(roleToTest.trim())) {
            if (!this.declaredRolesContains("**")) {
                return true;
            }
            return this._userIdentity.isUserInRole(role, scope);
        }
        return this._userIdentity.isUserInRole(role, scope);
    }

    public boolean declaredRolesContains(String roleName) {
        SecurityHandler security = SecurityHandler.getCurrentSecurityHandler();
        if (security == null) {
            return false;
        }
        if (security instanceof ConstraintAware) {
            Set<String> declaredRoles = ((ConstraintAware)((Object)security)).getRoles();
            return declaredRoles != null && declaredRoles.contains(roleName);
        }
        return false;
    }

    @Override
    public Authentication logout(ServletRequest request) {
        SecurityHandler security = SecurityHandler.getCurrentSecurityHandler();
        if (security != null) {
            security.logout(this);
            Authenticator authenticator = security.getAuthenticator();
            if (authenticator instanceof LoginAuthenticator) {
                ((LoginAuthenticator)authenticator).logout(request);
                return new LoggedOutAuthentication((LoginAuthenticator)authenticator);
            }
        }
        return Authentication.UNAUTHENTICATED;
    }
}

