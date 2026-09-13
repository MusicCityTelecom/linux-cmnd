/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Splitter
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.util.function.FunctionUtils
 *  org.springframework.util.StringUtils
 */
package org.apereo.cas.authentication.handler.support.jaas;

import com.google.common.base.Splitter;
import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.util.function.FunctionUtils;

public class AccountsPreDefinedLoginModule
implements LoginModule {
    private Subject subject;
    private CallbackHandler callbackHandler;
    private Map<String, String> accounts;
    private boolean succeeded;

    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
        String providedAccounts;
        this.subject = subject;
        this.callbackHandler = callbackHandler;
        this.accounts = new LinkedHashMap<String, String>();
        String string = providedAccounts = options.containsKey("accounts") ? options.get("accounts").toString() : null;
        if (StringUtils.isNotBlank((CharSequence)providedAccounts)) {
            Set eachAccount = org.springframework.util.StringUtils.commaDelimitedListToSet((String)providedAccounts);
            eachAccount.stream().map(account -> Splitter.on((String)"::").splitToList((CharSequence)account)).filter(results -> results.size() == 2).forEach(results -> this.accounts.put((String)results.get(0), (String)results.get(1)));
        }
    }

    @Override
    public boolean login() throws LoginException {
        NameCallback nameCallback = new NameCallback("username");
        PasswordCallback passwordCallback = new PasswordCallback("password", false);
        FunctionUtils.doAndHandle(o -> this.callbackHandler.handle(new Callback[]{nameCallback, passwordCallback}), throwable -> {
            throw new FailedLoginException(throwable.getMessage());
        }).accept(nameCallback);
        String username = nameCallback.getName();
        if (this.accounts.containsKey(username)) {
            String password = new String(passwordCallback.getPassword());
            this.succeeded = this.accounts.get(username).equals(password);
            this.subject.getPrincipals().add(new StaticPrincipal(username));
            return true;
        }
        this.succeeded = false;
        return false;
    }

    @Override
    public boolean commit() {
        return this.succeeded;
    }

    @Override
    public boolean abort() {
        return false;
    }

    @Override
    public boolean logout() {
        return true;
    }

    public static class StaticPrincipal
    implements Principal {
        private String name;

        @Generated
        public StaticPrincipal() {
        }

        @Generated
        public StaticPrincipal(String name) {
            this.name = name;
        }

        @Override
        @Generated
        public String getName() {
            return this.name;
        }

        @Generated
        public void setName(String name) {
            this.name = name;
        }

        @Override
        @Generated
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof StaticPrincipal)) {
                return false;
            }
            StaticPrincipal other = (StaticPrincipal)o;
            if (!other.canEqual(this)) {
                return false;
            }
            String this$name = this.name;
            String other$name = other.name;
            return !(this$name == null ? other$name != null : !this$name.equals(other$name));
        }

        @Generated
        protected boolean canEqual(Object other) {
            return other instanceof StaticPrincipal;
        }

        @Override
        @Generated
        public int hashCode() {
            int PRIME = 59;
            int result = 1;
            String $name = this.name;
            result = result * 59 + ($name == null ? 43 : $name.hashCode());
            return result;
        }

        @Override
        @Generated
        public String toString() {
            return "AccountsPreDefinedLoginModule.StaticPrincipal(name=" + this.name + ")";
        }
    }
}

