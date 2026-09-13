/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.annotation.Conditional
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.session.ReactiveSessionRepository
 */
package org.springframework.boot.autoconfigure.session;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.session.ReactiveSessionCondition;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.ReactiveSessionRepository;

@Configuration(proxyBeanMethods=false)
@ConditionalOnMissingBean(value={ReactiveSessionRepository.class})
@Conditional(value={ReactiveSessionCondition.class})
class NoOpReactiveSessionConfiguration {
    NoOpReactiveSessionConfiguration() {
    }
}

