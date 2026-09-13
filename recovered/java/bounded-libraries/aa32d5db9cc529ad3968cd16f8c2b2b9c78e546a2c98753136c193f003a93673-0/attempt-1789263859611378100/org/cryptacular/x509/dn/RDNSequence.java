/*
 * Decompiled with CFR 0.152.
 */
package org.cryptacular.x509.dn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.cryptacular.x509.dn.Attribute;
import org.cryptacular.x509.dn.AttributeType;
import org.cryptacular.x509.dn.RDN;

public class RDNSequence
implements Iterable<RDN> {
    private final List<RDN> rdns = new ArrayList<RDN>(10);

    public void add(RDN rdn) {
        this.rdns.add(rdn);
    }

    @Override
    public Iterator<RDN> iterator() {
        return this.rdns.iterator();
    }

    public Iterable<RDN> backward() {
        return () -> new Iterator<RDN>(){
            private final ListIterator it;
            {
                this.it = RDNSequence.this.rdns.listIterator(RDNSequence.this.rdns.size());
            }

            @Override
            public boolean hasNext() {
                return this.it.hasPrevious();
            }

            @Override
            public RDN next() {
                return (RDN)this.it.previous();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Remove not supported");
            }
        };
    }

    public List<String> getValues(AttributeType type) {
        ArrayList<String> values = new ArrayList<String>(this.rdns.size());
        for (RDN rdn : this.rdns) {
            values.addAll(rdn.getAttributes().getValues(type));
        }
        return Collections.unmodifiableList(values);
    }

    public String getValue(AttributeType type) {
        List<String> values = this.getValues(type);
        if (values.size() > 0) {
            return values.get(0);
        }
        return null;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        int i = 0;
        for (RDN rdn : this) {
            for (Attribute attr : rdn.getAttributes()) {
                if (i++ > 0) {
                    builder.append(", ");
                }
                builder.append(attr.getType()).append('=').append(attr.getValue());
            }
        }
        return builder.toString();
    }
}

