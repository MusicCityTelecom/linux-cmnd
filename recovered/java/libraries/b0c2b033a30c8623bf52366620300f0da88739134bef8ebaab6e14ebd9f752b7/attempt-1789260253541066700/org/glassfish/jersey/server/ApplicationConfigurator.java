/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server;

import java.util.Collection;
import java.util.Collections;
import javax.inject.Singleton;
import javax.ws.rs.core.Application;
import org.glassfish.jersey.internal.BootstrapBag;
import org.glassfish.jersey.internal.BootstrapConfigurator;
import org.glassfish.jersey.internal.inject.Binding;
import org.glassfish.jersey.internal.inject.Bindings;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.internal.util.collection.Value;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerBootstrapBag;
import org.glassfish.jersey.server.spi.ComponentProvider;

class ApplicationConfigurator
implements BootstrapConfigurator {
    private Application application;
    private Class<? extends Application> applicationClass;

    ApplicationConfigurator(Application application) {
        this.application = application;
    }

    ApplicationConfigurator(Class<? extends Application> applicationClass) {
        this.applicationClass = applicationClass;
    }

    @Override
    public void init(InjectionManager injectionManager, BootstrapBag bootstrapBag) {
        Application resultApplication;
        ServerBootstrapBag serverBag = (ServerBootstrapBag)bootstrapBag;
        if (this.application != null) {
            ResourceConfig rc;
            if (this.application instanceof ResourceConfig && (rc = (ResourceConfig)this.application).getApplicationClass() != null) {
                rc.setApplication(ApplicationConfigurator.createApplication(injectionManager, rc.getApplicationClass(), serverBag.getComponentProviders()));
            }
            resultApplication = this.application;
        } else {
            resultApplication = ApplicationConfigurator.createApplication(injectionManager, this.applicationClass, serverBag.getComponentProviders());
        }
        serverBag.setApplication(resultApplication);
        injectionManager.register((Binding)Bindings.service(resultApplication).to(Application.class));
    }

    private static Application createApplication(InjectionManager injectionManager, Class<? extends Application> applicationClass, Value<Collection<ComponentProvider>> componentProvidersValue) {
        ResourceConfig _rc;
        Class<? extends Application> innerAppClass;
        Application app;
        if (applicationClass == ResourceConfig.class) {
            return new ResourceConfig();
        }
        if (applicationClass == Application.class) {
            return new Application();
        }
        Collection<ComponentProvider> componentProviders = componentProvidersValue.get();
        boolean appClassBound = false;
        for (ComponentProvider cp : componentProviders) {
            if (!cp.bind(applicationClass, Collections.emptySet())) continue;
            appClassBound = true;
            break;
        }
        if (!appClassBound && applicationClass.isAnnotationPresent(Singleton.class)) {
            injectionManager.register((Binding)Bindings.serviceAsContract(applicationClass).in(Singleton.class));
            appClassBound = true;
        }
        Application application = app = appClassBound ? injectionManager.getInstance(applicationClass) : injectionManager.createAndInitialize(applicationClass);
        if (app instanceof ResourceConfig && (innerAppClass = (_rc = (ResourceConfig)app).getApplicationClass()) != null) {
            Application innerApp = ApplicationConfigurator.createApplication(injectionManager, innerAppClass, componentProvidersValue);
            _rc.setApplication(innerApp);
        }
        return app;
    }
}

