/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.Closure
 *  org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation
 */
package groovy.xml.slurpersupport;

import groovy.lang.Closure;
import groovy.xml.slurpersupport.GPathResult;
import groovy.xml.slurpersupport.Node;
import groovy.xml.slurpersupport.NodeChild;
import groovy.xml.slurpersupport.NodeChildren;
import groovy.xml.slurpersupport.NodeIterator;
import java.util.Iterator;
import java.util.Map;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;

public class FilteredNodeChildren
extends NodeChildren {
    private final Closure closure;

    public FilteredNodeChildren(GPathResult parent, Closure closure, Map<String, String> namespaceTagHints) {
        super(parent, parent.name, namespaceTagHints);
        this.closure = closure;
    }

    @Override
    public GPathResult pop() {
        return this.parent.parent;
    }

    @Override
    public Iterator nodeIterator() {
        return new NodeIterator(this.parent.nodeIterator()){

            @Override
            protected Object getNextNode(Iterator iter) {
                while (iter.hasNext()) {
                    Object node = iter.next();
                    if (!FilteredNodeChildren.this.closureYieldsTrueForNode(new NodeChild((Node)node, FilteredNodeChildren.this.parent, FilteredNodeChildren.this.namespaceTagHints))) continue;
                    return node;
                }
                return null;
            }
        };
    }

    private boolean closureYieldsTrueForNode(Object childNode) {
        return DefaultTypeTransformation.castToBoolean((Object)this.closure.call(new Object[]{childNode}));
    }
}

