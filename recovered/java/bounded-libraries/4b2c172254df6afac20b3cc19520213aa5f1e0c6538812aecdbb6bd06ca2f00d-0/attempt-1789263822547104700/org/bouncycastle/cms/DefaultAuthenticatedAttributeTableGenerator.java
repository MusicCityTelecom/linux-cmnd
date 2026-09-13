/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.ASN1ObjectIdentifier
 *  org.bouncycastle.asn1.ASN1Set
 *  org.bouncycastle.asn1.DEROctetString
 *  org.bouncycastle.asn1.DERSet
 *  org.bouncycastle.asn1.cms.Attribute
 *  org.bouncycastle.asn1.cms.AttributeTable
 *  org.bouncycastle.asn1.cms.CMSAlgorithmProtection
 *  org.bouncycastle.asn1.cms.CMSAttributes
 *  org.bouncycastle.asn1.x509.AlgorithmIdentifier
 */
package org.bouncycastle.cms;

import java.util.Hashtable;
import java.util.Map;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.cms.Attribute;
import org.bouncycastle.asn1.cms.AttributeTable;
import org.bouncycastle.asn1.cms.CMSAlgorithmProtection;
import org.bouncycastle.asn1.cms.CMSAttributes;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.cms.CMSAttributeTableGenerator;

public class DefaultAuthenticatedAttributeTableGenerator
implements CMSAttributeTableGenerator {
    private final Hashtable table;

    public DefaultAuthenticatedAttributeTableGenerator() {
        this.table = new Hashtable();
    }

    public DefaultAuthenticatedAttributeTableGenerator(AttributeTable attributeTable) {
        this.table = attributeTable != null ? attributeTable.toHashtable() : new Hashtable();
    }

    protected Hashtable createStandardAttributeTable(Map map) {
        Object object;
        Hashtable hashtable = new Hashtable();
        Object object2 = this.table.keys();
        while (object2.hasMoreElements()) {
            object = object2.nextElement();
            hashtable.put(object, this.table.get(object));
        }
        if (!hashtable.containsKey(CMSAttributes.contentType)) {
            object2 = ASN1ObjectIdentifier.getInstance(map.get("contentType"));
            object = new Attribute(CMSAttributes.contentType, (ASN1Set)new DERSet((ASN1Encodable)object2));
            hashtable.put(object.getAttrType(), object);
        }
        if (!hashtable.containsKey(CMSAttributes.messageDigest)) {
            object2 = (byte[])map.get("digest");
            object = new Attribute(CMSAttributes.messageDigest, (ASN1Set)new DERSet((ASN1Encodable)new DEROctetString((byte[])object2)));
            hashtable.put(object.getAttrType(), object);
        }
        if (!hashtable.contains(CMSAttributes.cmsAlgorithmProtect)) {
            object2 = new Attribute(CMSAttributes.cmsAlgorithmProtect, (ASN1Set)new DERSet((ASN1Encodable)new CMSAlgorithmProtection((AlgorithmIdentifier)map.get("digestAlgID"), 2, (AlgorithmIdentifier)map.get("macAlgID"))));
            hashtable.put(object2.getAttrType(), object2);
        }
        return hashtable;
    }

    public AttributeTable getAttributes(Map map) {
        return new AttributeTable(this.createStandardAttributeTable(map));
    }
}

