/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.audit.AuditTrailExecutionPlan
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.cas.throttle.ThrottledRequestExecutor
 *  org.apereo.cas.throttle.ThrottledRequestResponseHandler
 *  org.apereo.cas.web.support.ThrottledSubmission
 *  org.apereo.cas.web.support.ThrottledSubmissionsStore
 *  org.springframework.context.ConfigurableApplicationContext
 */
package org.apereo.cas.web.support;

import lombok.Generated;
import org.apereo.cas.audit.AuditTrailExecutionPlan;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.throttle.ThrottledRequestExecutor;
import org.apereo.cas.throttle.ThrottledRequestResponseHandler;
import org.apereo.cas.web.support.ThrottledSubmission;
import org.apereo.cas.web.support.ThrottledSubmissionsStore;
import org.springframework.context.ConfigurableApplicationContext;

public class ThrottledSubmissionHandlerConfigurationContext {
    private final AuditTrailExecutionPlan auditTrailExecutionPlan;
    private final ThrottledRequestResponseHandler throttledRequestResponseHandler;
    private final ThrottledRequestExecutor throttledRequestExecutor;
    private final ConfigurableApplicationContext applicationContext;
    private final CasConfigurationProperties casProperties;
    private final ThrottledSubmissionsStore<ThrottledSubmission> throttledSubmissionStore;

    @Generated
    protected ThrottledSubmissionHandlerConfigurationContext(ThrottledSubmissionHandlerConfigurationContextBuilder<?, ?> b) {
        this.auditTrailExecutionPlan = b.auditTrailExecutionPlan;
        this.throttledRequestResponseHandler = b.throttledRequestResponseHandler;
        this.throttledRequestExecutor = b.throttledRequestExecutor;
        this.applicationContext = b.applicationContext;
        this.casProperties = b.casProperties;
        this.throttledSubmissionStore = b.throttledSubmissionStore;
    }

    @Generated
    public static ThrottledSubmissionHandlerConfigurationContextBuilder<?, ?> builder() {
        return new ThrottledSubmissionHandlerConfigurationContextBuilderImpl();
    }

    @Generated
    public String toString() {
        return "ThrottledSubmissionHandlerConfigurationContext(auditTrailExecutionPlan=" + this.auditTrailExecutionPlan + ", throttledRequestResponseHandler=" + this.throttledRequestResponseHandler + ", throttledRequestExecutor=" + this.throttledRequestExecutor + ", applicationContext=" + this.applicationContext + ", casProperties=" + this.casProperties + ", throttledSubmissionStore=" + this.throttledSubmissionStore + ")";
    }

    @Generated
    public AuditTrailExecutionPlan getAuditTrailExecutionPlan() {
        return this.auditTrailExecutionPlan;
    }

    @Generated
    public ThrottledRequestResponseHandler getThrottledRequestResponseHandler() {
        return this.throttledRequestResponseHandler;
    }

    @Generated
    public ThrottledRequestExecutor getThrottledRequestExecutor() {
        return this.throttledRequestExecutor;
    }

    @Generated
    public ConfigurableApplicationContext getApplicationContext() {
        return this.applicationContext;
    }

    @Generated
    public CasConfigurationProperties getCasProperties() {
        return this.casProperties;
    }

    @Generated
    public ThrottledSubmissionsStore<ThrottledSubmission> getThrottledSubmissionStore() {
        return this.throttledSubmissionStore;
    }

    @Generated
    private static final class ThrottledSubmissionHandlerConfigurationContextBuilderImpl
    extends ThrottledSubmissionHandlerConfigurationContextBuilder<ThrottledSubmissionHandlerConfigurationContext, ThrottledSubmissionHandlerConfigurationContextBuilderImpl> {
        @Generated
        private ThrottledSubmissionHandlerConfigurationContextBuilderImpl() {
        }

        @Override
        @Generated
        protected ThrottledSubmissionHandlerConfigurationContextBuilderImpl self() {
            return this;
        }

        @Override
        @Generated
        public ThrottledSubmissionHandlerConfigurationContext build() {
            return new ThrottledSubmissionHandlerConfigurationContext(this);
        }
    }

    @Generated
    public static abstract class ThrottledSubmissionHandlerConfigurationContextBuilder<C extends ThrottledSubmissionHandlerConfigurationContext, B extends ThrottledSubmissionHandlerConfigurationContextBuilder<C, B>> {
        @Generated
        private AuditTrailExecutionPlan auditTrailExecutionPlan;
        @Generated
        private ThrottledRequestResponseHandler throttledRequestResponseHandler;
        @Generated
        private ThrottledRequestExecutor throttledRequestExecutor;
        @Generated
        private ConfigurableApplicationContext applicationContext;
        @Generated
        private CasConfigurationProperties casProperties;
        @Generated
        private ThrottledSubmissionsStore<ThrottledSubmission> throttledSubmissionStore;

        @Generated
        protected abstract B self();

        @Generated
        public abstract C build();

        @Generated
        public B auditTrailExecutionPlan(AuditTrailExecutionPlan auditTrailExecutionPlan) {
            this.auditTrailExecutionPlan = auditTrailExecutionPlan;
            return this.self();
        }

        @Generated
        public B throttledRequestResponseHandler(ThrottledRequestResponseHandler throttledRequestResponseHandler) {
            this.throttledRequestResponseHandler = throttledRequestResponseHandler;
            return this.self();
        }

        @Generated
        public B throttledRequestExecutor(ThrottledRequestExecutor throttledRequestExecutor) {
            this.throttledRequestExecutor = throttledRequestExecutor;
            return this.self();
        }

        @Generated
        public B applicationContext(ConfigurableApplicationContext applicationContext) {
            this.applicationContext = applicationContext;
            return this.self();
        }

        @Generated
        public B casProperties(CasConfigurationProperties casProperties) {
            this.casProperties = casProperties;
            return this.self();
        }

        @Generated
        public B throttledSubmissionStore(ThrottledSubmissionsStore<ThrottledSubmission> throttledSubmissionStore) {
            this.throttledSubmissionStore = throttledSubmissionStore;
            return this.self();
        }

        @Generated
        public String toString() {
            return "ThrottledSubmissionHandlerConfigurationContext.ThrottledSubmissionHandlerConfigurationContextBuilder(auditTrailExecutionPlan=" + this.auditTrailExecutionPlan + ", throttledRequestResponseHandler=" + this.throttledRequestResponseHandler + ", throttledRequestExecutor=" + this.throttledRequestExecutor + ", applicationContext=" + this.applicationContext + ", casProperties=" + this.casProperties + ", throttledSubmissionStore=" + this.throttledSubmissionStore + ")";
        }
    }
}

