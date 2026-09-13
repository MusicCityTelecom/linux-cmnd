/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.EntityManager
 *  javax.persistence.PersistenceException
 *  org.eclipse.persistence.sessions.UnitOfWork
 *  org.springframework.jdbc.datasource.ConnectionHandle
 *  org.springframework.lang.Nullable
 *  org.springframework.transaction.TransactionDefinition
 *  org.springframework.transaction.TransactionException
 */
package org.springframework.orm.jpa.vendor;

import java.sql.Connection;
import java.sql.SQLException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import org.eclipse.persistence.sessions.UnitOfWork;
import org.springframework.jdbc.datasource.ConnectionHandle;
import org.springframework.lang.Nullable;
import org.springframework.orm.jpa.DefaultJpaDialect;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;

public class EclipseLinkJpaDialect
extends DefaultJpaDialect {
    private boolean lazyDatabaseTransaction = false;

    public void setLazyDatabaseTransaction(boolean lazyDatabaseTransaction) {
        this.lazyDatabaseTransaction = lazyDatabaseTransaction;
    }

    @Override
    @Nullable
    public Object beginTransaction(EntityManager entityManager, TransactionDefinition definition) throws PersistenceException, SQLException, TransactionException {
        if (definition.getIsolationLevel() != -1) {
            UnitOfWork uow = (UnitOfWork)entityManager.unwrap(UnitOfWork.class);
            uow.getLogin().setTransactionIsolation(definition.getIsolationLevel());
        }
        entityManager.getTransaction().begin();
        if (!definition.isReadOnly() && !this.lazyDatabaseTransaction) {
            ((UnitOfWork)entityManager.unwrap(UnitOfWork.class)).beginEarlyTransaction();
        }
        return null;
    }

    @Override
    public ConnectionHandle getJdbcConnection(EntityManager entityManager, boolean readOnly) throws PersistenceException, SQLException {
        return new EclipseLinkConnectionHandle(entityManager);
    }

    private static class EclipseLinkConnectionHandle
    implements ConnectionHandle {
        private final EntityManager entityManager;
        @Nullable
        private Connection connection;

        public EclipseLinkConnectionHandle(EntityManager entityManager) {
            this.entityManager = entityManager;
        }

        public Connection getConnection() {
            if (this.connection == null) {
                this.connection = (Connection)this.entityManager.unwrap(Connection.class);
            }
            return this.connection;
        }
    }
}

