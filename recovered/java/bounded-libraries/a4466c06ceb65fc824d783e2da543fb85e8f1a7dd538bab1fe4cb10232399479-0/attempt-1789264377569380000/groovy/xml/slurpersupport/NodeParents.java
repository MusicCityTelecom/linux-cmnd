/*
 * Decompiled with CFR 0.152.
 */
package groovy.xml.slurpersupport;

import groovy.xml.slurpersupport.GPathResult;
import groovy.xml.slurpersupport.Node;
import groovy.xml.slurpersupport.NodeChildren;
import groovy.xml.slurpersupport.NodeIterator;
import java.util.Iterator;
import java.util.Map;

public class NodeParents
extends NodeChildren {
    public NodeParents(GPathResult parent, Map<String, String> namespaceTagHints) {
        super(parent, parent.parent.name, namespaceTagHints);
    }

    @Override
    public Iterator nodeIterator() {
        return new NodeIterator(this.parent.nodeIterator()){
            private Node prev;
            {
                this.prev = null;
            }

            @Override
            protected Object getNextNode(Iterator iter) {
                while (iter.hasNext()) {
                    Node node = ((Node)iter.next()).parent();
                    if (node == null || node == this.prev) continue;
                    this.prev = node;
                    return node;
                }
                return null;
            }
        };
    }
}

