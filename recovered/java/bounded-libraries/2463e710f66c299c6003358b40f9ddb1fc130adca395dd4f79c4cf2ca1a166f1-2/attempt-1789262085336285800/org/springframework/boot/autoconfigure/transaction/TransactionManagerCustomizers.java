/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.util.LambdaSafe
 *  org.springframework.boot.util.LambdaSafe$Callbacks
 *  org.springframework.transaction.PlatformTransactionManager
 */
package org.springframework.boot.autoconfigure.transaction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.springframework.boot.autoconfigure.transaction.PlatformTransactionManagerCustomizer;
import org.springframework.boot.util.LambdaSafe;
import org.springframework.transaction.PlatformTransactionManager;

public class TransactionManagerCustomizers {
    private final List<PlatformTransactionManagerCustomizer<?>> customizers;

    public TransactionManagerCustomizers(Collection<? extends PlatformTransactionManagerCustomizer<?>> customizers) {
        this.customizers = customizers != null ? new ArrayList(customizers) : Collections.emptyList();
    }

    public void customize(PlatformTransactionManager transactionManager) {
        ((LambdaSafe.Callbacks)LambdaSafe.callbacks(PlatformTransactionManagerCustomizer.class, this.customizers, (Object)transactionManager, (Object[])new Object[0]).withLogger(TransactionManagerCustomizers.class)).invoke(customizer -> customizer.customize(transactionManager));
    }
}

