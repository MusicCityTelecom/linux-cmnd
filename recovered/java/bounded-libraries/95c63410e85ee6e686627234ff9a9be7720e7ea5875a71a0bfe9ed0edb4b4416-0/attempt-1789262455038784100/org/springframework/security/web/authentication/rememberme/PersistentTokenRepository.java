/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.security.web.authentication.rememberme;

import java.util.Date;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;

public interface PersistentTokenRepository {
    public void createNewToken(PersistentRememberMeToken var1);

    public void updateToken(String var1, String var2, Date var3);

    public PersistentRememberMeToken getTokenForSeries(String var1);

    public void removeUserTokens(String var1);
}

