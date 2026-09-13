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
@XmlType(name="FormattedTextSubSectionType", propOrder={"paragraph"})
public class FormattedTextSubSectionType {
    @XmlElement(name="Paragraph", required=true)
    protected List<ParagraphType> paragraph;
    @XmlAttribute(name="SubTitle")
    protected String subTitle;
    @XmlAttribute(name="SubCode")
    protected String subCode;
    @XmlAttribute(name="SubSectionNumber")
    protected Integer subSectionNumber;

    public List<ParagraphType> getParagraph() {
        if (this.paragraph == null) {
            this.paragraph = new ArrayList<ParagraphType>();
        }
        return this.paragraph;
    }

    public String getSubTitle() {
        return this.subTitle;
    }

    public void setSubTitle(String value) {
        this.subTitle = value;
    }

    public String getSubCode() {
        return this.subCode;
    }

    public void setSubCode(String value) {
        this.subCode = value;
    }

    public Integer getSubSectionNumber() {
        return this.subSectionNumber;
    }

    public void setSubSectionNumber(Integer value) {
        this.subSectionNumber = value;
    }
}

