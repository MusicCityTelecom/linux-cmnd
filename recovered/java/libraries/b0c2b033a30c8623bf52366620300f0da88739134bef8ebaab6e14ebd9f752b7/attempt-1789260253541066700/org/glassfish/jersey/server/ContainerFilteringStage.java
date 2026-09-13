/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server;

import java.util.ArrayList;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.internal.inject.Providers;
import org.glassfish.jersey.message.internal.TracingLogger;
import org.glassfish.jersey.model.internal.RankedComparator;
import org.glassfish.jersey.model.internal.RankedProvider;
import org.glassfish.jersey.process.internal.AbstractChainableStage;
import org.glassfish.jersey.process.internal.Stage;
import org.glassfish.jersey.process.internal.Stages;
import org.glassfish.jersey.server.ContainerRequest;
import org.glassfish.jersey.server.ContainerResponse;
import org.glassfish.jersey.server.internal.ServerTraceEvent;
import org.glassfish.jersey.server.internal.process.Endpoint;
import org.glassfish.jersey.server.internal.process.MappableException;
import org.glassfish.jersey.server.internal.process.RequestProcessingContext;
import org.glassfish.jersey.server.monitoring.RequestEvent;

class ContainerFilteringStage
extends AbstractChainableStage<RequestProcessingContext> {
    private final Iterable<RankedProvider<ContainerRequestFilter>> requestFilters;
    private final Iterable<RankedProvider<ContainerResponseFilter>> responseFilters;

    ContainerFilteringStage(Iterable<RankedProvider<ContainerRequestFilter>> requestFilters, Iterable<RankedProvider<ContainerResponseFilter>> responseFilters) {
        this.requestFilters = requestFilters;
        this.responseFilters = responseFilters;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Stage.Continuation<RequestProcessingContext> apply(RequestProcessingContext context) {
        int processedCount;
        long timestamp;
        ServerTraceEvent summaryEvent;
        TracingLogger tracingLogger;
        boolean postMatching;
        block14: {
            Stage.Continuation<RequestProcessingContext> continuation;
            block13: {
                Iterable<ContainerRequestFilter> sortedRequestFilters;
                postMatching = this.responseFilters == null;
                ContainerRequest request = context.request();
                tracingLogger = TracingLogger.getInstance(request);
                if (postMatching) {
                    ArrayList rankedProviders = new ArrayList(2);
                    rankedProviders.add(this.requestFilters);
                    rankedProviders.add(request.getRequestFilters());
                    sortedRequestFilters = Providers.mergeAndSortRankedProviders(new RankedComparator(), rankedProviders);
                    context.monitoringEventBuilder().setContainerRequestFilters(sortedRequestFilters);
                    context.triggerEvent(RequestEvent.Type.REQUEST_MATCHED);
                } else {
                    context.push(new ResponseFilterStage(context, this.responseFilters, tracingLogger));
                    sortedRequestFilters = Providers.sortRankedProviders(new RankedComparator(), this.requestFilters);
                }
                summaryEvent = postMatching ? ServerTraceEvent.REQUEST_FILTER_SUMMARY : ServerTraceEvent.PRE_MATCH_SUMMARY;
                timestamp = tracingLogger.timestamp(summaryEvent);
                processedCount = 0;
                try {
                    ServerTraceEvent filterEvent = postMatching ? ServerTraceEvent.REQUEST_FILTER : ServerTraceEvent.PRE_MATCH;
                    for (ContainerRequestFilter filter : sortedRequestFilters) {
                        long filterTimestamp = tracingLogger.timestamp(filterEvent);
                        try {
                            filter.filter(request);
                            ++processedCount;
                        }
                        catch (Exception exception) {
                            try {
                                throw new MappableException(exception);
                            }
                            catch (Throwable throwable) {
                                ++processedCount;
                                tracingLogger.logDuration(filterEvent, filterTimestamp, filter);
                                throw throwable;
                            }
                        }
                        tracingLogger.logDuration(filterEvent, filterTimestamp, filter);
                        final Response abortResponse = request.getAbortResponse();
                        if (abortResponse == null) continue;
                        continuation = Stage.Continuation.of(context, Stages.asStage(new Endpoint(){

                            @Override
                            public ContainerResponse apply(RequestProcessingContext requestContext) {
                                return new ContainerResponse(requestContext.request(), abortResponse);
                            }
                        }));
                        if (postMatching) {
                            context.triggerEvent(RequestEvent.Type.REQUEST_FILTERED);
                        }
                        break block13;
                    }
                    break block14;
                }
                catch (Throwable throwable) {
                    if (postMatching) {
                        context.triggerEvent(RequestEvent.Type.REQUEST_FILTERED);
                    }
                    tracingLogger.logDuration(summaryEvent, timestamp, processedCount);
                    throw throwable;
                }
            }
            tracingLogger.logDuration(summaryEvent, timestamp, processedCount);
            return continuation;
        }
        if (postMatching) {
            context.triggerEvent(RequestEvent.Type.REQUEST_FILTERED);
        }
        tracingLogger.logDuration(summaryEvent, timestamp, processedCount);
        return Stage.Continuation.of(context, this.getDefaultNext());
    }

    private static class ResponseFilterStage
    extends AbstractChainableStage<ContainerResponse> {
        private final RequestProcessingContext processingContext;
        private final Iterable<RankedProvider<ContainerResponseFilter>> filters;
        private final TracingLogger tracingLogger;

        private ResponseFilterStage(RequestProcessingContext processingContext, Iterable<RankedProvider<ContainerResponseFilter>> filters, TracingLogger tracingLogger) {
            this.processingContext = processingContext;
            this.filters = filters;
            this.tracingLogger = tracingLogger;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Stage.Continuation<ContainerResponse> apply(ContainerResponse responseContext) {
            ArrayList rankedProviders = new ArrayList(2);
            rankedProviders.add(this.filters);
            rankedProviders.add(responseContext.getRequestContext().getResponseFilters());
            Iterable<ContainerResponseFilter> sortedResponseFilters = Providers.mergeAndSortRankedProviders(new RankedComparator(RankedComparator.Order.DESCENDING), rankedProviders);
            ContainerRequest request = responseContext.getRequestContext();
            this.processingContext.monitoringEventBuilder().setContainerResponseFilters(sortedResponseFilters);
            this.processingContext.triggerEvent(RequestEvent.Type.RESP_FILTERS_START);
            long timestamp = this.tracingLogger.timestamp(ServerTraceEvent.RESPONSE_FILTER_SUMMARY);
            int processedCount = 0;
            try {
                for (ContainerResponseFilter filter : sortedResponseFilters) {
                    long filterTimestamp = this.tracingLogger.timestamp(ServerTraceEvent.RESPONSE_FILTER);
                    try {
                        filter.filter(request, responseContext);
                        ++processedCount;
                    }
                    catch (Exception ex) {
                        try {
                            throw new MappableException(ex);
                        }
                        catch (Throwable throwable) {
                            ++processedCount;
                            this.tracingLogger.logDuration(ServerTraceEvent.RESPONSE_FILTER, filterTimestamp, filter);
                            throw throwable;
                        }
                    }
                    this.tracingLogger.logDuration(ServerTraceEvent.RESPONSE_FILTER, filterTimestamp, filter);
                }
                this.processingContext.triggerEvent(RequestEvent.Type.RESP_FILTERS_FINISHED);
            }
            catch (Throwable throwable) {
                this.processingContext.triggerEvent(RequestEvent.Type.RESP_FILTERS_FINISHED);
                this.tracingLogger.logDuration(ServerTraceEvent.RESPONSE_FILTER_SUMMARY, timestamp, processedCount);
                throw throwable;
            }
            this.tracingLogger.logDuration(ServerTraceEvent.RESPONSE_FILTER_SUMMARY, timestamp, processedCount);
            return Stage.Continuation.of(responseContext, this.getDefaultNext());
        }
    }
}

