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
import org.opentravel.ota._2003._05.SourceType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="POS_Type", propOrder={"source"})
public class POSType {
    @XmlElement(name="Source", required=true)
    protected List<SourceType> source;

    public List<SourceType> getSource() {
        if (this.source == null) {
            this.source = new ArrayList<SourceType>();
        }
        return this.source;
    }
}

