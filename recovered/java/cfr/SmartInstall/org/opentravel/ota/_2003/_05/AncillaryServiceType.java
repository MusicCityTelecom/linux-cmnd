/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.AncillaryService;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="AncillaryServiceType")
@XmlSeeAlso(value={AncillaryService.class})
public class AncillaryServiceType {
    @XmlAttribute(name="Description")
    protected String description;
    @XmlAttribute(name="Code", required=true)
    protected String code;
    @XmlAttribute(name="CodeContext")
    protected String codeContext;
    @XmlAttribute(name="Quantity")
    @XmlSchemaType(name="nonNegativeInteger")
    protected BigInteger quantity;

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String value) {
        this.code = value;
    }

    public String getCodeContext() {
        return this.codeContext;
    }

    public void setCodeContext(String value) {
        this.codeContext = value;
    }

    public BigInteger getQuantity() {
        return this.quantity;
    }

    public void setQuantity(BigInteger value) {
        this.quantity = value;
    }
}

