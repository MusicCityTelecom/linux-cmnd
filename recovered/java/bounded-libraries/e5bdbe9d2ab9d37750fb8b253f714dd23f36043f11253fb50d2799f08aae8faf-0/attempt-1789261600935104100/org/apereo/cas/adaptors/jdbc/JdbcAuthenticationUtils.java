/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.authentication.CoreAuthenticationUtils
 *  org.apereo.cas.authentication.principal.PrincipalNameTransformerUtils
 *  org.apereo.cas.authentication.support.password.PasswordEncoderUtils
 *  org.apereo.cas.authentication.support.password.PasswordPolicyContext
 *  org.apereo.cas.configuration.model.core.authentication.PasswordEncoderProperties
 *  org.apereo.cas.configuration.model.core.authentication.PrincipalTransformationProperties
 *  org.apereo.cas.configuration.model.support.jdbc.authn.BaseJdbcAuthenticationProperties
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ConfigurableApplicationContext
 */
package org.apereo.cas.adaptors.jdbc;

import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.adaptors.jdbc.AbstractJdbcUsernamePasswordAuthenticationHandler;
import org.apereo.cas.authentication.CoreAuthenticationUtils;
import org.apereo.cas.authentication.principal.PrincipalNameTransformerUtils;
import org.apereo.cas.authentication.support.password.PasswordEncoderUtils;
import org.apereo.cas.authentication.support.password.PasswordPolicyContext;
import org.apereo.cas.configuration.model.core.authentication.PasswordEncoderProperties;
import org.apereo.cas.configuration.model.core.authentication.PrincipalTransformationProperties;
import org.apereo.cas.configuration.model.support.jdbc.authn.BaseJdbcAuthenticationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

public final class JdbcAuthenticationUtils {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcAuthenticationUtils.class);

    public static void configureJdbcAuthenticationHandler(AbstractJdbcUsernamePasswordAuthenticationHandler handler, PasswordPolicyContext config, BaseJdbcAuthenticationProperties properties, ConfigurableApplicationContext applicationContext) {
        handler.setPasswordEncoder(PasswordEncoderUtils.newPasswordEncoder((PasswordEncoderProperties)properties.getPasswordEncoder(), (ApplicationContext)applicationContext));
        handler.setPrincipalNameTransformer(PrincipalNameTransformerUtils.newPrincipalNameTransformer((PrincipalTransformationProperties)properties.getPrincipalTransformation()));
        handler.setPasswordPolicyConfiguration(config);
        handler.setState(properties.getState());
        if (StringUtils.isNotBlank((CharSequence)properties.getCredentialCriteria())) {
            handler.setCredentialSelectionPredicate(CoreAuthenticationUtils.newCredentialSelectionPredicate((String)properties.getCredentialCriteria()));
        }
        LOGGER.trace("Configured authentication handler [{}] to handle database url at [{}]", (Object)handler.getName(), (Object)properties.getName());
    }

    @Generated
    private JdbcAuthenticationUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

