/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.xml.bind.JAXBContext
 *  javax.xml.bind.JAXBException
 *  javax.xml.bind.Unmarshaller
 *  org.springframework.core.io.Resource
 *  org.springframework.util.Assert
 */
package org.apereo.services.persondir.support.xml;

import java.io.IOException;
import java.io.InputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.apereo.services.persondir.support.xml.CachingJaxbLoader;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

public class CachingJaxbLoaderImpl<T>
implements CachingJaxbLoader<T> {
    protected final Class<T> loadedType;
    protected long noLastModifiedReloadPeriod = 300000L;
    protected Resource mappedXmlResource;
    protected T unmarshalledObject;
    protected long lastModifiedTime = Integer.MIN_VALUE;

    public CachingJaxbLoaderImpl(Class<T> loadedType) {
        Assert.notNull(loadedType, (String)"loadedType can not be null");
        this.loadedType = loadedType;
    }

    public long getNoLastModifiedReloadPeriod() {
        return this.noLastModifiedReloadPeriod;
    }

    public void setNoLastModifiedReloadPeriod(long noLastModifiedReloadPeriod) {
        this.noLastModifiedReloadPeriod = noLastModifiedReloadPeriod;
    }

    public Resource getMappedXmlResource() {
        return this.mappedXmlResource;
    }

    public void setMappedXmlResource(Resource mappedXmlResource) {
        this.mappedXmlResource = mappedXmlResource;
    }

    @Override
    public T getUnmarshalledObject() {
        return this.getUnmarshalledObject(null);
    }

    @Override
    public T getUnmarshalledObject(CachingJaxbLoader.UnmarshallingCallback<T> callback) {
        Long lastModified = null;
        if (this.unmarshalledObject != null && this.isCacheValid(lastModified = this.getLastModified())) {
            return this.unmarshalledObject;
        }
        InputStream xmlInputStream = this.getXmlInputStream();
        JAXBContext jaxbContext = this.getJAXBContext();
        Unmarshaller unmarshaller = this.getUnmarshaller(jaxbContext);
        T unmarshalledObject = this.unmarshal(xmlInputStream, unmarshaller);
        if (callback != null) {
            callback.postProcessUnmarshalling(unmarshalledObject);
        }
        this.unmarshalledObject = unmarshalledObject;
        this.lastModifiedTime = lastModified != null ? lastModified : System.currentTimeMillis();
        return this.unmarshalledObject;
    }

    protected Long getLastModified() {
        try {
            return this.mappedXmlResource.lastModified();
        }
        catch (IOException ioe) {
            return null;
        }
    }

    protected boolean isCacheValid(Long lastModified) {
        return lastModified != null && lastModified <= this.lastModifiedTime || lastModified == null && this.lastModifiedTime + this.noLastModifiedReloadPeriod <= System.currentTimeMillis();
    }

    protected InputStream getXmlInputStream() {
        InputStream xmlInputStream;
        try {
            xmlInputStream = this.mappedXmlResource.getInputStream();
        }
        catch (IOException e) {
            throw new RuntimeException("Failed to open InputStream for Resource: " + this.mappedXmlResource, e);
        }
        return xmlInputStream;
    }

    protected JAXBContext getJAXBContext() {
        Package loadedPackage = this.loadedType.getPackage();
        String filterDisplayPackage = loadedPackage.getName();
        try {
            return JAXBContext.newInstance((String)filterDisplayPackage);
        }
        catch (JAXBException e) {
            throw new RuntimeException("Failed to create " + JAXBContext.class + " to unmarshal " + this.loadedType, e);
        }
    }

    protected Unmarshaller getUnmarshaller(JAXBContext jaxbContext) {
        try {
            return jaxbContext.createUnmarshaller();
        }
        catch (JAXBException e) {
            throw new RuntimeException("Failed to create " + Unmarshaller.class + " to unmarshal " + this.loadedType, e);
        }
    }

    protected T unmarshal(InputStream xmlInputStream, Unmarshaller unmarshaller) {
        try {
            return (T)unmarshaller.unmarshal(xmlInputStream);
        }
        catch (JAXBException e) {
            throw new RuntimeException("Unexpected JAXB error while unmarshalling  " + this.mappedXmlResource, e);
        }
    }
}

