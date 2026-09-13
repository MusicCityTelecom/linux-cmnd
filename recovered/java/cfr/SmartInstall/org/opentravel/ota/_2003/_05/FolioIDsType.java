/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="FolioIDsType", propOrder={"folioID"})
public class FolioIDsType {
    @XmlElement(name="FolioID", required=true)
    protected List<String> folioID;

    public List<String> getFolioID() {
        if (this.folioID == null) {
            this.folioID = new ArrayList<String>();
        }
        return this.folioID;
    }
}

