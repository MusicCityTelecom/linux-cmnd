/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  org.springframework.beans.factory.BeanCreationException
 *  org.springframework.beans.factory.DisposableBean
 *  org.springframework.core.io.Resource
 */
package org.apereo.services.persondir.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.Closeable;
import java.io.IOException;
import java.util.Map;
import org.apereo.services.persondir.support.ComplexStubPersonAttributeDao;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.core.io.Resource;

public class JsonBackedComplexStubPersonAttributeDao
extends ComplexStubPersonAttributeDao
implements Closeable,
DisposableBean {
    private final Resource personAttributesConfigFile;
    private final ObjectMapper jacksonObjectMapper = new ObjectMapper().findAndRegisterModules();
    private final Object synchronizationMonitor = new Object();
    private Closeable resourceWatcherService;

    public JsonBackedComplexStubPersonAttributeDao(Resource personAttributesConfigFile) {
        this.personAttributesConfigFile = personAttributesConfigFile;
        this.resourceWatcherService = null;
    }

    public JsonBackedComplexStubPersonAttributeDao(Resource personAttributesConfigFile, Closeable resourceWatcherService) {
        this.personAttributesConfigFile = personAttributesConfigFile;
        this.resourceWatcherService = resourceWatcherService;
    }

    public void setResourceWatcherService(Closeable resourceWatcherService) {
        this.resourceWatcherService = resourceWatcherService;
    }

    public void init() throws IOException {
        try {
            this.unmarshalAndSetBackingMap();
        }
        catch (Exception ex) {
            throw new BeanCreationException(String.format("The semantic structure of the person attributesJSON config is not correct. Please fix it in this resource: [%s]", this.personAttributesConfigFile), (Throwable)ex);
        }
    }

    @Override
    public void close() throws IOException {
        if (this.resourceWatcherService != null) {
            this.resourceWatcherService.close();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void unmarshalAndSetBackingMap() throws IOException {
        this.logger.info("Un-marshaling person attributes from the config file {}", (Object)this.personAttributesConfigFile);
        Map backingMap = (Map)this.jacksonObjectMapper.readValue(this.personAttributesConfigFile.getInputStream(), Map.class);
        this.logger.debug("Person attributes have been successfully read into the map ");
        Object object = this.synchronizationMonitor;
        synchronized (object) {
            super.setBackingMap(backingMap);
        }
    }

    public void destroy() throws Exception {
        this.close();
    }
}

