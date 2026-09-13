/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  org.apache.commons.lang3.BooleanUtils
 *  org.apache.commons.lang3.StringUtils
 *  org.jooq.lambda.Unchecked
 *  org.springframework.core.annotation.AnnotatedElementUtils
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.ReflectionUtils
 */
package org.apereo.cas.configuration.features;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.jooq.lambda.Unchecked;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

public interface CasFeatureModule {
    private static String getMethodName(Field field, String prefix) {
        return prefix + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
    }

    @JsonIgnore
    default public boolean isDefined() {
        HashSet fields = new HashSet();
        ReflectionUtils.doWithFields(this.getClass(), fields::add, field -> AnnotatedElementUtils.isAnnotated((AnnotatedElement)field, RequiredProperty.class));
        return fields.stream().allMatch(Unchecked.predicate(field -> {
            String getter = CasFeatureModule.getMethodName(field, "get");
            if (ClassUtils.hasMethod(this.getClass(), (String)getter, (Class[])new Class[0])) {
                Method method = ClassUtils.getMethod(this.getClass(), (String)getter, (Class[])new Class[0]);
                method.setAccessible(true);
                Object value = method.invoke(this, new Object[0]);
                return value != null && StringUtils.isNotBlank((CharSequence)value.toString());
            }
            getter = CasFeatureModule.getMethodName(field, "is");
            if (ClassUtils.hasMethod(this.getClass(), (String)getter, (Class[])new Class[0])) {
                Method method = ClassUtils.getMethod(this.getClass(), (String)getter, (Class[])new Class[0]);
                method.setAccessible(true);
                Object value = method.invoke(this, new Object[0]);
                return value != null && BooleanUtils.toBoolean((String)value.toString());
            }
            return false;
        }));
    }

    @JsonIgnore
    default public boolean isUndefined() {
        return !this.isDefined();
    }

    public static enum FeatureCatalog {
        Webflow,
        Logout,
        Discovery,
        Core,
        SessionManagement,
        JDBC,
        GeoLocation,
        Metrics,
        Monitoring,
        CasConfiguration,
        Jetty,
        Undertow,
        SpringBootAdmin,
        WebApplication,
        ApacheTomcat,
        Notifications,
        Validation,
        Thymeleaf,
        Tokens,
        WsFederation,
        SAML,
        WsFederationIdentityProvider,
        Authentication,
        MultifactorAuthentication,
        MultifactorAuthenticationTrustedDevices,
        DelegatedAuthentication,
        Audit,
        Authy,
        Events,
        AccountManagement,
        AccountRegistration,
        AcceptableUsagePolicy,
        PersonDirectory,
        SPNEGO,
        PasswordlessAuthn,
        U2F,
        YubiKey,
        Electrofence,
        ACME,
        CAPTCHA,
        ForgotUsername,
        LDAP,
        InterruptNotifications,
        Radius,
        RadiusMFA,
        WebAuthn,
        GoogleAuthenticator,
        SCIM,
        ServiceRegistry,
        ServiceRegistryStreaming,
        SurrogateAuthentication,
        SAMLIdentityProvider,
        SAMLIdentityProviderMetadata,
        SAMLServiceProviderMetadata,
        OAuth,
        OpenIDConnect,
        Throttling,
        PasswordManagement,
        PasswordManagementHistory,
        TicketRegistry,
        TicketRegistryLocking,
        Consent,
        UMA,
        RestProtocol,
        SimpleMFA,
        X509,
        Reports;

        private static final Set<String> PRESENT_FEATURES;

        public void register() {
            this.register("");
        }

        public void register(String module) {
            Object featureName = this.name();
            if (StringUtils.isNotBlank((CharSequence)module)) {
                featureName = (String)featureName + "." + module;
            }
            PRESENT_FEATURES.add((String)featureName);
        }

        public static Set<String> getRegisteredFeatures() {
            return Set.copyOf(PRESENT_FEATURES);
        }

        public boolean isRegistered(String module) {
            Object featureName = this.name();
            if (StringUtils.isNotBlank((CharSequence)module)) {
                featureName = (String)featureName + "." + module;
            }
            return PRESENT_FEATURES.contains(featureName);
        }

        public boolean isRegistered() {
            return this.isRegistered("");
        }

        public String toProperty(String module) {
            String propertyName = CasFeatureModule.class.getSimpleName() + "." + this.name();
            if (StringUtils.isNotBlank((CharSequence)module)) {
                propertyName = propertyName + "." + module;
            }
            propertyName = propertyName + ".enabled";
            return propertyName;
        }

        static {
            PRESENT_FEATURES = Collections.synchronizedSet(new TreeSet());
        }
    }
}

