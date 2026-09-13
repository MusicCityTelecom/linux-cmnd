/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.AuthenticationBuilder
 *  org.apereo.cas.authentication.AuthenticationTransaction
 *  org.apereo.cas.authentication.Credential
 *  org.apereo.cas.authentication.MultifactorAuthenticationFailureModeEvaluator
 *  org.apereo.cas.authentication.MultifactorAuthenticationProvider
 *  org.apereo.cas.configuration.model.support.mfa.BaseMultifactorAuthenticationProviderProperties$MultifactorAuthenticationProviderFailureModes
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.services.ServicesManager
 *  org.apereo.cas.util.function.FunctionUtils
 *  org.springframework.beans.factory.ObjectProvider
 */
package org.apereo.cas.authentication.metadata;

import lombok.Generated;
import org.apereo.cas.authentication.AuthenticationBuilder;
import org.apereo.cas.authentication.AuthenticationTransaction;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.MultifactorAuthenticationFailureModeEvaluator;
import org.apereo.cas.authentication.MultifactorAuthenticationProvider;
import org.apereo.cas.authentication.metadata.BaseAuthenticationMetaDataPopulator;
import org.apereo.cas.configuration.model.support.mfa.BaseMultifactorAuthenticationProviderProperties;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.ServicesManager;
import org.apereo.cas.util.function.FunctionUtils;
import org.springframework.beans.factory.ObjectProvider;

public class MultifactorAuthenticationProviderMetadataPopulator
extends BaseAuthenticationMetaDataPopulator {
    private final String authenticationContextAttribute;
    private final ObjectProvider<? extends MultifactorAuthenticationProvider> provider;
    private final ServicesManager servicesManager;

    public void populateAttributes(AuthenticationBuilder builder, AuthenticationTransaction transaction) {
        RegisteredService registeredService = this.servicesManager.findServiceBy(transaction.getService());
        MultifactorAuthenticationFailureModeEvaluator failureEval = ((MultifactorAuthenticationProvider)this.provider.getObject()).getFailureModeEvaluator();
        boolean bypass = failureEval != null && failureEval.evaluate(registeredService, (MultifactorAuthenticationProvider)this.provider.getObject()) == BaseMultifactorAuthenticationProviderProperties.MultifactorAuthenticationProviderFailureModes.PHANTOM && !((MultifactorAuthenticationProvider)this.provider.getObject()).isAvailable(registeredService);
        FunctionUtils.doIf((boolean)bypass, unused -> builder.mergeAttribute(this.authenticationContextAttribute, (Object)((MultifactorAuthenticationProvider)this.provider.getObject()).getId())).accept(this.provider);
    }

    public boolean supports(Credential credential) {
        return ((MultifactorAuthenticationProvider)this.provider.getObject()).getFailureModeEvaluator() != null && credential != null;
    }

    @Override
    @Generated
    public String toString() {
        return "MultifactorAuthenticationProviderMetadataPopulator(super=" + super.toString() + ", authenticationContextAttribute=" + this.authenticationContextAttribute + ", provider=" + this.provider + ", servicesManager=" + this.servicesManager + ")";
    }

    @Generated
    public MultifactorAuthenticationProviderMetadataPopulator(String authenticationContextAttribute, ObjectProvider<? extends MultifactorAuthenticationProvider> provider, ServicesManager servicesManager) {
        this.authenticationContextAttribute = authenticationContextAttribute;
        this.provider = provider;
        this.servicesManager = servicesManager;
    }
}

