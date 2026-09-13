/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.annotation.Order
 */
package org.springframework.boot.web.context;

import java.util.Locale;
import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;
import org.springframework.boot.web.context.MissingWebServerFactoryBeanException;
import org.springframework.core.annotation.Order;

@Order(value=0)
class MissingWebServerFactoryBeanFailureAnalyzer
extends AbstractFailureAnalyzer<MissingWebServerFactoryBeanException> {
    MissingWebServerFactoryBeanFailureAnalyzer() {
    }

    @Override
    protected FailureAnalysis analyze(Throwable rootFailure, MissingWebServerFactoryBeanException cause) {
        return new FailureAnalysis("Web application could not be started as there was no " + cause.getBeanType().getName() + " bean defined in the context.", "Check your application's dependencies for a supported " + cause.getWebApplicationType().name().toLowerCase(Locale.ENGLISH) + " web server.\nCheck the configured web application type.", (Throwable)((Object)cause));
    }
}

