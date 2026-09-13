/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.expression.EvaluationContext
 *  org.springframework.expression.Expression
 *  org.springframework.security.access.AccessDecisionVoter
 *  org.springframework.security.access.ConfigAttribute
 *  org.springframework.security.access.expression.ExpressionUtils
 *  org.springframework.security.access.expression.SecurityExpressionHandler
 *  org.springframework.security.core.Authentication
 *  org.springframework.util.Assert
 */
package org.springframework.security.web.access.expression;

import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.expression.ExpressionUtils;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionConfigAttribute;
import org.springframework.util.Assert;

public class WebExpressionVoter
implements AccessDecisionVoter<FilterInvocation> {
    private final Log logger = LogFactory.getLog(this.getClass());
    private SecurityExpressionHandler<FilterInvocation> expressionHandler = new DefaultWebSecurityExpressionHandler();

    public int vote(Authentication authentication, FilterInvocation filterInvocation, Collection<ConfigAttribute> attributes) {
        Assert.notNull((Object)authentication, (String)"authentication must not be null");
        Assert.notNull((Object)filterInvocation, (String)"filterInvocation must not be null");
        Assert.notNull(attributes, (String)"attributes must not be null");
        WebExpressionConfigAttribute webExpressionConfigAttribute = this.findConfigAttribute(attributes);
        if (webExpressionConfigAttribute == null) {
            this.logger.trace((Object)"Abstained since did not find a config attribute of instance WebExpressionConfigAttribute");
            return 0;
        }
        EvaluationContext ctx = webExpressionConfigAttribute.postProcess(this.expressionHandler.createEvaluationContext(authentication, (Object)filterInvocation), filterInvocation);
        boolean granted = ExpressionUtils.evaluateAsBoolean((Expression)webExpressionConfigAttribute.getAuthorizeExpression(), (EvaluationContext)ctx);
        if (granted) {
            return 1;
        }
        this.logger.trace((Object)"Voted to deny authorization");
        return -1;
    }

    private WebExpressionConfigAttribute findConfigAttribute(Collection<ConfigAttribute> attributes) {
        for (ConfigAttribute attribute : attributes) {
            if (!(attribute instanceof WebExpressionConfigAttribute)) continue;
            return (WebExpressionConfigAttribute)attribute;
        }
        return null;
    }

    public boolean supports(ConfigAttribute attribute) {
        return attribute instanceof WebExpressionConfigAttribute;
    }

    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }

    public void setExpressionHandler(SecurityExpressionHandler<FilterInvocation> expressionHandler) {
        this.expressionHandler = expressionHandler;
    }
}

