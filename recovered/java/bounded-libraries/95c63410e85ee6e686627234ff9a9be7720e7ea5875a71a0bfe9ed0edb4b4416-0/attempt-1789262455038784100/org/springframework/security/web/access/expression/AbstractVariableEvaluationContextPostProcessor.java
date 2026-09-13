/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  org.springframework.expression.EvaluationContext
 */
package org.springframework.security.web.access.expression;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.expression.EvaluationContext;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DelegatingEvaluationContext;
import org.springframework.security.web.access.expression.EvaluationContextPostProcessor;

abstract class AbstractVariableEvaluationContextPostProcessor
implements EvaluationContextPostProcessor<FilterInvocation> {
    AbstractVariableEvaluationContextPostProcessor() {
    }

    @Override
    public final EvaluationContext postProcess(EvaluationContext context, FilterInvocation invocation) {
        return new VariableEvaluationContext(context, invocation.getHttpRequest());
    }

    abstract Map<String, String> extractVariables(HttpServletRequest var1);

    class VariableEvaluationContext
    extends DelegatingEvaluationContext {
        private final HttpServletRequest request;
        private Map<String, String> variables;

        VariableEvaluationContext(EvaluationContext delegate, HttpServletRequest request) {
            super(delegate);
            this.request = request;
        }

        @Override
        public Object lookupVariable(String name) {
            Object result = super.lookupVariable(name);
            if (result != null) {
                return result;
            }
            if (this.variables == null) {
                this.variables = AbstractVariableEvaluationContextPostProcessor.this.extractVariables(this.request);
            }
            return this.variables.get(name);
        }
    }
}

