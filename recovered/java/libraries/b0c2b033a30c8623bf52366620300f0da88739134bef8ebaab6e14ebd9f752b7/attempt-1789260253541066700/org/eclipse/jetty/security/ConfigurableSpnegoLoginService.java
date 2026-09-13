/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.security;

import java.io.Serializable;
import java.net.InetAddress;
import java.nio.file.Path;
import java.security.PrivilegedAction;
import java.util.Base64;
import java.util.HashMap;
import javax.security.auth.Subject;
import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.Configuration;
import javax.security.auth.login.LoginContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.eclipse.jetty.security.DefaultIdentityService;
import org.eclipse.jetty.security.IdentityService;
import org.eclipse.jetty.security.LoginService;
import org.eclipse.jetty.security.SpnegoUserIdentity;
import org.eclipse.jetty.security.SpnegoUserPrincipal;
import org.eclipse.jetty.security.authentication.AuthorizationService;
import org.eclipse.jetty.server.UserIdentity;
import org.eclipse.jetty.util.component.ContainerLifeCycle;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.ietf.jgss.GSSContext;
import org.ietf.jgss.GSSCredential;
import org.ietf.jgss.GSSException;
import org.ietf.jgss.GSSManager;
import org.ietf.jgss.GSSName;
import org.ietf.jgss.Oid;

