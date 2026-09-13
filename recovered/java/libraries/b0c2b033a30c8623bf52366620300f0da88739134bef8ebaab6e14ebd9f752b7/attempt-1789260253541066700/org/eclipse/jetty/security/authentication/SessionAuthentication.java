/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.security.authentication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionEvent;
import org.eclipse.jetty.security.AbstractUserAuthentication;
import org.eclipse.jetty.security.LoginService;
import org.eclipse.jetty.security.SecurityHandler;
import org.eclipse.jetty.server.UserIdentity;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

public class SessionAuthentication
extends AbstractUserAuthentication
implements Serializable,
HttpSessionActivationListener,
HttpSessionBindingListener {
    private static final Logger LOG = Log.getLogger(SessionAuthentication.class);
    private static final long serialVersionUID = -4643200685888258706L;
    public static final String __J_AUTHENTICATED = "org.eclipse.jetty.security.UserIdentity";
    private final String _name;
    private final Object _credentials;
    private transient HttpSession _session;

    public SessionAuthentication(String method, UserIdentity userIdentity, Object credentials) {
        super(method, userIdentity);
        this._name = userIdentity.getUserPrincipal().getName();
        this._credentials = credentials;
    }

    @Override
    public UserIdentity getUserIdentity() {
        if (this._userIdentity == null) {
            throw new IllegalStateException("!UserIdentity");
        }
        return super.getUserIdentity();
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        SecurityHandler security = SecurityHandler.getCurrentSecurityHandler();
        if (security == null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("!SecurityHandler", new Object[0]);
            }
            return;
        }
        LoginService loginService = security.getLoginService();
        if (loginService == null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("!LoginService", new Object[0]);
            }
            return;
        }
        this._userIdentity = loginService.login(this._name, this._credentials, null);
        LOG.debug("Deserialized and relogged in {}", this);
    }

    @Override
    @Deprecated
    public void logout() {
    }

    public String toString() {
        return String.format("%s@%x{%s,%s}", this.getClass().getSimpleName(), this.hashCode(), this._session == null ? "-" : this._session.getId(), this._userIdentity);
    }

    @Override
    public void sessionWillPassivate(HttpSessionEvent se) {
    }

    @Override
    public void sessionDidActivate(HttpSessionEvent se) {
        if (this._session == null) {
            this._session = se.getSession();
        }
    }

    @Override
    @Deprecated
    public void valueBound(HttpSessionBindingEvent event) {
    }

    @Override
    @Deprecated
    public void valueUnbound(HttpSessionBindingEvent event) {
    }
}

