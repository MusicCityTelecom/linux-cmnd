/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.xml;

import com.tpvision.smartinstall.xml.Folder;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"folder"})
@XmlRootElement(name="childrenFolders")
public class ChildrenFolders {
    protected List<Folder> folder;

    public List<Folder> getFolder() {
        if (null == this.folder) {
            this.folder = new ArrayList<Folder>();
        }
        return this.folder;
    }
}

