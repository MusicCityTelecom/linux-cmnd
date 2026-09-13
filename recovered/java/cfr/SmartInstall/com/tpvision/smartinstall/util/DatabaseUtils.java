/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.util;

import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.zaxxer.hikari.HikariDataSource;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.sql.Connection;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseUtils {
    private static final Logger LOG = LoggerFactory.getLogger(DatabaseUtils.class);
    private static DatabaseUtils singleton = new DatabaseUtils();
    private boolean databaseInited;
    private boolean updated;

    public static DatabaseUtils getInstance() {
        return singleton;
    }

    public boolean checkDatabaseInited() {
        boolean inited = false;
        try (Connection conn = JpaManager.getConnection();){
            inited = true;
        }
        catch (Exception e) {
            LOG.error("database not inited or user and pass not match", e);
        }
        this.setDatabaseInited(inited);
        return inited;
    }

    public void migrate() {
        LOG.info("flyway migrating");
        boolean isupdated = false;
        if (!this.isDatabaseInited()) {
            LOG.error("database not inited, exit");
            this.setUpdated(isupdated);
            return;
        }
        this.setUpdated(isupdated);
        HikariDataSource dataSource = (HikariDataSource)JpaManager.getDataSource();
        try {
            Flyway flyway = Flyway.configure().baselineOnMigrate(true).dataSource(dataSource.getJdbcUrl(), dataSource.getUsername(), dataSource.getPassword()).load();
            flyway.migrate();
        }
        catch (Exception e) {
            LOG.error("migrate fail," + e.getMessage(), e);
        }
        isupdated = true;
        this.setUpdated(isupdated);
    }

    public String getWebinfPath() {
        URI classesUri = null;
        try {
            classesUri = Thread.currentThread().getContextClassLoader().getResource("").toURI();
        }
        catch (URISyntaxException e) {
            LOG.error(e.getMessage(), e);
        }
        return Paths.get(classesUri).toFile().getParentFile().getAbsolutePath();
    }

    public boolean isDatabaseInited() {
        return this.databaseInited;
    }

    public void setDatabaseInited(boolean databaseInited) {
        this.databaseInited = databaseInited;
    }

    public boolean isUpdated() {
        return this.updated;
    }

    public void setUpdated(boolean updated) {
        this.updated = updated;
    }
}

