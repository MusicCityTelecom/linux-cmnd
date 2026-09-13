/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.Closure
 *  org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation
 */
package groovy.xml.slurpersupport;

import groovy.lang.Closure;
import groovy.xml.slurpersupport.Attributes;
import groovy.xml.slurpersupport.GPathResult;
import groovy.xml.slurpersupport.NodeIterator;
import java.util.Iterator;
import java.util.Map;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;

public class FilteredAttributes
extends Attributes {
    private final Closure closure;

    public FilteredAttributes(GPathResult parent, Closure closure, Map<String, String> namespaceTagHints) {
        super(parent, parent.name, namespaceTagHints);
        this.closure = closure;
    }

    @Override
    public Iterator nodeIterator() {
        return new NodeIterator(this.parent.iterator()){

            @Override
            protected Object getNextNode(Iterator iter) {
                while (iter.hasNext()) {
                    Object node = iter.next();
                    if (!DefaultTypeTransformation.castToBoolean((Object)FilteredAttributes.this.closure.call(new Object[]{node}))) continue;
                    return node;
                }
                return null;
            }
        };
    }
}

