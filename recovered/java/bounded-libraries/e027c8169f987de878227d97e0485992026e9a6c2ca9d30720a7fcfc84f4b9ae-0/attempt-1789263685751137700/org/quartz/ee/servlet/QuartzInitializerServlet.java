/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletConfig
 *  javax.servlet.ServletException
 *  javax.servlet.http.HttpServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package org.quartz.ee.servlet;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzInitializerServlet
extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public static final String QUARTZ_FACTORY_KEY = "org.quartz.impl.StdSchedulerFactory.KEY";
    private boolean performShutdown = true;
    private boolean waitOnShutdown = false;
    private transient Scheduler scheduler = null;

    public void init(ServletConfig cfg) throws ServletException {
        super.init(cfg);
        this.log("Quartz Initializer Servlet loaded, initializing Scheduler...");
        try {
            String configFile = cfg.getInitParameter("config-file");
            String shutdownPref = cfg.getInitParameter("shutdown-on-unload");
            if (shutdownPref != null) {
                this.performShutdown = Boolean.valueOf(shutdownPref);
            }
            String shutdownWaitPref = cfg.getInitParameter("wait-on-shutdown");
            if (shutdownPref != null) {
                this.waitOnShutdown = Boolean.valueOf(shutdownWaitPref);
            }
            StdSchedulerFactory factory = this.getSchedulerFactory(configFile);
            this.scheduler = factory.getScheduler();
            String startOnLoad = cfg.getInitParameter("start-scheduler-on-load");
            int startDelay = 0;
            String startDelayS = cfg.getInitParameter("start-delay-seconds");
            try {
                if (startDelayS != null && startDelayS.trim().length() > 0) {
                    startDelay = Integer.parseInt(startDelayS);
                }
            }
            catch (Exception e) {
                this.log("Cannot parse value of 'start-delay-seconds' to an integer: " + startDelayS + ", defaulting to 5 seconds.", e);
                startDelay = 5;
            }
            if (startOnLoad == null || Boolean.valueOf(startOnLoad).booleanValue()) {
                if (startDelay <= 0) {
                    this.scheduler.start();
                    this.log("Scheduler has been started...");
                } else {
                    this.scheduler.startDelayed(startDelay);
                    this.log("Scheduler will start in " + startDelay + " seconds.");
                }
            } else {
                this.log("Scheduler has not been started. Use scheduler.start()");
            }
            String factoryKey = cfg.getInitParameter("servlet-context-factory-key");
            if (factoryKey == null) {
                factoryKey = QUARTZ_FACTORY_KEY;
            }
            this.log("Storing the Quartz Scheduler Factory in the servlet context at key: " + factoryKey);
            cfg.getServletContext().setAttribute(factoryKey, (Object)factory);
            String servletCtxtKey = cfg.getInitParameter("scheduler-context-servlet-context-key");
            if (servletCtxtKey != null) {
                this.log("Storing the ServletContext in the scheduler context at key: " + servletCtxtKey);
                this.scheduler.getContext().put(servletCtxtKey, (Object)cfg.getServletContext());
            }
        }
        catch (Exception e) {
            this.log("Quartz Scheduler failed to initialize: " + e.toString());
            throw new ServletException((Throwable)e);
        }
    }

    protected StdSchedulerFactory getSchedulerFactory(String configFile) throws SchedulerException {
        StdSchedulerFactory factory = configFile != null ? new StdSchedulerFactory(configFile) : new StdSchedulerFactory();
        return factory;
    }

    public void destroy() {
        if (!this.performShutdown) {
            return;
        }
        try {
            if (this.scheduler != null) {
                this.scheduler.shutdown(this.waitOnShutdown);
            }
        }
        catch (Exception e) {
            this.log("Quartz Scheduler failed to shutdown cleanly: " + e.toString());
            e.printStackTrace();
        }
        this.log("Quartz Scheduler successful shutdown.");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendError(403);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendError(403);
    }
}

