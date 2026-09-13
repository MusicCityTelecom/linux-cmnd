/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  graphql.ExecutionResult
 *  graphql.GraphQLError
 *  graphql.execution.instrumentation.parameters.InstrumentationExecutionParameters
 *  graphql.execution.instrumentation.parameters.InstrumentationFieldFetchParameters
 *  graphql.schema.DataFetcher
 *  io.micrometer.core.instrument.Tag
 */
package org.springframework.boot.actuate.metrics.graphql;

import graphql.ExecutionResult;
import graphql.GraphQLError;
import graphql.execution.instrumentation.parameters.InstrumentationExecutionParameters;
import graphql.execution.instrumentation.parameters.InstrumentationFieldFetchParameters;
import graphql.schema.DataFetcher;
import io.micrometer.core.instrument.Tag;

public interface GraphQlTagsContributor {
    public Iterable<Tag> getExecutionTags(InstrumentationExecutionParameters var1, ExecutionResult var2, Throwable var3);

    public Iterable<Tag> getErrorTags(InstrumentationExecutionParameters var1, GraphQLError var2);

    public Iterable<Tag> getDataFetchingTags(DataFetcher<?> var1, InstrumentationFieldFetchParameters var2, Throwable var3);
}

