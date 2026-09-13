/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.security;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.security.Authenticator;
import org.eclipse.jetty.security.DefaultAuthenticatorFactory;
import org.eclipse.jetty.security.DefaultIdentityService;
import org.eclipse.jetty.security.IdentityService;
import org.eclipse.jetty.security.LoginService;
import org.eclipse.jetty.security.RoleInfo;
import org.eclipse.jetty.security.ServerAuthException;
import org.eclipse.jetty.security.authentication.DeferredAuthentication;
import org.eclipse.jetty.server.Authentication;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.server.UserIdentity;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerWrapper;
import org.eclipse.jetty.util.component.DumpableCollection;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

public abstract class SecurityHandler
extends HandlerWrapper
implements Authenticator.AuthConfiguration {
    private static final Logger LOG = Log.getLogger(SecurityHandler.class);
    private static final List<Authenticator.Factory> __knownAuthenticatorFactories = new ArrayList<Authenticator.Factory>();
    private boolean _checkWelcomeFiles = false;
    private Authenticator _authenticator;
    private Authenticator.Factory _authenticatorFactory;
    private String _realmName;
    private String _authMethod;
    private final Map<String, String> _initParameters = new HashMap<String, String>();
    private LoginService _loginService;
    private IdentityService _identityService;
    private boolean _renewSession = true;
    public static final Principal __NO_USER;
    public static final Principal __NOBODY;

    protected SecurityHandler() {
        this.addBean(new DumpableCollection("knownAuthenticatorFactories", __knownAuthenticatorFactories));
    }

    @Override
    public IdentityService getIdentityService() {
        return this._identityService;
    }

    public void setIdentityService(IdentityService identityService) {
        if (this.isStarted()) {
            throw new IllegalStateException("Started");
        }
        this.updateBean(this._identityService, identityService);
        this._identityService = identityService;
    }

    @Override
    public LoginService getLoginService() {
        return this._loginService;
    }

    public void setLoginService(LoginService loginService) {
        if (this.isStarted()) {
            throw new IllegalStateException("Started");
        }
        this.updateBean(this._loginService, loginService);
        this._loginService = loginService;
    }

    public Authenticator getAuthenticator() {
        return this._authenticator;
    }

    public void setAuthenticator(Authenticator authenticator) {
        if (this.isStarted()) {
            throw new IllegalStateException("Started");
        }
        this.updateBean(this._authenticator, authenticator);
        this._authenticator = authenticator;
        if (this._authenticator != null) {
            this._authMethod = this._authenticator.getAuthMethod();
        }
    }

    public Authenticator.Factory getAuthenticatorFactory() {
        return this._authenticatorFactory;
    }

    public void setAuthenticatorFactory(Authenticator.Factory authenticatorFactory) {
        if (this.isRunning()) {
            throw new IllegalStateException("running");
        }
        this.updateBean(this._authenticatorFactory, authenticatorFactory);
        this._authenticatorFactory = authenticatorFactory;
    }

    public List<Authenticator.Factory> getKnownAuthenticatorFactories() {
        return __knownAuthenticatorFactories;
    }

    @Override
    public String getRealmName() {
        return this._realmName;
    }

    public void setRealmName(String realmName) {
        if (this.isRunning()) {
            throw new IllegalStateException("running");
        }
        this._realmName = realmName;
    }

    @Override
    public String getAuthMethod() {
        return this._authMethod;
    }

    public void setAuthMethod(String authMethod) {
        if (this.isRunning()) {
            throw new IllegalStateException("running");
        }
        this._authMethod = authMethod;
    }

    public boolean isCheckWelcomeFiles() {
        return this._checkWelcomeFiles;
    }

    public void setCheckWelcomeFiles(boolean authenticateWelcomeFiles) {
        if (this.isRunning()) {
            throw new IllegalStateException("running");
        }
        this._checkWelcomeFiles = authenticateWelcomeFiles;
    }

    @Override
    public String getInitParameter(String key) {
        return this._initParameters.get(key);
    }

    @Override
    public Set<String> getInitParameterNames() {
        return this._initParameters.keySet();
    }

    public String setInitParameter(String key, String value) {
        if (this.isStarted()) {
            throw new IllegalStateException("started");
        }
        return this._initParameters.put(key, value);
    }

    protected LoginService findLoginService() throws Exception {
        Collection<LoginService> list = this.getServer().getBeans(LoginService.class);
        LoginService service = null;
        String realm = this.getRealmName();
        if (realm != null) {
            for (LoginService s : list) {
                if (s.getName() == null || !s.getName().equals(realm)) continue;
                service = s;
                break;
            }
        } else if (list.size() == 1) {
            service = list.iterator().next();
        }
        return service;
    }

    protected IdentityService findIdentityService() {
        return this.getServer().getBean(IdentityService.class);
    }

    @Override
    protected void doStart() throws Exception {
        block20: {
            block21: {
                ContextHandler.Context context = ContextHandler.getCurrentContext();
                if (context != null) {
                    Enumeration<String> names = context.getInitParameterNames();
                    while (names != null && names.hasMoreElements()) {
                        String name = names.nextElement();
                        if (!name.startsWith("org.eclipse.jetty.security.") || this.getInitParameter(name) != null) continue;
                        this.setInitParameter(name, context.getInitParameter(name));
                    }
                }
                if (this._loginService == null) {
                    this.setLoginService(this.findLoginService());
                    if (this._loginService != null) {
                        this.unmanage(this._loginService);
                    }
                }
                if (this._identityService == null) {
                    if (this._loginService != null) {
                        this.setIdentityService(this._loginService.getIdentityService());
                    }
                    if (this._identityService == null) {
                        this.setIdentityService(this.findIdentityService());
                    }
                    if (this._identityService == null) {
                        if (this._realmName != null) {
                            this.setIdentityService(new DefaultIdentityService());
                            this.manage(this._identityService);
                        }
                    } else {
                        this.unmanage(this._identityService);
                    }
                }
                if (this._loginService != null) {
                    if (this._loginService.getIdentityService() == null) {
                        this._loginService.setIdentityService(this._identityService);
                    } else if (this._loginService.getIdentityService() != this._identityService) {
                        throw new IllegalStateException("LoginService has different IdentityService to " + this);
                    }
                }
                if (this._authenticator != null || this._identityService == null) break block20;
                if (this._authenticatorFactory == null) break block21;
                Authenticator authenticator = this._authenticatorFactory.getAuthenticator(this.getServer(), ContextHandler.getCurrentContext(), this, this._identityService, this._loginService);
                if (authenticator == null) break block20;
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Created authenticator {} with {}", authenticator, this._authenticatorFactory);
                }
                this.setAuthenticator(authenticator);
                break block20;
            }
            for (Authenticator.Factory factory : this.getKnownAuthenticatorFactories()) {
                Authenticator authenticator = factory.getAuthenticator(this.getServer(), ContextHandler.getCurrentContext(), this, this._identityService, this._loginService);
                if (authenticator == null) continue;
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Created authenticator {} with {}", authenticator, factory);
                }
                this.setAuthenticator(authenticator);
                break;
            }
        }
        if (this._authenticator != null) {
            this._authenticator.setConfiguration(this);
        } else if (this._realmName != null) {
            LOG.warn("No Authenticator for " + this, new Object[0]);
            throw new IllegalStateException("No Authenticator");
        }
        super.doStart();
    }

    @Override
    protected void doStop() throws Exception {
        if (!this.isManaged(this._identityService)) {
            this.removeBean(this._identityService);
            this._identityService = null;
        }
        if (!this.isManaged(this._loginService)) {
            this.removeBean(this._loginService);
            this._loginService = null;
        }
        super.doStop();
    }

    protected boolean checkSecurity(Request request) {
        switch (request.getDispatcherType()) {
            case REQUEST: 
            case ASYNC: {
                return true;
            }
            case FORWARD: {
                if (this.isCheckWelcomeFiles() && request.getAttribute("org.eclipse.jetty.server.welcome") != null) {
                    request.removeAttribute("org.eclipse.jetty.server.welcome");
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean isSessionRenewedOnAuthentication() {
        return this._renewSession;
    }

    public void setSessionRenewedOnAuthentication(boolean renew) {
        this._renewSession = renew;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void handle(String pathInContext, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Response base_response = baseRequest.getResponse();
        Handler handler = this.getHandler();
        if (handler == null) {
            return;
        }
        Authenticator authenticator = this._authenticator;
        if (this.checkSecurity(baseRequest)) {
            RoleInfo roleInfo;
            if (authenticator != null) {
                authenticator.prepareRequest(baseRequest);
            }
            if (!this.checkUserDataPermissions(pathInContext, baseRequest, base_response, roleInfo = this.prepareConstraintInfo(pathInContext, baseRequest))) {
                if (baseRequest.isHandled()) return;
                response.sendError(403);
                baseRequest.setHandled(true);
                return;
            }
            boolean isAuthMandatory = this.isAuthMandatory(baseRequest, base_response, roleInfo);
            if (isAuthMandatory && authenticator == null) {
                LOG.warn("No authenticator for: " + roleInfo, new Object[0]);
                if (baseRequest.isHandled()) return;
                response.sendError(403);
                baseRequest.setHandled(true);
                return;
            }
            Object previousIdentity = null;
            try {
                Authentication authentication = baseRequest.getAuthentication();
                if (authentication == null || authentication == Authentication.NOT_CHECKED) {
                    Authentication authentication2 = authentication = authenticator == null ? Authentication.UNAUTHENTICATED : authenticator.validateRequest(request, response, isAuthMandatory);
                }
                if (authentication instanceof Authentication.Wrapped) {
                    request = ((Authentication.Wrapped)authentication).getHttpServletRequest();
                    response = ((Authentication.Wrapped)authentication).getHttpServletResponse();
                }
                if (authentication instanceof Authentication.ResponseSent) {
                    baseRequest.setHandled(true);
                    return;
                }
                if (authentication instanceof Authentication.User) {
                    boolean authorized;
                    Authentication.User userAuth = (Authentication.User)authentication;
                    baseRequest.setAuthentication(authentication);
                    if (this._identityService != null) {
                        previousIdentity = this._identityService.associate(userAuth.getUserIdentity());
                    }
                    if (isAuthMandatory && !(authorized = this.checkWebResourcePermissions(pathInContext, baseRequest, base_response, roleInfo, userAuth.getUserIdentity()))) {
                        response.sendError(403, "!role");
                        baseRequest.setHandled(true);
                        if (this._identityService == null) return;
                        this._identityService.disassociate(previousIdentity);
                        return;
                    }
                    handler.handle(pathInContext, baseRequest, request, response);
                    if (authenticator == null) return;
                    authenticator.secureResponse(request, response, isAuthMandatory, userAuth);
                    return;
                }
                if (authentication instanceof Authentication.Deferred) {
                    DeferredAuthentication deferred = (DeferredAuthentication)authentication;
                    baseRequest.setAuthentication(authentication);
                    try {
                        handler.handle(pathInContext, baseRequest, request, response);
                    }
                    finally {
                        previousIdentity = deferred.getPreviousAssociation();
                    }
                    if (authenticator == null) return;
                    Authentication auth = baseRequest.getAuthentication();
                    if (auth instanceof Authentication.User) {
                        Authentication.User userAuth = (Authentication.User)auth;
                        authenticator.secureResponse(request, response, isAuthMandatory, userAuth);
                        return;
                    }
                    authenticator.secureResponse(request, response, isAuthMandatory, null);
                    return;
                }
                baseRequest.setAuthentication(authentication);
                if (this._identityService != null) {
                    previousIdentity = this._identityService.associate(null);
                }
                handler.handle(pathInContext, baseRequest, request, response);
                if (authenticator == null) return;
                authenticator.secureResponse(request, response, isAuthMandatory, null);
                return;
            }
            catch (ServerAuthException e) {
                response.sendError(500, e.getMessage());
                return;
            }
            finally {
                if (this._identityService != null) {
                    this._identityService.disassociate(previousIdentity);
                }
            }
        }
        handler.handle(pathInContext, baseRequest, request, response);
    }

    public static SecurityHandler getCurrentSecurityHandler() {
        ContextHandler.Context context = ContextHandler.getCurrentContext();
        if (context == null) {
            return null;
        }
        return context.getContextHandler().getChildHandlerByClass(SecurityHandler.class);
    }

    public void logout(Authentication.User user) {
        IdentityService identityService;
        LOG.debug("logout {}", user);
        if (user == null) {
            return;
        }
        LoginService loginService = this.getLoginService();
        if (loginService != null) {
            loginService.logout(user.getUserIdentity());
        }
        if ((identityService = this.getIdentityService()) != null) {
            Object previous = null;
            identityService.disassociate(previous);
        }
    }

    protected abstract RoleInfo prepareConstraintInfo(String var1, Request var2);

    protected abstract boolean checkUserDataPermissions(String var1, Request var2, Response var3, RoleInfo var4) throws IOException;

    protected abstract boolean isAuthMandatory(Request var1, Response var2, Object var3);

    protected abstract boolean checkWebResourcePermissions(String var1, Request var2, Response var3, Object var4, UserIdentity var5) throws IOException;

    static {
        Iterator<Authenticator.Factory> serviceLoaderIterator = ServiceLoader.load(Authenticator.Factory.class).iterator();
        while (true) {
            try {
                while (serviceLoaderIterator.hasNext()) {
                    __knownAuthenticatorFactories.add(serviceLoaderIterator.next());
                }
            }
            catch (ServiceConfigurationError error) {
                LOG.warn("Error while loading AuthenticatorFactory with ServiceLoader", error);
                continue;
            }
            break;
        }
        __knownAuthenticatorFactories.add(new DefaultAuthenticatorFactory());
        __NO_USER = new Principal(){

            @Override
            public String getName() {
                return null;
            }

            @Override
            public String toString() {
                return "No User";
            }
        };
        __NOBODY = new Principal(){

            @Override
            public String getName() {
                return "Nobody";
            }

            @Override
            public String toString() {
                return this.getName();
            }
        };
    }

    public class NotChecked
    implements Principal {
        @Override
        public String getName() {
            return null;
        }

        @Override
        public String toString() {
            return "NOT CHECKED";
        }

        public SecurityHandler getSecurityHandler() {
            return SecurityHandler.this;
        }
    }
}

