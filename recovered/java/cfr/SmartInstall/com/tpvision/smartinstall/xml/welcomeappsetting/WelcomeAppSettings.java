/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.xml.welcomeappsetting;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"schemaVersion", "item"})
@XmlRootElement(name="WelcomeAppSettings")
public class WelcomeAppSettings {
    @XmlElement(name="SchemaVersion", required=true)
    protected SchemaVersion schemaVersion;
    protected List<Item> item;

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

    public Item getItemByName(String itemName) {
        for (Item aitem : this.getItem()) {
            if (!aitem.getName().equalsIgnoreCase(itemName)) continue;
            return aitem;
        }
        Item newItem = new Item();
        newItem.name = itemName;
        newItem.value = "";
        newItem.cloneIn = "Yes";
        this.getItem().add(newItem);
        return newItem;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"value"})
    public static class SchemaVersion {
        @XmlValue
        protected String value;
        @XmlAttribute(name="MajorVerNo")
        protected Byte majorVerNo;
        @XmlAttribute(name="MinorVerNo")
        protected Byte minorVerNo;

        public String getValue() {
            return this.value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public Byte getMajorVerNo() {
            return this.majorVerNo;
        }

        public void setMajorVerNo(Byte value) {
            this.majorVerNo = value;
        }

        public Byte getMinorVerNo() {
            return this.minorVerNo;
        }

        public void setMinorVerNo(Byte value) {
            this.minorVerNo = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"name", "value", "cloneIn"})
    public static class Item {
        @XmlElement(name="Name", required=true)
        protected String name;
        @XmlElement(name="Value", required=true)
        protected String value;
        @XmlElement(name="CloneIn", required=true)
        protected String cloneIn;

        public String getName() {
            return this.name;
        }

        public void setName(String value) {
            this.name = value;
        }

        public String getValue() {
            return this.value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getCloneIn() {
            return this.cloneIn;
        }

        public void setCloneIn(String value) {
            this.cloneIn = value;
        }
    }
}

