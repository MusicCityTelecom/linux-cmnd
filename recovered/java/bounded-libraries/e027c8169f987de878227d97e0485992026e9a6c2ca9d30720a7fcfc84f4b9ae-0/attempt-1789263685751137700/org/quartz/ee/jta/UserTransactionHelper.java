/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.transaction.HeuristicMixedException
 *  javax.transaction.HeuristicRollbackException
 *  javax.transaction.NotSupportedException
 *  javax.transaction.RollbackException
 *  javax.transaction.SystemException
 *  javax.transaction.UserTransaction
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.quartz.ee.jta;

import javax.naming.InitialContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserTransactionHelper {
    public static final String DEFAULT_USER_TX_LOCATION = "java:comp/UserTransaction";
    private static String userTxURL = "java:comp/UserTransaction";

    private UserTransactionHelper() {
    }

    public static String getUserTxLocation() {
        return userTxURL;
    }

    public static void setUserTxLocation(String userTxURL) {
        if (userTxURL != null) {
            UserTransactionHelper.userTxURL = userTxURL;
        }
    }

    public static UserTransaction lookupUserTransaction() throws SchedulerException {
        return new UserTransactionWithContext();
    }

    public static void returnUserTransaction(UserTransaction userTransaction) {
        if (userTransaction != null && userTransaction instanceof UserTransactionWithContext) {
            UserTransactionWithContext userTransactionWithContext = (UserTransactionWithContext)userTransaction;
            userTransactionWithContext.closeContext();
        }
    }

    private static class UserTransactionWithContext
    implements UserTransaction {
        InitialContext context;
        UserTransaction userTransaction;

        public UserTransactionWithContext() throws SchedulerException {
            try {
                this.context = new InitialContext();
            }
            catch (Throwable t) {
                throw new SchedulerException("UserTransactionHelper failed to create InitialContext to lookup/create UserTransaction.", t);
            }
            try {
                this.userTransaction = (UserTransaction)this.context.lookup(userTxURL);
            }
            catch (Throwable t) {
                this.closeContext();
                throw new SchedulerException("UserTransactionHelper could not lookup/create UserTransaction.", t);
            }
            if (this.userTransaction == null) {
                this.closeContext();
                throw new SchedulerException("UserTransactionHelper could not lookup/create UserTransaction from the InitialContext.");
            }
        }

        public void closeContext() {
            try {
                if (this.context != null) {
                    this.context.close();
                }
            }
            catch (Throwable t) {
                UserTransactionWithContext.getLog().warn("Failed to close InitialContext used to get a UserTransaction.", t);
            }
            this.context = null;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        protected void finalize() throws Throwable {
            try {
                if (this.context != null) {
                    UserTransactionWithContext.getLog().warn("UserTransaction was never returned to the UserTransactionHelper.");
                    this.closeContext();
                }
            }
            finally {
                super.finalize();
            }
        }

        private static Logger getLog() {
            return LoggerFactory.getLogger(UserTransactionWithContext.class);
        }

        public void begin() throws NotSupportedException, SystemException {
            this.userTransaction.begin();
        }

        public void commit() throws RollbackException, HeuristicMixedException, HeuristicRollbackException, SecurityException, IllegalStateException, SystemException {
            this.userTransaction.commit();
        }

        public void rollback() throws IllegalStateException, SecurityException, SystemException {
            this.userTransaction.rollback();
        }

        public void setRollbackOnly() throws IllegalStateException, SystemException {
            this.userTransaction.setRollbackOnly();
        }

        public int getStatus() throws SystemException {
            return this.userTransaction.getStatus();
        }

        public void setTransactionTimeout(int seconds) throws SystemException {
            this.userTransaction.setTransactionTimeout(seconds);
        }
    }
}

