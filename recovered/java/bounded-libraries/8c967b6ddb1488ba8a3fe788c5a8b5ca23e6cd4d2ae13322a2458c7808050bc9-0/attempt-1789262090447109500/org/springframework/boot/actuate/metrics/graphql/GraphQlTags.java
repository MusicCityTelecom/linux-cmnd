/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  graphql.ErrorClassification
 *  graphql.ErrorType
 *  graphql.ExecutionResult
 *  graphql.GraphQLError
 *  graphql.execution.ExecutionStepInfo
 *  graphql.execution.instrumentation.parameters.InstrumentationFieldFetchParameters
 *  graphql.schema.GraphQLObjectType
 *  io.micrometer.core.instrument.Tag
 *  org.springframework.util.CollectionUtils
 */
package org.springframework.boot.actuate.metrics.graphql;

import graphql.ErrorClassification;
import graphql.ErrorType;
import graphql.ExecutionResult;
import graphql.GraphQLError;
import graphql.execution.ExecutionStepInfo;
import graphql.execution.instrumentation.parameters.InstrumentationFieldFetchParameters;
import graphql.schema.GraphQLObjectType;
import io.micrometer.core.instrument.Tag;
import java.util.Collection;
import java.util.List;
import org.springframework.util.CollectionUtils;

public final class GraphQlTags {
    private static final Tag OUTCOME_SUCCESS = Tag.of((String)"outcome", (String)"SUCCESS");
    private static final Tag OUTCOME_ERROR = Tag.of((String)"outcome", (String)"ERROR");
    private static final Tag UNKNOWN_ERROR_TYPE = Tag.of((String)"error.type", (String)"UNKNOWN");

    private GraphQlTags() {
    }

    public static Tag executionOutcome(ExecutionResult result, Throwable exception) {
        if (exception == null && result.getErrors().isEmpty()) {
            return OUTCOME_SUCCESS;
        }
        return OUTCOME_ERROR;
    }

    public static Tag errorType(GraphQLError error) {
        ErrorClassification errorType = error.getErrorType();
        if (errorType instanceof ErrorType) {
            return Tag.of((String)"error.type", (String)((ErrorType)errorType).name());
        }
        return UNKNOWN_ERROR_TYPE;
    }

    public static Tag errorPath(GraphQLError error) {
        StringBuilder builder = new StringBuilder();
        List pathSegments = error.getPath();
        if (!CollectionUtils.isEmpty((Collection)pathSegments)) {
            builder.append('$');
            for (Object segment : pathSegments) {
                try {
                    Integer.parseUnsignedInt(segment.toString());
                    builder.append("[*]");
                }
                catch (NumberFormatException exc) {
                    builder.append('.');
                    builder.append(segment);
                }
            }
        }
        return Tag.of((String)"error.path", (String)builder.toString());
    }

    public static Tag dataFetchingOutcome(Throwable exception) {
        return exception != null ? OUTCOME_ERROR : OUTCOME_SUCCESS;
    }

    public static Tag dataFetchingPath(InstrumentationFieldFetchParameters parameters) {
        ExecutionStepInfo executionStepInfo = parameters.getExecutionStepInfo();
        StringBuilder dataFetchingPath = new StringBuilder();
        if (executionStepInfo.hasParent() && executionStepInfo.getParent().getType() instanceof GraphQLObjectType) {
            dataFetchingPath.append(((GraphQLObjectType)executionStepInfo.getParent().getType()).getName());
            dataFetchingPath.append('.');
        }
        dataFetchingPath.append(executionStepInfo.getPath().getSegmentName());
        return Tag.of((String)"path", (String)dataFetchingPath.toString());
    }
}

