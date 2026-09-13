/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.hibernate.validator.HibernateValidator
 */
package org.glassfish.hk2.utilities.general;

import java.lang.annotation.ElementType;
import java.security.AccessController;
import java.security.PrivilegedAction;
import javax.validation.Path;
import javax.validation.TraversableResolver;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorContext;
import javax.validation.ValidatorFactory;
import org.glassfish.hk2.utilities.general.internal.MessageInterpolatorImpl;
import org.hibernate.validator.HibernateValidator;

public class ValidatorUtilities {
    private static final TraversableResolver TRAVERSABLE_RESOLVER = new TraversableResolver(){

        @Override
        public boolean isReachable(Object traversableObject, Path.Node traversableProperty, Class<?> rootBeanType, Path pathToTraversableObject, ElementType elementType) {
            return true;
        }

        @Override
        public boolean isCascadable(Object traversableObject, Path.Node traversableProperty, Class<?> rootBeanType, Path pathToTraversableObject, ElementType elementType) {
            return true;
        }
    };
    private static Validator validator;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static Validator initializeValidator() {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(HibernateValidator.class.getClassLoader());
            ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
            ValidatorContext validatorContext = validatorFactory.usingContext();
            validatorContext.messageInterpolator(new MessageInterpolatorImpl());
            Validator validator = validatorContext.traversableResolver(TRAVERSABLE_RESOLVER).getValidator();
            return validator;
        }
        finally {
            Thread.currentThread().setContextClassLoader(cl);
        }
    }

    public static synchronized Validator getValidator() {
        if (validator == null) {
            validator = AccessController.doPrivileged(new PrivilegedAction<Validator>(){

                @Override
                public Validator run() {
                    return ValidatorUtilities.initializeValidator();
                }
            });
        }
        if (validator == null) {
            throw new IllegalStateException("Could not find a javax.validator");
        }
        return validator;
    }
}

