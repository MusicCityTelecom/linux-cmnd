/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.DefaultParameterNameDiscoverer
 *  org.springframework.core.MethodParameter
 *  org.springframework.core.ParameterNameDiscoverer
 *  org.springframework.core.ResolvableType
 *  org.springframework.lang.Nullable
 *  org.springframework.util.ObjectUtils
 */
package org.springframework.messaging.handler.invocation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.MethodParameter;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.ResolvableType;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.HandlerMethod;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolverComposite;
import org.springframework.messaging.handler.invocation.MethodArgumentResolutionException;
import org.springframework.util.ObjectUtils;

public class InvocableHandlerMethod
extends HandlerMethod {
    private static final Object[] EMPTY_ARGS = new Object[0];
    private HandlerMethodArgumentResolverComposite resolvers = new HandlerMethodArgumentResolverComposite();
    private ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();

    public InvocableHandlerMethod(HandlerMethod handlerMethod) {
        super(handlerMethod);
    }

    public InvocableHandlerMethod(Object bean, Method method) {
        super(bean, method);
    }

    public InvocableHandlerMethod(Object bean, String methodName, Class<?> ... parameterTypes) throws NoSuchMethodException {
        super(bean, methodName, parameterTypes);
    }

    public void setMessageMethodArgumentResolvers(HandlerMethodArgumentResolverComposite argumentResolvers) {
        this.resolvers = argumentResolvers;
    }

    public void setParameterNameDiscoverer(ParameterNameDiscoverer parameterNameDiscoverer) {
        this.parameterNameDiscoverer = parameterNameDiscoverer;
    }

    @Nullable
    public Object invoke(Message<?> message, Object ... providedArgs) throws Exception {
        Object[] args = this.getMethodArgumentValues(message, providedArgs);
        if (this.logger.isTraceEnabled()) {
            this.logger.trace((Object)("Arguments: " + Arrays.toString(args)));
        }
        return this.doInvoke(args);
    }

    protected Object[] getMethodArgumentValues(Message<?> message, Object ... providedArgs) throws Exception {
        Object[] parameters = this.getMethodParameters();
        if (ObjectUtils.isEmpty((Object[])parameters)) {
            return EMPTY_ARGS;
        }
        Object[] args = new Object[parameters.length];
        for (int i2 = 0; i2 < parameters.length; ++i2) {
            Object parameter = parameters[i2];
            parameter.initParameterNameDiscovery(this.parameterNameDiscoverer);
            args[i2] = InvocableHandlerMethod.findProvidedArgument((MethodParameter)parameter, providedArgs);
            if (args[i2] != null) continue;
            if (!this.resolvers.supportsParameter((MethodParameter)parameter)) {
                throw new MethodArgumentResolutionException(message, (MethodParameter)parameter, InvocableHandlerMethod.formatArgumentError((MethodParameter)parameter, "No suitable resolver"));
            }
            try {
                args[i2] = this.resolvers.resolveArgument((MethodParameter)parameter, message);
                continue;
            }
            catch (Exception ex) {
                String exMsg;
                if (this.logger.isDebugEnabled() && (exMsg = ex.getMessage()) != null && !exMsg.contains(parameter.getExecutable().toGenericString())) {
                    this.logger.debug((Object)InvocableHandlerMethod.formatArgumentError((MethodParameter)parameter, exMsg));
                }
                throw ex;
            }
        }
        return args;
    }

    @Nullable
    protected Object doInvoke(Object ... args) throws Exception {
        try {
            return this.getBridgedMethod().invoke(this.getBean(), args);
        }
        catch (IllegalArgumentException ex) {
            this.assertTargetBean(this.getBridgedMethod(), this.getBean(), args);
            String text = ex.getMessage() != null ? ex.getMessage() : "Illegal argument";
            throw new IllegalStateException(this.formatInvokeError(text, args), ex);
        }
        catch (InvocationTargetException ex) {
            Throwable targetException = ex.getTargetException();
            if (targetException instanceof RuntimeException) {
                throw (RuntimeException)targetException;
            }
            if (targetException instanceof Error) {
                throw (Error)targetException;
            }
            if (targetException instanceof Exception) {
                throw (Exception)targetException;
            }
            throw new IllegalStateException(this.formatInvokeError("Invocation failure", args), targetException);
        }
    }

    MethodParameter getAsyncReturnValueType(@Nullable Object returnValue) {
        return new AsyncResultMethodParameter(returnValue);
    }

    private class AsyncResultMethodParameter
    extends HandlerMethod.HandlerMethodParameter {
        @Nullable
        private final Object returnValue;
        private final ResolvableType returnType;

        public AsyncResultMethodParameter(Object returnValue) {
            super(-1);
            this.returnValue = returnValue;
            this.returnType = ResolvableType.forType((Type)super.getGenericParameterType()).getGeneric(new int[0]);
        }

        protected AsyncResultMethodParameter(AsyncResultMethodParameter original) {
            super(original);
            this.returnValue = original.returnValue;
            this.returnType = original.returnType;
        }

        public Class<?> getParameterType() {
            if (this.returnValue != null) {
                return this.returnValue.getClass();
            }
            if (!ResolvableType.NONE.equals((Object)this.returnType)) {
                return this.returnType.toClass();
            }
            return super.getParameterType();
        }

        public Type getGenericParameterType() {
            return this.returnType.getType();
        }

        @Override
        public AsyncResultMethodParameter clone() {
            return new AsyncResultMethodParameter(this);
        }
    }
}

