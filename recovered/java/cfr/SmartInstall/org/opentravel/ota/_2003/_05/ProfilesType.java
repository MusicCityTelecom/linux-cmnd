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
import org.opentravel.ota._2003._05.ProfileType;
import org.opentravel.ota._2003._05.UniqueIDType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ProfilesType", propOrder={"profileInfo"})
public class ProfilesType {
    @XmlElement(name="ProfileInfo", required=true)
    protected List<ProfileInfo> profileInfo;

    public List<ProfileInfo> getProfileInfo() {
        if (this.profileInfo == null) {
            this.profileInfo = new ArrayList<ProfileInfo>();
        }
        return this.profileInfo;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"uniqueID", "profile"})
    public static class ProfileInfo {
        @XmlElement(name="UniqueID")
        protected List<UniqueIDType> uniqueID;
        @XmlElement(name="Profile", required=true)
        protected ProfileType profile;

        public List<UniqueIDType> getUniqueID() {
            if (this.uniqueID == null) {
                this.uniqueID = new ArrayList<UniqueIDType>();
            }
            return this.uniqueID;
        }

        public ProfileType getProfile() {
            return this.profile;
        }

        public void setProfile(ProfileType value) {
            this.profile = value;
        }
    }
}

