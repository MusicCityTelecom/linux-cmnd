/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.flywaydb.core.Flyway
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.core.Ordered
 *  org.springframework.util.Assert
 */
package org.springframework.boot.autoconfigure.flyway;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.core.Ordered;
import org.springframework.util.Assert;

public class FlywayMigrationInitializer
implements InitializingBean,
Ordered {
    private final Flyway flyway;
    private final FlywayMigrationStrategy migrationStrategy;
    private int order = 0;

    public FlywayMigrationInitializer(Flyway flyway) {
        this(flyway, null);
    }

    public FlywayMigrationInitializer(Flyway flyway, FlywayMigrationStrategy migrationStrategy) {
        Assert.notNull((Object)flyway, (String)"Flyway must not be null");
        this.flyway = flyway;
        this.migrationStrategy = migrationStrategy;
    }

    public void afterPropertiesSet() throws Exception {
        if (this.migrationStrategy != null) {
            this.migrationStrategy.migrate(this.flyway);
        } else {
            try {
                this.flyway.migrate();
            }
            catch (NoSuchMethodError ex) {
                this.flyway.getClass().getMethod("migrate", new Class[0]).invoke(this.flyway, new Object[0]);
            }
        }
    }

    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}

