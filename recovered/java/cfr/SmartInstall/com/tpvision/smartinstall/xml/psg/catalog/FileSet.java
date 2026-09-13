/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.xml.psg.catalog;

import com.tpvision.smartinstall.xml.psg.catalog.Files;
import com.tpvision.smartinstall.xml.psg.catalog.InterimFolder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"prefixFolder", "files", "requirement", "id", "interimFolder", "usage"})
@XmlRootElement(name="FileSet")
public class FileSet {
    @XmlElement(name="PrefixFolder", required=true)
    protected String prefixFolder;
    @XmlElement(name="Files", required=true)
    protected Files files;
    @XmlElement(name="Requirement", required=true)
    protected String requirement;
    @XmlElement(name="ID", required=true)
    protected String id;
    @XmlElement(name="InterimFolder", required=true)
    protected InterimFolder interimFolder;
    @XmlElement(name="Usage", required=true)
    protected String usage;

    public String getPrefixFolder() {
        return this.prefixFolder;
    }

    public void setPrefixFolder(String value) {
        this.prefixFolder = value;
    }

    public Files getFiles() {
        return this.files;
    }

    public void setFiles(Files value) {
        this.files = value;
    }

    public String getRequirement() {
        return this.requirement;
    }

    public void setRequirement(String value) {
        this.requirement = value;
    }

    public String getID() {
        return this.id;
    }

    public void setID(String value) {
        this.id = value;
    }

    public InterimFolder getInterimFolder() {
        return this.interimFolder;
    }

    public void setInterimFolder(InterimFolder value) {
        this.interimFolder = value;
    }

    public String getUsage() {
        return this.usage;
    }

    public void setUsage(String value) {
        this.usage = value;
    }
}

