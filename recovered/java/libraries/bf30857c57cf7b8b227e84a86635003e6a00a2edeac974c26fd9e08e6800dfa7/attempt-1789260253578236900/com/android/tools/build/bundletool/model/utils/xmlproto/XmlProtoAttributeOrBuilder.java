/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.utils.xmlproto;

import com.android.aapt.Resources;
import com.android.tools.build.bundletool.model.utils.xmlproto.UnexpectedAttributeTypeException;
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoPrintUtils;

abstract class XmlProtoAttributeOrBuilder<AttributeProtoT extends Resources.XmlAttributeOrBuilder> {
    XmlProtoAttributeOrBuilder() {
    }

    abstract AttributeProtoT getProto();

    public final String getName() {
        return this.getProto().getName();
    }

    public final String getNamespaceUri() {
        return this.getProto().getNamespaceUri();
    }

    public final int getResourceId() {
        return this.getProto().getResourceId();
    }

    public final String getValueAsString() {
        if (this.getProto().getCompiledItem().getValueCase().equals(Resources.Item.ValueCase.STR)) {
            return this.getProto().getCompiledItem().getStr().getValue();
        }
        return this.getProto().getValue();
    }

    public final boolean getValueAsBoolean() {
        Resources.Primitive primitive = this.getProto().getCompiledItem().getPrim();
        if (!primitive.getOneofValueCase().equals(Resources.Primitive.OneofValueCase.BOOLEAN_VALUE)) {
            throw new UnexpectedAttributeTypeException((Resources.XmlAttributeOrBuilder)this.getProto(), "boolean");
        }
        return primitive.getBooleanValue();
    }

    public final int getValueAsRefId() {
        if (!this.getProto().getCompiledItem().getValueCase().equals(Resources.Item.ValueCase.REF)) {
            throw new UnexpectedAttributeTypeException((Resources.XmlAttributeOrBuilder)this.getProto(), "reference");
        }
        return this.getProto().getCompiledItem().getRef().getId();
    }

    public final int getValueAsDecimalInteger() {
        Resources.Primitive primitive = this.getProto().getCompiledItem().getPrim();
        if (!primitive.getOneofValueCase().equals(Resources.Primitive.OneofValueCase.INT_DECIMAL_VALUE)) {
            throw new UnexpectedAttributeTypeException((Resources.XmlAttributeOrBuilder)this.getProto(), "decimal int");
        }
        return primitive.getIntDecimalValue();
    }

    public final int getValueAsHexInteger() {
        Resources.Primitive primitive = this.getProto().getCompiledItem().getPrim();
        if (!primitive.getOneofValueCase().equals(Resources.Primitive.OneofValueCase.INT_HEXADECIMAL_VALUE)) {
            throw new UnexpectedAttributeTypeException((Resources.XmlAttributeOrBuilder)this.getProto(), "hexadecimal int");
        }
        return primitive.getIntHexadecimalValue();
    }

    public final int getValueAsInteger() {
        Resources.Primitive primitive = this.getProto().getCompiledItem().getPrim();
        switch (primitive.getOneofValueCase()) {
            case INT_DECIMAL_VALUE: {
                return primitive.getIntDecimalValue();
            }
            case INT_HEXADECIMAL_VALUE: {
                return primitive.getIntHexadecimalValue();
            }
        }
        throw new UnexpectedAttributeTypeException((Resources.XmlAttributeOrBuilder)this.getProto(), "decimal|hexadecimal int");
    }

    public final String getDebugString() {
        if (!this.getProto().hasCompiledItem()) {
            return this.getProto().getValue();
        }
        return XmlProtoPrintUtils.getItemValueAsString(this.getProto().getCompiledItem());
    }

    public String toString() {
        return this.getProto().toString();
    }
}

