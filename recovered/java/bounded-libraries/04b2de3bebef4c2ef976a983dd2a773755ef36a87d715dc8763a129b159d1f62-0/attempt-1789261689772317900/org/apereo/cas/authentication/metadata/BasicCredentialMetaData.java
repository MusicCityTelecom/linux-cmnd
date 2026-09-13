/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.Credential
 *  org.apereo.cas.authentication.DetailedCredentialMetaData
 */
package org.apereo.cas.authentication.metadata;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import lombok.Generated;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.DetailedCredentialMetaData;

public class BasicCredentialMetaData
implements DetailedCredentialMetaData {
    private static final long serialVersionUID = 4929579849241505377L;
    private final String id;
    private final Class<? extends Credential> credentialClass;
    private final Map<String, Serializable> properties = new HashMap<String, Serializable>();

    public BasicCredentialMetaData(Credential credential, Map<String, Serializable> properties) {
        this.id = credential.getId();
        this.credentialClass = credential.getClass();
        this.putProperties(properties);
    }

    public BasicCredentialMetaData(Credential credential) {
        this(credential, new HashMap<String, Serializable>());
    }

    public void putProperties(Map<String, Serializable> properties) {
        this.properties.putAll(properties);
    }

    @Generated
    public String getId() {
        return this.id;
    }

    @Generated
    public Class<? extends Credential> getCredentialClass() {
        return this.credentialClass;
    }

    @Generated
    public Map<String, Serializable> getProperties() {
        return this.properties;
    }

    @Generated
    public BasicCredentialMetaData() {
        this.id = null;
        this.credentialClass = null;
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof BasicCredentialMetaData)) {
            return false;
        }
        BasicCredentialMetaData other = (BasicCredentialMetaData)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$id = this.id;
        String other$id = other.id;
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) {
            return false;
        }
        Class<? extends Credential> this$credentialClass = this.credentialClass;
        Class<? extends Credential> other$credentialClass = other.credentialClass;
        if (this$credentialClass == null ? other$credentialClass != null : !this$credentialClass.equals(other$credentialClass)) {
            return false;
        }
        Map<String, Serializable> this$properties = this.properties;
        Map<String, Serializable> other$properties = other.properties;
        return !(this$properties == null ? other$properties != null : !((Object)this$properties).equals(other$properties));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof BasicCredentialMetaData;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $id = this.id;
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        Class<? extends Credential> $credentialClass = this.credentialClass;
        result = result * 59 + ($credentialClass == null ? 43 : $credentialClass.hashCode());
        Map<String, Serializable> $properties = this.properties;
        result = result * 59 + ($properties == null ? 43 : ((Object)$properties).hashCode());
        return result;
    }
}

