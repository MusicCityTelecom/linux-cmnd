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
import org.opentravel.ota._2003._05.WarningType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="WarningsType", propOrder={"warning"})
public class WarningsType {
    @XmlElement(name="Warning", required=true)
    protected List<WarningType> warning;

    public List<WarningType> getWarning() {
        if (this.warning == null) {
            this.warning = new ArrayList<WarningType>();
        }
        return this.warning;
    }
}

