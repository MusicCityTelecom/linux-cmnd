/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.jaxb.internal;

import javax.inject.Singleton;
import javax.ws.rs.ConstrainedTo;
import javax.ws.rs.RuntimeType;
import javax.ws.rs.ext.ParamConverterProvider;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.internal.inject.ClassBinding;
import org.glassfish.jersey.jaxb.internal.JaxbStringReaderProvider;

@ConstrainedTo(value=RuntimeType.SERVER)
public class JaxbParamConverterBinder
extends AbstractBinder {
    @Override
    protected void configure() {
        ((ClassBinding)this.bind(JaxbStringReaderProvider.RootElementProvider.class).to(ParamConverterProvider.class)).in(Singleton.class);
    }
}

