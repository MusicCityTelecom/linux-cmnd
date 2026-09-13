/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.asn1;

import java.io.IOException;
import org.bouncycastle.asn1.ASN1ApplicationSpecificParser;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Exception;
import org.bouncycastle.asn1.ASN1OutputStream;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.ASN1TaggedObjectParser;
import org.bouncycastle.asn1.DERApplicationSpecific;
import org.bouncycastle.asn1.DLApplicationSpecific;

public abstract class ASN1ApplicationSpecific
extends ASN1TaggedObject
implements ASN1ApplicationSpecificParser {
    final ASN1TaggedObject taggedObject;

    public static ASN1ApplicationSpecific getInstance(Object object) {
        if (object == null || object instanceof ASN1ApplicationSpecific) {
            return (ASN1ApplicationSpecific)object;
        }
        if (object instanceof byte[]) {
            try {
                return ASN1ApplicationSpecific.getInstance(ASN1Primitive.fromByteArray((byte[])object));
            }
            catch (IOException iOException) {
                throw new IllegalArgumentException("Failed to construct object from byte[]: " + iOException.getMessage());
            }
        }
        throw new IllegalArgumentException("unknown object in getInstance: " + object.getClass().getName());
    }

    ASN1ApplicationSpecific(ASN1TaggedObject aSN1TaggedObject) {
        super(aSN1TaggedObject.explicitness, ASN1ApplicationSpecific.checkTagClass(aSN1TaggedObject.tagClass), aSN1TaggedObject.tagNo, aSN1TaggedObject.obj);
        this.taggedObject = aSN1TaggedObject;
    }

    public int getApplicationTag() {
        return this.taggedObject.getTagNo();
    }

    @Override
    public byte[] getContents() {
        return this.taggedObject.getContents();
    }

    public ASN1Primitive getEnclosedObject() throws IOException {
        return this.taggedObject.getBaseObject().toASN1Primitive();
    }

    public ASN1Primitive getObject(int n) throws IOException {
        return this.taggedObject.getBaseUniversal(false, n);
    }

    @Override
    public ASN1Encodable getObjectParser(int n, boolean bl) throws IOException {
        throw new ASN1Exception("this method only valid for CONTEXT_SPECIFIC tags");
    }

    @Override
    public ASN1Encodable parseBaseUniversal(boolean bl, int n) throws IOException {
        return this.taggedObject.parseBaseUniversal(bl, n);
    }

    @Override
    public ASN1Encodable parseExplicitBaseObject() throws IOException {
        return this.taggedObject.parseExplicitBaseObject();
    }

    @Override
    public ASN1TaggedObjectParser parseExplicitBaseTagged() throws IOException {
        return this.taggedObject.parseExplicitBaseTagged();
    }

    @Override
    public ASN1TaggedObjectParser parseImplicitBaseTagged(int n, int n2) throws IOException {
        return this.taggedObject.parseImplicitBaseTagged(n, n2);
    }

    public boolean hasApplicationTag(int n) {
        return this.tagNo == n;
    }

    @Override
    public boolean hasContextTag(int n) {
        return false;
    }

    public ASN1TaggedObject getTaggedObject() {
        return this.taggedObject;
    }

    @Override
    public boolean isConstructed() {
        return this.taggedObject.isConstructed();
    }

    @Override
    public ASN1Encodable readObject() throws IOException {
        return this.parseExplicitBaseObject();
    }

    @Override
    boolean encodeConstructed() {
        return this.taggedObject.encodeConstructed();
    }

    @Override
    int encodedLength(boolean bl) throws IOException {
        return this.taggedObject.encodedLength(bl);
    }

    @Override
    void encode(ASN1OutputStream aSN1OutputStream, boolean bl) throws IOException {
        this.taggedObject.encode(aSN1OutputStream, bl);
    }

    @Override
    String getASN1Encoding() {
        return this.taggedObject.getASN1Encoding();
    }

    @Override
    ASN1Sequence rebuildConstructed(ASN1Primitive aSN1Primitive) {
        return this.taggedObject.rebuildConstructed(aSN1Primitive);
    }

    @Override
    ASN1TaggedObject replaceTag(int n, int n2) {
        return this.taggedObject.replaceTag(n, n2);
    }

    @Override
    ASN1Primitive toDERObject() {
        return new DERApplicationSpecific((ASN1TaggedObject)this.taggedObject.toDERObject());
    }

    @Override
    ASN1Primitive toDLObject() {
        return new DLApplicationSpecific((ASN1TaggedObject)this.taggedObject.toDLObject());
    }

    private static int checkTagClass(int n) {
        if (64 != n) {
            throw new IllegalArgumentException();
        }
        return n;
    }
}

