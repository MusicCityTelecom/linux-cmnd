/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  org.springframework.expression.EvaluationContext
 *  org.springframework.expression.Expression
 *  org.springframework.expression.spel.standard.SpelExpressionParser
 *  org.springframework.expression.spel.support.StandardEvaluationContext
 */
package org.springframework.security.web.util.matcher;

import javax.servlet.http.HttpServletRequest;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.web.util.matcher.ELRequestMatcherContext;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class ELRequestMatcher
implements RequestMatcher {
    private final Expression expression;

    public ELRequestMatcher(String el) {
        SpelExpressionParser parser = new SpelExpressionParser();
        this.expression = parser.parseExpression(el);
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        EvaluationContext context = this.createELContext(request);
        return (Boolean)this.expression.getValue(context, Boolean.class);
    }

    public EvaluationContext createELContext(HttpServletRequest request) {
        return new StandardEvaluationContext((Object)new ELRequestMatcherContext(request));
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("EL [el=\"").append(this.expression.getExpressionString()).append("\"");
        sb.append("]");
        return sb.toString();
    }
}

