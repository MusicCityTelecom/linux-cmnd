/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.core.authentication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.authentication.GroovyAuthenticationPolicyProperties;
import org.apereo.cas.configuration.model.core.authentication.RestAuthenticationPolicyProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-core-authentication", automated=true)
public class AuthenticationPolicyProperties
implements Serializable {
    private static final long serialVersionUID = 2039700004862120066L;
    private boolean requiredHandlerAuthenticationPolicyEnabled;
    private boolean sourceSelectionEnabled;
    private AnyCredential any = new AnyCredential();
    private RequiredAuthenticationHandler req = new RequiredAuthenticationHandler();
    private AllCredentials all = new AllCredentials();
    private AllHandlers allHandlers = new AllHandlers();
    private List<GroovyAuthenticationPolicyProperties> groovy = new ArrayList<GroovyAuthenticationPolicyProperties>(0);
    private List<RestAuthenticationPolicyProperties> rest = new ArrayList<RestAuthenticationPolicyProperties>(0);
    private NotPrevented notPrevented = new NotPrevented();
    private UniquePrincipal uniquePrincipal = new UniquePrincipal();

    @Generated
    public boolean isRequiredHandlerAuthenticationPolicyEnabled() {
        return this.requiredHandlerAuthenticationPolicyEnabled;
    }

    @Generated
    public boolean isSourceSelectionEnabled() {
        return this.sourceSelectionEnabled;
    }

    @Generated
    public AnyCredential getAny() {
        return this.any;
    }

    @Generated
    public RequiredAuthenticationHandler getReq() {
        return this.req;
    }

    @Generated
    public AllCredentials getAll() {
        return this.all;
    }

    @Generated
    public AllHandlers getAllHandlers() {
        return this.allHandlers;
    }

    @Generated
    public List<GroovyAuthenticationPolicyProperties> getGroovy() {
        return this.groovy;
    }

    @Generated
    public List<RestAuthenticationPolicyProperties> getRest() {
        return this.rest;
    }

    @Generated
    public NotPrevented getNotPrevented() {
        return this.notPrevented;
    }

    @Generated
    public UniquePrincipal getUniquePrincipal() {
        return this.uniquePrincipal;
    }

    @Generated
    public AuthenticationPolicyProperties setRequiredHandlerAuthenticationPolicyEnabled(boolean requiredHandlerAuthenticationPolicyEnabled) {
        this.requiredHandlerAuthenticationPolicyEnabled = requiredHandlerAuthenticationPolicyEnabled;
        return this;
    }

    @Generated
    public AuthenticationPolicyProperties setSourceSelectionEnabled(boolean sourceSelectionEnabled) {
        this.sourceSelectionEnabled = sourceSelectionEnabled;
        return this;
    }

    @Generated
    public AuthenticationPolicyProperties setAny(AnyCredential any) {
        this.any = any;
        return this;
    }

    @Generated
    public AuthenticationPolicyProperties setReq(RequiredAuthenticationHandler req) {
        this.req = req;
        return this;
    }

    @Generated
    public AuthenticationPolicyProperties setAll(AllCredentials all) {
        this.all = all;
        return this;
    }

    @Generated
    public AuthenticationPolicyProperties setAllHandlers(AllHandlers allHandlers) {
        this.allHandlers = allHandlers;
        return this;
    }

    @Generated
    public AuthenticationPolicyProperties setGroovy(List<GroovyAuthenticationPolicyProperties> groovy) {
        this.groovy = groovy;
        return this;
    }

    @Generated
    public AuthenticationPolicyProperties setRest(List<RestAuthenticationPolicyProperties> rest) {
        this.rest = rest;
        return this;
    }

    @Generated
    public AuthenticationPolicyProperties setNotPrevented(NotPrevented notPrevented) {
        this.notPrevented = notPrevented;
        return this;
    }

    @Generated
    public AuthenticationPolicyProperties setUniquePrincipal(UniquePrincipal uniquePrincipal) {
        this.uniquePrincipal = uniquePrincipal;
        return this;
    }

    @RequiresModule(name="cas-server-core-authentication", automated=true)
    public static class RequiredAuthenticationHandler
    extends BaseAuthenticationPolicy {
        private static final long serialVersionUID = -4206244023952305821L;
        private boolean tryAll;
        private String handlerName = "handlerName";

        @Generated
        public boolean isTryAll() {
            return this.tryAll;
        }

        @Generated
        public String getHandlerName() {
            return this.handlerName;
        }

        @Generated
        public RequiredAuthenticationHandler setTryAll(boolean tryAll) {
            this.tryAll = tryAll;
            return this;
        }

        @Generated
        public RequiredAuthenticationHandler setHandlerName(String handlerName) {
            this.handlerName = handlerName;
            return this;
        }
    }

    @RequiresModule(name="cas-server-core-authentication", automated=true)
    public static class AllHandlers
    extends BaseAuthenticationPolicy {
        private static final long serialVersionUID = 928409456096460793L;
    }

    @RequiresModule(name="cas-server-core-authentication", automated=true)
    public static class AllCredentials
    extends BaseAuthenticationPolicy {
        private static final long serialVersionUID = 928409456096460793L;
    }

    @RequiresModule(name="cas-server-core-authentication", automated=true)
    public static class AnyCredential
    extends BaseAuthenticationPolicy {
        private static final long serialVersionUID = 4600357071276768175L;
        private boolean tryAll;

        public AnyCredential() {
            this.setEnabled(true);
        }

        @Generated
        public boolean isTryAll() {
            return this.tryAll;
        }

        @Generated
        public AnyCredential setTryAll(boolean tryAll) {
            this.tryAll = tryAll;
            return this;
        }
    }

    @RequiresModule(name="cas-server-core-authentication", automated=true)
    public static class UniquePrincipal
    extends BaseAuthenticationPolicy {
        private static final long serialVersionUID = -4930217087310738715L;
    }

    @RequiresModule(name="cas-server-core-authentication", automated=true)
    public static class NotPrevented
    extends BaseAuthenticationPolicy {
        private static final long serialVersionUID = 8184166804664983317L;
    }

    @RequiresModule(name="cas-server-core-authentication", automated=true)
    public static abstract class BaseAuthenticationPolicy
    implements Serializable {
        private static final long serialVersionUID = -1830217018850738715L;
        private boolean enabled;
        private String name;

        @Generated
        public boolean isEnabled() {
            return this.enabled;
        }

        @Generated
        public String getName() {
            return this.name;
        }

        @Generated
        public BaseAuthenticationPolicy setEnabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        @Generated
        public BaseAuthenticationPolicy setName(String name) {
            this.name = name;
            return this;
        }
    }
}

