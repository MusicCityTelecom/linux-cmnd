/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.utilities;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.glassfish.hk2.api.DescriptorVisibility;
import org.glassfish.hk2.api.Injectee;
import org.glassfish.hk2.api.JustInTimeInjectionResolver;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.api.Visibility;
import org.glassfish.hk2.utilities.GreedyDefaultImplementation;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;

@Singleton
@Visibility(value=DescriptorVisibility.LOCAL)
public class GreedyResolver
implements JustInTimeInjectionResolver {
    private final ServiceLocator locator;

    @Inject
    private GreedyResolver(ServiceLocator locator) {
        this.locator = locator;
    }

    @Override
    public boolean justInTimeResolution(Injectee failedInjectionPoint) {
        Type rawType;
        Type type = failedInjectionPoint.getRequiredType();
        if (type == null) {
            return false;
        }
        Class<?> clazzToAdd = null;
        if (type instanceof Class) {
            clazzToAdd = (Class<?>)type;
        } else if (type instanceof ParameterizedType && (rawType = ((ParameterizedType)type).getRawType()) instanceof Class) {
            clazzToAdd = (Class)rawType;
        }
        if (clazzToAdd == null) {
            return false;
        }
        if (clazzToAdd.isInterface()) {
            GreedyDefaultImplementation gdi = clazzToAdd.getAnnotation(GreedyDefaultImplementation.class);
            if (gdi != null) {
                clazzToAdd = gdi.value();
            } else {
                return false;
            }
        }
        ServiceLocatorUtilities.addClasses(this.locator, clazzToAdd);
        return true;
    }
}