public class ConfigurableSpnegoLoginService
extends ContainerLifeCycle
implements LoginService {
    private static final Logger LOG = Log.getLogger(ConfigurableSpnegoLoginService.class);
    private final GSSManager _gssManager = GSSManager.getInstance();
    private final String _realm;
    private final AuthorizationService _authorizationService;
    private IdentityService _identityService = new DefaultIdentityService();
    private String _serviceName;
    private Path _keyTabPath;
    private String _hostName;
    private SpnegoContext _context;

    public ConfigurableSpnegoLoginService(String realm, AuthorizationService authorizationService) {
        this._realm = realm;
        this._authorizationService = authorizationService;
    }

    @Override
    public String getName() {
        return this._realm;
    }

    public Path getKeyTabPath() {
        return this._keyTabPath;
    }

    public void setKeyTabPath(Path keyTabFile) {
        this._keyTabPath = keyTabFile;
    }

    public String getServiceName() {
        return this._serviceName;
    }

    public void setServiceName(String serviceName) {
        this._serviceName = serviceName;
    }

    public String getHostName() {
        return this._hostName;
    }

    public void setHostName(String hostName) {
        this._hostName = hostName;
    }

    @Override
    protected void doStart() throws Exception {
        if (this._hostName == null) {
            this._hostName = InetAddress.getLocalHost().getCanonicalHostName();
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Retrieving credentials for service {}/{}", this.getServiceName(), this.getHostName());
        }
        LoginContext loginContext = new LoginContext("", null, null, new SpnegoConfiguration());
        loginContext.login();
        Subject subject = loginContext.getSubject();
        this._context = Subject.doAs(subject, this.newSpnegoContext(subject));
        super.doStart();
    }

    private PrivilegedAction<SpnegoContext> newSpnegoContext(Subject subject) {
        return () -> {
            try {
                GSSName serviceName = this._gssManager.createName(this.getServiceName() + "@" + this.getHostName(), GSSName.NT_HOSTBASED_SERVICE);
                Oid kerberosOid = new Oid("1.2.840.113554.1.2.2");
                Oid spnegoOid = new Oid("1.3.6.1.5.5.2");
                Oid[] mechanisms = new Oid[]{kerberosOid, spnegoOid};
                GSSCredential serviceCredential = this._gssManager.createCredential(serviceName, 0, mechanisms, 2);
                SpnegoContext context = new SpnegoContext();
                context._subject = subject;
                context._serviceCredential = serviceCredential;
                return context;
            }
            catch (GSSException x) {
                throw new RuntimeException(x);
            }
        };
    }

    @Override
    public UserIdentity login(String username, Object credentials, ServletRequest req) {
        Subject subject = this._context._subject;
        HttpServletRequest request = (HttpServletRequest)req;
        HttpSession httpSession = request.getSession(false);
        GSSContext gssContext = null;
        if (httpSession != null) {
            GSSContextHolder holder = (GSSContextHolder)httpSession.getAttribute(GSSContextHolder.ATTRIBUTE);
            GSSContext gSSContext = gssContext = holder == null ? null : holder.gssContext;
        }
        if (gssContext == null) {
            gssContext = Subject.doAs(subject, this.newGSSContext());
        }
        byte[] input = Base64.getDecoder().decode((String)credentials);
        byte[] output = Subject.doAs(this._context._subject, this.acceptGSSContext(gssContext, input));
        String token = Base64.getEncoder().encodeToString(output);
        String userName = this.toUserName(gssContext);
        SpnegoUserPrincipal principal = new SpnegoUserPrincipal(userName, token);
        if (gssContext.isEstablished()) {
            if (httpSession != null) {
                httpSession.removeAttribute(GSSContextHolder.ATTRIBUTE);
            }
            UserIdentity roles = this._authorizationService.getUserIdentity(request, userName);
            return new SpnegoUserIdentity(subject, principal, roles);
        }
        if (httpSession == null) {
            httpSession = request.getSession(true);
        }
        GSSContextHolder holder = new GSSContextHolder(gssContext);
        httpSession.setAttribute(GSSContextHolder.ATTRIBUTE, holder);
        return new SpnegoUserIdentity(subject, principal, null);
    }

    private PrivilegedAction<GSSContext> newGSSContext() {
        return () -> {
            try {
                return this._gssManager.createContext(this._context._serviceCredential);
            }
            catch (GSSException x) {
                throw new RuntimeException(x);
            }
        };
    }

    private PrivilegedAction<byte[]> acceptGSSContext(GSSContext gssContext, byte[] token) {
        return () -> {
            try {
                return gssContext.acceptSecContext(token, 0, token.length);
            }
            catch (GSSException x) {
                throw new RuntimeException(x);
            }
        };
    }

    private String toUserName(GSSContext gssContext) {
        try {
            String name = gssContext.getSrcName().toString();
            int at = name.indexOf(64);
            if (at < 0) {
                return name;
            }
            return name.substring(0, at);
        }
        catch (GSSException x) {
            throw new RuntimeException(x);
        }
    }

    @Override
    public boolean validate(UserIdentity user) {
        return false;
    }

    @Override
    public IdentityService getIdentityService() {
        return this._identityService;
    }

    @Override
    public void setIdentityService(IdentityService identityService) {
        this._identityService = identityService;
    }

    @Override
    public void logout(UserIdentity user) {
    }

    private static class GSSContextHolder
    implements Serializable {
        public static final String ATTRIBUTE = GSSContextHolder.class.getName();
        private final transient GSSContext gssContext;

        private GSSContextHolder(GSSContext gssContext) {
            this.gssContext = gssContext;
        }
    }

    private static class SpnegoContext {
        private Subject _subject;
        private GSSCredential _serviceCredential;

        private SpnegoContext() {
        }
    }

    private class SpnegoConfiguration
    extends Configuration {
        private SpnegoConfiguration() {
        }

        @Override
        public AppConfigurationEntry[] getAppConfigurationEntry(String name) {
            String principal = ConfigurableSpnegoLoginService.this.getServiceName() + "/" + ConfigurableSpnegoLoginService.this.getHostName();
            HashMap<String, String> options = new HashMap<String, String>();
            if (LOG.isDebugEnabled()) {
                options.put("debug", "true");
            }
            options.put("doNotPrompt", "true");
            options.put("refreshKrb5Config", "true");
            options.put("principal", principal);
            options.put("useKeyTab", "true");
            Path keyTabPath = ConfigurableSpnegoLoginService.this.getKeyTabPath();
            if (keyTabPath != null) {
                options.put("keyTab", keyTabPath.toAbsolutePath().toString());
            }
            options.put("storeKey", "true");
            options.put("isInitiator", "false");
            String moduleClass = "com.sun.security.auth.module.Krb5LoginModule";
            AppConfigurationEntry config = new AppConfigurationEntry(moduleClass, AppConfigurationEntry.LoginModuleControlFlag.REQUIRED, options);
            return new AppConfigurationEntry[]{config};
        }
    }
}

