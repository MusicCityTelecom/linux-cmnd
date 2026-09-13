/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanNotOfRequiredTypeException
 */
package org.springframework.boot.diagnostics.analyzer;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Proxy;
import org.springframework.beans.factory.BeanNotOfRequiredTypeException;
import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;

public class BeanNotOfRequiredTypeFailureAnalyzer
extends AbstractFailureAnalyzer<BeanNotOfRequiredTypeException> {
    private static final String ACTION = "Consider injecting the bean as one of its interfaces or forcing the use of CGLib-based proxies by setting proxyTargetClass=true on @EnableAsync and/or @EnableCaching.";

    @Override
    protected FailureAnalysis analyze(Throwable rootFailure, BeanNotOfRequiredTypeException cause) {
        if (!Proxy.isProxyClass(cause.getActualType())) {
            return null;
        }
        return new FailureAnalysis(this.getDescription(cause), ACTION, (Throwable)cause);
    }

    private String getDescription(BeanNotOfRequiredTypeException ex) {
        StringWriter description = new StringWriter();
        PrintWriter printer = new PrintWriter(description);
        printer.printf("The bean '%s' could not be injected because it is a JDK dynamic proxy%n%n", ex.getBeanName());
        printer.printf("The bean is of type '%s' and implements:%n", ex.getActualType().getName());
        for (Class<?> actualTypeInterface : ex.getActualType().getInterfaces()) {
            printer.println("\t" + actualTypeInterface.getName());
        }
        printer.printf("%nExpected a bean of type '%s' which implements:%n", ex.getRequiredType().getName());
        for (Class<?> requiredTypeInterface : ex.getRequiredType().getInterfaces()) {
            printer.println("\t" + requiredTypeInterface.getName());
        }
        return description.toString();
    }
}

