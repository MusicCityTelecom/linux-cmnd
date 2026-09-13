/*
 * Decompiled with CFR 0.152.
 */
package org.htng._2011b;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import org.htng._2011b.HTNGComponentRoomType;
import org.htng._2011b.HTNGRequestBaseType;
import org.opentravel.ota._2003._05.ActionType;
import org.opentravel.ota._2003._05.TPAExtensionsType;
import org.opentravel.ota._2003._05.UniqueIDType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"roomInformationList"})
@XmlRootElement(name="HTNG_WakeupSchedulingNotifRQ")
public class HTNGWakeupSchedulingNotifRQ
extends HTNGRequestBaseType {
    @XmlElement(name="RoomInformationList", required=true)
    protected RoomInformationList roomInformationList;

    public RoomInformationList getRoomInformationList() {
        return this.roomInformationList;
    }

    public void setRoomInformationList(RoomInformationList value) {
        this.roomInformationList = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"wakeupID", "room", "wakeupInfo", "tpaExtensions"})
    public static class RoomInformationList {
        @XmlElement(name="WakeupID")
        protected UniqueIDType wakeupID;
        @XmlElement(name="Room")
        protected HTNGComponentRoomType room;
        @XmlElement(name="WakeupInfo", required=true)
        protected WakeupInfo wakeupInfo;
        @XmlElement(name="TPA_Extensions", namespace="http://www.opentravel.org/OTA/2003/05")
        protected TPAExtensionsType tpaExtensions;

        public UniqueIDType getWakeupID() {
            return this.wakeupID;
        }

        public void setWakeupID(UniqueIDType value) {
            this.wakeupID = value;
        }

        public HTNGComponentRoomType getRoom() {
            return this.room;
        }

        public void setRoom(HTNGComponentRoomType value) {
            this.room = value;
        }

        public WakeupInfo getWakeupInfo() {
            return this.wakeupInfo;
        }

        public void setWakeupInfo(WakeupInfo value) {
            this.wakeupInfo = value;
        }

        public TPAExtensionsType getTPAExtensions() {
            return this.tpaExtensions;
        }

        public void setTPAExtensions(TPAExtensionsType value) {
            this.tpaExtensions = value;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="")
        public static class WakeupInfo {
            @XmlAttribute(name="WakeupTime")
            @XmlSchemaType(name="dateTime")
            protected XMLGregorianCalendar wakeupTime;
            @XmlAttribute(name="TotalDays")
            protected Integer totalDays;
            @XmlAttribute(name="Action")
            protected ActionType action;

            public XMLGregorianCalendar getWakeupTime() {
                return this.wakeupTime;
            }

            public void setWakeupTime(XMLGregorianCalendar value) {
                this.wakeupTime = value;
            }

            public int getTotalDays() {
                if (this.totalDays == null) {
                    return 1;
                }
                return this.totalDays;
            }

            public void setTotalDays(Integer value) {
                this.totalDays = value;
            }

            public ActionType getAction() {
                return this.action;
            }

            public void setAction(ActionType value) {
                this.action = value;
            }
        }
    }
}

