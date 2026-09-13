/*
 * Decompiled with CFR 0.152.
 */
package org.cryptacular.x509.dn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import org.cryptacular.x509.dn.Attribute;
import org.cryptacular.x509.dn.AttributeType;
import org.cryptacular.x509.dn.StandardAttributeType;
import org.cryptacular.x509.dn.UnknownAttributeType;

public class Attributes
implements Iterable<Attribute> {
    private final List<Attribute> attributes = new ArrayList<Attribute>(5);

    public void add(String typeOid, String value) {
        StandardAttributeType type = StandardAttributeType.fromOid(typeOid);
        if (type != null) {
            this.add(new Attribute(type, value));
        } else {
            this.add(new Attribute(new UnknownAttributeType(typeOid), value));
        }
    }

    public void add(Attribute attr) {
        if (attr == null) {
            throw new IllegalArgumentException("Attribute cannot be null");
        }
        this.attributes.add(attr);
    }

    public int size() {
        return this.attributes.size();
    }

    public List<Attribute> getAll() {
        return Collections.unmodifiableList(this.attributes);
    }

    public List<String> getValues(AttributeType type) {
        ArrayList values = new ArrayList(this.attributes.size());
        values.addAll(this.attributes.stream().filter(attr -> attr.getType().equals(type)).map(Attribute::getValue).collect(Collectors.toList()));
        return Collections.unmodifiableList(values);
    }

    public String getValue(AttributeType type) {
        for (Attribute attr : this.attributes) {
            if (!attr.getType().equals(type)) continue;
            return attr.getValue();
        }
        return null;
    }

    @Override
    public Iterator<Attribute> iterator() {
        return this.attributes.iterator();
    }
}

