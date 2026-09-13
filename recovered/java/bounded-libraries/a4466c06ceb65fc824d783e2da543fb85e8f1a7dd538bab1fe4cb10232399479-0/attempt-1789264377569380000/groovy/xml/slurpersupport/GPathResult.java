/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.Buildable
 *  groovy.lang.Closure
 *  groovy.lang.DelegatingMetaClass
 *  groovy.lang.GString
 *  groovy.lang.GroovyObject
 *  groovy.lang.GroovyObjectSupport
 *  groovy.lang.IntRange
 *  groovy.lang.MetaClass
 *  groovy.lang.Range
 *  groovy.lang.Writable
 *  org.codehaus.groovy.runtime.DefaultGroovyMethods
 *  org.codehaus.groovy.runtime.ResourceGroovyMethods
 *  org.codehaus.groovy.runtime.StringGroovyMethods
 */
package groovy.xml.slurpersupport;

import groovy.lang.Buildable;
import groovy.lang.Closure;
import groovy.lang.DelegatingMetaClass;
import groovy.lang.GString;
import groovy.lang.GroovyObject;
import groovy.lang.GroovyObjectSupport;
import groovy.lang.IntRange;
import groovy.lang.MetaClass;
import groovy.lang.Range;
import groovy.lang.Writable;
import groovy.xml.slurpersupport.Attributes;
import groovy.xml.slurpersupport.NoChildren;
import groovy.xml.slurpersupport.Node;
import groovy.xml.slurpersupport.NodeChild;
import groovy.xml.slurpersupport.NodeChildren;
import groovy.xml.slurpersupport.NodeParents;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.codehaus.groovy.runtime.ResourceGroovyMethods;
import org.codehaus.groovy.runtime.StringGroovyMethods;

