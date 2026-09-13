/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.internal;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.DescriptorVisibility;
import org.glassfish.hk2.api.DynamicConfigurationListener;
import org.glassfish.hk2.api.ErrorInformation;
import org.glassfish.hk2.api.ErrorService;
import org.glassfish.hk2.api.ErrorType;
import org.glassfish.hk2.api.Filter;
import org.glassfish.hk2.api.ImmediateController;
import org.glassfish.hk2.api.MultiException;
import org.glassfish.hk2.api.Operation;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.api.ValidationInformation;
import org.glassfish.hk2.api.ValidationService;
import org.glassfish.hk2.api.Validator;
import org.glassfish.hk2.api.Visibility;
import org.glassfish.hk2.utilities.ImmediateContext;

@Singleton
@Visibility(value=DescriptorVisibility.LOCAL)
public class ImmediateHelper
implements DynamicConfigurationListener,
Runnable,
ValidationService,
ErrorService,
Validator,
ImmediateController {
    private static final ThreadFactory THREAD_FACTORY = new ImmediateThreadFactory();
    private static final Executor DEFAULT_EXECUTOR = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(true), THREAD_FACTORY);
    private final ServiceLocator locator;
    private final ImmediateContext immediateContext;
    private final HashSet<Long> tidsWithWork = new HashSet();
    private final Object queueLock = new Object();
    private boolean threadAvailable;
    private boolean outstandingJob;
    private boolean waitingForWork;
    private boolean firstTime = true;
    private ImmediateController.ImmediateServiceState currentState = ImmediateController.ImmediateServiceState.SUSPENDED;
    private Executor currentExecutor = DEFAULT_EXECUTOR;
    private long decayTime = 20000L;

    @Inject
    private ImmediateHelper(ServiceLocator serviceLocator, ImmediateContext immediateContext) {
        this.locator = serviceLocator;
        this.immediateContext = immediateContext;
    }

    private boolean hasWork() {
        long tid = Thread.currentThread().getId();
        boolean wasFirst = this.firstTime;
        this.firstTime = false;
        boolean retVal = this.tidsWithWork.contains(tid);
        this.tidsWithWork.remove(tid);
        if (retVal || !wasFirst) {
            return retVal;
        }
        List<ActiveDescriptor<?>> immediates = this.getImmediateServices();
        return !immediates.isEmpty();
    }

    private void doWorkIfWeHaveSome() {
        if (!this.hasWork()) {
            return;
        }
        this.outstandingJob = true;
        if (!this.threadAvailable) {
            this.threadAvailable = true;
            this.currentExecutor.execute(this);
        } else if (this.waitingForWork) {
            this.queueLock.notify();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void configurationChanged() {
        Object object = this.queueLock;
        synchronized (object) {
            if (this.currentState.equals((Object)ImmediateController.ImmediateServiceState.SUSPENDED)) {
                return;
            }
            this.doWorkIfWeHaveSome();
        }
    }

    @Override
    public Filter getLookupFilter() {
        return this.immediateContext.getValidationFilter();
    }

    @Override
    public Validator getValidator() {
        return this;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void onFailure(ErrorInformation errorInformation) throws MultiException {
        if (!ErrorType.DYNAMIC_CONFIGURATION_FAILURE.equals((Object)errorInformation.getErrorType())) {
            long tid = Thread.currentThread().getId();
            Object object = this.queueLock;
            synchronized (object) {
                this.tidsWithWork.remove(tid);
            }
            return;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean validate(ValidationInformation info) {
        if (info.getOperation().equals((Object)Operation.BIND) || info.getOperation().equals((Object)Operation.UNBIND)) {
            long tid = Thread.currentThread().getId();
            Object object = this.queueLock;
            synchronized (object) {
                this.tidsWithWork.add(tid);
            }
        }
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void run() {
        while (true) {
            Object object = this.queueLock;
            synchronized (object) {
                long elapsedTime;
                for (long decayTime = this.decayTime; this.currentState.equals((Object)ImmediateController.ImmediateServiceState.RUNNING) && !this.outstandingJob && decayTime > 0L; decayTime -= elapsedTime) {
                    this.waitingForWork = true;
                    long currentTime = System.currentTimeMillis();
                    try {
                        this.queueLock.wait(decayTime);
                    }
                    catch (InterruptedException ie) {
                        this.threadAvailable = false;
                        this.waitingForWork = false;
                        return;
                    }
                    elapsedTime = System.currentTimeMillis() - currentTime;
                }
                this.waitingForWork = false;
                if (!this.outstandingJob || this.currentState.equals((Object)ImmediateController.ImmediateServiceState.SUSPENDED)) {
                    this.threadAvailable = false;
                    return;
                }
                this.outstandingJob = false;
            }
            this.immediateContext.doWork();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Executor getExecutor() {
        Object object = this.queueLock;
        synchronized (object) {
            return this.currentExecutor;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void setExecutor(Executor executor) throws IllegalStateException {
        Object object = this.queueLock;
        synchronized (object) {
            if (this.currentState.equals((Object)ImmediateController.ImmediateServiceState.RUNNING)) {
                throw new IllegalStateException("ImmediateSerivce attempt made to change executor while in RUNNING state");
            }
            this.currentExecutor = executor == null ? DEFAULT_EXECUTOR : executor;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public long getThreadInactivityTimeout() {
        Object object = this.queueLock;
        synchronized (object) {
            return this.decayTime;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void setThreadInactivityTimeout(long timeInMillis) throws IllegalStateException {
        Object object = this.queueLock;
        synchronized (object) {
            if (timeInMillis < 0L) {
                throw new IllegalArgumentException();
            }
            this.decayTime = timeInMillis;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ImmediateController.ImmediateServiceState getImmediateState() {
        Object object = this.queueLock;
        synchronized (object) {
            return this.currentState;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void setImmediateState(ImmediateController.ImmediateServiceState state) {
        Object object = this.queueLock;
        synchronized (object) {
            if (state == null) {
                throw new IllegalArgumentException();
            }
            if (state == this.currentState) {
                return;
            }
            this.currentState = state;
            if (this.currentState.equals((Object)ImmediateController.ImmediateServiceState.RUNNING)) {
                this.doWorkIfWeHaveSome();
            }
        }
    }

    private List<ActiveDescriptor<?>> getImmediateServices() {
        List<ActiveDescriptor<?>> inScopeAndInThisLocator;
        try {
            inScopeAndInThisLocator = this.locator.getDescriptors(this.immediateContext.getValidationFilter());
        }
        catch (IllegalStateException ise) {
            inScopeAndInThisLocator = Collections.emptyList();
        }
        return inScopeAndInThisLocator;
    }

    private static class ImmediateThreadFactory
    implements ThreadFactory {
        private ImmediateThreadFactory() {
        }

        @Override
        public Thread newThread(Runnable runnable) {
            ImmediateThread activeThread = new ImmediateThread(runnable);
            return activeThread;
        }
    }

    private static class ImmediateThread
    extends Thread {
        private ImmediateThread(Runnable r) {
            super(r);
            this.setDaemon(true);
            this.setName(this.getClass().getSimpleName() + "-" + System.currentTimeMillis());
        }
    }
}

