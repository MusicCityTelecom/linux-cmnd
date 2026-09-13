/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="SourceIdentificationType", propOrder={"value"})
public class SourceIdentificationType {
    @XmlValue
    protected String value;
    @XmlAttribute(name="Code")
    protected String code;
    @XmlAttribute(name="Issue")
    protected String issue;
    @XmlAttribute(name="Page")
    protected Integer page;

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String value) {
        this.code = value;
    }

    public String getIssue() {
        return this.issue;
    }

    public void setIssue(String value) {
        this.issue = value;
    }

    public Integer getPage() {
        return this.page;
    }

    public void setPage(Integer value) {
        this.page = value;
    }
}

