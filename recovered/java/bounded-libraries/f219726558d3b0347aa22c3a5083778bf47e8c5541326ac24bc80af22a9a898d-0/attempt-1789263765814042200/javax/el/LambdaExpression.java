/*
 * Decompiled with CFR 0.152.
 */
package javax.el;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ValueExpression;

public class LambdaExpression {
    private List<String> formalParameters = new ArrayList<String>();
    private ValueExpression expression;
    private ELContext context;
    private Map<String, Object> envirArgs = null;

    public LambdaExpression(List<String> formalParameters, ValueExpression expression) {
        this.formalParameters = formalParameters;
        this.expression = expression;
        this.envirArgs = new HashMap<String, Object>();
    }

    public void setELContext(ELContext context) {
        this.context = context;
    }

    public Object invoke(ELContext elContext, Object ... args) throws ELException {
        int i = 0;
        HashMap<String, Object> lambdaArgs = new HashMap<String, Object>();
        lambdaArgs.putAll(this.envirArgs);
        for (String fParam : this.formalParameters) {
            if (i >= args.length) {
                throw new ELException("Expected Argument " + fParam + " missing in Lambda Expression");
            }
            lambdaArgs.put(fParam, args[i++]);
        }
        elContext.enterLambdaScope(lambdaArgs);
        Object ret = this.expression.getValue(elContext);
        if (ret instanceof LambdaExpression) {
            ((LambdaExpression)ret).envirArgs.putAll(lambdaArgs);
        }
        elContext.exitLambdaScope();
        return ret;
    }

    public Object invoke(Object ... args) {
        return this.invoke(this.context, args);
    }
}

