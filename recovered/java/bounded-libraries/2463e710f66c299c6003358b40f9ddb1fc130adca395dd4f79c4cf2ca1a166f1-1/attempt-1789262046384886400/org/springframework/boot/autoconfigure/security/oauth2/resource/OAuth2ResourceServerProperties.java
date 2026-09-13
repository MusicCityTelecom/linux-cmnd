/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.context.properties.ConfigurationProperties
 *  org.springframework.boot.context.properties.DeprecatedConfigurationProperty
 *  org.springframework.boot.context.properties.source.InvalidConfigurationPropertyValueException
 *  org.springframework.core.io.Resource
 *  org.springframework.util.Assert
 *  org.springframework.util.StreamUtils
 */
package org.springframework.boot.autoconfigure.security.oauth2.resource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.DeprecatedConfigurationProperty;
import org.springframework.boot.context.properties.source.InvalidConfigurationPropertyValueException;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;
import org.springframework.util.StreamUtils;

@ConfigurationProperties(prefix="spring.security.oauth2.resourceserver")
public class OAuth2ResourceServerProperties {
    private final Jwt jwt = new Jwt();
    private final Opaquetoken opaqueToken = new Opaquetoken();

    public Jwt getJwt() {
        return this.jwt;
    }

    public Opaquetoken getOpaquetoken() {
        return this.opaqueToken;
    }

    public static class Opaquetoken {
        private String clientId;
        private String clientSecret;
        private String introspectionUri;

        public String getClientId() {
            return this.clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getClientSecret() {
            return this.clientSecret;
        }

        public void setClientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
        }

        public String getIntrospectionUri() {
            return this.introspectionUri;
        }

        public void setIntrospectionUri(String introspectionUri) {
            this.introspectionUri = introspectionUri;
        }
    }

    public static class Jwt {
        private String jwkSetUri;
        private List<String> jwsAlgorithms = Arrays.asList("RS256");
        private String issuerUri;
        private Resource publicKeyLocation;
        private List<String> audiences = new ArrayList<String>();

        public String getJwkSetUri() {
            return this.jwkSetUri;
        }

        public void setJwkSetUri(String jwkSetUri) {
            this.jwkSetUri = jwkSetUri;
        }

        @Deprecated
        @DeprecatedConfigurationProperty(replacement="spring.security.oauth2.resourceserver.jwt.jws-algorithms")
        public String getJwsAlgorithm() {
            return this.jwsAlgorithms.isEmpty() ? null : this.jwsAlgorithms.get(0);
        }

        @Deprecated
        public void setJwsAlgorithm(String jwsAlgorithm) {
            this.jwsAlgorithms = new ArrayList<String>(Arrays.asList(jwsAlgorithm));
        }

        public List<String> getJwsAlgorithms() {
            return this.jwsAlgorithms;
        }

        public void setJwsAlgorithms(List<String> jwsAlgortithms) {
            this.jwsAlgorithms = jwsAlgortithms;
        }

        public String getIssuerUri() {
            return this.issuerUri;
        }

        public void setIssuerUri(String issuerUri) {
            this.issuerUri = issuerUri;
        }

        public Resource getPublicKeyLocation() {
            return this.publicKeyLocation;
        }

        public void setPublicKeyLocation(Resource publicKeyLocation) {
            this.publicKeyLocation = publicKeyLocation;
        }

        public List<String> getAudiences() {
            return this.audiences;
        }

        public void setAudiences(List<String> audiences) {
            this.audiences = audiences;
        }

        public String readPublicKey() throws IOException {
            String key = "spring.security.oauth2.resourceserver.public-key-location";
            Assert.notNull((Object)this.publicKeyLocation, (String)"PublicKeyLocation must not be null");
            if (!this.publicKeyLocation.exists()) {
                throw new InvalidConfigurationPropertyValueException(key, (Object)this.publicKeyLocation, "Public key location does not exist");
            }
            try (InputStream inputStream = this.publicKeyLocation.getInputStream();){
                String string = StreamUtils.copyToString((InputStream)inputStream, (Charset)StandardCharsets.UTF_8);
                return string;
            }
        }
    }
}

