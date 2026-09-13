/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 */
package org.springframework.messaging.simp.user;

import java.util.Set;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

public class UserDestinationResult {
    private final String sourceDestination;
    private final Set<String> targetDestinations;
    private final String subscribeDestination;
    @Nullable
    private final String user;

    public UserDestinationResult(String sourceDestination, Set<String> targetDestinations, String subscribeDestination, @Nullable String user) {
        Assert.notNull((Object)sourceDestination, (String)"'sourceDestination' must not be null");
        Assert.notNull(targetDestinations, (String)"'targetDestinations' must not be null");
        Assert.notNull((Object)subscribeDestination, (String)"'subscribeDestination' must not be null");
        this.sourceDestination = sourceDestination;
        this.targetDestinations = targetDestinations;
        this.subscribeDestination = subscribeDestination;
        this.user = user;
    }

    public String getSourceDestination() {
        return this.sourceDestination;
    }

    public Set<String> getTargetDestinations() {
        return this.targetDestinations;
    }

    public String getSubscribeDestination() {
        return this.subscribeDestination;
    }

    @Nullable
    public String getUser() {
        return this.user;
    }

    public String toString() {
        return "UserDestinationResult [source=" + this.sourceDestination + ", target=" + this.targetDestinations + ", subscribeDestination=" + this.subscribeDestination + ", user=" + this.user + "]";
    }
}

