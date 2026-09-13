/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.oidc;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.RestEndpointProperties;
import org.apereo.cas.configuration.model.SpringResourceProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-oidc")
@JsonFilter(value="OidcWebFingerProperties")
public class OidcWebFingerProperties
implements Serializable {
    private static final long serialVersionUID = 231228615694269276L;
    private UserInfoRepository userInfo = new UserInfoRepository();

    @Generated
    public UserInfoRepository getUserInfo() {
        return this.userInfo;
    }

    @Generated
    public OidcWebFingerProperties setUserInfo(UserInfoRepository userInfo) {
        this.userInfo = userInfo;
        return this;
    }

    @RequiresModule(name="cas-server-support-oidc")
    public static class Rest
    extends RestEndpointProperties {
        private static final long serialVersionUID = -2172345378378393382L;
    }

    @RequiresModule(name="cas-server-support-oidc")
    public static class Groovy
    extends SpringResourceProperties {
        private static final long serialVersionUID = 7179027843747126083L;
    }

    @RequiresModule(name="cas-server-support-oidc")
    public static class UserInfoRepository
    implements Serializable {
        private static final long serialVersionUID = 1279027843747126043L;
        private Rest rest = new Rest();
        private Groovy groovy = new Groovy();

        @Generated
        public UserInfoRepository setRest(Rest rest) {
            this.rest = rest;
            return this;
        }

        @Generated
        public UserInfoRepository setGroovy(Groovy groovy) {
            this.groovy = groovy;
            return this;
        }

        @Generated
        public Rest getRest() {
            return this.rest;
        }

        @Generated
        public Groovy getGroovy() {
            return this.groovy;
        }
    }
}

