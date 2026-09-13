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
 *  io.micrometer.core.instrument.Tags
 */
package org.springframework.boot.actuate.metrics.graphql;

import graphql.ExecutionResult;
import graphql.GraphQLError;
import graphql.execution.instrumentation.parameters.InstrumentationExecutionParameters;
import graphql.execution.instrumentation.parameters.InstrumentationFieldFetchParameters;
import graphql.schema.DataFetcher;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import java.util.Collections;
import java.util.List;
import org.springframework.boot.actuate.metrics.graphql.GraphQlTags;
import org.springframework.boot.actuate.metrics.graphql.GraphQlTagsContributor;
import org.springframework.boot.actuate.metrics.graphql.GraphQlTagsProvider;

public class DefaultGraphQlTagsProvider
implements GraphQlTagsProvider {
    private final List<GraphQlTagsContributor> contributors;

    public DefaultGraphQlTagsProvider(List<GraphQlTagsContributor> contributors) {
        this.contributors = contributors;
    }

    public DefaultGraphQlTagsProvider() {
        this(Collections.emptyList());
    }

    @Override
    public Iterable<Tag> getExecutionTags(InstrumentationExecutionParameters parameters, ExecutionResult result, Throwable exception) {
        Tags tags = Tags.of((Tag[])new Tag[]{GraphQlTags.executionOutcome(result, exception)});
        for (GraphQlTagsContributor contributor : this.contributors) {
            tags = tags.and(contributor.getExecutionTags(parameters, result, exception));
        }
        return tags;
    }

    @Override
    public Iterable<Tag> getErrorTags(InstrumentationExecutionParameters parameters, GraphQLError error) {
        Tags tags = Tags.of((Tag[])new Tag[]{GraphQlTags.errorType(error), GraphQlTags.errorPath(error)});
        for (GraphQlTagsContributor contributor : this.contributors) {
            tags = tags.and(contributor.getErrorTags(parameters, error));
        }
        return tags;
    }

    @Override
    public Iterable<Tag> getDataFetchingTags(DataFetcher<?> dataFetcher, InstrumentationFieldFetchParameters parameters, Throwable exception) {
        Tags tags = Tags.of((Tag[])new Tag[]{GraphQlTags.dataFetchingOutcome(exception), GraphQlTags.dataFetchingPath(parameters)});
        for (GraphQlTagsContributor contributor : this.contributors) {
            tags = tags.and(contributor.getDataFetchingTags(dataFetcher, parameters, exception));
        }
        return tags;
    }
}