public abstract class GPathResult
extends GroovyObjectSupport
implements Writable,
Buildable,
Iterable {
    protected final GPathResult parent;
    protected final String name;
    protected final String namespacePrefix;
    protected final Map namespaceMap = new HashMap();
    protected final Map<String, String> namespaceTagHints;

    public GPathResult(GPathResult parent, String name, String namespacePrefix, Map<String, String> namespaceTagHints) {
        if (parent == null) {
            this.parent = this;
            this.namespaceMap.put("xml", "http://www.w3.org/XML/1998/namespace");
        } else {
            this.parent = parent;
            this.namespaceMap.putAll(parent.namespaceMap);
        }
        this.name = name;
        this.namespacePrefix = namespacePrefix;
        this.namespaceTagHints = namespaceTagHints;
        this.setMetaClass(this.getMetaClass());
    }

    public void setMetaClass(MetaClass metaClass) {
        DelegatingMetaClass newMetaClass = new DelegatingMetaClass(metaClass){

            public Object getAttribute(Object object, String attribute) {
                return GPathResult.this.getProperty("@" + attribute);
            }

            public void setAttribute(Object object, String attribute, Object newValue) {
                GPathResult.this.setProperty("@" + attribute, newValue);
            }
        };
        super.setMetaClass((MetaClass)newMetaClass);
    }

    public Object getProperty(String property) {
        if ("..".equals(property)) {
            return this.parent();
        }
        if ("*".equals(property)) {
            return this.children();
        }
        if ("**".equals(property)) {
            return this.depthFirst();
        }
        if (property.startsWith("@")) {
            if (property.contains(":") && !this.namespaceTagHints.isEmpty()) {
                int i = property.indexOf(58);
                return new Attributes(this, "@" + property.substring(i + 1), property.substring(1, i), this.namespaceTagHints);
            }
            return new Attributes(this, property, this.namespaceTagHints);
        }
        if (property.contains(":") && !this.namespaceTagHints.isEmpty()) {
            int i = property.indexOf(58);
            return new NodeChildren(this, property.substring(i + 1), property.substring(0, i), this.namespaceTagHints);
        }
        return new NodeChildren(this, property, this.namespaceTagHints);
    }

    public void setProperty(String property, Object newValue) {
        if (property.startsWith("@")) {
            if (newValue instanceof String || newValue instanceof GString) {
                for (Object o : this) {
                    NodeChild child = (NodeChild)o;
                    child.attributes().put(property.substring(1), newValue);
                }
            }
        } else {
            NodeChildren result = new NodeChildren(this, property, this.namespaceTagHints);
            if (newValue instanceof Map) {
                Iterator iterator = ((Map)newValue).entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry o;
                    Map.Entry entry = o = iterator.next();
                    result.setProperty("@" + entry.getKey(), entry.getValue());
                }
            } else if (newValue instanceof Closure) {
                ((GPathResult)result).replaceNode((Closure)newValue);
            } else {
                ((GPathResult)result).replaceBody(newValue);
            }
        }
    }

    public Object leftShift(Object newValue) {
        this.appendNode(newValue);
        return this;
    }

    public Object plus(final Object newValue) {
        this.replaceNode(new Closure(this){

            public void doCall(Object[] args) {
                GroovyObject delegate = (GroovyObject)this.getDelegate();
                delegate.getProperty("mkp");
                delegate.invokeMethod("yield", (Object)args);
                delegate.getProperty("mkp");
                delegate.invokeMethod("yield", (Object)new Object[]{newValue});
            }
        });
        return this;
    }

    public Object plus(Closure newValue) {
        return this.plus((Object)newValue);
    }

    protected abstract void replaceNode(Closure var1);

    protected abstract void replaceBody(Object var1);

    protected abstract void appendNode(Object var1);

    public String name() {
        return this.name;
    }

    public GPathResult pop() {
        return this.parent;
    }

    public GPathResult parent() {
        return new NodeParents(this, this.namespaceTagHints);
    }

    public GPathResult children() {
        return new NodeChildren(this, this.namespaceTagHints);
    }

    public String lookupNamespace(String prefix) {
        Object namespace = this.namespaceMap.get(prefix);
        if (namespace != null) {
            return namespace.toString();
        }
        return this.namespaceTagHints.isEmpty() ? prefix : this.namespaceTagHints.get(prefix);
    }

    public String toString() {
        return this.text();
    }

    public Integer toInteger() {
        if (this.textIsEmptyOrNull()) {
            return null;
        }
        return StringGroovyMethods.toInteger((CharSequence)this.text());
    }

    public Long toLong() {
        if (this.textIsEmptyOrNull()) {
            return null;
        }
        return StringGroovyMethods.toLong((CharSequence)this.text());
    }

    public Float toFloat() {
        if (this.textIsEmptyOrNull()) {
            return null;
        }
        return StringGroovyMethods.toFloat((CharSequence)this.text());
    }

    public Double toDouble() {
        if (this.textIsEmptyOrNull()) {
            return null;
        }
        return StringGroovyMethods.toDouble((CharSequence)this.text());
    }

    public BigDecimal toBigDecimal() {
        if (this.textIsEmptyOrNull()) {
            return null;
        }
        return StringGroovyMethods.toBigDecimal((CharSequence)this.text());
    }

    public BigInteger toBigInteger() {
        if (this.textIsEmptyOrNull()) {
            return null;
        }
        return StringGroovyMethods.toBigInteger((CharSequence)this.text());
    }

    private boolean textIsEmptyOrNull() {
        String t = this.text();
        return null == t || 0 == t.length();
    }

    public URL toURL() throws MalformedURLException {
        return ResourceGroovyMethods.toURL((CharSequence)this.text());
    }

    public URI toURI() throws URISyntaxException {
        return ResourceGroovyMethods.toURI((CharSequence)this.text());
    }

    public Boolean toBoolean() {
        return StringGroovyMethods.toBoolean((String)this.text());
    }

    public GPathResult declareNamespace(Map newNamespaceMapping) {
        this.namespaceMap.putAll(newNamespaceMapping);
        return this;
    }

    public int hashCode() {
        return this.text().hashCode();
    }

    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }
        return this.text().equals(obj.toString());
    }

    public Object getAt(int index) {
        if (index < 0) {
            List list = this.list();
            int adjustedIndex = index + list.size();
            if (adjustedIndex >= 0 && adjustedIndex < list.size()) {
                return list.get(adjustedIndex);
            }
        } else {
            Iterator iter = this.iterator();
            int count = 0;
            while (iter.hasNext()) {
                if (count++ == index) {
                    return iter.next();
                }
                iter.next();
            }
        }
        return new NoChildren(this, this.name, this.namespaceTagHints);
    }

    public Object getAt(IntRange range) {
        return DefaultGroovyMethods.getAt((List)this.list(), (Range)range);
    }

    public void putAt(int index, Object newValue) {
        GPathResult result = (GPathResult)this.getAt(index);
        if (newValue instanceof Closure) {
            result.replaceNode((Closure)newValue);
        } else {
            result.replaceBody(newValue);
        }
    }

    public Iterator depthFirst() {
        return new Iterator(){
            private final List list = new LinkedList();
            private final Stack stack = new Stack();
            private Iterator iter = GPathResult.this.iterator();
            private GPathResult next = this.getNextByDepth();

            @Override
            public boolean hasNext() {
                return this.next != null;
            }

            public Object next() {
                try {
                    GPathResult gPathResult = this.next;
                    return gPathResult;
                }
                finally {
                    this.next = this.getNextByDepth();
                }
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }

            private GPathResult getNextByDepth() {
                while (this.iter.hasNext()) {
                    GPathResult node = (GPathResult)this.iter.next();
                    this.list.add(node);
                    this.stack.push(this.iter);
                    this.iter = node.children().iterator();
                }
                if (this.list.isEmpty()) {
                    return null;
                }
                GPathResult result = (GPathResult)this.list.get(0);
                this.list.remove(0);
                this.iter = (Iterator)this.stack.pop();
                return result;
            }
        };
    }

    public Iterator breadthFirst() {
        return new Iterator(){
            private final List list = new LinkedList();
            private Iterator iter = GPathResult.this.iterator();
            private GPathResult next = this.getNextByBreadth();

            @Override
            public boolean hasNext() {
                return this.next != null;
            }

            public Object next() {
                try {
                    GPathResult gPathResult = this.next;
                    return gPathResult;
                }
                finally {
                    this.next = this.getNextByBreadth();
                }
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }

            private GPathResult getNextByBreadth() {
                ArrayList<GPathResult> children = new ArrayList<GPathResult>();
                while (this.iter.hasNext() || !children.isEmpty()) {
                    if (this.iter.hasNext()) {
                        GPathResult node = (GPathResult)this.iter.next();
                        this.list.add(node);
                        this.list.add(this.iter);
                        children.add(node.children());
                        continue;
                    }
                    ArrayList nextLevel = new ArrayList();
                    for (Object e : children) {
                        GPathResult next = (GPathResult)e;
                        for (Object o : next) {
                            nextLevel.add(o);
                        }
                    }
                    this.iter = nextLevel.iterator();
                    children = new ArrayList();
                }
                if (this.list.isEmpty()) {
                    return null;
                }
                GPathResult result = (GPathResult)this.list.get(0);
                this.list.remove(0);
                this.iter = (Iterator)this.list.get(0);
                this.list.remove(0);
                return result;
            }
        };
    }

    public List list() {
        Iterator iter = this.nodeIterator();
        LinkedList<NodeChild> result = new LinkedList<NodeChild>();
        while (iter.hasNext()) {
            result.add(new NodeChild((Node)iter.next(), this.parent, this.namespacePrefix, this.namespaceTagHints));
        }
        return result;
    }

    public boolean isEmpty() {
        return this.size() == 0;
    }

    public Closure getBody() {
        return new Closure(this.parent, this){

            public void doCall(Object[] args) {
                GroovyObject delegate = (GroovyObject)this.getDelegate();
                GPathResult thisObject = (GPathResult)this.getThisObject();
                Node node = (Node)thisObject.getAt(0);
                List children = node.children();
                for (Object child : children) {
                    delegate.getProperty("mkp");
                    if (child instanceof Node) {
                        delegate.invokeMethod("yield", (Object)new Object[]{new NodeChild((Node)child, thisObject, "*", null)});
                        continue;
                    }
                    delegate.invokeMethod("yield", (Object)new Object[]{child});
                }
            }
        };
    }

    public abstract int size();

    public abstract String text();

    public abstract GPathResult parents();

    public abstract Iterator childNodes();

    public abstract Iterator iterator();

    public abstract GPathResult find(Closure var1);

    public abstract GPathResult findAll(Closure var1);

    public abstract Iterator nodeIterator();

    protected Iterator createIterator(final Object obj) {
        return new Iterator(){
            private boolean hasNext = true;

            @Override
            public boolean hasNext() {
                return this.hasNext;
            }

            public Object next() {
                try {
                    Object object = this.hasNext ? obj : null;
                    return object;
                }
                finally {
                    this.hasNext = false;
                }
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
}

