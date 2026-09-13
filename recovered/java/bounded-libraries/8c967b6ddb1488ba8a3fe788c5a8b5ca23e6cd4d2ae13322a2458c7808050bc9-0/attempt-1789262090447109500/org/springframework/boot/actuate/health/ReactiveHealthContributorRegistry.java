/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.health;

import org.springframework.boot.actuate.health.ContributorRegistry;
import org.springframework.boot.actuate.health.ReactiveHealthContributor;

public interface ReactiveHealthContributorRegistry
extends ContributorRegistry<ReactiveHealthContributor> {
}

