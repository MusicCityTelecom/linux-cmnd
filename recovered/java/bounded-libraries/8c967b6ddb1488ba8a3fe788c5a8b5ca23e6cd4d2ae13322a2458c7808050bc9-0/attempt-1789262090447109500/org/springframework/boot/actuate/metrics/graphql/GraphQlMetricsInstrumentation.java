/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  graphql.ExecutionResult
 *  graphql.GraphQLError
 *  graphql.execution.instrumentation.InstrumentationContext
 *  graphql.execution.instrumentation.InstrumentationState
 *  graphql.execution.instrumentation.SimpleInstrumentation
 *  graphql.execution.instrumentation.SimpleInstrumentationContext
 *  graphql.execution.instrumentation.parameters.InstrumentationExecutionParameters
 *  graphql.execution.instrumentation.parameters.InstrumentationFieldFetchParameters
 *  graphql.schema.DataFetcher
 *  io.micrometer.core.instrument.DistributionSummary
 *  io.micrometer.core.instrument.MeterRegistry
 *  io.micrometer.core.instrument.Tag
 *  io.micrometer.core.instrument.Timer
 *  io.micrometer.core.instrument.Timer$Builder
 *  io.micrometer.core.instrument.Timer$Sample
 */
package org.springframework.boot.actuate.metrics.graphql;

import graphql.ExecutionResult;
import graphql.GraphQLError;
import graphql.execution.instrumentation.InstrumentationContext;
import graphql.execution.instrumentation.InstrumentationState;
import graphql.execution.instrumentation.SimpleInstrumentation;
import graphql.execution.instrumentation.SimpleInstrumentationContext;
import graphql.execution.instrumentation.parameters.InstrumentationExecutionParameters;
import graphql.execution.instrumentation.parameters.InstrumentationFieldFetchParameters;
import graphql.schema.DataFetcher;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Timer;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.boot.actuate.metrics.AutoTimer;
import org.springframework.boot.actuate.metrics.graphql.GraphQlTagsProvider;

public class GraphQlMetricsInstrumentation
extends SimpleInstrumentation {
    private final MeterRegistry registry;
    private final GraphQlTagsProvider tagsProvider;
    private final AutoTimer autoTimer;
    private final DistributionSummary dataFetchingSummary;

    public GraphQlMetricsInstrumentation(MeterRegistry registry, GraphQlTagsProvider tagsProvider, AutoTimer autoTimer) {
        this.registry = registry;
        this.tagsProvider = tagsProvider;
        this.autoTimer = autoTimer;
        this.dataFetchingSummary = DistributionSummary.builder((String)"graphql.request.datafetch.count").baseUnit("calls").description("Count of DataFetcher calls per request.").register(this.registry);
    }

    public InstrumentationState createState() {
        return new RequestMetricsInstrumentationState(this.autoTimer, this.registry);
    }

    public InstrumentationContext<ExecutionResult> beginExecution(final InstrumentationExecutionParameters parameters) {
        if (this.autoTimer.isEnabled()) {
            final RequestMetricsInstrumentationState state = (RequestMetricsInstrumentationState)parameters.getInstrumentationState();
            state.startTimer();
            return new SimpleInstrumentationContext<ExecutionResult>(){

                public void onCompleted(ExecutionResult result, Throwable exc) {
                    Iterable<Tag> tags = GraphQlMetricsInstrumentation.this.tagsProvider.getExecutionTags(parameters, result, exc);
                    state.tags(tags).stopTimer();
                    if (!result.getErrors().isEmpty()) {
                        result.getErrors().forEach(error -> GraphQlMetricsInstrumentation.this.registry.counter("graphql.error", GraphQlMetricsInstrumentation.this.tagsProvider.getErrorTags(parameters, (GraphQLError)error)).increment());
                    }
                    GraphQlMetricsInstrumentation.this.dataFetchingSummary.record((double)state.getDataFetchingCount());
                }
            };
        }
        return super.beginExecution(parameters);
    }

    public DataFetcher<?> instrumentDataFetcher(DataFetcher<?> dataFetcher, InstrumentationFieldFetchParameters parameters) {
        if (this.autoTimer.isEnabled() && !parameters.isTrivialDataFetcher()) {
            return environment -> {
                Timer.Sample sample = Timer.start((MeterRegistry)this.registry);
                try {
                    Object value = dataFetcher.get(environment);
                    if (value instanceof CompletionStage) {
                        CompletionStage completion = (CompletionStage)value;
                        return completion.whenComplete((result, error) -> this.recordDataFetcherMetric(sample, dataFetcher, parameters, (Throwable)error));
                    }
                    this.recordDataFetcherMetric(sample, dataFetcher, parameters, null);
                    return value;
                }
                catch (Throwable throwable) {
                    this.recordDataFetcherMetric(sample, dataFetcher, parameters, throwable);
                    throw throwable;
                }
            };
        }
        return super.instrumentDataFetcher(dataFetcher, parameters);
    }

    private void recordDataFetcherMetric(Timer.Sample sample, DataFetcher<?> dataFetcher, InstrumentationFieldFetchParameters parameters, Throwable throwable) {
        Timer.Builder timer = this.autoTimer.builder("graphql.datafetcher");
        timer.tags(this.tagsProvider.getDataFetchingTags(dataFetcher, parameters, throwable));
        sample.stop(timer.register(this.registry));
        RequestMetricsInstrumentationState state = (RequestMetricsInstrumentationState)parameters.getInstrumentationState();
        state.incrementDataFetchingCount();
    }

    static class RequestMetricsInstrumentationState
    implements InstrumentationState {
        private final MeterRegistry registry;
        private final Timer.Builder timer;
        private Timer.Sample sample;
        private final AtomicLong dataFetchingCount = new AtomicLong();

        RequestMetricsInstrumentationState(AutoTimer autoTimer, MeterRegistry registry) {
            this.timer = autoTimer.builder("graphql.request");
            this.registry = registry;
        }

        RequestMetricsInstrumentationState tags(Iterable<Tag> tags) {
            this.timer.tags(tags);
            return this;
        }

        void startTimer() {
            this.sample = Timer.start((MeterRegistry)this.registry);
        }

        void stopTimer() {
            this.sample.stop(this.timer.register(this.registry));
        }

        void incrementDataFetchingCount() {
            this.dataFetchingCount.incrementAndGet();
        }

        long getDataFetchingCount() {
            return this.dataFetchingCount.get();
        }
    }
}

