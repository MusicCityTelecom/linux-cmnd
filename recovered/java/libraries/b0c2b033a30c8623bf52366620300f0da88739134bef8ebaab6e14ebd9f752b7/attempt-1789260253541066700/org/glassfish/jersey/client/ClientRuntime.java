/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.client;

import java.util.Collections;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Provider;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedMap;
import org.glassfish.jersey.client.AbortException;
import org.glassfish.jersey.client.ClientAsyncExecutorLiteral;
import org.glassfish.jersey.client.ClientBackgroundSchedulerLiteral;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientExecutor;
import org.glassfish.jersey.client.ClientFilteringStages;
import org.glassfish.jersey.client.ClientLifecycleListener;
import org.glassfish.jersey.client.ClientRequest;
import org.glassfish.jersey.client.ClientResponse;
import org.glassfish.jersey.client.InvocationInterceptorStages;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.RequestProcessingInitializationStage;
import org.glassfish.jersey.client.ResponseCallback;
import org.glassfish.jersey.client.internal.ClientResponseProcessingException;
import org.glassfish.jersey.client.internal.LocalizationMessages;
import org.glassfish.jersey.client.spi.AsyncConnectorCallback;
import org.glassfish.jersey.client.spi.Connector;
import org.glassfish.jersey.internal.BootstrapBag;
import org.glassfish.jersey.internal.Version;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.internal.inject.Providers;
import org.glassfish.jersey.internal.util.collection.LazyValue;
import org.glassfish.jersey.internal.util.collection.Ref;
import org.glassfish.jersey.internal.util.collection.Values;
import org.glassfish.jersey.message.MessageBodyWorkers;
import org.glassfish.jersey.model.internal.ManagedObjectsFinalizer;
import org.glassfish.jersey.process.internal.ChainableStage;
import org.glassfish.jersey.process.internal.RequestScope;
import org.glassfish.jersey.process.internal.Stage;
import org.glassfish.jersey.process.internal.Stages;

