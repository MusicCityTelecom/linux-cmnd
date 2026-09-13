/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.client.authentication;

import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFilter;

public class HttpAuthenticationFeature
implements Feature {
    public static final String HTTP_AUTHENTICATION_USERNAME = "jersey.config.client.http.auth.username";
    public static final String HTTP_AUTHENTICATION_PASSWORD = "jersey.config.client.http.auth.password";
    public static final String HTTP_AUTHENTICATION_BASIC_USERNAME = "jersey.config.client.http.auth.basic.username";
    public static final String HTTP_AUTHENTICATION_BASIC_PASSWORD = "jersey.config.client.http.auth.basic.password";
    public static final String HTTP_AUTHENTICATION_DIGEST_USERNAME = "jersey.config.client.http.auth.digest.username";
    public static final String HTTP_AUTHENTICATION_DIGEST_PASSWORD = "jersey.config.client.http.auth.digest.password";
    private final Mode mode;
    private final HttpAuthenticationFilter.Credentials basicCredentials;
    private final HttpAuthenticationFilter.Credentials digestCredentials;

    public static BasicBuilder basicBuilder() {
        return new BuilderImpl(Mode.BASIC_PREEMPTIVE);
    }

    public static HttpAuthenticationFeature basic(String username, byte[] password) {
        return HttpAuthenticationFeature.build(Mode.BASIC_PREEMPTIVE, username, password);
    }

    public static HttpAuthenticationFeature basic(String username, String password) {
        return HttpAuthenticationFeature.build(Mode.BASIC_PREEMPTIVE, username, password);
    }

    public static HttpAuthenticationFeature digest() {
        return HttpAuthenticationFeature.build(Mode.DIGEST);
    }

    public static HttpAuthenticationFeature digest(String username, byte[] password) {
        return HttpAuthenticationFeature.build(Mode.DIGEST, username, password);
    }

    public static HttpAuthenticationFeature digest(String username, String password) {
        return HttpAuthenticationFeature.build(Mode.DIGEST, username, password);
    }

    public static UniversalBuilder universalBuilder() {
        return new BuilderImpl(Mode.UNIVERSAL);
    }

    public static HttpAuthenticationFeature universal(String username, byte[] password) {
        return HttpAuthenticationFeature.build(Mode.UNIVERSAL, username, password);
    }

    public static HttpAuthenticationFeature universal(String username, String password) {
        return HttpAuthenticationFeature.build(Mode.UNIVERSAL, username, password);
    }

    private static HttpAuthenticationFeature build(Mode mode) {
        return new BuilderImpl(mode).build();
    }

    private static HttpAuthenticationFeature build(Mode mode, String username, byte[] password) {
        return new BuilderImpl(mode).credentials(username, password).build();
    }

    private static HttpAuthenticationFeature build(Mode mode, String username, String password) {
        return new BuilderImpl(mode).credentials(username, password).build();
    }

    private HttpAuthenticationFeature(Mode mode, HttpAuthenticationFilter.Credentials basicCredentials, HttpAuthenticationFilter.Credentials digestCredentials) {
        this.mode = mode;
        this.basicCredentials = basicCredentials;
        this.digestCredentials = digestCredentials;
    }

    @Override
    public boolean configure(FeatureContext context) {
        context.register(new HttpAuthenticationFilter(this.mode, this.basicCredentials, this.digestCredentials, context.getConfiguration()));
        return true;
    }

    static class BuilderImpl
    implements UniversalBuilder,
    BasicBuilder {
        private String usernameBasic;
        private byte[] passwordBasic;
        private String usernameDigest;
        private byte[] passwordDigest;
        private Mode mode;

        public BuilderImpl(Mode mode) {
            this.mode = mode;
        }

        @Override
        public Builder credentials(String username, String password) {
            return this.credentials(username, password == null ? null : password.getBytes(HttpAuthenticationFilter.CHARACTER_SET));
        }

        @Override
        public Builder credentials(String username, byte[] password) {
            this.credentialsForBasic(username, password);
            this.credentialsForDigest(username, password);
            return this;
        }

        @Override
        public UniversalBuilder credentialsForBasic(String username, String password) {
            return this.credentialsForBasic(username, password == null ? null : password.getBytes(HttpAuthenticationFilter.CHARACTER_SET));
        }

        @Override
        public UniversalBuilder credentialsForBasic(String username, byte[] password) {
            this.usernameBasic = username;
            this.passwordBasic = password;
            return this;
        }

        @Override
        public UniversalBuilder credentialsForDigest(String username, String password) {
            return this.credentialsForDigest(username, password == null ? null : password.getBytes(HttpAuthenticationFilter.CHARACTER_SET));
        }

        @Override
        public UniversalBuilder credentialsForDigest(String username, byte[] password) {
            this.usernameDigest = username;
            this.passwordDigest = password;
            return this;
        }

        @Override
        public HttpAuthenticationFeature build() {
            return new HttpAuthenticationFeature(this.mode, this.usernameBasic == null ? null : new HttpAuthenticationFilter.Credentials(this.usernameBasic, this.passwordBasic), this.usernameDigest == null ? null : new HttpAuthenticationFilter.Credentials(this.usernameDigest, this.passwordDigest));
        }

        @Override
        public BasicBuilder nonPreemptive() {
            if (this.mode == Mode.BASIC_PREEMPTIVE) {
                this.mode = Mode.BASIC_NON_PREEMPTIVE;
            }
            return this;
        }
    }

    public static interface UniversalBuilder
    extends Builder {
        public UniversalBuilder credentialsForBasic(String var1, String var2);

        public UniversalBuilder credentialsForBasic(String var1, byte[] var2);

        public UniversalBuilder credentialsForDigest(String var1, String var2);

        public UniversalBuilder credentialsForDigest(String var1, byte[] var2);
    }

    public static interface BasicBuilder
    extends Builder {
        public BasicBuilder nonPreemptive();
    }

    public static interface Builder {
        public Builder credentials(String var1, byte[] var2);

        public Builder credentials(String var1, String var2);

        public HttpAuthenticationFeature build();
    }

    static enum Mode {
        BASIC_PREEMPTIVE,
        BASIC_NON_PREEMPTIVE,
        DIGEST,
        UNIVERSAL;

    }
}

