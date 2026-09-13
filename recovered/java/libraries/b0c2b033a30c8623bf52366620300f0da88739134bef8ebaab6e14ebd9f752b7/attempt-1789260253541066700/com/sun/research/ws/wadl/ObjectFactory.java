/*
 * Decompiled with CFR 0.152.
 */
package com.sun.research.ws.wadl;

import com.sun.research.ws.wadl.Application;
import com.sun.research.ws.wadl.Doc;
import com.sun.research.ws.wadl.Grammars;
import com.sun.research.ws.wadl.Include;
import com.sun.research.ws.wadl.Link;
import com.sun.research.ws.wadl.Method;
import com.sun.research.ws.wadl.Option;
import com.sun.research.ws.wadl.Param;
import com.sun.research.ws.wadl.Representation;
import com.sun.research.ws.wadl.Request;
import com.sun.research.ws.wadl.Resource;
import com.sun.research.ws.wadl.ResourceType;
import com.sun.research.ws.wadl.Resources;
import com.sun.research.ws.wadl.Response;
import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {
    public Resources createResources() {
        return new Resources();
    }

    public Doc createDoc() {
        return new Doc();
    }

    public Resource createResource() {
        return new Resource();
    }

    public Param createParam() {
        return new Param();
    }

    public Option createOption() {
        return new Option();
    }

    public Link createLink() {
        return new Link();
    }

    public Method createMethod() {
        return new Method();
    }

    public Request createRequest() {
        return new Request();
    }

    public Representation createRepresentation() {
        return new Representation();
    }

    public Response createResponse() {
        return new Response();
    }

    public Application createApplication() {
        return new Application();
    }

    public Grammars createGrammars() {
        return new Grammars();
    }

    public Include createInclude() {
        return new Include();
    }

    public ResourceType createResourceType() {
        return new ResourceType();
    }
}

