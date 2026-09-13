/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataIntegrityViolationException
 */
package org.springframework.security.web.authentication.rememberme;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

public class InMemoryTokenRepositoryImpl
implements PersistentTokenRepository {
    private final Map<String, PersistentRememberMeToken> seriesTokens = new HashMap<String, PersistentRememberMeToken>();

    @Override
    public synchronized void createNewToken(PersistentRememberMeToken token) {
        PersistentRememberMeToken current = this.seriesTokens.get(token.getSeries());
        if (current != null) {
            throw new DataIntegrityViolationException("Series Id '" + token.getSeries() + "' already exists!");
        }
        this.seriesTokens.put(token.getSeries(), token);
    }

    @Override
    public synchronized void updateToken(String series, String tokenValue, Date lastUsed) {
        PersistentRememberMeToken token = this.getTokenForSeries(series);
        PersistentRememberMeToken newToken = new PersistentRememberMeToken(token.getUsername(), series, tokenValue, new Date());
        this.seriesTokens.put(series, newToken);
    }

    @Override
    public synchronized PersistentRememberMeToken getTokenForSeries(String seriesId) {
        return this.seriesTokens.get(seriesId);
    }

    @Override
    public synchronized void removeUserTokens(String username) {
        Iterator<String> series = this.seriesTokens.keySet().iterator();
        while (series.hasNext()) {
            String seriesId = series.next();
            PersistentRememberMeToken token = this.seriesTokens.get(seriesId);
            if (!username.equals(token.getUsername())) continue;
            series.remove();
        }
    }
}

