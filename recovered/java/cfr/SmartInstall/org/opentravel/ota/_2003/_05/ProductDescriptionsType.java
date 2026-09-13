/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.ParagraphType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ProductDescriptionsType", propOrder={"productDescription"})
public class ProductDescriptionsType {
    @XmlElement(name="ProductDescription", required=true)
    protected List<ProductDescription> productDescription;

    public List<ProductDescription> getProductDescription() {
        if (this.productDescription == null) {
            this.productDescription = new ArrayList<ProductDescription>();
        }
        return this.productDescription;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"description"})
    public static class ProductDescription {
        @XmlElement(name="Description")
        protected ParagraphType description;
        @XmlAttribute(name="ProductDescriptionRPH")
        protected String productDescriptionRPH;

        public ParagraphType getDescription() {
            return this.description;
        }

        public void setDescription(ParagraphType value) {
            this.description = value;
        }

        public String getProductDescriptionRPH() {
            return this.productDescriptionRPH;
        }

        public void setProductDescriptionRPH(String value) {
            this.productDescriptionRPH = value;
        }
    }
}

