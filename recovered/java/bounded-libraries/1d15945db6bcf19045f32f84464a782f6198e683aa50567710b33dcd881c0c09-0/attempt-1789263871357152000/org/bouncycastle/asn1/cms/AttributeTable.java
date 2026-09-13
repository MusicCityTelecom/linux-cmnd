/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.ASN1EncodableVector
 *  org.bouncycastle.asn1.ASN1ObjectIdentifier
 *  org.bouncycastle.asn1.ASN1Set
 *  org.bouncycastle.asn1.DERSet
 */
package org.bouncycastle.asn1.cms;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.cms.Attribute;
import org.bouncycastle.asn1.cms.Attributes;

public class AttributeTable {
    private Hashtable attributes = new Hashtable();

    public AttributeTable(Hashtable hashtable) {
        this.attributes = this.copyTable(hashtable);
    }

    public AttributeTable(ASN1EncodableVector aSN1EncodableVector) {
        for (int i = 0; i != aSN1EncodableVector.size(); ++i) {
            Attribute attribute = Attribute.getInstance(aSN1EncodableVector.get(i));
            this.addAttribute(attribute.getAttrType(), attribute);
        }
    }

    public AttributeTable(ASN1Set aSN1Set) {
        for (int i = 0; i != aSN1Set.size(); ++i) {
            Attribute attribute = Attribute.getInstance(aSN1Set.getObjectAt(i));
            this.addAttribute(attribute.getAttrType(), attribute);
        }
    }

    public AttributeTable(Attribute attribute) {
        this.addAttribute(attribute.getAttrType(), attribute);
    }

    public AttributeTable(Attributes attributes) {
        this(ASN1Set.getInstance((Object)attributes.toASN1Primitive()));
    }

    private void addAttribute(ASN1ObjectIdentifier aSN1ObjectIdentifier, Attribute attribute) {
        Object v = this.attributes.get(aSN1ObjectIdentifier);
        if (v == null) {
            this.attributes.put(aSN1ObjectIdentifier, attribute);
        } else {
            Vector<Object> vector;
            if (v instanceof Attribute) {
                vector = new Vector<Object>();
                vector.addElement(v);
                vector.addElement((Object)attribute);
            } else {
                vector = (Vector<Object>)v;
                vector.addElement((Object)attribute);
            }
            this.attributes.put(aSN1ObjectIdentifier, vector);
        }
    }

    public Attribute get(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        Object v = this.attributes.get(aSN1ObjectIdentifier);
        if (v instanceof Vector) {
            return (Attribute)((Object)((Vector)v).elementAt(0));
        }
        return (Attribute)((Object)v);
    }

    public ASN1EncodableVector getAll(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        Object v = this.attributes.get(aSN1ObjectIdentifier);
        if (v instanceof Vector) {
            Enumeration enumeration = ((Vector)v).elements();
            while (enumeration.hasMoreElements()) {
                aSN1EncodableVector.add((ASN1Encodable)((Attribute)((Object)enumeration.nextElement())));
            }
        } else if (v != null) {
            aSN1EncodableVector.add((ASN1Encodable)((Attribute)((Object)v)));
        }
        return aSN1EncodableVector;
    }

    public int size() {
        int n = 0;
        Enumeration enumeration = this.attributes.elements();
        while (enumeration.hasMoreElements()) {
            Object v = enumeration.nextElement();
            if (v instanceof Vector) {
                n += ((Vector)v).size();
                continue;
            }
            ++n;
        }
        return n;
    }

    public Hashtable toHashtable() {
        return this.copyTable(this.attributes);
    }

    public ASN1EncodableVector toASN1EncodableVector() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        Enumeration enumeration = this.attributes.elements();
        while (enumeration.hasMoreElements()) {
            Object v = enumeration.nextElement();
            if (v instanceof Vector) {
                Enumeration enumeration2 = ((Vector)v).elements();
                while (enumeration2.hasMoreElements()) {
                    aSN1EncodableVector.add((ASN1Encodable)Attribute.getInstance(enumeration2.nextElement()));
                }
                continue;
            }
            aSN1EncodableVector.add((ASN1Encodable)Attribute.getInstance(v));
        }
        return aSN1EncodableVector;
    }

    public Attributes toASN1Structure() {
        return new Attributes(this.toASN1EncodableVector());
    }

    private Hashtable copyTable(Hashtable hashtable) {
        Hashtable hashtable2 = new Hashtable();
        Enumeration enumeration = hashtable.keys();
        while (enumeration.hasMoreElements()) {
            Object k = enumeration.nextElement();
            hashtable2.put(k, hashtable.get(k));
        }
        return hashtable2;
    }

    public AttributeTable add(ASN1ObjectIdentifier aSN1ObjectIdentifier, ASN1Encodable aSN1Encodable) {
        AttributeTable attributeTable = new AttributeTable(this.attributes);
        attributeTable.addAttribute(aSN1ObjectIdentifier, new Attribute(aSN1ObjectIdentifier, (ASN1Set)new DERSet(aSN1Encodable)));
        return attributeTable;
    }

    public AttributeTable remove(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        AttributeTable attributeTable = new AttributeTable(this.attributes);
        attributeTable.attributes.remove(aSN1ObjectIdentifier);
        return attributeTable;
    }
}

