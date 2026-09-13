/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.annotation.Conditional
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.session.SessionRepository
 */
package org.springframework.boot.autoconfigure.session;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.session.ServletSessionCondition;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.SessionRepository;

@Configuration(proxyBeanMethods=false)
@ConditionalOnMissingBean(value={SessionRepository.class})
@Conditional(value={ServletSessionCondition.class})
class NoOpSessionConfiguration {
    NoOpSessionConfiguration() {
    }
}

