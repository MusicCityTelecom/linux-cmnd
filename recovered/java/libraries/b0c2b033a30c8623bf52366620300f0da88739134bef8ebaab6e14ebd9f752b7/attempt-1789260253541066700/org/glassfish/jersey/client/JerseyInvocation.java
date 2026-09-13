/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.client;

import java.lang.reflect.Type;
import java.net.URI;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.BiFunction;
import java.util.logging.Logger;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotAcceptableException;
import javax.ws.rs.NotAllowedException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.NotSupportedException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.RedirectionException;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.ServiceUnavailableException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.CompletionStageRxInvoker;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.client.ResponseProcessingException;
import javax.ws.rs.client.RxInvoker;
import javax.ws.rs.client.RxInvokerProvider;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientRequest;
import org.glassfish.jersey.client.ClientResponse;
import org.glassfish.jersey.client.ClientRuntime;
import org.glassfish.jersey.client.CompletableFutureAsyncInvoker;
import org.glassfish.jersey.client.InboundJaxrsResponse;
import org.glassfish.jersey.client.JerseyCompletionStageRxInvoker;
import org.glassfish.jersey.client.ResponseCallback;
import org.glassfish.jersey.client.internal.ClientResponseProcessingException;
import org.glassfish.jersey.client.internal.LocalizationMessages;
import org.glassfish.jersey.internal.MapPropertiesDelegate;
import org.glassfish.jersey.internal.inject.Providers;
import org.glassfish.jersey.internal.util.Producer;
import org.glassfish.jersey.internal.util.PropertiesHelper;
import org.glassfish.jersey.internal.util.ReflectionHelper;
import org.glassfish.jersey.process.internal.RequestScope;
import org.glassfish.jersey.spi.ExecutorServiceProvider;

