/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.Closure
 *  groovy.lang.GroovyObject
 *  groovy.lang.GroovyRuntimeException
 *  org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation
 */
package groovy.xml.slurpersupport;

import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import groovy.lang.GroovyRuntimeException;
import groovy.xml.slurpersupport.GPathResult;
import groovy.xml.slurpersupport.NoChildren;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.Map;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;

public class Attribute
extends GPathResult {
    private final String value;

    public Attribute(String name, String value, GPathResult parent, String namespacePrefix, Map<String, String> namespaceTagHints) {
        super(parent, name, namespacePrefix, namespaceTagHints);
        this.value = value;
    }

    @Override
    public String name() {
        return this.name.substring(1);
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public String text() {
        return this.value;
    }

    public String namespaceURI() {
        if (this.namespacePrefix == null || this.namespacePrefix.isEmpty()) {
            return "";
        }
        String uri = (String)this.namespaceTagHints.get(this.namespacePrefix);
        return uri == null ? "" : uri;
    }

    @Override
    public GPathResult parents() {
        throw new GroovyRuntimeException("parents() not implemented yet");
    }

    @Override
    public Iterator childNodes() {
        throw new GroovyRuntimeException("can't call childNodes() in the attribute " + this.name);
    }

    @Override
    public Iterator iterator() {
        return this.nodeIterator();
    }

    @Override
    public GPathResult find(Closure closure) {
        if (DefaultTypeTransformation.castToBoolean((Object)closure.call(new Object[]{this}))) {
            return this;
        }
        return new NoChildren(this, "", this.namespaceTagHints);
    }

    @Override
    public GPathResult findAll(Closure closure) {
        return this.find(closure);
    }

    @Override
    public Iterator nodeIterator() {
        return this.createIterator(this);
    }

    public Writer writeTo(Writer out) throws IOException {
        out.write(this.value);
        return out;
    }

    public void build(GroovyObject builder) {
        builder.getProperty("mkp");
        builder.invokeMethod("yield", (Object)new Object[]{this.value});
    }

    @Override
    protected void replaceNode(Closure newValue) {
    }

    @Override
    protected void replaceBody(Object newValue) {
    }

    @Override
    protected void appendNode(Object newValue) {
    }
}

