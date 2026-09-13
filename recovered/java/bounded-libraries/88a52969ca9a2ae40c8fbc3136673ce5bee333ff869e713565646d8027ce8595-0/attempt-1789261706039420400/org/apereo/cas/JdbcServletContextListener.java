/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletContextEvent
 *  javax.servlet.ServletContextListener
 *  javax.servlet.annotation.WebListener
 *  org.apereo.cas.CentralAuthenticationService
 */
package org.apereo.cas;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.apereo.cas.CentralAuthenticationService;

@WebListener
public class JdbcServletContextListener
implements ServletContextListener {
    public void contextInitialized(ServletContextEvent sce) {
    }

    public final void contextDestroyed(ServletContextEvent sce) {
        Logger logger = Logger.getLogger(CentralAuthenticationService.NAMESPACE);
        logger.fine("Unregistering JDBC drivers...");
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        Iterator<Driver> drivers = DriverManager.getDrivers().asIterator();
        while (drivers.hasNext()) {
            Driver driver = drivers.next();
            if (driver.getClass().getClassLoader() == cl) {
                try {
                    logger.fine("Attempting to deregister JDBC driver " + driver);
                    DriverManager.deregisterDriver(driver);
                }
                catch (SQLException ex) {
                    logger.log(Level.WARNING, "Error deregistering JDBC driver ", ex);
                }
                continue;
            }
            logger.fine("Not deregistering JDBC driver as it does not belong to this classloader: " + driver);
        }
    }
}

