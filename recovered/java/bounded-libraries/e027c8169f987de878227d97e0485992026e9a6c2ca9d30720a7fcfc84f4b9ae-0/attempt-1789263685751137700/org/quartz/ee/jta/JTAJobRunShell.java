/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.transaction.SystemException
 *  javax.transaction.UserTransaction
 */
package org.quartz.ee.jta;

import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.core.JobRunShell;
import org.quartz.ee.jta.UserTransactionHelper;
import org.quartz.spi.TriggerFiredBundle;

public class JTAJobRunShell
extends JobRunShell {
    private final Integer transactionTimeout;
    private UserTransaction ut;

    public JTAJobRunShell(Scheduler scheduler, TriggerFiredBundle bndle) {
        super(scheduler, bndle);
        this.transactionTimeout = null;
    }

    public JTAJobRunShell(Scheduler scheduler, TriggerFiredBundle bndle, int timeout) {
        super(scheduler, bndle);
        this.transactionTimeout = timeout;
    }

    @Override
    protected void begin() throws SchedulerException {
        this.cleanupUserTransaction();
        boolean beganSuccessfully = false;
        try {
            this.getLog().debug("Looking up UserTransaction.");
            this.ut = UserTransactionHelper.lookupUserTransaction();
            if (this.transactionTimeout != null) {
                this.ut.setTransactionTimeout(this.transactionTimeout.intValue());
            }
            this.getLog().debug("Beginning UserTransaction.");
            this.ut.begin();
            beganSuccessfully = true;
        }
        catch (SchedulerException se) {
            throw se;
        }
        catch (Exception nse) {
            throw new SchedulerException("JTAJobRunShell could not start UserTransaction.", nse);
        }
        finally {
            if (!beganSuccessfully) {
                this.cleanupUserTransaction();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void complete(boolean successfulExecution) throws SchedulerException {
        block12: {
            if (this.ut == null) {
                return;
            }
            try {
                try {
                    if (this.ut.getStatus() == 1) {
                        this.getLog().debug("UserTransaction marked for rollback only.");
                        successfulExecution = false;
                    }
                }
                catch (SystemException e) {
                    throw new SchedulerException("JTAJobRunShell could not read UserTransaction status.", e);
                }
                if (successfulExecution) {
                    try {
                        this.getLog().debug("Committing UserTransaction.");
                        this.ut.commit();
                        break block12;
                    }
                    catch (Exception nse) {
                        throw new SchedulerException("JTAJobRunShell could not commit UserTransaction.", nse);
                    }
                }
                try {
                    this.getLog().debug("Rolling-back UserTransaction.");
                    this.ut.rollback();
                }
                catch (Exception nse) {
                    throw new SchedulerException("JTAJobRunShell could not rollback UserTransaction.", nse);
                }
            }
            finally {
                this.cleanupUserTransaction();
            }
        }
    }

    @Override
    public void passivate() {
        this.cleanupUserTransaction();
        super.passivate();
    }

    private void cleanupUserTransaction() {
        if (this.ut != null) {
            UserTransactionHelper.returnUserTransaction(this.ut);
            this.ut = null;
        }
    }
}

