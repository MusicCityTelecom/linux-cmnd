/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Conditional
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.security.config.annotation.web.builders.HttpSecurity
 *  org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer$AuthorizedUrl
 *  org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer
 *  org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator
 *  org.springframework.security.oauth2.core.OAuth2TokenValidator
 *  org.springframework.security.oauth2.jose.jws.SignatureAlgorithm
 *  org.springframework.security.oauth2.jwt.Jwt
 *  org.springframework.security.oauth2.jwt.JwtClaimValidator
 *  org.springframework.security.oauth2.jwt.JwtDecoder
 *  org.springframework.security.oauth2.jwt.JwtDecoders
 *  org.springframework.security.oauth2.jwt.JwtValidators
 *  org.springframework.security.oauth2.jwt.NimbusJwtDecoder
 *  org.springframework.security.oauth2.jwt.SupplierJwtDecoder
 *  org.springframework.security.web.SecurityFilterChain
 *  org.springframework.util.CollectionUtils
 */
package org.springframework.boot.autoconfigure.security.oauth2.resource.servlet;

import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.ConditionalOnDefaultWebSecurity;
import org.springframework.boot.autoconfigure.security.oauth2.resource.IssuerUriCondition;
import org.springframework.boot.autoconfigure.security.oauth2.resource.KeyValueCondition;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimValidator;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.SupplierJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.util.CollectionUtils;

@Configuration(proxyBeanMethods=false)
class OAuth2ResourceServerJwtConfiguration {
    OAuth2ResourceServerJwtConfiguration() {
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnDefaultWebSecurity
    static class OAuth2SecurityFilterChainConfiguration {
        OAuth2SecurityFilterChainConfiguration() {
        }

        @Bean
        @ConditionalOnBean(value={JwtDecoder.class})
        SecurityFilterChain jwtSecurityFilterChain(HttpSecurity http) throws Exception {
            http.authorizeRequests(requests -> ((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)requests.anyRequest()).authenticated());
            http.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
            return (SecurityFilterChain)http.build();
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnMissingBean(value={JwtDecoder.class})
    static class JwtDecoderConfiguration {
        private final OAuth2ResourceServerProperties.Jwt properties;

        JwtDecoderConfiguration(OAuth2ResourceServerProperties properties) {
            this.properties = properties.getJwt();
        }

        @Bean
        @ConditionalOnProperty(name={"spring.security.oauth2.resourceserver.jwt.jwk-set-uri"})
        JwtDecoder jwtDecoderByJwkKeySetUri() {
            NimbusJwtDecoder nimbusJwtDecoder = NimbusJwtDecoder.withJwkSetUri((String)this.properties.getJwkSetUri()).jwsAlgorithms(this::jwsAlgorithms).build();
            String issuerUri = this.properties.getIssuerUri();
            Supplier<OAuth2TokenValidator<Jwt>> defaultValidator = issuerUri != null ? () -> JwtValidators.createDefaultWithIssuer((String)issuerUri) : JwtValidators::createDefault;
            nimbusJwtDecoder.setJwtValidator(this.getValidators(defaultValidator));
            return nimbusJwtDecoder;
        }

        private void jwsAlgorithms(Set<SignatureAlgorithm> signatureAlgorithms) {
            for (String algorithm : this.properties.getJwsAlgorithms()) {
                signatureAlgorithms.add(SignatureAlgorithm.from((String)algorithm));
            }
        }

        private OAuth2TokenValidator<Jwt> getValidators(Supplier<OAuth2TokenValidator<Jwt>> defaultValidator) {
            OAuth2TokenValidator<Jwt> defaultValidators = defaultValidator.get();
            List<String> audiences = this.properties.getAudiences();
            if (CollectionUtils.isEmpty(audiences)) {
                return defaultValidators;
            }
            ArrayList<Object> validators = new ArrayList<Object>();
            validators.add(defaultValidators);
            validators.add(new JwtClaimValidator("aud", aud -> aud != null && !Collections.disjoint(aud, audiences)));
            return new DelegatingOAuth2TokenValidator(validators);
        }

        @Bean
        @Conditional(value={KeyValueCondition.class})
        JwtDecoder jwtDecoderByPublicKeyValue() throws Exception {
            RSAPublicKey publicKey = (RSAPublicKey)KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(this.getKeySpec(this.properties.readPublicKey())));
            NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withPublicKey((RSAPublicKey)publicKey).signatureAlgorithm(SignatureAlgorithm.from((String)this.exactlyOneAlgorithm())).build();
            jwtDecoder.setJwtValidator(this.getValidators(JwtValidators::createDefault));
            return jwtDecoder;
        }

        private byte[] getKeySpec(String keyValue) {
            keyValue = keyValue.replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "");
            return Base64.getMimeDecoder().decode(keyValue);
        }

        private String exactlyOneAlgorithm() {
            int count;
            List<String> algorithms = this.properties.getJwsAlgorithms();
            int n = count = algorithms != null ? algorithms.size() : 0;
            if (count != 1) {
                throw new IllegalStateException("Creating a JWT decoder using a public key requires exactly one JWS algorithm but " + count + " were configured");
            }
            return algorithms.get(0);
        }

        @Bean
        @Conditional(value={IssuerUriCondition.class})
        SupplierJwtDecoder jwtDecoderByIssuerUri() {
            return new SupplierJwtDecoder(() -> {
                String issuerUri = this.properties.getIssuerUri();
                NimbusJwtDecoder jwtDecoder = (NimbusJwtDecoder)JwtDecoders.fromIssuerLocation((String)issuerUri);
                jwtDecoder.setJwtValidator(this.getValidators(() -> JwtValidators.createDefaultWithIssuer((String)issuerUri)));
                return jwtDecoder;
            });
        }
    }
}

