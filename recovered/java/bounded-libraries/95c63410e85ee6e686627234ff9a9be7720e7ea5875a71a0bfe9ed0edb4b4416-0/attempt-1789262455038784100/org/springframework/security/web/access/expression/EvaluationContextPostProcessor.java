/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.expression.EvaluationContext
 */
package org.springframework.security.web.access.expression;

import org.springframework.expression.EvaluationContext;

interface EvaluationContextPostProcessor<I> {
    public EvaluationContext postProcess(EvaluationContext var1, I var2);
}