public class JerseyInvocation
implements Invocation {
    private static final Logger LOGGER = Logger.getLogger(JerseyInvocation.class.getName());
    private final ClientRequest requestContext;
    private final boolean copyRequestContext;
    private static final Map<String, EntityPresence> METHODS = JerseyInvocation.initializeMap();

    private JerseyInvocation(Builder builder) {
        this(builder, false);
    }

    private JerseyInvocation(Builder builder, boolean copyRequestContext) {
        this.validateHttpMethodAndEntity(builder.requestContext);
        this.requestContext = new ClientRequest(builder.requestContext);
        this.copyRequestContext = copyRequestContext;
    }

    private static Map<String, EntityPresence> initializeMap() {
        HashMap<String, EntityPresence> map = new HashMap<String, EntityPresence>();
        map.put("DELETE", EntityPresence.MUST_BE_NULL);
        map.put("GET", EntityPresence.MUST_BE_NULL);
        map.put("HEAD", EntityPresence.MUST_BE_NULL);
        map.put("OPTIONS", EntityPresence.MUST_BE_NULL);
        map.put("PATCH", EntityPresence.MUST_BE_PRESENT);
        map.put("POST", EntityPresence.OPTIONAL);
        map.put("PUT", EntityPresence.MUST_BE_PRESENT);
        map.put("TRACE", EntityPresence.MUST_BE_NULL);
        return map;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void validateHttpMethodAndEntity(ClientRequest request) {
        String method;
        EntityPresence entityPresence;
        boolean suppressExceptions = PropertiesHelper.isProperty(request.getConfiguration().getProperty("jersey.config.client.suppressHttpComplianceValidation"));
        Object shcvProperty = request.getProperty("jersey.config.client.suppressHttpComplianceValidation");
        if (shcvProperty != null) {
            suppressExceptions = PropertiesHelper.isProperty(shcvProperty);
        }
        if ((entityPresence = METHODS.get((method = request.getMethod()).toUpperCase(Locale.ROOT))) == EntityPresence.MUST_BE_NULL && request.hasEntity()) {
            if (!suppressExceptions) throw new IllegalStateException(LocalizationMessages.ERROR_HTTP_METHOD_ENTITY_NOT_NULL(method));
            LOGGER.warning(LocalizationMessages.ERROR_HTTP_METHOD_ENTITY_NOT_NULL(method));
            return;
        } else {
            if (entityPresence != EntityPresence.MUST_BE_PRESENT || request.hasEntity()) return;
            if (!suppressExceptions) throw new IllegalStateException(LocalizationMessages.ERROR_HTTP_METHOD_ENTITY_NULL(method));
            LOGGER.warning(LocalizationMessages.ERROR_HTTP_METHOD_ENTITY_NULL(method));
        }
    }

    private ClientRequest requestForCall(ClientRequest requestContext) {
        return this.copyRequestContext ? new ClientRequest(requestContext) : requestContext;
    }

    @Override
    public Response invoke() throws ProcessingException, WebApplicationException {
        ClientRuntime runtime = this.request().getClientRuntime();
        RequestScope requestScope = runtime.getRequestScope();
        return this.runInScope(() -> new InboundJaxrsResponse(runtime.invoke(this.requestForCall(this.requestContext)), requestScope), requestScope);
    }

    @Override
    public <T> T invoke(Class<T> responseType) throws ProcessingException, WebApplicationException {
        if (responseType == null) {
            throw new IllegalArgumentException(LocalizationMessages.RESPONSE_TYPE_IS_NULL());
        }
        ClientRuntime runtime = this.request().getClientRuntime();
        RequestScope requestScope = runtime.getRequestScope();
        return (T)this.runInScope(() -> this.translate(runtime.invoke(this.requestForCall(this.requestContext)), requestScope, responseType), requestScope);
    }

    @Override
    public <T> T invoke(GenericType<T> responseType) throws ProcessingException, WebApplicationException {
        if (responseType == null) {
            throw new IllegalArgumentException(LocalizationMessages.RESPONSE_TYPE_IS_NULL());
        }
        ClientRuntime runtime = this.request().getClientRuntime();
        RequestScope requestScope = runtime.getRequestScope();
        return (T)this.runInScope(() -> this.translate(runtime.invoke(this.requestForCall(this.requestContext)), requestScope, responseType), requestScope);
    }

    private <T> T runInScope(Producer<T> producer, RequestScope scope) throws ProcessingException, WebApplicationException {
        return (T)scope.runInScope(() -> this.call(producer, scope));
    }

    private <T> T call(Producer<T> producer, RequestScope scope) throws ProcessingException, WebApplicationException {
        try {
            return producer.call();
        }
        catch (ClientResponseProcessingException crpe) {
            throw new ResponseProcessingException(this.translate(crpe.getClientResponse(), scope, Response.class), crpe.getCause());
        }
        catch (ProcessingException ex) {
            if (WebApplicationException.class.isInstance(ex.getCause())) {
                throw (WebApplicationException)ex.getCause();
            }
            throw ex;
        }
    }

    @Override
    public Future<Response> submit() {
        CompletableFuture<Response> responseFuture = new CompletableFuture<Response>();
        ClientRuntime runtime = this.request().getClientRuntime();
        runtime.submit(runtime.createRunnableForAsyncProcessing(this.requestForCall(this.requestContext), new InvocationResponseCallback(responseFuture, (request, scope) -> this.translate((ClientResponse)request, (RequestScope)scope, (Class)Response.class))));
        return responseFuture;
    }

    @Override
    public <T> Future<T> submit(Class<T> responseType) {
        if (responseType == null) {
            throw new IllegalArgumentException(LocalizationMessages.RESPONSE_TYPE_IS_NULL());
        }
        CompletableFuture responseFuture = new CompletableFuture();
        ClientRuntime runtime = this.request().getClientRuntime();
        runtime.submit(runtime.createRunnableForAsyncProcessing(this.requestForCall(this.requestContext), new InvocationResponseCallback(responseFuture, (request, scope) -> this.translate((ClientResponse)request, (RequestScope)scope, responseType))));
        return responseFuture;
    }

    private <T> T translate(ClientResponse response, RequestScope scope, Class<T> responseType) throws ProcessingException {
        if (responseType == Response.class) {
            return responseType.cast(new InboundJaxrsResponse(response, scope));
        }
        if (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL) {
            try {
                return response.readEntity(responseType);
            }
            catch (ProcessingException ex) {
                if (ex.getClass() == ProcessingException.class) {
                    throw new ResponseProcessingException((Response)new InboundJaxrsResponse(response, scope), ex.getCause());
                }
                throw new ResponseProcessingException((Response)new InboundJaxrsResponse(response, scope), (Throwable)ex);
            }
            catch (WebApplicationException ex) {
                throw new ResponseProcessingException((Response)new InboundJaxrsResponse(response, scope), (Throwable)ex);
            }
            catch (Exception ex) {
                throw new ResponseProcessingException(new InboundJaxrsResponse(response, scope), LocalizationMessages.UNEXPECTED_ERROR_RESPONSE_PROCESSING(), ex);
            }
        }
        throw this.convertToException(new InboundJaxrsResponse(response, scope));
    }

    @Override
    public <T> Future<T> submit(GenericType<T> responseType) {
        if (responseType == null) {
            throw new IllegalArgumentException(LocalizationMessages.RESPONSE_TYPE_IS_NULL());
        }
        CompletableFuture responseFuture = new CompletableFuture();
        ClientRuntime runtime = this.request().getClientRuntime();
        runtime.submit(runtime.createRunnableForAsyncProcessing(this.requestForCall(this.requestContext), new InvocationResponseCallback(responseFuture, (request, scope) -> this.translate((ClientResponse)request, (RequestScope)scope, responseType))));
        return responseFuture;
    }

    private <T> T translate(ClientResponse response, RequestScope scope, GenericType<T> responseType) throws ProcessingException {
        if (responseType.getRawType() == Response.class) {
            return (T)new InboundJaxrsResponse(response, scope);
        }
        if (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL) {
            try {
                return response.readEntity(responseType);
            }
            catch (ProcessingException ex) {
                throw new ResponseProcessingException((Response)new InboundJaxrsResponse(response, scope), ex.getCause() != null ? ex.getCause() : ex);
            }
            catch (WebApplicationException ex) {
                throw new ResponseProcessingException((Response)new InboundJaxrsResponse(response, scope), (Throwable)ex);
            }
            catch (Exception ex) {
                throw new ResponseProcessingException(new InboundJaxrsResponse(response, scope), LocalizationMessages.UNEXPECTED_ERROR_RESPONSE_PROCESSING(), ex);
            }
        }
        throw this.convertToException(new InboundJaxrsResponse(response, scope));
    }

    @Override
    public <T> Future<T> submit(InvocationCallback<T> callback) {
        return this.submit(null, callback);
    }

    public <T> Future<T> submit(GenericType<T> responseType, final InvocationCallback<T> callback) {
        final CompletableFuture responseFuture = new CompletableFuture();
        try {
            Class callbackParamClass;
            Object callbackParamType;
            ReflectionHelper.DeclaringClassInterfacePair pair = ReflectionHelper.getClass(callback.getClass(), InvocationCallback.class);
            if (responseType == null) {
                Type[] typeArguments = ReflectionHelper.getParameterizedTypeArguments(pair);
                callbackParamType = typeArguments == null || typeArguments.length == 0 ? Object.class : typeArguments[0];
                callbackParamClass = ReflectionHelper.erasure((Type)callbackParamType);
            } else {
                callbackParamType = responseType.getType();
                callbackParamClass = ReflectionHelper.erasure(responseType.getRawType());
            }
            ResponseCallback responseCallback = new ResponseCallback((Type)callbackParamType){
                final /* synthetic */ Type val$callbackParamType;
                {
                    this.val$callbackParamType = type;
                }

                @Override
                public void completed(ClientResponse response, RequestScope scope) {
                    if (responseFuture.isCancelled()) {
                        response.close();
                        this.failed(new ProcessingException(new CancellationException(LocalizationMessages.ERROR_REQUEST_CANCELLED())));
                        return;
                    }
                    if (callbackParamClass == Response.class) {
                        Object result = callbackParamClass.cast(new InboundJaxrsResponse(response, scope));
                        responseFuture.complete(result);
                        callback.completed(result);
                    } else if (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL) {
                        Object result = response.readEntity(new GenericType(this.val$callbackParamType));
                        responseFuture.complete(result);
                        callback.completed(result);
                    } else {
                        this.failed(JerseyInvocation.this.convertToException(new InboundJaxrsResponse(response, scope)));
                    }
                }

                /*
                 * WARNING - Removed try catching itself - possible behaviour change.
                 */
                @Override
                public void failed(ProcessingException error) {
                    Exception called;
                    block5: {
                        called = null;
                        try {
                            if (error.getCause() instanceof WebApplicationException) {
                                responseFuture.completeExceptionally(error.getCause());
                                break block5;
                            }
                            if (responseFuture.isCancelled()) break block5;
                            try {
                                JerseyInvocation.this.call(() -> {
                                    throw error;
                                }, null);
                            }
                            catch (Exception ex) {
                                called = ex;
                                responseFuture.completeExceptionally(ex);
                            }
                        }
                        catch (Throwable throwable) {
                            callback.failed(error.getCause() instanceof CancellationException ? error.getCause() : (called != null ? called : error));
                            throw throwable;
                        }
                    }
                    callback.failed(error.getCause() instanceof CancellationException ? error.getCause() : (called != null ? called : error));
                }
            };
            ClientRuntime runtime = this.request().getClientRuntime();
            runtime.submit(runtime.createRunnableForAsyncProcessing(this.requestForCall(this.requestContext), responseCallback));
        }
        catch (Throwable error) {
            ProcessingException ce;
            if (error instanceof ClientResponseProcessingException) {
                ce = new ProcessingException(error.getCause());
                responseFuture.completeExceptionally(ce);
            } else if (error instanceof ProcessingException) {
                ce = (ProcessingException)error;
                responseFuture.completeExceptionally(ce);
            } else if (error instanceof WebApplicationException) {
                ce = new ProcessingException(error);
                responseFuture.completeExceptionally(error);
            } else {
                ce = new ProcessingException(error);
                responseFuture.completeExceptionally(ce);
            }
            callback.failed(ce);
        }
        return responseFuture;
    }

    @Override
    public JerseyInvocation property(String name, Object value) {
        this.requestContext.setProperty(name, value);
        return this;
    }

    private ProcessingException convertToException(Response response) {
        try {
            WebApplicationException webAppException;
            response.bufferEntity();
            int statusCode = response.getStatus();
            Response.Status status = Response.Status.fromStatusCode(statusCode);
            if (status == null) {
                Response.Status.Family statusFamily = response.getStatusInfo().getFamily();
                webAppException = this.createExceptionForFamily(response, statusFamily);
            } else {
                switch (status) {
                    case BAD_REQUEST: {
                        webAppException = new BadRequestException(response);
                        break;
                    }
                    case UNAUTHORIZED: {
                        webAppException = new NotAuthorizedException(response);
                        break;
                    }
                    case FORBIDDEN: {
                        webAppException = new ForbiddenException(response);
                        break;
                    }
                    case NOT_FOUND: {
                        webAppException = new NotFoundException(response);
                        break;
                    }
                    case METHOD_NOT_ALLOWED: {
                        webAppException = new NotAllowedException(response);
                        break;
                    }
                    case NOT_ACCEPTABLE: {
                        webAppException = new NotAcceptableException(response);
                        break;
                    }
                    case UNSUPPORTED_MEDIA_TYPE: {
                        webAppException = new NotSupportedException(response);
                        break;
                    }
                    case INTERNAL_SERVER_ERROR: {
                        webAppException = new InternalServerErrorException(response);
                        break;
                    }
                    case SERVICE_UNAVAILABLE: {
                        webAppException = new ServiceUnavailableException(response);
                        break;
                    }
                    default: {
                        Response.Status.Family statusFamily = response.getStatusInfo().getFamily();
                        webAppException = this.createExceptionForFamily(response, statusFamily);
                    }
                }
            }
            return new ResponseProcessingException(response, (Throwable)webAppException);
        }
        catch (Throwable t) {
            return new ResponseProcessingException(response, LocalizationMessages.RESPONSE_TO_EXCEPTION_CONVERSION_FAILED(), t);
        }
    }

    private WebApplicationException createExceptionForFamily(Response response, Response.Status.Family statusFamily) {
        WebApplicationException webAppException;
        switch (statusFamily) {
            case REDIRECTION: {
                webAppException = new RedirectionException(response);
                break;
            }
            case CLIENT_ERROR: {
                webAppException = new ClientErrorException(response);
                break;
            }
            case SERVER_ERROR: {
                webAppException = new ServerErrorException(response);
                break;
            }
            default: {
                webAppException = new WebApplicationException(response);
            }
        }
        return webAppException;
    }

    ClientRequest request() {
        return this.requestContext;
    }

    public String toString() {
        return "JerseyInvocation [" + this.request().getMethod() + ' ' + this.request().getUri() + "]";
    }

    private class InvocationResponseCallback<R>
    implements ResponseCallback {
        private final CompletableFuture<R> responseFuture;
        private final BiFunction<ClientResponse, RequestScope, R> producer;

        private InvocationResponseCallback(CompletableFuture<R> responseFuture, BiFunction<ClientResponse, RequestScope, R> producer) {
            this.responseFuture = responseFuture;
            this.producer = producer;
        }

        @Override
        public void completed(ClientResponse response, RequestScope scope) {
            if (this.responseFuture.isCancelled()) {
                response.close();
                return;
            }
            try {
                this.responseFuture.complete(this.producer.apply(response, scope));
            }
            catch (ProcessingException ex) {
                this.failed(ex);
            }
        }

        @Override
        public void failed(ProcessingException error) {
            if (this.responseFuture.isCancelled()) {
                return;
            }
            try {
                JerseyInvocation.this.call(() -> {
                    throw error;
                }, null);
            }
            catch (Exception exception) {
                this.responseFuture.completeExceptionally(exception);
            }
        }
    }

    static class AsyncInvoker
    extends CompletableFutureAsyncInvoker
    implements javax.ws.rs.client.AsyncInvoker {
        private final Builder builder;

        AsyncInvoker(Builder request) {
            this.builder = request;
            this.builder.requestContext.setAsynchronous(true);
        }

        @Override
        public CompletableFuture<Response> method(String name) {
            this.builder.requestContext.setMethod(name);
            return (CompletableFuture)new JerseyInvocation(this.builder).submit();
        }

        @Override
        public <T> CompletableFuture<T> method(String name, Class<T> responseType) {
            if (responseType == null) {
                throw new IllegalArgumentException(LocalizationMessages.RESPONSE_TYPE_IS_NULL());
            }
            this.builder.requestContext.setMethod(name);
            return (CompletableFuture)new JerseyInvocation(this.builder).submit(responseType);
        }

        @Override
        public <T> CompletableFuture<T> method(String name, GenericType<T> responseType) {
            if (responseType == null) {
                throw new IllegalArgumentException(LocalizationMessages.RESPONSE_TYPE_IS_NULL());
            }
            this.builder.requestContext.setMethod(name);
            return (CompletableFuture)new JerseyInvocation(this.builder).submit(responseType);
        }

        @Override
        public <T> CompletableFuture<T> method(String name, InvocationCallback<T> callback) {
            this.builder.requestContext.setMethod(name);
            return (CompletableFuture)new JerseyInvocation(this.builder).submit(callback);
        }

        @Override
        public CompletableFuture<Response> method(String name, Entity<?> entity) {
            this.builder.requestContext.setMethod(name);
            this.builder.storeEntity(entity);
            return (CompletableFuture)new JerseyInvocation(this.builder).submit();
        }

        @Override
        public <T> CompletableFuture<T> method(String name, Entity<?> entity, Class<T> responseType) {
            if (responseType == null) {
                throw new IllegalArgumentException(LocalizationMessages.RESPONSE_TYPE_IS_NULL());
            }
            this.builder.requestContext.setMethod(name);
            this.builder.storeEntity(entity);
            return (CompletableFuture)new JerseyInvocation(this.builder).submit(responseType);
        }

        @Override
        public <T> CompletableFuture<T> method(String name, Entity<?> entity, GenericType<T> responseType) {
            if (responseType == null) {
                throw new IllegalArgumentException(LocalizationMessages.RESPONSE_TYPE_IS_NULL());
            }
            this.builder.requestContext.setMethod(name);
            this.builder.storeEntity(entity);
            return (CompletableFuture)new JerseyInvocation(this.builder).submit(responseType);
        }

        @Override
        public <T> CompletableFuture<T> method(String name, Entity<?> entity, InvocationCallback<T> callback) {
            this.builder.requestContext.setMethod(name);
            this.builder.storeEntity(entity);
            return (CompletableFuture)new JerseyInvocation(this.builder).submit(callback);
        }
    }

    public static class Builder
    implements Invocation.Builder {
        private final ClientRequest requestContext;

        protected Builder(URI uri, ClientConfig configuration) {
            this.requestContext = new ClientRequest(uri, configuration, new MapPropertiesDelegate());
        }

        ClientRequest request() {
            return this.requestContext;
        }

        private void storeEntity(Entity<?> entity) {
            if (entity != null) {
                this.requestContext.variant(entity.getVariant());
                this.requestContext.setEntity(entity.getEntity());
                this.requestContext.setEntityAnnotations(entity.getAnnotations());
            }
        }

        @Override
        public JerseyInvocation build(String method) {
            this.requestContext.setMethod(method);
            return new JerseyInvocation(this, true);
        }

        @Override
        public JerseyInvocation build(String method, Entity<?> entity) {
            this.requestContext.setMethod(method);
            this.storeEntity(entity);
            return new JerseyInvocation(this, true);
        }

        @Override
        public JerseyInvocation buildGet() {
            this.requestContext.setMethod("GET");
            return new JerseyInvocation(this, true);
        }

        @Override
        public JerseyInvocation buildDelete() {
            this.requestContext.setMethod("DELETE");
            return new JerseyInvocation(this, true);
        }

        @Override
        public JerseyInvocation buildPost(Entity<?> entity) {
            this.requestContext.setMethod("POST");
            this.storeEntity(entity);
            return new JerseyInvocation(this, true);
        }

        @Override
        public JerseyInvocation buildPut(Entity<?> entity) {
            this.requestContext.setMethod("PUT");
            this.storeEntity(entity);
            return new JerseyInvocation(this, true);
        }

        @Override
        public javax.ws.rs.client.AsyncInvoker async() {
            return new AsyncInvoker(this);
        }

        @Override
        public Builder accept(String ... mediaTypes) {
            this.requestContext.accept(mediaTypes);
            return this;
        }

        @Override
        public Builder accept(MediaType ... mediaTypes) {
            this.requestContext.accept(mediaTypes);
            return this;
        }

        @Override
        public Invocation.Builder acceptEncoding(String ... encodings) {
            this.requestContext.getHeaders().addAll("Accept-Encoding", (Object[])encodings);
            return this;
        }

        @Override
        public Builder acceptLanguage(Locale ... locales) {
            this.requestContext.acceptLanguage(locales);
            return this;
        }

        @Override
        public Builder acceptLanguage(String ... locales) {
            this.requestContext.acceptLanguage(locales);
            return this;
        }

        @Override
        public Builder cookie(Cookie cookie) {
            this.requestContext.cookie(cookie);
            return this;
        }

        @Override
        public Builder cookie(String name, String value) {
            this.requestContext.cookie(new Cookie(name, value));
            return this;
        }

        @Override
        public Builder cacheControl(CacheControl cacheControl) {
            this.requestContext.cacheControl(cacheControl);
            return this;
        }

        @Override
        public Builder header(String name, Object value) {
            MultivaluedMap<String, Object> headers = this.requestContext.getHeaders();
            if (value == null) {
                headers.remove(name);
            } else {
                headers.add(name, value);
            }
            if ("User-Agent".equalsIgnoreCase(name)) {
                this.requestContext.ignoreUserAgent(value == null);
            }
            return this;
        }

        @Override
        public Builder headers(MultivaluedMap<String, Object> headers) {
            this.requestContext.replaceHeaders(headers);
            return this;
        }

        @Override
        public Response get() throws ProcessingException {
            return this.method("GET");
        }

        @Override
        public <T> T get(Class<T> responseType) throws ProcessingException, WebApplicationException {
            return this.method("GET", responseType);
        }

        @Override
        public <T> T get(GenericType<T> responseType) throws ProcessingException, WebApplicationException {
            return this.method("GET", responseType);
        }

        @Override
        public Response put(Entity<?> entity) throws ProcessingException {
            return this.method("PUT", entity);
        }

        @Override
        public <T> T put(Entity<?> entity, Class<T> responseType) throws ProcessingException, WebApplicationException {
            return this.method("PUT", entity, responseType);
        }

        @Override
        public <T> T put(Entity<?> entity, GenericType<T> responseType) throws ProcessingException, WebApplicationException {
            return this.method("PUT", entity, responseType);
        }

        @Override
        public Response post(Entity<?> entity) throws ProcessingException {
            return this.method("POST", entity);
        }

        @Override
        public <T> T post(Entity<?> entity, Class<T> responseType) throws ProcessingException, WebApplicationException {
            return this.method("POST", entity, responseType);
        }

        @Override
        public <T> T post(Entity<?> entity, GenericType<T> responseType) throws ProcessingException, WebApplicationException {
            return this.method("POST", entity, responseType);
        }

        @Override
        public Response delete() throws ProcessingException {
            return this.method("DELETE");
        }

        @Override
        public <T> T delete(Class<T> responseType) throws ProcessingException, WebApplicationException {
            return this.method("DELETE", responseType);
        }

        @Override
        public <T> T delete(GenericType<T> responseType) throws ProcessingException, WebApplicationException {
            return this.method("DELETE", responseType);
        }

        @Override
        public Response head() throws ProcessingException {
            return this.method("HEAD");
        }

        @Override
        public Response options() throws ProcessingException {
            return this.method("OPTIONS");
        }

        @Override
        public <T> T options(Class<T> responseType) throws ProcessingException, WebApplicationException {
            return this.method("OPTIONS", responseType);
        }

        @Override
        public <T> T options(GenericType<T> responseType) throws ProcessingException, WebApplicationException {
            return this.method("OPTIONS", responseType);
        }

        @Override
        public Response trace() throws ProcessingException {
            return this.method("TRACE");
        }

        @Override
        public <T> T trace(Class<T> responseType) throws ProcessingException, WebApplicationException {
            return this.method("TRACE", responseType);
        }

        @Override
        public <T> T trace(GenericType<T> responseType) throws ProcessingException, WebApplicationException {
            return this.method("TRACE", responseType);
        }

        @Override
        public Response method(String name) throws ProcessingException {
            this.requestContext.setMethod(name);
            return new JerseyInvocation(this).invoke();
        }

        @Override
        public <T> T method(String name, Class<T> responseType) throws ProcessingException, WebApplicationException {
            if (responseType == null) {
                throw new IllegalArgumentException(LocalizationMessages.RESPONSE_TYPE_IS_NULL());
            }
            this.requestContext.setMethod(name);
            return new JerseyInvocation(this).invoke(responseType);
        }

        @Override
        public <T> T method(String name, GenericType<T> responseType) throws ProcessingException, WebApplicationException {
            if (responseType == null) {
                throw new IllegalArgumentException(LocalizationMessages.RESPONSE_TYPE_IS_NULL());
            }
            this.requestContext.setMethod(name);
            return new JerseyInvocation(this).invoke(responseType);
        }

        @Override
        public Response method(String name, Entity<?> entity) throws ProcessingException {
            this.requestContext.setMethod(name);
            this.storeEntity(entity);
            return new JerseyInvocation(this).invoke();
        }

        @Override
        public <T> T method(String name, Entity<?> entity, Class<T> responseType) throws ProcessingException, WebApplicationException {
            if (responseType == null) {
                throw new IllegalArgumentException(LocalizationMessages.RESPONSE_TYPE_IS_NULL());
            }
            this.requestContext.setMethod(name);
            this.storeEntity(entity);
            return new JerseyInvocation(this).invoke(responseType);
        }

        @Override
        public <T> T method(String name, Entity<?> entity, GenericType<T> responseType) throws ProcessingException, WebApplicationException {
            if (responseType == null) {
                throw new IllegalArgumentException(LocalizationMessages.RESPONSE_TYPE_IS_NULL());
            }
            this.requestContext.setMethod(name);
            this.storeEntity(entity);
            return new JerseyInvocation(this).invoke(responseType);
        }

        @Override
        public Builder property(String name, Object value) {
            this.requestContext.setProperty(name, value);
            return this;
        }

        @Override
        public CompletionStageRxInvoker rx() {
            return this.rx(JerseyCompletionStageRxInvoker.class);
        }

        @Override
        public <T extends RxInvoker> T rx(Class<T> clazz) {
            if (clazz == JerseyCompletionStageRxInvoker.class) {
                ExecutorService provided;
                ExecutorService configured = this.request().getClientConfig().getExecutorService();
                if (configured == null && (provided = this.executorService()) != null) {
                    this.request().getClientConfig().executorService(provided);
                }
                return (T)new JerseyCompletionStageRxInvoker(this);
            }
            return this.createRxInvoker(clazz, this.executorService());
        }

        private <T extends RxInvoker> T rx(Class<T> clazz, ExecutorService executorService) {
            if (executorService == null) {
                throw new IllegalArgumentException(LocalizationMessages.NULL_INPUT_PARAMETER("executorService"));
            }
            return this.createRxInvoker(clazz, executorService);
        }

        private ExecutorService executorService() {
            ExecutorService result = this.request().getClientConfig().getExecutorService();
            if (result != null) {
                return result;
            }
            return this.requestContext.getInjectionManager().getInstance(ExecutorServiceProvider.class).getExecutorService();
        }

        private <T extends RxInvoker> T createRxInvoker(Class<? extends RxInvoker> clazz, ExecutorService executorService) {
            if (clazz == null) {
                throw new IllegalArgumentException(LocalizationMessages.NULL_INPUT_PARAMETER("clazz"));
            }
            Iterable<RxInvokerProvider> allProviders = Providers.getAllProviders(this.requestContext.getInjectionManager(), RxInvokerProvider.class);
            for (RxInvokerProvider invokerProvider : allProviders) {
                if (!invokerProvider.isProviderFor(clazz)) continue;
                Object rxInvoker = invokerProvider.getRxInvoker(this, executorService);
                if (rxInvoker == null) {
                    throw new IllegalStateException(LocalizationMessages.CLIENT_RX_PROVIDER_NULL());
                }
                return rxInvoker;
            }
            throw new IllegalStateException(LocalizationMessages.CLIENT_RX_PROVIDER_NOT_REGISTERED(clazz.getSimpleName()));
        }
    }

    private static enum EntityPresence {
        MUST_BE_NULL,
        MUST_BE_PRESENT,
        OPTIONAL;

    }
}

