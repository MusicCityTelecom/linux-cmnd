/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.inject.hk2;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.Descriptor;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.api.ServiceLocatorFactory;
import org.glassfish.hk2.utilities.Binder;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.glassfish.jersey.inject.hk2.Hk2BootstrapBinder;
import org.glassfish.jersey.inject.hk2.Hk2Helper;
import org.glassfish.jersey.inject.hk2.LocalizationMessages;
import org.glassfish.jersey.internal.inject.Binding;
import org.glassfish.jersey.internal.inject.ClassBinding;
import org.glassfish.jersey.internal.inject.ForeignDescriptor;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.internal.inject.InstanceBinding;
import org.glassfish.jersey.internal.inject.ServiceHolder;
import org.glassfish.jersey.internal.inject.ServiceHolderImpl;
import org.jvnet.hk2.external.runtime.ServiceLocatorRuntimeBean;

abstract class AbstractHk2InjectionManager
implements InjectionManager {
    private static final Logger LOGGER = Logger.getLogger(AbstractHk2InjectionManager.class.getName());
    private static final ServiceLocatorFactory factory = ServiceLocatorFactory.getInstance();
    private ServiceLocator locator;

    AbstractHk2InjectionManager(Object parent) {
        ServiceLocator parentLocator = AbstractHk2InjectionManager.resolveServiceLocatorParent(parent);
        this.locator = AbstractHk2InjectionManager.createLocator(parentLocator);
        ServiceLocatorUtilities.bind(this.locator, new Hk2BootstrapBinder(this.locator));
        this.locator.setDefaultClassAnalyzerName("JerseyClassAnalyzer");
        ServiceLocatorRuntimeBean serviceLocatorRuntimeBean = this.locator.getService(ServiceLocatorRuntimeBean.class, new Annotation[0]);
        if (serviceLocatorRuntimeBean != null) {
            if (LOGGER.isLoggable(Level.FINE)) {
                LOGGER.fine(LocalizationMessages.HK_2_CLEARING_CACHE(serviceLocatorRuntimeBean.getServiceCacheSize(), serviceLocatorRuntimeBean.getReflectionCacheSize()));
            }
            serviceLocatorRuntimeBean.clearReflectionCache();
            serviceLocatorRuntimeBean.clearServiceCache();
        }
    }

    private static ServiceLocator createLocator(ServiceLocator parentLocator) {
        ServiceLocator result = factory.create(null, parentLocator, null, ServiceLocatorFactory.CreatePolicy.DESTROY);
        result.setNeutralContextClassLoader(false);
        ServiceLocatorUtilities.enablePerThreadScope(result);
        return result;
    }

    private static ServiceLocator resolveServiceLocatorParent(Object parent) {
        AbstractHk2InjectionManager.assertParentLocatorType(parent);
        ServiceLocator parentLocator = null;
        if (parent != null) {
            if (parent instanceof ServiceLocator) {
                parentLocator = (ServiceLocator)parent;
            } else if (parent instanceof AbstractHk2InjectionManager) {
                parentLocator = ((AbstractHk2InjectionManager)parent).getServiceLocator();
            }
        }
        return parentLocator;
    }

    private static void assertParentLocatorType(Object parent) {
        if (parent != null && !(parent instanceof ServiceLocator) && !(parent instanceof AbstractHk2InjectionManager)) {
            throw new IllegalArgumentException(LocalizationMessages.HK_2_UNKNOWN_PARENT_INJECTION_MANAGER(parent.getClass().getSimpleName()));
        }
    }

    public ServiceLocator getServiceLocator() {
        return this.locator;
    }

    @Override
    public boolean isRegistrable(Class<?> clazz) {
        return Binder.class.isAssignableFrom(clazz);
    }

    @Override
    public <T> List<ServiceHolder<T>> getAllServiceHolders(Class<T> contract, Annotation ... qualifiers) {
        return this.getServiceLocator().getAllServiceHandles(contract, qualifiers).stream().map(sh -> new ServiceHolderImpl(sh.getService(), sh.getActiveDescriptor().getImplementationClass(), sh.getActiveDescriptor().getContractTypes(), sh.getActiveDescriptor().getRanking())).collect(Collectors.toList());
    }

    @Override
    public <T> T getInstance(Class<T> clazz, Annotation ... annotations) {
        return this.getServiceLocator().getService(clazz, annotations);
    }

    @Override
    public <T> T getInstance(Type clazz) {
        return this.getServiceLocator().getService(clazz, new Annotation[0]);
    }

    @Override
    public Object getInstance(ForeignDescriptor foreignDescriptor) {
        return this.getServiceLocator().getServiceHandle((ActiveDescriptor)foreignDescriptor.get()).getService();
    }

    @Override
    public <T> T getInstance(Class<T> clazz) {
        return this.getServiceLocator().getService(clazz, new Annotation[0]);
    }

    @Override
    public <T> T getInstance(Class<T> clazz, String classAnalyzer) {
        return this.getServiceLocator().getService(clazz, classAnalyzer, new Annotation[0]);
    }

    @Override
    public <T> List<T> getAllInstances(Type clazz) {
        return this.getServiceLocator().getAllServices(clazz, new Annotation[0]);
    }

    @Override
    public void preDestroy(Object preDestroyMe) {
        this.getServiceLocator().preDestroy(preDestroyMe);
    }

    @Override
    public void shutdown() {
        if (factory.find(this.getServiceLocator().getName()) != null) {
            factory.destroy(this.getServiceLocator().getName());
        } else {
            this.getServiceLocator().shutdown();
        }
    }

    public <U> U createAndInitialize(Class<U> clazz) {
        return this.getServiceLocator().createAndInitialize(clazz);
    }

    @Override
    public ForeignDescriptor createForeignDescriptor(Binding binding) {
        ForeignDescriptor foreignDescriptor = this.createAndTranslateForeignDescriptor(binding);
        ActiveDescriptor activeDescriptor = ServiceLocatorUtilities.addOneDescriptor(this.getServiceLocator(), (Descriptor)foreignDescriptor.get(), false);
        return ForeignDescriptor.wrap(activeDescriptor, activeDescriptor::dispose);
    }

    @Override
    public void inject(Object injectMe) {
        this.getServiceLocator().inject(injectMe);
    }

    @Override
    public void inject(Object injectMe, String classAnalyzer) {
        this.getServiceLocator().inject(injectMe, classAnalyzer);
    }

    private ForeignDescriptor createAndTranslateForeignDescriptor(Binding binding) {
        ActiveDescriptor<?> activeDescriptor;
        if (ClassBinding.class.isAssignableFrom(binding.getClass())) {
            activeDescriptor = Hk2Helper.translateToActiveDescriptor((ClassBinding)binding);
        } else if (InstanceBinding.class.isAssignableFrom(binding.getClass())) {
            activeDescriptor = Hk2Helper.translateToActiveDescriptor((InstanceBinding)binding, new Type[0]);
        } else {
            throw new RuntimeException(org.glassfish.jersey.internal.LocalizationMessages.UNKNOWN_DESCRIPTOR_TYPE(binding.getClass().getSimpleName()));
        }
        return ForeignDescriptor.wrap(activeDescriptor, activeDescriptor::dispose);
    }
}