class ClientRuntime
implements JerseyClient.ShutdownHook,
ClientExecutor {
    private static final Logger LOG = Logger.getLogger(ClientRuntime.class.getName());
    private final Stage<ClientRequest> requestProcessingRoot;
    private final Stage<ClientResponse> responseProcessingRoot;
    private final Connector connector;
    private final ClientConfig config;
    private final RequestScope requestScope;
    private final LazyValue<ExecutorService> asyncRequestExecutor;
    private final LazyValue<ScheduledExecutorService> backgroundScheduler;
    private final Iterable<ClientLifecycleListener> lifecycleListeners;
    private final AtomicBoolean closed = new AtomicBoolean(false);
    private final ManagedObjectsFinalizer managedObjectsFinalizer;
    private final InjectionManager injectionManager;
    private final InvocationInterceptorStages.PreInvocationInterceptorStage preInvocationInterceptorStage;
    private final InvocationInterceptorStages.PostInvocationInterceptorStage postInvocationInterceptorStage;

    public ClientRuntime(ClientConfig config, Connector connector, InjectionManager injectionManager, BootstrapBag bootstrapBag) {
        Provider<Ref<ClientRequest>> clientRequest = () -> (Ref)injectionManager.getInstance(new GenericType<Ref<ClientRequest>>(){}.getType());
        RequestProcessingInitializationStage requestProcessingInitializationStage = new RequestProcessingInitializationStage(clientRequest, bootstrapBag.getMessageBodyWorkers(), injectionManager);
        Stage.Builder<ClientRequest> requestingChainBuilder = Stages.chain(requestProcessingInitializationStage);
        this.preInvocationInterceptorStage = InvocationInterceptorStages.createPreInvocationInterceptorStage(injectionManager);
        this.postInvocationInterceptorStage = InvocationInterceptorStages.createPostInvocationInterceptorStage(injectionManager);
        ChainableStage<ClientRequest> requestFilteringStage = this.preInvocationInterceptorStage.hasPreInvocationInterceptors() ? ClientFilteringStages.createRequestFilteringStage(this.preInvocationInterceptorStage.createPreInvocationInterceptorFilter(), injectionManager) : ClientFilteringStages.createRequestFilteringStage(injectionManager);
        this.requestProcessingRoot = requestFilteringStage != null ? requestingChainBuilder.build(requestFilteringStage) : requestingChainBuilder.build();
        ChainableStage<ClientResponse> responseFilteringStage = ClientFilteringStages.createResponseFilteringStage(injectionManager);
        this.responseProcessingRoot = responseFilteringStage != null ? responseFilteringStage : Stages.identity();
        this.managedObjectsFinalizer = bootstrapBag.getManagedObjectsFinalizer();
        this.config = config;
        this.connector = connector;
        this.requestScope = bootstrapBag.getRequestScope();
        this.asyncRequestExecutor = Values.lazy(() -> config.getExecutorService() == null ? injectionManager.getInstance(ExecutorService.class, ClientAsyncExecutorLiteral.INSTANCE) : config.getExecutorService());
        this.backgroundScheduler = Values.lazy(() -> config.getScheduledExecutorService() == null ? injectionManager.getInstance(ScheduledExecutorService.class, ClientBackgroundSchedulerLiteral.INSTANCE) : config.getScheduledExecutorService());
        this.injectionManager = injectionManager;
        this.lifecycleListeners = Providers.getAllProviders(injectionManager, ClientLifecycleListener.class);
        for (ClientLifecycleListener listener : this.lifecycleListeners) {
            try {
                listener.onInit();
            }
            catch (Throwable t) {
                LOG.log(Level.WARNING, LocalizationMessages.ERROR_LISTENER_INIT(listener.getClass().getName()), t);
            }
        }
    }

    Runnable createRunnableForAsyncProcessing(final ClientRequest request, final ResponseCallback callback) {
        try {
            this.requestScope.runInScope(() -> this.preInvocationInterceptorStage.beforeRequest(request));
        }
        catch (Throwable throwable) {
            return () -> this.requestScope.runInScope(() -> this.processFailure(request, throwable, callback));
        }
        return () -> this.requestScope.runInScope(() -> {
            Object runtimeException = null;
            try {
                ClientRequest processedRequest;
                try {
                    processedRequest = Stages.process(request, this.requestProcessingRoot);
                    processedRequest = this.addUserAgent(processedRequest, this.connector.getName());
                }
                catch (AbortException aborted) {
                    this.processResponse(request, aborted.getAbortResponse(), callback);
                    return;
                }
                AsyncConnectorCallback connectorCallback = new AsyncConnectorCallback(){

                    @Override
                    public void response(ClientResponse response) {
                        ClientRuntime.this.requestScope.runInScope(() -> ClientRuntime.this.processResponse(request, response, callback));
                    }

                    @Override
                    public void failure(Throwable failure) {
                        ClientRuntime.this.requestScope.runInScope(() -> ClientRuntime.this.processFailure(request, failure, callback));
                    }
                };
                this.connector.apply(processedRequest, connectorCallback);
            }
            catch (Throwable throwable) {
                this.processFailure(request, throwable, callback);
            }
        });
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return ((ExecutorService)this.asyncRequestExecutor.get()).submit(task);
    }

    @Override
    public Future<?> submit(Runnable task) {
        return ((ExecutorService)this.asyncRequestExecutor.get()).submit(task);
    }

    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        return ((ExecutorService)this.asyncRequestExecutor.get()).submit(task, result);
    }

    @Override
    public <T> ScheduledFuture<T> schedule(Callable<T> callable, long delay, TimeUnit unit) {
        return ((ScheduledExecutorService)this.backgroundScheduler.get()).schedule(callable, delay, unit);
    }

    @Override
    public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
        return ((ScheduledExecutorService)this.backgroundScheduler.get()).schedule(command, delay, unit);
    }

    private void processResponse(ClientRequest request, ClientResponse response, ResponseCallback callback) {
        ClientResponse processedResponse = null;
        Throwable caught = null;
        try {
            processedResponse = Stages.process(response, this.responseProcessingRoot);
        }
        catch (Throwable throwable) {
            caught = throwable;
        }
        try {
            processedResponse = this.postInvocationInterceptorStage.afterRequest(request, processedResponse, caught);
        }
        catch (Throwable throwable) {
            this.processFailure(throwable, callback);
            return;
        }
        callback.completed(processedResponse, this.requestScope);
    }

    private void processFailure(ClientRequest request, Throwable failure, ResponseCallback callback) {
        if (this.postInvocationInterceptorStage.hasPostInvocationInterceptor()) {
            try {
                ClientResponse clientResponse = this.postInvocationInterceptorStage.afterRequest(request, null, failure);
                callback.completed(clientResponse, this.requestScope);
            }
            catch (RuntimeException e) {
                RuntimeException t = e.getSuppressed().length == 1 && e.getSuppressed()[0] == failure ? failure : e;
                this.processFailure(t, callback);
            }
        } else {
            this.processFailure(failure, callback);
        }
    }

    private void processFailure(Throwable failure, ResponseCallback callback) {
        callback.failed(failure instanceof ProcessingException ? (ProcessingException)failure : new ProcessingException(failure));
    }

    private Future<?> submit(ExecutorService executor, Runnable task) {
        return executor.submit(() -> this.requestScope.runInScope(task));
    }

    private ClientRequest addUserAgent(ClientRequest clientRequest, String connectorName) {
        MultivaluedMap<String, Object> headers = clientRequest.getHeaders();
        if (headers.containsKey("User-Agent")) {
            if (clientRequest.getHeaderString("User-Agent") == null) {
                headers.remove("User-Agent");
            }
        } else if (!clientRequest.ignoreUserAgent()) {
            if (connectorName != null && !connectorName.isEmpty()) {
                headers.put("User-Agent", Collections.singletonList(String.format("Jersey/%s (%s)", Version.getVersion(), connectorName)));
            } else {
                headers.put("User-Agent", Collections.singletonList(String.format("Jersey/%s", Version.getVersion())));
            }
        }
        return clientRequest;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public ClientResponse invoke(ClientRequest request) {
        ProcessingException processingException = null;
        ClientResponse response = null;
        try {
            this.preInvocationInterceptorStage.beforeRequest(request);
            try {
                response = this.connector.apply(this.addUserAgent(Stages.process(request, this.requestProcessingRoot), this.connector.getName()));
            }
            catch (AbortException aborted) {
                response = aborted.getAbortResponse();
            }
            response = Stages.process(response, this.responseProcessingRoot);
            return response;
        }
        catch (ClientResponseProcessingException crpe) {
            processingException = crpe;
            response = crpe.getClientResponse();
            return response;
        }
        catch (ProcessingException pe) {
            processingException = pe;
            return processingException;
        }
        catch (Throwable t) {
            processingException = new ProcessingException(t.getMessage(), t);
            return processingException;
        }
        finally {
            response = this.postInvocationInterceptorStage.afterRequest(request, response, processingException);
            return response;
        }
    }

    public RequestScope getRequestScope() {
        return this.requestScope;
    }

    public ClientConfig getConfig() {
        return this.config;
    }

    protected void finalize() throws Throwable {
        try {
            this.close();
        }
        finally {
            super.finalize();
        }
    }

    @Override
    public void onShutdown() {
        this.close();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void close() {
        if (this.closed.compareAndSet(false, true)) {
            try {
                for (ClientLifecycleListener listener : this.lifecycleListeners) {
                    try {
                        listener.onClose();
                    }
                    catch (Throwable t) {
                        LOG.log(Level.WARNING, LocalizationMessages.ERROR_LISTENER_CLOSE(listener.getClass().getName()), t);
                    }
                }
            }
            finally {
                try {
                    this.connector.close();
                }
                finally {
                    this.managedObjectsFinalizer.preDestroy();
                    this.injectionManager.shutdown();
                }
            }
        }
    }

    public void preInitialize() {
        this.injectionManager.getInstance(MessageBodyWorkers.class);
    }

    public Connector getConnector() {
        return this.connector;
    }

    InjectionManager getInjectionManager() {
        return this.injectionManager;
    }
}

