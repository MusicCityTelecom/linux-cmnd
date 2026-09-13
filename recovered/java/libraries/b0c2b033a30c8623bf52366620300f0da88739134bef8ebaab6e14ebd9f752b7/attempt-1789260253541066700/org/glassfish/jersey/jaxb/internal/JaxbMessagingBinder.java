/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.jaxb.internal;

import java.lang.reflect.Type;
import javax.inject.Singleton;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.transform.TransformerFactory;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.internal.inject.ClassBinding;
import org.glassfish.jersey.internal.inject.PerThread;
import org.glassfish.jersey.internal.inject.SupplierClassBinding;
import org.glassfish.jersey.jaxb.internal.DocumentBuilderFactoryInjectionProvider;
import org.glassfish.jersey.jaxb.internal.DocumentProvider;
import org.glassfish.jersey.jaxb.internal.SaxParserFactoryInjectionProvider;
import org.glassfish.jersey.jaxb.internal.TransformerFactoryInjectionProvider;
import org.glassfish.jersey.jaxb.internal.XmlCollectionJaxbProvider;
import org.glassfish.jersey.jaxb.internal.XmlInputFactoryInjectionProvider;
import org.glassfish.jersey.jaxb.internal.XmlJaxbElementProvider;
import org.glassfish.jersey.jaxb.internal.XmlRootElementJaxbProvider;
import org.glassfish.jersey.jaxb.internal.XmlRootObjectJaxbProvider;

public class JaxbMessagingBinder
extends AbstractBinder {
    @Override
    protected void configure() {
        this.bindSingletonWorker(DocumentProvider.class);
        this.bindSingletonWorker(XmlJaxbElementProvider.App.class);
        this.bindSingletonWorker(XmlJaxbElementProvider.Text.class);
        this.bindSingletonWorker(XmlJaxbElementProvider.General.class);
        this.bindSingletonWorker(XmlCollectionJaxbProvider.App.class);
        this.bindSingletonWorker(XmlCollectionJaxbProvider.Text.class);
        this.bindSingletonWorker(XmlCollectionJaxbProvider.General.class);
        this.bindSingletonWorker(XmlRootElementJaxbProvider.App.class);
        this.bindSingletonWorker(XmlRootElementJaxbProvider.Text.class);
        this.bindSingletonWorker(XmlRootElementJaxbProvider.General.class);
        ((ClassBinding)this.bind(XmlRootObjectJaxbProvider.App.class).to(MessageBodyReader.class)).in(Singleton.class);
        ((ClassBinding)this.bind(XmlRootObjectJaxbProvider.Text.class).to(MessageBodyReader.class)).in(Singleton.class);
        ((ClassBinding)this.bind(XmlRootObjectJaxbProvider.General.class).to(MessageBodyReader.class)).in(Singleton.class);
        ((SupplierClassBinding)this.bindFactory(DocumentBuilderFactoryInjectionProvider.class).to((Type)((Object)DocumentBuilderFactory.class))).in(PerThread.class);
        ((SupplierClassBinding)this.bindFactory(SaxParserFactoryInjectionProvider.class).to((Type)((Object)SAXParserFactory.class))).in(PerThread.class);
        ((SupplierClassBinding)this.bindFactory(XmlInputFactoryInjectionProvider.class).to((Type)((Object)XMLInputFactory.class))).in(PerThread.class);
        ((SupplierClassBinding)this.bindFactory(TransformerFactoryInjectionProvider.class).to((Type)((Object)TransformerFactory.class))).in(PerThread.class);
    }

    private <T extends MessageBodyReader & MessageBodyWriter> void bindSingletonWorker(Class<T> worker) {
        ((ClassBinding)((ClassBinding)this.bind(worker).to(MessageBodyReader.class)).to(MessageBodyWriter.class)).in(Singleton.class);
    }
}

