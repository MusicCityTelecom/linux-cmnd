/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.xml.script;

import com.tpvision.smartinstall.xml.script.Attributes;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="Activity")
@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(propOrder={"action", "priority", "attributes"})
public class Activity {
    @XmlElement(name="Action", required=false)
    protected String action;
    @XmlElement(name="Priority", required=false)
    protected String priority;
    @XmlElement(name="Attributes", required=false)
    protected Attributes attributes;

    public String getAction() {
        return this.action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getPriority() {
        return this.priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Attributes getAttributes() {
        return this.attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }
}

