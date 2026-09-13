/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1ObjectIdentifier
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.est.AttrOrOID
 *  org.bouncycastle.asn1.est.CsrAttrs
 *  org.bouncycastle.util.Encodable
 */
package org.bouncycastle.est;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.est.AttrOrOID;
import org.bouncycastle.asn1.est.CsrAttrs;
import org.bouncycastle.est.ESTException;
import org.bouncycastle.util.Encodable;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class CSRAttributesResponse
implements Encodable {
    private final CsrAttrs csrAttrs;
    private final HashMap<ASN1ObjectIdentifier, AttrOrOID> index;

    public CSRAttributesResponse(byte[] byArray) throws ESTException {
        this(CSRAttributesResponse.parseBytes(byArray));
    }

    public CSRAttributesResponse(CsrAttrs csrAttrs) throws ESTException {
        this.csrAttrs = csrAttrs;
        this.index = new HashMap(csrAttrs.size());
        AttrOrOID[] attrOrOIDArray = csrAttrs.getAttrOrOIDs();
        for (int i = 0; i != attrOrOIDArray.length; ++i) {
            AttrOrOID attrOrOID = attrOrOIDArray[i];
            if (attrOrOID.isOid()) {
                this.index.put(attrOrOID.getOid(), attrOrOID);
                continue;
            }
            this.index.put(attrOrOID.getAttribute().getAttrType(), attrOrOID);
        }
    }

    private static CsrAttrs parseBytes(byte[] byArray) throws ESTException {
        try {
            return CsrAttrs.getInstance((Object)ASN1Primitive.fromByteArray((byte[])byArray));
        }
        catch (Exception exception) {
            throw new ESTException("malformed data: " + exception.getMessage(), exception);
        }
    }

    public boolean hasRequirement(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return this.index.containsKey(aSN1ObjectIdentifier);
    }

    public boolean isAttribute(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        if (this.index.containsKey(aSN1ObjectIdentifier)) {
            return !this.index.get(aSN1ObjectIdentifier).isOid();
        }
        return false;
    }

    public boolean isEmpty() {
        return this.csrAttrs.size() == 0;
    }

    public Collection<ASN1ObjectIdentifier> getRequirements() {
        return this.index.keySet();
    }

    public byte[] getEncoded() throws IOException {
        return this.csrAttrs.getEncoded();
    }
}

