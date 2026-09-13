/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.xml.setting.v2k16;

import com.tpvision.smartinstall.xml.setting.v2k16.Item;
import com.tpvision.smartinstall.xml.setting.v2k16.SchemaVersion;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"schemaVersion", "item"})
@XmlRootElement(name="TVSettings")
public class TVSettings {
    @XmlElement(name="SchemaVersion", required=true)
    protected SchemaVersion schemaVersion;
    @XmlElement(required=true)
    protected List<Item> item;

    public void setItem(List<Item> item) {
        this.item = item;
    }

    public SchemaVersion getSchemaVersion() {
        return this.schemaVersion;
    }

    public void setSchemaVersion(SchemaVersion value) {
        this.schemaVersion = value;
    }

    public List<Item> getItem() {
        if (null == this.item) {
            this.item = new ArrayList<Item>();
        }
        return this.item;
    }
}

