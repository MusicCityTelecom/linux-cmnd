/*
 * Decompiled with CFR 0.152.
 */
package org.htng._2011b;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="HTNG_EventFiltersType", propOrder={"filter"})
public class HTNGEventFiltersType {
    @XmlElement(name="Filter", required=true)
    protected List<String> filter;

    public List<String> getFilter() {
        if (this.filter == null) {
            this.filter = new ArrayList<String>();
        }
        return this.filter;
    }
}

