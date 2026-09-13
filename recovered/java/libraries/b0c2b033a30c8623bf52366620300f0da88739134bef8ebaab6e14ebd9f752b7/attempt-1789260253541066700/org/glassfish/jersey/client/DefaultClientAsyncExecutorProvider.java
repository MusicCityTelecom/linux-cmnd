/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.client;

import java.util.logging.Logger;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.jersey.client.ClientAsyncExecutor;
import org.glassfish.jersey.client.internal.LocalizationMessages;
import org.glassfish.jersey.internal.util.collection.LazyValue;
import org.glassfish.jersey.internal.util.collection.Value;
import org.glassfish.jersey.internal.util.collection.Values;
import org.glassfish.jersey.spi.ThreadPoolExecutorProvider;

@ClientAsyncExecutor
class DefaultClientAsyncExecutorProvider
extends ThreadPoolExecutorProvider {
    private static final Logger LOGGER = Logger.getLogger(DefaultClientAsyncExecutorProvider.class.getName());
    private final LazyValue<Integer> asyncThreadPoolSize;

    @Inject
    public DefaultClientAsyncExecutorProvider(final @Named(value="ClientAsyncThreadPoolSize") int poolSize) {
        super("jersey-client-async-executor");
        this.asyncThreadPoolSize = Values.lazy(new Value<Integer>(){

            @Override
            public Integer get() {
                if (poolSize <= 0) {
                    LOGGER.config(LocalizationMessages.IGNORED_ASYNC_THREADPOOL_SIZE(poolSize));
                    return Integer.MAX_VALUE;
                }
                LOGGER.config(LocalizationMessages.USING_FIXED_ASYNC_THREADPOOL(poolSize));
                return poolSize;
            }
        });
    }

    @Override
    protected int getMaximumPoolSize() {
        return (Integer)this.asyncThreadPoolSize.get();
    }

    @Override
    protected int getCorePoolSize() {
        Integer maximumPoolSize = this.getMaximumPoolSize();
        if (maximumPoolSize != Integer.MAX_VALUE) {
            return maximumPoolSize;
        }
        return 0;
    }
}

