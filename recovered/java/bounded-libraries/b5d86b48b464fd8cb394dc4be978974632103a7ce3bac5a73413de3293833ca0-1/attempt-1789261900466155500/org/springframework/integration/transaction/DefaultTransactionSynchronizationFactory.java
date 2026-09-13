/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.transaction.support.TransactionSynchronization
 *  org.springframework.util.Assert
 */
package org.springframework.integration.transaction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.integration.transaction.IntegrationResourceHolder;
import org.springframework.integration.transaction.IntegrationResourceHolderSynchronization;
import org.springframework.integration.transaction.TransactionSynchronizationFactory;
import org.springframework.integration.transaction.TransactionSynchronizationProcessor;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.util.Assert;

public class DefaultTransactionSynchronizationFactory
implements TransactionSynchronizationFactory {
    private final Log logger = LogFactory.getLog(this.getClass());
    private final TransactionSynchronizationProcessor processor;

    public DefaultTransactionSynchronizationFactory(TransactionSynchronizationProcessor processor) {
        Assert.notNull((Object)processor, (String)"'processor' must not be null");
        this.processor = processor;
    }

    @Override
    public TransactionSynchronization create(Object key) {
        Assert.notNull((Object)key, (String)"'key' must not be null");
        return new DefaultTransactionalResourceSynchronization(key);
    }

    private final class DefaultTransactionalResourceSynchronization
    extends IntegrationResourceHolderSynchronization {
        DefaultTransactionalResourceSynchronization(Object resourceKey) {
            super(new IntegrationResourceHolder(), resourceKey);
        }

        public void beforeCommit(boolean readOnly) {
            if (DefaultTransactionSynchronizationFactory.this.logger.isTraceEnabled()) {
                DefaultTransactionSynchronizationFactory.this.logger.trace((Object)"'pre-Committing' transactional resource");
            }
            DefaultTransactionSynchronizationFactory.this.processor.processBeforeCommit(this.resourceHolder);
        }

        protected boolean shouldReleaseBeforeCompletion() {
            return false;
        }

        protected void processResourceAfterCommit(IntegrationResourceHolder resourceHolder) {
            if (DefaultTransactionSynchronizationFactory.this.logger.isTraceEnabled()) {
                DefaultTransactionSynchronizationFactory.this.logger.trace((Object)"'Committing' transactional resource");
            }
            DefaultTransactionSynchronizationFactory.this.processor.processAfterCommit(resourceHolder);
        }

        public void afterCompletion(int status) {
            if (status != 0) {
                if (DefaultTransactionSynchronizationFactory.this.logger.isTraceEnabled()) {
                    DefaultTransactionSynchronizationFactory.this.logger.trace((Object)"'Rolling back' transactional resource");
                }
                DefaultTransactionSynchronizationFactory.this.processor.processAfterRollback(this.resourceHolder);
            }
            super.afterCompletion(status);
        }
    }
}

