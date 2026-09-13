/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.Registration
 */
package org.springframework.boot.actuate.web.mappings.servlet;

import javax.servlet.Registration;

public class RegistrationMappingDescription<T extends Registration> {
    private final T registration;

    public RegistrationMappingDescription(T registration) {
        this.registration = registration;
    }

    public String getName() {
        return this.registration.getName();
    }

    public String getClassName() {
        return this.registration.getClassName();
    }

    protected final T getRegistration() {
        return this.registration;
    }
}

