/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.firebase;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Generated;
import org.apereo.cas.configuration.model.SpringResourceProperties;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-notifications-fcm")
public class GoogleFirebaseCloudMessagingProperties
implements Serializable {
    private static final long serialVersionUID = -5679682641899738092L;
    @RequiredProperty
    private String registrationTokenAttributeName;
    private SpringResourceProperties serviceAccountKey;
    private String databaseUrl;
    private List<String> scopes = Stream.of("https://www.googleapis.com/auth/firebase.messaging").collect(Collectors.toList());

    @Generated
    public String getRegistrationTokenAttributeName() {
        return this.registrationTokenAttributeName;
    }

    @Generated
    public SpringResourceProperties getServiceAccountKey() {
        return this.serviceAccountKey;
    }

    @Generated
    public String getDatabaseUrl() {
        return this.databaseUrl;
    }

    @Generated
    public List<String> getScopes() {
        return this.scopes;
    }

    @Generated
    public GoogleFirebaseCloudMessagingProperties setRegistrationTokenAttributeName(String registrationTokenAttributeName) {
        this.registrationTokenAttributeName = registrationTokenAttributeName;
        return this;
    }

    @Generated
    public GoogleFirebaseCloudMessagingProperties setServiceAccountKey(SpringResourceProperties serviceAccountKey) {
        this.serviceAccountKey = serviceAccountKey;
        return this;
    }

    @Generated
    public GoogleFirebaseCloudMessagingProperties setDatabaseUrl(String databaseUrl) {
        this.databaseUrl = databaseUrl;
        return this;
    }

    @Generated
    public GoogleFirebaseCloudMessagingProperties setScopes(List<String> scopes) {
        this.scopes = scopes;
        return this;
    }
}

