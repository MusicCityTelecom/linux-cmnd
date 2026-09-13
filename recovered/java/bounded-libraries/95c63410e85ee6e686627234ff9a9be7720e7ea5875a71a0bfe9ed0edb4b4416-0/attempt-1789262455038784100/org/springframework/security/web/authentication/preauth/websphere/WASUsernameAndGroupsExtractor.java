/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.security.web.authentication.preauth.websphere;

import java.util.List;

interface WASUsernameAndGroupsExtractor {
    public List<String> getGroupsForCurrentUser();

    public String getCurrentUserName();
}

