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
import org.htng._2011b.HTNGComponentRoomType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="HTNG_ComponentRoomsType", propOrder={"componentRoom"})
public class HTNGComponentRoomsType {
    @XmlElement(name="ComponentRoom", required=true)
    protected List<HTNGComponentRoomType> componentRoom;

    public List<HTNGComponentRoomType> getComponentRoom() {
        if (this.componentRoom == null) {
            this.componentRoom = new ArrayList<HTNGComponentRoomType>();
        }
        return this.componentRoom;
    }
}

